package algorithm

import (
	"io"
	"log"
	"github.com/sirupsen/logrus"
	"os"
)

var (
	Info    *log.Logger
	Warning *log.Logger
	Error   *log.Logger
	//mylog = logrus.New()
)

func init() {
	//mylog.Out = os.Stdout
	//mylog.Formatter = &logrus.JSONFormatter{}

	logFile, err := os.OpenFile("logs.log", os.O_CREATE|os.O_WRONLY|os.O_APPEND, 0666)
	if err != nil {
		log.Fatalln("打开日志文件失败：", err)
	}

	Info = log.New(io.MultiWriter(os.Stderr, logFile), "Info:", log.Ldate|log.Ltime|log.Lshortfile)
	Warning = log.New(io.MultiWriter(os.Stderr, logFile), "Warning:", log.Ldate|log.Ltime|log.Lshortfile)
	Error = log.New(io.MultiWriter(os.Stderr, logFile), "Error:", log.Ldate|log.Ltime|log.Lshortfile)
}
