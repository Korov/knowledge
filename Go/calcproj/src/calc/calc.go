package main

import "os"
import "fmt"
import "simplemath"
import "strconv"

var Usage = func() {
	fmt.Println("USAGE: calc command [arguments] ...")
	fmt.Println("\nThe Command are")
}

func main()  {
	args := os.Args
	if args == nil || len(args) < 2 {
		Usage()
		return
	}

	switch args[0] {
	case "add":
		v1, err1 := strconv.Atoi(args[1])
		v2, err2 := strconv.Atoi(args[2])
		ret := simplemath.Add(v1, v2)
		fmt.Println("Result: ", ret)
	case "sqrt":
		v, err := strconv.Atoi(args[1])
		ret := simplemath.Sqrt(v)
		fmt.Println("Result: ", ret)
	}
}