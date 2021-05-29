package router

import (
	"fmt"
	"testing"
	"time"
)

func TestGetKeyCount(t *testing.T) {
	GetKeyCount()
}

func TestGetNameByTime(t *testing.T) {
	GetNameByTime()
}

func TestDemo(t *testing.T) {
	var endTime = time.Now().Unix()*1000
	var startTime = endTime - 24*60*60*1000
	fmt.Printf("start time:%d\n", startTime)
	fmt.Printf("end time:%d\n", endTime)
}
