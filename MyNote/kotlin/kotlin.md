# Kotlin

Kotlin是一种 **_静态类型_** 的编程语言。

kotlin类型系统中支持 **函数类型** ，函数式编程的核心概念如下：

- 头等函数：把函数（一小段行为）当作值使用，可以用变量保存它，把它当作参数传递，或者当作其他函数的返回值
- 不可变性：使用不可变对象，确保他们的状态在创建之后不能再变化
- 无副作用：使用的是纯函数。此类函数在输入相同时会产生同样的结果，并且不会修改其他对象的状态，也不会和外面的世界交互


## 基础

内容包括

1. 声明函数，变量，类，枚举以及属性
2. kotlin中的控制结构
3. 智能转换
4. 抛出和处理异常


### 函数、变量和类

**kotlin鼓励你使用不可变的数据而不是可变的数据**

声明变量
```Kotlin
fun main() {
    // 自动推断类型为String
    val value1 = "this is string"

    // 显示的声明变量的类型为String
    val value2: String = "this is another string"

    // var声明的变量可以改变引用，val声明的变量为不可变引用,相当于java中的final
    var value3 = 5

    println(value1)
    println(value2)
    println(value3)

    value3 = 6
    println(value3)
}
```

声明函数
```Kotlin
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
}
```

[TIP]
.语句和表达式
====
在kotlin中，if是表达式，而不是语句。语句和表达式的区别在于，表达式有值，并且能作为另一个表达式的一部分使用；而语句总是包围着他的代码块中的顶层元素，并且没有自己的值。在java中，所有的控制结构都是语句。而在kotlin中，除了循环（for、do和do/while）以外大多数控制结构都是表达式。

====

类
```kotlin
/**
 * age的默认值是6
 */
class Person(name: String = "demo", age: Int = 6) {
    // val声明属性只有getter方法没有setter方法
    val name: String = name

    // var声明的属性既有getter方法也有setter方法
    var age: Int = age

    val isChildren: Boolean
        get() {
            return age < 18
        }
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
```

### 枚举和when

声明枚举类型和使用when来处理枚举类
```kotlin
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
```

### kotlin中常见的循环使用方法
```kotlin
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
```

### kotlin中的异常

kotlin的异常处理和java以及其他许多语言的处理方式相似。一个函数可以正常结束，也可以出现在错误的情况下抛出异常。方法的调用者能捕获打破这个异常并处理它，如果没有被处理，异常会沿着调用栈再次抛出。

声明异常和捕获异常的方式和java中一致，但是不需要在方法后面写上 `throws IOException` 。

## 函数的定义与调用

我们可以在调用函数的时候指定传递的参数
```kotlin
fun sayHello (name:String) = "hello $name"

fun sayHelloTest() {
    val result = sayHello(name = "zhangsan")
}
```

kotlin可以扩展已有类的方法，扩展函数不允许你打破它的封装性，扩展函数不能访问私有的或者受保护的成员。
```kotlin
// 给String类添加了一个方法
fun String.lastChar(): Char = this.get(length - 1)
```
对于自己定义的扩展函数不会自动的在整个项目范围内生效。如果你要使用它，需要进行导入(可以对自定的的函数进行重命名)： `import strings.lastChar as last`

## 类、对象和接口

kotlin中的接口可以包含属性声明，kotlin的声明默认是final和pulic的。此外嵌套的类默认并不是内部类：他们并没有包含对其外部类的隐式引用。

### 接口

kotlin的接口可以包含抽象方法的定义以及非抽象方法的实现，但他们不能包含任何状态。
```kotlin
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
```

#### open、final和abstract修饰符：默认为final

如果你想允许创建一个类的子类，需要使用open修饰符来标示这个类。此外，需要给每一个可以被重写的属性或方法添加open修饰符。如果你重写了一个基类或者接口的成员，重写了的成员同样默认是open的，如果你想改变这一行为，阻止你的类的子类重写你的实现，可以显式的将重写的成员标注为final。

在kotlin中，同java一样，可以将一个类声明为abstract的，这种类不能被实例化。一个抽象类通常包含一些没有实现并且必须在子类重写的抽象成员。抽象成员始终是open的，所以不需要显式的使用open修饰符。


.类中访问修饰符的意义
[width="100%",options="header,footer"]
|====================
|修饰符  |相关成员  |描述  
|final  |不能被重写  |类中的成员默认使用  
|open  |可以被重写  |需要明确的表明  
|abstract  |必须被重写  |只能在抽象类中使用：抽象成员不能有实现  
|override  |重写父类或接口中的成员  |如果没有使用final表明，重写的成员默认是开放的  
|====================

#### 可见性修饰符：默认为public

可见性修饰符帮助控制对代码库中声明的访问。通过限制类中实现细节的可见性，可以确保在修改他们时避免破坏依赖这个类的代码的风险。


.可见性修饰符
[width="100%",options="header,footer"]
|====================
|修饰符  |类成员  |顶层声明 
|public(默认)  |所有地方可见  |所有地方可见
|internal  |模块中可见  |模块中可见
|protected  |子类中可见  |- 
|private  |类中可见  |文件中可见
|====================






































