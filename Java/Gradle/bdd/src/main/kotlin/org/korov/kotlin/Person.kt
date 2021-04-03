package org.korov.kotlin

/**
 * age的默认值是6
 */
class Person(name: String = "demo", age: Int = 6) {
    // val声明属性只有getter方法没有setter方法
    val name: String = name

    // var声明的属性既有getter方法也有setter方法
    var age: Int = age

    val isChildren: Boolean
        get() = age < 18
}

fun main() {
    var person = Person("kotlin")
    println(person.name)
    println(person.age)
    println(person.isChildren)

    person.age = 19
    println(person.age)
    println(person.isChildren)
}