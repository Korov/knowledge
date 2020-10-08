# 设置go的proxy

```bash
go env -w GOPROXY=https://mirrors.aliyun.com/goproxy/
# export GOPROXY=https://mirrors.aliyun.com/goproxy/
# 项目初始化的时候需要执行此命令
go mod init algorithm
# 下载依赖
go mod download
```

# 日志框架

logrus

# 测试框架

testing，使用在代码路径下执行`go test -v`会将执行结果输出到界面上。

测试框架Ginkgo用不了`go get -u github.com/onsi/ginkgo/ginkgo`

日志框架logrus也用不了

