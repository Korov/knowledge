# Java

## HashCode的作用

hashcode是根据对象内容生成的一串编码，这串编码的可以辅助判断对象是否相等。

## Java基类Object类

所有类的父类，其中方法有equal，hashcode，toString ,clone，wait和notify，notifyAll。

## java中四种修饰符的限制范围

public：本类，本包，不同包子类，不同包非子类

protected：本类，本包，不同包子类

default：本类，本包

private：本类

## final和static关键字的作用

final修饰类中的变量，变量的值不可以改变，并且必须在构造函数结束前完成初始化。final修饰的方法可以被继承不可以被重写。final修饰的类不可以被继承。

static修饰的方法可以不通过本类的对象即可调用，static修饰的变量为全局变量，所有的类共享该变量。只有内部类才可以用static修饰。

## java 中操作字符串都有哪些类？它们之间有什么区别？

- String : final修饰，String类的方法都是返回new String。即对String对象的任何改变都不影响到原对象，对字符串的修改操作都会生成新的对象。
- StringBuffer : 对字符串的操作的方法都加了synchronized，保证线程安全。
- StringBuilder : 不保证线程安全，在方法体内需要进行字符串的修改操作，可以new StringBuilder对象，调用StringBuilder对象的append、replace、delete等方法修改字符串。

## 抽象类和普通类有那些区别？

- 抽象类不能被实例化
- 抽象类可以没有抽象方法，有抽象方法的类必须被声明为抽象类，抽象方法只需要声明，无需实现
- 抽象类的子类必须实现抽象类中的抽象方法，否则这个子类也是抽象类
- 抽象方法不能被声明为静态，不能用private，static和final修饰

## 抽象类和接口的区别

1. 抽象类和接口都不能被实例化
2. 抽象类被继承，接口被实现，单继承多实现
3. 接口可以声明方法和定义 默认方法 default-method和静态方法 static-method的实现，而抽象类中可以声明方法也可以实现方法
4. 抽象类中的抽象方法必须由子类全部实现，否则这个子类也是抽象类。接口中的方法必须由实现类全部实现，否则这个类就是一个抽象类
5. 接口是设计的结果，抽象类是重构的结果

## java 中 IO 流分为几种

字节流和字符流，分别由四个抽象类来表示。字节流的输入输入：InputStream，OutputStream。字符流的输入输出：Reader，Writer。

读取文本的时候使用字符流，可以直接读取，用字节流的话还需要转换。其他文件的读取使用字节流。

## BIO、NIO、AIO 有什么区别？

BIO：Block IO同步阻塞式IO，并发处理能力低。线程发起IO请求，不管内核是否准备好IO操作，从发起请求，线程一直阻塞，直到操作完成

NIO：Non IO同步非阻塞IO，客户端和服务端通过Channel（通道）通讯，实现了多路复用。线程发起IO请求，立即返回；内核在做好IO操作的准备后，通过调用注册的回调函数通知线程做IO操作，线程开始阻塞，直到操作完成。

AIO：Asynchronous IO异步非阻塞IO。线程发起IO请求，立即返回；内存做好IO操作的准备之后，做IO操作，直到操作完成或者失败，通过调用注册的回调函数通知线程做的IO操作完成或失败。

- BIO是一个连接一个线程。
- NIO是一个请求一个线程。
- AIO是一个有效请求一个线程。

## java容器有哪些

Collection和Map两个

```
|Collection
 |　　├List
 |　　│-├LinkedList
 |　　│-├ArrayList
 |　　│-└Vector
 |　　│　└Stack
 |　　├Set
 |　　│├HashSet
 |　　│├TreeSet
 |　　│└LinkedSet
 |
 |Map
 　├Hashtable
 　├HashMap
 　└WeakHashMap
```

**同步容器**：Vector，Stack，HashTable，Collections.synchronized方法生成

**并发容器**：

- ConcurrentHashMap：线程安全的HashMap实现
- CopyOnWriteArrayList：线程安全且在读操作时无锁的ArrayList
- CopyOnWriteArraySet：基于CopyOnWriteArrayList，不添加重复元素
- ArrayBlockingQueue：基于数组，先进先出，线程安全，可实现制定时间的阻塞读写，并且数量可以限制
- LinkedBlockingQueue：基于链表实现，读写各用一把锁，在高并发读写操作都多的情况下性能优于ArrayBlockingQueue。

## HashMap和HashTable，ConcurrentHashMap有什么区别

**线程安全性不同**：Hashtable是线程安全的，HashMap不是线程安全的

**继承的父类不同**：HashMap继承AbstractMap类，Hashtable继承Dictionary类，但是他们都实现了Map、Cloneable和Serializable接口。

**对外提供的接口不同**：Hashtable比HashMap多提供了elments()和contains()两个方法，elments() 方法继承自Hashtable的父类Dictionnary。elements() 方法用于返回此Hashtable中的value的枚举。contains()方法判断该Hashtable是否包含传入的value。它的作用与containsValue()一致。事实上，contansValue() 就只是调用了一下contains() 方法。

**对null key和null value的支持不同**：Hashtable既不支持Null key也不支持Null value。HashMap可以有一个null key，多个null value。

**遍历方式不同**：Hashtable、HashMap都使用了 Iterator。而由于历史原因，Hashtable还使用了Enumeration的方式 。

**初始容量和每次扩充大小不同**：Hashtable默认的初始大小是11,之后每次扩充容量变为原来的2n+1，。HashMap默认初始大小为16,每次扩充容量变为原来的2倍。创建时，如果给定了容量初始值，那么Hashtable会直接使用你给定的大小，而HashMap会将其扩充为2的幂次方大小。

**计算hash值的方法不同**：Hashtable直接使用对象的hashCode，HashMap会重新根据hashcode计算hash值。

存储结构中ConcurrentHashMap比HashMap多出了一个类Segment，而Segment是一个可重入锁。
ConcurrentHashMap是使用了锁分段技术来保证线程安全的。
锁分段技术：首先将数据分成一段一段的存储，然后给每一段数据配一把锁，当一个线程占用锁访问其中一个段数据的时候，其他段的数据也能被其他线程访问。
ConcurrentHashMap提供了与Hashtable和SynchronizedMap不同的锁机制。Hashtable中采用的锁机制是一次锁住整个hash表，从而在同一时刻只能由一个线程对其进行操作；而ConcurrentHashMap中则是一次锁住一个桶。
ConcurrentHashMap默认将hash表分为16个桶，诸如get、put、remove等常用操作只锁住当前需要用到的桶。这样，原来只能一个线程进入，现在却能同时有16个写线程执行，并发性能的提升是显而易见的。

## ConcurrentHashMap jdk1.8

采用Node+CAS+Synchronized来保证并发

假设table已经初始化完成，put操作采用CAS+synchronized实现并发插入或更新操作：当bucket为空时，使用CAS操作，将Node放入对应的bucket中。当出现hash冲突，则采用synchronized关键字。倘若当前hash对应的节点时链表的表头，遍历链表，更新对应的node或者在链表的末尾添加node节点；若当前节点为红黑树，在树上更新或新增节点。若当前map正在扩容`f.hash == MOVED`，则跟其他线程一起进行扩容。

### 红黑树与平衡二叉树区别

红黑树：节点是红色或黑色；根节点是黑色；每个叶子节点都是黑色的空节点；每个红色节点的两个子节点都是黑色；从任一节点到其每个叶子的所有路径都包含相同数据的黑色节点。

这些约束保证了根节点的路径中最长路径与最短路径之间相差不超过两倍。而平衡二叉树要求左右两个子树的高度差不超过1.但是插入和删除会牺牲掉logN左右的时间。而红黑树最多只需要3次就能达到平衡。但是两者的时间复杂度相差不大。

## HashMap的实现

HashMap是基于拉链法实现的一个散列表，内部由数组、链表和红黑树实现。

 ![img](http://korov.myqnapcloud.cn:19000/images/1216080-20180412104426360-1425516709.png)

HashMap的工作原理：

1. 数组的初始容量为16，而容量是以2的次方扩充的，一是为了提高性能使用足够大的数组，二是为了能使用位运算代替取模预算(据说提升了5~8倍)
2. 数组是否需要扩充是通过负载因子判断的，如果当前元素个数为数组容量的0.75时，就会扩充数组。这个0.75就是默认的负载因子，可由构造传入。我们也可以设置大于1的负载因子，这样数组就不会扩充，牺牲性能，节省内存。
3. 为了解决碰撞，数组中的元素是单向链表类型。当链表长度到达一个阈值时（8），会将链表转换成红黑树提高性能。而当链表长度缩小到另一个阈值时（6），又会将红黑树转换回单向链表提高性能，这里是一个平衡点。
4. 对于第三点补充说明，检查链表长度转换成红黑树之前，还会先检测当前数组数组是否到达一个阈值（64），如果没有到达这个容量，会放弃转换，先去扩充数组。所以上面也说了链表长度的阈值是7或8，因为会有一次放弃转换的操作。

**数组的索引bucket**：HashMap采用hash算法来决定集合中元素的存储位置，每当系统初始化HashMap时，会创建一个为`capacity`的数组，这个数组里面可以存储元素的位置被成为`桶(bucket)`, 每个`bucket`都有其指定索引。可以根据该索引快速访问存储的元素。

### 存在有序的HashMap吗？

TreeMap和LinkedHashMap。TreeMap默认是按照key值升序排序的，用红黑树作为实现的，可以使用比较器改变排序。LinkedHashMap是按照put的顺序排序的,LinkedHashMap继承了HashMap，大部分代码都是重用的。

### 我们能否让HashMap同步

Map m = Collections.synchronizeMap(hashMap);

### 你知道HashMap的put()方法和get()方法的工作原理吗

put：

1. 检查数组是否为空，执行resize()扩充
2. 通过hash值计算数组索引，获取该索引位的首节点
3. 如果首节点为null`（没发生碰撞）`，直接添加节点到该索引位`(bucket)`
4. 如果首节点不为null`（发生碰撞）`，那么有3种情况：① key和首节点的key相同，覆盖old value`（保证key的唯一性）`；否则执行②或③；② 如果首节点是红黑树节点（TreeNode），将键值对添加到红黑树。③ 如果首节点是链表，将键值对添加到链表。添加之后会判断链表长度是否到达。TREEIFY_THRESHOLD - 1这个阈值，“尝试”将链表转换成红黑树。
5. 最后判断当前元素个数是否大于threshold，扩充数组

get：

1. 检查数组是否为null 和 索引位首节点`(bucket的第一个节点)`是否为null
2. 如果索引节点的hash==key的hash 或者 key和索引节点的k相同则直接返回`(bucket的第一个节点)`
3. 如果是红黑色则到红黑树查找
4. 如果有冲突，则通过key.equals(k)查找
5. 都没找到就返回null

### 当两个对象的hashcode相同会发生什么

两个对象的hashCode相同所以它们的bucket位置相同，会发生hash碰撞。HashMap使用链表存储对象，这个Entry会存储在链表中，存储时会检查链表中是否包含key `(key != null && key.equals(k)`，或将键值对添加到链表尾部。如果链表长度大于或等于8，链表转换红黑树

### 如果两个键的hashcode相同，如何获取对象值

两个对象的hashCode相同所以它们的bucket位置相同，找到bucket位置之后，会调用keys.equals()方法去找到链表中正确的节点 `(key != null && key.equals(k)`

### 影响HashMap性能的因素

初始容量，负载因子。

### HashMap为什么只允许一个key为null

如果key为null，hash这个key会返回0，然后null == null为true，此时这个值就只会放在第一个桶里，再来一个null的话还是插入第一个桶了，所以只会有一个。

## HashMap在高并发下如果没有处理线程安全会有怎样的安全隐患，具体表现是什么

1.多线程put时可能会导致get无限循环，具体表现为CPU使用率100%；原因：在向HashMap  put元素时，会检查HashMap的容量是否足够，如果不足，则会新建一个比原来容量大两倍的Hash表，然后把数组从老的Hash表中迁移到新的Hash表中，迁移的过程就是一个rehash()的过程（该方法中的transfer方法会执行出现循环Entry List），多个线程同时操作就有可能会形成循环链表，所以在使用get()时，就会出现Infinite Loop的情况。

这是JDK7时候迁移的时候会出现这个问题，jdk8做了修改，rehash的时候只会往链表的末尾添加元素，不会出现无限循环的问题了

2.多线程put时可能导致元素丢失，jdk7和jdk8都有这个问题

## 如何实现数组和 List 之间的转换

1. 遍历数组然后add进list
2. 使用Arrays.asList
3. Collections.addAll()
4. 使用Stream中的Collector收集器

## Queue中的方法

**offer()和add()的区别**：add()和offer()都是向队列中添加一个元素。但是如果想在一个满的队列中加入一个新元素，调用 add() 方法就会抛出一个 unchecked 异常，而调用 offer() 方法会返回 false。

**peek()和element()的区别**：peek()和element()都将在**不移除**的情况下返回队头，但是peek()方法在队列为空时返回null，调用element()方法会抛出NoSuchElementException异常。

**poll()和remove()的区别**：poll()和remove()都将**移除**并且返回队头，但是在poll()在队列为空时返回null，而remove()会抛出NoSuchElementException异常。

## Java 中 LinkedHashMap 和 PriorityQueue 的区别是什么？

PriorityQueue 保证最高或者最低优先级的的元素总是在队列头部，但是 LinkedHashMap 维持的顺序是元素插入的顺序。当遍历一个 PriorityQueue 时，没有任何顺序保证，但是 LinkedHashMap 课保证遍历顺序是元素插入的顺序。

LinkedHashMap存储数据的结构继承自HashMap，但是重新实现了Entry（多了before和after），以及重新实现了newNode方法，使用newNode方法时会维护一个Node的双向链表。

## Iterator有什么特点

1. Iterator遍历集合元素的过程中不允许线程对集合元素进行修改，否则会抛出ConcurrentModificationEception的异常。
2. Iterator遍历集合元素的过程中可以通过remove方法来移除集合中的元素。
3. Iterator必须依附某个Collection对象而存在，Iterator本身不具有装载数据对象的功能。
4. Iterator.remove方法删除的是上一次Iterator.next()方法返回的对象。调用remove时必须先调用next
5. 强调以下next（）方法，该方法通过游标指向的形式返回Iterator下一个元素。

## Iterator 和 ListIterator 有什么区别

1. ListIterator有add()方法，可以向list中添加对象
2. ListIterator和Iterator都有hasNext()和next()方法，可以实现顺序向后遍历，但是ListIterator有hasPrevious()和previous()方法，可以实现逆向（顺序向前）遍历。
3. ListIterator可以定位当前的索引位置，nextIndex()和previousIndex()可以实现。Iterator没有此功能。
4. ListIterator可以通过set方法实现对象的修改，Iterator不可以

## 怎么确保一个集合不能被修改

```java
Collections.unmodifiableList(List);
Collections.unmodifiableMap(List);
Collections.unmodifiableSet(List);
```

或者用Guava中自带的ImutableList

## 什么是反射

是指程序在运行状态中，对于任意一个类，都可以知道这个类的所有属性和方法；对于任意一个对象，都能够调用他的任意方法和属性。
其原理是通过类的全限定名加载class文件，然后通过class文件创建一个完整的对象。

## 什么是 java 序列化？集中序列化方案？什么情况下需要序列化？

序列化：将Java对象转换成字节流。反序列化：将字节流转换成Java对象的过程。

序列化方式：

- 实现Serializable接口(隐式序列化，实例中所有属性都会被序列化)
- 实现Externalizable接口。(显式序列化)：Externalizable接口继承自Serializable, 我们在实现该接口时，必须实现writeExternal()和readExternal()方法，而且只能通过手动进行序列化，并且两个方法是自动调用的，因此，这个序列化过程是可控的，可以自己选择哪些部分序列化
- 实现Serializable接口+添加writeObject()和readObject()方法。(显+隐序列化)：如果想将方式一和方式二的优点都用到的话，可以采用方式三， 先实现Serializable接口，并且添加writeObject()和readObject()方法。注意这里是添加，不是重写或者覆盖。但是添加的这两个方法必须有相应的格式。
  - 方法必须要被private修饰  
  - 第一行调用默认的defaultRead/WriteObject(); ----->隐式序列化非static和transient
  - 调用read/writeObject()将获得的值赋给相应的值  --->显式序列化

当Java对象需要在网络上传输或者持久化存储到文件中时，就需要使用序列化

注意：

1. 某个类可以被序列化，则其子类也可以被序列化
2. 声明为 static 和 transient 的成员变量，不能被序列化。static 成员变量是描述类级别的属性，transient 表示临时数据
3. 反序列化读取序列化对象的顺序要保持一致

## 什么是动态代理？动态代理是如何实现的？动态代理有哪些应用？

动态代理：当想要给实现了某个接口的类中的方法，加一些额外的处理。比如说加日志，加事务等。可以给这个类创建一个代理，故名思议就是创建一个新的类，这个类不仅包含原来类方法的功能，而且还在原来的基础上添加了额外处理的新类。这个代理类并不是定义好的，是动态生成的。具有解耦意义，灵活，扩展性强。
动态代理实现：首先必须定义一个接口，还要有一个`InvocationHandler`实现类。再有一个工具类Proxy(调用他的newInstance()可以产生代理对象）。利用`InvocationHandler`实现类，拼接代理类源码（利用了反射），将其编译生成代理类的二进制码，利用加载器加载，并将其实例化产生代理对象，最后返回。
动态代理的应用：Spring的AOP，加事务，加权限，加日志。

## 动态代理的两种方式，以及区别

**JDK动态代理**：利用反射机制生成一个实现代理接口的匿名类，在调用具体方法前调用InvokeHandler来处理。
**CGlib动态代理**：利用ASM（开源的Java字节码编辑库，操作字节码）开源包，将代理对象类的class文件加载进来，通过修改其字节码生成子类来处理。

**区别**：JDK代理只能对实现接口的类生成代理；CGlib是针对类实现代理，对指定的类生成一个子类，并覆盖其中的方法，这种通过继承类的实现方式，不能代理final修饰的类。

**Spring如何选择用JDK还是CGLiB？**

1、当Bean实现接口时，Spring就会用JDK的动态代理。  2、当Bean没有实现接口时，Spring使用CGlib是实现。  3、可以强制使用CGlib（在spring配置中加入<aop:aspectj-autoproxy  proxy-target-class="true"/>，或者SpringBoot中添加@EnableAspectJAutoProxy(proxyTargetClass = true)注解）。

**CGlib比JDK快？**

1、使用CGLib实现动态代理，CGLib底层采用ASM字节码生成框架，使用字节码技术生成代理类， 在jdk6之前比使用Java反射效率要高。唯一需要注意的是，CGLib不能对声明为final的方法进行代理，  因为CGLib原理是动态生成被代理类的子类。  2、在jdk6、jdk7、jdk8逐步对JDK动态代理优化之后，在调用次数较少的情况下，JDK代理效率高于CGLIB代理效率，只有当进行大量调用的时候，jdk6和jdk7比CGLIB代理效率低一点，但是到jdk8的时候，jdk代理效率高于CGLIB代理，总之，每一次jdk版本升级，jdk代理效率都得到提升，而CGLIB代理消息确有点跟不上步伐。

## 如何实现克隆

实现Cloneable接口，并重写object类中的clone方法可以实现浅克隆。
实现Serializable，通过对象的序列化和反序列化实现正真的深克隆。

## 自定义注解的实现

首先我们了解一下自定义注解的标准示例，注解类使用 @interface 关键字修饰，且在注解类上方声明注解相关信息，包含以下四种信息

```
@Documented – 注解是否将包含在JavaDoc中

@Retention – 什么时候使用该注解

@Target – 注解用于什么地方

@Inherited – 是否允许子类继承该注解
```

 

 1.）@Retention – 定义该注解的生命周期
  ●   RetentionPolicy.SOURCE : 在编译阶段丢弃。这些注解在编译结束之后就不再有任何意义，所以它们不会写入字节码。@Override, @SuppressWarnings都属于这类注解。
  ●   RetentionPolicy.CLASS : 在类加载的时候丢弃。在字节码文件的处理中有用。注解默认使用这种方式
  ●   RetentionPolicy.RUNTIME : 始终不会丢弃，运行期也保留该注解，因此可以使用反射机制读取该注解的信息。我们自定义的注解通常使用这种方式。

  2.）Target – 表示该注解用于什么地方。默认值为任何元素，表示该注解用于什么地方。可用的ElementType 参数包括
  ● ElementType.CONSTRUCTOR: 用于描述构造器
  ● ElementType.FIELD: 成员变量、对象、属性（包括enum实例）
  ● ElementType.LOCAL_VARIABLE: 用于描述局部变量
  ● ElementType.METHOD: 用于描述方法
  ● ElementType.PACKAGE: 用于描述包
  ● ElementType.PARAMETER: 用于描述参数
  ● ElementType.TYPE: 用于描述类、接口(包括注解类型) 或enum声明

 3.)@Documented – 一个简单的Annotations 标记注解，表示是否将注解信息添加在java 文档中。

 4.)@Inherited – 定义该注释和子类的关系
     @Inherited 元注解是一个标记注解，@Inherited 阐述了某个被标注的类型是被继承的。如果一个使用了@Inherited 修饰的annotation 类型被用于一个class，则这个annotation 将被用于该class 的子类。

## java8新特性

### 1.lambda表达式

是一个可传递的代码块，可以在以后执行一次或多次。对于函数式接口，就可以提供一个lambda表达式。java.util.function包下提供了很多的函数式接口，我常用的是Function和Predicate两个接口。

lambda可以访问外部变量，该变量可以不是final的但是必须是实际final变量。

### 2.函数式接口

对于只有一个抽象方法的接口。这种接口称为函数式接口。被声明 `@FunctionalInterface` 注解的接口应该满足函数式接口的定义。如果某个接口只有一个抽象方法，但我们并没有给该接口声明 `@FunctionalInterface` 注解，那么编译器依旧会将该接口看作是函数式接口。

### 3.stream api

流遵循了做什么而非怎么做。

流与集合之间的差异：

- 流并不存储其元素。这些元素可能存储在底层的集合中，或者是按需生成的。
- 流的操作不会修改其源数据。例如，filter方法不会从新的流中移除元素，而是会生成一个新的流，其中不包含被过滤掉的元素
- 流的操作是尽可能的惰性执行的。

流中常用的方法：

- filter：讲符合条件的数据过滤出来形成一个新的流
- map`<R> Stream<R> map(Function<? super T, ? extends R> mapper)`：产生一个流，它包含将mapper应用于当前流中所有元素产生的结果（多个流产生的结果还是多个流）
- flatMap`<R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper);`：产生一个流，通过将mapper应用于当前流中所有元素所产生 的结果链接到一起而获得的。（可以将多个流拼接成一个流）
- limit：产生一个流，其中包含当前流的前n个元素
- skip：产生一个流，跳过当前流的前n个元素
- concat：产生一个流，将流a和流b连接起来
- distinct：产生一个流，包含当前流中所有不同的元素
- sorted：产生一个流，对当前流排序之后产生的流
- peek`Stream<T> peek(Consumer<? super T> action)`：产生一个流，它与当前流中的元素相同，在获取其中每个元素时，会将其传递给action处理，但是没有返回值

### 4.接口

接口中可以定义 默认方法 default-method和静态方法 static-method的实现。

如果接口声明了 default 方法，并且某类实现了该接口，那么 default 方法将会被继承。如果有一个类继承了两个不同接口的同名 default 方法，jvm 编译器是无法识别到底该使用哪个方法的，必须重写 default 方法或指定要继承接口的 default 方法。

### 5.方法引用

把类的静态方法或者实例的静态方法和实例方法整合成lambda表达式传递出去

- `object::staticMethod`
- `Class::staticMethod`
- `Class::instanceMethod`

在前 2 种情况中，方法引用等价于提供方法参数的 lambda 表达式。前面已经提到，System.out::println 等价于 x -> System.out.println(x。) 类似地，Math::pow 等价于（x，y) ->Math.pow(x, y。)
对于第 3 种情况， 第 1 个参数会成为方法的目标。例如，String::compareToIgnoreCase 等同于 (x, y) -> x.compareToIgnoreCase(y) 

### 6.重复注解，并且拓宽了注解的应用场景

在定义的注解上方加入`@Repeatable`就可以重复使用该注解。jdk8之后注解几乎可以使用在任何元素上：局部变量、接口类型、超类和接口实现类。

### 7.增加了工具类

date、time类

## 什么是不可变对象（immutable object）？Java 中怎么创建一个不可变对象？

不可变对象指对象一旦被创建，状态就不能再改变。任何修改都会创建一个新的对象，如 String、Integer及其它包装类。

创建不可变对象：

- 将类声明为final
- 将所有成员声明为私有，并且不提供setter方法
- 将所有可变的成员声明为final
- 通过构造器初始化所有成员
- 在getter方法中，不要直接返回对象本身，返回深拷贝的克隆对象

## 我们能创建一个包含可变对象的不可变对象吗？

是的，我们是可以创建一个包含可变对象的不可变对象的，你只需要谨慎一点，不要共享可变对象的引用就可以了，如果需要变化时，就返回原对象的一个拷贝。最常见的例子就是对象中包含一个日期对象的引用。

## byte

### 怎么将 byte 转换为 String？

可以使用 String 接收 byte[] 参数的构造器来进行转换，需要注意的点是要使用的正确的编码，否则会使用平台默认编码，这个编码可能跟原来的编码相同，也可能不同

### 我们能将 int 强制转换为 byte 类型的变量吗？如果该值大于 byte 类型的范围，将会出现什么现象？

是的，我们可以做强制转换，但是 Java 中 int 是 32 位的，而 byte 是 8 位的，所以，如果强制转化是，int 类型的高 24 位将会被丢弃，byte 类型的范围是从 -128 到 128。

## 存在两个类，B 继承 A，C 继承 B，我们能将 B 转换为 C 么？

不可以，运行时会抛出ClassCastException，父类不可以转化为子类

## 哪个类包含 clone 方法？是 Cloneable 还是 Object？

java.lang.Cloneable 是一个标示性接口，不包含任何方法，clone 方法在 object 类中定义。并且需要知道 clone() 方法是一个本地方法，这意味着它是由 c 或 c++ 或 其他本地语言实现的。

## 我能在不进行强制转换的情况下将一个 double 值赋值给 long 类型的变量吗

不行，你不能在没有强制类型转换的前提下将一个 double 值赋值给 long 类型的变量，因为 double 类型的范围比 long 类型更广，所以必须要进行强制转换。

## a = a + b 与 a += b 的区别

+= 隐式的将加操作的结果类型强制转换为持有结果的类型。如果两这个整型相加，如 byte、short 或者 int，首先会将它们提升到 int 类型，然后在执行加法操作。如果加法操作的结果比 a 的最大值要大，则 a+b 会出现编译错误，但是 a += b 没问题，如下：

```java
byte a = 127;
byte b = 127;
b = a + b; // error : cannot convert from int to byte
b += a; // ok
```

（译者注：这个地方应该表述的有误，其实无论 a+b 的值为多少，编译器都会报错，因为 a+b 操作会将 a、b 提升为 int 类型，所以将 int 类型赋值给 byte 就会编译出错）

## Java 中的构造器链是什么？

当你从一个构造器中调用另一个构造器，就是Java 中的构造器链。这种情况只在重载了类的构造器的时候才会出现。

## Java 中的编译期常量是什么？使用它又什么风险？

公共静态不可变（public static final ）变量也就是我们所说的编译期常量，这里的 public 可选的。实际上这些变量在编译时会被替换掉，因为编译器知道这些变量的值，并且知道这些变量在运行时不能改变。

风险：你使用了一个内部的或第三方库中的公有编译时常量，但是这个值后面被其他人改变了，这可能会导致程序出错。

## Java 中，Comparator 与 Comparable 有什么不同？

Comparable 接口用于定义对象的自然顺序，而 comparator 通常用于定义用户定制的顺序。通常是某个类实现Comparable接口，重写compareTo方法来实现自然顺序。comparator也是一个接口，通常将某个实现了comparator接口的类作为排序策略传递给某些类。

## 为什么在重写 equals 方法的时候需要重写 hashCode 方法

因为有强制的规范指定需要同时重写 hashcode 与 equal 是方法，许多容器类，如 HashMap、HashSet 都依赖于 hashcode 与 equals 的规定。

## 列出 5 个应该遵循的 JDBC 最佳实践

1. 使用批量的操作来插入和更新数据
2. 使用 PreparedStatement 来避免 SQL 注入，并提高性能
3. 使用数据库连接池
4. 通过列名来获取结果集，不要使用列的下标来获取。
5. 获取列的时候只获取必要的列，能distinct尽量distinct
6. 对于数据量较大的表不可以使用关联查询，避免笛卡儿积问题

## 描述 Java 中的重载和重写

重载和重写都允许你用相同的名称来实现不同的功能，但是重载是编译时活动，而重写是运行时活动。你可以在同一个类中重载方法，但是只能在子类中重写方法。重写必须要有继承。

## OOP 中的 组合、聚合和关联有什么区别

如果两个对象彼此有关系，就说他们是彼此相关联的。组合和聚合是面向对象中的两种形式的关联。组合是一种比聚合更强力的关联。组合中，一个对象是另一个的拥有者，而聚合则是指一个对象使用另一个对象。如果对象 A 是由对象 B 组合的，则 A 不存在的话，B一定不存在，但是如果 A 对象聚合了一个对象 B，则即使 A 不存在了，B 也可以单独存在。

# Java并发

## Java实现多线程有哪几种方式

- 继承Threads类
- 实现Runnable接口
- 通过Callable和FutureTask创建线程
- 通过线程池创建线程

## Callable和Future的了解

- Callable接口只有一个call方法，Future有cancel，isCancelled，isDone和get方法。
- Callable用于生成结果，Future用于获取结果
- Future表示一个可能还没有完成的异步任务的结果，针对这个结果可以添加Callback以便在任务执行成功或失败后作出相应的操作
- FutureTask是Runnable, Future接口的实现类。
- RunnableFuture
  - 这个接口同时继承Future接口和Runnable接口，在成功执行run（）方法后，可以通过Future访问执行结果。这个接口都实现类是FutureTask,一个可取消的异步计算，这个类提供了Future的基本实现，后面我们的demo也是用这个类实现，它实现了启动和取消一个计算，查询这个计算是否已完成，恢复计算结果。计算的结果只能在计算已经完成的情况下恢复。如果计算没有完成，get方法会阻塞，一旦计算完成，这个计算将不能被重启和取消，除非调用runAndReset方法。
  - FutureTask能用来包装一个Callable或Runnable对象，因为它实现了Runnable接口，而且它能被传递到Executor进行执行。为了提供单例类，这个类在创建自定义的工作类时提供了protected构造函数。

## 并发和并行的区别

并发：同一时间段，多个任务都在执行，单位时间内不一定同时执行;并行：单位时间内，多个任务同时执行。

## 线程与进程之间的不同

进程是资源分配的最小单位，线程是程序执行的最小单位，每个进程有自己的独立地址空间，线程共享进程中的数据。

## 说一下 runnable 和 callable 有什么区别

相同：两者都是接口，两者都可用来编写多线程程序，都通过Thread.start()启动线程
不同：实现Callable接口的任务线程能返回执行结果，实现Runnable接口的任务线程不能返回结果；Callable接口的call()方法允许抛出异常；Runnable的run()方法异常只能在内部消化，不能往上继续抛。
注：Callalbe接口支持返回执行结果，需要调用FutureTask.get()得到，此方法会阻塞主进程的继续往下执行，如果不调用不会阻塞

## Thread类中的start和run方法有什么区别

start被用来启动新线程并且在内部调用了run，run不能。start不能被重复调用，run可以。

## 在多线程中，什么是上下文切换

存储和恢复CPU状态的过程，它使得线程能够从中断点恢复。

## volatile变量

一种实现数据共享的轻量锁，**提供可见性保证。**

volatile可以修饰对象，但是volatile指向的是对象的地址，而不是对象的内容，这样多线程访问时候无法访问到最新的对象内容。通过读屏障和写屏障来实现，每次读的时候都是从主内存获取最新的数据，每次写的时候将数据写入到主内存中

### Java变量的读写

Java通过几种原子操作完成`工作内存`和`主内存`的交互：

1. lock：作用于主内存，把变量标识为线程独占状态。
2. read：作用主内存，把一个变量的值从主内存传输到线程的工作内存。
3. load：作用于工作内存，把read操作传过来的变量值放入工作内存的变量副本中。
4. use：作用工作内存，把工作内存当中的一个变量值传给执行引擎。
5. assign：作用工作内存，把一个从执行引擎接收到的值赋值给工作内存的变量。
6. store：作用于工作内存的变量，把工作内存的一个变量的值传送到主内存中。
7. write：作用于主内存的变量，把store操作传来的变量的值放入主内存的变量中。
8. unlock：作用于主内存，解除独占状态。

### volatile如何保持内存可见性

volatile的特殊规则就是：

- read、load、use动作必须**连续出现**。
- assign、store、write动作必须**连续出现**。

所以，使用volatile变量能够保证:

- 每次`读取前`必须先从主内存刷新最新的值。
- 每次`写入后`必须立即同步回主内存当中。

### volatile如何防止指令重排

volatile关键字通过`内存屏障`来防止指令被重排序。

为了实现volatile的内存语义，编译器在生成字节码时，会在指令序列中插入内存屏障来禁止特定类型的处理器重排序。然而，对于编译器来说，发现一个最优布置来最小化插入屏障的总数几乎不可能，为此，Java内存模型采取保守策略。

下面是基于保守策略的JMM内存屏障插入策略：

- 在每个volatile写操作的前面插入一个StoreStore屏障。
- 在每个volatile写操作的后面插入一个StoreLoad屏障。
- 在每个volatile读操作的后面插入一个LoadLoad屏障。
- 在每个volatile读操作的后面插入一个LoadStore屏障。

### volatile 修饰符的有过什么实践

一种实践是用 volatile 修饰 long 和 double 变量，使其能按原子类型来读写。double 和 long 都是64位宽，因此对这两种类型的读是分为两部分的，第一次读取第一个 32 位，然后再读剩下的 32 位，这个过程不是原子的，但 Java 中 volatile 型的 long 或 double 变量的读写是原子的。volatile 修复符的另一个作用是提供内存屏障（memory barrier），例如在分布式框架中的应用。简单的说，就是当你写一个 volatile 变量之前，Java 内存模型会插入一个写屏障（write barrier），读一个 volatile 变量之前，会插入一个读屏障（read barrier）。意思就是说，在你写一个 volatile 域时，能保证任何线程都能看到你写的值，同时，在写之前，也能保证任何数值的更新对所有线程是可见的，因为内存屏障会将其他所有写的值更新到缓存。

## 死锁是什么？如何避免死锁？

死锁是指两个或两个以上的线程在执行过程中，因争夺资源而造成的一种互相等待的现象，若无外力作用，它们都将无法推进下去。

死锁发生的四个条件：

- 互斥条件：一个资源每次只能被一个线程使用
- 请求与保持条件：一个线程因请求资源而阻塞时，对已获得的资源保持不放
- 不剥夺条件：线程已获得的资源，在未使用完之前，不能强行剥夺
- 循环等待条件：若干线程之间形成一种头尾相接的循环等待资源关系

避免死锁最简单的方法及时阻止循环等待条件，将系统中所有的资源设置标志位、排序，规定所有的进程申请资源必须以一定的顺序做操作来避免死锁。

## Thread类中的yield方法有什么作用

Thread.yield()方法会使当前线程从运行状态变为就绪状态，把运行机会让给其他相同优先级的线程。

## Java中interrupted 和 isInterrupted方法的区别

interrupted()是静态方法：内部实现是调用的当前线程的isInterrupted()，并且会重置当前线程的中断状态。isInterrupted()是实例方法，是调用该方法的对象所表示的那个线程的isInterrupted()，不会重置当前线程的中断状态。

```Java
public class ThreadTest {
    public static void main(String[] args) {
        // 调用interrupt将线程中断状态设置为true
        Thread.currentThread().interrupt();
        System.out.println(Thread.currentThread().isInterrupted());
        System.out.println(Thread.interrupted());
        // 线程中断状态被interrupted重置为false
        System.out.println(Thread.currentThread().isInterrupted());
    }
}

true
true
false
```

## Java多线程中调用wait() 和 sleep()方法有什么不同

sleep()和wait()都是使线程暂停执行一段时间的方法。区别：

1. 原理不同：sleep()方法是Thread类的静态方法，是线程用来控制自身流程的，它会使此线程暂停执行一段时间，而把执行机会让给其他线程，等到计时时间一到，此线程会自动苏醒。而wait()方法是Object类的方法，用于线程间的通信，这个方法会使当前拥有该对象锁的进程等待，直到其他线程用调用notify()或notifyAll()时才苏醒过来，开发人员也可以给它指定一个时间使其自动醒来
2. 对锁的处理机制不同：由于sleep()方法的主要作用是让线程暂停一段时间，时间一到则自动恢复，不涉及线程间的通信，因此调用sleep()方法仅仅释放CPU资源或者让当前线程停止执行一段时间，但不会释放锁。而wait()方法则不同，当调用wait()方法后，线程会释放掉它所占用的锁，从而使线程所在对象中的其他synchronized数据可被别的线程使用。
3. 使用区域不同：wait()方法必须放在同步控制方法或者同步语句块中使用，而sleep方法则可以放在任何地方使用。sleep()方法必须捕获异常，而wait()、notify()、notifyAll()不需要捕获异常。在sleep的过程中，有可能被其他对象调用它的interrupt()，产生InterruptedException异常。

由于sleep不会释放锁标志，容易导致死锁问题的发生，一般情况下，不推荐使用sleep()方法，而推荐使用wait()方法。

wait()方法应该在while循环中使用，确保所有条件满足时才开始处理。

```java
while(condition dose not hold){
    wait();
}
```

## 什么是多线程环境下的伪共享（false sharing）

伪共享是多线程系统（每个处理器有自己的局部缓存）中一个众所周知的性能问题。伪共享发生在不同处理器的上的线程对变量的修改依赖于相同的缓存行。（多个线程同时修改同一个缓存行）。避免伪共享的最基本方式是仔细审查代码，根据缓存行来调整你的数据结构。

## 守护线程

在Java中有两类线程：用户线程 (User Thread)、守护线程 (Daemon Thread)。 
 守护线程和用户线程的区别在于：守护线程依赖于创建它的线程，而用户线程则不依赖。举个简单的例子：如果在main线程中创建了一个守护线程，当main方法运行完毕之后，守护线程也会随着消亡。而用户线程则不会，用户线程会一直运行直到其运行完毕。在JVM中，像垃圾收集器线程就是守护线程

## java thread状态

- new：初始状态，线程刚创建，尚未启动
- runnable：运行状态，Java线程中将就绪（ready）和运行中（running）两种状态笼统的称为“运行”。 线程对象创建后，其他线程(比如main线程）调用了该对象的start()方法。该状态的线程位于可运行线程池中，等待被线程调度选中，获取CPU的使用权，此时处于就绪状态（ready）。就绪状态的线程在获得CPU时间片后变为运行中状态（running）
- blocked：阻塞状态，线程阻塞于锁
- waiting：等待状态，进入该状态的线程需要等待其他线程做出一些特定动作（通知或中断）
- timed_waiting：超时等待，该状态不同于WAITING，它可以在指定的时间后自行返回
- terminated：终止，表示该线程已经执行完毕

## 进程调度算法

**实时系统**：FIFO(First Input First Output，先进先出算法)，SJF(Shortest Job First，最短作业优先算法)，SRTF(Shortest Remaining Time First，最短剩余时间优先算法）。  
 **交互式系统**：RR(Round Robin，时间片轮转算法)，HPF(Highest Priority First，最高优先级算法)，多级队列，最短进程优先，保证调度，彩票调度，公平分享调度。

## synchronized与lock和volatile区别

synchronized与volatile区别：

- volatile本质是在告诉jvm当前变量在寄存器（工作内存）中的值是不确定的，需要从主存中读取； synchronized则是锁定当前变量，只有当前线程可以访问该变量，其他线程被阻塞住。
- volatile仅能使用在变量级别；synchronized则可以使用在变量、方法、代码块和类级别的
- volatile仅能实现变量的修改可见性，不能保证原子性；而synchronized则可以保证变量的修改可见性和原子性
- volatile不会造成线程的阻塞；synchronized可能会造成线程的阻塞
- volatile标记的变量不会被编译器优化；synchronized标记的变量可以被编译器优化

synchronized与lock区别：

- 首先synchronized是java内置关键字，在jvm层面，Lock是个java类
- synchronized无法判断是否获取锁的状态，Lock可以判断是否获取到锁
- synchronized会自动释放锁，Lock需在finally中手工释放锁（unlock()方法释放锁），否则容易造成线程死锁；
- 用synchronized关键字的两个线程1和线程2，如果当前线程1获得锁，线程2线程等待。如果线程1阻塞，线程2则会一直等待下去，而Lock锁就不一定会等待下去，如果尝试获取不到锁，线程可以不用一直等待就结束了；
- synchronized的锁可重入、不可中断、非公平，而Lock锁可重入、可判断、可公平（两者皆可），（公平锁是指多个线程在等待同一个锁时，必须按照申请的时间顺序来依次获得锁）
- lock锁适合大量同步的代码的同步问题，synchronized锁适合代码少量的同步问题
- synchronized可以给类、方法、代码块和变量加锁，而lock只能给代码块加锁。

## Lock接口有哪些实现类，使用场景是什么。

Lock接口有三个实现类，一个是ReentrantLock,另两个是ReentrantReadWriteLock类中的两个静态内部类ReadLock和WriteLock。

与互斥锁定相比，读-写锁定允许对共享数据进行更高级别的并发访问。虽然一次只有一个线程（writer 线程）可以修改共享数据，但在许多情况下，
任何数量的线程可以同时读取共享数据（reader 线程）。从理论上讲，与互斥锁定相比，使用读-写锁定所允许的并发性增强将带来更大的性能提高。

在实践中，只有在多处理器上并且只在访问模式适用于共享数据时，才能完全实现并发性增强。——例如，某个最初用数据填充并且之后不经常对其进行修改的 collection，因为经常对其进行搜索（比如搜索某种目录），所以这样的 collection 是使用读-写锁定的理想候选者。

### ReentrantLock原理

可重入锁的原理是在锁内部维护了一个线程标示，标示该锁目前被那个线程占用，然后关联一个计数器，一开始计数器值为0，说明该锁没有被任何线程占用，当一个线程获取了该锁，计数器会变成1，其他线程在获取该锁时候发现锁的所有者不是自己所以被阻塞，但是当获取该锁的线程再次获取锁时候发现锁拥有者是自己会把计数器值+1， 当释放锁后计数器会-1，当计数器为0时候，锁里面的线程标示重置为null,这时候阻塞的线程会获取被唤醒来获取该锁.
--ReentrantLock 类实现了Lock ，它拥有与synchronized 相同的并发性和内存语义，但是添加了类似锁投票、定时锁等候和可中断锁等候的一些特性。
此外，它还提供了在激烈争用情况下更佳的性能。（换句话说，当许多线程都想访问共享资源时，JVM 可以花更少的时候来调度线程，把更多时间用在执行线程上。）

### ReentrantLock扩展的功能

#### 实现可轮询的锁请求

--在内部锁中，死锁是致命的——唯一的恢复方法是重新启动程序，唯一的预防方法是在构建程序时不要出错。而可轮询的锁获取模式具有更完善的错误
恢复机制，可以规避死锁的发生。 
--如果你不能获得所有需要的锁，那么使用可轮询的获取方式使你能够重新拿到控制权，它会释放你已经获得的这些锁，然后再重新尝试。
可轮询的锁获取模式，由tryLock()方法实现。此方法仅在调用时锁为空闲状态才获取该锁。如果锁可用，则获取锁，并立即返回值true。
如果锁不可用，则此方法将立即返回值false。此方法的典型使用语句如下：

```java
Lock lock = ...;   
if (lock.tryLock()) {   
try {   
// manipulate protected state   
} finally {   
lock.unlock();   
}   
} else {   
// perform alternative actions   
}
```

#### 实现可定时的锁请求

--当使用内部锁时，一旦开始请求，锁就不能停止了，所以内部锁给实现具有时限的活动带来了风险。为了解决这一问题，可以使用定时锁。
当具有时限的活动调用了阻塞方法，定时锁能够在时间预算内设定相应的超时。如果活动在期待的时间内没能获得结果，定时锁能使程序提前返回。
可定时的锁获取模式，由tryLock(long, TimeUnit)方法实现。

#### 实现可中断的锁获取请求

可中断的锁获取操作允许在可取消的活动中使用。lockInterruptibly()方法能够使你获得锁的时候响应中断。

### ReentrantReadWriteLock

对于lock的读写锁，可以通过new ReentrantReadWriteLock()获取到一个读写锁。所谓读写锁，便是多线程之间读不互斥，读写互斥。读写锁是一种自旋锁，如果当前没有读者，也没有写者，那么写者可以立刻获得锁，否则它必须自旋在那里，直到没有任何写者或读者。如果当前没有写者，那么读者可以立即获得该读写锁，否则读者必须自旋在那里，直到写者释放该锁。

### lock实现线程间通信

Condition可以替代传统的线程间通信，用await()替换wait()，用signal()替换notify()，用signalAll()替换notifyAll()。

### JAVA的AQS是否了解，它是干嘛的？

抽象的队列式的同步器，AQS定义了一套多线程访问共享资源的同步器框架，许多同步类实现都依赖于它，如常用的ReentrantLock/Semaphore/CountDownLatch...。

它维护了一个volatile int state（代表共享资源）和一个FIFO线程等待队列（多线程争用资源被阻塞时会进入此队列）。这里volatile是核心关键词，具体volatile的语义，在此不述。state的访问方式有三种:

- getState()
- setState()
- compareAndSetState()

　　AQS定义两种资源共享方式：Exclusive（独占，只有一个线程能执行，如ReentrantLock）和Share（共享，多个线程可同时执行，如Semaphore/CountDownLatch）。

　　不同的自定义同步器争用共享资源的方式也不同。**自定义同步器在实现时只需要实现共享资源state的获取与释放方式即可**，至于具体线程等待队列的维护（如获取资源失败入队/唤醒出队等），AQS已经在顶层实现好了。自定义同步器实现时主要实现以下几种方法：

- isHeldExclusively()：该线程是否正在独占资源。只有用到condition才需要去实现它。
- tryAcquire(int)：独占方式。尝试获取资源，成功则返回true，失败则返回false。
- tryRelease(int)：独占方式。尝试释放资源，成功则返回true，失败则返回false。
- tryAcquireShared(int)：共享方式。尝试获取资源。负数表示失败；0表示成功，但没有剩余可用资源；正数表示成功，且有剩余资源。
- tryReleaseShared(int)：共享方式。尝试释放资源，如果释放后允许唤醒后续等待结点返回true，否则返回false。

　　以ReentrantLock为例，state初始化为0，表示未锁定状态。A线程lock()时，会调用tryAcquire()独占该锁并将state+1。此后，其他线程再tryAcquire()时就会失败，直到A线程unlock()到state=0（即释放锁）为止，其它线程才有机会获取该锁。当然，释放锁之前，A线程自己是可以重复获取此锁的（state会累加），这就是可重入的概念。但要注意，获取多少次就要释放多么次，这样才能保证state是能回到零态的。

　　再以CountDownLatch以例，任务分为N个子线程去执行，state也初始化为N（注意N要与线程个数一致）。这N个子线程是并行执行的，每个子线程执行完后countDown()一次，state会CAS减1。等到所有子线程都执行完后(即state=0)，会unpark()主调用线程，然后主调用线程就会从await()函数返回，继续后余动作。

　　一般来说，自定义同步器要么是独占方法，要么是共享方式，他们也只需实现tryAcquire-tryRelease、tryAcquireShared-tryReleaseShared中的一种即可。但AQS也支持自定义同步器同时实现独占和共享两种方式，如ReentrantReadWriteLock。

## 说一下 atomic 的原理

自旋 + CAS（乐观锁）。在这个过程中，通过compareAndSwapInt比较更新value值，如果更新失败，重新获取旧值，然后更新

## 四种常见线程池以及ThreadPoolExecutor

jdk1.8线程池种类：newFixedThreadPool（定长线程池），newCachedThreadPool（可缓存线程池），newScheduledThreadPool（定长线程池，可执行周期性任务），newSingleThreadExecutor（单线程，线程池），newSingleThreadScheduledExecutor（单线程可执行周期性任务线程池），newWorkStealingPool（任务窃取线程池，不保证执行顺序，适合任务耗时差异较大。线程池中有多个线程队列，有的线程队列中有大量的比较耗时的任务堆积，而有的线程队列却是空的，就存在有的线程处于饥饿状态，当一个线程处于饥饿状态时，它就会去其它的线程队列中窃取任务。解决饥饿导致的效率问题）
ThreadPoolExecutor四种线程池就是通过ThreadPoolExecutor此类的构造方法实现的。设置核心线程数量，线程存活时间，线程池可以容纳的最大线程数。线程池中的任务队列三种：SynchronousQueue,LinkedBlockingDeque,ArrayBlockingQueue。

## 线程池的参数有哪些，在线程池创建一个线程的过程

核心参数：

- corePoolSize：核心线程数，核心线程会一直存活，即使没有任务需要执行
- workQueue：任务队列容量（阻塞队列）。当核心线程数达到最大时，新任务会放在队列中排队等待执行。
- maximumPoolSize：最大线程数.当线程数>=corePoolSize，且任务队列已满时。线程池会创建新线程来处理任务。当线程数=maximumPoolSize，且任务队列已满时，线程池会拒绝处理任务而抛出异常。
- keepAliveTime：线程空闲时间。当线程空闲时间达到keepAliveTime时，线程会退出，直到线程数量=corePoolSize。如果allowCoreThreadTimeout=true，则会直到线程数量=0。
- unit：线程存活时长大单位，结合上个参数使用
- threadFactory：线程池创建线程的工厂
- rejectedExecutionHandler：任务拒绝处理器。两种情况会拒绝处理任务：(1)当线程数已经达到maxPoolSize，切队列已满，会拒绝新任务。(2)当线程池被调用shutdown()后，会等待线程池里的任务执行完毕，再shutdown。如果在调用shutdown()和线程池真正shutdown之间提交任务，会拒绝新任务。线程池会调用rejectedExecutionHandler来处理这个任务。如果没有设置默认是AbortPolicy，会抛出异常。ThreadPoolExecutor类有几个内部实现类来处理这类情况：(1)AbortPolicy  丢弃任务，抛运行时异常。(2)CallerRunsPolicy 执行任务。(3)DiscardPolicy  忽视，什么都不会发生。(4)DiscardOldestPolicy  从队列中踢出最先进入队列（最后一个执行）的任务。实现RejectedExecutionHandler接口，也可自定义处理器。

## 线程池有哪些状态

1. RUNNING：这是最正常的状态，接受新的任务，处理等待队列中的任务。线程池的初始化状态是RUNNING。线程池被一旦被创建，就处于RUNNING状态，并且线程池中的任务数为0
2. SHUTDOWN：不接受新的任务提交，但是会继续处理等待队列中的任务。调用线程池的shutdown()方法时，线程池由RUNNING -> SHUTDOWN。
3. STOP：不接受新的任务提交，不再处理等待队列中的任务，中断正在执行任务的线程。调用线程池的shutdownNow()方法时，线程池由(RUNNING or SHUTDOWN ) -> STOP。
4. TIDYING：所有的任务都销毁了，workCount 为 0，线程池的状态在转换为 TIDYING 状态时，会执行钩子方法 terminated()。因为terminated()在ThreadPoolExecutor类中是空的，所以用户想在线程池变为TIDYING时进行相应的处理；可以通过重载terminated()函数来实现。当线程池在SHUTDOWN状态下，阻塞队列为空并且线程池中执行的任务也为空时，就会由 SHUTDOWN -> TIDYING。当线程池在STOP状态下，线程池中执行的任务为空时，就会由STOP -> TIDYING。
5. TERMINATED：线程池处在TIDYING状态时，执行完terminated()之后，就会由 TIDYING -> TERMINATED。

## 线程池的submit和execute方法区别

线程池中的execute方法大家都不陌生，即开启线程执行池中的任务。还有一个方法submit也可以做到，它的功能是提交指定的任务去执行并且返回Future对象，即执行的结果。

- 接收参数不一样：execute() 参数 Runnable ；submit() 参数 (Runnable) 或 (Runnable 和 结果 T) 或 (Callable)
- 返回值不一样：execute() 没有返回值；而 submit() 有返回值
- submit() 的返回值 Future 调用get方法时，可以捕获处理异常

## java 程序中怎么保证多线程的运行安全

保证三要素：

- 原子性：一个或者多个操作在 CPU 执行的过程中不被中断的特性
- 可见性：一个线程对共享变量的修改，另外一个线程能够立刻看到
- 有序性：程序执行的顺序按照代码的先后顺序执行

## 多线程锁的升级原理是什么

锁的级别：无锁->偏向锁->轻量级锁->重量级锁
锁分级别的原因：没有优化前，sychroniezed是重量级锁（悲观锁），使用wait、notify、notifyAll来切换线程状态非常消耗系统资源，线程的挂起和唤醒间隔很短暂，这样很浪费资源，影响性能。所以JVM对sychronized关键字进行了优化，把锁分为无锁、偏向锁、轻量级锁、重量级锁

- 无锁：没有对资源进行锁定，所有的线程都能访问并修改同一个资源，但同时只有一个线程能修改成功，其它修改失败的线程会不断重试直到修改成功。
- 偏向锁：对象的代码一直被同一线程执行，不存在多个线程竞争，该线程在后续执行中自动获取锁，降低获取锁带来的性能开销。偏向锁，指的是偏向第一个加锁线程，该线程是不会主动释放偏向锁的，只有当其他线程尝试竞争偏向锁才会被释放。偏向锁的撤销，需要在某个时间点上没有字节码正在执行时，先暂停偏向锁的线程，然后判断锁对象是否处于被锁定状态，如果线程不处于活动状态，则将对象头设置成无锁状态，并撤销偏向锁。如果线程处于活动状态，升级为轻量级锁的状态。
- 轻量级锁：轻量级锁是指当锁是偏向锁的时候，被第二个线程B访问，此时偏向锁就会升级为轻量级锁，线程B会通过自旋的形式尝试获取锁，线程不会阻塞，从未提升性能。当前只有一个等待线程，则该线程将通过自旋进行等待。但是当自旋超过一定次数时，轻量级锁边会升级为重量级锁，当一个线程已持有锁，另一个线程在自旋，而此时第三个线程来访时，轻量级锁也会升级为重量级锁。（自旋（spinlock）是指当一个线程获取锁的时候，如果锁已经被其它线程获取，那么该线程将循环等待，然后不断的判断锁是否能够被成功获取，直到获取到锁才会退出循环。）
- 重量级锁：指当有一个线程获取锁之后，其余所有等待获取该锁的线程都会处于阻塞状态。重量级锁通过对象内部的监听器（monitor）实现，而其中monitor的本质是依赖于底层操作系统的Mutex Lock实现，操作系统实现线程之间的切换需要从用户态切换到内核态，切换成本非常高。

## ThreadLocal是什么？有哪些使用场景？

ThreadLocal 是线程本地存储，在每个线程中都创建了一个 ThreadLocalMap对象，每个线程可以访问自己内部 ThreadLocalMap 对象内的 value。

经典的使用场景是为每个线程分配一个 JDBC 连接Connection。这样就可以保证每个线程的都在各自的 Connection上进行数据库的操作，不会出现 A 线程关了 B线程正在使用的 Connection； 还有Session 管理 等问题。

## synchronized底层实现原理

jvm基于进入和退出Monitor对象来实现方法同步和代码块同步。
方法级的同步是隐式，即无需通过字节码指令来控制的，它实现在方法调用和返回操作之中。JVM可以从方法常量池中的方法表结构(method_info Structure) 中的 ACC_SYNCHRONIZED 访问标志区分一个方法是否同步方法。当方法调用时，调用指令将会 检查方法的 ACC_SYNCHRONIZED 访问标志是否被设置，如果设置了，执行线程将先持有monitor（虚拟机规范中用的是管程一词）， 然后再执行方法，最后再方法完成(无论是正常完成还是非正常完成)时释放monitor。
代码块的同步是利用monitorenter和monitorexit这两个字节码指令。它们分别位于同步代码块的开始和结束位置。当jvm执行到monitorenter指令时，当前线程试图获取monitor对象的所有权，如果未加锁或者已经被当前线程所持有，就把锁的计数器+1；当执行monitorexit指令时，锁计数器-1；当锁计数器为0时，该锁就被释放了。如果获取monitor对象失败，该线程则会进入阻塞状态，直到其他线程释放锁。
这里要注意：

synchronized是可重入的，所以不会自己把，自己锁死
synchronized锁一旦被一个线程持有，其他试图获取该锁的线程将被阻塞

## Executor拒绝策略

1. AbortPolicy:为java线程池默认的阻塞策略，不执行此任务，而且直接抛出一个运行时异常，切记ThreadPoolExecutor.execute需要try 
   catch，否则程序会直接退出.
2. DiscardPolicy:直接抛弃，任务不执行，空方法
3. DiscardOldestPolicy:从队列里面抛弃head的一个任务，并再次execute 此task。
4. CallerRunsPolicy:在调用execute的线程里面执行此command，会阻塞入
5. 用户自定义拒绝策略:实现RejectedExecutionHandler，并自己定义策略模式

## CopyOnWriteArrayList

CopyOnWriteArrayList :  写时加锁，当添加一个元素的时候，将原来的容器进行copy，复制出一个新的容器，然后在新的容器里面写，写完之后再将原容器的引用指向新的容器，而读的时候是读旧容器的数据，所以可以进行并发的读，但这是一种弱一致性的策略。  

 使用场景：CopyOnWriteArrayList适合使用在读操作远远大于写操作的场景里，比如缓存。

## java里的阻塞队列

ArrayBlockingQueue ：一个由数组结构组成的有界阻塞队列。   LinkedBlockingQueue ：一个由链表结构组成的有界阻塞队列。 
 PriorityBlockingQueue ：一个支持优先级排序的无界阻塞队列。   DelayQueue：一个使用优先级队列实现的无界阻塞队列。   SynchronousQueue：一个不存储元素的阻塞队列。   LinkedTransferQueue：一个由链表结构组成的无界阻塞队列。   LinkedBlockingDeque：一个由链表结构组成的双向阻塞队列。

## 说说你对Fork/Join的并行计算框架的了解

这就是分治，也就是把一个复杂的问题分解成相似的子问题，然后子问题再分子问题，直到问题分的很简单不必再划分了。然后层层返回问题的结果，最终上报给王！

分治分为两个阶段：

1. 分解任务，把任务分解为一个个小人物直至任务可以简单的计算返回结果
2. 合并结果，把每个小任务的结果合并返回得到最终的结果。

Fork/Join框架主要包含两部分：ForkJoinPool、ForkJoinTask。

- ForkJoinPool：就是治理分治任务的线程池。它和在之前的文章提到ThreadPoolExecutor线程池，共同点都是消费者-生产者模式的实现，但是有一些不同。`ThreadPoolExecuto`r的线程池是只有一个任务队列的，而`ForkJoinPool`有多个任务队列。通过`ForkJoinPool`的`invoke`或`submit`或`execute`提交任务的时候会根据一定规则分配给不同的任务队列，并且任务队列的双端队列。为啥要双端队列呢？因为ForkJoinPool有一个机制，当某个工作线程对应消费的任务队列空闲的时候它会去别的忙的任务队列的尾部分担(stealing)任务过来执行(好伙伴啊)。然后那个忙的任务队列还是头部出任务给它对应的工作线程消费。这样双端就井然有序，不会有任务争抢的情况。（execute 异步，无返回结果 invoke 同步，有返回结果 （会阻塞） submit 异步，有返回结果 （Future））
- ForkJoinTask：这就是分治任务啦，就等同于我们平日用的Runnable。它是一个抽象类，核心方法就是fork和join。fork方法用来异步执行一个子任务，join方法会阻塞当前线程等待子任务返回。ForkJoinTask有两个子类分别是RecursiveAction和RecursiveTask。这两个子类也是抽象类，都是通过递归来执行分治任务。每个子类都有抽象方法compute差别就在于RecursiveAction的没有返回值而RecursiveTask有返回值。

## 栅栏，闭锁，信号量

闭锁（CountDownLatch）：闭锁通常使用一个计数器，表示需要等待事件的数量。**CountDownLatch**是最常见灵活的闭锁实现。首先新建CountDownLatch对象，申请一个计数器初始值。调用countDown()函数会递减闭锁值，表示一个事件发生了；调用await()函数会查询该计数器，若为0表示所有等待事件都已发生，若不为0则一直阻塞到计数器为0(除非等待中的线程中断，或者超过等待时间)。

信号量(Semaphore)：计数信号量用于控制某个特定资源的操作数量。可以想象Semaphore计数器为虚拟许可(permit)，虚拟许可的数量通过初始化构造函数指定。

1. 线程在执行操作时，先调用acquire( )函数获得许可(计数器自减1)；如果此时许可没有剩余(计数器为0)则阻塞到有许可为止（除非被中断或者操作过时）。
2. 线程执行完后，调用release( )函数释放许可(计数器自增1)；
3. 初始值为1的信号量称作二值信号量(只有可能0、1)，可以用作不可重入的“互斥锁”。

栅栏 (CyclicBarrier)：栅栏和闭锁非常相似，唯一的区别在于，**闭锁主要用于等待事件**(线程也可)，**栅栏主要用于等待其他线程**。此外，**闭锁通常有先验已知**多少数量的线程参与事件(毕竟从n阻塞减至0)；**栅栏通常不知道具体参与线程数量**，但可以设置一定数量即破坏栅栏放行(从0阻塞加至n)。（在栅栏未破坏前(未放行前)，await()调用将阻塞已经到达栅栏的线程；栅栏破坏时，所有await调用都将终止并抛出BrokenBarrierException；栅栏破坏后，await调用会给通过栅栏的线程分配一个索引号；）

## 线程间通信方式

1. 管道：管道是一种文件，在一端写入，另一端读出，数据流向是单向的。管道中的数据是一次性的，一旦被读数据就会被抛弃。当管道输出流write()导致管道缓冲区变满时，管道的write()调用将默认的被阻塞，等待缓冲区的数据被读取。同样的读进程也可能工作得比写进程块。当所有当前进程数据被读取时，管道变空。当这种情况发生时，一个随后的read()调用将默认被阻塞，等待缓冲区数据，这解决了read()调用返回文件结束的问题。
2. 信号量：上面有解释，主要用于线程之间同步
3. 消息队列：各种MQ？？？
4. 信号：
5. 共享内存：
6. 套接字：为两台计算机之间的通信提供方法

## 什么是 Busy spin？我们为什么要使用它？

Busy spin 是一种在不释放 CPU 的基础上等待事件的技术。它经常用于避免丢失 CPU 缓存中的数据（如果线程先暂停，之后在其他CPU上运行就会丢失）。所以，如果你的工作要求低延迟，并且你的线程目前没有任何顺序，这样你就可以通过循环检测队列中的新消息来代替调用 sleep() 或 wait() 方法。它唯一的好处就是你只需等待很短的时间，如几微秒或几纳秒。LMAX 分布式框架是一个高性能线程间通信的库，该库有一个 BusySpinWaitStrategy 类就是基于这个概念实现的，使用 busy spin 循环 EventProcessors 等待屏障。

## Java 中怎么获取一份线程 dump 文件

在 Linux 下，你可以通过命令 kill -3 PID （Java 进程的进程 ID）来获取 Java 应用的 dump 文件。在 Windows 下，你可以按下 Ctrl + Break 来获取。这样 JVM 就会将线程的 dump 文件打印到标准输出或错误文件中，它可能打印在控制台或者日志文件中，具体位置依赖应用的配置。如果你使用Tomcat。

## 什么是线程局部变量？

线程局部变量是局限于线程内部的变量，属于线程自身所有，不在多个线程间共享。Java 提供 ThreadLocal 类来支持线程局部变量，是一种实现线程安全的方式。但是在管理环境下（如 web 服务器）使用线程局部变量的时候要特别小心，在这种情况下，工作线程的生命周期比任何应用变量的生命周期都要长。任何线程局部变量一旦在工作完成后没有释放，Java 应用就存在内存泄露的风险。

## Java 中，编写多线程程序的时候你会遵循哪些最佳实践？

1. 给线程命名，这样可以帮助调试
2. 最小化同步的范围，而不是将整个方法同步，只对关键部分做同步
3. 如果可以，更偏向于使用 volatile 而不是 synchronized。
4. 使用更高层次的并发工具，而不是使用 wait() 和 notify() 来实现线程间通信，如 BlockingQueue，CountDownLatch 及 Semeaphore。
5. 优先使用并发集合，而不是对集合进行同步。并发集合提供更好的可扩展性。
6. 优先使用线程池

# Java WEB

## BIO、NIO、AIO 有什么区别？

BIO：Block IO同步阻塞式IO，并发处理能力低。线程发起IO请求，不管内核是否准备好IO操作，从发起请求，线程一直阻塞，直到操作完成

NIO：Non IO同步非阻塞IO，客户端和服务端通过Channel（通道）通讯，实现了多路复用。线程发起IO请求，立即返回；内核在做好IO操作的准备后，通过调用注册的回调函数通知线程做IO操作，线程开始阻塞，直到操作完成。

AIO：Asynchronous IO异步非阻塞IO。线程发起IO请求，立即返回；内存做好IO操作的准备之后，做IO操作，直到操作完成或者失败，通过调用注册的回调函数通知线程做的IO操作完成或失败。

- BIO是一个连接一个线程。
- NIO是一个请求一个线程。
- AIO是一个有效请求一个线程。

## Java 中怎么创建 ByteBuffer？

| allocate(int capacity)                      | 从堆空间中分配一个容量大小为capacity的byte数组作为缓冲区的byte数据存储器 |
| ------------------------------------------- | ------------------------------------------------------------ |
| allocateDirect(int capacity)                | 是不使用JVM堆栈而是通过操作系统来创建内存块用作缓冲区，它与当前操作系统能够更好的耦合，因此能进一步提高I/O操作速度。但是分配直接缓冲区的系统开销很大，因此只有在缓冲区较大并长期存在，或者需要经常重用时，才使用这种缓冲区 |
| wrap(byte[] array)                          | 这个缓冲区的数据会存放在byte数组中，bytes数组或buff缓冲区任何一方中数据的改动都会影响另一方。其实ByteBuffer底层本来就有一个bytes数组负责来保存buffer缓冲区中的数据，通过allocate方法系统会帮你构造一个byte数组 |
| wrap(byte[] array,  int offset, int length) | 在上一个方法的基础上可以指定偏移量和长度，这个offset也就是包装后byteBuffer的position，而length呢就是limit-position的大小，从而我们可以得到limit的位置为length+position(offset) |

## buffer常用方法

| limit(), limit(10)等                    | 其中读取和设置这4个属性的方法的命名和jQuery中的val(),val(10)类似，一个负责get，一个负责set |
| --------------------------------------- | ------------------------------------------------------------ |
| reset()                                 | 把position设置成mark的值，相当于之前做过一个标记，现在要退回到之前标记的地方 |
| clear()                                 | position = 0;limit = capacity;mark = -1; 有点初始化的味道，但是并不影响底层byte数组的内容 |
| flip()                                  | limit = position;position = 0;mark = -1; 翻转，也就是让flip之后的position到limit这块区域变成之前的0到position这块，翻转就是将一个处于存数据状态的缓冲区变为一个处于准备取数据的状态 |
| rewind()                                | 把position设为0，mark设为-1，不改变limit的值                 |
| remaining()                             | return limit - position;返回limit和position之间相对位置差    |
| hasRemaining()                          | return position < limit返回是否还有未读内容                  |
| compact()                               | 把从position到limit中的内容移到0到limit-position的区域内，position和limit的取值也分别变成limit-position、capacity。如果先将positon设置到limit，再compact，那么相当于clear() |
| get()                                   | 相对读，从position位置读取一个byte，并将position+1，为下次读写作准备 |
| get(int index)                          | 绝对读，读取byteBuffer底层的bytes中下标为index的byte，不改变position |
| get(byte[] dst, int offset, int length) | 从position位置开始相对读，读length个byte，并写入dst下标从offset到offset+length的区域 |
| put(byte b)                             | 相对写，向position的位置写入一个byte，并将postion+1，为下次读写作准备 |
| put(int index, byte b)                  | 绝对写，向byteBuffer底层的bytes中下标为index的位置插入byte b，不改变position |
| put(ByteBuffer src)                     | 用相对写，把src中可读的部分（也就是position到limit）写入此byteBuffer |
| put(byte[] src, int offset, int length) | 从src数组中的offset到offset+length区域读取数据并使用相对写写入此byteBuffer |

## ByteBuffer 中的字节序是什么？

java字节序：JAVA虚拟机中多字节类型数据的存放顺序，JAVA字节序也是BIG-ENDIAN。

字节序分为两种：

- BIG-ENDIAN—-大字节序：BIG-ENDIAN就是最低地址存放最高有效字节。
- LITTLE-ENDIAN—-小字节序：LITTLE-ENDIAN是最低地址存放最低有效字节。

ByteBuffer类中的order(ByteOrder bo) 方法可以设置 ByteBuffer 的字节序。

其中的ByteOrder是枚举：
ByteOrder BIG_ENDIAN 代表大字节序的 ByteOrder 。ByteOrder LITTLE_ENDIAN 代表小字节序的 ByteOrder 。ByteOrder nativeOrder() 返回当前硬件平台的字节序。

## Java 采用的是大端还是小端？

Java采用的是大端

## Java 中，ByteBuffer 与 StringBuffer有什么区别

两者没什么关系。

ByteBuffer是Java NIO中的buffer。

而StringBuffer是字符串连接用的buffer类

## Java 中，直接缓冲区与非直接缓冲器有什么区别

非直接缓冲区：将缓冲区建立在JVM的内存中，可以通过allocate()创建

直接缓冲区：将缓冲区建立在物理内存中，可以提供效率。可以通过allocateDirect()分配直接缓冲区。

## Java 中的内存映射缓存区是什么

MappedByteBuffer是java nio引入的文件内存映射方案，读写性能极高。NIO最主要的就是实现了对异步操作的支持。其中一种通过把一个套接字通道(SocketChannel)注册到一个选择器(Selector)中，不时调用后者的选择(select)方法就能返回满足的选择键(SelectionKey)，键中包含了SOCKET事件信息。这就是select模型。

SocketChannel的读写是通过一个类叫ByteBuffer(java.nio.ByteBuffer)来操作的.这个类本身的设计是不错的，比直接操作byte[]方便多了. ByteBuffer有两种模式:直接/间接.间接模式最典型(也只有这么一种)的就是HeapByteBuffer，即操作堆内存 (byte[]).但是内存毕竟有限，如果我要发送一个1G的文件怎么办?不可能真的去分配1G的内存；这时就必须使用"直接"模式，即 MappedByteBuffer 文件映射.

MappedByteBuffer 将文件直接映射到内存（这里的内存指的是虚拟内存，并不是物理内存）。通常，可以映射整个文件，如果文件比较大的话可以分段进行映射，只要指定文件的那个部分就可以。

FileChannel提供了map方法来把文件影射为内存映像文件： MappedByteBuffer map(int mode，long position，long size); 可以把文件的从position开始的size大小的区域映射为内存映像文件，mode指出了 可访问该内存映像文件的三种方式：

- READ_ONLY：试图修改得到的缓冲区将导致抛出 ReadOnlyBufferException.(MapMode.READ_ONLY)
- READ_WRITE： 对得到的缓冲区的更改最终将传播到文件；该更改对映射到同一文件的其他程序不一定是可见的。 (MapMode.READ_WRITE)
- PRIVATE： 对得到的缓冲区的更改不会传播到文件，并且该更改对映射到同一文件的其他程序也不是可见的；相反，会创建缓冲区已修改部分的专用副本。 (MapMode.PRIVATE)

三种方法：

- force()缓冲区是READ_WRITE模式下，此方法对缓冲区内容的修改强行写入文件
- load()将缓冲区的内容载入内存，并返回该缓冲区的引用
- isLoaded()如果缓冲区的内容在物理内存中，则返回真，否则返回假

三个特性：调用信道的map()方法后，即可将文件的某一部分或全部映射到内存中，映射内存缓冲区是个直接缓冲区，继承自ByteBuffer，但相对于ByteBuffer，它有更多的优点：

- 读取快
- 写入快
- 随时随地写入

## 说出 5 条 IO 的最佳实践

1. 使用有缓冲区的 IO 类，而不要单独读取字节或字符
2. 使用 NIO 和 NIO2
3. 在 finally 块中关闭流，或者使用 try-with-resource 语句。
4. 使用内存映射文件获取更快的 IO。

## servlet的生命周期

首先加载servlet的class，实例化servlet，然后初始化servlet调用init()的方法，接着调用服务的service的方法处理doGet和doPost方法，最后是我的还有容器关闭时候调用destroy 销毁方法

## jsp和servlet有什么区别

Servlet：

- 一种服务器端的Java应用程序
- 由 Web 容器加载和管理
- 用于生成动态 Web 内容
- 负责处理客户端请求

Jsp:

- 是 Servlet 的扩展，本质上还是 Servlet
- 每个 Jsp 页面就是一个 Servlet 实例
- Jsp 页面会被 Web 容器编译成 Servlet，Servlet 再负责响应用户请求

区别:

- Servlet 适合动态输出 Web 数据和业务逻辑处理，对于 html页面内容的修改非常不方便；Jsp 是在 Html 代码中嵌入 Java 代码，适合页面的显示
- 内置对象不同，获取内置对象的方式不同

## Jsp有哪些内置对象？作用分别是什么？

Page，pageContext，request，response，session，application，out，config，exception
Page指的是JSP被翻译成Servlet的对象的引用.
pageContext对象可以用来获得其他8个内置对象,还可以作为JSP的域范围对象使用.pageContext中存的值是当前的页面的作用范围》
request代表的是请求对象,可以用于获得客户机的信息,也可以作为域对象来使用，使用request保存的数据在一次请求范围内有效。
Session代表的是一次会话，可以用于保存用户的私有的信息,也可以作为域对象使用，使用session保存的数据在一次会话范围有效
Application：代表整个应用范围,使用这个对象保存的数据在整个web应用中都有效。
Response是响应对象,代表的是从服务器向浏览器响应数据.
Out:JSPWriter是用于向页面输出内容的对象
Config：指的是ServletConfig用于JSP翻译成Servlet后 获得Servlet的配置的对象.
Exception:在页面中设置isErrorPage=”true”，即可使用，是Throwable的引用.用来获得页面的错误信息。

## Jsp的四种作用域

page:代表页面上下文，范围是一个页面及其静态包含的内容
request:代表请求上下文，范围是一个请求涉及的几个页面，通常是一个页面和其包含的内容以及forward动作转向的页面
session:代表客户的一次会话上下文，范围是一个用户在会话有效期内多次请求所涉及的页面
application:全局作用域，代表Web应用程序上下文，范围是整个Web应用中所有请求所涉及的页面

## session与cookie的区别

1. Cookie以文本文件格式存储在浏览器中，而session存储在服务端它存储了限制数据量。
2. cookie的存储限制了数据量，只允许4KB，而session是无限量的
3. 我们可以轻松访问cookie值但是我们无法轻松访问会话值，因此它更安全
4. 设置cookie时间可以使cookie过期。但是使用session-destory（），我们将会销毁会话。

## Session实现原理

1. 当用户第一次访问时会创建一个session对象，这个session对象有一个唯一的ID SESSIONID。
2. 把SESSIONID作为cookie的值发送给浏览器保存
3. 第二次访问的时候，浏览器使用存有SESSIONID的cookie访问服务器
4. 服务器根据SESSIONID在服务器内存中搜索是否存放对应编号的session对象
5. 如果有找到了对应ID的session则返回该session，否则返回null或者创建新的session对象走流程1

## 如何防止SQL注入

使用采用预编译语句集

## 什么是 XSS 攻击，如何避免

跨站脚本攻击。XSS攻击涉及到三方：攻击者，用户，web server。用户是通过浏览器来访问web server上的网页，XSS攻击就是攻击者通过各种办法，在用户访问的网页中插入自己的脚本，让其在用户访问网页时在其浏览器中进行执行。攻击者通过插入的脚本的执行，来获得用户的信息，比如cookie，发送到攻击者自己的网站(跨站了)。XSS可以分为反射型XSS和持久性XSS，还有DOM Based XSS。(一句话，XSS就是在用户的浏览器中执行攻击者自己定制的脚本。)

XSS防御的总体思路是：对输入(和URL参数)进行过滤，对输出进行编码，白名单和黑名单结合。也就是对提交的所有内容进行过滤，对url中的参数进行过滤，过滤掉会导致脚本执行的相关内容；然后对动态输出到页面的内容进行html编码，使脚本无法在浏览器中执行。

使用 OWASP AntiSamy Project 和 OWASP ESAPI for Java 来防御 XSS(还有客户端的esapi4js: esapi.js)。AntiSamy 提供了 XSS Filter 的实现，而 ESAPI 则提供了对输出进行编码的实现。

## 什么是 CSRF 攻击，如何避免？

CSRF：Cross Site Request Forgery（跨站点请求伪造）。CSRF 攻击者在用户已经登录目标网站之后，诱使用户访问一个攻击页面，利用目标网站对用户的信任，以用户身份发起伪造的用户请求，达到攻击目的。

防御：

- CSRF 漏洞检测的工具，如 CSRFTester、CSRF Request Builder...
- 验证 HTTP Referer 字段
- 添加并验证 token
- 添加自定义 http 请求头
- 敏感操作添加验证码
- 使用 post 请求

# 异常

## throw 和 throws 的区别

throw是抛出一个异常，throws是生命方法可能抛出的异常。

## try-catch-finally 中哪个部分可以省略？

catch和finally语句块可以省略其中之一

## 当try、catch中有return时，finally中的代码会执行么？

会，return的值是不会改变，但是实在finally执行之后return。