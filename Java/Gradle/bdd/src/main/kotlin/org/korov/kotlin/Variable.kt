package org.korov.kotlin


fun main() {
    // 自动推断类型为String
    val value1 = "this is string"

    // 显示的声明变量的类型为String
    val value2: String = "this is another string"

    // var声明的变量可以改变引用，val声明的变量为不可变引用
    var value3 = 5

    println(value1)
    println(value2)
    println(value3)

    value3 = 6
    println(value3)
}




