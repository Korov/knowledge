# wrapper

是一个包装器，用`gradle wrapper`创建，其目录结构如下：

```bash
$ tree .
.
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
└── gradlew.bat

2 directories, 4 files
```

将这几个文件复制到相应的gradle项目中，下次运行此项目就会使用此wrapper版本的gradle，配置gradle命令`./gradlew wrapper`

# groovy中的闭包

闭包是一段代码，类似于java中的lambda表达式，在gradle中，我们主要把闭包当参数来使用。

```groovy
// 定义一个闭包
def b1 ={
    println("Hello 闭包")
}
// 定义一个方式，使用闭包作为参数
def method1(Closure closure) {
    closure()
}
// 调用该方法
method1(b1)

// 定义一个闭包带参数
def b2 ={
    v ->
    println("Hello $v")
}
// 定义一个方式，使用闭包作为参数
def method2(Closure closure) {
    closure("带参数的闭包")
}
// 调用该方法
method2(b2)
```



# 自我总结

## 创建一个新的项目

```bash
gradle init
```

## 升级已有项目的gradle wrapper

```bash
./gradlew wrapper --gradle-version=6.2.2 --distribution-type=bin
```

