package src

import (
	"fmt"
	"github.com/cihub/seelog"
)

var Logger seelog.LoggerInterface

func init() {
	var err error
	Logger = seelog.Disabled
	Logger, err = seelog.LoggerFromConfigAsFile("seelog.xml")
	if err != nil {
		fmt.Println(err)
		return
	}
}