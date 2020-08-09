package detail

import "fmt"

func base1(value int) {
	/**
	var v1 int
	var v2 string
	var v3 [10]int //数组
	var v4 []int //数组切片
	var v5 struct{
		f int
	}
	var v6 *int // 指针
	var v7 map[string]int // map, key为string类型，value为int类型
	var v8 func(a int) int

	// 同时声明多个变量
	var (
		v9 int
		v10 string
	)
	*/

	/**
	变量初始化
	var v1 int = 10
	var v2 = 10
	v3 := 10
	*/
	switch value {
	case 0:
		fmt.Printf("Value:%s", value)
	case 2:
		fmt.Printf("Value:%s", value)
	case 3:
		fallthrough
	case 4:
		fmt.Printf("Value:%s", value)
	default:
		fmt.Printf("Value:%s", 0)
	}
}
