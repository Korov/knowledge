# 自我介绍

## 介绍自己

各位面试官好，我叫朱磊，毕业于广西大学信息管理与信息系统专业，2018年7月至2019年11月就职于中软国际，职能是作为SmartNDP WEB项目中链路模块的主要负责人，主要职责是保证链路模块整体功能的正常迭代，以及链路整个模块的框架的可维护性。技术方面熟悉java，mysql，spring，redis，git，maven。

## 自己的项目经验

# Java

## HashCode的作用

hashcode是根据对象内容生成的一串编码，这串编码的作用就是辅助判断对象是否相等。

## Java基类Object类

所有类的父类，其中方法有equal，hashcode，toString ,clone，wait和notify，notifyAll。

## final和static关键字的作用

final修饰类中的属性或变量，该属性或变量必须在构造函数执行完毕之前完成初始化。final修饰的变量的值不可以改变。final修饰的方法可以被继承不可以被重写。final修饰的类不可以被继承。

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
3. 接口只能声明方法，而抽象类中可以声明方法也可以实现方法
4. 抽象类中的抽象方法必须由子类全部实现，否则这个子类也是抽象类。接口中的方法必须由实现类全部实现，否则这个类就是一个抽象类
5. 接口里的方法只能声明，不能有具体的实现，这说明接口是设计的结果，抽象类时重构的结果

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

**同步容器**：Vector，Stack，HashTable，Collections.synchronized方法生成

**并发容器**：

- ConcurrentHashMap：线程安全的HashMap实现
- CopyOnWriteArrayList：线程安全且在读操作时无锁的ArrayList
- CopyOnWriteArraySet：基于CopyOnWriteArrayList，不添加重复元素
- ArrayBlockingQueue：基于数组，先进先出，线程安全，可实现制定时间的阻塞读写，并且数量可以限制
- LinkedBlockingQueue：基于链表实现，读写各用一把锁，在高并发读写操作都多的情况下性能优于ArrayBlockingQueue。

## HashMap和HashTable有什么区别

**线程安全性不同**：Hashtable是线程安全的，HashMap不是线程安全的

**继承的父类不同**：HashMap继承AbstractMap类，Hashtable继承Dictionary类，但是他们都实现了Map、Cloneable和Serializable接口。

**对外提供的接口不同**：Hashtable比HashMap多提供了elments()和contains()两个方法，elments() 方法继承自Hashtable的父类Dictionnary。elements() 方法用于返回此Hashtable中的value的枚举。contains()方法判断该Hashtable是否包含传入的value。它的作用与containsValue()一致。事实上，contansValue() 就只是调用了一下contains() 方法。

**对null key和null value的支持不同**：Hashtable既不支持Null key也不支持Null value。HashMap可以有一个null key，多个null value。

**遍历方式不同**：Hashtable、HashMap都使用了 Iterator。而由于历史原因，Hashtable还使用了Enumeration的方式 。

**初始容量和每次扩充大小不同**：Hashtable默认的初始大小是11,之后每次扩充容量变为原来的2n+1，。HashMap默认初始大小为16,每次扩充容量变为原来的2倍。创建时，如果给定了容量初始值，那么Hashtable会直接使用你给定的大小，而HashMap会将其扩充为2的幂次方大小。

**计算hash值的方法不同**：Hashtable直接使用对象的hashCode，HashMap会重新根据hashcode计算hash值。

## HashMap的实现

HashMap是基于拉链法实现的一个散列表，内部由数组、链表和红黑树实现。

 ![img](picture\1216080-20180412104426360-1425516709.png)

HashMap的工作原理：

1. 数组的初始容量为16，而容量是以2的次方扩充的，一是为了提高性能使用足够大的数组，二是为了能使用位运算代替取模预算(据说提升了5~8倍)
2. 数组是否需要扩充是通过负载因子判断的，如果当前元素个数为数组容量的0.75时，就会扩充数组。这个0.75就是默认的负载因子，可由构造传入。我们也可以设置大于1的负载因子，这样数组就不会扩充，牺牲性能，节省内存。
3. 为了解决碰撞，数组中的元素是单向链表类型。当链表长度到达一个阈值时（7或8），会将链表转换成红黑树提高性能。而当链表长度缩小到另一个阈值时（6），又会将红黑树转换回单向链表提高性能，这里是一个平衡点。
4. 对于第三点补充说明，检查链表长度转换成红黑树之前，还会先检测当前数组数组是否到达一个阈值（64），如果没有到达这个容量，会放弃转换，先去扩充数组。所以上面也说了链表长度的阈值是7或8，因为会有一次放弃转换的操作。

**数组的索引bucket**：HashMap采用hash算法来决定集合中元素的存储位置，每当系统初始化HashMap时，会创建一个为`capacity`的数组，这个数组里面可以存储元素的位置被成为`桶(bucket)`, 每个`bucket`都有其指定索引。可以根据该索引快速访问存储的元素。

### 存在有序的HashMap吗？

TreeMap和LinkedHashMap。TreeMap默认是按照key值升序排序的，用红黑树作为实现的，可以使用比较器改变排序。LinkedHashMap是按照put的顺序排序的。

### 你有更好的实现有序Map的方法吗？

B+树

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

负载因子，哈希值。

### HashMap为什么只允许一个key为null

如果key为null，会放在第一个bucket位置，而且是在链表最前面。

## 如何实现数组和 List 之间的转换

1. 遍历数组然后add进list
2. 使用Arrays.asList
3. Collections.addAll()
4. 使用Stream中的Collector收集器

## ArrayList和Vector区别

两个都实现了List接口，继承了AbstractList类。Vector是线程安全的。都使用数组实现。vector的扩充为2n，ArrayList扩充为原来的1.5n。

## Queue中的方法

**offer()和add()的区别**：add()和offer()都是向队列中添加一个元素。但是如果想在一个满的队列中加入一个新元素，调用 add() 方法就会抛出一个 unchecked 异常，而调用 offer() 方法会返回 false。

**peek()和element()的区别**：peek()和element()都将在**不移除**的情况下返回队头，但是peek()方法在队列为空时返回null，调用element()方法会抛出NoSuchElementException异常。

**poll()和remove()的区别**：poll()和remove()都将**移除**并且返回对头，但是在poll()在队列为空时返回null，而remove()会抛出NoSuchElementException异常。

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

## 什么是反射

是指程序在运行状态中，对于任意一个类，都可以知道这个类的所有属性和方法；对于任意一个对象，都能够调用他的任意方法和属性。
其原理是通过类的全限定名加载class文件，然后通过class文件创建一个完整的对象。

## 什么是 java 序列化？什么情况下需要序列化？

序列化：将Java对象转换成字节流。反序列化：将字节流转换成Java对象的过程。

当Java对象需要在网络上传输或者持久化存储到文件中时，就需要使用序列化

注意：

1. 某个类可以被序列化，则其子类也可以被序列化
2. 声明为 static 和 transient 的成员变量，不能被序列化。static 成员变量是描述类级别的属性，transient 表示临时数据
3. 反序列化读取序列化对象的顺序要保持一致

## 什么是动态代理？动态代理是如何实现的？动态代理有哪些应用？

动态代理：当想要给实现了某个接口的类中的方法，加一些额外的处理。比如说加日志，加事务等。可以给这个类创建一个代理，故名思议就是创建一个新的类，这个类不仅包含原来类方法的功能，而且还在原来的基础上添加了额外处理的新类。这个代理类并不是定义好的，是动态生成的。具有解耦意义，灵活，扩展性强。
动态代理实现：首先必须定义一个接口，还要有一个InvocationHandler(将实现接口的类的对象传递给它)处理类。再有一个工具类Proxy(习惯性将其称为代理类，因为调用他的newInstance()可以产生代理对象,其实他只是一个产生代理对象的工具类）。利用到InvocationHandler，拼接代理类源码，将其编译生成代理类的二进制码，利用加载器加载，并将其实例化产生代理对象，最后返回。
动态代理的应用：Spring的AOP，加事务，加权限，加日志。

## 如何实现克隆

实现Cloneable接口，并重写object类中的clone方法可以实现浅克隆。
实现Serializable，通过对象的序列化和反序列化实现正真的深克隆。

# Java并发

## 并发和并行的区别

并发：同一时间段，多个任务都在执行，单位时间内不一定同时执行;并行：单位时间内，多个任务同时执行。

## 线程与进程之间的不同

进程是资源分配的最小单位，线程是程序执行的最小单位，每个进程有自己的独立地址空间，线程共享进程中的数据。

## 创建线程有哪几种方式

继承Thread类创建线程，实现Runnable接口创建线程，实现Callable接口创建线程

## 说一下 runnable 和 callable 有什么区别

相同：两者都是接口，两者都可用来编写多线程程序，都通过Thread.start()启动线程
不同：实现Callable接口的任务线程能返回执行结果，实现Runnable接口的任务线程不能返回结果；Callable接口的call()方法允许抛出异常；Runnable的run()方法异常只能在内部消化，不能往上继续抛。
注：Callalbe接口支持返回执行结果，需要调用FutureTask.get()得到，此方法会阻塞主进程的继续往下执行，如果不调用不会阻塞

## Thread类中的start和run方法有什么区别

start被用来启动新线程并且在内部调用了run，run不能。start不能被重复调用，run可以。

## 在多线程中，什么是上下文切换

存储和恢复CPU状态的过程，它使得线程能够从中断点恢复。

## volatile变量

一种实现数据共享的轻量锁，被volatile修饰的变量不会被重排序，并且虚拟机会保证你从主内存中获取的变量是最新的。

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
- volatile仅能使用在变量级别；synchronized则可以使用在变量、方法、和类级别的
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
- synchronized可以给类、方法、代码块加锁，而lock只能给代码块加锁。

## 说一下 atomic 的原理

自旋 + CAS（乐观锁）。在这个过程中，通过compareAndSwapInt比较更新value值，如果更新失败，重新获取旧值，然后更新

## 四种常见线程池以及ThreadPoolExecutor

jdk1.8线程池种类：newFixedThreadPool（定长线程池），newCachedThreadPool（可缓存线程池），newScheduledThreadPool（定长线程池，可执行周期性任务），newSingleThreadExecutor（单线程，线程池），newSingleThreadScheduledExecutor（单线程可执行周期性任务线程池），newWorkStealingPool（任务窃取线程池，不保证执行顺序，适合任务耗时差异较大。线程池中有多个线程队列，有的线程队列中有大量的比较耗时的任务堆积，而有的线程队列却是空的，就存在有的线程处于饥饿状态，当一个线程处于饥饿状态时，它就会去其它的线程队列中窃取任务。解决饥饿导致的效率问题）
ThreadPoolExecutor四种线程池就是通过ThreadPoolExecutor此类的构造方法实现的。设置核心线程数量，线程存活时间，线程池可以容纳的最大线程数。线程池中的任务队列三种：SynchronousQueue,LinkedBlockingDeque,ArrayBlockingQueue。

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
3. 信号量：上面有解释，主要用于线程之间同步
4. 消息队列：各种MQ？？？
5. 信号：
6. 共享内存：
7. 套接字：为两台计算机之间的通信提供方法

# Java WEB

## BIO、NIO、AIO 有什么区别？

BIO：Block IO同步阻塞式IO，并发处理能力低。线程发起IO请求，不管内核是否准备好IO操作，从发起请求，线程一直阻塞，直到操作完成

NIO：Non IO同步非阻塞IO，客户端和服务端通过Channel（通道）通讯，实现了多路复用。线程发起IO请求，立即返回；内核在做好IO操作的准备后，通过调用注册的回调函数通知线程做IO操作，线程开始阻塞，直到操作完成。

AIO：Asynchronous IO异步非阻塞IO。线程发起IO请求，立即返回；内存做好IO操作的准备之后，做IO操作，直到操作完成或者失败，通过调用注册的回调函数通知线程做的IO操作完成或失败。

- BIO是一个连接一个线程。
- NIO是一个请求一个线程。
- AIO是一个有效请求一个线程。

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

# JVM

## jvm加载类过程

虚拟机把描述类的数据从Class文件加载到内存，并对数据进行校验、转换解析和初始化，最终形成可以被虚拟机直接使用的Java类型，这就是虚拟机的类加载机制。它的整个生命周期包括：加载（Loading）、验证（Verification）、准备（Preparation）、解析（Resolution）、初始化（Initialization）、使用（Using）和卸载（Unloading）7个阶段。其中验证、准备、解析3个部分统称为连接（Linking）

## GC算法

复制算法，标记-清除算法，标记-整理算法，分代收集算法

## 四种引用

- 强引用：使用最普遍的引用，一般情况下，垃圾回收器绝对不会回收它。内存不足时，抛出OOM。
- 软引用：内存空间足够，垃圾回收器不会回收它。反之，则回收。适用于缓存，而且不会OOM。
- 弱引用：只有当垃圾回收器扫描到弱引用指向的对象时，才会回收它。生命周期比软引用更短。ThreadLocal的key使用了弱引用。
- 虚引用：在任何时候都可能被垃圾回收器回收，必须与引用队列关联使用。

## jvm 的主要组成部分？及其作用？

- 类加载器（ClassLoader）
- 运行时数据区（Runtime Data Area）
- 执行引擎（Execution Engine）
- 本地库接口（Native Interface）

组件的作用： 首先通过类加载器（ClassLoader）会把 Java 代码转换成字节码，运行时数据区（Runtime Data Area）再把字节码加载到内存中，而字节码文件只是 JVM 的一套指令集规范，并不能直接交个底层操作系统去执行，因此需要特定的命令解析器执行引擎（Execution Engine），将字节码翻译成底层系统指令，再交由 CPU 去执行，而这个过程中需要调用其他语言的本地库接口（Native Interface）来实现整个程序的功能

##  jvm 运行时数据区

不同虚拟机的运行时数据区可能略微有所不同，但都会遵从 Java 虚拟机规范， Java 虚拟机规范规定的区域分为以下 5 个部分：

- 程序计数器（Program Counter Register）：当前线程所执行的字节码的行号指示器，字节码解析器的工作是通过改变这个计数器的值，来选取下一条需要执行的字节码指令，分支、循环、跳转、异常处理、线程恢复等基础功能，都需要依赖这个计数器来完成
- Java 虚拟机栈（Java Virtual Machine Stacks）：用于存储局部变量表、操作数栈、动态链接、方法出口等信息
- 本地方法栈（Native Method Stack）：与虚拟机栈的作用是一样的，只不过虚拟机栈是服务 Java 方法的，而本地方法栈是为虚拟机调用 Native 方法服务的
- Java 堆（Java Heap）：Java 虚拟机中内存最大的一块，是被所有线程共享的，几乎所有的对象实例都在这里分配内存
- 方法区（Methed Area）：用于存储已被虚拟机加载的类信息、常量、静态变量、即时编译后的代码等数据

## 说一下堆栈的区别

功能方面：堆是用来存放对象的，栈是用来执行程序的
共享性：堆是线程共享的，栈是线程私有的
空间大小：堆大小远远大于栈

## 队列和栈是什么？有什么区别？

队列和栈都是被用来预存储数据的。

队列允许先进先出检索元素，但也有例外的情况，Deque 接口允许从两端检索元素。

栈和队列很相似，但它运行对元素进行后进先出进行检索

## 什么是双亲委派模型？

在介绍双亲委派模型之前先说下类加载器。对于任意一个类，都需要由加载它的类加载器和这个类本身一同确立在 JVM 中的唯一性，每一个类加载器，都有一个独立的类名称空间。类加载器就是根据指定全限定名称将 class 文件加载到 JVM 内存，然后再转化为 class 对象。

类加载器分类：

启动类加载器（Bootstrap ClassLoader），是虚拟机自身的一部分，用来加载Java_HOME/lib/目录中的，或者被 -Xbootclasspath 参数所指定的路径中并且被虚拟机识别的类库
其他类加载器：
扩展类加载器（Extension ClassLoader）：负责加载\lib\ext目录或Java. ext. dirs系统变量指定的路径中的所有类库
应用程序类加载器（Application ClassLoader）。负责加载用户类路径（classpath）上的指定类库，我们可以直接使用这个类加载器。一般情况，如果我们没有自定义类加载器默认就是用这个加载器
双亲委派模型：如果一个类加载器收到了类加载的请求，它首先不会自己去加载这个类，而是把这个请求委派给父类加载器去完成，每一层的类加载器都是如此，这样所有的加载请求都会被传送到顶层的启动类加载器中，只有当父加载无法完成加载请求（它的搜索范围中没找到所需的类）时，子加载器才会尝试去加载类

## 怎么判断对象是否可以被回收？

一般有两种方法来判断：

- 引用计数器：为每个对象创建一个引用计数，有对象引用时计数器 +1，引用被释放时计数 -1，当计数器为 0 时就可以被回收。它有一个缺点不能解决循环引用的问题
- 可达性分析：从 GC Roots 开始向下搜索，搜索所走过的路径称为引用链。当一个对象到 GC Roots 没有任何引用链相连时，则证明此对象是可以被回收的

## 说一下 jvm 有哪些垃圾回收器？

- Serial：最早的单线程串行垃圾回收器
- Serial Old：Serial 垃圾回收器的老年版本，同样也是单线程的，可以作为 CMS 垃圾回收器的备选预案
- ParNew：是 Serial 的多线程版本
- Parallel 和 ParNew 收集器类似是多线程的，但 Parallel 是吞吐量优先的收集器，可以牺牲等待时间换取系统的吞吐量
- Parallel Old 是 Parallel 老生代版本，Parallel 使用的是复制的内存回收算法，Parallel Old 使用的是标记-整理的内存回收算法
- CMS：一种以获得最短停顿时间为目标的收集器，非常适用 B/S 系统
- G1：一种兼顾吞吐量和停顿时间的 GC 实现，是 JDK 9 以后的默认 GC 选项

## 详细介绍一下 CMS 垃圾回收器？

CMS 是英文 Concurrent Mark-Sweep 的简称，是以牺牲吞吐量为代价来获得最短回收停顿时间的垃圾回收器。对于要求服务器响应速度的应用上，这种垃圾回收器非常适合。在启动 JVM 的参数加上“-XX:+UseConcMarkSweepGC”来指定使用 CMS 垃圾回收器

CMS 使用的是标记-清除的算法实现的，所以在 gc 的时候回产生大量的内存碎片，当剩余内存不能满足程序运行要求时，系统将会出现 Concurrent Mode Failure，临时 CMS 会采用 Serial Old 回收器进行垃圾清除，此时的性能将会被降低

## 新生代垃圾回收器和老生代垃圾回收器都有哪些？有什么区别？

新生代回收器：Serial、ParNew、Parallel Scavenge

老年代回收器：Serial Old、Parallel Old、CMS

整堆回收器：G1

新生代垃圾回收器一般采用的是复制算法，复制算法的优点是效率高，缺点是内存利用率低；老年代回收器一般采用的是标记-整理的算法进行垃圾回收

## 简述分代垃圾回收器是怎么工作的？

分代回收器有两个分区：老生代和新生代，新生代默认的空间占比总空间的 1/3，老生代的默认占比是 2/3

新生代使用的是复制算法，新生代里有 3 个分区：Eden、To Survivor、From Survivor，它们的默认占比是 8:1:1，它的执行流程如下：

把 Eden + From Survivor 存活的对象放入 To Survivor 区

清空 Eden 和 From Survivor 分区

From Survivor 和 To Survivor 分区交换，From Survivor 变 To Survivor，To Survivor 变 From Survivor

每次在 From Survivor 到 To Survivor 移动时都存活的对象，年龄就 +1，当年龄到达 15（默认配置是 15）时，升级为老生代。大对象也会直接进入老生代。 老生代当空间占用到达某个值之后就会触发全局垃圾收回，一般使用标记整理的执行算法。以上这些循环往复就构成了整个分代垃圾回收的整体执行流程

## 说一下 jvm 调优的工具？

JDK 自带了很多监控工具，都位于 JDK 的 bin 目录下，其中最常用的是 jconsole 和 jvisualvm 这两款视图监控工具

jconsole：用于对 JVM 中的内存、线程和类等进行监控；

jvisualvm：JDK 自带的全能分析工具，可以分析：内存快照、线程快照、程序死锁、监控内存的变化、gc 变化等

## 常用的 jvm 调优的参数都有哪些？

-Xms2g：初始化推大小为 2g

-Xmx2g：堆最大内存为 2g

-XX:NewRatio=4：设置年轻的和老年代的内存比例为 1:4

-XX:SurvivorRatio=8：设置新生代 Eden 和 Survivor 比例为 8:2

–XX:+UseParNewGC：指定使用 ParNew + Serial Old 垃圾回收器组合

-XX:+UseParallelOldGC：指定使用 ParNew + ParNew Old 垃圾回收器组合

-XX:+UseConcMarkSweepGC：指定使用 CMS + Serial Old 垃圾回收器组合

-XX:+PrintGC：开启打印 gc 信息

-XX:+PrintGCDetails：打印 gc 详细信息

# 网络

## http的301和302有什么区别

301是永久重定向，而302是临时重定向

## forward和redirect的区别是什么？

1. 从地址栏来讲：forword是服务器内部的重定向，客户端浏览器的网址是不会发生变化的，但是那内容变化了。redirect是服务器根据逻辑，发送一个状态码，告诉浏览器重新去请求那个地址，所以地址栏显示的是新的地址。
2. 从数据共享来讲：forward的数据是可以共享的。redirect不能共享。
3. 从运用的地方来讲：forword 一般用于用户登录的时候，根据角色转发到相应的模块，redirect一般用于用户注销登录时返回主页面或者跳转到其他网站
4. 从效率上来说：forword效率高，而redirect效率低

## tcp与udp区别

- tcp是面向连接的，udp面向无连接
- 对于系统资源的要求，tcp多，udp少
- udp程序结构简单
- tcp保证数据正确性，udp可能丢包，tcp保证数据顺序，udp不保证

## tcp为什么要三次握手，两次不行吗？为什么

为了实现可靠数据传输， TCP 协议的通信双方， 都必须维护一个序列号， 以标识发送出去的数据包中， 哪些是已经被对方收到的。 三次握手的过程即是通信双方相互告知序列号起始值， 并确认对方已经收到了序列号起始值的必经步骤。

如果只是两次握手，至多只有连接发起方的起始序列号能被确认，另一方选择的序列号得不到确认。

## 说一下 tcp 粘包是怎么产生的

## OSI 的七层模型都有哪些

## get 和 post 请求有哪些区别

## 如何实现跨域

## 说一下 JSONP 实现原理

# MySQL

## sql中where的执行顺序

针对MySQL，其条件执行顺序是从左往右，自上而下。针对Oracle，其条件执行顺序是从右往左，自下而上。

MySQL的查询语句遵循原则：排除越多的条件放在第一个。

## MySQL优化

用show status like 'Com_%'查看当前的数据库是以查询为主还是以插入为主

通过慢查询日志查看那些sql语句执行效率低，或者使用show processlist命令查看MySQL当前的线程状态和锁

使用EXPLAIN查看MySQL语句的执行过程并优化

## 乐观锁（CAS compare and swap），悲观锁

- 乐观锁：每次去拿数据的时候都认为别人不会修改，所以不会上锁，但是在提交更新的时候会判断一下在此期间别人有没有去更新这个数据。
- 悲观锁：每次去拿数据的时候都认为别人会修改，所以每次在拿数据的时候都会上锁，这样别人想拿这个数据就会阻止，直到这个锁被释放。

数据库的乐观锁需要自己实现，在表里面添加一个 version 字段，每次修改成功值加 1，这样每次修改的时候先对比一下，自己拥有的 version 和数据库现在的 version 是否一致，如果不一致就不修改，这样就实现了乐观锁。

## 索引是如何实现的

索引是满足某种特定查找算法的数据结构，而这些数据结构会以某种方式指向数据，从而实现高效查找数据。

具体来说 MySQL 中的索引，不同的数据引擎实现有所不同，但目前主流的数据库引擎的索引都是 B+ 树实现的，B+ 树的搜索效率，可以到达二分法的性能，找到数据区域之后就找到了完整的数据结构了，所有索引的性能也是更好的。

## 怎么验证 mysql 的索引是否满足需求？

使用 explain 查看 SQL 是如何执行查询语句的，从而分析你的索引是否满足需求。

## 说一下数据库的事务隔离

MySQL 的事务隔离是在 MySQL. ini 配置文件里添加的，在文件的最后添加：transaction-isolation = REPEATABLE-READ

可用的配置值：READ-UNCOMMITTED、READ-COMMITTED、REPEATABLE-READ、SERIALIZABLE。

READ-UNCOMMITTED：未提交读，最低隔离级别、事务未提交前，就可被其他事务读取（会出现幻读、脏读、不可重复读）。

READ-COMMITTED：提交读，一个事务提交后才能被其他事务读取到（会造成幻读、不可重复读）。

REPEATABLE-READ：可重复读，默认级别，保证多次读取同一个数据时，其值都和事务开始时候的内容是一致，禁止读取到别的事务未提交的数据（会造成幻读）。

SERIALIZABLE：序列化，代价最高最可靠的隔离级别，该隔离级别能防止脏读、不可重复读、幻读。

脏读 ：表示一个事务能够读取另一个事务中还未提交的数据。比如，某个事务尝试插入记录 A，此时该事务还未提交，然后另一个事务尝试读取到了记录 A。

不可重复读 ：是指在一个事务内，多次读同一数据。

幻读 ：指同一个事务内多次查询返回的结果集不一样。比如同一个事务 A 第一次查询时候有 n 条记录，但是第二次同等条件下查询却有 n+1 条记录，这就好像产生了幻觉。发生幻读的原因也是另外一个事务新增或者删除或者修改了第一个事务结果集里面的数据，同一个记录的数据内容被修改了，所有数据行的记录就变多或者变少了。

## 说一下 mysql 常用的引擎

InnoDB 引擎：InnoDB 引擎提供了对数据库 acid 事务的支持，并且还提供了行级锁和外键的约束，它的设计的目标就是处理大数据容量的数据库系统。MySQL 运行的时候，InnoDB 会在内存中建立缓冲池，用于缓冲数据和索引。但是该引擎是不支持全文搜索，同时启动也比较的慢，它是不会保存表的行数的，所以当进行 select count(*) from table 指令的时候，需要进行扫描全表。由于锁的粒度小，写操作是不会锁定全表的,所以在并发度较高的场景下使用会提升效率的。

MyIASM 引擎：MySQL 的默认引擎，但不提供事务的支持，也不支持行级锁和外键。因此当执行插入和更新语句时，即执行写操作的时候需要锁定这个表，所以会导致效率会降低。不过和 InnoDB 不同的是，MyIASM 引擎是保存了表的行数，于是当进行 select count(*) from table 语句时，可以直接的读取已经保存的值而不需要进行扫描全表。所以，如果表的读操作远远多于写操作时，并且不需要事务的支持的，可以将 MyIASM 作为数据库引擎的首选。

## 说一下 mysql 的行锁和表锁？

MyISAM 只支持表锁，InnoDB 支持表锁和行锁，默认为行锁。

- 表级锁：开销小，加锁快，不会出现死锁。锁定粒度大，发生锁冲突的概率最高，并发量最低。
- 行级锁：开销大，加锁慢，会出现死锁。锁力度小，发生锁冲突的概率小，并发度最高。

## 数据库的三范式是什么

- 第一范式：强调的是列的原子性，即数据库表的每一列都是不可分割的原子数据项。
- 第二范式：要求实体的属性完全依赖于主关键字。所谓完全依赖是指不能存在仅依赖主关键字一部分的属性。
- 第三范式：任何非主属性不依赖于其它非主属性。

## 一张自增表里面总共有 7 条数据，删除了最后 2 条数据，重启 mysql 数据库，又插入了一条数据，此时 id 是几

- 表类型如果是 MyISAM ，那 id 就是 18。
- 表类型如果是 InnoDB，那 id 就是 15。

InnoDB 表只会把自增主键的最大 id 记录在内存中，所以重启之后会导致最大 id 丢失

## 如何获取当前数据库版本

使用 select version() 获取当前 MySQL 数据库版本

## 说一下 ACID 是什么

- Atomicity（原子性）：一个事务（transaction）中的所有操作，或者全部完成，或者全部不完成，不会结束在中间某个环节。事务在执行过程中发生错误，会被恢复（Rollback）到事务开始前的状态，就像这个事务从来没有执行过一样。即，事务不可分割、不可约简。
- Consistency（一致性）：在事务开始之前和事务结束以后，数据库的完整性没有被破坏。这表示写入的资料必须完全符合所有的预设约束、触发器、级联回滚等。
- Isolation（隔离性）：数据库允许多个并发事务同时对其数据进行读写和修改的能力，隔离性可以防止多个事务并发执行时由于交叉执行而导致数据的不一致。事务隔离分为不同级别，包括读未提交（Read uncommitted）、读提交（read committed）、可重复读（repeatable read）和串行化（Serializable）。
- Durability（持久性）：事务处理结束后，对数据的修改就是永久的，即便系统故障也不会丢失

## char 和 varchar 的区别是什么？

char(n) ：固定长度类型，比如订阅 char(10)，当你输入"abc"三个字符的时候，它们占的空间还是 10 个字节，其他 7 个是空字节。

chat 优点：效率高；缺点：占用空间；适用场景：存储密码的 md5 值，固定长度的，使用 char 非常合适。

varchar(n) ：可变长度，存储的值是每个值占用的字节再加上一个用来记录其长度的字节的长度。

所以，从空间上考虑 varcahr 比较合适；从效率上考虑 char 比较合适，二者使用需要权衡。

## float 和 double 的区别是什么？

- float 最多可以存储 8 位的十进制数，并在内存中占 4 字节。
- double 最可可以存储 16 位的十进制数，并在内存中占 8 字节。

## mysql 问题排查都有哪些手段

- 使用 show processlist 命令查看当前所有连接信息。
- 使用 explain 命令查询 SQL 语句执行计划。
- 开启慢查询日志，查看慢查询的 SQL。

## 如何做 mysql 的性能优化

- 为搜索字段创建索引。
- 避免使用 select *，列出需要查询的字段。
- 垂直分割分表。
- 选择正确的存储引擎。

# Redis

## redis 是什么？都有哪些使用场景？

Redis是一个使用c语言开发的键值对高速缓存数据库。

Redis使用场景：

- 记录帖子点赞数、点击数、评论数；
- 缓存近期热帖；
- 缓存文章详情信息；
- 记录用户会话信息。

## redis 有哪些功能？

- 数据缓存功能
- 分布式锁的功能
- 支持数据持久化
- 支持事务
- 支持消息队列

## redis 和 memecache 有什么区别？

- 存储方式不同：memcache把数据全部存在内存之中，断电后会挂掉，数据不能超过内存大小；redis有部分存在硬盘上，这样能保证数据的持久性。
- 数据支持类型：memcache对数据类型支持相对简单；redis有复杂的数据类型。
- 使用底层模型不同：它们之间底层实现方式，以及与客户端之间通信的应用协议不一样，redis自己构建了vm机制，因为一般的系统调用系统函数的话，会浪费一定的时间去移动和请求。
- value值大小不同：redis最大可以达到1gb；memcache只有1mb。

## redis 为什么是单线程的？

- 因为 cpu 不是 Redis 的瓶颈，Redis 的瓶颈最有可能是机器内存或者网络带宽。既然单线程容易实现，而且 cpu 又不会成为瓶颈，那就顺理成章地采用单线程的方案了。
- 关于 Redis 的性能，官方网站也有，普通笔记本轻松处理每秒几十万的请求。
- 而且单线程并不代表就慢 nginx 和 nodejs 也都是高性能单线程的代表。

## 什么是缓存穿透？怎么解决？什么事缓存雪崩？怎么解决？

**缓存穿透**：指查询一个一定不存在的数据，由于缓存是不命中时需要从数据库查询，查不到数据则不写入缓存，这将导致这个不存在的数据每次请求都要到数据库去查询，造成缓存穿透。

**解决方案**：最简单粗暴的方法如果一个查询返回的数据为空（不管是数据不存在，还是系统故障），我们就把这个空结果进行缓存，但它的过期时间会很短，最长不超过五分钟。可以提前对一定不存在的key进行过滤。

**缓存雪崩**：当缓存服务器重启或者大量缓存集中在某一个时间段失效，这样在失效的时候，会给后端系统带来很大压力，导致系统崩溃。

**解决方案**：1.当缓存失效后，通过加锁或者队列来控制读数据库写缓存的线程数量。比如对某个key只允许一个线程查询数据和写缓存，其他线程等待。2.做二级缓存，A1为原始缓存，A2为拷贝缓存，A1失效时，可以访问A2，A1缓存失效时间设置为短期，A2设置为长期。3.不同的key设置不同的过期时间，让缓存失效的时间点尽量均匀。

## redis 支持的数据类型有哪些

string(字符串)、list(列表)、hash(字典)、set(集合)、zset(有序集合)。

## redis 支持的 java 客户端都有哪些

支持的java客户端有redisson、jedis、lettuce等。

## jedis 和 redisson 有哪些区别

- jedis：提供了比较全面的redis命令的支持。
- redisson：实现了分布式和可扩展的java数据结构，与jedis相比redisson的功能相对简单，不支持排序、事务、管道、分区等redis特性。

## 怎么保证缓存和数据库数据的一致性？

- 合理设置缓存的过期时间。
- 新增、更改、删除数据库操作时同步更新 Redis，可以使用事物机制来保证数据的一致性。

## redis 持久化有几种方式

Redis 的持久化有两种方式，或者说有两种策略：

- RDB（Redis Database）：指定的时间间隔能对你的数据进行快照存储。
- AOF（Append Only File）：每一个收到的写命令都通过write函数追加到文件中。

## Redis的通讯协议RESP

RESP是redis客户端和服务端之间使用的一种通讯协议，其特点是实现简单，快速解析，可读性好。

## Redis有哪些架构模式？讲讲其特点

**单机版**，特点：简单，问题：内存容量有限、处理能力有限、无法高可用

**主从复制**，其允许用户根据一个Redis服务器来创建任意多个该服务器的复制品，主服务器为master，其他的为slave。主服务器会一直将发生在自己身上的数据更新同步给从服务器，从而一直保证主从服务器的数据相同。特点：降低主服务器的读压力。问题：无法实现高可用，没有解决master写的压力。

**哨兵**，哨兵是一个分布式系统中监控redis主从服务器，并在主服务器下线时自动进行故障转移。

其特性：

- 监控：Sentinel会不断地检查你的主服务器和从服务器是否正常运转
- 提醒：当被监控的某个Redis服务器出现问题时，Sentinel可以通过API向管理员或者其他应用程序发送通知
- 自动故障迁移：当一个主服务器不能正常工作时，Sentinel会开始一次自动故障迁移操作。

特点：保证高可用，监控各个结点，自动故障迁移。缺点：主从模式，切换需要时间会丢失数据，没有解决master的写压力。

**集群（proxy型）**：

![image-20191220181254044](picture\image-20191220181254044.png)

Twemproxy 是一个 Twitter 开源的一个 redis 和 memcache 快速/轻量级代理服务器； Twemproxy 是一个快速的单线程代理程序，支持 Memcached ASCII 协议和 redis 协议。

优点：

- 多种hash算法：MD5、CRC16、CRC32、CRC32a、hsieh、murmur、Jenkins
- 支持失败节点自动删除
- 后端sharing分片逻辑对业务透明，业务方的读写操作和操作单个Redis一致

缺点：增加了新的proxy，需要维护其高可用

**集群（直连型）**：

![image-20191220181640533](picture\image-20191220181640533.png)

Redis3.0之后版本支持redis-cluster集群，采用无中心结构，每个节点保存数据和整个集群状态，每个节点都和其他所有结点连接。

优点：

- 无中心结构，缓解了Redis的写压力
- 数据按照 slot 存储分布在多个节点，节点间数据共享，可动态调整数据分布
- 可扩展，可线性扩展到1000个节点，节点可动态添加或删除
- 高可用，部分节点不可用时，集群仍可用
- 实现故障自动failover，节点之间通过gossip协议交换状态信息，用投票机制完成slave到master的角色升级

缺点：资源隔离性较差，容易出现互相影响的情况；数据通过异步复制，不保证数据的强一致性。

## redis 怎么实现分布式锁？

占坑一般使用 setnx(set if not exists)指令指令来抢占锁，只允许被一个程序占有，抢到之后使用，使用完调用 del 释放锁。其他线程未抢到锁则继续抢或者返回执行失败。

## redis 分布式锁有什么缺陷？

Redis 分布式锁不能解决超时的问题，分布式锁有一个超时时间，程序的执行如果超出了锁的超时时间就会出现问题。

## 使用Redis做异步队列

一般使用list结构作为队列，rpush生产消息，lpop消费消息。当lpop没有消息的时候，要适当sleep一会再重试。

但是当消费者下线时，生产的消息会丢失

## 怎么实现一次生产多次消费

使用pub/sub主题订阅者模式，可以实现1:N的消息队列

## redis 如何做内存优化？

尽可能使用散列表（hashes），散列表（是说散列表里面存储的数少）使用的内存非常小，所以你应该尽可能的将你的数据模型抽象到一个散列表里面。

比如你的web系统中有一个用户对象，不要为这个用户的名称，姓氏，邮箱，密码设置单独的key,而是应该把这个用户的所有信息存储到一张散列表里面。

## redis 淘汰策略有哪些？

- volatile-lru：从已设置过期时间的数据集（server. db[i]. expires）中挑选最近最少使用的数据淘汰。
- volatile-ttl：从已设置过期时间的数据集（server. db[i]. expires）中挑选将要过期的数据淘汰。
- volatile-random：从已设置过期时间的数据集（server. db[i]. expires）中任意选择数据淘汰。
- allkeys-lru：从数据集（server. db[i]. dict）中挑选最近最少使用的数据淘汰。
- allkeys-random：从数据集（server. db[i]. dict）中任意选择数据淘汰。
- no-enviction（驱逐）：禁止驱逐数据。

## redis 常见的性能问题有哪些？该如何解决？

- 主服务器写内存快照，会阻塞主线程的工作，当快照比较大时对性能影响是非常大的，会间断性暂停服务，所以主服务器最好不要写内存快照。
- Redis 主从复制的性能问题，为了主从复制的速度和连接的稳定性，主从库最好在同一个局域网内。

# Spring

## Spring bean的生命周期

首先是实例化bean，然后将bean的各种引用注入到bean中，如果实现了某些指定的接口，则会调用这些接口中要求实现的方法，之后就是使用bean，最后销毁bean。

## SpringMVC，其处理流程

![image-20191116183355508](F:/MyGitHub/ChinaGitHub/gitee/MyNote/面试/picture/image-20191116183355508.png)

1. 发起请求到前端控制器（DispatcherServlet）
2. 前端控制器请求HandlerMapping查找Handler（可以根据XML配置、注解进行查找）
3. 处理器映射器HandlerMapping向前端控制器返回Handler，HandlerMapping会把请求映射为 HandlerExecutionChain对象（包含一个Handler处理器（页面控制）对象，多个HandlerInterceptor拦截器对象），通过这种策略模式，很容易添加新的映射策略
4. 前端控制器调用处理器适配器去执行Handler
5. 处理器适配器HandlerAdapter将会根据适配的结果去执行Handler
6. Handler执行完成给适配器返回ModelAndView
7. 处理器适配器向前端控制器返回ModelAndView （ModelAndView是springmvc框架的一个底层对象，包括 Model和view）
8. 前端控制器请求视图解析器去进行视图解析 （根据逻辑视图名解析成真正的视图(jsp)），通过这种策略很容易更换其他视图技术，只需要更改视图解析器即可 
9. 视图解析器向前端控制器返回View 
10. 前端控制器进行视图渲染 （视图渲染将模型数据(在ModelAndView对象中)填充到request域） 
11. 前端控制器向用户响应结果 

1.Spring mvc先将请求发送给DispatcherServlet。2.DispatcherServlet 查询一个或多个 HandlerMapping，找到处理请求的 Controller。3.DispatcherServlet 再把请求提交到对应的 Controller。4.Controller 进行业务逻辑处理后，会返回一个ModelAndView。5.Dispathcher 查询一个或多个 ViewResolver 视图解析器，找到 ModelAndView 对象指定的视图对象。6.视图对象负责渲染返回给客户端

## Spring mvc有哪些组件

- 前置控制器 DispatcherServlet
- 映射控制器 HandlerMapping
- 处理器 Controller
- 模型和视图 ModelAndView
- 视图解析器 ViewResolver

## @RequestMapping 的作用是什么

将http请求映射到相应的类/方法上

## @Autowired 的作用是什么

@Autowired它可以对类成员变量、方法及构造函数进行标注，完成自动装配的工作，通过@Autowired的使用来消除set/get方法。

## SpringMVC怎么样设定重定向和转发

转发在返回值前面加forward，重定向在返回值前面加redirect。

## springmvc怎么和ajax相互调用

通过jackson或者其他框架吧java里面的对象直接转化成json对象

## sprinmvc中中文乱码

post乱码可以在web.xml中配置一个CharacterEncodingFilter过滤器。

get乱码：对参数进行重新编码，或者在tomcat配置文件中修改编码与项目的编码一致。

## 怎么样从springmvc中得到request或者session

直接在方法的形参中声明HttpServletRequest，springmvc就会自动把request对象传入。

## 1.7 怎么样把ModelMap里面的数据放入Session中

可以在类上面加上@SessionAttributes注解,里面包含的字符串就是要放入session里面的key

## 为什么要使用spring

1. 降低了组件之间的耦合性，实现了软件各层之间的解耦
2. 可以使用容易提供的众多服务，如事物管理，消息服务等
3. 容器提供了单例模式支持
4. 容器提供了AOP技术，利用容器很容易实现权限拦截，运行期监控等功能
5. 容器提供了众多的辅助类，能加快应用的开发
6. spring对与主流的应用框架提供了集成支持
7. spring属于低侵入式设计，代码污染极低
8. 独立于各种应用服务器
9. spring的DI机制降低了也业务对象替换的复杂性
10. spring的高度开放性，并不强制应用完全依赖于spring，开发者可以自由选择spring的部分或全部

## 解释一下Spring AOP

AOP即面向切面编程，是OOP编程的有效补充。使用AOP技术，可以将一些系统性相关的编程工作，独立提取出来，独立实现，然后通过切面切入进系统。从而避免了在业务逻辑的代码中混入很多的系统相关的逻辑——比如权限管理，事物管理，日志记录等等。

AOP分为静态AOP和动态AOP：

- 静态AOP是指AspectJ实现的AOP，他是将切面代码直接编译到Java类文件中。
- 动态AOP是指将切面代码进行动态织入实现的AOP，JDK动态代理。

## 解释一下spring ioc

即“控制反转”，不是什么技术，而是一种设计思想。在Java开发中，Ioc意味着将你设计好的对象交给容器控制，而不是传统的在你的对象内部直接控制。

　　IoC很好的体现了面向对象设计法则之一—— 好莱坞法则：“别找我们，我们找你”；即由IoC容器帮对象找相应的依赖对象并注入，而不是由对象主动去找。

## Spring有哪些主要模块

core模块、aop模块、data access模块、web模块、test模块

- spring core：框架的最基础部分，提供 ioc 和依赖注入特性。
- spring context：构建于 core 封装包基础上的 context 封装包，提供了一种框架式的对象访问方法。
- spring dao：Data Access Object 提供了JDBC的抽象层。
- spring aop：提供了面向切面的编程实现，让你可以自定义拦截器、切点等。
- spring Web：提供了针对 Web 开发的集成特性，例如文件上传，利用 servlet listeners 进行 ioc 容器初始化和针对 Web 的 ApplicationContext。
- spring Web mvc：spring 中的 mvc 封装包提供了 Web 应用的 Model-View-Controller（MVC）的实现。

## spring 常用的注入方式有哪些

Spring通过DI（依赖注入）实现IOC（控制反转），常用的注入方式主要有三种：构造方法注入，setter注入，基于注解的注入。

## spring 中的 bean 是线程安全的吗

不是。spring 中的 bean 默认是单例模式，spring 框架并没有对单例 bean 进行多线程的封装处理。

## spring 支持几种 bean 的作用域

singleton、prototype、request、session、globalSession五中作用域。

- singleton：spring ioc 容器中只存在一个 bean 实例，bean 以单例模式存在，是系统默认值；
- prototype：每次从容器调用 bean 时都会创建一个新的示例，既每次 getBean()相当于执行 new Bean()操作；
- Web 环境下的作用域：
  - request：每次 http 请求都会创建一个 bean；
  - session：同一个 http session 共享一个 bean 实例；
  - global-session：用于 portlet 容器，因为每个 portlet 有单独的 session，globalsession 提供一个全局性的 http session

## spring 自动装配 bean 有哪些方式

- byName：按照bean的属性名称来匹配要装配的bean
- byType：按照bean的类型来匹配要装配的bean
- constructor：按照bean的构造器入参的类型来进行匹配 
- autodetect（自动检测）：先使用constructor进行装配，如果不成功就使用byType来装配

## spring 事务实现方式有哪些

- 声明式事务：声明式事务也有两种实现方式，基于 xml 配置文件的方式和注解方式（在类上添加 @Transaction 注解）。
- 编码方式：提供编码的形式管理和维护事务。

## 说一下 spring 的事务隔离

spring 有五大隔离级别，默认值为 ISOLATION_DEFAULT（使用数据库的设置），其他四个隔离级别和数据库的隔离级别一致：

- ISOLATION_DEFAULT：用底层数据库的设置隔离级别，数据库设置的是什么我就用什么；
- ISOLATIONREADUNCOMMITTED：未提交读，最低隔离级别、事务未提交前，就可被其他事务读取（会出现幻读、脏读、不可重复读）；
- ISOLATIONREADCOMMITTED：提交读，一个事务提交后才能被其他事务读取到（会造成幻读、不可重复读），SQL server 的默认级别；
- ISOLATIONREPEATABLEREAD：可重复读，保证多次读取同一个数据时，其值都和事务开始时候的内容是一致，禁止读取到别的事务未提交的数据（会造成幻读），MySQL 的默认级别；
- ISOLATION_SERIALIZABLE：序列化，代价最高最可靠的隔离级别，该隔离级别能防止脏读、不可重复读、幻读。
- 脏读 ：表示一个事务能够读取另一个事务中还未提交的数据。比如，某个事务尝试插入记录 A，此时该事务还未提交，然后另一个事务尝试读取到了记录 A。
- 不可重复读 ：是指在一个事务内，多次读同一数据。
- 幻读 ：指同一个事务内多次查询返回的结果集不一样。比如同一个事务 A 第一次查询时候有 n 条记录，但是第二次同等条件下查询却有 n+1 条记录，这就好像产生了幻觉。发生幻读的原因也是另外一个事务新增或者删除或者修改了第一个事务结果集里面的数据，同一个记录的数据内容被修改了，所有数据行的记录就变多或者变少了。

# springboot、springcloud

## 什么是 spring boot

SpringBoot是一个构建在Spring框架顶部的项目。它提供了一个更简单、更快捷的方法来设置、配置和运行简单和基于web的应用程序。

## SpringBoot常用的starter

1、spring-boot-starter-web(嵌入Tomcat和web开发需要的servlet和jsp支持)
2、spring-boot-starter-data-jpa(数据库支持)
3、spring-boot-starter-data-Redis(Redis支持)
4、spring-boot-starter-data-solr(solr搜索应用框架支持)
5、mybatis-spring-boot-starter(第三方mybatis集成starter)

## 为什么要用 spring boot？

配置简单、独立运行、自动装配、无代码生成和xml配置、提供应用监控、易上手、提升开发效率。

## spring boot 核心配置文件是什么

spring boot 核心的两个配置文件：

- bootstrap (. yml 或者 . properties)：boostrap 由父 ApplicationContext 加载的，比 applicaton 优先加载，且 boostrap 里面的属性不能被覆盖
- application (. yml 或者 . properties)：用于 spring boot 项目的自动化配置

## SpringBoot自动配置原理：

1、@EnableAutoConfiguration这个注解会"猜"你将如何配置spring，前提是你已经添加了jar依赖项，如果spring-boot-starter-web已经添加Tomcat和SpringMVC，这个注释就会自动假设您在开发一个web应用程序并添加相应的spring配置，会自动去maven中读取每个starter中的spring.factories文件，该文件里配置了所有需要被创建spring容器中bean
2、在main方法中加上@SpringBootApplication和@EnableAutoConfiguration

## SpringBoot starter工作原理：

1、SpringBoot在启动时扫描项目依赖的jar包，寻找包含spring.factories文件的jar
2、根据spring.factories配置加载AutoConfigure
3、根据@Conditional注解的条件，进行自动配置并将bean注入到Spring Context

##  spring boot 配置文件有哪几种类型？它们有什么区别？

配置文件有.properties格式和.yml格式，它们主要的区别是书法风格不同。. yml 格式不支持 @PropertySource 注解导入

## spring boot 有哪些方式可以实现热部署

- 使用 devtools 启动热部署，添加 devtools 库，在配置文件中把 spring. devtools. restart. enabled 设置为 true
- 使用 Intellij Idea 编辑器，勾上自动编译或手动重新编译

## jpa 和Hibernate ，  mybatis有什么区别

**JPA:**规范，各大ORM框架实现这个规范。可以自动建表

**Hibernate:**是完整的ORM，不需要我们写sql，框架比较重，学习成本比较高，性能不好控制，功能强大且文档丰富。

**Mybatis:**不是完整的ORM,程序员需要自己去写全部的SQL,轻量级框架，学习成本低，性能好控制。不能自动建表。

## 什么是 spring cloud？

spring cloud 是一系列框架的有序集合。它利用 spring boot 的开发便利性巧妙地简化了分布式系统基础设施的开发，如服务发现注册、配置中心、消息总线、负载均衡、断路器、数据监控等，都可以用 spring boot 的开发风格做到一键启动和部署。

## Springcloud解决那些问题：

配置管理、（注册中心eureka、zk）、服务发现、服务注册、断路器、路由策略、全局锁、分布式会话、客户端调用、接口网关（zuul）、服务管理系统

## SpringBoot与Springcloud：

1>、SpringBoot简化了xml配置，快速整合框架
2>、Springcloud是一套微服务解决方案—RPC远程调用
3>、关系Springcloud依赖与SpringBoot（web组件用的SpringMVC），为什么Springcloud会依赖与SpringBoot？因为Springcloud写接口就是SpringMVC接口
4>、SpringBootproperties和yml中可以使用${random}设置一些随机值

## 服务的调用：

rest、feign（均使用httpclient技术），负载均衡ribbon

## 服务调用的原理：

服务首先注册到注册中心eureka中(注册一个名字通过名字调用)负载均衡ribbon，先去注册中心取到对应的服务，然后交给我ribbon

## spring cloud 断路器的作用是什么

在分布式架构中，断路器模式的作用也是类似的，当某个服务单元发生故障（类似用电器发生短路）之后，通过断路器的故障监控（类似熔断保险丝），向调用方返回一个错误响应，而不是长时间的等待。这样就不会使得线程因调用故障服务被长时间占用不释放，避免了故障在分布式系统中的蔓延。

## spring cloud 的核心组件有哪些

Eureka：服务注册于发现
Feign：基于动态代理机制，根据注解和选择的机器，拼接请求 url 地址，发起请求
Ribbon：实现负载均衡，从一个服务的多台机器中选择一台
Hystrix：提供线程池，不同的服务走不同的线程池，实现了不同服务调用的隔离，避免了服务雪崩的问题
Zuul：网关管理，由 Zuul 网关转发请求给对应的服务

## springcloud如何实现服务注册与发现

服务发布时指定对应的服务名(IP地址和端口号)，将服务注册到注册中心(eureka和zookeeper)，但是这一切是Springcloud自动实现的，只需要在SpringBoot的启动类上加上@EnableDisscoveryClient注解，同一服务修改端口就可以启动多个实例调用方法：传递服务名称通过注册中心获取所有的可用实例，通过负载均衡策略(Ribbon和Feign)调用对应的服务

## Ribbon和Feign的区别

- 启动类使用的注解不同，Ribbon使用的是@RibbonClient，Feign使用的是@EnableFeignClients
- 服务的指定位置不同，Ribbon是在@RibbonClient注解上声明，Feign则是在定义抽象方法的接口中使用@FeignClient声明
- 调用方式不同，Ribbon需要自己构建http请求，模拟http请求然后使用RestTemplate发送给其他服务，步骤比较繁琐。Feign则是在Ribbon的基础上进行了一次改进，采用接口调用的方式，将需要调用的其他服务的方法定义成抽象方法即可，不需要自己构建http请求，不过要注意的是抽象方法的注解、方法签名要和提供方的完全一致。

## 雪崩效应

分布式系统中的服务通信依赖于网络，网络不好，必然会对分布式系统带来很大的影响。在分布式系统中，服务之间相互依赖，如果一个服务之间出现了故障或者网络延迟，在高并发的情况下，会导致线程阻塞，在很短的时间内该服务的线程资源会消耗殆尽，最终使得该服务不可用。由于服务的相互依赖，可能会导致整个系统的不可用，这就是“雪崩效应”。为了防止此类事件的发生，分布式系统必然要采取相应的措施，如熔断机制（Springcloud采用的是Hystrix）

## Eureka基础架构

1>、服务注册中心：Eureka提供的服务端，提供服务注册与发现的功能
1>>、失效剔除：对于那些非正常下线的服务实例（内存溢出、网络故障导致的），服务注册中心不能收到“服务下线”的请求，为了将这些无法提供服务的实例从服务列表中剔除，Eureka Server在启动的时候会创建一个定时任务，默认每隔一段时间（默认60s）将当前清单中超时（默认90s）没有续约的服务剔除出去。
2>>、自我保护：Eureka Server 在运行期间，会统计心跳失败的比例在15分钟之内是否低于85%，如果出现低于的情况（生产环境由于网络不稳定会导致），Eureka Server会降当前的实例注册信息保护起来，让这些实例不过期，尽可能保护这些注册信息，但是在这保护期间内实例出现问题，那么客户端就很容易拿到实际上已经不存在的服务实例，会出现调用失败的情况，所以客户端必须有容错机制，比如可以使用请求重试、断路器等机制。
在本地进行开发时可以使用 eureka.server.enable-self-preseervation=false参数来关闭保护机制，以确保注册中心可以将不可用的实例剔除。
2>、服务提供者：提供服务的应用，可以是SpringBoot应用也可以是其他的技术平台且遵循Eureka通信机制的应用。他将自己提供的服务注册到Eureka，以供其他应用发现，（如：service层）
1>>、服务注册：服务提供者在启动的时候会通过发送Rest请求的方式将自己注册到Eureka Server（服务注册中心）中，同时带上自身服务的一些元数据，Eureka Server 接收到这个Rest请求后，将元数据存储在一个双层结构Map中，第一层的key是服务名，第二层key是具体服务的实例名
2>>、服务同步：若有两个或两个以上的Eureka Server（服务注册中心）时，他们之间是互相注册的，当服务提供者发送注册请求到一个服务注册中心时，它会将该请求转发到集群中相连的其他注册中心，从而实现注册中心间的服务同步，这样服务提供者的服务信息可以通过任意一台服务中心获取到
3>>、服务续约：在注册完服务之后，服务提供者会维护一个心跳来持续告诉Eureka Server：“我还活着”，以防止Eureka Server的“剔除任务”将该服务实例从服务列表中排除出去。配置：eureka.instance.lease-renewal-in-seconds=30(续约任务的调用间隔时间，默认30秒，也就是每隔30秒向服务端发送一次心跳，证明自己依然存活)，eureka.instance.lease-expiration-duration-in-seconds=90(服务失效时间，默认90秒，也就是告诉服务端，如果90秒之内没有给你发送心跳就证明我“死”了，将我剔除)
3>、服务消费者：消费者应用从服务注册中心获取服务列表，从而使消费者可以知道去何处调用其所需要的服务，如：Ribbon实现消费方式、Feign实现消费方式
1>>、获取服务：当启动服务消费者的时候，它会发送一个Rest请求给注册中心，获取上面注册的服务清单，Eureka Server会维护一份只读的服务清单来返回给客户端，并且每三十秒更新一次
2>>、服务调用：在服务消费者获取到服务清单后，通过服务名可以获得具体提供服务的实例名和该实例的元信息，采用Ribbon实现负载均衡
3>>、服务下线：当服务实例进行正常的关闭操作时，它会触发一个服务下线的Rest请求给Eureka Server，告诉服务注册中心“我要下线了”。服务端接收到请求之后，将该服务状态设置为下线，并把下线时间传播出去。

## Eureka和zookeeper都可以提供服务注册与发现的功能，两者的区别

Zookeeper保证了CP(C：一致性，P：分区容错性)，Eureka保证了AP(A：高可用，P：分区容错)
1、Zookeeper-----当向注册中心查询服务列表时，我们可以容忍注册中心返回的是几分钟以前的信息，但不能容忍直接down掉不可用的。也就是说服务注册功能对高可用性要求比较高，但是zk会出现这样的一种情况，当master节点因为网络故障与其他节点失去联系时，剩余的节点会重新选leader。问题在于，选取leader的时间过长(30~120s)，且选取期间zk集群都不可用，这样就会导致选取期间注册服务瘫痪。在云部署的环境下，因网络问题使得zk集群失去master节点是较大概率会发生的事，虽然服务最终恢复，但是漫长的选择时间导致的注册长期不可用是不能容忍的
2、Eureka则看明白这一点，因此再设计的优先保证了高可用性。Eureka各个节点都是平等的，几个节点挂掉不会影响到正常节点的工作，剩余的节点依然可以提供注册和查询服务。而Eureka的客户端再向某个Eureka注册时如果发现连接失败，则会自动切换至其他节点，只要有一台Eureka还在，就能保证注册服务的可用(保证可用性)，只不过查到的信息可能不是最新的(不保证一致性)。除此之外Eureka还有一种自我保护机制，如果在15分钟内超过85%的节点都没有正常心跳，那么Eureka就认为客户端与注册中心出现了网络故障，此时就会出现以下几种情况：
1>、Eureka不再从注册列表移除因为长时间没收到心跳而应该过期的服务
2>、Eureka仍然能够接受新服务的注册和查询请求，但是不会被同步到其它节点上(保证当前节点可用)
3>、当网络稳定时，当前实例新的注册信息会被同步到其它节点中
Eureka还有客户端缓存功能(Eureka分为客户端程序和服务器端程序两个部分，客户端程序负责向外提供注册与发现服务接口)。所以即便Eureka集群中所有节点都失效，或者发生网络分隔故障导致客户端不能访问任何一台Eureka服务器；Eureka服务的消费者任然可以通过Eureka客户端缓存来获取所有的服务注册信息。甚至最极端的环境下，所有正常的Eureka节点都不对请求产生响应也没有更好的服务器解决方案来解决这种问题时；得益于Eureka的客户端缓存技术，消费者服务仍然可以通过Eureka客户端查询与获取注册服务信息，这点很重要，因此Eureka可以很好的应对网络故障导致部分节点失去联系的情况，而不像Zookeeper那样使整个注册服务瘫痪。

## CAP理论

1、Consistency：指数据的强一致性。如果写入某个数据成功，之后读取，读到的都是新写入的数据；如果写入失败，读到的都不是写入失败的数据。
2、Availability：指服务的可用性
3、Partition-tolerance：指分区容错

## Ribbon和Nginx的区别

Nginx性能好，但Ribbon可以剔除不健康节点，Nginx剔除比较麻烦，Ribbon是客户端负载均衡，Nginx是服务端负载均衡

## 服务注册与发现

服务注册就是向服务注册中心注册一个服务实例，服务提供者将自己的服务信息（服务名、IP地址等）告知注册中心。服务发现是服务消费另一个服务时，注册中心将服务的实例返回给服务消费者，一个服务既是服务提供者又是服务消费者。
服务注册中心健康检查机制，当一个服务实例注册成功以后，会定时向注册中心发送一个心跳证明自己可用，若停止发送心跳证明服务不可用将会别剔除。若过段时间继续想注册中心提供心跳，将会重新加入服务注册中心列表中。

## 服务的负载均衡

为什么要用：微服务是将业务代码拆分为很多小的服务单元，服务之间的相互调用通过HTTP协议来调用，为了保证服务的高可用，服务单元往往都是集群化部署的，那么消费者该调用那个服务提供者的实例呢？
介绍：服务消费者集成负载均衡组件，该组件会向服务消费者获取服务注册列表信息，并隔一段时间重新刷新获取列表。当服务消费者消费服务时，负载均衡组件获取服务提供者所有实例的注册信息，并通过一定的负载均衡策略（可以自己配置）选择一个服务提供者实例，向该实例进行服务消费，这样就实现了负载均衡。

## 微服务

微服务就是将工程根据不同的业务规则拆分成微服务，部署在不同的服务器上，服务之间相互调用，java中有的微服务有dubbo(只能用来做微服务)、springcloud( 提供了服务的发现、断路器等)。\

## 微服务的特点

按业务划分为一个独立运行的程序，即服务单元
服务之间通过HTTP协议相互通信
自动化部署
可以用不同的编程语言
可以用不同的存储技术
服务集中化管理
微服务是一个分布式系统

## 微服务的优势

1、将一个复杂的业务拆分为若干小的业务，将复杂的业务简单化，新人只需要了解他所接管的服务的代码，减少了新人的学习成本。
2、由于微服务是分布式服务，服务于服务之间没有任何耦合。微服务系统的微服务单元具有很强的横向拓展能力。
3、服务于服务之间采用HTTP网络通信协议来通信，单个服务内部高度耦合，服务与服务之间完全独立，无耦合。这使得微服务可以采用任何的开发语言和技术来实现，提高开发效率、降低开发成本。
4、微服务是按照业务进行拆分的，并有坚实的服务边界，若要重写某一业务代码，不需了解所以业务，重写简单。
5、微服务的每个服务单元是独立部署的，即独立运行在某个进程中，微服务的修改和部署对其他服务没有影响。
6、微服务在CAP理论中采用的AP架构，具有高可用分区容错特点。高可用主要体现在系统7x24不间断服务，他要求系统有大量的服务器集群，从而提高系统的负载能力。分区容错也使得系统更加健壮。

## 微服务的不足

1、微服务的复杂度：构建一个微服务比较复杂，服务与服务之间通过HTTP协议或其他消息传递机制通信，开发者要选出最佳的通信机制，并解决网络服务差时带来的风险。
2、分布式事物：将事物分成多阶段提交，如果一阶段某一节点失败仍会导致数据不正确。如果事物涉及的节点很多，某一节点的网络出现异常会导致整个事务处于阻塞状态，大大降低数据库的性能。
3、服务划分：将一个完整的系统拆分成很多个服务，是一件非常困难的事，因为这涉及了具体的业务场景
4、服务部署：最佳部署容器Docker



# MyBatis

## mybatis 中 #{}和 ${}的区别是什么

- \#{}是预编译处理，${}是字符串替换；
- Mybatis在处理#{}时，会将sql中的#{}替换为?号，调用PreparedStatement的set方法来赋值；
- Mybatis在处理时，就是把{}时，就是把时，就是把{}替换成变量的值；
- 使用#{}可以有效的防止SQL注入，提高系统安全性。

## mybatis 有几种分页方式？

分页方式：逻辑分页和物理分页。

逻辑分页：使用MyBatis自带的RowBounds进行分页，它是一次性查询很多数据，然后在数据中进行检索。

物理分页：自己手写SQL分页或使用分页插件PageHelper，去数据库查询指定条数的分页数据形式。

## RowBounds 是一次性查询全部结果吗？为什么？

RowBounds表面是在“所有”数据中检索数据，其实并非是一次性查询出所有的数据。因为MyBatis是对jdbc的封装，在jdbc驱动中有一个Fetch Size的配置，它规定了每次最多从数据库查询多少条数据，假如你要查询更多数据，它会在你执行next()的时候，去查询更多的数据。就好比你去自动取款机取10000元，但是取款机每次只能取2500元，所以你需要取4次才能把钱取完。对于jdbc来说，当你调用next()的时候会自动帮你完成查询工作。这样的好处可以有效的防止内存溢出。

## mybatis 是否支持延迟加载？延迟加载的原理是什么？

MyBatis支持延迟加载，设置lazyLoadingEnabled=true即可。    延迟加载的原理是调用的时候触发加载，而不是在初始化的时候就加载信息。比如调用a.getB().getName(),这个时候发现a.get()的值为null,此时会单独触发事先保存好的关联B对象的SQL,先查询出来B,然后在调用a.setB(b),而这个时候在调用a.getB().getName()就有值了，这就是延迟加载的基本原理。

## 说一下 mybatis 的一级缓存和二级缓存

一级缓存: 基于 PerpetualCache 的 HashMap 本地缓存，其存储作用域为 Session，当 Session flush 或 close 之后，该 Session 中的所有 Cache 就将清空，默认打开一级缓存。

二级缓存与一级缓存其机制相同，默认也是采用 PerpetualCache，HashMap 存储，不同在于其存储作用域为 Mapper(Namespace)，并且可自定义存储源，如 Ehcache。默认不打开二级缓存，要开启二级缓存，使用二级缓存属性类需要实现Serializable序列化接口(可用来保存对象的状态),可在它的映射文件中配置 ；

对于缓存数据更新机制，当某一个作用域(一级缓存 Session/二级缓存Namespaces)的进行了C/U/D 操作后，默认该作用域下所有 select 中的缓存将被 clear。

## mybatis 和 hibernate 的区别有哪些？

- 灵活性：MyBatis更加灵活，自己可以写sql语句，使用起来比较方便。
- 可移植性：MyBatis有很多自己写的sql，因为每个数据库的sql可以不相同，所以可移植性比较差。
- 学习和使用门槛：MyBatis入门比较简单，使用门槛也更低。
- 二级缓存：hibernate拥有更好的二级缓存，它的二级缓存可以自行更换第三方的二级缓存。

## mybatis 有哪些执行器（Executor）？

Mybatis有三种基本的执行器（Executor）：

SimpleExecutor：每执行一次update或select，就开启一个Statement对象，用完立刻关闭Statement对象。

ReuseExecutor：执行update或select，以sql作为key查找Statement对象，存在就使用，不存在就创建，用完后，不关闭Statement对象，而是放置于Map内，供下一次使用。简言之，就是重复使用Statement对象。

BatchExecutor：执行update（没有select，JDBC批处理不支持select），将所有sql都添加到批处理中（addBatch()），等待统一执行（executeBatch()），它缓存了多个Statement对象，每个Statement对象都是addBatch()完毕后，等待逐一执行executeBatch()批处理。与JDBC批处理相同。

## mybatis 分页插件的实现原理是什么？

分页插件的基本原理是使用Mybatis提供的插件接口，实现自定义插件，在插件的拦截方法内拦截待执行的sql，然后重写sql，根据dialect方言，添加对应的物理分页语句和物理分页参数。

## mybatis 如何编写一个自定义插件

Mybatis自定义插件针对Mybatis四大对象（Executor、StatementHandler 、ParameterHandler 、ResultSetHandler ）进行拦截，具体拦截方式为：

Executor：拦截执行器的方法(log记录)

StatementHandler ：拦截Sql语法构建的处理

ParameterHandler ：拦截参数的处理

ResultSetHandler ：拦截结果集的处理

Mybatis自定义插件必须实现Interceptor接口：

```java
public interface Interceptor {

    Object intercept(Invocation invocation) throws Throwable;

    Object plugin(Object target);

    void setProperties(Properties properties);

}
```

intercept方法：拦截器具体处理逻辑方法

plugin方法：根据签名signatureMap生成动态代理对象

setProperties方法：设置Properties属性

自定义插件demo：

```java
// ExamplePlugin.java

@Intercepts({@Signature(

  type= Executor.class,

  method = "update",

  args = {MappedStatement.class,Object.class})})

public class ExamplePlugin implements Interceptor {

  public Object intercept(Invocation invocation) throws Throwable {

  Object target = invocation.getTarget(); //被代理对象

  Method method = invocation.getMethod(); //代理方法

  Object[] args = invocation.getArgs(); //方法参数

  // do something ...... 方法拦截前执行代码块

  Object result = invocation.proceed();

  // do something .......方法拦截后执行代码块

  return result;

  }

  public Object plugin(Object target) {

    return Plugin.wrap(target, this);

  }

  public void setProperties(Properties properties) {

  }

}
```

一个@Intercepts可以配置多个@Signature，@Signature中的参数定义如下：

- type：表示拦截的类，这里是Executor的实现类；
- method：表示拦截的方法，这里是拦截Executor的update方法；
- args：表示方法参数。

# RabbitMQ

## rabbitmq 的使用场景有哪些？

- 抢购活动，削峰填谷，防止系统崩塌。
- 延迟信息处理，比如10分钟之后给下单未付款的用户发送邮件提醒。
- 解耦系统，对于新增的功能可以单独写模块扩展，比如用户确认评价之后，新增了给用户返积分的功能，这个时候不用在业务代码里添加新积分的功能，只需要把新增积分的接口订阅确认评价的消息队列即可，后面在添加任何功能只需要订阅对应的消息队列即可。

## rabbitmq 有哪些重要的角色

RabbitMQ 中重要的角色有：生产者、消费者和代理：

- 生产者：消息的创建者，负责创建和推送数据到消息服务器；
- 消费者：消息的接收方，用于处理数据和确认消息；
- 代理：就是 RabbitMQ 本身，用于扮演“快递”的角色，本身不生产消息，只是扮演“快递”的角色。

## rabbitmq 有哪些重要的组件？

- ConnectionFactory（连接管理器）：应用程序与Rabbit之间建立连接的管理器，程序代码中使用。
- Channel（信道）：消息推送使用的通道。
- Exchange（交换器）：用于接受、分配消息。
- Queue（队列）：用于存储生产者的消息。
- RoutingKey（路由键）：用于把生成者的数据分配到交换器上。
- BindingKey（绑定键）：用于把交换器的消息绑定到队列上。

## rabbitmq 中 vhost 的作用是什么？

vhost：每个RabbitMQ都能创建很多vhost，我们称之为虚拟主机，每个虚拟主机其实都是mini版的RabbitMQ，它拥有自己的队列，交换器和绑定，拥有自己的权限机制。

## rabbitmq 的消息是怎么发送的？

首先客户端必须连接到 RabbitMQ 服务器才能发布和消费消息，客户端和 rabbit server 之间会创建一个 tcp 连接，一旦 tcp 打开并通过了认证（认证就是你发送给 rabbit 服务器的用户名和密码），你的客户端和 RabbitMQ 就创建了一条 amqp 信道（channel），信道是创建在“真实” tcp 上的虚拟连接，amqp 命令都是通过信道发送出去的，每个信道都会有一个唯一的 id，不论是发布消息，订阅队列都是通过这个信道完成的。

## rabbitmq 怎么保证消息的稳定性？

- 提供了事务的功能。
- 通过将 channel 设置为 confirm（确认）模式。

## rabbitmq 怎么避免消息丢失？

- 把消息持久化磁盘，保证服务器重启消息不丢失。
- 每个集群中至少有一个物理磁盘，保证消息落入磁盘。

## 要保证消息持久化成功的条件有哪些？

- 声明队列必须设置持久化 durable 设置为 true.
- 消息推送投递模式必须设置持久化，deliveryMode 设置为 2（持久）。
- 消息已经到达持久化交换器。
- 消息已经到达持久化队列。

以上四个条件都满足才能保证消息持久化成功。

## rabbitmq 持久化有什么缺点？

持久化的缺地就是降低了服务器的吞吐量，因为使用的是磁盘而非内存存储，从而降低了吞吐量。可尽量使用 ssd 硬盘来缓解吞吐量的问题。

## rabbitmq 有几种广播类型？

三种广播模式：

- fanout: 所有bind到此exchange的queue都可以接收消息（纯广播，绑定到RabbitMQ的接受者都能收到消息）；
- direct: 通过routingKey和exchange决定的那个唯一的queue可以接收消息；
- topic:所有符合routingKey(此时可以是一个表达式)的routingKey所bind的queue可以接收消息；

## rabbitmq 怎么实现延迟消息队列？

- 通过消息过期后进入死信交换器，再由交换器转发到延迟消费队列，实现延迟功能；
- 使用 RabbitMQ-delayed-message-exchange 插件实现延迟功能。

## rabbitmq 集群有什么用？

集群主要有以下两个用途：

- 高可用：某个服务器出现问题，整个 RabbitMQ 还可以继续使用；
- 高容量：集群可以承载更多的消息量。

## rabbitmq 节点的类型有哪些？

- 磁盘节点：消息会存储到磁盘。
- 内存节点：消息都存储在内存中，重启服务器消息丢失，性能高于磁盘类型。

## rabbitmq 集群搭建需要注意哪些问题？

- 各节点之间使用“–link”连接，此属性不能忽略。
- 各节点使用的 erlang cookie 值必须相同，此值相当于“秘钥”的功能，用于各节点的认证。
- 整个集群中必须包含一个磁盘节点。

## rabbitmq 每个节点是其他节点的完整拷贝吗？为什么？

不是，原因有以下两个：

- 存储空间的考虑：如果每个节点都拥有所有队列的完全拷贝，这样新增节点不但没有新增存储空间，反而增加了更多的冗余数据；
- 性能的考虑：如果每条消息都需要完整拷贝到每一个集群节点，那新增节点并没有提升处理消息的能力，最多是保持和单节点相同的性能甚至是更糟。

## rabbitmq 集群中唯一一个磁盘节点崩溃了会发生什么情况？

如果唯一磁盘的磁盘节点崩溃了，不能进行以下操作：

- 不能创建队列
- 不能创建交换器
- 不能创建绑定
- 不能添加用户
- 不能更改权限
- 不能添加和删除集群节点

唯一磁盘节点崩溃了，集群是可以保持运行的，但你不能更改任何东西。

## rabbitmq 对集群节点停止顺序有要求吗？

RabbitMQ 对集群的停止的顺序是有要求的，应该先关闭内存节点，最后再关闭磁盘节点。如果顺序恰好相反的话，可能会造成消息的丢失。

# Kafka

## kafka 可以脱离 zookeeper 单独使用吗？为什么？

kafka不能脱离zookeeper单独使用，因为kafka使用zookeeper管理和协调kafka的节点服务器。

## kafka 有几种数据保留的策略？

有两种数据保存策略,按照过期时间和按照存储的消息大小。

## kafka 同时设置了 7 天和 10G 清除数据，到第五天的时候消息达到了 10G，这个时候 kafka 将如何处理？

这个时候kafka会执行数据清除工作，时间和大小不论那个满足条件，都会清空数据。

## 什么情况会导致 kafka 运行变慢

1、cpu性能瓶颈 2、磁盘读写瓶颈 3、网络瓶颈

## 使用 kafka 集群需要注意什么？

- 集群的数量不是越多越好,最好不要超过7个,因为节点越多,消息复制需要的时间就越长,整个群组的吞吐量就越低。
- 集群数量最好是单数,因为超过一半故障集群就不能用了,设置为单数容错率更高。

## Kafka为什么那么快

数据写入：使用了两个技术，顺序写入和MMFile（Memory Mapped File，操作系统的分页存储）

数据读取：基于sendfile实现zero copy。

批量压缩：对多个消息进行批量压缩，加快网络IO传输速度

# zookeeper

## zookeeper 是什么？

zookeeper 是一个分布式的，开放源码的分布式应用程序协调服务，是 google chubby 的开源实现，是 hadoop 和 hbase 的重要组件。它是一个为分布式应用提供一致性服务的软件，提供的功能包括：配置维护、域名服务、分布式同步、组服务等。

## zookeeper 都有哪些功能

- 集群管理：监控节点存活状态、运行请求等。
- 主节点选举：主节点挂掉了之后可以从备用的节点开始新一轮选主，主节点选举说的就是这个选举的过程，使用 zookeeper 可以协助完成这个过程。
- 分布式锁：zookeeper 提供两种锁：独占锁、共享锁。独占锁即一次只能有一个线程使用资源，共享锁是读锁共享，读写互斥，即可以有多线线程同时读同一个资源，如果要使用写锁也只能有一个线程使用。zookeeper可以对分布式锁进行控制。
- 命名服务：在分布式系统中，通过使用命名服务，客户端应用能够根据指定名字来获取资源或服务的地址，提供者等信息。

## zookeeper 有几种部署模式？

zookeeper 有三种部署模式：

- 单机部署：一台集群上运行；
- 集群部署：多台集群运行；
- 伪集群部署：一台集群启动多个 zookeeper 实例运行。

## zookeeper 怎么保证主从节点的状态同步？

zookeeper 的核心是原子广播，这个机制保证了各个 server 之间的同步。实现这个机制的协议叫做 zab 协议。 zab 协议有两种模式，分别是恢复模式（选主）和广播模式（同步）。当服务启动或者在领导者崩溃后，zab 就进入了恢复模式，当领导者被选举出来，且大多数 server 完成了和 leader 的状态同步以后，恢复模式就结束了。状态同步保证了 leader 和 server 具有相同的系统状态。

## 集群中为什么要有主节点

在分布式环境中，有些业务逻辑只需要集群中的某一台机器进行执行，其他的机器可以共享这个结果，这样可以大大减少重复计算，提高性能，所以就需要主节点。

## 集群中有 3 台服务器，其中一个节点宕机，这个时候 zookeeper 还可以使用吗？

可以继续使用，单数服务器只要没超过一半的服务器宕机就可以继续使用。

## 说一下 zookeeper 的通知机制

客户端端会对某个 znode 建立一个 watcher 事件，当该 znode 发生变化时，这些客户端会收到 zookeeper 的通知，然后客户端可以根据 znode 变化来做出业务上的改变。

# Linux

## 怎么清屏？怎么退出当前命令？怎么执行睡眠？怎么查看当前用户 id？查看指定帮助用什么命令？

清屏： clear
退出当前命令： ctrl+c 彻底退出
执行睡眠 ： ctrl+z 挂起当前进程fg 恢复后台
查看当前用户 id： ”id“：查看显示目前登陆账户的 uid 和 gid 及所属分组及用户名
查看指定帮助： 如 man adduser 这个很全 而且有例子； adduser --help 这个告诉你一些常用参数； info adduesr；

## Ls 命令执行什么功能？ 可以带哪些参数，有什么区别？

ls 执行的功能： 列出指定目录中的目录，以及文件
哪些参数以及区别： a 所有文件l 详细信息，包括大小字节数，可读可写可执行的权限等

## 用什么命令对一个文件的内容进行统计

wc 命令 - c 统计字节数 - l 统计行数 - w 统计字数

## 终端是哪个文件夹下的哪个文件？黑洞文件是哪个文件夹下的哪个命令？

终端 /dev/tty

黑洞文件 /dev/null

## Grep 命令有什么用？ 如何忽略大小写？ 如何查找不含该串的行?

是一种强大的文本搜索工具，它能使用正则表达式搜索文本，并把匹 配的行打印出来。
grep [stringSTRING] filename grep [^string] filename

## 建立软链接(快捷方式)，以及硬链接的命令

软链接： ln -s slink source
硬链接： ln link source

## 怎么使一个命令在后台运行?

一般都是使用 & 在命令结尾来让程序自动运行。(命令后可以不追加空格)

## 哪个命令专门用来查看后台任务? 

job -l

## 把后台任务调到前台执行使用什么命令?把停下的后台任务在后台执行起来用什么命令?

把后台任务调到前台执行 fg

把停下的后台任务在后台执行起来 bg

## 终止进程用什么命令? 带什么参数? 

kill [-s <信息名称或编号>][程序] 或 kill [-l <信息编号>] 

kill-9 pid

## 搜索文件用什么命令? 格式是怎么样的? 

find <指定目录> <指定条件> <指定动作>

whereis 加参数与文件名

locate 只加文件名

find 直接搜索磁盘，较慢。

find / -name "string*"

## 使用什么命令查看用过的命令列表

history

## 使用什么命令查看磁盘使用空间？ 空闲空间呢?

df -hl

## 使用什么命令查看 ip 地址及接口信息

ifconfig

## 查看各类环境变量用什么命令

查看所有 env
查看某个，如 home： env $HOME

## 查找命令的可执行文件是去哪查找的? 怎么对其进行设置及添加?

whereis [-bfmsu][-B <目录>...][-M <目录>...][-S <目录>...][文件...]

补充说明：whereis 指令会在特定目录中查找符合条件的文件。这些文件的烈性应属于原始代码，二进制文件，或是帮助文件。

> -b  只查找二进制文件。
>
> -B<目录> 只在设置的目录下查找二进制文件。 -f 不显示文件名前的路径名称。
> -m  只查找说明文件。
> -M<目录> 只在设置的目录下查找说明文件。 -s 只查找原始代码文件。
> -S<目录> 只在设置的目录下查找原始代码文件。 -u 查找不包含指定类型的文件。
> which 指令会在 PATH 变量指定的路径中，搜索某个系统命令的位置，并且返回第一个搜索结果。
> -n 指定文件名长度，指定的长度必须大于或等于所有文件中最长的文件名。
> -p 与-n 参数相同，但此处的包括了文件的路径。 -w 指定输出时栏位的宽度。
> -V  显示版本信息

## 通过什么命令查找执行命令?

which 只能查可执行文件

whereis 只能查二进制文件、说明文档，源文件等

## 怎么对命令进行取别名

alias la='ls -a'

## 如果一个linux新手想要知道当前系统支持的所有命令的列表，他需要怎么做

使用命令compgen ­-c，可以打印出所有支持的命令列表

## 如果你的助手想要打印出当前的目录栈，你会建议他怎么做

dirs

## 你的系统目前有许多正在运行的任务，在不重启机器的条件下，有什么方法可以把所有正在运行的进程移除呢？

使用linux命令 ’disown -r ’可以将所有正在运行的进程移除。

## bash shell 中的hash 命令有什么作用？

linux命令’hash’管理着一个内置的哈希表，记录了已执行过的命令的完整路径, 用该命令可以打印出你所使用过的命令以及执行的次数。

## 使用哪一个命令可以查看自己文件系统的磁盘空间配额呢？

使用命令repquota 能够显示出一个文件系统的配额信息

【附】只有root用户才能够查看其它用户的配额。

## 如何看当前Linux系统有几颗物理CPU和每颗CPU的核数

```bash
[root@localhost ~]# cat /proc/cpuinfo|grep -c 'physical id'
1
[root@localhost ~]# cat /proc/cpuinfo|grep -c 'processor'
1
```

## 查看系统负载有两个常用的命令，是哪两个？这三个数值表示什么含义呢？

```bash
[root@localhost ~]# w
 07:02:07 up 2 days, 10:38,  2 users,  load average: 3.32, 2.48, 1.22
USER     TTY      FROM             LOGIN@   IDLE   JCPU   PCPU WHAT
root     tty1                      16Nov19 15days  2:56   0.00s xinit /etc/X11/
root     pts/0    :0               07:00    7.00s 26.63s  0.05s w
[root@localhost ~]# uptime
 07:02:55 up 2 days, 10:39,  2 users,  load average: 3.60, 2.73, 1.36
```

其中load average即系统负载，三个数值分别表示一分钟、五分钟、十五分钟内系统的平均负载，即平均任务数

## vmstat r, b, si, so, bi, bo 这几列表示什么含义呢

vmstat是Virtual Meomory Statistics（虚拟内存统计）的缩写，可对操作系统的虚拟内存、进程、CPU活动进行监控。是对系统的整体情况进行统计，不足之处是无法对某个进程进行深入分析。

```bash
[root@localhost ~]# vmstat
procs -----------memory---------- ---swap-- -----io---- -system-- ------cpu-----
 r  b   swpd   free   buff  cache   si   so    bi    bo   in   cs us sy id wa st
 1  0 1486020  71408     36 304676   58   64  1167    77    7   74  6  3 91  0  0
```

r即running，表示正在跑的任务数

b即blocked，表示被阻塞的任务数

si表示有多少数据从交换分区读入内存

so表示有多少数据从内存写入交换分区

bi表示有多少数据从磁盘读入内存

bo表示有多少数据从内存写入磁盘

简记：i --input，进入内存

o --output，从内存出去

s --swap，交换分区

b --block，块设备，磁盘

单位都是KB

## linux系统里，您知道buffer和cache如何区分吗

buffer和cache都是内存中的一块区域，当CPU需要写数据到磁盘时，由于磁盘速度比较慢，所以CPU先把数据存进buffer，然后CPU去执行其他任务，buffer中的数据会定期写入磁盘；当CPU需要从磁盘读入数据时，由于磁盘速度比较慢，可以把即将用到的数据提前存入cache，CPU直接从Cache中拿数据要快的多。

## 使用top查看系统资源占用情况时，哪一列表示内存占用呢

```bash
top - 07:09:13 up 2 days, 10:45,  2 users,  load average: 2.45, 2.59, 1.76
Tasks: 245 total,   1 running, 244 sleeping,   0 stopped,   0 zombie
%Cpu(s): 19.5 us,  7.4 sy,  0.0 ni, 72.7 id,  0.0 wa,  0.0 hi,  0.3 si,  0.0 st
KiB Mem :   995892 total,    71460 free,   596932 used,   327500 buff/cache
KiB Swap:  2097148 total,   700412 free,  1396736 used.   130548 avail Mem 

   PID USER      PR  NI    VIRT    RES    SHR S %CPU %MEM     TIME+ COMMAND     
  6631 polkitd   20   0  616328   1828    944 S  8.7  0.2  72:47.20 polkitd     
  6582 dbus      20   0   70972   2120    564 S  8.0  0.2  65:03.88 dbus-daemon 
 16348 root      20   0  396516   1440    764 S  6.0  0.1  47:36.45 accounts-d+ 
 16450 root      20   0  456788    988    588 S  2.0  0.1  15:39.87 gsd-account 
 16295 root      20   0 3079676 134216  12160 S  0.7 13.5   7:10.61 gnome-shell 
 62200 polkitd   20   0 1561428   7632    864 S  0.7  0.8   1:27.28 mongod      
```

VIRT虚拟内存用量

RES物理内存用量

SHR共享内存用量

%MEM内存用量

## 如何实时查看网卡流量为多少？如何查看历史网卡流量？

sar -n DEV#查看网卡流量，默认10分钟更新一次

sar -n DEV 1 10#一秒显示一次，一共显示10次

sar -n DEV -f /var/log/sa/sa22#查看指定日期的流量日志

## 如何查看当前系统都有哪些进程

ps -aux 或者ps -elf

## ps 查看系统进程时，有一列为STAT， 其含义

D不可中断，R正在运行，T停止或被追踪，W进入内存交换，X死掉的进程，<高优先级，n低优先级，s包含子进程，+位于后台的进程组S表示正在休眠；Z表示僵尸进程

## 如何查看系统都开启了哪些端口

```bash
[root@localhost ~]# netstat -lnp
Active Internet connections (only servers)
Proto Recv-Q Send-Q Local Address           Foreign Address         State       PID/Program name    
tcp        0      0 0.0.0.0:111             0.0.0.0:*               LISTEN      1/systemd           
tcp        0      0 0.0.0.0:6000            0.0.0.0:*               LISTEN      16078/X             
tcp        0      0 192.168.122.1:53        0.0.0.0:*               LISTEN      7831/dnsmasq        
tcp        0      0 0.0.0.0:22              0.0.0.0:*               LISTEN      7498/sshd           
tcp        0      0 127.0.0.1:631           0.0.0.0:*               LISTEN      7495/cupsd          
tcp        0      0 127.0.0.1:25            0.0.0.0:*               LISTEN      7837/master xxxxxxxxxx netstat -lnp[root@localhost ~]# netstat -lnpActive Internet connections (only servers)Proto Recv-Q Send-Q Local Address           Foreign Address         State       PID/Program name    tcp        0      0 0.0.0.0:111             0.0.0.0:*               LISTEN      1/systemd           tcp        0      0 0.0.0.0:6000            0.0.0.0:*               LISTEN      16078/X             tcp        0      0 192.168.122.1:53        0.0.0.0:*               LISTEN      7831/dnsmasq        tcp        0      0 0.0.0.0:22              0.0.0.0:*               LISTEN      7498/sshd           tcp        0      0 127.0.0.1:631           0.0.0.0:*               LISTEN      7495/cupsd          tcp        0      0 127.0.0.1:25            0.0.0.0:*               LISTEN      7837/master bash
```

## 如何查看网络连接状况

```bash
[root@localhost ~]# netstat -an
Active Internet connections (servers and established)
Proto Recv-Q Send-Q Local Address           Foreign Address         State      
tcp        0      0 0.0.0.0:111             0.0.0.0:*               LISTEN     
tcp        0      0 0.0.0.0:6000            0.0.0.0:*               LISTEN     
tcp        0      0 192.168.122.1:53        0.0.0.0:*               LISTEN     
tcp        0      0 0.0.0.0:22              0.0.0.0:*               LISTEN     
tcp        0      0 127.0.0.1:631           0.0.0.0:*               LISTEN     
tcp        0      0 127.0.0.1:25            0.0.0.0:*               LISTEN     
tcp        0      0 172.17.0.1:40358        172.17.0.7:9092         CLOSE_WAIT 
tcp6       0      0 :::5672                 :::*                    LISTEN     
```

## 想修改ip，需要编辑哪个配置文件，修改完配置文件后，如何重启网卡，使配置生效

/etc/sysconfig/network-scripts/ifcft-eth0（如果是eth1文件名为ifcft-eth1），内容如下：

DEVICE=eth0

HWADDR=00:0C:29:06:37:BA

TYPE=Ethernet

UUID=0eea1820-1fe8-4a80-a6f0-39b3d314f8da

ONBOOT=yes

NM_CONTROLLED=yes

BOOTPROTO=static

IPADDR=192.168.147.130

NETMASK=255.255.255.0

GATEWAY=192.168.147.2

DNS1=192.168.147.2

DNS2=8.8.8.8

修改网卡后，可以使用命令重启网卡：

ifdown eth0

ifup eth0

也可以重启网络服务：

service network restart

## 能否给一个网卡配置多个IP? 如果能，怎么配置？

可以。

cat /etc/sysconfig/network-scripts/ifcfg-eth0#查看eth0的配置

DEVICE=eth0

HWADDR=00:0C:29:06:37:BA

TYPE=Ethernet

UUID=0eea1820-1fe8-4a80-a6f0-39b3d314f8da

ONBOOT=yes

NM_CONTROLLED=yes

BOOTPROTO=static

IPADDR=192.168.147.130

NETMASK=255.255.255.0

GATEWAY=192.168.147.2

DNS1=192.168.147.2

DNS2=8.8.8.8

（1）新建一个ifcfg-eth0:1文件

cp /etc/sysconfig/network-scripts/ifcfg-eth0 /etc/sysconfig/network-scripts/ifcfg-eth0:1

（2）修改其内容如下：vim /etc/sysconfig/network-scripts/ifcfg-eth0:1

DEVICE=eth0:1

HWADDR=00:0C:29:06:37:BA

TYPE=Ethernet

UUID=0eea1820-1fe8-4a80-a6f0-39b3d314f8da

ONBOOT=yes

NM_CONTROLLED=yes

BOOTPROTO=static

IPADDR=192.168.147.133

NETMASK=255.255.255.0

GATEWAY=192.168.147.2

DNS1=192.168.147.2

DNS2=8.8.8.8

（3）重启网络服务：

service network restart

## 如何查看某个网卡是否连接着交换机

mii-tool eth0 或者 mii-tool eth1

## 如何查看当前主机的主机名，如何修改主机名？要想重启后依旧生效，需要修改哪个配 置文件呢？

查看主机名：hostname

centos6.5

修改主机名：hostname centos6.5-1

永久生效需要修改配置文件：vim /etc/sysconfig/network

NETWORKING=yes

HOSTNAME=centos6.5-1

## 设置DNS需要修改哪个配置文件

（1）在文件 /etc/resolv.conf 中设置DNS

（2）在文件 /etc/sysconfig/network-scripts/ifcfg-eth0 中设置DNS

## 使用iptables 写一条规则：把来源IP为192.168.1.101访问本机80端口的包直接拒绝

iptables -I INPUT -s 192.168.1.101 -p tcp --dport 80 -j REJECT

## 要想把iptable的规则保存到一个文件中如何做？如何恢复？

使用iptables-save重定向到文件中：iptables-save > 1.ipt

使用iptables-restore反重定向回来：iptables-restore < 1.ipt

## 如何备份某个用户的任务计划

将/var/spool/cron/目录下指定用户的任务计划拷贝到备份目录cron_bak/下即可

cp /var/spool/cron/rachy /tmp/bak/cron_bak/

## 任务计划格式中，前面5个数字分表表示什么含义

依次表示：分、时、日、月、周

## 如何可以把系统中不用的服务关掉

（1）使用可视化工具：ntsysv

（2）使用命令：chkconfig servicename off

## 如何让某个服务（假如服务名为 nginx）只在3,5两个运行级别开启，其他级别关闭

先关闭所有运行级别：chkconfig nginx off

然后打开35运行级别：chkconfig --level 35 nginx on

## rsync 同步命令中，下面两种方式有什么不同呢

(1) rsync -av  /dira/  ip:/dirb/

(2) rsync -av  /dira/  ip::dirb

答：(1)前者是通过ssh方式同步的

(2)后者是通过rsync服务的方式同步的

## rsync 同步时，如果要同步的源中有软连接，如何把软连接的目标文件或者目录同步

同步源文件需要加-L选项

## 某个账号登陆linux后，系统会在哪些日志文件中记录相关信息

用户身份验证过程记录在/var/log/secure中，登录成功的信息记录在/var/log/wtmp。

## 网卡或者硬盘有问题时，我们可以通过使用哪个命令查看相关信息

使用命令dmesg

## 分别使用xargs和exec实现这样的需求，把当前目录下所有后缀名为.txt的文件的权限修改为777

（1）find ./ -type f -name "*.txt" |xargs chmod 777

（2）find ./ -type f -name "*.txt" -exec chmod 777 {} ;

## 有一个脚本运行时间可能超过2天，如何做才能使其不间断的运行，而且还可以随时观察脚本运行时的输出信息

使用screen工具

## 在Linux系统下如何按照下面要求抓包：只过滤出访问http服务的，目标ip为192.168.0.111，一共抓1000个包，并且保存到1.cap文件中

tcpdump -nn -s0 host 192.168.0.111 and port 80 -c 1000 -w 1.cap

## rsync 同步数据时，如何过滤出所有.txt的文件不同步

加上--exclude选项：--exclude=“*.txt”

## rsync同步数据时，如果目标文件比源文件还新，则忽略该文件，如何做

保留更新使用-u或者--update选项

## 想在Linux命令行下访问某个网站，并且该网站域名还没有解析，如何做

在/etc/hosts文件中增加一条从该网站域名到其IP的解析记录即可，或者使用curl -x

## 自定义解析域名的时候，我们可以编辑哪个文件？是否可以一个ip对应多个域名？是否一个域名对应多个ip

编辑 /etc/hosts ,可以一个ip对应多个域名，不可以一个域名对多个ip

## 我们可以使用哪个命令查看系统的历史负载（比如说两天前的）

sar -q -f /var/log/sa/sa22  #查看22号的系统负载

## 在Linux下如何指定dns服务器，来解析某个域名

使用dig命令：dig @DNSip  domain.com

如：dig @8.8.8.8 www.baidu.com#使用谷歌DNS解析百度

## 使用rsync同步数据时，假如我们采用的是ssh方式，并且目标机器的sshd端口并不是默认的22端口，那我们如何做

rsync "--rsh=ssh -p 10022"或者rsync -e "ssh -p 10022"

## rsync同步时，如何删除目标数据多出来的数据，即源上不存在，但目标却存在的文件或者目录

加上--delete选项

## 使用free查看内存使用情况时，哪个数值表示真正可用的内存量

free列第二行的值

## 有一天你突然发现公司网站访问速度变的很慢很慢，你该怎么办呢

可以从两个方面入手分析：分析系统负载，使用w命令或者uptime命令查看系统负载，如果负载很高，则使用top命令查看CPU，MEM等占用情况，要么是CPU繁忙，要么是内存不够，如果这二者都正常，再去使用sar命令分析网卡流量，分析是不是遭到了攻击。一旦分析出问题的原因，采取对应的措施解决，如决定要不要杀死一些进程，或者禁止一些访问等。

## rsync使用服务模式时，如果我们指定了一个密码文件，那么这个密码文件的权限应该设置成多少才可以

600或400