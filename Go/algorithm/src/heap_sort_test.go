package src_test

import (
	. "algorithm/src"
	"fmt"
	"testing"
)

/**
自带的单元测试框架
 */

// 单元测试，必须 Test 开头
func TestHeapSort(t *testing.T) {
	var logger = GetLogger()
	logger.Info("start heap sort test")
	var expect = "1,3,4,6,7,9,"

	var values = []int{3, 4, 1, 9, 7, 6}
	var input string
	for _, value := range values {
		input += fmt.Sprintf("%d", value) + ","
	}
	println("input: ", input)

	var result, _ = HeapSort(values)

	var output string
	for _, value := range result {
		output += fmt.Sprintf("%d", value) + ","
	}
	println("output: ", output)

	// 自带的框架没有assert只能通过日志来确定测试是否成功
	if expect == output {
        t.Log("heap sort success")
	} else {
		t.Error("heap sort failed")
	}
}

// 性能测试
func BenchmarkHeapSort(b *testing.B) {
	b.StopTimer() // 暂停计时器
    // TODO 可以做一些前期的准备工作
	b.StartTimer() // 开启计时器，之前的准备时间未计入总花费时间内
	for i := 0; i < b.N; i++ {
		var values = []int{3, 4, 1, 9, 7, 6}
		HeapSort(values)
	}
}

