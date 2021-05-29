package main

import (
	"fmt"
	"trend/router"
)

func main() {
	r := router.GetRouter()

	if err := r.Run("korov-linux.org:8180"); err != nil {
		fmt.Println("startup service failed, err:%v\n", err)
	}
}
