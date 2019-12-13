# 常见46题

**问题一：**

绝对路径用什么符号表示？当前目录、上层目录用什么表示？主目录用什么表示? 切换目录用什么命令？

**答案：**
绝对路径： 如/etc/init.d
当前目录和上层目录： ./  ../
主目录： ~/
切换目录： cd

**问题二：**

怎么查看当前进程？怎么执行退出？怎么查看当前路径？
**答案：**
查看当前进程： ps
执行退出： exit
查看当前路径： pwd

**问题三：**

怎么清屏？怎么退出当前命令？怎么执行睡眠？怎么查看当前用户 id？查看指定帮助用什么命令？
**答案：**
清屏： clear
退出当前命令： ctrl+c 彻底退出
执行睡眠 ： ctrl+z 挂起当前进程fg 恢复后台
查看当前用户 id： ”id“：查看显示目前登陆账户的 uid 和 gid 及所属分组及用户名
查看指定帮助： 如 man adduser 这个很全 而且有例子； adduser --help 这个告诉你一些常用参数； info adduesr；

**问题四：**

Ls 命令执行什么功能？ 可以带哪些参数，有什么区别？
**答案：**
ls 执行的功能： 列出指定目录中的目录，以及文件
哪些参数以及区别： a 所有文件l 详细信息，包括大小字节数，可读可写可执行的权限等

**问题五：**

建立软链接(快捷方式)，以及硬链接的命令。
**答案：**
软链接： ln -s slink source
硬链接： ln link source

**问题六：**

目录创建用什么命令？创建文件用什么命令？复制文件用什么命令？
**答案：**
创建目录： mkdir
创建文件：典型的如 touch，vi 也可以创建文件，其实只要向一个不存在的文件输出，都会创建文件
复制文件： cp 7. 文件权限修改用什么命令？格式是怎么样的？
文件权限修改： chmod
格式如下：

> chmodu+xfile给file的属主增加执行权限chmodu+xfile给file的属主增加执行权限 chmod 751 file 给 file 的属主分配读、写、执行(7)的权限，给 file 的所在组分配读、执行(5)的权限，给其他用户分配执行(1)的权限
> chmodu=rwx,g=rx,o=xfile上例的另一种形式chmodu=rwx,g=rx,o=xfile上例的另一种形式 chmod =r file 为所有用户分配读权限
> chmod444file同上例chmod444file同上例 chmod a-wx,a+r file同上例
> $ chmod -R u+r directory 递归地给 directory 目录下所有文件和子目录的属主分配读的权限

 

**问题八：**

查看文件内容有哪些命令可以使用？
**答案：**
vi 文件名 #编辑方式查看，可修改
cat 文件名 #显示全部文件内容
more 文件名 #分页显示文件内容
less 文件名 #与 more 相似，更好的是可以往前翻页
tail 文件名 #仅查看尾部，还可以指定行数
head 文件名 #仅查看头部,还可以指定行数

**问题九：**

随意写文件命令？怎么向屏幕输出带空格的字符串，比如”hello world”? 

**答案：**

写文件命令：vi

向屏幕输出带空格的字符串:echo hello world

 

**问题十：**

终端是哪个文件夹下的哪个文件？黑洞文件是哪个文件夹下的哪个命令？
**答案：**
终端 /dev/tty

黑洞文件 /dev/null

**问题十一：**

移动文件用哪个命令？改名用哪个命令？
**答案：**
mv mv

**问题十二：**

复制文件用哪个命令？如果需要连同文件夹一块复制呢？如果需要有提示功能呢？
**答案：**
cp cp -r  ？？？？

**问题十三：**

删除文件用哪个命令？如果需要连目录及目录下文件一块删除呢？删除空文件夹用什么命令？
**答案：**
rm rm -r rmdir

**问题十四：** 

Linux 下命令有哪几种可使用的通配符？分别代表什么含义?
**答案：**
“？”可替代单个字符。

“*”可替代任意多个字符。

方括号“[charset]”可替代 charset 集中的任何单个字符，如[a-z]，[abABC]

 

**问题十五：**

用什么命令对一个文件的内容进行统计？(行号、单词数、字节数)
**答案：**

wc 命令 - c 统计字节数 - l 统计行数 - w 统计字数。

**问题十六：**

Grep 命令有什么用？ 如何忽略大小写？ 如何查找不含该串的行?
**答案：**
是一种强大的文本搜索工具，它能使用正则表达式搜索文本，并把匹 配的行打印出来。
grep [stringSTRING] filename grep [^string] filename

**问题十七：**

Linux 中进程有哪几种状态？在 ps 显示出来的信息中，分别用什么符号表示的？
**答案：**
（1）、不可中断状态：进程处于睡眠状态，但是此刻进程是不可中断的。不可中断， 指进程不响应异步信号。
（2）、暂停状态/跟踪状态：向进程发送一个 SIGSTOP 信号，它就会因响应该信号 而进入 TASK_STOPPED 状态;当进程正在被跟踪时，它处于 TASK_TRACED 这个特殊的状态。
“正在被跟踪”指的是进程暂停下来，等待跟踪它的进程对它进行操作。

（3）、就绪状态：在 run_queue 队列里的状态

（4）、运行状态：在 run_queue 队列里的状态
（5）、可中断睡眠状态：处于这个状态的进程因为等待某某事件的发生（比如等待 socket 连接、等待信号量），而被挂起
（6）、zombie 状态（僵尸）：父亲没有通过 wait 系列的系统调用会顺便将子进程的尸体（task_struct）也释放掉
（7）、退出状态

> D 不可中断 Uninterruptible（usually IO）
> R 正在运行，或在队列中的进程
> S 处于休眠状态
> T 停止或被追踪
> Z 僵尸进程
> W 进入内存交换（从内核 2.6 开始无效）
> X 死掉的进程

 

**问题十八：**

怎么使一个命令在后台运行?
**答案：**
一般都是使用 & 在命令结尾来让程序自动运行。(命令后可以不追加空格)



**问题十九：**

利用 ps 怎么显示所有的进程? 怎么利用 ps 查看指定进程的信息？
**答案：**
ps -ef (system v 输出) 

ps -aux bsd 格式输出

ps -ef | grep pid

**问题二十：**

哪个命令专门用来查看后台任务? 

**答案：**

job -l



**问题二十一：**

把后台任务调到前台执行使用什么命令?把停下的后台任务在后台执行起来用什么命令?
**答案：**
把后台任务调到前台执行 fg

把停下的后台任务在后台执行起来 bg

 

**问题二十二：**

终止进程用什么命令? 带什么参数? 

**答案：**

kill [-s <信息名称或编号>][程序] 或 kill [-l <信息编号>] 

kill-9 pid

 

**问题二十三：**

怎么查看系统支持的所有信号？

**答案：**

kill -l

**问题二十四：**

搜索文件用什么命令? 格式是怎么样的? 

**答案：**

find <指定目录> <指定条件> <指定动作>

whereis 加参数与文件名

locate 只加文件名

find 直接搜索磁盘，较慢。

find / -name "string*"

 

**问题二十五：**

查看当前谁在使用该主机用什么命令? 查找自己所在的终端信息用什么命令?
**答案：**
查找自己所在的终端信息：who am i

查看当前谁在使用该主机：who

 

**问题二十六：**

使用什么命令查看用过的命令列表?

**答案：**

history



**问题二十七：**

使用什么命令查看磁盘使用空间？ 空闲空间呢?

答案：

df -hl
文件系统 容量 已用 可用 已用% 挂载点
Filesystem Size Used Avail Use% Mounted on /dev/hda2 45G 19G 24G 44% /
/dev/hda1 494M 19M 450M 4% /boot

**问题二十八：**

使用什么命令查看网络是否连通?
**答案：**
netstat

**问题二十九：**

使用什么命令查看 ip 地址及接口信息？

**答案：**

ifconfig

**问题三十：**

查看各类环境变量用什么命令?

**答案：**

查看所有 env
查看某个，如 home： env $HOME

**问题三十一：**

通过什么命令指定命令提示符?

**答案：**

> \u：显示当前用户账号
>
> \h：显示当前主机名
>
> \W：只显示当前路径最后一个目录
>
> \w：显示当前绝对路径（当前用户目录会以~代替）
>
> $PWD：显示当前全路径
>
> $：显示命令行’$'或者’#'符号
>
> \#：下达的第几个命令
>
> \d：代表日期，格式为week day month date，例如："MonAug1"
>
> \t：显示时间为24小时格式，如：HH：MM：SS
>
> \T：显示时间为12小时格式
>
> \A：显示时间为24小时格式：HH：MM
>
> \v：BASH的版本信息 如export PS1=’[\u@\h\w\#]$‘

 

**问题三十二：**

查找命令的可执行文件是去哪查找的? 怎么对其进行设置及添加? 

**答案：**

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

 

**问题三十三：**

通过什么命令查找执行命令?
**答案：**
which 只能查可执行文件

whereis 只能查二进制文件、说明文档，源文件等



**问题三十四：**

怎么对命令进行取别名？
**答案：**
alias la='ls -a'

**问题三十五：**

du 和 df 的定义，以及区别？
**答案：**

du 显示目录或文件的大小

df 显示每个<文件>所在的文件系统的信息，默认是显示所有文件系统。
（文件系统分配其中的一些磁盘块用来记录它自身的一些数据，如 i 节点，磁盘分布图，间接块，超级块等。这些数据对大多数用户级的程序来说是不可见的，通常称为 Meta Data。） du 命令是用户级的程序，它不考虑 Meta Data，而 df 命令则查看文件系统的磁盘分配图并考虑 Meta Data。
df 命令获得真正的文件系统数据，而 du 命令只查看文件系统的部分情况。

**问题三十六：**

awk 详解。
**答案：**

> awk '{pattern + action}' {filenames}
> \#cat /etc/passwd |awk -F ':' '{print 1"\t"1"\t"7}' //-F 的意思是以':'分隔 root /bin/bash
> daemon /bin/sh 搜索/etc/passwd 有 root 关键字的所有行

> \#awk -F: '/root/' /etc/passwd root:x:0:0:root:/root:/bin/bash

 

**问题三十七：**

当你需要给命令绑定一个宏或者按键的时候，应该怎么做呢？

**答案：**

可以使用bind命令，bind可以很方便地在shell中实现宏或按键的绑定。

在进行按键绑定的时候，我们需要先获取到绑定按键对应的字符序列。

比如获取F12的字符序列获取方法如下：先按下Ctrl+V,然后按下F12 .我们就可以得到F12的字符序列 ^[[24~。

接着使用bind进行绑定。

[root@localhost ~]# bind ‘”\e[24~":"date"'

注意：相同的按键在不同的终端或终端模拟器下可能会产生不同的字符序列。

【附】也可以使用showkey -a命令查看按键对应的字符序列。

 

**问题三十八：**

如果一个linux新手想要知道当前系统支持的所有命令的列表，他需要怎么做？

**答案：**

使用命令compgen ­-c，可以打印出所有支持的命令列表。

> [root@localhost ~]$ compgen -c
>
> l.
>
> ll
>
> ls
>
> which
>
> if
>
> then
>
> else
>
> elif
>
> fi
>
> case
>
> esac
>
> for
>
> select
>
> while
>
> until
>
> do
>
> done
>
> …

 

**问题三十九：**

如果你的助手想要打印出当前的目录栈，你会建议他怎么做？

**答案：**

使用Linux 命令dirs可以将当前的目录栈打印出来。

> [root@localhost ~]# dirs

> /usr/share/X11

【附】：目录栈通过pushd popd 来操作。

 

**问题四十：**

你的系统目前有许多正在运行的任务，在不重启机器的条件下，有什么方法可以把所有正在运行的进程移除呢？

**答案：**

使用linux命令 ’disown -r ’可以将所有正在运行的进程移除。

 

**问题四十一：**

bash shell 中的hash 命令有什么作用？

**答案：**

linux命令’hash’管理着一个内置的哈希表，记录了已执行过的命令的完整路径, 用该命令可以打印出你所使用过的命令以及执行的次数。

> [root@localhost ~]# hash
>
> hits command
>
> 2 /bin/ls
>
> 2 /bin/su

 

**问题四十二：**

哪一个bash内置命令能够进行数学运算。

**答案：**

bash shell 的内置命令let 可以进行整型数的数学运算。

> \#! /bin/bash
> …
> …
> let c=a+b
> …
> …

 

**问题四十三：**

怎样一页一页地查看一个大文件的内容呢？

**答案：**

通过管道将命令”cat file_name.txt” 和 ’more’ 连接在一起可以实现这个需要.

[root@localhost ~]# cat file_name.txt | more

 

**问题四十四：**

数据字典属于哪一个用户的？

**答案：**

数据字典是属于’SYS’用户的，用户‘SYS’ 和 ’SYSEM’是由系统默认自动创建的

 

**问题四十五：**

怎样查看一个linux命令的概要与用法？假设你在/bin目录中偶然看到一个你从没见过的的命令，怎样才能知道它的作用和用法呢？

**答案：**

使用命令whatis 可以先出显示出这个命令的用法简要，比如，你可以使用whatis zcat 去查看‘zcat’的介绍以及使用简要。

> [root@localhost ~]# whatis zcat
>
> zcat [gzip] (1) – compress or expand files

 

**问题四十六：**

使用哪一个命令可以查看自己文件系统的磁盘空间配额呢？

**答案：**

使用命令repquota 能够显示出一个文件系统的配额信息

【附】只有root用户才能够查看其它用户的配额。

# 常见39题

**1、如何看当前Linux系统有几颗物理CPU和每颗CPU的核数？**

答：[root@centos6 ~ 10:55 #35]# cat /proc/cpuinfo|grep -c 'physical id'

4

[root@centos6 ~ 10:56 #36]# cat /proc/cpuinfo|grep -c 'processor'

4

**2、查看系统负载有两个常用的命令，是哪两个？这三个数值表示什么含义呢？**

答：[root@centos6 ~ 10:56 #37]# w

10:57:38 up 14 min,  1 user,  load average: 0.00, 0.00, 0.00

USER   TTY    FROM        LOGIN@  IDLE  JCPU  PCPU WHAT

root   pts/0   192.168.147.1   18:44   0.00s  0.10s  0.00s w

[root@centos6 ~ 10:57 #38]# uptime

10:57:47 up 14 min,  1 user,  load average: 0.00, 0.00, 0.00

其中load average即系统负载，三个数值分别表示一分钟、五分钟、十五分钟内系统的平均负载，即平均任务数。

**3、vmstat r, b, si, so, bi, bo 这几列表示什么含义呢？**

答：[root@centos6 ~ 10:57 #39]# vmstat

procs -----------memory---------- ---swap-- -----io---- --system-- -----cpu-----

r  b  swpd  free  buff  cache  si  so   bi   bo  in  cs us sy id wa st

0  0    0 1783964  13172 106056   0   0   29   7  15  11  0  0 99  0  0

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

**4、linux系统里，您知道buffer和cache如何区分吗？**

答：buffer和cache都是内存中的一块区域，当CPU需要写数据到磁盘时，由于磁盘速度比较慢，所以CPU先把数据存进buffer，然后CPU去执行其他任务，buffer中的数据会定期写入磁盘；当CPU需要从磁盘读入数据时，由于磁盘速度比较慢，可以把即将用到的数据提前存入cache，CPU直接从Cache中拿数据要快的多。

**5、使用top查看系统资源占用情况时，哪一列表示内存占用呢？**

答： PID USER    PR  NI  VIRT  RES  SHR S %CPU %MEM   TIME+  COMMAND

301 root    20  0   0   0   0 S  0.3  0.0  0:00.08 jbd2/sda3-8

1 root    20  0  2900 1428 1216 S  0.0  0.1  0:01.28 init

2 root    20  0   0   0   0 S  0.0  0.0  0:00.00 kthreadd

3 root    RT  0   0   0   0 S  0.0  0.0  0:00.00 migration/0

VIRT虚拟内存用量

RES物理内存用量

SHR共享内存用量

%MEM内存用量

**6、如何实时查看网卡流量为多少？如何查看历史网卡流量？**

答：安装sysstat包，使用sar命令查看。

yum install -y sysstat#安装sysstat包，获得sar命令

sar -n DEV#查看网卡流量，默认10分钟更新一次

sar -n DEV 1 10#一秒显示一次，一共显示10次

sar -n DEV -f /var/log/sa/sa22#查看指定日期的流量日志

**7、如何查看当前系统都有哪些进程？**

答：ps -aux 或者ps -elf

[root@centos6 ~ 13:20 #56]# ps -aux

Warning: bad syntax, perhaps a bogus '-'? See /usr/share/doc/procps-3.2.8/FAQ

USER    PID %CPU %MEM   VSZ  RSS TTY    STAT START  TIME COMMAND

root     1  0.0  0.0  2900  1428 ?     Ss  10:43  0:01 /sbin/init

root     2  0.0  0.0    0   0 ?     S   10:43  0:00 [kthreadd]

root     3  0.0  0.0    0   0 ?     S   10:43  0:00 [migration/0]

root     4  0.0  0.0    0   0 ?     S   10:43  0:00 [ksoftirqd/0]

……

[root@centos6 ~ 13:21 #57]# ps -elf

F S UID     PID  PPID  C PRI  NI ADDR SZ WCHAN  STIME TTY      TIME CMD

4 S root     1   0  0  80  0 -  725 -    10:43 ?     00:00:01 /sbin/init

1 S root     2   0  0  80  0 -   0 -    10:43 ?     00:00:00 [kthreadd]

1 S root     3   2  0 -40  - -   0 -    10:43 ?     00:00:00 [migration/0]

1 S root     4   2  0  80  0 -   0 -    10:43 ?     00:00:00 [ksoftirqd/0]

1 S root     5   2  0 -40  - -   0 -    10:43 ?     00:00:00 [migration/0]

**8、ps 查看系统进程时，有一列为STAT， 如果当前进程的stat为Ss 表示什么含义？如果为Z表示什么含义？**

答：S表示正在休眠；s表示主进程；Z表示僵尸进程。

**9、如何查看系统都开启了哪些端口？**

答：[root@centos6 ~ 13:20 #55]# netstat -lnp

Active Internet connections (only servers)

Proto Recv-Q Send-Q Local Address        Foreign Address       State    PID/Program name

tcp     0    0 0.0.0.0:22          0.0.0.0:*          LISTEN    1035/sshd

tcp     0    0 :::22            :::*             LISTEN    1035/sshd

udp     0    0 0.0.0.0:68          0.0.0.0:*                931/dhclient

Active UNIX domain sockets (only servers)

Proto RefCnt Flags    Type    State     I-Node PID/Program name   Path

unix  2    [ ACC ]   STREAM   LISTENING   6825  1/init        @/com/ubuntu/upstart

unix  2    [ ACC ]   STREAM   LISTENING   8429  1003/dbus-daemon   /var/run/dbus/system_bus_socket

**10、如何查看网络连接状况？**

答：[root@centos6 ~ 13:22 #58]# netstat -an

Active Internet connections (servers and established)

Proto Recv-Q Send-Q Local Address        Foreign Address       State

tcp     0    0 0.0.0.0:22          0.0.0.0:*          LISTEN

tcp     0    0 192.168.147.130:22      192.168.147.1:23893     ESTABLISHED

tcp     0    0 :::22            :::*             LISTEN

udp     0    0 0.0.0.0:68          0.0.0.0:*

……

**11、想修改ip，需要编辑哪个配置文件，修改完配置文件后，如何重启网卡，使配置生效？**

答：使用vi或者vim编辑器编辑网卡配置文件/etc/sysconfig/network-scripts/ifcft-eth0（如果是eth1文件名为ifcft-eth1），内容如下：

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

**12、能否给一个网卡配置多个IP? 如果能，怎么配置？**

答：可以给一个网卡配置多个IP，配置步骤如下：

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

**13、如何查看某个网卡是否连接着交换机？**

答：mii-tool eth0 或者 mii-tool eth1

**14、如何查看当前主机的主机名，如何修改主机名？要想重启后依旧生效，需要修改哪个配 置文件呢？**

答：查看主机名：hostname

centos6.5

修改主机名：hostname centos6.5-1

永久生效需要修改配置文件：vim /etc/sysconfig/network

NETWORKING=yes

HOSTNAME=centos6.5-1

**15、设置DNS需要修改哪个配置文件？**

答：（1）在文件 /etc/resolv.conf 中设置DNS

（2）在文件 /etc/sysconfig/network-scripts/ifcfg-eth0 中设置DNS

**16、使用iptables 写一条规则：把来源IP为192.168.1.101访问本机80端口的包直接拒绝**

答：iptables -I INPUT -s 192.168.1.101 -p tcp --dport 80 -j REJECT

**17、要想把iptable的规则保存到一个文件中如何做？如何恢复？**

答：使用iptables-save重定向到文件中：iptables-save > 1.ipt

使用iptables-restore反重定向回来：iptables-restore < 1.ipt

**18、如何备份某个用户的任务计划？**

答：将/var/spool/cron/目录下指定用户的任务计划拷贝到备份目录cron_bak/下即可

cp /var/spool/cron/rachy /tmp/bak/cron_bak/

19、任务计划格式中，前面5个数字分表表示什么含义？

答：依次表示：分、时、日、月、周

**20、如何可以把系统中不用的服务关掉？**

答：（1）使用可视化工具：ntsysv

（2）使用命令：chkconfig servicename off

**21、如何让某个服务（假如服务名为 nginx）只在3,5两个运行级别开启，其他级别关闭？**

答：先关闭所有运行级别：chkconfig nginx off

然后打开35运行级别：chkconfig --level 35 nginx on

**22、rsync 同步命令中，下面两种方式有什么不同呢？**

(1) rsync -av  /dira/  ip:/dirb/

(2) rsync -av  /dira/  ip::dirb

答：(1)前者是通过ssh方式同步的

(2)后者是通过rsync服务的方式同步的

**23、rsync 同步时，如果要同步的源中有软连接，如何把软连接的目标文件或者目录同步？**

答：同步源文件需要加-L选项

**24、某个账号登陆linux后，系统会在哪些日志文件中记录相关信息？**

答：用户身份验证过程记录在/var/log/secure中，登录成功的信息记录在/var/log/wtmp。

**25、网卡或者硬盘有问题时，我们可以通过使用哪个命令查看相关信息？**

答：使用命令dmesg

**26、分别使用xargs和exec实现这样的需求，把当前目录下所有后缀名为.txt的文件的权限修改为777**

答：（1）find ./ -type f -name "*.txt" |xargs chmod 777

（2）find ./ -type f -name "*.txt" -exec chmod 777 {} ;

**27、有一个脚本运行时间可能超过2天，如何做才能使其不间断的运行，而且还可以随时观察脚本运行时的输出信息？**

答：使用screen工具

**28、在Linux系统下如何按照下面要求抓包：只过滤出访问http服务的，目标ip为192.168.0.111，一共抓1000个包，并且保存到1.cap文件中？**

答：tcpdump -nn -s0 host 192.168.0.111 and port 80 -c 1000 -w 1.cap

**29、rsync 同步数据时，如何过滤出所有.txt的文件不同步？**

答：加上--exclude选项：--exclude=“*.txt”

**30、rsync同步数据时，如果目标文件比源文件还新，则忽略该文件，如何做？**

答：保留更新使用-u或者--update选项

**31、想在Linux命令行下访问某个网站，并且该网站域名还没有解析，如何做？**

答：在/etc/hosts文件中增加一条从该网站域名到其IP的解析记录即可，或者使用curl -x

**32、自定义解析域名的时候，我们可以编辑哪个文件？是否可以一个ip对应多个域名？是否一个域名对应多个ip？**

答：编辑 /etc/hosts ,可以一个ip对应多个域名，不可以一个域名对多个ip

**33、我们可以使用哪个命令查看系统的历史负载（比如说两天前的）？**

答：sar -q -f /var/log/sa/sa22  #查看22号的系统负载

**34、在Linux下如何指定dns服务器，来解析某个域名？**

答：使用dig命令：dig @DNSip  domain.com

如：dig @8.8.8.8 www.baidu.com#使用谷歌DNS解析百度

**35、使用rsync同步数据时，假如我们采用的是ssh方式，并且目标机器的sshd端口并不是默认的22端口，那我们如何做？**

答：rsync "--rsh=ssh -p 10022"或者rsync -e "ssh -p 10022"

**36、rsync同步时，如何删除目标数据多出来的数据，即源上不存在，但目标却存在的文件或者目录？**

答：加上--delete选项

**37、使用free查看内存使用情况时，哪个数值表示真正可用的内存量？**

答：free列第二行的值

**38、有一天你突然发现公司网站访问速度变的很慢很慢，你该怎么办呢？**

（服务器可以登陆，提示：你可以从系统负载和网卡流量入手）

答：可以从两个方面入手分析：分析系统负载，使用w命令或者uptime命令查看系统负载，如果负载很高，则使用top命令查看CPU，MEM等占用情况，要么是CPU繁忙，要么是内存不够，如果这二者都正常，再去使用sar命令分析网卡流量，分析是不是遭到了攻击。一旦分析出问题的原因，采取对应的措施解决，如决定要不要杀死一些进程，或者禁止一些访问等。

**39、rsync使用服务模式时，如果我们指定了一个密码文件，那么这个密码文件的权限应该设置成多少才可以？**

答：600或400