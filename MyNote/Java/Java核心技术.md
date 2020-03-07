# Java核心技术

## 1.Java的基本程序设计结构

使用javac编译文件并运行：

```java
public class HelloWorld {
    public static void main(String[] args) {
		if (args == null || args.length == 0) {
            System.out.println("Hello World!");
        } else {
            System.out.printf("Hello %s", args[0]);
        }
    }
}
```

![1566312846478](picture\1566312846478.png)

### 1.1数据类型

#### 1.1.1整形

| 类型  | 存储需求 | 取值范围                                  |
| ----- | -------- | ----------------------------------------- |
| int   | 4字节    | -2147483648   ~2147483647（正好超过20亿） |
| short | 2字节    | -32768~32767                              |
| long  | 8字节    | -9223372036854775808~9223372036854775807  |
| byte  | 1字节    | -128~127                                  |

**<font color="#ff0000">Java7开始数字可以加下划线2_000等同于2000，java会自动去掉下划线</font>**

#### 1.1.2浮点类型

| 类型   | 存储需求 | 取值范围 |
| ------ | -------- | -------- |
| float  | 4字节    |          |
| double | 8字节    |          |

三个特殊浮点数：

正无穷大：Double.***POSITIVE_INFINITY***

负无穷大：Double.***NEGATIVE_INFINITY***

NaN：Double.***NaN***

<u>**如果计算中不允许有任何舍入误差应该使用BigInteger(任意精度的整数运算)BigDecimal(任意精度的浮点数运算)**</u>

#### 1.1.3char类型

单引号括起来的字面值，

特殊字符转移序列：

\b  退格

\t  制表

\n  换行

\r  回车

\" 双引号

\' 单引号

\\ 反斜杠

#### 1.1.4final

关键字final表示这个变量只能被赋值一次，一旦被赋值之后，就不能再更改了。全量一般使用全大写。

static final表示类常量，可以在一个类中的多个方法中使用

java中的计算方式是先计算再截断，但是可以使用strictfp修饰方法，计算时就会先截断再计算

#### 1.1.5位运算符（一点都不熟悉）

&(and)  |(or) ^(xor) ~(not)

\>>和<<运算符将位模式左移或右移，>>>运算符会用0填充高位

#### 1.1.6运算符优先级

| **运算符**                          | **结合性** |
| ----------------------------------- | ---------- |
| [ ] . ( ) (方法调用)                | 从左向右   |
| ! ~ ++ -- +(一元运算)   -(一元运算) | 从右向左   |
| * / %                               | 从左向右   |
| + -                                 | 从左向右   |
| << >>   >>>                         | 从左向右   |
| < <= > >=   instanceof              | 从左向右   |
| == !=                               | 从左向右   |
| &                                   | 从左向右   |
| ^                                   | 从左向右   |
| \|                                  | 从左向右   |
| &&                                  | 从左向右   |
| \|\|                                | 从左向右   |
| ?:                                  | 从右向左   |
| =                                   | 从右向左   |

#### 1.1.7字符串

字符串是不可以更改的，创建了之后就会一直保持原样，每次的修改都只是创建了一个新的字符串然后重新引用。每次修改都会创建新的字符串，效率低，但是字符串放在了公共的存储池中，所有地方共享，以此实现高效率。

不可以使用==判断字符串是否相等，原因：只有字符串常量是共享的，+或substring等操作产生的结果不共享。

#### 1.1.8码点与代码单元

Java中char是一个用UTF-16编码表示Unicode码点的代码单元。常用Unicode字符用一个代码单元就可以表示，而辅助字符需要一对代码单元表示。

length方法返回采用UTF-16编码表示的给定字符串所需的代码单元数量。

```java
String greeting = "Hello";
int length = greeting.length(); // 5
```

要想得到实际长度，即码点数量，使用

```java
int cpCount = greeting.codePointCount(0, greeting.length()); // 5
```

返回第n位的代码单元

```java
char first = greeting.charAt(0); // H
```

获取第2个码点

```java
int index = greeting.offsetByCodePoints(0, 2); // 2
int cp = greeting.codePointAt(index); // 108
```

#### 1.1.9输入输出

Scanner可以捕获控制台输入，Console可以用于获取密码

格式化输出：printf       （还可以使用String.format()将字符串格式化赋值个String变量）

| 转换符 | 类型                                                         | 举例                      |
| ------ | ------------------------------------------------------------ | ------------------------- |
| d      | 十进制数                                                     | 159                       |
| x      | 十六进制数                                                   | 9f                        |
| o      | 八进制数                                                     | 237                       |
| f      | 定点浮点数                                                   | 15.9                      |
| e      | 指数浮点数                                                   | 1.59e+01                  |
| g      | 通用浮点数                                                   | -                         |
| a      | 十六进制浮点数                                               | 0x1.fccdp3                |
| s      | 字符串                                                       | Hello                     |
| c      | 字符                                                         | H                         |
| b      | 布尔                                                         | True                      |
| h      | 散列码                                                       | 42628b2                   |
| tx或Tx | 日期时间（T强制大写）                                        | 已过时建议使用java.time类 |
| %      | 百分号                                                       | %                         |
| n      | 与平台有关的行分隔符（建议使用System.lineSeparator()与平台无关） | -                         |

| 标志            | 目的                                                         | 举例        |
| --------------- | ------------------------------------------------------------ | ----------- |
| +               | 打印正数和负数的符号                                         | +3333.33    |
| 空格            | 在正数之前添加空格                                           | \| 333.33\| |
| 0               | 数字前面补0                                                  | 003333.33   |
| -               | 左对齐                                                       | \|333.33 \| |
| (               | 将负数括在括号内                                             | (3333.33)   |
| ,               | 添加分组分隔符                                               | 3,333.33    |
| #(对于f格式)    | 包含小数点                                                   | 3,333.      |
| #(对于x或0格式) | 添加前缀0x或0                                                | 0xcafe      |
| $               | 给定被格式化的参数索引。例如，%1$d,%1$x将以十进制和十六进制格式打印第一个参数 | 159 9F      |
| <               | 格式化前面说明的数值。例如，%d%<x以十进制和十六进制打印同一个数值 | 159 9F      |

日期和时间转换符：

| 转换符 | 类型                                         | 举例                         |
| ------ | -------------------------------------------- | ---------------------------- |
| c      | 完整的日期和时间                             | Mon Feb 09 18:05:19 PST 2015 |
| F      | ISO 8601 日期                                | 2015-02-09                   |
| D      | 美国格式的日期                               | 02/09/2015                   |
| T      | 24小时时间                                   | 18:05:19                     |
| r      | 12小时时间                                   |                              |
| R      | 24小时时间没有秒                             |                              |
| Y      | 4位数字的年（前面补0）                       |                              |
| y      | 年的后两位数字（前面补0）                    |                              |
| C      | 年的前两位数字（前面补0）                    |                              |
| B      | 月的完整拼写                                 |                              |
| b或h   | 月的缩写                                     |                              |
| m      | 两位数字的月（前面补0）                      |                              |
| d      | 两位数字的日（前面补0）                      |                              |
| e      | 两位数字的日（前面不补0）                    |                              |
| A      | 星期几的完整拼写                             |                              |
| a      | 星期几的缩写                                 |                              |
| j      | 三位数的年中的日子（前面补0），在001~366之间 |                              |
| H      | 两位数字的小时（前面补0），在0~23之间        |                              |
| k      | 两位数字的小时（前面不补0），在0~23之间      |                              |
| I      | 两位数字的小时（前面补0），在0~12之间        |                              |
| l      | 两位数字的小时（前面不补0），在0~12之间      |                              |
| M      | 两位数字的分钟（前面补0）                    |                              |
| S      | 两位数字的秒（前面补0）                      |                              |
| L      | 三位数字的毫秒（前面补0）                    |                              |
| N      | 九位数字的毫微妙（前面补0）                  |                              |
| p      | 上午或下午的标志                             |                              |
| z      | 从GMT起，RFC822的数字位移                    |                              |
| Z      | 时区                                         |                              |
| s      | 从格林威治时间1970-01-01 00:00:00起的秒数    |                              |
| Q      | 从格林威治时间1970-01-01 00:00:00起的毫秒数  |                              |

```java
System.out.printf("%1$s %2$tB %2$te, %2$tY", "Due date:", new Date());
System.out.printf("%s %tB %<te, %<tY", "Due date:", new Date());
```

这两个语句相同效果：Due date: February 9, 2015

## 1.2对象与类

实现封装的关键在于绝对不能让类中的方法直接地访问其他类的实例域。程序仅通过对象的方法与对象数据进行交互。

### 1.2.1对象

要想使用OOP，一定要清楚对象的三个主要特性：

- 对象的行为--可以对对象施加哪些操作，或对象可以对对象施加哪些方法

- 对象的状态--当施加那些方法时，对象如何响应

- 对象标识--如何辨别具有相同行为与状态的不同对象

每个对象都保存着描述当前特征的信息。这就是对象的状态。对象的状态可能会随着时间而发生改变，但这种改变不会是自发的。对象状态的改变必须通过调用方法实现（若不通过方法改变状态，则封装性被破坏）

### 1.2.2类

创建类，名词就是属性，动词就应该是方法。

类之间的关系：依赖（uses-a），聚合（has-a），继承（is-a）；如果一个类的方法操纵另一个类的对象，则这个类依赖另一个类。应尽可能减少依赖。聚合关系意味着类A的对象包含类B的对象。

凭借经验可知，如果如要返回一个可变数据域的拷贝，就应该使用克隆，（但实际情况中不克隆更好用）

### 1.2.3final

将类中的实例域定义为final后构建对象时必须初始化这样的域。也就是说必须确保在每一个构造器执行之后，这个域的值被设置。并且在之后的操作中不可以改变此域的值

### 1.2.4static

将域定义为static，每个类中只有一个这样的域，而所有的实例共享这个静态域，而每个对象对于所有的实例域却都有自己的一份拷贝。

静态方法是一种不能像对象实施操作的方法。即没有隐式参数this，但是静态方法可以访问自身类中的静态域。或者所需参数都是通过显示参数提供可以使用静态方法。

### 1.2.5方法参数

按值调用表示方法接受者调用提供的值。按引用调用表示方法接收的是调用者提供的变量地址。一个方法可以修改传递引用所对应的变量值，而不能修改传递值所对应的变量值。

### 1.2.6对象构造

如果在构造器中没有显示的给域赋予初值，那么就会被自动地赋为默认值：数值为0，布尔值为false，对象应用为null。

类的初始化过程：加载class文件，堆中开辟空间，变量的默认初始化，变量的显示初始化，变量的静态方法赋值，构造代码块初始化，构造方法初始化。如果是初始化一个子类，则会先初始化父类然后初始化子类，以覆盖的方式。

### 1.2.7初始化块

在一个类的声明中，可以包含多个代码块。只要构造类的兑现个，这些块就会被执行。

```java
class Employee{
    private static int nextId;
    private int id;
    //...
    {
        id=nextId;
        nextId++;
    }
    // 执行构造器之前上面的初始化代码块会被执行
    public Employee(){
        
    }
}
```

调用构造器的具体处理步骤：

所有数据域被初始化为默认值；按照在类声明中出现的次序，依次执行所有域初始化语句和初始化块；如果构造器第一行调用了第二个构造器，则执行第二个构造器主题；执行这个构造器的主体。

### 1.2.8静态导入

```java
import static java.lang.System.out;
public class test {    
    public static void main(String[] args) {
        out.println("hello world");
    }
}
```

省去了System前缀；

可以通过package指定将类放入什么包中。如果没有则放入默认的default package中。若源文件不再相应的包中且没有依赖也可以编译成功，但是无法执行，因为虚拟机找不到类。

### 1.2.9注释

包与概述注释：

为包添加一个注释需要在包目录中添加一个单独的文件：package.html命名的html文件，标记在<body>...</body>之间的所有文本都会被抽取出来；提供一个package-info.java命名的Java文件

```java
/**...*/
```

中的所有内容会被抽取出来。

可以为所有的源文件提供一个概述性的注释。这个注释将被放置在一个名为overview.html的文件中，这个文件位于包含所有源文件的父目录中。标记<body>...</body>之间的所有文本将被抽取出来，当用户从导航栏中选择Overview时，就会显示出这些内容。

可以使用javadoc命令抽取注释

### 1.2.10总结

1. 一定要保证数据私有
2. 一定要对数据初始化
3. 不要在类中使用过多的基本类型（类更容易理解和修改）
4. 不是所有的域都需要独立的域访问器和域更改器
5. 将职责过多的类进行分解
6. 类名和方法名要能够体现他们的职责
7. 优先使用不可变的类（可以更安全的在多个线程间共享其对象）

## 1.3继承

### 1.3.1超类和子类

子类会继承超类的所有属性，但这些属性都是拷贝的值。

子类可以覆盖超类的方法：

```java
public double getSalary(){
    //必须使用super调用超类的方法才能访问父类的私有属性
    //这是因为salary变量仍然存储在超类中，子类中拥有的只是自己定义的
    //域和方法时分开存的
    double baseSalary = super.getSalary();
    return baseSalary + bonus;
}
```

也可以用super调用超类的构造器

### 1.3.2多态和动态绑定

**<u>*一个对象变量，注意是对象变量，可以指示多种实际类型的现象被称为多态。在运行时能够自动选择调用哪个方法的现象称为动态绑定。</u>***

#### 1.3.2.1多态

一个超类对象变量可以引用任何其子类对象。例如Father为超类，Son为子类则以下代码可以实现：

```java
        Father[] fathers = new Father[2];
        Father father = new Father();
        Son son = new Son();
        fathers[0] = father;
        fathers[1] = son;
```

但不能讲一个超类的引用赋给子类变量。

使用的时候可以强转会子类变量：

```java
        son.setAge("12");
        Son son1 = (Son) fathers[1];
        son1.getAge();
        Son son2 = (Son) fathers[0];//异常无法转换
```

可以是使用instanceof判断是否可以转换。例如：fathers[0] instanceof Son // false

#### 1.3.2.2方法调用

1. 编译器查看对象的声明类型和方法名。假设调用x.f(args)，且隐式参数x声明为C类的对象。需要注意的是：有可能存在多个名字为f，但参数类型不一样的方法。例如，可能存在方法f(int)和f(String)。编译器将会一一列举所有C类中名为f的方法和其超类中访问属性为public且名为f的方法（超类私有方法不可访问）。至此编译器已获得所有可能被调用的候选方法。
2. 接下来，编译器将查看调用方法时提供的参数类型。如果在所有名为f的方法中存在一个与提供的参数类型完全匹配，就选择这个方法。这个过程被称为<u>**重载解析**</u>。例如，对于调用x.f("Hello")来说，编译器将会挑选f(String)，而不是其他的。由于允许类型转换（int可以转换成double），所以这个过程可能很复杂。如果编译器没有找到与参数类型匹配的方法，或者发现经过类型转换后有多个方法与之匹配，就会报告一个错误。至此，编译器已获得需要调用的方法名字和参数类型。
3. 如果是private方法、static方法、final方法或者构造器，那么编译器将可以准确地指导应该调用哪个方法，我们将这种调用方式成为静态绑定。与此对应的是，调用的方法依赖于隐式参数的实际类型，并且在运行时实现动态绑定。在我们列举的示例中，编译器采用动态绑定的方式生成一条调用f(String)的指令。
4. 当程序运行，并且采用动态绑定调用方法时，虚拟机一定调用与x所引用对象的实际类型最合适的那个类的方法。假设x的实际类型是D，它是C类的子类。如果类定义了方法f(String)，就直接调用它；否则，将在D类的超类中寻找f(String)，以此类推。

每次调用方法都要进行搜索，时间开销相当大。因此，虚拟机预先为每个类创建了一个方发表，其中列出了所有方法的签名和实际调用的方法。这样一来，在真正调用方法的时候，虚拟机仅查找这个表就行了。

### 1.3.3阻止继承final

如果类被final修饰，则其不可以扩展子类，类被声明为final之后，类中的方法自动成为final，域不会成为final。如果类的方法被final修饰，则此方法不可以被子类覆盖。

### 1.3.3抽象类

抽象类不可以被实例化。

包含一个或多个抽象方法的类必须被声明为抽象类。抽象方法充当着占位的角色，它们的具体实现在子类中。扩展抽象类可以有两种选择。一种是在抽象类中定义部分抽象类方法或不定义抽象类方法，这样就必须将子类也标记为抽象类；另一种是定义全部的抽象方法，这样一来，子类就不是抽象的了。

可以定义一个抽象类的对象变量，但它只能引用非抽象子类的对象。例如

```java
Person p = new Student("Vince", "Economics");
```

这里p是一个抽象类Person的变量，Person引用了一个非抽象子类Student的实例。

如果在Person中定义了抽象方法getDescription，那么上面代码中定义的p可以调用getDescription方法。

### 1.3.4受保护访问

可以使用protected声明方法或域，这些方法可以被子类访问，也可以被本包中的类访问。其他类不可以访问。

### 1.3.5相等测试与继承

java语言规范要求equals方法具有下面的特性：

1. 自反性：对于任何非空引用x，x.equals(x)应该返回true。

2. 对称性：对于任何引用x和y，当且仅当y.equals(x)返回true，x.equals(y)也应该返回true。

3. 传递性：对于任何引用x、y和z，如果x.equals(y)返回true，y.equals(z)返回true，x.equals(z)也应该返回true。

4. 一致性：如果x和y引用的对象没有发生变化，反复调用x.equals(y)应该返回同样的结果。

5. 对于任意非空引用x，x.equals(null)应该返回false。

   

### 1.3.6hashCode方法

散列码是由对象导出的一个整型值。hashCode定义在Object类中，因此每个对象都有一个默认的散列码，其值为对象的存储地址。

如果重新定义equals方法，就必须重新定义hashCode方法，并合理地组合实例域的散列码。

```java
public int hashCode(){
    return Objects.hash(name, salary, hireDay);
}
```

Equals与hashCode必须一致：若果x.equals(y)返回true，那么x.hashCode()就必须与y.hashCode()具有相同的值。

### 1.3.7泛型数组列表

对于ArrayList，如果调用add且内部数组已经满了，数组列表就自动地创建一个更大的数组，并将所有的对象从较小的数组中拷贝到较大的数组中。

如果已经清楚或能够估计出数据可能存储的元素数量，就可以在填充数组之前调用ensureCapacity方法：staff.ensureCapacity(100)，一旦能够确认数组列表的大小不再发生变化，就可以用trimToSize方法。这个方法将存储区域的大小调整为当前元素数量所需的存储空间数目。垃圾回收器将回收多余的存储空间。

### 1.3.8对象包装器与自动装箱

所有的基本类型都有一个与之对应的类。这些类称为包装器。对象包装器是不可变得，一旦构造了包装器，就不允许更改包装在其中的值。同时，对象包装器类还是final，因此不能定义他们的子类。

### 1.3.9枚举

比较两个枚举类型的值时，永远不要调用equals，直接使用==就可以了。

```java
//枚举类
public enum Size {
    SMALL("S"), MEDIUM("M"), LARGE("L"), EXTRA_LARGE("XL");
    private String abbreviation;

    private Size(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}
//测试类
public class MyTest {
    @Test
    public void test() {
        Locale locale = new Locale("zh", "CN");
        String value = "small".toUpperCase(locale);
        Size size = Enum.valueOf(Size.class, value);
        System.out.println("abbreviation: " + size.getAbbreviation());
    }
}
```

### 1.3.10反射

能够分析类能力的程序称为反射。

获取Class的三种方法

```java
        TestEntity entity = new TestEntity();
        Class class1 = entity.getClass();

        Class class2 = TestEntity.class;

        Class class3 = Class.forName("java.util.Random");
        // 通过构造器创建一个实例
        Object object = class3.getDeclaredConstructor(String.class, String.class).newInstance("value1", "value2");
```

Class类中的getFields、getMethods和getConstructors方法将分别返回类提供的public域、方法和构造器数组，其中包括超类的共有成员。Class类的getDeclareFields、getDeclareMethods和getDeclaredConstructors方法将分别返回类中声明的全部域、方法和构造器，其中包括私有和受保护成员，但不包括超类成员。

设置类中域的值

```java
Field f = class1.getDeclaredField("userName");
f.setAccessible(true);
Object v = f.get(entity);
f.set(entity,"my name");
```

### 1.3.11使用反射编写泛型数组代码

```java
public Object goodCopyOf(Object a, int newLength) {
        Class class1 = a.getClass();
        if (!class1.isArray()) {
            return new Object();
        }
        Class componentType = class1.getComponentType();
        int length = Array.getLength(a);
        Object newArray = Array.newInstance(componentType, newLength);
        System.arraycopy(a, 0, newArray, 0, Math.min(length, newLength));
        return newArray;
    }
```

### 1.3.12调用任意方法

使用Method的invoke方法

```java
Method getUserName = class1.getDeclaredMethod("getUserName");
        // String.class是参数的class
        Method setUserName = class1.getDeclaredMethod("setUserName", String.class);
        setUserName.invoke(entity, "your name");
        String value = (String) getUserName.invoke(entity);
```

## 1.4接口、lambda表达式域内部类

- 接口：这种技术主要用来描述类具有什么功能，而并不给出每个功能的具体实现，一个类可以实现一个或多个接口，并在需要接口的地方，随时使用实现了相应接口的对象
- lambda表达式：一种表示可以在将来某个时间点执行的代码块的简洁方法。
- 内部类：内部类定义在另外一个类的内部，其中的方法可以访问他们的外部类的域。
- 代理（proxy）：一种实现任意接口的对象。

### 1.4.1接口

接口是对类的一组需求描述，这些类要遵从接口描述的统一格式进行定义。

接口中的所有方法自动属于public，因此在接口中声明方法时，不用提供关键字public。

接口中的附加要求：在调用x.compareTo(y)的时候，这个compareTo方法必须确实比较两个对象的内容，并返回比较的结果。当x小于y时，放回一个负数，当x等于y时，返回0，否则返回一个正数。

**接口中可以定义常量（public static final），但是接口绝不能含有实例域。**

### 1.4.2接口的特性

接口不可以被实例化，但可以引用实现了接口的类对象，接口可以使用extends扩展另外一个接口。

Java SE8中，允许在接口中增加静态方法。

可以为接口提供一个默认实现。必须用default修饰符标记这样一个方法。

```java
public interface Test {
    // 实现类中可以不用实现此方法而直接调用
    default void sayHello() {
        System.out.println("Hello World!");
    }
}
```

默认方法可能会有冲突，解决方法：

1. 超类优先
2. 接口冲突，如果一个超接口提供了一个默认方法，另一个接口提供了一个同名而且参数类型相同的方法，必须覆盖这个方法来解决冲突。

### 1.4.3lambda表达式

lambda表达式是一个可传递的代码块，可以在以后执行一次或多次。

lambda形式：参数，箭头（->），以及一个表达式

函数式接口：对于只有一个抽象方法的接口，需要这种接口的对象时，就可以提供一个lambda表达式。

```java
Arrays.sort(words, (first, second) -> first.length() - second.length());
```

lambda表达式可以转换为接口：

```java
Timer timer1 = new Timer(1000, event->{
            log.info("time to to");
        });
```

### 1.4.4方法引用

用::操作符分隔方法名与对象或类名：

- object::instanceMethod
- Class::staticMethod
- Class::instanceMethod

在前两种情况中农，方法引用等价于提供方法参数的lambda表达式，System.out::println等价于x->System.out.println(x)，Math::pow等价于(x,y)->Math.pow(x,y)。对于第三种情况，第一个参数会成为方法的目标。例如，String::compareToIgnoreCase等价于(x,y)->x.compareToIgnoreCase(y)。

### 1.4.5构造器引用

例如

```java
ArrayList<String> names = ...;
Stream<Person> stream = names.stream().map(Person::new);
List<Person> people = stream.collect(Collectors.toList());
Object[] people = stream.toArray();
Person[] people = stream.toArray(Person[]::new);
```

map方法会为各个列表元素调用Person(String)构造器。

### 1.4.6lambda变量作用域

lambda表达式中，只能引用值不会改变的变量。如果在lambda表达式中引用变量，而变量的值可能在外部改变这是不合法的。

规定：lambda表达式中捕获的变量必须实际上是最终变量。

lambda表达式中的this是指创建这个lambda表达式的方法的this参数。例如

```java
public class Application(){
    public void init(){
        ActionListener listener = event -> {
            Ssytem.out.println(this.toString());
        }
    }   
}
```

表达式this.toString()会调用Application对象的toString方法。

### 1.4.7处理lambda表达式

使用lambda表达式的重点是延迟执行。

将lambda表达式传递给接口（Runnable）

```java
public class HandleLambda {
    public static void main(String[] args) {
        repeat(10, () -> System.out.println("Hello World!"));
        repeat(10, i -> System.out.printf("Countdown: %s\n", 9 - i));
    }

    /**
     * 传递lambda给runnable接口
     * @param n
     * @param action
     */
    public static void repeat(int n, Runnable action) {
        for (int i = 0; i < n; i++) action.run();
    }

    /**
     * 传递给自定义接口
     * @param n
     * @param action
     */
    public static void repeat(int n, IntComsumer action) {
        for (int i = 0; i < n; i++) action.accept(i);
    }
}

//接口
public interface IntComsumer {
    void accept(int value);
}
```

### 1.4.8内部类

内部类是定义在另外一个类中的类，使用内部类有三种原因：

1. 内部类方法可以访问该类定义所在的作用域中的数据，包括私有的数据
2. 内部类可以对同一个包中的其他类隐藏起来
3. 当想要定义一个回调函数（某个特定时间发生时应该采取的行动）且不想编写大量代码时，使用匿名内部类比较边界

### 1.4.9局部类

```java
public void start(){
        class TimePrinter implements ActionListener{
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("At the tone, the time is "+new Date());
                if(beep) Toolkit.getDefaultToolkit().beep();
            }
        }
        ActionListener listener = new TimePrinter();
        Timer t = new Timer(1000,listener);
        t.start();
    }
```

局部类不能用public或private访问说明符进行声明。它的作用域被限定在声明这个局部类的块中。局部类有一个优势，即对外部世界可以完全地隐藏起来。除了start方法之外，没有任何方法指导TimePrinter类的存在。

### 1.4.10匿名内部类

```java
public void start(int interval, boolean beep) {
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println("At the tone, the time is " + new Date());
                if (beep) Toolkit.getDefaultToolkit().beep();
            }
        };
        Timer t = new Timer(1000, listener);
        t.start();
    }

//推荐使用lambda表达式代替内部类
public void start(int interval, boolean beep) {
        Timer t = new Timer(1000, actionEvent -> {
            System.out.println("At the tone, the time is " + new Date());
            if (beep) Toolkit.getDefaultToolkit().beep();
        });
        t.start();
    }
```

含义：创建一个实现ActionListener接口的类的新对象

### 1.4.11静态内部类

有时候，使用内部类只是为了把一个类隐藏在另一类的内部，并不需要引用外围类对象，可以将内部类声明为static。每个外部类的实例会共享一个内部类

```java
public class VoidTest {
    
    static class InnerTest{
        public static void innerTest(){
            System.out.println("static inner class");
        }
    }
}
```

## 1.5代理

利用代理可以在运行时创建一个实现了一组给定接口的新类。这种功能只有在编译时无法确定需要实现哪个接口时才有必要使用。

代理类是在程序运行过程中创建的，一旦被创建，就变成了常规类，所有的代理类都扩展与Proxy类，一个代理类只有一个实例域---调用处理器，它定义在Proxy的超类中。为了履行代理对象的职责，所需要的任何附加数据都必须存储在调用处理器中。

```java
public class ProxyTest {
    public static void main(String[] args) {
        Object[] elements = new Object[1000];
        for (int i = 0; i < elements.length; i++) {
            Integer value = i + 1;
            InvocationHandler handler = new TraceHandler(value);
            // 在运行时定义类，这个类实现了Comparable接口，但他的comparTo方法调用了代理对象处理器的invoke方法
            Object proxy = Proxy.newProxyInstance(null, new Class[]{Comparable.class}, handler);
            elements[i] = proxy;
        }
        Integer key = new Random().nextInt(elements.length) + 1;
        // 二分查找会使用compareTo方法进行对比
        int result = Arrays.binarySearch(elements, key);
        if (result >= 0) System.out.println(elements[result]);

        // proxy对象实例中的compareTo方法为invoke方法
        Integer value = 4;
        Comparable comparable = (Comparable) elements[3];
        comparable.compareTo(value);
    }

    static class TraceHandler implements InvocationHandler {
        private Object target;

        public TraceHandler(Object value) {
            target = value;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
            System.out.printf("%s.%s(", target, method.getName());
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    System.out.printf("%s", args[i]);
                    if (i < args.length - 1) System.out.printf(",");
                }
            }
            System.out.printf(")\n");
            return method.invoke(target, args);
        }
    }
}
```

## 1.6异常、断言和日志

### 1.6.1异常分类

在java程序设计语言中，异常对象都是派生于Throwable类的一个实例。

Throwable后又分为两层Error和Exception。

Error：描述了java运行时系统的内部错误和资源耗尽错误。应用程序不应该抛出这种类型的对象。如果出现了这样的内部错误，除了通告给用户，并尽力使程序安全地终止之外，再也无能为力了。

Exception：这个层次结构又分为两个分支：一个分支派生于RuntimeException（由程序错误导致的异常）；另一个分支包含其他异常（程序本身没有问题，但由于I/O错误这类问题导致的异常）。

派生于RuntimeException的异常包含下面几种情况：

1. 错误的类型转换
2. 数组访问越界
3. 访问null指针

不是派生于RuntimeException的异常包含：

1. 试图在文件尾部后面读取数据
2. 试图打开一个不存在的文件
3. 试图根据给定的字符串查找Class对象，而这个字符串表示的类并不存在

Java语言规范将派生于Error类或RuntimeException类的所有异常称为**非受查**异常

，其他的异常称为**受查异常**

**<u>一个方法必须声明所有可能抛出的受查异常，而非受查异常要么不可控制，要么就应该避免发生</u>**

### 1.6.2创建异常类

若遇到标准异常类没有能够充分地描述清楚问题，我们就要创建自己的异常类。我们需要定义一个派生于Exception的类，或者派生于Exception子类的类。

```java
//异常类
public class FileFormatException extends IOException {
    public FileFormatException() {
    }

    public FileFormatException(String grip) {
        super(grip);
    }
}

//抛出异常
public void test3() throws FileFormatException {
        String value = "test";
        if (value.length() == 5) throw new FileFormatException("length is 5");
    }
```

### 1.6.3捕获异常

如果编写一个覆盖超类的方法，而这个超类的方法又没有抛出异常，那么这个子类的方法就必须捕获方法代码中出现的每一个受检异常。不允许在子类的throws说明符中出现超过超类方法所列出的异常类范围。

### 1.6.4再次抛出异常与异常链

将原始异常设置为新异常的原因

```java
try {
            list.get(4);
        } catch (ArrayIndexOutOfBoundsException e) {
            Throwable se = new FileFormatException("get Exception");
            se.initCause(e);
            throw se;
        }
```

当捕获异常时可以使用下面语句重新获得原始异常：

Throwable e = se.getCause();

强烈建议使用这种包装技术，这样可以让用户抛出子系统中的高级异常，而不会丢失原始异常的细节。

### 1.6.5分析堆栈轨迹元素

StackTraceElement类含有能够获得文件名和当前执行的代码行号的方法，同时，含有能够获得类名和方法名的方法。

```java
public class StackTraceTest {
    //factorial阶乘
    public static int  factorial(int n) {
        System.out.println("factorial("+ n +"):");
        Throwable t = new Throwable();
        //使用getStackTrace方法 ，得到StackTraceElement对象一个数组，可以进行分析
        StackTraceElement[] frames  = t.getStackTrace();
        for (StackTraceElement f : frames) {
            System.out.println(f);
        }
        int r;
        if (n <= 1) {
            r = 1;
        }else {
            r=n*factorial(n-1);
        }
        System.out.println("return"+r);
        return r;
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter n :");
        int n = in.nextInt();
        factorial(n);
    }
}
```

### 1.6.6断言

断言失败是致命的、不可恢复的错误，断言检查只用于开发阿和测试阶段。

在默认情况下断言是被禁用的。可以在运行程序时启动

java -enableassertions MyApp

在某个类后某个包中使用断言，使用-da禁用断言

java -ea:MyClass -ea:com.mycompany.mylib... -ea:... -da:MyClass MyApp

```java
int value = 4;
/**
* 出现错误   java.lang.AssertionError: 判断错误
*/
assert value == 3 : "判断错误";
```

### 1.6.7日志

```java
public void test() {
        /**
         * 打印一个info日志
         * 9月 09, 2019 1:12:23 上午 com.korov.springboot.BaseTest.VoidTest test
         * 信息: File->Open menu item selected
         */
        Logger.getGlobal().info("File->Open menu item selected");
        // 在适当的地方可以关闭全局日志
        Logger.getGlobal().setLevel(Level.OFF);
    }
```

高级日志

```java
public class VoidTest {
    // 对com.korov.springboot.BaseTest设置了日志级别，其子记录器也会继承这个级别
    private static final Logger logger = Logger.getLogger("com.korov.springboot.BaseTest");

    @Test
    public void test() {
        // 设置只打印FINE级别以上日志
        logger.setLevel(Level.FINE);
        logger.info("企业及日志 info");
        // 此日志不会打印
        logger.finer("企业级日志 finer");
}
```

推荐是用logback或者log4j2

### 1.6.7调试技巧

1. 使用print或者上面的Logger打印消息

2. 在类中添加main方法调用

3. Junit

4. 日志代理：若Random类的nextDouble方法出现了问题，可以按照下面方式调试，当调用nextDouble方法时，就会产生一个日志消息，也可以生成堆栈轨迹

5. ```java
   Random generator = new Random(){
       public double nextDouble(){
           double result = super.nextDouble();
          Logger.getGlobal().info("nextDouble:" + result);
           return result;
       }
   };
   ```

6. 打印异常的堆栈信息

   ```java
   try{
               // TODO
           }catch (Exception e){
               // 直接打印堆栈
               e.printStackTrace();
               throw e;
           }
   
           int value =4;
           // 只要在代码中任何位置插入下面这条语句就可以获得堆栈轨迹
           Thread.dumpStack();
           if (value==4) throw new Exception("value is 4");
   ```

   

7. 使用jconsole

8. 使用jmap


## 1.7泛型程序设计

### 1.7.1类型变量的限定

public static <T extends Comparable> T min (T[] a)...

说明此方法只能被实现了comparable接口的类调用。

一个类型变量或通配符可以有多个限定：T extends Comparable & Serializable。使用&分隔。

### 1.7.2java泛型转换的事实

1. 虚拟机中没有泛型，只有普通的类和方法
2. 所有的类型参数都用他们的限定类型替换
3. 桥方法被合成来保持多态
4. 为保持类型安全性，必要时插入强制类型转换

### 1.7.3泛型代码与虚拟机

https://www.cnblogs.com/ixenos/p/5638585.html

### 1.7.4泛型约束与局限性

https://www.cnblogs.com/ixenos/p/5645851.html

### 1.7.5通配符类型

#### 1.7.5.1通配符概念

通配符类型中，允许类型参数变化。

**? extends X**

子类型限定，表示泛型的类型参数不是固定的，而是X及其子类型。

如果存在Operate<ParentClass> operate1 = new Operate<SonClass>(new SonClass())，那么这行代码会报错，编译器提示类型不兼容，因为左边不是右边的基类。此时如果有这样一个方法：

public static void method(Operate<ParentClass> operate) {}，

就无法将 new Operate<SonClass>(new SonClass()) 传递给这个方法。解决办法就是使用子类型限定定义方法参数：

public static void method(Operate<? extends ParentClass> operate) {}，

就可以将 new Operate<SonClass>(new SonClass()) 传递给这个方法。

子类型限定的副作用是不能传递null以外的类型。

Operate<? extends ParentClass>的方法可以想象成下面这个样子（实际上不能这样写代码）：

public ? extends ParentClass get() {

 return item;

}

public void set(? extends ParentClass item) {

 this.item = item;

}

此时get方法可以正常调用，因为返回的item肯定是ParentClass或者它的子类型。但是set方法就不能传递null以外的类型了，因为编译器只知道需要ParentClass或者它的子类型，但是不知道具体是哪个类，所以只能调用set(null)。如下：

public static void method(Operate<? extends ParentClass> operate) {

 operate.get();

 operate.set(null);

 operate.set(new ParentClass());//报错

 operate.set(new SonClass());//报错

}

**? super X**

超类型限定，表示泛型的类型参数不是固定的，而是X及其父类型。

Operate<? super SonClass>的方法可以想象成下面这个样子（实际上不能这样写代码）：

```java
public ? super SonClass get() {
 return item;
}

public void set(? super SonClass item) {
 this.item = item;
}
```

1. 类型参数限定为X及其父类型，直至Object类，因为不知道具体是哪个父类型，因此方法返回的类型只能赋给Object。
2. 只能传递null、X及其子类型，因为X及其子类型都是向上转型成X及其父类型。

Operate<? super SonClass> operate3 = new Operate<ParentClass>(new ParentClass())是成立的，所以可以将new Operate<ParentClass>(new ParentClass())传递给以下方法。

```java
public static void method(Operate<? super SonClass> operate{
 SonClass sonClass = operate.get();//报错
 ParentClass parentClass = operate.get();//报错
 Object object = operate.get();
 operate.set(new ParentClass());//报错
 operate.set(null);
 operate.set(new SonClass());
}
```

**?**

无类型限定，泛型的类型参数没有限定。

1. 只能传递null类型。
2. 方法返回的类型只能赋给Object。

new Operate<SonClass>(new SonClass())、new Operate<ParentClass>(new ParentClass())、new Operate<Object>(new Object())、new Operate<String>(new String())等都可以传递给以下方法。

```java
public static void method(Operate<?> operate) {
 SonClass sonClass = operate.get();//报错
 ParentClass parentClass = operate.get();//报错
 operate.set(new ParentClass());//报错
 Object object = operate.get();
 operate.set(null);
 operate.set(new SonClass());//报错
}
```

有什么作用呢？对于一些不需要实际类型的方法，就显得比泛型方法可读性强，如下。

```java
public static void method(Operate<?> operate) {
 System.out.println(operate.get() == null);
}

public static <T> void method(Operate<T> operate) {
 System.out.println(operate.get() == null);
}
```

## 1.8集合

### 1.8.1Collection接口

在Java类库中，集合类的基本接口是Collection接口。这个接口有两个基本方法：

```java
public interface Collection<E>{
  boolean add(E element);
  Iterator<E> iterator();
  ...
}
```

add方法用于向集合中添加元素。如果添加元素确实改变了集合就返回true，如果结合没有发生变化就返回false。iterator方法用于返回一个实现了Iterator接口的对象。可以使用这个迭代器对象依次访问集合中的元素。

### 1.8.2迭代器

Iterator接口包含4个方法：

```java
public interface Iterator<E>{
    E next();
    boolean hasNext();
    void remove();
    default void forEachRemaining(Consumer<? super E> action)
}
```

使用iterator

```java
Collection<String> c=...;
Iterator<String> iter=c.iterator();
while(iter.hasNext()){
    String element = iter.next();
    // TODO
}

for(String element : c){
    // TODO
}
```

for each循环可以与任何实现了Iterator接口的对象一起工作。Collection接口扩展了Iterator接口，因此对于标准类库中的任何集合都可以使用for each。

在Java SE8中，甚至不用写循环。可以调用forEachRemaining方法并提供一个lambda表达式。将对迭代器的每一个元素调用这个lambda表达式，直到再没有元素为止：iterator.forEachRemaining(element -> // TODO)

应该将Java迭代器认为是位于两个元素之间。当调用next时，迭代器就越过下一个元素，并将返回越过的那个元素的引用。

iterator接口的remove方法将会删除上次调用next方法时返回的元素。必须先调用next然后调用remove方法，连续两次调用remove方法时不允许的。

### 1.8.3Java库中的具体集合

| 集合类型        | 描述                                                 |
| --------------- | ---------------------------------------------------- |
| ArrayList       | 一种可以动态增长和缩减的索引序列                     |
| LinkedList      | 一种可以在任何位置进行高效的插入和删除操作的有序序列 |
| ArrayDeque      | 一种用循环数组实现的双端队列                         |
| HashSet         | 一种没有重复元素的无序集合                           |
| TreeSet         | 一种有序集                                           |
| EnumSet         | 一种包含枚举类型值的集                               |
| LinkedHashSet   | 一种可以记住元素插入次序的集                         |
| PriorityQueue   | 一种允许高效删除最小元素的集合                       |
| HashMap         | 一种存储键/值关联的数据结构                          |
| TreeMap         | 一种键/值有序排列的映射表                            |
| EnumMap         | 一种键/值属于枚举类型的映射表                        |
| LinkedHashMap   | 一种可以记住键值项添加次序的映射表                   |
| WeakHashMap     | 一种其值无用武之地后可以被垃圾回收器回收的映射表     |
| IdentityHashMap | 一种用==,而不是用equals比较键值的映射表              |

### 1.8.4链表

ListIterator是Iterator的子接口，此接口的add方法不返回boolean值，它假定添加操作总会改变链表。另外ListIterator接口有两个方法可以用来反向遍历链表：E previous()和boolean hasPrevious()；

add方法只依赖于迭代器的位置（可以随时使用），而remove方法依赖于迭代器的状态（必须先使用next方法）。

```java
// 对于链表来说此段代码效率极低，因为每次查找元素都要从列表的头部重新开始搜索
for(int i=0; i<list.size(); i++){
    // TODO
}
```

**Vector类的所有方法都是同步的可以在多线程中使用，ArrayList非同步**

### 1.8.5散列表

如果不在意元素的顺序，并想要快速查找想要的数据，可以使用散列表。

在Java中散列表用链表数组实现。每个列表被称为桶。想要查找表中对象的位置，就要先计算它的散列码，然后于桶中的总数取余，所得到的结果就是保存这个元素的桶的索引。

创建散列表的时候指定初始桶数可以很好的提高散列表的性能，通常将桶数设置为元素的75%~150%。最好将桶数设置为一个素数，以防键的聚集。

### 1.8.6树集

TreeSet是一个有序集合，可以以任意顺序将元素插入到集合中。在对集合进行遍历时，每个值将自动按照排序后的顺序呈现。

将一个元素添加到树中要比添加到散列表中慢，但是于检查数组或链表中的重复元素相比还是要快很多。

### 1.8.7优先级队列

优先级队列中的元素可以按照任意的顺序插入，却总是按照排序的顺序进行检索，也就是说无论何时调用remove方法，总会获得当前优先级队列中最小的元素。优先级队列使用堆实现。

### 1.8.8映射

映射用来存放键值对。

Java类库中有为映射提供了两种实现：HashMap和TreeMap。散列映射对键进行散列，树映射用键的整体顺序对元素进行排序，并将其组织成搜索树。散列比较函数只能作用于键。于键关联的值不能进行散列或比较。散列比树稍微快点。

Map的merge方法：好强

default V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V>)

如果key与一个非null的v关联，将函数应用到v和value，将key于结果关联，或者如果结果为null，则删除这个键。否则，将key于value关联。

### 1.8.9链接散列集于映射

LinkedHashSet和LinkedHashMap类用来记住插入元素项的顺序。

### 1.8.10枚举集与映射

EnumSet是一个枚举类型元素集的高效实现。由于枚举集只有有限个实例，所以EnumSet内部用位序列实现。如果对应的值在集中，则相应的位被置为1。

EnumSet类没有公共的构造器。可以使用静态工厂方法构造这个集

```java
public enum Weekday {
    MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY,SUNDAY
}

public static void main(String[] args) {
		//创建一个包含指定所有元素的枚举集元素类型
        EnumSet<Weekday> always = EnumSet.allOf(Weekday.class);
        //System.out.println(always);
        //创建具有指定元素类型的空枚举集
        EnumSet<Weekday> never = EnumSet.noneOf(Weekday.class);
        //System.out.println(never);
        //创建一个最初包含所有元素的枚举集由两个指定端点定义的范围。 返回的集合将包含端点本身
        EnumSet<Weekday> range = EnumSet.range(Weekday.MONDAY, Weekday.FRIDAY);
        //System.out.println(range);
        //创建最初包含指定元素的枚举集。
        EnumSet<Weekday> mwf = EnumSet.of(Weekday.MONDAY, Weekday.WEDNESDAY, Weekday.FRIDAY);
        System.out.println(mwf);
    }
```

EnumMap是一个键类型为枚举类型的映射。它可以直接且高效地用一个值数组实现。在使用时，需要在构造器中指定键的类型

```java
EnumMap<Weekday, Employee> personInCharge = new EnumMap<>(Weekday.class);
```

1.9

# java11新特性

## 1. FlightRecorder

记录Java程序的运行时数据，类似飞机中的黑匣子，对于性能损耗很小，如果使用VisualVM将会占用主机大量性能，推荐使用JFR作为性能记录。然后分析运行时数据。

可以使用`-XX:StartFlightRecording`jvm参数启动，启动之后会输出如下内容。

```log
/usr/lib/jvm/java-11-openjdk/bin/java -XX:StartFlightRecording -javaagent:/home/korov/Desktop/install/idea-IU-193.5662.53/lib/idea_rt.jar=33641:/home/korov/Desktop/install/idea-IU-193.5662.53/bin -Dfile.encoding=UTF-8 -classpath /home/korov/Desktop/temp/javaDemo/target/classes com.demo.JFRTest
Started recording 1. No limit specified, using maxsize=250MB as default.

Use jcmd 25165 JFR.dump name=1 filename=FILEPATH to copy recording data to file.
```

`Started recording 1`代表此记录的编号为1，`using maxsize=250MB`表示记录的内容存在内存中，最多保存250MB。

接下来使用命令将记录导出到文件

```bash
jcmd 25165 JFR.dump name=1 filename=JFRTest.jfr
```

使用命令方式启动JFR

```bash
$ jcmd <pid> JFR.start  #PID为要监控的java进程的id

$ jcmd <pid> JFR.dump filename=recording.jfr

$ jcmd <pid> JFR.stop
```

获取到文件之后可以使用`jfr`命令来解析生成的文件。