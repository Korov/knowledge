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

func HelloDefault(c *gin.Context) {
	c.JSON(http.StatusOK, gin.H{
		"message": "Hello Gin",
	})
}

func HelloWithName(c *gin.Context) {
	name := c.Param("name")
	c.JSON(http.StatusOK, gin.H{
		"message": fmt.Sprintf("Hello %s", name),
	})
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
	collection = db.Collection("key-count")
	collection = collection

	type KeyCount struct {
		Key string `bson:"key"`
		Value string `bson:"value"`
		Count int64 `bson:"count"`
	}

	var results []KeyCount
	cur, err := collection.Find(context.TODO(), bson.D{})
	if err != nil {
		fmt.Println(err)
	}
	cur.All(context.TODO(), &results)
	for index, result := range results {
		fmt.Printf("index:%d, key:%s, value:%s, count:%d\n",index, result.Key, result.Value, result.Count)
	}
}
