package org.korov.kotlin

fun test(value: String): String {
    return "hello $value"
}

// 表达式函数体
fun test1(value: String): String = "hello $value"

// 不用显示声明返回类型
fun test2(value: String) = "hello ${if (value.equals("kotlin")) "$value plus" else value}"

fun main() {
    val value = "kotlin"
    var result = test(value)
    println(result)

    result = test1(value)
    println(result)

    result = test2(value)
    println(result)

    var char = value.lastChar();
    println(char)
}

fun sayHello (name:String) = "hello $name"

fun sayHelloTest() {
    val result = sayHello(name = "zhangsan")
}

fun String.lastChar(): Char = this.get(length - 1)