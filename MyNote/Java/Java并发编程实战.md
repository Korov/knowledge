# 1 简介

线程是CPU调度的基本单位。

# 2 线程安全性

在构建稳健的并发程序时，必须正确地使用线程和锁。但这些终归只是一些机制。要编写线程安全的代码，其核心在于要对状态访问操作进行管理，特别是对共享的和可变的状态的访问。

当多个线程访问某个状态变量并且其中有一个线程执行写入操作，必须采用同步机制来协同这些线程对变量的访问。Java中的主要同步机制是关键字synchronize，它提供了一种独占的加锁方式，单**同步**这个术语还包括volatile类型的变量，显示锁以及原子变量。

**有时候，面向对象中的抽象和封装会降低程序的性能，但在编写并发应用程序时，一种正确的编程方法就是：首先使代码正确运行，然后再提高代码的速度。**

## 2.1 什么是线程安全性

当多个线程访问某个类时，这个类始终都应该表现出正确的行为，那么就称这个类是线程安全的。

无状态对象（它既不包含任何域，也不包含任何对其他类中域的引用）一定是线程安全的。

## 2.2 原子性

在并发编程中，由于不恰当的执行时序而出现不正确的结果是一种非常重要的情况，被称为竞态条件。

假定有两个操作A和B，如果从执行A的线程来看，当另一个线程执行B时，要么将B全部执行完，要么完全不执行B，那么A和B对彼此来说是原子的。

## 2.3 内置锁

Java提供了一种内置的锁机制来支持原子性：同步代码块。同步代码块包括两部分：一个作为锁的对象引用，一个作为由这个锁保护的代码块。以关键字synchronized来修饰的方法就是一种横跨整个方法体的同步代码块，其中该同步代码块的锁就是方法调用所在的对象。静态的synchronized方法以Class对象作为锁。

每个Java对象都可以用作一个实现同步的锁，这些锁被称为内置锁或监视锁。线程在进入同步代码块之前会自动获得锁，并且在推出同步代码块时自动释放锁，而无论是通过正常的控制路径退出，还是通过从代码块中抛出异常退出。获得内置锁的唯一途径就是进入由这个锁保护的同步代码块或方法。

Java的内置锁相当于一种互斥体，这意味着最多只有一个线程能持有这种锁。

## 2.4 重入

当某个线程请求一个由其他线程持有的锁时，发出请求的线程就会阻塞。然而，由于内置锁是可重入的，因此如果某个线程试图获得一个已经由它自己持有的锁，那么这个请求就会成功。**“重入”意味着获取锁的操作的粒度是线程，而不是调用**。重入的一种实现方法是，为每个锁关联一个获取计数值和一个所有者线程。当计数值为0时，这个锁就被认为是没有被任何线程持有。当线程请求一个未被持有的锁时，JVM将记下锁的持有者，并且将获取计数值置为1.如果同一个线程再次获取这个锁，计数值将递增，而当线程退出同步代码块时，计数器会相应的递减。当计数值为0时，这个锁将被释放。

## 2.5 用锁来保护状态

一种常见的错误是认为，只有在写入共享变量时才需要使用同步，然而事实并非如此。对于可能被多个线程同时访问的可变状态变量，在访问它时都需要持有同一个锁，在这种情况下，我们称状态变量是由这个锁保护的。

**每个共享的和可变的变量都应该只由一个锁来保护，从而使维护人员知道是哪一个锁。**

一种常见的枷锁约定是，将所有的可变状态都封装在对象内部，并通过对象的内置锁对所有访问可变状态的代码路径进行同步，使得在该对象上不会发生并发访问。

## 2.5 活跃性和性能

被锁锁定的代码块越少则线程的并发性越高，但是锁定的代码太少有可能导致频繁的线程切换问题，导致性能损耗。

**通常，在简单性与性能之间存在着相互制约因素。当实现某个同步策略时，一定不要盲目的为了性能而牺牲简单性（这可能破坏安全性）**

当执行计算密集的操作，或者某个可能阻塞的操作，若果持有锁的时间过长，那么都会带来活跃性或性能问题。

# 3 对象的共享

同步除了用于实现原子性或者确定“临界区”，还有另一个重要的方面：内存可见性。当某个线程正在使用对象状态而另一个线程在同时修改该状态，并且希望确保当一个线程修改了对象状态后，其他线程能够看到发生的状态变化。同步可以实现。

## 3.1 可见性

### 3.1.1 非原子的64位操作

当线程在没有同步的情况下读取变量时，可能会得到一个失效值，但至少这个值是由之前某个线程设置的值，而不是一个随机值。这种安全性保证也被称为最低安全性。

最低安全性适用于绝大多数便令，但存在一个例外：非volatile类型的64位数值变量（double和long）。Java内存模型要求，变量的读取操作和写入操作必须是原子操作，但对于非volatile类型的long和double变量，JVM允许将64位的读写操作分解为两个32位的操作。当读取一个非volatile类型的变量时，如果对该变量的读操作写写操作在不同的线程中执行，那么很可能会读取到某个值的高32位和另一个值的低32位。

### 3.1.2 加锁与可见性

内置锁可以用于确保某个线程以一种可预测的方式来查看另一个线程的执行结果，当线程A执行某个同步代码块时，线程B随后进入由同一个锁保护的同步代码块，在这种情况下可以保证，在锁被释放之前，A看到的变量值在B获得锁后同样可以由B看到。换句话说，当线程B执行由锁保护的同步代码块时，可以看到线程A之前在同一个同步代码块中的所有操作结果。

**加锁的含义不仅仅局限于互斥行为，还包括内存可见性。为了确保所有线程都能看到共享变量的最新值，所有执行读操作或者写操作的线程都必须在同一个锁上同步。**

### 3.1.3 Volatile变量

当把变量声明为volatile类型后，编译器与运行时都会注意到这个变量是共享的，因此不会讲该变量上的操作与其他内存操作一起重排序。**volatile变量不会被缓存在寄存器或者对其他处理器不可见的地方**，因此在读取volatile类型的变量时总会放回最新写入的值。

**获取volatile变量的值是直接从主内存中获取，改的话也是直接改到主内存中，不会放到线程私有内存中**

## 3.2 发布与逸出

发布（Publish）一个对象的意思是指，使对象能够在当前作用域之外的代码中使用。当某个不应该发布的对象被发布时，这种情况被称为逸出（Escape）。

发布对象的最简单方法是将对象的引用保存到一个公有的静态变量中，以便任何类和线程都能看见该对象。当发布某个对象时，可能会间接地发布其他对象。例如发布的对象A中有B对象的引用。

当发布一个对象时，在该对象的非私有域中引用的所有对象同样会被发布。一般来说，如果一个已经发布的对象能够通过非私有的变量引用和方法调用达到其他的对象，那么这些对象也都会被发布。

**安全的对象构造过程**：如果this引用在构造过程中逸出，那么这种对象就被认为是不正确的构造。因此不要在构造过程中让this引用逸出。

## 3.3 线程封闭

数据仅在单线程中被访问，即数据不共享。线程封闭式是实现线程安全的最简单的方式之一。

### 3.3.1 Ad-hoc线程限制

指维护线程限制性的任务全部落在实现上的这种情况。因为没有可见性修饰符与本地变量等语言特性协助将对象限制在目标线程上，所以这种方式非常容易出错。

### 3.3.2 栈限制

栈限制是线程限制的一种特例，在栈限制中，只能通过本地变量才可以触及对象。

### 3.3.3 ThreadLocal

**ThreadLocal提供了线程内存储变量的能力，并且使线程安全的，每个ThreadLocal只能自己get和set**

更加规范的线程限制方式。它允许你将每个线程与持有数值的对象关联在一起。ThreadLocal提供了get与set访问器，为每个使用它的线程维护一份单独的拷贝。所以get总是返回由**当前执行线程**通过set设置的最新值。

ThreadLocal变量通常用于防止在基于可变的单体（Singleton）或全局变量的设计中，出现共享。

这项技术还用于下面的情况：一个频繁执行的操作既需要像buffer这样的临时对象，同时还需要避免每次都重分配该临时对象。实现一个应用程序框架会广泛地使用ThreadLocal。

# 4 对象的组合

### 4.2.1 java监视器模式

遵循java监视器模式的对象会把对象的所有可变状态都封装起来，并由对象自己的内置锁来保护。

注意**引用对象的锁**

```Java
public class ListHelper<E> {
    public List<E> list = (List<E>) Collections.synchronizedCollection(new ArrayList<E>());
    // 此锁锁的是putIfAbsent并没有锁list，此时list仍不是线程安全的
    public synchronized boolean putIfAbsent(E x){
        boolean absent = !list.contains(x);
        if(absent){
            list.add(x);
        }
        return absent;
        
    }
}

// 正确地应该如下对引用对象加锁
public class ListHelper<E> {
    public List<E> list = (List<E>) Collections.synchronizedCollection(new ArrayList<E>());
    
    public boolean putIfAbsent(E x){
        synchronized(list){
            boolean absent = !list.contains(x);
            if(absent){
                list.add(x);
            }
            return absent;
        }
        
    }
}
```



## 4.3 线程安全性的委托

大多数对象都是组合对象。当从头开始构建一个类，或者将多个非线程安全的类组合为一个类时，Java监视器模式是非常有用的。但是，如果类中的各个组件都已经是线程安全的，我们是否需要再增加一个额外的线程安全层，这需要视情况而定。在某些情况下，通过多个线程安全类组合而成的类是线程安全的。

# 5 基础构建模块

## 5.1 同步容器类

同步容器类包括Vector和Hashtable。这些类实现线程安全的方式是：将它们的状态封装起来，并对每个公有方法都进行同步，使得每次只有一个线程能访问容器的状态。

### 5.1.1 同步容器类的问题

同步容器类都是线程安全的，但在某些情况下可能需要额外的客户端加锁来保护复合操作。容器上常见的复合操作包括：迭代、跳转以及条件运算在没有客户端加锁的情况下仍然是线程安全的。其他情况就有可能出现意料之外的行为。

Vector容器在获取数组长度的时候并没有加锁，此时获取的长度有可能是错误的，导致出现意想不到的结果。迭代器也是，当一边迭代一边并发修改的时候就可能出现错误。

### 5.1.2 隐藏迭代器

对所有共享容器进行迭代的地方都需要加锁，但是有部分迭代器会隐藏起来，要仔细排查。

**容器的hashCode和equals等方法也会间接地执行迭代操作，containsAll、removeAll等方法也会隐藏调用迭代器。**

## 5.2 并发容器

同步容器将所有对容器状态的访问都串行化，以实现它们的线程安全。这种方法的代价是严重降低并发性，当多个线程竞争容器的锁时，吞吐量将严重减低。

并发容器是针对多个线程并发访问设计的。**通过并发容器来代替同步容器，可以极大地提高伸缩性并降低风险。**

### 5.2.1 ConcurrentHashMap

ConcurrentHashMap与HashMap一样是一个基于散列的Map，但它使用了一种粒度更细的加锁机制来实现更大程度的共享，这种机制称为分段锁（Lock Striping）。在这种机制中，任意数量的**读取**线程可以并发地访问Map，执行**读取操作**的线程和执行**写入操作**的线程可以并发地访问Map，并且**一定数量的写入线程**可以并发地修改Map。其结果是在并发访问环境下将实现更高的吞吐量，而在单线程环境中只损失非常小的性能。

ConcurrentHashMap提供的迭代器不会抛出ConcurrentModificationException，因此不需要子迭代过程中对容器加锁。其迭代器具有弱一致性，而非及时失败。弱一致性的迭代器可以容忍并发的修改，当创建迭代器时会遍历已有的元素，并可以（但是不保证）在迭代器被构造后将修改操作反映给容器。

### 5.2.2 额外的院子Map操作

ConcurrentHashMap不能被加锁来执行独占访问。可以使用ConcurrentMap来实现相应的功能。

### 5.2.3 CopyOnWriteArrayList

用于替代同步List，在某些情况下它提供了更好的并发性能，并且在迭代期间不需要对容器进行加锁或复制。（类似的，CopyOnWriteArraySet用于替代同步Set）。

其线程安全性在于，只要正确的发布一个事实不可变的对象，那么在访问该对象时就不需要进一步同步。在每次修改时，都会创建并重新发布一个新的容器副本，从而实现可变性。写入时复制容器的迭代器保留一个指向底层基础数组的引用，这个数组当前位于迭代器的起始位置，由于它不会被修改，因此在对其进行同步时只需要确保数组内容的可见性。但是每次修改都会复制底层数组，所以应谨慎使用。

## 5.3 阻塞队列和生产者-消费者模式

阻塞队列提供了可阻塞的put和take方法，以及支持定时的offer和poll方法。如果对垒已经满了，那么put方法将阻塞直到有空间可用；如果队列为空，那么take方法将会阻塞直到有元素可用。队列可以是有界的也可以是无界的，无界队列永远都不会充满，因此无限队列上的put方法也永远不会阻塞。offer方法，如果数据项不能被添加到队列中，那么将返回一个失败状态。

**有界队列是强大的资源管理工具，用来建立可靠的应用程序：他们遏制那些可以产生过多工作量、具有威胁的活动，从而让你的程序在面对超负荷工作时更加健壮**

## 5.4 阻塞和可中断的方法

线程可能会因为几种原因呗阻塞或暂停：等待I/O操作结束，等待获得一个锁，等待从Thread.sleep中唤醒，或者是等待另一个线程计算结果。当一个线程阻塞时，它通常被挂起，并被设置成线程阻塞的某个状态。

中断是一种协作机制。一个线程不能够迫使其他线程停止正在做的事情，或者去做其他事情当线程A中断B时，A仅仅是要求B在达成某个方便停止的关键点时，停止正在做的事情。

当你在代码中调用了一个会抛出InterruptedException的方法时，你自己的方法也成为了一个阻塞方法，要为响应中断做好准备。两种基本的处理中断方法：

- 传递InterruptedException，将异常传递给调用让他去处理
- 恢复中断：捕获异常并在当前线程中通过调用interrupt从中断恢复。

## 5.5 Synchronizer

Synchronizer是一个对象，它根据本身的状态调节线程的控制流。阻塞队列可以扮演一个Synchronizer的角色；其他类型的Synchronizer包括信号量（semaphore）、关卡（barrier）以及闭锁（latch）。

所有Synchronizer都享有类似的结构特性：他们封装状态，而这些状态决定着线程执行到某一点时是通过还是被迫等待；他们还提供操控状态的方法，以及高效的等待Synchronizer进入到期望状态的方法。

### 5.5.1 闭锁

闭锁是一种Synchronizer，它可以延迟线程的进度直到线程到达终止状态。一个闭锁工作起来就像一道大门：直到闭锁达到终点状态之前，门一直是关闭的，没有线程能够通过，在终点状态到来的时候，门开了，允许所有线程都通过。一旦闭锁到达了终点状态，它就不能够再改变状态了，所以它永远保持敞开状态。

CountDownLatch是一个灵活的闭锁实现，允许一个或多个线程等待一个事件集的发生。闭锁的状态包括一个计数器，初始化为一个正数，用来表现需要等待的事件数。countDown方法对计数器做减操作，表示一个事件已将发生了，而await方法等待计数器达到零，此时所有需要等待的事件都已发生。如果计数器入口值为非零，await会一直阻塞直到计数器为零，或者等待线程中断以及超时。

```java
public class TestHarness {
    public long timeTasks(int threadNumber, final Runnable task) throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(threadNumber);
        for (int i = 0; i < threadNumber; i++) {
            Thread thread = new Thread() {
                public void run() {
                    try {
                        startGate.await();
                        try {
                            task.run();
                        } finally {
                            endGate.countDown();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
        }
        long start = System.nanoTime();
        startGate.countDown();
        System.out.printf("Task start\n");
        endGate.await();
        long end = System.nanoTime();
        System.out.printf("Time is: %s\n", end - start);
        return end - start;
    }
}
```

TestHarness阐释了闭锁的两种常见用法。TestHarness创建了一些线程，并发地执行给定的任务。它使用两个闭锁，一个**开始阀门**和一个**结束阀门**。开始阀门将计数器初始化为1.结束阀门将计数器初始化为工作线程的数量。每个工作线程要做的第一件事情就是等待开始阀门打开；这样做能确保直到所有线程都做好准备时才开始工作。每个线程的最后一个工作是为结束阀门减一，这样做使控制线程有效的等待，直到最后一个工作线程完成任务。

### 5.5.2 FutureTask

FutureTask同样可以作为闭锁。FutureTask实现描述了一个抽象的可携带结果的计算。FutureTask的计算是通过Callable实现的，它等价于一个可携带结果的Runnable，并且有3个状态：等待、运行和完成。包括所有计算以任意的方式结束，包括正常结束、取消和异常。一旦FutureTask进入完成状态，它会永远停止在这个状态上。

Future.get的行为依赖于任务的状态。如果它已经完成，get可以立刻得到返回结果，否则会被阻塞直到任务转入完成状态，然后返回结果或抛出异常。FutureTask把计算的结果从运行计算的线程传送到需要这个结果的线程；FutureTask的规约保证了这种传递建立在结果的安全发布基础之上。

Executor框架利用FutureTask来完成异步任务，并可以用来进行任何潜在的耗时计算，而且可以在真正需要计算结果之前就启动他们开始计算。

```java
public class PreLoader {
    ProductInfo loadProductInfo() {
        System.out.printf("ProductInfo is loading!\n");
        ProductInfo productInfo = new ProductionInfo(Thread.currentThread().getName());
        return productInfo;
    }

    private final FutureTask<ProductInfo> futureTask = new FutureTask<ProductInfo>(new Callable<ProductInfo>() {
        @Override
        public ProductInfo call() throws Exception {
            return loadProductInfo();
        }
    });
    private final Thread thread = new Thread(futureTask);

    public void start() {
        thread.start();
    }

    public ProductInfo get() throws InterruptedException {
        try {
            return futureTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
```

Preloader创建了一个FutureTask，其中包含充数据库加载产品信息的任务，以及一个执行运算的线程。由于构造函数或静态初始化方法中启动线程并不是一个好方法，因此提供一个start方法来启动线程。当程序随后需要ProductInfo时，可以调用get方法，如果数据已经加载，那么将返回这些数据，否则将等待加载完成后再返回。

### 5.5.3 信号量

计数信号量用来控制同时访问某个特定资源的操作数量，或者同时执行某个指定操作的数量。计数信号量还可以用来实现某种资源池，或者对容器施加边界。

Semaphore中管理者一组虚拟的许可（permit），许可的初始数量可通过构造函数来指定。在执行操作时可以首先获得许可，并在使用后释放许可。如果没有许可，那么acquire将阻塞直到有许可（或者直到被中断或者操作超时）。release方法将返回一个许可给信号量。计算信号量的一种简化形式是二值信号量，即初始值为1的Semaphore。二值信号量可以用作互斥体（mutex），并具备不可重入的加锁语义：谁拥有这个唯一的许可，谁就拥有了互斥锁。

可以使用Semaphore实现资源池，计数值初始化为池的大小，当获取一个线程时acquire，用完之后release。也可以使用Semaphore将任何一种容器变成有界阻塞容器。

```java
public class BoundedHashSet<T> {
    private final Set<T> set;
    private final Semaphore semaphore;

    public BoundedHashSet(int bound) {
        this.set = Collections.synchronizedSet(new HashSet<>());
        semaphore = new Semaphore(bound);
    }

    public boolean add(T o) throws InterruptedException {
        semaphore.acquire();
        boolean wasAdded = false;
        try {
            wasAdded = set.add(o);
            return wasAdded;
        } finally {
            if (!wasAdded) {
                semaphore.release();
            }
        }
    }

    public boolean remove(T o) {
        boolean wasRemoved = set.remove(o);
        if (wasRemoved) {
            semaphore.release();
        }
        return wasRemoved;
    }
}
```

### 5.5.4 栅栏

栅栏（Barrier）类似于闭锁，它能阻塞一组线程直到某个事件发生。栅栏与闭锁的关键区别在于，所有线程必须同时到达栅栏位置，才能继续执行。闭锁用于等待事件，而栅栏用于等待其他线程。

CyclicBarrier可以使一定数量的参与方反复的在栅栏位置汇集，它在并行迭代算法中非常有用。这种算法通常将一个问题拆分成一系列相互独立的子问题。当线程到达栅栏位置时将调用await方法，这个方法将阻塞直到所有线程都到达栅栏位置。如果所有线程都到达了栅栏位置，那么栅栏将打开，此时所有线程都被释放，而栅栏将被重置以便下次使用。如果对await的调用超时，或者await阻塞的线程被中断，那么栅栏就被认为是打破了，所有阻塞的await调用都将终止并抛出BrokenBarrierException。如果成功的通过栅栏，那么await将为每个线程返回一个唯一的到达索引号，我们可以利用这些索引来选举产生一个领导线程，并在下一次迭代中由该领到线程执行一些特殊的工作。

在模拟程序中通常需要使用栅栏，例如某个步骤中的计算可以并行执行，但必须等到该步骤中的所有计算都执行完毕才能进入下一个步骤。

```java
public class CellularAutomata {
    private final Board mainBoard;
    private final CyclicBarrier barrier;
    private final Worker[] workers;

    public CellularAutomata(Board board) {
        this.mainBoard = board;
        int count = Runtime.getRuntime().availableProcessors();
        this.barrier = new CyclicBarrier(count, new Runnable() {　　//
            @Override
            public void run() {
                mainBoard.commitNewValues();
            }
        });
        this.workers = new Worker[count];
        for (int i = 0; i < count; i++) {
            workers[i] = new Worker(mainBoard.getSubBoard(count, i));
        }
    }

    public void start() {
        for (int i = 0; i < workers.length; i++) {
            new Thread(workers[i]).start();
        }
        mainBoard.waitForConvergence();
    }

    private class Worker implements Runnable {
        private final Board board;
        public Worker(Board board) {
            this.board = board;
        }
        public void run() {
            while (!board.hasConverged()) {
                for (int x = 0; x < board.getMaxX(); x++) {
                    for (int y = 0; y < board.getMaxY; y++) {
                        board.setNewValue(x, y, computeValue(x, y));
                    }
                }
                try {
                    barrier.await();　　//
                } catch (InterruptedException e) {
                    return;
                } catch (BrokenBarrierException e) {
                    return;
                }
            }
        }
    }
}
```

 CellularAutomata 中给出了如何通过栅栏来计算细胞的自动化模拟，例如 Conway 的生命游戏。在把模拟过程并行化，为每个元素（在这个实例中相当于一个细胞）分配一个独立的线程是不现实的，因为这将会产生过多的线程，而在协调这些线程上导致德开销将降低计算性能。合理的做法是：将问题分解成一定数量的子问题，为每个子问题分配一个线程来进行求解，之后再将所有的结果合并起来。 

另一种形式的栅栏是Exchanger，它是一种两方栅栏，各方在栅栏位置上交换数据。当两方执行不对称的操作时，Exchanger会非常有用，例如当一个线程向缓冲区写入数据，而另一个线程从缓冲区中读取数据。这些线程可以使用Exchanger来汇合，并将满的缓冲区与空的缓冲区交换。当两个线程通过Exchanger交换对象时，这种交换就把这两个对象安全地发布给另一方。

## 5.6 构建高效且可伸缩的结果缓存

```java
public class Memoizer<A, V> implements Computable<A, V> {
    private final ConcurrentMap<A, Future<V>> cache = new ConcurrentHashMap<>();
    private final Computable<A, V> computable;

    public Memoizer(Computable<A, V> computable) {
        this.computable = computable;
    }

    @Override
    public V compute(A arg) throws InterruptedException {
        while (true) {
            Future<V> future = cache.get(arg);
            if (future == null) {
                Callable<V> eval = new Callable<V>() {
                    @Override
                    public V call() throws Exception {
                        return computable.compute(arg);
                    }
                };
                FutureTask<V> futureTask = new FutureTask<>(eval);
                future = cache.putIfAbsent(arg, futureTask);
                if (future == null) {
                    future = futureTask;
                    futureTask.run();
                }
            }
            try {
                return future.get();
            } catch (CancellationException e) {
                cache.remove(arg, future);
            } catch (ExecutionException e) {
                System.out.println("debug!");
            }
        }
    }
}
```

上面代码中Computable<A, V>接口中声明了一个函数Computable，有一个其实现类需要较长的时间来计算数据。将用于缓存值得Map重新定义为ConcurrentHashMap<A,Future\<V>>，Memoizer会首先检查某个相应的计算是否已经开始，如果还没有启动，那么就创建一个FutureTask，并注册到Map中，然后启动计算：如果已经启动，那么等待现有计算的结果。结果可能很快会得到，也可能还在运算过程中。

# 6 任务执行

## 6.1 在线程中执行任务

当围绕“任务执行”来设计应用程序结构时，第一步就是要找出清晰的任务边界。在理想情况下，各个任务之间是相互独立的：任务并不依赖于其他任务的状态、结果或边界效应。独立性有助于实现并发，因为如果存在足够多的处理资源，那么这些独立的任务都可以并行执行。为了在调度与负载均衡等过程中实现更高的灵活性，每项任务还应该表示应用程序的一小部分处理能力。

## 6.2 Executor框架

任务是一组逻辑工作单元，而线程则是使人物异步执行的机制。

Executor是一个借口，但他却为灵活且强大的异步任务执行框架提供了基础，该框架能支持多种不同类型的任务执行策略。它提供了一种标准的方法将任务的提交过程与执行过程解耦开来，并用Runnable来表示任务。

Executor基于生产者-消费者模式，提交任务的操作相当于生产者（生成待完成的工作单元），执行任务的线程则相当于消费者（执行完成这些工作单元）。

### 6.2.1 基于Executor的Web服务器

```java
public class TaskExecutionWebServer {
    private static final int NTHREADS = 100;
    private static final Executor exec = Executors.newFixedThreadPool(NTHREADS);

    public static void main(String[] args) throws IOException {
        ServerSocket socket = new ServerSocket(80);
        while (true) {
            Socket connection = socket.accept();
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    TaskExecutionWebServer.handleRequest(connection);
                }
            };
            exec.execute(task);
        }
    }

    private static void handleRequest(Socket connection) {
        System.out.println("connection is resolving!");
    }
}
```

以上代码监控本机的80端口，每次有一个请求就会创建一个线程来做相应的处理，上线是100个线程。使用了一个带有有界线程池的Executor。通过execute方法将任务提交到工作队列中，工作线程反复地从工作队列中取出任务并执行他们。

### 6.2.2 执行策略

通过将任务的提交与执行解耦开来，从而无须太大的困难就可以为某种类型的任务指定和修改执行策略。在执行策略中定义了任务执行的“what,where,when,how”等方面，包括：

- 在什么（what）线程中执行任务
- 任务按照什么（what）顺序执行（FIFO，LIFO，优先级）
- 有多少个（How Many）任务能并发执行
- 在队列中有多少个（How Many）任务在等待执行
- 如果系统由于过载而需要拒绝一个任务，那么应该选择哪一个（which）任务？另外如何（How）通知应用程序有任务被拒绝
- 在执行一个任务之前或之后，应该进行哪些（what）动作

各种执行策略都是一种资源管理工具，最佳策略取决于可用的计算资源以及对服务质量的需求。通过限制并发任务的数量，可以确保应用程序不会由于资源耗尽而失败，或者由于在稀缺资源上发生竞争而严重影响性能。通过间个任务的提交与任务的执行策略分离开来，有助与在部署阶段选择与可用硬件资源最匹配的执行策略。

每当看到下面这种形式的代码时：new Thread(runnable).start()并且你希望获得一种更灵活的执行策略时，请考虑使用Executor来代替Thread。

### 6.2.3 线程池

从子买呢含义来看，是指管理一组同构工作线程的资源池。线程池是与工作队列密切相关的，其中在工作队列中保存了所有等待执行的任务。工作者线程的任务很简单：从工作队列中获取一个任务，执行任务，然后返回线程池并等待下一个任务。

在线程池中执行任务比为每个任务分配一个线程优势更多。通过重用现有的线程而不是创建新线程，可以在处理多个请求时分摊在线程创建和销毁过程中产生的巨大开销。另一个额外的好处是，当请求到达时，工作线程通常已经存在，因此不会由于等待创建线程而延迟任务的执行，从而提高了响应性。通过适当调整线程池的大小，可以创建足够多的线程以便使处理器保持忙碌状态，同时还可以防止过多线程相互竞争资源而使应用程序耗尽内存或失败。

类库提供了一个灵活的线程池以及一些有用的默认配置。可以通过调用Executors中的静态工厂方法之一来创建一个线程池：

- newFixedThreadPool：创建一个固定长度的线程池，每当提交一个任务是就创建一个线程，直到达到线程池的最大数量，这时线程池的规模将不再变化。（如果某个线程由于发生了未预期的Exception而结束，那么线程池会补充一个新的线程）
- newCachedThreadPool：创建一个可缓存的线程池，如果线程池的当前规模超过了处理需求时，那么将回收空闲的线程，而当需求增加时，则可以添加新的线程，线程池的规模不存在任何限制。
- newSingleThreadPool：是一个单线程的Executor，它创建单个工作者线程来执行任务，如果这个线程异常结束，会创建另一个线程来替代。它能确保依照任何任务在队列中的顺序来串行执行。
- newScheduledThreadPool：创建一个固定长度的线程池，而且以延迟或定时的方式来执行任务，类似于Timer。

### 6.2.4 Executor的生命周期

为了解决执行服务的生命周期问题，Executor扩展了ExecutorService接口，添加了一些用于生命周期管理的方法（同时还有一些用于任务提交的便利方法）。

ExecutorService的生命周期有3中状态：**运行**、**关闭**和**终止**。ExecutorService在初始创建时处于运行状态。**shutdown**方法将执行平缓的关闭过程：不再接受新的任务，同时等待已经提交的任务执行完成--包括那些还未开始执行的任务。**shutdownNow**方法将执行粗暴的关闭过程：它将尝试取消所有运行中的任务，并且不再启动队列中尚未开始执行的任务。

在ExecutorService管壁厚提交的任务将由“拒绝执行处理器”来处理，它会抛弃任务，或者使得execute方法抛出一个未检查的RejectedExecutionException。等待所有任务都完成后，ExecutorService将转入终止状态。可以调用**awaitTermination**来等待ExecutorService到达终止状态，或者通过调用isTerminated来轮询executorService是否已经终止。通常在调用awaitTermination之后会立即调用shutdown，从而产生同步关闭ExecutorService的效果。

### 6.2.5 延迟任务与周期任务

如果要构建自己的调度服务，那么可以使用DelayQueue，它实现了BlockingQueue，并为ScheduledThreadPoolExecutor提供调度功能。DelayQueue管理着一组Delayed对象。每个Delayed对象都有一个相应的延迟时间：在DelayQueue中，只有某个元素逾期后，才能从DelayQueue中执行take操作。从DelayQueue中返回的对象将根据他们的延迟时间进行排序。

## 6.3 找出可利用的并行性

Executor框架帮助指定执行策略，但如果要使用Executor，必须将任务表述为一个Runnable。在大多数服务器应用程序中都存在一个明显的任务边界：单个客户请求。

本节中我们将开发一些不同版本的组件，并且每个版本都实现了额不同成都的并发性。

### 6.3.2 携带结果的任务Callable与Future

Executor框架使用Runnable作为其基本的任务表示形式。Runnable是一种很大局限的抽象，虽然run能写入到日志文件或者将结果放入某个共享的数据结构，但它不能返回一个值或抛出一个受检查的异常。

对于那些存在延迟的计算Callable是一种更好的抽象：它认为主入口点将返回一个值，并可能抛出一个异常。在Executor中包含了一些辅助方法能将其他类型的任务封装为一个Callable。

Runnable和Callable描述的都是抽象的计算任务。这些任务通常是由范围的，即都有一个明确的起始点，并且最终会结束。Executor执行的任务有4个生命周期阶段：创建、提交、开始和完成。由于有些任务可能要执行很长的时间，因此通常希望能够取消这些任务。在Executor框架中，已提交但尚未开始的任务可以取消，但对于那些已经开始执行的任务，只有当他们能响应中断时，才能取消。取消一个已经完成的任务不会有任何影响。

**Future**表示一个任务的生命周期，并提供了相应的方法判断是否已经完成或取消，以及获取任务的结果和取消任务等。在Future规范中包含的隐含意义是，任务的生命周期只能前进，不能后退，当某个任务完成后，它就永远停留在“完成”状态上。

**get**方法的行为取决于任务的状态（尚未开始、正在运行、已完成）。如果任务已经完成，那么get会立即返回或者抛出一个Exception，如果任务没有完成，那么get将阻塞并直到任务完成。如果任务抛出了异常，那么get将该异常封装为ExecutionException并重新抛出。如果任务被取消，那么将抛出CancellationException。如果get抛出了ExecutionException，那么可以通过getCause来获得被封装的初始异常。

### 6.3.3 示例：使用Future实现页面渲染器

为了使页面渲染器实现更高的并发性，首先将渲染过程分解为两个任务，一个是渲染所有的文本，另一个是下载所有的图像。

Callable和Future有助于表示这些协同任务之间的交互。Renderer中创建了一个Callable来下载所有的图像，并将其提交到一个ExecutorService。这将返回一个描述任务执行情况的Future。当主任务需要图像时，它会等待Futrue.get的调用结果。如果幸运的话，当开始请求时所有的图像就已经下载完成了，即使没有，至少图像的下载任务也已经提前开始了。

```Java
public abstract class FutureRenderer {
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    void renderPage(CharSequence source) {
        List<ImageInfo> imageInfos = scanForImageInfo(source);
        Callable<List<ImageData>> task = new Callable<List<ImageData>>() {
            @Override
            public List<ImageData> call() throws Exception {
                List<ImageData> result = new ArrayList<>();
                for (ImageInfo imageInfo : imageInfos) {
                    result.add(imageInfo.downloadImage());
                }
                return result;
            }
        };
        Future<List<ImageData>> future = executorService.submit(task);
        renderText(source);

        try {
            List<ImageData> imageData = future.get();
            for (ImageData data : imageData) {
                renderImage(data);
            }
        } catch (InterruptedException e) {
            // Re-assert the thread's interrupted status
            Thread.currentThread().interrupt();
            // We don't need the result, so cancel the task too
            future.cancel(true);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    interface ImageData {
    }

    interface ImageInfo {
        ImageData downloadImage();
    }

    abstract void renderText(CharSequence s);

    abstract List<ImageInfo> scanForImageInfo(CharSequence s);

    abstract void renderImage(ImageData i);
}
```

### 6.3.4 CompletionService：Executor与BlockingQueue

CompletionService将Executor和BlockingQueue的功能融合在一起。你可以将Callable任务提交给它来执行，然后使用类似于队列操作的take和poll等方法来获得已完成的结果，而这些结果会在完成时将被封装为Future。ExecutorCompletionService实现了CompletionService，并将计算部分委托给一个Executor。

ExecutorCompletionService的实现非常简单。在构造函数中创建一个BlockingQueue来保存计算完成的结果。当计算完成时，调用Future-Task中的done方法。当提交某个任务时，该任务将首先包装为一个QueueingFuture，这是FutureTask的一个子类，然后再改写子类的done方法，并将结果放入BlockingQueue中。take和poll方法委托给了BlockingQueue，这些方法会在得出结果之前阻塞。

### 6.3.5 示例：使用CompletionService实现页面渲染器

可以通过CompletionService从两个方面来提高页面渲染器的性能：缩短总运行时间以及提高响应性。为每一幅图像的下载都创建一个独立任务，并在线程池中执行他们，从而将串行的下载过程转换为并行的过程。此外，通过从CompletionService中获取结果以及使每张图片在下载完成后立刻显示出来，能使用户获得一个更加动态和更高响应性的用户界面。

```java
public abstract class Renderer {
    private final ExecutorService executorService;

    public Renderer(ExecutorService executorService) {
        this.executorService = executorService;
    }

    void renderPage(CharSequence source) {
        List<ImageInfo> infos = scanForImageInfo(source);
        CompletionService<ImageData> completionService = new ExecutorCompletionService<>(executorService);
        for (ImageInfo imageInfo : infos) {
            completionService.submit(new Callable<ImageData>() {
                @Override
                public ImageData call() throws Exception {
                    return imageInfo.downloadImage();
                }
            });
        }
        renderText(source);

        try {
            for (int t = 0, n = infos.size(); t < n; t++) {
                Future<ImageData> future = completionService.take();
                ImageData imageData = future.get();
                renderImage(imageData);
            }
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
        }
    }

    abstract void renderText(CharSequence s);

    abstract List<ImageInfo> scanForImageInfo(CharSequence s);

    abstract void renderImage(ImageData i);
}
```

多个ExecutorCompletionService可以共享一个Executor，因此可以创建一个对于特定计算私有，又能共享一个公共Executor的ExecutorCompletionService。因此，CompletionService的作用就相当于一组计算句柄，这与Future作为单个计算的句柄是非常类似的。通过记录提交给CompletionService的任务数量，并计算出已经获得的已完成结果的数量，即使使用一个共享的Executor，也能知道已经获得了所有任务结果的时间。

### 6.3.6 为人物设置时限

如果某个任务无法再指定时间内完成，那么将不再需要他的结果，此时可以放弃这个任务。

在支持时间限制的Future.get中支持这种需求：当结果可用时，它将立即返回，如果在指定时限内没有计算出结果，那么间将抛出TimeoutException。

在使用限时任务时需要注意，当这些任务超时后应该立即停止，从而避免为继续计算一个不再使用的寄过而浪费计算资源。

下面代码示例：在生成的页面中包括响应用户请求的内容以及从广告服务器上获得的广告。它将获取广告的任务提交给一个Executor，然后计算剩余的文本页面内容，最后等待广告信息，直到超出指定的时间。如果geit超时，那么将取消广告获取任务，并转而使用默认的广告信息。

```java
public class RenderWithTimeBudget {
    private static final Ad DEFAULT_AD = new Ad();
    private static final long TIME_BUDGET = 1000;
    private static final ExecutorService exec = Executors.newCachedThreadPool();

    Page renderPageWithAd() throws InterruptedException {
        long endNanos = System.nanoTime() + TIME_BUDGET;
        Future<Ad> f = exec.submit(new FetchAdTask());
        // Render the page while waiting for the ad
        Page page = renderPageBody();
        Ad ad;
        try {
            // Only wait for the remaining time budget
            long timeLeft = endNanos - System.nanoTime();
            ad = f.get(timeLeft, NANOSECONDS);
        } catch (ExecutionException e) {
            ad = DEFAULT_AD;
        } catch (TimeoutException e) {
            ad = DEFAULT_AD;
            f.cancel(true);
        }
        page.setAd(ad);
        return page;
    }

    Page renderPageBody() { return new Page(); }


    static class Ad {
    }

    static class Page {
        public void setAd(Ad ad) { }
    }

    static class FetchAdTask implements Callable<Ad> {
        @Override
        public Ad call() {
            return new Ad();
        }
    }

}
```

### 6.3.7 示例：旅行预订门户网站

如下示例使用了支持限时的invokeAll，将多个任务提交到一个ExecutorService并获得结果。InvokeAll方法的参数为一组任务，并返回一组Future。这两个集合有这相同的结构。invokeAll按照任务集合中迭代器的顺序将所有的Future添加到返回的集合中，从而使调用者能将各个Future与其表示的Callable关联起来。当所有任务都执行完毕时，或者调用线程被中断时，又或者超过指定时限时，invokeAll将返回。当超过指定时限后，任何还未完成的任务都会被取消。当invokeAll返回后，每个任务要么正常地完成，要么被取消，而客户端代码可以调用get或isCancelled来判断究竟是何种情况。

```Java
public class TimeBudget {
    private static ExecutorService exec = Executors.newCachedThreadPool();

    public List<TravelQuote> getRankedTravelQuotes(TravelInfo travelInfo, Set<TravelCompany> companies,
                                                   Comparator<TravelQuote> ranking, long time, TimeUnit unit)
            throws InterruptedException {
        List<QuoteTask> tasks = new ArrayList<QuoteTask>();
        for (TravelCompany company : companies) {
            tasks.add(new QuoteTask(company, travelInfo));
        }

        List<Future<TravelQuote>> futures = exec.invokeAll(tasks, time, unit);

        List<TravelQuote> quotes =
                new ArrayList<TravelQuote>(tasks.size());
        Iterator<QuoteTask> taskIter = tasks.iterator();
        for (Future<TravelQuote> f : futures) {
            QuoteTask task = taskIter.next();
            try {
                quotes.add(f.get());
            } catch (ExecutionException e) {
                quotes.add(task.getFailureQuote(e.getCause()));
            } catch (CancellationException e) {
                quotes.add(task.getTimeoutQuote(e));
            }
        }

        Collections.sort(quotes, ranking);
        return quotes;
    }

}

class QuoteTask implements Callable<TravelQuote> {
    private final TravelCompany company;
    private final TravelInfo travelInfo;

    public QuoteTask(TravelCompany company, TravelInfo travelInfo) {
        this.company = company;
        this.travelInfo = travelInfo;
    }

    TravelQuote getFailureQuote(Throwable t) {
        return null;
    }

    TravelQuote getTimeoutQuote(CancellationException e) {
        return null;
    }

    @Override
    public TravelQuote call() throws Exception {
        return company.solicitQuote(travelInfo);
    }
}

interface TravelCompany {
    TravelQuote solicitQuote(TravelInfo travelInfo) throws Exception;
}

interface TravelQuote {
}

interface TravelInfo {
}
```

# 7 取消与关闭

要使任务和线程能安全、快速、可靠的停止下来，并不是一件容易的事。Java没有提供任何机制来安全地终止线程。但他提供了中断（Interruption），这是一种协作机制，能够使一个线程终止另一个线程的当前工作。

本章将给出各种实现取消和中断的机制，以及如何编写任务和服务，使他们额能对取消请求做出响应。

## 7.1 任务取消

如果外部代码能在某个操作正常完成之前将其置入“完成”状态，那么这个操作就可以称为可取消的（Cancellable）。取消某个操作的原因很多：用户请求取消、有时间限制的操作、应用程序事件、错误、关闭。

在Java中没有一种安全的抢占式方法来停止线程，因此也就没有安全的抢占式方法来停止任务。只有一些写作式的机制，使请求取消的任务和代码都遵循一种协商好的协议。

其中一种协作机制能设置某个“已请求取消（Cancellation Requested）”标志，而任务将定期地查看该标志。如果设置了这个标志，那么任务将提前结束。

```Java
public class PrimeGenerator implements Runnable {
    private final List<BigInteger> primes = new ArrayList<>();
    private volatile boolean cancelled;

    @Override
    public void run() {
        BigInteger p = BigInteger.ONE;
        while (!cancelled) {
            p = p.nextProbablePrime();
            synchronized (this) {
                primes.add(p);
            }
        }
    }

    public void cancel() {
        cancelled = true;
    }

    public synchronized List<BigInteger> get() {
        return new ArrayList<>(primes);
    }
}
```

### 7.1.1 中断

PrimeGenerator中的取消机制最终会使得搜索素数的任务退出，但在退出的过程中需要花费一定的时间。然而，如果使用这种方法的任务调用了一个阻塞方法，例如BlockingQueue.put，那么可能会产生一个更严重的问题--在检查取消标志之前线程阻塞了，任务可能永远不会检查取消标志，因此永远不会结束。

阻塞库方法，例如Thread.sleep和Object.wait等，都会检查线程何时中断，并且在发现中断时提前返回。他们在响应中断时执行的操作包括：清除中断状态，抛出InterruptedException，表示阻塞操作由于中断而提前结束。JVM并不能保证阻塞方法检测到终端的速度，但在实际情况中响应速度还是非常快的。

当线程在非阻塞状态下中断时，它的中断状态将被设置，然后根据将被取消的操作来检查中断状态以判断发生了中断。通过这样的方法，中断操作将变得“有粘性”--如果补触发InterruptedException，那么中断状态将一直保持，直到明确地清除中断状态。

对终端操作的正确理解是：它并不会真正地中断一个正在运行的线程，而只是发出中断请求，然后由线程在下一个合适的时刻中断自己。

**调用interrupt并不意味着立即停止目标线程正在进行的工作，而只是传递了请求中断的消息。**

在使用静态的interrupted时应该小心，因为它会清除当前线程的中断状态。如果调用interrupted时返回了true，那么除非你想屏蔽这个中断，否则必须对它进行处理--可以抛出InterruptedException，或者通过再次调用interrupt来恢复中断状态。

```Java
public class PrimeProducer extends Thread {
    private final BlockingQueue<BigInteger> queue;

    PrimeProducer(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            BigInteger p = BigInteger.ONE;
            // 判断线程是否处于中断状态，若处于中断状态则抛出一个异常
            while (!Thread.currentThread().isInterrupted()) {
                queue.put(p = p.nextProbablePrime());
            }
        } catch (InterruptedException consumed) {
            System.out.printf("Thread: %s is interrupted.\n", Thread.currentThread().getName());
        }
    }

    void cancel() {
        // 通过调用中断设置其为true将此线程的状态设置为interrupted
        interrupt();
    }
}
```

### 7.1.2 中断策略

中断策略规定线程如何解释某个中断请求--当发现中断请求时，应该做那些工作，那些工作单元对于终端来说是原子操作，以及以多快的速度来响应中断。

最合理的中断策略是某种形式的线程级取消操作或服务级取消操作：尽快退出，在必要时进行清理，通知某个所有者该线程已经退出。此外还可以建立其他的中断策略，例如暂停服务或重新开始服务，但对于那些包含非标准中断策略的线程或线程池，只能用于能知道这些策略的任务中。

区分任务和线程对终端的反应是很重要的。一个中断请求可以有一个或多个接受者--中断线程池中的某个工作者线程，同时意味着“取消当前任务”和“关闭工作者线程”。

任务不会再其自己拥有的线程中执行，而是在某个服务（例如线程池）拥有的线程中执行。

**由于每个线程拥有各自的中断策略，因此除非你知道中断对该线程的含义，否则就不应该中断这个线程。**

### 7.1.3 响应中断

有两种使用策略可用于处理InterruptedException：

- 传递异常，从而使你的方法也称为可中断的阻塞方法（将异常throws）
- 恢复中断状态，从而使调用栈中的上层代码能够对其进行处理（catch异常进行下一步处理）

对于一些不支持取消但仍可以调用中断阻塞方法的操作，他们必须在循环中调用这些方法，并在发现中断后重新尝试。

```Java
public Task getNextTask(BlockingQueue<Task> queue){
    boolean interrupted = false;
    try{
        while(true){
            try{
                return queue.take();
            }catch(InterruptedException e){
                interrupted = true;// 重新尝试
            }
        }
    }finally{
        if(interrupted){
            Thread.correntThread().interrupt();
        }
    }
}

```

### 7.1.4 示例：计时运行

以下代码给出了在指定时间内运行一个任务的Runnable的示例。它在调用线程中运行任务，并安排了一个取消任务，在运行指定的时间间隔后中断它。这解决了从任务中抛出未检查异常的问题，因为该异常会被timedRun的调用者捕获。在启动任务线程之后，timedRun将执行一个限时的join方法。在join返回后，它将检查任务中是否有异常抛出，如果有的话，则会在调用timedRun的线程中再次抛出该异常。由于Throwable将在两个线程之间共享，因此变量将被声明为volatile类型，从而确保安全地将其从任务线程发布到timedRun线程。

```Java
class TimedRun {
    private static final ScheduledExecutorService cancelExec = newScheduledThreadPool(1);

    static void timedRun(Runnable r, long timeout, TimeUnit unit) throws InterruptedException {
        class RethrowableTask implements Runnable {
            private volatile Throwable t;

            @Override
            public void run() {
                try {
                    System.out.printf("Thread:%s is start\n", Thread.currentThread().getName());
                    r.run();
                } catch (Throwable t) {
                    this.t = t;
                }
            }

            private void rethrow() {
                if (t != null) {
                    throw launderThrowable(t);
                }
            }
        }

        RethrowableTask task = new RethrowableTask();
        Thread taskThread = new Thread(task);
        System.out.printf("Thread:%s is start, time is :%s\n", Thread.currentThread().getName(), System.currentTimeMillis());
        taskThread.start();
        cancelExec.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.printf("Thread:%s begin to end task, time is :%s\n", Thread.currentThread().getName(), System.currentTimeMillis());
                taskThread.interrupt();
            }
        }, timeout, unit);
        taskThread.join(unit.toMillis(timeout));
        task.rethrow();
    }
}
```

### 7.1.5 通过Future来实现取消

ExecutorService.submit将返回一个Future来描述任务。Future拥有一个cancel方法，该方法带有一个boolean类型的参数mayInterruptIfRunning，表示取消操作是否成功。如果mayInterruptIfRunning为true并且任务当前正在某个线程中运行，那么这个线程能被中断。如果这个参数为false，那么意味着“若任务还没有启动，就不要运行它”，这种方式应该用于那些不处理中断的任务。

在什么情况下调用cancel可以将参数指定为true，执行任务的线程是由标准的Executor创建的，它实现了一种中断策略使得任务可以通过终端被取消。当尝试取消某个任务时，不宜直接中断线程池，因为你并不知道当中断请求达到时正在运行什么任务--只能通过任务的Future来实现取消。

以下代码：将任务提交给一个ExecutorService，并通过一个定时的Future.get来获得结果。如果get在返回时抛出了一个TimeoutException，那么任务将通过他的Future来取消。

```Java
public class TimedRun {
    private static final ExecutorService taskExec = Executors.newCachedThreadPool();

    public static void timedRun(Runnable r,
                                long timeout, TimeUnit unit)
            throws InterruptedException {
        Future<?> task = taskExec.submit(r);
        try {
            task.get(timeout, unit);
        } catch (TimeoutException e) {
            // task will be cancelled below
        } catch (ExecutionException e) {
            // exception thrown in task; rethrow
            throw launderThrowable(e.getCause());
        } finally {
            // Harmless if task already completed
            task.cancel(true); // interrupt if running
        }
    }
}
```

### 7.1.6 处理不可中断的阻塞

在Java库中，许多可阻塞的方法都是通过提前返回或者抛出InterruptedException来响应中断请求的，从而使开发人员更容易构建出能响应取消请求的任务。对于那些由于执行不可中断操作而被阻塞的线程，可以使用类似于中断的手段来停止这些线程，但这要求我们必须知道线程阻塞的原因。

 **关键步骤就是重写原来中断线程或者取消任务的方法，在方法里面加入自己的取消操作，比如关闭数据流，关闭套接字等，然后再调用父类的中断方法，这样就可以既关闭了阻塞的任务，又中断了线程。** 

**Java.io包中的同步Socket I/O**。在服务器应用程序中，最常见的阻塞I/O形式就是对套接字进行读取和写入。虽然InputStream和OutputStream中的read和write等方法都不会响应中断，但通过关闭底层的套接字，可以使得由于执行read和write等方法而被阻塞的线程抛出一个SocketException。

**Java.io包中的同步I/O**。当中断一个正在InterruptibleChannel上等待的线程时，将抛出ClosedByInterruptException并关闭链路。当关闭一个InterruptibleChannel时，将导致所有在链路操作上阻塞的线程都抛出AsynchronousClosedException。大多数标准的Channel都实现了InterruptibleChannel。

**Selector的异步I/O**。如果一个线程在调用Selector.select方法时阻塞了，那么调用close或wakeup方法会使线程抛出ClosedSelectorException并提前返回。

**获取某个锁**。如果一个线程由于等待某个内置锁而阻塞，那么将无法响应中断。在Lock类中提供了lockInterruptibly方法，该方法允许在等待一个锁的同时仍能响应中断。

```Java
public class ReaderThread extends Thread {
    private static final int BUFSZ = 512;
    private final Socket socket;
    private final InputStream in;

    public ReaderThread(Socket socket) throws IOException {
        this.socket = socket;
        this.in = socket.getInputStream();
    }

    public void interrupt() {
        try {
            socket.close();
        } catch (IOException ignored) {
        } finally {
            super.interrupt();
        }
    }

    public void run() {
        try {
            byte[] buf = new byte[BUFSZ];
            while (true) {
                int count = in.read(buf);
                if (count < 0)
                    break;
                else if (count > 0)
                    processBuffer(buf, count);
            }
        } catch (IOException e) { /* Allow thread to exit */
        }
    }

    public void processBuffer(byte[] buf, int count) {
    }
}
```

### 7.1.7 采用newTaskFor来封装非标准的取消

当把一个Callable提交给ExecutorService时，submit方法会返回一个Future，我们可以通过这个Future来取消任务。newTaskFor还能返回一个RunnableFuture接口，该接口扩展了Future和Runnable。

以下代码中，CancellableTask中定义了一个CancellableTask接口，该

```java
public abstract class SocketUsingTask <T> implements CancellableTask<T> {
    @GuardedBy("this") private Socket socket;

    protected synchronized void setSocket(Socket s) {
        socket = s;
    }

    public synchronized void cancel() {
        try {
            if (socket != null)
                socket.close();
        } catch (IOException ignored) {
        }
    }

    public RunnableFuture<T> newTask() {
        return new FutureTask<T>(this) {
            public boolean cancel(boolean mayInterruptIfRunning) {
                try {
                    SocketUsingTask.this.cancel();
                } finally {
                    return super.cancel(mayInterruptIfRunning);
                }
            }
        };
    }
}


interface CancellableTask <T> extends Callable<T> {
    void cancel();

    RunnableFuture<T> newTask();
}


@ThreadSafe
class CancellingExecutor extends ThreadPoolExecutor {
    public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        if (callable instanceof CancellableTask)
            return ((CancellableTask<T>) callable).newTask();
        else
            return super.newTaskFor(callable);
    }
}
```

## 7.2 停止基于线程的服务

所谓的拥有线程.指的是在其服务内启动一个线程.把服务看成一个对象,内部组件就是组合.有线程的启动.你就拥有线程的Thread\FutureTask....就可以控制他们的生命周期.然后使用方法像程序提供这些线程的生命周期控制,不要将线程发布出去..(可以在内部控制一个线程池帮你管理你所启动的线程）

### 7.2.1 示例：日志

```Java
public class LogService {
    private final BlockingQueue<String> queue;
    private final LoggerThread loggerThread;
    private final PrintWriter writer;
    @GuardedBy("this") private boolean isShutdown;
    @GuardedBy("this") private int reservations;

    public LogService(Writer writer) {
        this.queue = new LinkedBlockingQueue<String>();
        this.loggerThread = new LoggerThread();
        this.writer = new PrintWriter(writer);
    }

    public void start() {
        loggerThread.start();
    }

    public void stop() {
        synchronized (this) {
            isShutdown = true;
        }
        loggerThread.interrupt();
    }

    public void log(String msg) throws InterruptedException {
        synchronized (this) {
            if (isShutdown)
                throw new IllegalStateException(/*...*/);
            ++reservations;
        }
        queue.put(msg);
    }

    private class LoggerThread extends Thread {
        public void run() {
            try {
                while (true) {
                    try {
                        synchronized (LogService.this) {
                            if (isShutdown && reservations == 0)
                                break;
                        }
                        String msg = queue.take();
                        synchronized (LogService.this) {
                            --reservations;
                        }
                        writer.println(msg);
                    } catch (InterruptedException e) { /* retry */
                    }
                }
            } finally {
                writer.close();
            }
        }
    }
}
```

### 7.2.2 关闭ExecutorService

ExecutorService提供了两种关闭方法：使用shutdown正常关闭，以及使用shutdownNow强行关闭。shutdownNow首先关闭当前正在执行的任务，然后返回所有尚未启动的任务清单，但是shutdownNow无法返回当前正在执行的任务的状态。

### 7.2.3 “毒丸”对象

“毒丸”是指一个放在队列上的对象，其含义是：“当得到这个对象时，立即停止”。

## 7.3 处理非正常地线程终止

导致线程提前死亡的最主要原因就是RuntimeException。由于这些异常表示出现某种编程错误或者其他不可修复的错误，因此他们通常不会被捕获，他们不会再调用栈中逐层传递，而是默认地在控制台中输出栈追踪信息，并终止线程。

可以通过主动方法使用try-cathch或者try-fnally来确保线程正常执行。

**未捕获异常的处理**

在Thread API中同样提供了UncaughtExceptionHandler，它能检测出某个线程由于未捕获异常而终结的情况。与上面的主动方法互补。

当一个线程由于未捕获异常而退出时，JVM会把这个时间报告给应用程序提供的UncaughtExceptionHandler异常处理器。如果没有提供任何异常处理器，那么默认的行为是将栈追踪信息输出到System.err。可以自己定义如何处理未捕获异常的方法。

## 7.4 JVM关闭

### 7.4.1 关闭钩子

在正常关闭中，JVM首先调用所有已注册的关闭钩子（Shutdown Hook）。关闭钩子是指通过Runtime.addShutdownHook注册的但尚未开始的线程。JVM并不能保证关闭钩子的调用顺序。在关闭应用程序时，如果有（守护或非守护）线程仍然在运行，那么这些线程接下来将与关闭进行并发执行。当所有的关闭钩子都执行结束时，如果runFinalizersOnExit为true，那么JVM将运行终结器，然后再停止。JVM并不会停止或中断任务在关闭时任然运行的应用程序线程。当JVM最终结束时，这些线程将被强行结束。如果关闭钩子或终结器没有执行完成，那么正常关闭进程“挂起”并且JVM必须强行关闭。当被强行关闭时，只是关闭JVM，而不会运行关闭钩子。

### 7.4.2 守护线程

执行辅助工作的线程，并且这个线程不会阻碍JVM关闭。

线程可分为两种：普通线程和守护线程。在JVM启动时创建的所有线程中，除了主线程以外，其他的线程都是守护线程。当创建一个新线程时，新线程将继承创建它的线程的守护状态，因此在默认情况下，主线程创建的所有线程都是普通线程。

普通线程与守护线程之间的差异仅在于当线程退出时发生的操作。当一个线程退出时，JVM会检查其他正在运行的线程，如果这些线程都是守护线程，那么JVM会正常退出操作。当JVM停止时，所有仍然存在的守护线程都将被抛弃--既不会执行finally代码块，也不会执行回卷栈，而JVM直接退出。

### 7.4.3 终结器

对于系统的资源，例如文件句柄或套接字句柄，当不再需要他们时，必须显式地交还给操作系统。为了实现这个功能，垃圾回收器对那些定义了finalize方法的对象会进行特殊处理：在回收器释放他们后，调用他们的finalize方法，从而保证一些持久化的资源被释放。

**避免使用终结器**

# 8 线程池的使用

第6章介绍了任务执行框架，它不仅能简化任务与线程的生命周期管理，而且还能提供一种简单灵活的方式将任务的提交与任务的执行策略解耦开来。第7张介绍了在实际应用程序中使用任务执行框架时出现的一些与服务生命周期相关的细节问题。本章将介绍对线程池进行配置与调优的一些高级选项，并分析在使用任务执行框架时需要注意的各种危险，以及一些使用Executor的高级示例。

## 8.1 在任务与执行策略之间的隐性耦合

并非所有的任务都能适用所有的执行策略包括：

- 依赖性任务
- 使用线程封闭机制的任务
- 对响应时间敏感的任务
- 使用ThreadLocal的任务

只有当任务都是同类型的并且相互独立时，线程池的性能才能达到最佳。如果将运行时间较长的与运行时间较短的任务混合在一起，那么除非线程池很大，否则将可能造成“拥塞”。如果提交的任务依赖于其他任务，那么除非线程池无限大，否则将可能造成死锁。

### 8.1.1 线程饥饿死锁

在线程池中，如果任务依赖于其他任务，那么可能产生死锁。线程池中的任务需要无限等待一些必须由池中其他任务才能提供的资源或条件，例如某个等待任务的返回值或执行结果，那么除非线程池足够大，否则将发生线程饥饿死锁。

RenderPageTask想Executor提交了两个任务来获取页眉和页脚，并等待获取结果。此线程就会经常发生死锁。

```Java
public class RenderPageTask implements Callable<String> {
        @Override
        public String call() throws Exception {
            Future<String> header;
            Future<String> footer;
            header = executorService.submit(new LoadFileTask("header.html"));
            footer = executorService.submit(new LoadFileTask("footer.html"));
            String page = renderBody();
            // Will deadlock -- task waiting for result of subtask
            return header.get() + page + footer.get();
        }

        private String renderBody() {
            // Here's where we would actually render the page
            return "";
        }
    }
```

## 8.2 设置线程池的大小

线程池的理想大小取决于被提交任务的类型以及所部署系统的特性。在代码中通常不会固定线程池的大小，而应该通过某种配置机制来提供，或根据Runtime.availableProcessors来动态计算。

![image-20191109154236619](picture\image-20191109154236619.png)

## 8.3 配置ThreadPoolExecutor

### 8.3.1 线程的创建与销毁

线程池的基本大小（Core Pool Size）、最大大小（Maximum Pool Size）以及存活时间等因素共同负责线程的创建与销毁。基本大小也就是线程池的目标大小，即在没有任务执行时线程池的大小。线程池的最大大小表示可同时活动的线程数量的上限。如果某个线程的空闲时间超过了存活时间，那么将被标记为可回收的，并且当线程池的当前大小超过了基本大小时，这个线程将被终止。

newFixedThreadPool工厂方法将线程池的基本大小和最大大小设置为参数中的指定值，而且创建的线程池不会超时。newCachedThreadPool工厂方法将线程池的最大大小设置为Integer.MAX_VALUE，而将基本大小设置为0，并将超时设置为1分钟。

### 8.3.2 管理队列任务

ThreadPoolExecutor允许提供一个BlockingQueue来保存等待执行的任务。基本的任务排队方法有3种：无界队列、有界队列和同步移交。

对于非常大的或者无界的线程池，可以通过使用SynchronousQueue来避免任务排队，以及直接将任务从生产者移交给工作者线程。SynchronousQueue不是一个真正地队列，而是一种在线程之间进行移交的机制。要建一个元素放入SynchronousQueue中，必须有另一个线程正在等待接受这个元素。如果没有线程正在等待，并且线程池的当前大小小于最大值，那么ThreadPoolExecutor将创建一个新的线程，否则根据饱和策略，这个任务将被拒绝。

### 8.3.3 饱和策略

当有界队列被填满后，饱和策略开始发挥作用。ThreadPoolExecutor的饱和策略可以通过调用setRejectedExceutionHandler来修改。JDK提供了集中不同的RejectedExecutionHandler实现：

**中止（Abort）**策略是默认的饱和策略，该策略将会抛出未检查的RejectedExecutionException。当新提交的任务无法保存到队列中等待执行时，“抛弃（Discard）”策略会悄悄抛弃该任务。“抛弃最旧的（Discard-Oldest）”策略则会抛弃下一个将被执行的任务，然后尝试重新提交新的任务。

**“调用者运行（Caller-Runs）”**策略实现了一种调节机制，它会将某些任务回退到调用者，从而降低新任务的流量。它不会在线程池的某个线程中执行新提交的任务，而是在一个调用了execute的线程中执行该任务。