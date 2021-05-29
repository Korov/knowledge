package router

import (
	"context"
	"fmt"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
	"net/http"
	"time"

	"github.com/gin-gonic/gin"
)

func HelloDefault(context *gin.Context) {
	context.JSON(http.StatusOK, gin.H{
		"message": "Hello Gin",
	})
}

func GetSeries(context *gin.Context) {
	context.JSON(http.StatusOK, gin.H{
		"data": "[220, 182, 191, 234, 290, 330, 310]",
	})
}

type KeyCount struct {
	Key       string `bson:"key"`
	Name      string `bson:"name"`
	Message   string `bson:"message"`
	Value     string `bson:"value"`
	Timestamp int64  `bson:"timestamp"`
	Count     int64  `bson:"count"`
}

func GetKeyCount() {
	var (
		client     *mongo.Client
		err        error
		db         *mongo.Database
		collection *mongo.Collection
	)
	if client, err = mongo.Connect(context.TODO(), options.Client().ApplyURI("mongodb://admin:admin@localhost:27017").SetConnectTimeout(5*time.Second)); err != nil {
		fmt.Print(err)
		return
	}
	db = client.Database("admin")
	collection = db.Collection("kafka-name-count")
	collection = collection

	var findOptions = options.Find().SetSort(bson.D{{"timestamp", -1}})
	findOptions.SetLimit(100)
	findOptions.SetSkip(100 * 0)
	var spl_results []KeyCount
	spl_cur, err := collection.Find(context.TODO(), bson.D{{"key", "spl_alert"}}, findOptions)
	if err != nil {
		fmt.Println(err)
	}
	spl_cur.All(context.TODO(), &spl_results)
	for index, result := range spl_results {
		//fmt.Printf("index:%d, key:%s, name:%s, message:%s, value:%s, timestamp:%d, count:%d\n", index, result.Key, result.Name, result.Message, result.Value, result.Timestamp, result.Count)
		fmt.Printf("index:%d, key:%s, name:%s, timestamp:%d, count:%d\n", index, result.Key, result.Name, result.Timestamp, result.Count)
	}

	var flink_results []KeyCount
	flink_cur, err := collection.Find(context.TODO(), bson.D{{"key", "flink_alert"}}, findOptions)
	if err != nil {
		fmt.Println(err)
	}
	flink_cur.All(context.TODO(), &flink_results)
	for index, result := range flink_results {
		//fmt.Printf("index:%d, key:%s, name:%s, message:%s, value:%s, timestamp:%d, count:%d\n", index, result.Key, result.Name, result.Message, result.Value, result.Timestamp, result.Count)
		fmt.Printf("index:%d, key:%s, name:%s, timestamp:%d, count:%d\n", index, result.Key, result.Name, result.Timestamp, result.Count)
	}

	var sortOption = options.Find().SetSort(bson.D{{"timestamp", -1}})
	var endTime = time.Now().Unix() * 1000
	var startTime = endTime - 24*60*60*1000
	var time_result []KeyCount
	var filter = bson.M{"timestamp": bson.M{"$gt": startTime, "$lt": endTime}, "key": bson.M{"$in": []string{"spl_alert", "flink_alert"}}}
	time_cur, err := collection.Find(context.TODO(), filter, sortOption)
	if err != nil {
		fmt.Println(err)
	}
	time_cur.All(context.TODO(), &time_result)
	for index, result := range time_result {
		//fmt.Printf("index:%d, key:%s, name:%s, message:%s, value:%s, timestamp:%d, count:%d\n", index, result.Key, result.Name, result.Message, result.Value, result.Timestamp, result.Count)
		fmt.Printf("index:%d, key:%s, name:%s, timestamp:%d, count:%d\n", index, result.Key, result.Name, result.Timestamp, result.Count)
	}
}

type KeyNameCount struct {
	Name     string
	Type     string
	CountMap map[int64]int64
	Count    []int64
}

func GetNameByTime(ginContext *gin.Context) {
	var (
		client     *mongo.Client
		err        error
		db         *mongo.Database
		collection *mongo.Collection
	)
	if client, err = mongo.Connect(context.TODO(), options.Client().ApplyURI("mongodb://admin:admin@localhost:27017").SetConnectTimeout(5*time.Second)); err != nil {
		fmt.Print(err)
		return
	}
	db = client.Database("admin")
	collection = db.Collection("kafka-name-count")
	collection = collection

	var sortOption = options.Find().SetSort(bson.D{{"timestamp", -1}})
	var endTime = time.Now().Unix()*1000 - 2*24*60*60*1000
	var startTime = endTime - 1*24*60*60*1000

	fmt.Printf("start time:%d, end time:%d\n", startTime, endTime)
	var timePoint = make([]int64, 0, (endTime-startTime)/60000+100)
	var startPoint = startTime / 60000
	var entPoint = endTime / 60000
	for point := startPoint; point <= entPoint; point = point + 1 {
		timePoint = append(timePoint, point*60000)
	}

	var time_result []KeyCount
	var filter = bson.M{"timestamp": bson.M{"$gt": startTime}, "key": bson.M{"$in": []string{"spl_alert", "flink_alert"}}}
	time_cur, err := collection.Find(context.TODO(), filter, sortOption)
	if err != nil {
		fmt.Println(err)
	}
	time_cur.All(context.TODO(), &time_result)

	var keyNameMap = make(map[string]KeyNameCount)

	for _, result := range time_result {
		//fmt.Printf("index:%d, key:%s, timestamp:%d, name:%s, count:%d\n", index, result.Key, result.Timestamp, result.Name, result.Count)
		nameMap, ok := keyNameMap[result.Key+"-"+result.Name]
		if ok {
			var timePoint = (result.Timestamp / 60000) * 60000
			_, ok1 := nameMap.CountMap[timePoint]
			if !ok1 {
				nameMap.CountMap[timePoint] = result.Count
			}
		} else {
			var timePoint = (result.Timestamp / 60000) * 60000
			var nameCount = KeyNameCount{Name: result.Key + "-" + result.Name, Type: "line", CountMap: make(map[int64]int64), Count: make([]int64, 0, 0)}
			nameCount.CountMap[timePoint] = result.Count
			keyNameMap[result.Key+"-"+result.Name] = nameCount
		}
	}

	var keyNameArray = make([]KeyNameCount, 0, len(keyNameMap))
	for _, keyMap := range keyNameMap {
		for _, point := range timePoint {
			count, ok := keyMap.CountMap[point]
			if ok {
				keyMap.Count = append(keyMap.Count, count)
			} else {
				keyMap.Count = append(keyMap.Count, 0)
			}
		}
		keyMap.CountMap = make(map[int64]int64)
		keyNameArray = append(keyNameArray, keyMap)
	}

	ginContext.JSON(http.StatusOK, gin.H{
		"datas": keyNameArray, "timepoint": timePoint,
	})
}
