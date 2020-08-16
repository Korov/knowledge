package demo

import (
	"fmt"
	logrus "github.com/sirupsen/logrus"
	"log"
	"os"
	"strings"
)

func init() {
	log.SetPrefix("TRACE: ")
	log.SetFlags(log.Ldate | log.Lmicroseconds | log.Llongfile)
	logrus.SetFormatter(&logrus.JSONFormatter{}) //设置日志的输出格式为json格式，还可以设置为text格式
	logrus.SetOutput(os.Stdout)                  //设置日志的输出为标准输出
	logrus.SetLevel(logrus.InfoLevel)            //设置日志的显示级别，这一级别以及更高级别的日志信息将会输出
}

func demo1(value string, another string) {
	fmt.Printf("value: %s, another: %s", value, another)
	log.Printf("value: %s, another: %s", value, another)
	logrus.WithFields(logrus.Fields{"info": fmt.Sprintf("value: %s, another: %s", value, another)}).Info("临时测试")
	after := strings.Trim(value, another)
	fmt.Printf("result: %s", after)
	log.Printf("result: %s", after)
	logrus.WithFields(logrus.Fields{"info": fmt.Sprintf("result: %s", after)}).Info("临时测试")
}
