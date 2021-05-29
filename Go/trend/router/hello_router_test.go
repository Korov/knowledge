package router

import (
	"fmt"
	"github.com/go-playground/assert/v2"
	"net/http"
	"net/http/httptest"
	"testing"
	"time"
)

func TestGetKeyCount(t *testing.T) {
	GetKeyCount()
}

func TestGetNameByTime(t *testing.T) {
	router1 := GetRouter()

	w := httptest.NewRecorder()
	req, _ := http.NewRequest("GET", "/v1/get/name/time", nil)
	router1.ServeHTTP(w, req)
	fmt.Println(w.Body.String())

	assert.Equal(t, 200, w.Code)
	//GetNameByTime()
}

func TestDemo(t *testing.T) {
	var endTime = time.Now().Unix() * 1000
	var startTime = endTime - 4*24*60*60*1000

	fmt.Printf("start time:%d, end time:%d\n", startTime, endTime)
	var timePoint = make([]int64, 0, 0)

	fmt.Printf("time point size:%d\n", len(timePoint))

	var startPoint = startTime / 60000
	var entPoint = endTime / 60000
	for point := startPoint; point <= entPoint; point = point + 1 {
		fmt.Printf("point:%d\n", point)
		timePoint = append(timePoint, point)
	}

	fmt.Printf("time point size:%d\n", len(timePoint))
}
