需要设置好GOPATH才可以正常执行

```
export GOPATH=项目所在路径

mkdir gobuild
cd gobuild
go build calc

会产生一个calc的可执行文件
./calc add 2 3

执行测试
go test simplemath
```

