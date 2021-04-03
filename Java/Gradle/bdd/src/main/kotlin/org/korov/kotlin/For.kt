package org.korov.kotlin

fun main() {
    for (i in 1..10) {
        println(i)
    }

    for (i in 10 downTo 1 step 2) {
        println(i)
    }

    for (c in 'A'..'D') {
        println(c)
    }

    val sets = setOf("1","2","#")
    for (set in sets) {
        println(set)
    }

    val maps = mapOf(Pair("key1", "value1"), Pair("key2", "value2"))
    for ((key, value) in maps) {
        println("$key: $value")
    }

    val lists = listOf("a","b")
    for ((index, value) in  lists.withIndex()) {
        println("$index: $value")
    }

    val c = "d"
    if (c in lists) {
        println("in")
    } else if (c !in lists) {
        println("not in")
    }
    println(lists.contains(c))
}