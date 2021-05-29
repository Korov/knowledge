package main

import (
	"fmt"
	"gin-demo/routers"
)

func main() {
	r := routers.GetRouter()

	if err := r.Run("korov-linux.org:8180"); err != nil {
		fmt.Println("startup service failed, err:%v\n", err)
	}
}
