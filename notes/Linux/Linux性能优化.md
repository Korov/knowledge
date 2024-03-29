## 性能工具：系统CPU

目标：

1.  理解系统级性能的基本指标，包括CPU的使用情况
    
2.  明白哪些工具可以检索这些系统级性能指标
    

### CPU性能统计信息

#### 运行队列统计

在Linux中，一个进程要么是可运行的，要么是阻塞的（正在等待一个事件的完成）。阻塞进程可能在等待的是从I/O设备来的数据，或者是系统调用的结果。如果进程是可运行的，那就意为着它要和其他也是可运行的进程竞争CPU事件。一个可运行的进程不一定会使用CPU，但是当Linux调度器决定下一个要运行的进程时，它会从可运行进程队列中挑选。如果进程时可运行的，同时又在等待使用处理器，这些进程就构成了运行队列。运行队列越长，处于等待状态的进程就越多。

性能工具通常会给出可运行的进程个数和等待I/O的阻塞进程个数。另一种常见的系统统计时平均负载。系统的负载是指正在运行和可运行的进程总数。平均负载是给定事件内的负载量。一般情况下取负载的时间为1分钟、5分钟和15分钟。

#### 上下文切换

大部分现代处理器一次只能运行一个进程或线程。虽然有些处理器（比如超线程处理器）实际上可以同时运行多个进程，但是Linux会把他们看作多个单线程处理器。如果要制造出单处理器同时运行多个任务的假象，Linux内核就要不断的在不同的进程间切换。这种不同进程间的切换称为上下文切换，因为当其发生时，CPU要保存旧进程的所有上下文西悉尼，并取出新进程的所有上下文信息。上下文中包含了Linux跟踪新进程的大量信息，其中包括：进程正在执行的指令，分配给进程的内存，进程打开的文件等。这些上下文切换涉及大量信息的移动，因此，上下文切换的开销可以是相当大的。尽量减少上下文切换的次数是个好主意。

上下文切换可以是内核调度的结果，为了保证公平的给每个进程分配处理器的时间，内核周期性地中断正在运行的进程，在适当的情况下，内核调度器会决定开始另一个进程，而不是让当前进程继续执行。每次这种周期性中断或定时发生时，你的系统都可能进行上下文切换。每秒定时中断的次数与架构和内核版本有关。一个检查中断频率的简单方法是用 `/proc/interrupts` 文件，它可以确定已知时长内发生中断的次数 。

```
⚡ root@centos  ~  cat /proc/interrupts | grep timer ; sleep 10 ; cat /proc/interrupts | grep timer
  0:         36          0          0          0   IO-APIC-edge      timer
LOC: 3729334602  137377066  321248051 4152133473   Local timer interrupts
  0:         36          0          0          0   IO-APIC-edge      timer
LOC: 3729338521  137382922  321251994 4152138164   Local timer interrupts
```

如上所示，我们要求内核给出定时器启动的次数，等待10秒，再次请求。

#### 中断

处理器还周期性的从硬件设备接收中断。当设备有事件需要内核处理时，它通常会触发这些中断。例如磁盘控制器刚刚完成从驱动器取数据块的操作，并准备好提供给内核，那么磁盘控制器就会触发一个中断。对内核收到的每个中断，如果已经有相应的已注册的中断处理程序，就运行该程序，否则将忽略这个中断。这些中断处理程序在系统中有很高的运行优先级，并且通常执行速度也很快。有时，中断处理程序有工作要做，但是又不需要高优先级，因此可以启动“下半部”（bottom half），也就是所谓的软中断处理程序。如果又很多中断，内核会花大量的事件服务这些中断。

#### CPU使用率

在任何给定的时间，CPU可以执行一下七件事情中的一个：

1.  CPU可以是空闲的，处理器实际上没有做任何工作，并且等待有任务可以执行
    
2.  CPU可以运行用户代码，即指定的“用户”时间
    
3.  CPU可以执行Linux内核中的应用程序代码，这就是“系统”时间
    
4.  CPU可以执行“比较友好”的或者优先级被设置为低于一般进程的用户代码
    
5.  CPU可以处于 `iowait` 状态，即系统正在等待 `I/O` （如磁盘或网络）完成
    
6.  CPU可以处于 `irq` 状态，即它正在用高优先级代码处理硬件中断
    
7.  CPU可以处于 `softirq` 模式，即系统正在执行同样由中断触发的内核代码，只不过其运行于较低的优先级（下半部代码）
    

大多数性能工具将这些数值表示为占CPU总时间的百分比。这些时间的范围从 0% 到 100%，但全部三项加起来等于100%。一个具有高“系统”百分比的系统表明其大部分时间都消耗在了内核上。像 `oprofile` 一样的工具可以帮助确定时间都消耗在了哪里。具有“用户”时间的系统则将其大部分时间都用来运行应用程序。

### Linux性能工具：CPU

下面介绍哪些工具能够提取之前描述的信息

#### 

此工具可以获取整个系统性能的粗略信息，包括：

-   正在运行的进程个数
    
-   CPU的使用情况
    
-   CPU接收的中断个数
    
-   调度器执行的上下文切换次数
    

它是用于获取系统性能大致信息的极好工具

```
# delay表示延迟采集间隔，count表示采集次数
⚡ root@centos  ~  vmstat --help

Usage:
 vmstat [options] [delay [count]]

Options:
 -a, --active           active/inactive memory
 -f, --forks            number of forks since boot
 -m, --slabs            slabinfo
 -n, --one-header       do not redisplay header
 -s, --stats            event counter statistics
 -d, --disk             disk statistics
 -D, --disk-sum         summarize disk statistics
 -p, --partition <dev>  partition specific statistics
 -S, --unit <char>      define display unit
 -w, --wide             wide output
 -t, --timestamp        show timestamp

 -h, --help     display this help and exit
 -V, --version  output version information and exit
```

`vmstat` 运行于两种模式：采样模式和平均模式。如果不指定参数，运行于平均模式，显示从系统启动以来所有统计数据的均值。但是，如果指定了延迟，那么第一个采样任然是系统启动以来的均值，但之后按延迟描述采样系统并显示统计数据

```
 ⚡ root@centos  ~  vmstat 2 2
procs -----------memory---------- ---swap-- -----io---- -system-- ------cpu-----
 r  b   swpd   free   buff  cache   si   so    bi    bo   in   cs us sy id wa st
 2  0 2547596 6191196 135836 5032292    1    1    26   455    0    1  4  1 95  1  0
 0  0 2547596 6190684 135836 5032308    0    0     0    82 1957 2182  3  0 97  0  0
```

输出内容详解：

1.  procs
    
    1.  r:运行和等待cpu时间片的进程数，如果长期大于1，说明cpu不足，需要增加cpu
        
    2.  b:等待资源的进程数，比如正在等待I/O、或者内存交换等
        
    
2.  memory
    
    1.  swpd:切换到内存交换区的内存数量（k表示）。如果swpd值不为0，或者比较大，只要si、so的值长期为0，系统性能还是正常的
        
    2.  free:当前的空闲页面列表中内存数量（k表示）
        
    3.  buff:作为buffer cache的内存数量，一般对块设备的读写才需要缓冲
        
    4.  cache:作为page cache的内存数量，一般作为文件系统的cache，如果cache较大，说明用到cache的文件较多，如果此时IO中bi比较小，说明文件系统效率比较好
        
    
3.  swap
    
    1.  si:由内存进入内存交换区数量
        
    2.  so:由内存交换区进入内存数量
        
    
4.  io
    
    1.  bi:从块设备读入数据的总量（读磁盘）（每秒kb）
        
    2.  bo:块设备写入数据的总量（写磁盘）（每秒kb）
        
    
5.  system
    
    1.  in:某一时间间隔中观测到的每秒设备中断数
        
    2.  cs:每秒产生的上下文切换次数，如当cs比磁盘io和网络信息包速率高的多，都应进行进一步调查
        
    
6.  cpu
    
    1.  us:用户方式下所花费cpu时间的百分比。us的值比较高时，说明用户进程消耗的cpu时间多，如果长期大于50%，需要考虑优化用户的程序
        
    2.  sy:内核进程所花费的cpu时间百分比。这里us+sy的参考值为80%，如果大于80%说明可能存在cpu不足
        
    3.  wa:显示了IO等待所占用的cpu时间的百分比。者的wa参考值为30%，如果wa超过30%，说明IO等待严重，这可能时磁盘大量随机访问造成的，也可能磁盘或者磁盘访问控制器的带宽瓶颈造成的（主要时块操作）
        
    4.  id:空闲时间
        
    5.  st:虚拟机使用cpu时间
        
    

```
{23:46}~/docker/mysql/backup ➭ vmstat -s
     16388904 K total memory
      2892312 K used memory
       919108 K active memory
      3479760 K inactive memory
     11625864 K free memory
       593784 K buffer memory
      1276944 K swap cache
     33554432 K total swap
       106704 K used swap
     33447728 K free swap
       168994 non-nice user cpu ticks
           10 nice user cpu ticks
       126367 system cpu ticks
    145437222 idle cpu ticks
         4656 IO-wait cpu ticks
            0 IRQ cpu ticks
         8194 softirq cpu ticks
            0 stolen cpu ticks
      6040079 pages paged in
     53674764 pages paged out
         5516 pages swapped in
        31080 pages swapped out
     35658495 interrupts
    179173403 CPU context switches
   1640690672 boot time
        18934 forks
```

vmstat输出的另外一种展示方式， `ticks` 是一种时间单位。新参数 `forks` 它大体上表示的是从系统启动开始，已经创建的新进程的数量。

#### top

top善于将相当多的系统整体性能信息放在一个屏幕上。显示内容还能以交互的方式进行改变，因此，在系统运行时，如果一个特定的问题不断突显，你可以修改top显示的信息。

默认情况下，top表现为一个将占用cpu最多的进行按降序排序

命令:

```
top [-d delay] [-n iter] [-i] [-b]

-d delay:统计信息更新的时间间隔
-n iterations: 退出前迭代的次数。top更新统计信息的次数为iterations次
-i: 是否显示空闲进程
-b: 以批处理模式运行。通常，top只显示单屏信息，超出该屏幕的进程不显示。该选项显示全部进程，如果你要将top的输出保存为文件或将输出流水给另一个命令进行处理，那么该项是很有用的。
```

#### 

procinfo也为系统整体信息提供总览，尽管它提供的有些信息于vmstat相同，但它还会给cpu从每个设备接收的中断数量。其输出格式的易读性比vmstat稍微强一点，但却会占用更多的屏幕空间。

```
➜  ~ procinfo -h
procinfo version 2.0 $Rev: 304 $
usage: procinfo [-sidDSbhHv] [-nN]

        -nN     pause N second between updates (implies -f)
        -d      show differences rather than totals (implies -f)
        -D      show current memory/swap usage, differences on rest
        -S      with -nN and -d/-D, always show values per second
        -b      show number of bytes instead of requests for disk statistics
        -H      show memory stats in KiB/MiB/GiB
        -r      show memory usage -/+ buffers/cache
        -s      Don't skip netdevs in /etc/procinfo/skipIfaces
        -h      print this help
        -v      print version info
```

输出主要参数解释：

1.  context: The total number of context switches since bootup.
    
2.  irq:中断请求次数
    
3.  Load average:The average number of jobs running, followed by the number of runnable processes and the total number of processes, followed by the PID of the last process run. The pid of the last running process will probably always be procinfo’s PID.
    

procinfo表明系统空闲时间比其运行时间（uptime）还要多。这是因为系统实际上有4个cpu，因此对于一天的墙钟时间而言，cpu时间已经过去了四天。

#### 

展示随着时间变化的cpu行为。mpstat最大的优点是在统计信息的旁边显示时间，由此你可以找出cpu使用率与时间的关系。此外mastat可以监控单个处理器是否做了大部分的工作。

```
mpstat [-P {cpu | ALL}] [delay [count]]

-P {cpu | ALL}: 监控哪个cpu，cpu取值范围0~（cpu总数-1），ALL监控所有
```

```
 ⚡ root@centos  /proc  mpstat -P ALL 2 4
Linux 3.10.0-693.el7.x86_64 (centos)    2021年12月30日  _x86_64_        (4 CPU)

11时02分35秒  CPU    %usr   %nice    %sys %iowait    %irq   %soft  %steal  %guest  %gnice   %idle
11时02分37秒  all    1.26    0.00    0.25    0.00    0.00    0.13    0.00    0.00    0.00   98.36
11时02分37秒    0    0.51    0.00    0.51    0.00    0.00    0.00    0.00    0.00    0.00   98.99
11时02分37秒    1    2.51    0.00    0.50    0.00    0.00    0.00    0.00    0.00    0.00   96.98
11时02分37秒    2    0.50    0.00    0.50    0.00    0.00    0.00    0.00    0.00    0.00   98.99
11时02分37秒    3    1.01    0.00    0.00    0.00    0.00    0.50    0.00    0.00    0.00   98.49
```

输出详解：

1.  %steal: 显示当虚拟机管理程序为另一个虚拟处理器提供服务时，一个或多个虚拟 CPU 花费在非自愿等待上的时间百分比。
    
2.  %guest: 虚拟程序划分的cpu时间
    

#### 

sar命令可以用于记录性能信息，回放之前的记录信息，以及显示当前系统实时信息。sar命令的输出可以进行格式化，使之易于导入数据库，或是输送给其他linxu命令进行处理

```
sar [options] [delay [count]]

-c:报告每秒创建的进程数量
-I {irq | SUM | ALL | XALL}:报告系统已发生中断的速率
-P {cpu | ALL}:指定从哪个cpu收集信息。如不指定，则报告系统整体情况
-q:报告机器的运行队列长度和平均负载
-u:报告系统的cpu使用情况
-w:报告系统中已发生的上下文切换次数
-o filename:指定保存性能统计信息的二进制输出文件名
-f filename:指定性能统计信息的文件名
```

#### oprofile

oprofile是性能工具包，它利用几乎所有现代处理器都有的性能计数器来跟踪系统整体以及单个进程中cpu时间的消耗情况。除了测量cpu周期消耗在哪里之外，oprofile还可以测量关于cpu执行的非常底层的信息。根据由底层处理器支持的事件，它可以测量的内容包括：cache缺失、分支预测错误和内存引用，以及浮点操作。

采样非常强大，但使用时要小心一些不明显的陷阱。首先采样可能会显示你由90%的事件花在了一个特定的例程上，但它不会显示原因。一个特定历程消耗了大量周期有两种可能的原因。其一，该例程可能时瓶颈，其执行需要很多事件。但是，也可能例程的执行时间是合理的，而其被调用的次数非常高。通常有两种途径可以发现究竟是哪一种情况：通过查看采样找出特别热门的行，或是通过编写代码来计算例程被调用次数。

采样的第二个问题是你永远无法十分确定一个函数是从哪里被调用的。即使你已经搞明白它被调用了很多次，并且已经跟踪到了所有调用他的函数，但也不一定清楚其中哪一个函数完成了大多数的调用。

##### CPU性能相关的选项

oprofile实际上是一组协同工作的组件，用于收集CPU性能统计信息。oprofile主要有三个部分：

-   oprofile核心模块控制处理器并允许和禁止采样
    
-   oprofile后台模块收集采样，并将他们保存到磁盘
    
-   oprofile报告工具获取收集的采样，并向用户展示他们与在系统上运行的应用程序的关系。
    

## 性能工具：系统内存

### 内存性能统计信息

#### 内存子系统和性能

在现代处理器中，与CPU执行代码或处理信息相比，向内存子系统保存信息或从中读取信息一般花费的时间更长。

#### 内存子系统（虚拟存储器）

任何给定的linxu系统都有一定容量的RAM或物理内存。在这个物理内存中寻址时，Linux将其分成块或内存页。当对内存进行分配或传送时，Linux操作的单位是页，而不是单个字节。在报告一些内存统计数据时，Linux内核报告的是每秒页面的数量。

Linux默认页面大小为4KB，极少数情况下，这些页面的大小会导致极高的跟踪开销，所以内核用更大的块来操作内存，这些块被称为 `HugePage`。他们的容量为2048KB，这大大降低了管理庞大内存的开销。某些应用，如Oracle，用这些大页面加载内存中的大量数据，以达到最小化Linux内核的管理开销。但是HugePage如果不能被完全填满，就会浪费相当多的内存。

##### 交换

所有系统RAM芯片的物理内存容量都是固定的。即使应用程序需要的内存容量大于可用的物理内存，Linux内核仍然允许这些程序运行。Linux内核使用硬盘作为临时存储器，这个硬盘空间被称为交换分区（swap space）。交换分区相比正常的程序速度可以慢到一千倍。

##### 缓冲区（buffer）和缓存（cache）（物理内存太多）

如果你的物理系统内存容量超过了应用程序的需求，Linux就会在物理内存中缓存近期使用过的文件，这样后续访问这些文件时就不用取访问硬盘了。

除了高速缓存，Linux还使用了额外的存储作为缓冲区。为了进一步优化应用程序，Linux为需要被写回硬盘的数据预留了存储空间。这些预留空间被称为缓冲区。如果应用程序要将数据写回硬盘，通常需要花费较长的时间，Linux让应用程序立刻继续执行，但将文件数据保存到内存缓冲区。在之后的某个时刻，缓冲区被刷新到硬盘，而应用程序可以立即继续。

##### 活跃与非活跃内存

活跃内存是指当前被进程使用的内存，不活跃内存是指已经被分配了，但暂时还未使用的内存。这两种类型的内存没有本质上的区别。需要时，Linux找出进程最近最少使用的内存页面，并将他们从活跃列表移动到不活跃列表。当要选择把哪个内存页交换到硬盘时，内核就从不活跃内存列表中进行选择。

##### 内核的内存使用情况（分片）

除了应用程序需要分配内存外，Linux内核也会位了记账的目的消耗一定量的内存。记账包括，比如跟踪从网络或磁盘IO来的数据，以及跟踪哪些进程正在运行，哪些正在休眠。为了管理记账，内核有一系列缓存，包含了一个或多个内存分片。每个分片为一组对象，个数可以是一个或多个。内核消耗的内存分片数量取决于使用的是Linux内核的哪些部分，而且还可以随着机器负载类型的变化而变化

### Linux性能工具：CPU与内存

#### vmstat

```
vmstat [-a] [-s] [-m]

-a:该项改变内存统计信息的默认输出以表示活跃/非活跃内存量，而不是缓冲区和高速缓存使用情况的统计信息
-m:输出内核分片信息。信息详细展示了内核是如何分配的，并有助于确定哪部分内核消耗内存最多
```

`vmstat -m`：显示每一个分片（Cache），展示使用了多少元素（Num），分配了多少（Total），每个元素的大小（Size），整个分片使用了多少内存也（Pages）。

#### top

top提供了不同运行进程大量的内存信息。你可以使用这些信息来确定应用程序究竟是如何分配和使用内存的。

#### slabtop

显示内核是如何分配其各种缓存的，以及这些缓存的被占用情况。在内部，内核有一系列的缓存，他们由一个或多个分片（slab）构成。每个分片包括一组对象，对象个数为一个或多个。这些对象可以是活跃的（使用的）或非活跃的（未使用的）。slaptop向你展示的是不同分片的状况。它显示了这些分片的被占用情况，以及他们使用了多少内存。

```
OBJS — The total number of objects (memory blocks), including those in use (allocated), and some spares not in use.
ACTIVE — The number of objects (memory blocks) that are in use (allocated).
USE — Percentage of total objects that are active. ((ACTIVE/OBJS)(100))
OBJ SIZE — The size of the objects.
SLABS — The total number of slabs.
OBJ/SLAB — The number of objects that fit into a slab.
CACHE SIZE — The cache size of the slab.
NAME — The name of the slab.
```

## 性能工具：特定进程CPU

目标：

1.  确定应用程序的运行时间是花费在内核上还是在应用程序上
    
2.  确定应用程序有哪些库调用和系统调用，以及他们花费的时间
    
3.  分析应用程序，找出哪些源代码行和函数的完成时间最长。
    

### 进程性能统计信息

#### 内核时间vs用户时间

一个应用程序所耗时间最基本的划分是内核时间与用户时间。内核时间是消耗在Linux内核上的时间，而用户时间则是消耗在应用程序或库代码上的时间。Linux有工具，如time和ps，可以大致表明应用程序将其时间是花在了应用程序代码上还是花在了内核代码上。同时，还有oprofile和strace这样的命令使你能够跟踪哪些内核调用是代表进程发起的，以及每个调用完成需要多少时间

#### 库时间vs引用程序时间

任何应用程序，即便其复杂性非常低，也需要依赖系统库才能执行负载的操作。在库被应用程序使用时，ltrace命令和oprofile工具包提供了分析库性能的途径。Linux加载器ld的内置工具帮助你确定使用多个库是否会减慢应用程序的启动时间。

#### 细分应用程序时间

当已经知道某应用程序是瓶颈后，Linux可以向你提供工具来分析这个应用程序，以找出在这个程序中，时间都花在了哪里。gprof和oprofile可以生成应用程序的配置文件，确定是哪些源代码行花费了大量的时间

### 工具

#### time

测量命令的执行时间。测量的时间有三种类型：第一种测量的是真正的或经过的时间，即程序开始到结束执行之间的时间；第二种测量的是用户时间，即CPU代表该程序执行应用代码所花费的时间；第三种测量的是系统时间，即CPU代表该程序执行系统或内核代码所花费的时间。

命令： `/usr/bin/time application` (bash有自己默认的time函数功能更弱)

#### strace

strace是当程序执行时，追踪其发起的系统调用的工具。系统调用是有或代表一个应用程序进行的Linux内核函数调用。strace可以展示准确的系统调用，它在确定应用程序是如何使用Linux内核的方面是相当有用的。通过查看strace的输出，你可以了解应用程序如何使用内核，以及它依赖于什么类型的函数。

虽然strace主要用于跟踪进程与内核之间的交互，显示应用程序的每个系统调用的参数和结果，但是strace也可以提供不那么令人生畏的汇总信息。应用程序运行之后，strace会给出一个表格，显示每个系统调用的频率和该类型调用所花费的总时间。这个表格可以作为理解你的程序与Linux内核之间交互的首个关键信息。

##### CPU性能相关的选项

如下的strace调用对性能测试是最有用的

```
strace [-c] [-p pid] [-o file] [--help] [command [arg ...]]

-c:使strace打印出统计信息的概要，而非所有系统调用的独立列表
-p pid:将给定pid添加到进程，并开始跟踪
-o file:strace的输出将保存到file
```

输出的概要信息解释：

-   %time:对全部系统调用的总时间来说，该项为这一个系统调用所花时间的百分比
    
-   seconds:这一个系统调用所花费的总秒数
    
-   usecs/call:这个类型的一个系统调用所花费的微秒数
    
-   calls:这个类型的所用调用的总数
    
-   errors:这个系统调用返回错误的次数
    

#### ltrace

ltrace与strace概念相似，但它跟踪的是应用程序对库的调用而不是对内核的调用。虽然ltrace主要用于提供对库调用的参数和返回值的精确跟踪，但是你也可以用它来汇总每个调用所花的时间。这使你既可以发现应用程序有哪些库调用，又可以发现每个调用时间是多长。

使用ltrace要小心，因为它会产生具有误导性的结果。如果一个库函数调用了另一个函数，则花费的时间要计算两次。比如，如果库函数foo()调用了函数bar()，则函数foo()的报告时间将是函数foo()代码运行的全部时间再加上函数bar()花费的时间。

##### CPU性能相关的选项

```
ltrace [-c] [-p pid] [-o filename] [-S] [--help] command

-c:使得ltrace在命令执行完后打印出所有调用的汇总
-s:除了库调用之外，ltrace还跟踪系统调用，该项与strace提供的功能相同
-p pid:跟踪有给定pid的进程
-o file:将ltrace的输出保存到file
```

汇总模式提供了应用程序执行期间的库调用的性能统计信息，下面是其含义：

-   %time:相对库调用花费的总时间，该项是这一个库调用所花时间的百分比
    
-   seconds:该项为这一个库调用所用的总秒数
    
-   usecs/call:该项为这个类型种一个库调用所花的微妙数
    
-   calls:该项为这个类型调用的总数
    
-   function:该项为库调用的名称
    

#### ps（进程状态）

ps是极好的跟踪运行进程的命令

它给出正在运行进程的详细的静态和动态统计信息。ps提供的静态信息包括命令名和pid，动态信息包括内存和CPU的当前使用情况。

##### CPU性能相关的选项

ps有许多不同的选项，能检索正在运行中的应用程序的各种统计信息。下面的调用给出了与CPU性能最相关的选项，并将显示给定pid信息

```
ps [-o etime,time,pcpu,command] [-u user] [-U user] [PID]

etime:统计信息：经过时间是指从程序开始执行起耗费的总时间
time:统计信息：CPU时间是指进程运行于CPU所花费的系统时间加上用户时间
pcpu:进程当前消耗的CPU的百分比
command: -A 显示所有进程的统计信息，-u user 显示指定有效用户id的所有进程的统计信息， -U user 显示指定用户id的所有进程的统计信息
```

```
 ⚡ root@centos  /proc  ps -o etime,time,pcpu,cmd 2278231
    ELAPSED     TIME %CPU CMD
   09:25:23 00:00:02  0.0 -zsh
```

#### oprofile

本小节介绍的是oprofile用于分析进程级采样结果的部分。

## 性能工具：特定进程内存

目标：

-   确定一个应用程序使用了多少内存（ps, /proc）
    
-   确定应用程序的哪些函数分配内存（memprof）
    
-   用软件模拟（kcachegrind,cachegrind）和硬件性能计数器（oprofile）分析应用程序的内存使用情况
    
-   确定哪些进程创建和使用了共享内存（ipcs）
    

### Linux内存子系统

在诊断内存性能问题的时候，也许有必要观察应用程序在内存子系统的不同层次上是怎样执行的。在顶层，操作系统决定如何利用交换内存和物理内存。它决定应用程序的哪一块地址空间将被放到物理内存中，即所谓的驻留集。不属于驻留集却又被应用程序使用的其他内存将被交换到磁盘。由应用程序决定要向操作系统请求多少内存，即所谓的虚拟集。应用程序可以通过调用malloc进行显式分配，也可以通过使用大量的堆栈或库进行隐式分配。性能工具ps用于跟踪虚拟集和驻留集的大小。性能工具memprof用于跟踪应用程序的哪段代码是分配内存的。工具ipcs用于跟踪共享内存的使用情况。

当应用程序使用物理内存时，它首先与CPU的高速缓存子系统交互。现代CPU有多级高速缓存。最快的高速缓存离CPU最近，CPU会依次查看L1缓存，L2缓存，然后才是物理内存中取数据。明智地使用高速缓存，例如重新排列应用程序的数据结构以及减少代码量等方法，有可能减少高速缓存不命中的次数并提高性能。cachegrind和oprofile是很好的工具，用于发现应用程序对高速缓存的使用情况的信息，以及哪些函数和数据结构导致了高速缓存不命中。

### 内存性能工具

#### ps

ps有许多不同的选项，可以获取一个正在运行的应用程序各种各样的状态统计信息。ps可以检索到进程使用内存的容量和类型信息。ps可以用如下命令行调用：

```
ps [-o vsz,rss,tsiz,dsiz,majflt,minflt,pmem,command] <PID>

vsz:虚拟集大小是指应用程序使用的虚拟内存的容量。由于Linux只在应用程序试图使用物理内存时才分配它，因此，该项数值可能会比应用程序使用的物理内存量大很多
rss:驻留集大小是指应用程序当前使用的物理内存量
tsiz:文本大小是指程序代码的虚拟大小。再强调一次，这不是实际大小，而是虚拟大小，但是，该项数值清晰的表明了程序的大小
dsiz:数据大小是指程序数据使用量的虚拟大小。该项数值清晰地表明了应用程序地数据结构和堆栈地大小
majflt:主故障是指使得Linux代表进程从磁盘读取页面地缺页故障地数量。这种故障可能发生地情况是：当进程访问一块数据或指令仍留在磁盘上时，Linux要为应用程序进行无缝加载
minflt:次故障是指Linux不用诉诸磁盘读取就可以解决地故障数量。如果应用程序设计一块已经由Linux内核分配地内存，就可能发生这种情况。这种情况不需要访问磁盘，因为内核只需要选择一块空闲内存并将其分配给应用程序即可
pmep:进程消耗地系统内存百分比
command:命令名
```

#### /proc/<PID>

Linux内核提供了一个虚拟文件系统，使你能提取再系统上运行地进程地信息。除了许多其他统计数据之外，/proc还提供了进程地内存使用信息和库映射信息。

```
cat /proc/<PID>/status

VmSize:进程地虚拟集大小，是应用程序使用地虚拟内存量。由于Linux只再应用程序试图使用物理内存时才进行分配，因此，这个数字可能会比应用程序实际使用地物理内存容量大很多。
VmLck:被进程锁定地内存量。被锁定地内存不能交换到磁盘
VmRSS:驻留集大小或应用程序当前使用地物理内存量。它与ps提供地rss统计数据相同
VmData:数据大小或程序使用数据量地虚拟大小。与ps地dsiz统计数据不同，该项不包含堆栈信息
VmStk:进程地堆栈大小
VmExe:程序地可执行内存地虚拟大小，它不包含进程使用地库
VmLib:进程使用地库地大小
```

`<PID>` 目录下地另一个文件是maps，它提供了关于如何使用进程虚拟地址空间地信息。

```
cat /proc/<PID>/maps

Address:进程中库映射地地址范围
Permissions:内存区域地权限，r读，w写，x执行，s共享，p私有（写时复制）
Offset:库/应用程序内存映射区域开始处地偏移量
Device:这个特殊文件所在地设备（主设备号和次设备号）
Inode:映射文件地节点号
Pathname:映射到进程地文件地路径名
```

#### 

valgrind是一个强大地工具，使你能调试棘手地内存管理错误。虽然valgrind主要是一个开发者工具，但它也有一个界面能显示处理器地高速缓存使用情况。valgrind模拟当前地处理器，并在这个虚拟处理器上运行应用程序，同时跟踪内存使用情况。它还能模拟处理器高速缓存，并确定程序在哪里由指令和数据高速缓存地命中或缺失。

#### ipcs

ipcs是一种系统级工具，可以展示进程之间通信内存地信息。进程可以分配整体系统共享地内存、信号量，以及由系统上运行地多个进程所共享地内存队列。ipcs最好被用于跟踪哪些应用程序分配并使用了大量地共享内存。

```
ipcs [-t] [-c] [-l] [-u] [-p]

-t:显示共享内存创建时间，进程最后访问该内存地时间，以及进程最后与之分离地时间
-u:提供了关于共享内存使用量，以及它是否已被交换到磁盘还是仍留着内存地汇总信息
-l:显示了对共享内存使用情况地系统级限制
-p:显示了创建和最后使用共享内存地进程地PID
x:显示作为共享内存段地创建者和拥有者地用户
```

## 磁盘I/O

目标：

-   确定系统内磁盘I/O地总量和类型（读/写）（vmstat）
    
-   确定哪些设备服务了大部分地磁盘I/O（vmstat,iostat,sar）
    
-   确定特定磁盘处理I/O请求地有效性（iostat）
    
-   确定哪些进程正在使用一组给定地文件（lsof）
    

### 磁盘I/O介绍

大多数现代Linux系统都有一个或多个磁盘驱动。如果他们是IDE驱动，那么常常将被命名为hda、hdb、hdc等；而SCSI驱动则常常被命名为sda、sdb、sdc等。磁盘通常要分为多个分区、分区设备名称地创建方法是在基础驱动名称地后面直接添加分区编号。比如，系统中首个IDE硬驱动地第二个分区通常被标记为 `/dev/hda2`。一般每个独立分区要么包含一个文件系统，要么包含一个交换分区。这些分区被挂载到Linux根文件系统，该系统由 \`/etc/fstab\`指定。这些被挂载地文件系统包含了应用程序要读写地文件。

当一个应用程序进行读写时，Linux内核可以在其高速缓存或缓冲区中保存文本地副本，并且可以在不访问磁盘地情况下返回被请求地信息。但是，如果Linux内核没有在内存中保存数据副本，那它就向磁盘I/O队列添加一个请求。若Linux内核注意到多个请求都指向磁盘内相邻区域，它会把他们合并为一个大的请求。这种合并能消除第二次请求的寻道时间，以此来提高磁盘整体性能。当请求被放入磁盘队列，而磁盘当前不忙时，它就开始为I/O请求服务。如果磁盘正忙，则请求就在队列中等待，知道该设备可用，请求将被服务。

### 磁盘I/O性能工具

#### vmstat

可以提供系统整体上的I/O性能情况

```
vmstat [-D] [-d] [-p partition] [interval [count]]

-D:显示Linux I/O子系统总的统计数据。它可以让你很好的了解你的I/O子系统是如何被使用的，但它不会给出单个磁盘的统计数据。显示的统计数据是从系统启动开始的总信息，而不是两次采样之间的发生量
-d:按每interval一个样本的速率显示单个磁盘的统计数据。这些统计信息是从系统启动开始的总信息，而不是两次采样之间的发生量
-p partition:按照每interval一个采样的速率显示给定分区的性能统计数据。这些统计信息总是从系统启动开始的总信息，而不是两次采样之间的发生量
```

```
{12:57}~ ➭ vmstat -D
           28 disks
            0 partitions
       127630 total reads
        22596 merged reads
     15488182 read sectors
        41447 milli reading
       709197 writes
      1100696 merged writes
    133206328 written sectors
      2445229 milli writing
            0 inprogress IO
         1633 milli spent IO

disks:系统中磁盘总数
partitions：系统中分区总数
total reads：读请求总数
merged reads：为了提升性能而被合并的不同读请求数量，这些读请求访问的是磁盘上的相邻位置
read sectors：从磁盘读取的扇区总数（一个扇区通常为512）
milli reading：磁盘读所花费的时间（以毫秒为单位）
writes：写请求的总数
merged writes：为了提升性能而被合并的不同写请求数量，这些写请求访问的是磁盘上的相邻位置
written sectors：向磁盘写入的扇区总数（一个扇区通常为512字节）
milli writing：磁盘写所花费的时间（以毫秒为单位）
inprogress IO：当前正在处理的I/O总数
milli spent IO：等待I/O完成所花费的毫秒数
```

#### iostat

专门用于显示磁盘I/O子系统统计信息的工具。iostat提供的信息细化到每个设备和每个分区从特定磁盘读写了多少个块。此外，iostat还可以提供大量的信息来显示磁盘是如何被利用的，以及Linux花费了多长时间来等待将请求提交到磁盘。

```
iostat [-d] [-k] [-x] [device] [interval [count]]

-d:只显示磁盘IO的统计信息
-k：按KB显示统计数据，而不是按块显示


{16:33}~ ➭ iostat -d
Linux 5.10.60.1-microsoft-standard-WSL2 (korov-win)     12/31/2021      _x86_64_        (16 CPU)

Device             tps    kB_read/s    kB_wrtn/s    kB_dscd/s    kB_read    kB_wrtn    kB_dscd
loop0             0.01         0.49         0.00         0.00     110352          0          0
loop1             0.01         0.85         0.00         0.00     191162          0          0
sda               0.06         0.10        19.13         0.00      23305    4321560          0
sdb               0.00         0.18         0.00         0.00      39678        276         24
sdc               3.63        14.75       225.08        69.60    3332937   50846652   15723896
sdd               0.30        17.91        50.80        37.74    4046721   11475636    8526624

tps：每秒传输次数。每秒对设备/分区读写请求的次数
kB_read/s：每秒读取磁盘的速率
kB_wrtn/s：每秒写入磁盘的速率
kB_dscd/s：每秒丢弃数据
kB_read：在时间间隔内读取的总数量
kB_wrtn：在时间间隔内写入的总数量
kB_dscd：丢弃数据总量
```

#### pidstat

此工具监测每个进程的IO信息

```
pidstat [-p PID] [-d] [-t] [ <interval> [ <count> ] ]

-p:对应的pid
-d：表明监测对象是磁盘IO
-t：表示监测相关线程的IO信息
```

```
{15:49}~ ➭ pidstat -p 766 -d -t
Linux 5.10.60.1-microsoft-standard-WSL2 (korov-win)     01/01/2022      _x86_64_        (16 CPU)

03:49:39 PM   UID      TGID       TID   kB_rd/s   kB_wr/s kB_ccwr/s iodelay  Command
03:49:39 PM     0       766         -      0.99      1.14      0.12       2  zsh
03:49:39 PM     0         -       766      0.01      0.00      0.00       2  |__zsh
```

#### lsof（列出打开文件）

lsof提供了一种方法来去顶哪些进程打开了一个特定的文件。除了跟踪单个文件的用户外，lsof还可以显示使用了特定目录下文件的进程。同时，它还可以递归搜索整个目录树，并列出使用该目录树内文件的进程。

```
lsof [-r delay] [+D directory] [+d directory] [file]

-r delay:每间隔delay秒输出一次统计数据
+D directory：递归搜索给定目录下的所有文件，并报告哪些进程正在使用他们
+d directory：报告哪些进程正在使用给定目录下的文件

{17:29}~ ➭ lsof +D ./
COMMAND   PID USER   FD   TYPE DEVICE SIZE/OFF  NODE NAME
zsh       766 root  cwd    DIR   8,48     4096 40961 .
lsof    23192 root  cwd    DIR   8,48     4096 40961 .
lsof    23193 root  cwd    DIR   8,48     4096 40961 .

FD：该文件的描述符。txt表示可执行文件，mem表示内存映射文件
TYPE：文件类型，REG表示常规文件
DEVICE：用主设备号和次设备号表示的设备编号
NODE：文件的索引节点
```

## 性能工具：网络

目标：

-   确定系统内以太网设备的速度和双工设置（mii-tool,ethtool）
    
-   去顶流经每个以太网接口的网络流量（ifconfig,sar,gkrellm,iptraf,netstat,etherape）
    
-   去顶流入和流出系统的IP流量的类型（gkrellm,iptraf,netstat,etherape）
    
-   去顶流入和流出系统的每种类型的IP流量（gkrellm，iptraf，etherape）
    
-   确定是哪个应用程序产生了IP流量（netstat）
    

### 网络IO介绍

Linux和其他主流操作系统中的网络流量被抽象为一系列的硬件和软件层次。链路层，也就是最低一层，包含网络硬件，如以太网设备。再传送网络流量的时候，这一层并不区分流量类型，而仅仅以尽可能快的速度发送和接收数据（或帧）。

链路层的上面是网络层。这一层使用互联网协议（IP）和网际控制报文协议（ICMP）在机器间寻址并路由数据包。IP/ICMP尽其最大努力尝试在机器之间传递数据包，但是他们不能保证数据包是否能正真达到其目的地。

网络层上面是传输层，它定义了传输控制协议（TCP）和用户数据报协议（UDP）。TCP是一个可靠协议，它可以保证消息通过网络送达，如果消息无法送达它就会产生一个错误。UDP是一个不可靠协议，它无法保证消息能够送达（为了获得最高的数据传输速率）。UDP和TCP为IP增加了“服务”的概念。UDP和TCP接收有编号“端口”的消息。按照惯例，每个类型的网络服务都被分配了不同的编号。在Linux系统中，文件 `/etc/services` 定义了全部的端口以及他们提供的服务类型

最上一层为应用层。这一层包含了各种应用程序，他们使用下面各层在网络上传输数据包。

在Linux内核实现或控制的是最低三层（链路层、网络层和传输层）。内核可以提供每层的性能统计信息，包括数据流经每一层时的带宽使用情况信息和错误计数信息。

#### 链路层的网络流量

在网络层次结构的最低几层，Linux可以侦测到流经链路层的数据流量的速率。链路层，通常时以太网，以帧序列的形式将信息发送到网络上。即便时其上层次的信息片段的大小比帧大很多，链路层也会将他们分割为帧，再发送到网络上。数据帧的最大尺寸被称为最大传输单位（MTU）。你可以使用网络配置工具，如ip或ifconfig来设置MTU。对以太网而言，最大大小一般为1500字节，虽然有些硬件支持的巨型帧可以高达9000字节。MTU的大小对网络效率有直接影响。链路层上的每一个帧都有一个小容量的头部，因此，使用大尺寸的MTU就提高了用户数据对开销（头部）的比例。但是，使用大尺寸的MTU，每个数据帧被损坏或丢弃的几率会更高。对清洁物理链路来说，大尺寸MTU通常会带来更好的性能，因为它需要的开销更小；反之，对嘈杂的链路来说，更小的MTU则通常会提升性能，因为，当单个帧被损坏时，它要重传的数据更少。

在物理层，帧流经物理网络，Linux内核可以收集大量有关

### 网络性能工具

#### ethtool

命令： `ethtool [device]`

ethtool输出给定的以太网设备的配置信息。如果没有特别指定设备，ethtool就会输出系统所有以太网设备的统计信息。

```
[rizhiyi@siem_20-20 ~]$ sudo ethtool em1
Settings for em1:
        Supported ports: [ TP ]
        Supported link modes:   1000baseT/Full
                                10000baseT/Full
        Supported pause frame use: Symmetric Receive-only
        Supports auto-negotiation: Yes
        Supported FEC modes: Not reported
        Advertised link modes:  1000baseT/Full
                                10000baseT/Full
        Advertised pause frame use: No
        Advertised auto-negotiation: Yes
        Advertised FEC modes: Not reported
        Speed: 1000Mb/s
        Duplex: Full
        Port: Twisted Pair
        PHYAD: 13
        Transceiver: internal
        Auto-negotiation: on
        MDI-X: Unknown
        Supports Wake-on: g
        Wake-on: g
        Current message level: 0x00000000 (0)

        Link detected: yes
```

#### 

ifconfig的主要工作就是在Linux机器上安装和配置网络接口。它还提供了系统中所有网络设备的基本性能统计信息。

命令： `ifconfig [device]`

输出的统计信息：

-   RX packets：设备已接收的数据包数
    
-   TX packets：设备已发送的数据包数
    
-   errors:发送或接收时的错误数据
    
-   dropped:发送或接收时丢弃的数据包数
    
-   overruns：网络设备没有足够缓冲区来发送或接收一个数据包的次数
    
-   frame：底层以太网帧错误的数量
    
-   carrier:由于链路介质故障（如故障电缆）而丢弃的数据包数量
    

#### ip

命令： `ip -s [-s] link`

如果你用上述选项调用IP，它就会输出系统中所有网络设备的统计信息，包括环回（lo）设备和简单互联网转换（sit0）设备。设备sit0允许将IPv6的数据包封装到IPv4的数据包中，并保持下来，这样可以缓解IPv4和IPv6之间的转换。如果ip中还有一个-s，它将回提供底层以太网更加详细的统计信息。

```
{13:23}~ ➭ ip -s -s link
6: eth0: <BROADCAST,MULTICAST,UP,LOWER_UP> mtu 1500 qdisc mq state UP mode DEFAULT group default qlen 1000
    link/ether 00:15:5d:8c:8b:dc brd ff:ff:ff:ff:ff:ff
    RX:  bytes packets errors dropped  missed   mcast
    3130718532 2179062      0       0       0   25518
    RX errors:  length    crc   frame    fifo overrun
                     0      0       0       0       0
    TX:  bytes packets errors dropped carrier collsns
      59712894  641601      0       0       0       0
    TX errors: aborted   fifo  window heartbt transns
                     0      0       0       0      85

bytes:发送或接收的字节数
packets：发送或接收的数据包数
errors：发送或接收时发生的错误数
dropped：由于网卡缺少资源，导致未发送或接收的数据包数
overruns：网络没有足够的缓冲区空间来发送或接收更多数据包的次数
missed：
mcast：已接收的多播数据包的数量
carrier：由于链路介质故障而丢弃的数据包数量
collsns：传送时设备发生的冲突次数。当多个设备是同同时使用网络时就会发生冲突
```

ip提供的时自系统启动开始的总的系统统计数据。如果使用watch，你就可以监控这些数值是如何随着时间发生变化的。

#### sar

提供了与ip和ifconfig相似的数据，但是还提供了一些关于传输层打开的套接字数量的基本信息。

```
sar [-n DEV | EDEV | SOCK | FULL] [DEVICE] [interval] [count]

-n DEV:显示每个设备发送和接收的数据包数和字节数信息
-n EDEV：显示每个设备的发送和接收错误信息
-n SOCK：显示使用套接字（TCP、UDP和RAW）的总数信息
-n FULL：显示所有的网络统计信息
interval：采样间隔时长
count：采样总数
```

```
{13:38}~ ➭ sar -n DEV 1 2
Linux 5.10.60.1-microsoft-standard-WSL2 (korov-win)     01/01/2022      _x86_64_        (16 CPU)

01:38:43 PM     IFACE   rxpck/s   txpck/s    rxkB/s    txkB/s   rxcmp/s   txcmp/s  rxmcst/s   %ifutil
01:38:44 PM        lo      0.00      0.00      0.00      0.00      0.00      0.00      0.00      0.00
01:38:44 PM     bond0      0.00      0.00      0.00      0.00      0.00      0.00      0.00      0.00
01:38:44 PM    dummy0      0.00      0.00      0.00      0.00      0.00      0.00      0.00      0.00
01:38:44 PM     tunl0      0.00      0.00      0.00      0.00      0.00      0.00      0.00      0.00
01:38:44 PM      sit0      0.00      0.00      0.00      0.00      0.00      0.00      0.00      0.00
01:38:44 PM      eth0      0.00      0.00      0.00      0.00      0.00      0.00      0.00      0.00

01:38:44 PM     IFACE   rxpck/s   txpck/s    rxkB/s    txkB/s   rxcmp/s   txcmp/s  rxmcst/s   %ifutil
01:38:45 PM        lo      0.00      0.00      0.00      0.00      0.00      0.00      0.00      0.00
01:38:45 PM     bond0      0.00      0.00      0.00      0.00      0.00      0.00      0.00      0.00
01:38:45 PM    dummy0      0.00      0.00      0.00      0.00      0.00      0.00      0.00      0.00
01:38:45 PM     tunl0      0.00      0.00      0.00      0.00      0.00      0.00      0.00      0.00
01:38:45 PM      sit0      0.00      0.00      0.00      0.00      0.00      0.00      0.00      0.00
01:38:45 PM      eth0      0.00      0.00      0.00      0.00      0.00      0.00      0.00      0.00

Average:        IFACE   rxpck/s   txpck/s    rxkB/s    txkB/s   rxcmp/s   txcmp/s  rxmcst/s   %ifutil
Average:           lo      0.00      0.00      0.00      0.00      0.00      0.00      0.00      0.00
Average:        bond0      0.00      0.00      0.00      0.00      0.00      0.00      0.00      0.00
Average:       dummy0      0.00      0.00      0.00      0.00      0.00      0.00      0.00      0.00
Average:        tunl0      0.00      0.00      0.00      0.00      0.00      0.00      0.00      0.00
Average:         sit0      0.00      0.00      0.00      0.00      0.00      0.00      0.00      0.00
Average:         eth0      0.00      0.00      0.00      0.00      0.00      0.00      0.00      0.00


{13:24}~ ➭ sar -n SOCK 1 2
Linux 5.10.60.1-microsoft-standard-WSL2 (korov-win)     01/01/2022      _x86_64_        (16 CPU)

01:38:32 PM    totsck    tcpsck    udpsck    rawsck   ip-frag    tcp-tw
01:38:33 PM       144         1         0         0         0         0
01:38:34 PM       144         1         0         0         0         0
Average:          144         1         0         0         0         0
```

输出详解：

-   rxpck/s：数据包接收速率
    
-   txpck/s：数据包发送数据
    
-   rxkB/s：kB接收速率
    
-   txkB/s：kB发送速率
    
-   rxcmp/s：压缩包接收速率
    
-   txcmp/s：压缩包发送速率
    
-   rxmcst/s：多播包接收速率
    
-   rxerr/s：接收错误率
    
-   txerr/s：发送错误率
    
-   coll/s：发送时的以太网冲突率
    
-   rxdrop/s：由于Linux内核缓冲不足而导致的接收帧丢弃率
    
-   txdrop/s：由于Linux内核缓冲区不足而导致的发帧丢弃率
    
-   txcarr/s：由于载波错误而导致的发送帧丢弃率
    
-   rxfram/s：由于帧对齐错误而导致的接收帧丢弃率
    
-   rxfifo/s：由于FIFO错误而导致的接收帧丢弃率
    
-   txfifo/s：由于FIFO错误而导致的发送帧丢弃率
    
-   totsck：当前正在被使用的套接字总数
    
-   tcpsck：当前正在被使用的TCP套接字总数
    
-   updsck：当前正在被使用的UDP套接字总数
    
-   rawsck：当前正在被使用的RAW套接字总数
    
-   ip-frag：IP分片的总数
    

#### netstat

用它可以抽取的信息包括：当前正在使用的网络套接字的数量和类型，以及有关流入和流出当前系统的UDP和TCP数据包数量的特定接口统计数据。它还能将一个套接字回溯到其特定进程或PID，这在试图确定哪个应用程序要对网络流量负责时是很有用的。

```
netstat [-p] [-c] [-interfaces=<name>] [-s] [-t] [-u] [-w]

-p:给出打开每个被显示套接字的PID/程序名
-c：每秒持续更新显示信息
-interfaces=<name>：显示指定接口的网络统计信息
--statistics | -s：IP/UDP/ICMP/TCP统计信息
--tcp|-t：仅显示TCP套接字相关信息
--udp|-u:仅显示UDP套接字相关信息
--raw|-w:仅显示RAW套接字相关信息（IP和ICMP）
```

```
[rizhiyi@siem_20-20 ~]$ netstat -t -c
Active Internet connections (w/o servers)
Proto Recv-Q Send-Q Local Address           Foreign Address         State
tcp        0      0 rizhiyi:35694           rizhiyi:XmlIpcRegSvc    ESTABLISHED
tcp        0      0 rizhiyi:27017           rizhiyi:43238           ESTABLISHED
tcp        0      0 localhost:49606         localhost:19001         TIME_WAIT
tcp        0      0 rizhiyi:mysql           rizhiyi:47238           TIME_WAIT
tcp        0      0 rizhiyi:mysql           rizhiyi:33150           ESTABLISHED

[rizhiyi@siem_20-20 ~]$ netstat --interfaces=em1
Kernel Interface table
Iface             MTU    RX-OK RX-ERR RX-DRP RX-OVR    TX-OK TX-ERR TX-DRP TX-OVR Flg
em1              1500 130053444946      0 10861739 0      287642470009      0      0      0 BMRU
```

## 自我收集的指令

### pidstat

pidstat获取服务器指定进程的使用资源信息（包括CPU、设备IO、内存、线程、任务切换等）

```
10:12:12 root@korov-win ~ pidstat
Linux 5.10.60.1-microsoft-standard-WSL2 (korov-win)     01/04/2022      _x86_64_        (16 CPU)

02:07:58 PM   UID       PID    %usr %system  %guest   %wait    %CPU   CPU  Command
02:07:58 PM     0         1    0.00    0.00    0.00    0.00    0.00     0  init
02:07:58 PM     0       114    0.00    0.00    0.00    0.00    0.00     3  docker-desktop-
02:07:58 PM     0       124    0.01    0.00    0.00    0.00    0.01    12  docker
02:07:58 PM     0       173    0.00    0.00    0.00    0.00    0.00     0  init
02:07:58 PM     0       174    0.00    0.00    0.00    0.00    0.00     2  zsh
```

输出内容解释：

1.  第一行显示服务器内核信息、主机名、日期和 CPU 个数；
    
2.  CPU 统计数据(-u)
    
    1.  %usr - 当在用户层执行(应用程序)时这个任务的cpu使用率，和 nice 优先级无关。注意这个字段计算的cpu时间不包括在虚拟处理器中花去的时间。
        
    2.  %system - 这个任务在系统层使用时的cpu使用率。
        
    3.  %guest - 任务花费在虚拟机上的cpu使用率（运行在虚拟处理器）。
        
    4.  %CPU - 任务总的cpu使用率。在SMP环境(多处理器)中，如果在命令行中输入-I参数的话，cpu使用率会除以你的cpu数量。
        
    5.  CPU - 正在运行这个任务的处理器编号。
        
    
3.  IO 统计数据(-d)
    
    1.  kB\_rd/s - 任务从硬盘上的读取速度（kb）
        
    2.  kB\_wr/s - 任务向硬盘中的写入速度（kb）
        
    3.  kB\_ccwr/s - 任务写入磁盘被取消的速率（kb）
        
    
4.  页面失败和内存使用(-r)
    
    1.  minflt/s - 从内存中加载数据时每秒出现的小的错误的数目，这些不要求从磁盘载入内存页面。
        
    2.  majflt/s - 从内存中加载数据时每秒出现的较大错误的数目，这些要求从磁盘载入内存页面。
        
    3.  VSZ - 虚拟容量：整个进程的虚拟内存使用（kb）
        
    4.  RSS - 长期内存使用：任务的不可交换物理内存的使用量（kb）
        
    
5.  上下文切换情况（-w）
    
    1.  Cswch/s:每秒主动任务上下文切换数量
        
    2.  Nvcswch/s:每秒被动任务上下文切换数量