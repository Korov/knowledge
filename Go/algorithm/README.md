# 设置go的proxy

```bash
# mac或linux
export GO111MODULE=on
export GOPROXY=https://goproxy.cn
# windows powershell
$env:GO111MODULE = "on"
$env:GOPROXY = "https://goproxy.cn"
# 项目初始化的时候需要执行此命令
go mod init algorithm
# 下载依赖
go mod download
# 删除无用依赖，下载缺少的依赖
go mod tidy
```

# 日志框架

seelog

# 测试框架

testing，使用在代码路径下执行`go test -v`会将执行结果输出到界面上。

测试框架Ginkgo用不了`go get -u github.com/onsi/ginkgo/ginkgo`, `go get -u github.com/onsi/gomega/...`，执行完这两个命令之后ginkgo会被放到GOPATH的bin中

Ginkgo使用，进入到需要测试的代码路径

```bash
ginkgo bootstrap # set up a new ginkgo suite
ginkgo generate  # will create a sample test file.  edit this file and add your tests then...

go test # to run your tests

ginkgo  # also runs your tests
```



```
# 测试所有测试类
go test -v ./src -cover
# 测试指定类
go test -v ./src/merge_sort_test.go
# 测试单个函数 Ginkgo不知道怎么测单个函数
go test -v ./src/merge_sort_test.go -test.run TestHello
```

```bash
# 生成测试覆盖率报告
go test -v ./src -coverprofile=./report/covprofile
# 生成的文件转化程html
go tool cover -html=../report/covprofile -o ../report/coverage.html
```

```bash
# ginkgo执行所有测试
~/go/bin/ginkgo ./src
# 生成测试覆盖率，需要进入到代码路径才能生成测试覆盖率报告
~/go/bin/ginkgo ./src -cover -coverprofile=./PACKAGE.coverprofile
# 执行指定的It，通过It第一个字符串的正则匹配
~/go/bin/ginkgo -focus="merge_sort" ./src
# 跳过指定的It，通过It的第一个字符串的正则匹配
~/go/bin/ginkgo -skip="merge_sort" ./src
```
