package org.korov.kotlin

/**
 * 最简单的枚举类
 */
enum class Color {
    RED, YELLOW, WHITE
}

/**
 * 带属性的枚举类
 */
enum class Color1(val r: Int, val g: Int, val b: Int) {
    RED(255, 0, 0),
    YELLOW(255, 255, 0);

    fun rgb() = (r * 256 + g) * 256 + b
}

fun main() {
    println(Color1.YELLOW.rgb())
    var color = Color.YELLOW
    when (color) {
        Color.WHITE -> println("White")
        Color.RED, Color.YELLOW -> println("not White")
    }
}


