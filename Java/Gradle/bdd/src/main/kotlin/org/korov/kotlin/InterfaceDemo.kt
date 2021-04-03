package org.korov.kotlin

interface Click {
    fun click()
    fun showOff() = println("i am click interface")
}

interface Focus {
    fun setFocus(boolean: Boolean) = println("i ${if (boolean) "got" else "lost"} focus.")
    fun showOff() = println("i am focus interface")
}

class Button : Click , Focus{
    override fun click() {
        println("clicked")
    }

    /**
     * 两个接口中有相同的方法必须重写
     */
    override fun showOff() {
        super<Click>.showOff()
        super<Focus>.showOff()
        println("i am Button")
    }
}

fun main() {
    val interfaceDemo = Button()
    interfaceDemo.click()
    interfaceDemo.showOff()
}