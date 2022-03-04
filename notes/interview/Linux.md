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