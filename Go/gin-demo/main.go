package main

import (
	"fmt"
	"gin-demo/routers"
)

func main() {
	r := routers.GetRouter()

	if err := ; err != nil {
		fmt.Println("startup service failed, err:%v\n", err)
	}
}
