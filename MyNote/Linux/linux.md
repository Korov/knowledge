# 5 Linux的文件权限与目录配置

### 5.2.1 Linux文件属性

![image-20191205200500394](picture\image-20191205200500394.png)

#### 1 第一列代表这个文件的类型与权限

`drwxr-xr-x.` 中第一个字母代表文件类型，第二到第四个代表文件拥有者的权限，第五到第七代表文件所属群组权限，第八到第十代表其他人拥有的权限。

第一个字母含义：

- d：目录
- -：文件
- l：连接档（link file），类似windows中的快捷方式
- b：装置文件里面的可供存储的接口设备（可随机存取装置）
- c：文件里面的串行端口设备，例如键盘、鼠标

rwx分别代表可读、可写、可执行。

#### 2 第二列表示有多少档名连接到此节点（i-node）

#### 3 第三列表示这个文件（或目录）的拥有者帐号

#### 4 第四列表示这个文件的所属群组

#### 5 第五列表示文件的容量大小，默认单位为bytes

#### 6 第六列表示这个文件的建档日期或者是最近的修改日期

#### 7 第七列文件的档名

### 5.2.2 如何该表文件属性与权限

- chgrp：改变文件所属群组
- chown：改变文件拥有者
- chmod：改变文件的权限，SUID,SGID,SBIT等等的特性

改变权限时可以通过数字来实现，r:4,w:2,x:1。rwx的数字表示为4+2+1=7。

```bash
chmod 760 file_name
```

第一个数字表示所有者的权限为7即读写执行，第二个数字表示所属群组的权限为6即读写，第三个数字表示其他人的权限为0即不可读不可写不可执行。

也可以通过符号来改变文件的权限，u代表拥有者，g代表所属群组，o代表其他人，a代表所有，+表示加入，-去除，=设定

```bash
#使用了=设定了文件的权限
chmod u=rwx,g=rx,o=- file_name
#为所有用户添加写的权限
chmod a+w file_name
```

### 5.2.3 目录与文件的权限意义

#### 1 文件的权限

r：可读取此文件的实际内容

w：可以编辑、新增或者是修改该文件的内容，但是不可以删除文件

x：该文件具有可以被系统执行的权限

#### 2 目录的权限

r：具有读取目录结构的权限

w：可以建立新的文件与目录，删除已经存在的文件与目录，将已存在的文件或目录进行更名，搬移该目录内的文件、目录位置

x：代表用户能否进入该目录成为工作目录的用途，工作目录指目前所在的目录

### 5.2.4 Linux文件种类与扩展名

Linux文件长度限制：一般单一文件或目录的最大容许文件名为255bytes，以一个英文占一个bytes，一个中文占两个bytes。

Linux文件名的限制：避免***?><;&![]\'"`(){}**

## 5.3 Linux目录配置

### 5.3.1 Linux目录配置的依据--FHS

Filesystem Hierarchy Standard标准

|          | 可分享得                    | 不可分享得              |
| -------- | --------------------------- | ----------------------- |
| 不变的   | /usr（软件放置处）          | /etc（配置文件）        |
| 不变的   | /opt（第三方协力软件）      | /boot（开机与核心文档） |
| 可变动的 | /var/mail（使用者邮件信箱） | /var/run（程序相关）    |
| 可变动的 | /var/spool/news（新闻组）   | /var/lock（程序相关）   |

- 可分享的：可以分析那个给其他系统挂载使用的目录，所以包括执行文件与用户的邮件等数据，是能够分析那个给网络上其他主机挂载使用的目录
- 不可分享的：自己机器上面运作的装置文件或者是与程序有关的socket文件等，由于仅与自身机器有关，所以不适合分享给其他主机
- 不变的：有些数据是不会经常变动的，跟随者distribution而不变动。例如函数库、文件说明文件、系统管理员所管理的主机服务配置文件等
- 可变动的：经常改变的数据，例如登录文件

事实上，FHS针对目录树架构仅定义出三层目录底下应该放置什么数据而已，分别是底下这三个目录的定义：

- /：与开机系统有关
- /usr（unix software resource）：与软件安装、执行有关
- /var(variable):与系统运作过程有关

#### 1 根目录的意义与内容

根目录是整个系统最重要的一个目录，因为不但所有的目录都是由根目录衍生出来的，同时根目录也与开机/还原/系统修复等动作有关。

因此FHS标准建议：根目录所在分区槽应该越小越好，且应用程序所安装的软件最好不要与根目录放在同一个分区槽内，保持根目录越小越好。如此不但效能较佳，根目录所在的文件系统也较不容易发生问题。

FHS定义可以存放的目录文件：

| 目录       | 应放置文件内容                                               |
| ---------- | ------------------------------------------------------------ |
|            | 第一部分FHS要求必须存在的目录                                |
| /bin       | 系统有很多放置执行文件的目录，但/bin比较特殊，因为其**放置的是在单人维护模式下还能够被操作的指令**。在其中存放的指令可以被root与一般帐号所使用，主要有：cat,chmod,chown,date,mv,mkdir,cp,bash等等常用的指令 |
| /boot      | 这个目录主要在放置开机会使用到的文件，包括Linux核心文件以及开机选单与开机所需配置文件等等。Linux kernel常用的档名为：vmlinuz，如果使用的是grub2这个开机管理程序，则还会存在/boot/grub2/这个目录 |
| /dev       | 在Linux系统上，任何装置与接口设备都是以文件的形态存在这个目录当中。你只要透过存取这个目录底下的某个文件，就等于存取某个装置。 |
| /etc       | 系统主要的配置文件几乎都放置在这个目录内，例如人员的帐号密码、各种服务的启始档等等。一般来说，这个目录下的各文件属性是可以让一般使用者查阅的，但是只有root有权力修改。FHS建议不要放置可执行文件（binary）在这个目录中。比较重要的文件有：/etc/modprobe.d,/etc/passwd,/etc/fstab,/etc/issue等等。另外FHS还规范几个重要的目录最好要在/etc目录下：/etc/opt(必要)：这个目录放置第三方协力软件的相关配置；/etc/X11/(建议)：与X Windows有关的各种配置文件都在这里；/etc/sgml/(建议)：与SGML格式有关的各项配置文件；/etc/xml/(建议)：与XML格式有关的各项配置文件 |
| /lib       | 系统的函式库非常的多，而/lib放置的则是在开机时会用到的函式库，以及在/bin或/sbin底下的指令会呼叫的函式库。另外FHS要求底下的目录必须存在/bin/modules/这个目录主要放置可抽换式的核心相关模块 |
| /media     | 放置可移除的装置。包括软盘、光盘等。                         |
| /mnt       | **暂时**挂载某些额外的装置，一般建议放到这个目录中。         |
| /opt       | 第三方协力软件放置的目录。例如KDE这个桌面管理系统是一个独立的计划，不过他可以安装到Linux系统中，因此KDE的软件就建议放置到此目录下。如果你想要自行安装额外的软件，那么也能够将你的软件安装到这里来。 |
| /run       | 早期的FHS规定系统开机后所产生的各项信息应该放置到/var/run目录下，新版的FHS规范放到/run，由于/run可以使用内存来仿真，因此效能上会好很多 |
| /sbin      | Linux有非常多的指令是用来设定系统环境的，这些指令只有root才能够利用来设定系统，其他用户最多只能用来查询而已。放在/sbin下的为开机过程中所需要的，里面包括了开机、修复、还原系统所需要的指令。至于某些服务器软件程序，一般放置了/usr/sbin/中。至于本机自行安装的软件所产生的系统执行文件，则放置到/usr/local/sbin/当中了。常见的指令包括：fdisk,fsck,ifconfig,mkfs等等 |
| /srv       | srv可以视为service的缩写，是一些网络服务启动之后，这些服务所需要取用的数据目录。常见的服务例如WWW,FTP等。 |
| /tmp       | 这是让一般用户或者正在执行的程序暂时放置文件的地方。这个目录是任何人都能够存取的，所以你需要定期地清理一下。 |
| /usr       | 第二层FHS设定                                                |
| /var       | 第二层FHS设定，主要放置变动性数据                            |
|            | 第二部分：FHS建议可以存在的目录                              |
| /home      | 这是系统默认的用户家目录。在你新增一个一般使用者帐号时，默认的用户家目录都会规范到这里来。~:代表当前这个用户的家目录；~dmtsai：代表dmtsai的家目录 |
| /lib<qual> | 用来存放于/lib不同的格式的二进制函式库。例如支持64位的/lib64函式库等 |
| /root      | 系统管理员root的家目录。之所以放在这里，是因为如果进入单人维护模式而仅挂载根目录时，该目录就能够拥有root的夹目录，所以我们希望root的家目录与根目录放置在同一个分区槽中 |

早期的Linux在设计的时候，若放生问题时，救援模式通常仅挂载根目录而已，因此有五个重要的目录被要求一定要与根目录放置在一起，那就是/etc,/bin,/dev,/lib,/sbin。现在许多的Linux distribution由于已经将许多非必要的文件移出/usr之外了，所以/usr也越来越精简，同时因为/usr被建议为“即使挂载成为只读，系统还是可以正常运作”的模样，所以救援模式同时挂载/usr，例如CentOS7.x将/sbin,/bin/lib统统移动到/usr下面了。

#### 2 /usr的意义与内容

依据FHS的基本定义，/usr里面放置的数据属于可分享的与不可变动的。FHS建议所有软件开发者，应将他们的数据合理的分辨放置到这个目录下的次目录，而不要自行建立该软件自己独立的目录。因为是所有系统默认的软件都会放置到/usr下，因此这个目录有点类似windows系统的“C:\Windows\+C:\Program files\”这两个目录的综合体，系统刚安装完毕时，这个目录会占用最多的硬盘容量。

| 目录             | 应放置文件内容                                               |
| ---------------- | ------------------------------------------------------------ |
|                  | 第一部分：FHS要求必须要存在的目录                            |
| /usr/bin/        | 所有一般用户能够使用的命令都放在这里！目前新的CentOS7已经将全部的用户指令放置于此，而使用连接文档的方式将/bin连接至此。另外，FHS要求在此目录下不应该有子目录 |
| /usr/lib/        | 基本上，与/bin功能相同，所以/lib就是连接到此目录中的         |
| /usr/local/      | 系统管理员在本机自行安装 自己下载的软件，建议安装到此目录，这样比较便于管理。 |
| /usr/sbin/       | 非系统正常运作所需要的系统指令。最常见的就是某些网络服务器软件的服务指令（daemon） |
| /usr/share/      | 主要放置只读架构的数据文件，当然也包括共享文件。在这个目录下放置的数据几乎是不分硬件架构均可读取的数据，因为几乎都是文本文件，此目录下常见的还有这些次目录：/usr/share/man:联机帮助文件；/usr/share/doc:软件杂项的文件说明；/usr/share/zoneinfo：与时区有关的时区文件 |
|                  | 第二部分：FHS建议可以存在的目录                              |
| /usr/games/      | 与游戏比较相关的数据放置处                                   |
| /usr/include/    | c/c++等程序语言的挡头（header）与包含档（include）放置处，   |
| /usr/libexec/    | 某些不被一般使用者惯用的执行档或脚本等等，都会放置在此目录中 |
| /usr/lib\<qual>/ | 与/lib\<qual>功能相同                                        |
| /usr/src/        | 一般原始码建议放置到这里，src有source的意思。至于核心原始码则建议放置到/usr/src/linux/目录下 |

#### 3 /var的意义与内容

/var是在系统运作后才会渐渐占用硬盘容量的目录。因为/var目录主要针对常态性变动的文件，包括缓存（cache）、登录文件（log file）以及某些软件运作锁产生的文件，包括程序文件（lock file，run file），或者例如MySQL数据库的文件等等。

| 目录        | 应放置的文件内容                                             |
| ----------- | ------------------------------------------------------------ |
| /var/cache/ | 应用程序本身运作过程中会产生的一些暂存文件                   |
| /var/lib/   | 程序本身执行过程中，需要使用到的数据文件放置的目录。在此目录下各自的软件应该要有各自的目录。例如MySQL的数据库放置到/var/lib/mysql |
| /var/lock/  | 某些装置或者是文件资源一次只能被一个应用程序所使用，如果同时有两个程序使用该装置时，就可能产生一些错误的状况，因此就得要将该装置上锁，以确保该装置只会给单一软件所使用。 |
| /var/log/   | 重要到不行！这是登陆文件放置的目录！里面比较重要的文件如/var/log/message,/var/log/wtmp（记录登入者的信息）等 |
| /var/mail/  | 放置个人电子邮件信息的目录，不过这个目录也被放置到/var/spool/mail/目录中，通常这两个目录互为连接文件 |
| /var/run/   | 某些程序或者服务启动后，会将他们的PID放置到这个目录下。      |
| var/spool/  | 这个目录通常放置一些队列数据，所谓的队列就是排队等待其他程序使用的数据。这些数据被使用后通常都会被删除。 |

### 5.3.2 目录树

- 目录树的起始点位根目录
- 每个一个目录不止能使用本地端的partition的文件系统，也可以使用网络上的filesystem。举例来说，可以利用Network File System（NFS）服务器挂载某特定目录等
- 每一个文件在此目录树中的文件名（包含完整路径）都是独一无二的

# 6 Linux文件与目录管理

### 6.1.2 目录的相关操作

| 符号     | 描述                         |
| -------- | ---------------------------- |
| .        | 代表此目录                   |
| ..       | 代表上一层目录               |
| -        | 代表前一个工作目录           |
| ~        | 代表目前用户身份所在的家目录 |
| ~account | 代表account这个用户的家目录  |

常用的处理目录的命令：

- cd：变换目录
- pwd：显示当前目录的绝对路径
- mkdir：建立一个新的目录
- rmdir：删除一个**空的目录**

```bash
#-p表示可以建立多个目录
mkdir -p test1/test2
#-m 设置目录权限
mkdir -m 711 test2
#-p删除上层的空目录
rmdir -p test1/test2
```

### 6.1.3 关于执行文件路径的变来那个：$PATH

当我们在执行一个指令的时候，例如ls，系统会依照PATH的设定去每个PATH定义的目录下搜寻文件名为ls的可执行文件，如果在PATH定义的目录中含有多个文件名为ls的可执行文件，那么先搜寻到的同名指令先被执行！

```bash
echo $PATH
#在PATH添加路径,在原有的PATH之后再加一个/root到PATH中
export PATH="${PATH}:/root"
```

- 不同身份使用者预设的PATH不同，默认能够随意执行的指令也不同
- PATH是可以修改的
- 使用绝对路径或相对路径直接指定某个指令的文件名来执行，会比搜寻PATH来的正确
- 指令应该要放置到正确地目录下，执行才会比较方便
- 本目录最好不要放到PATH中

export PATH=...只对当前的shell有用关闭时候消失，编辑/etc/profile文件添加export PATH="$PATH:/NEW_PATH"，此方法是针对所有用户，个人电脑推荐。编辑~/.bashrc文件，添加export PATH="$PATH:/NEW_PATH"。

/etc/profile : 在登录时,操作系统定制用户环境时使用的第一个文件 ,此文件为系统的每个用户设置环境信息,当用户第一次登录时,该文件被执行。

/etc /environment : 在登录时操作系统使用的第二个文件, 系统在读取你自己的profile前,设置环境文件的环境变量。

~/.profile : 在登录时用到的第三个文件 是.profile文件,每个用户都可使用该文件输入专用于自己使用的shell信息,当用户登录时,该文件仅仅执行一次!默认情况下,他设置一些环境变量,执行用户的.bashrc文件。

/etc/bashrc : 为每一个运行bash shell的用户执行此文件.当bash shell被打开时,该文件被读取.

~/.bashrc : 该文件包含专用于你的bash shell的bash信息,当登录时以及每次打开新的shell时,该该文件被读取。

## 6.2 文件与目录管理

### 6.2.1 文件与目录的检视：ls

### 6.2.2 复制、删除与移动：cp,rm,mv

### 6.2.3 获取路径的文件名与目录名称

```bash
[root@bogon /]# basename /etc/sysconfig/network
network
[root@bogon /]# dirname /etc/sysconfig/network
/etc/sysconfig
```

## 6.3 文件内容查阅

- cat：由第一行开始显示内容

- tac：从最后一行开始显示

- nl：显示的时候，顺道输出行号

- more：一页一页的显示文件内容

- less：与more类似，可以往前翻页

  > less [options] file:
  >
  > options:
  >
  > 1. -e 当文件显示结束后，自动离开、
  > 2. -f强迫打开特殊文件，例如二进制文件
  > 3. -i忽略搜索时的大小写
  > 4. -m显示类似more命令的百分比
  > 5. `-o <filename>`将less输出的内容在制定文件中保存起来
  > 6. -Q不使用警告音
  > 7. -s显示连续空行为一行
  > 8. -S行过长时将超出部分舍弃
  > 9. /text 向下搜索text
  > 10. ?text 向上搜索text
  > 11. n 重复前一个搜索
  > 12. N 反向重复前一个搜索
  > 13. h 显示帮助界面
  >
  > 浏览多个文件`less file1 file2`，输入:n切换到下一个文件，:p切换到上一个文件
  >
  > less中可以使用的命令：
  >
  > 1. ctrl+F 向前移动一屏
  > 2. ctrl+B 向后移动一屏
  > 3. j 向前移动一行
  > 4. k 向后移动一行
  > 5. G 移动到最后一行
  > 6. g 移动到第一行
  > 7. F 实时跟踪文件的更新

- head：只看头几行

- tail：只看尾巴几行

- od：以二进制的方式读取文件内容

### 6.4.2 文件隐藏属性

chattr配置文件案隐藏属性

lsattr显示文件隐藏属性

### 6.4.3 文件特殊权限：SUID，SGID，SBIT

suid只适用于可执行文件，不能用于目录。设置suid以后，用户就可以使用拥有者的权限执行这个文件。

示例：

```bash
localhost :: ~ » ls -l /usr/bin/passwd
-rwsr-xr-x. 1 root root 34928 May 11  2019 /usr/bin/passwd

#设置和取消suid
chmod u+s filename
chmod u-s filename

chmod 4754 filename
chmod 0754 filename
```

sgid即适用于文件，也适用于目录。对于文件，sgid和suid的功能类似，只不过用户是以组的权限执行文件，而不是以拥有者的权限执行文件。

示例：

```bash
localhost :: ~ » ll | grep origin
-rw---S---. 1 root  root  2.1K Jun 12 05:38 original-ks.cfg

#设置和取消sgid
chmod g+s filename
chmod g-s filename

chmod 2754 filename
chmod 2754 filename
```



## 6.5 指令与文件的搜寻

### 6.5.1 脚本文件名的搜索

#### which：寻找可执行文件

此命令时区PATH包含的路径中寻找可执行的文件，which后面接的是完整的文件名，加上-a则可以列出所有的可以找到的同名执行文件，而非仅显示第一个。

#### 文件名的搜寻

一般先试用whereis或者是locate来检查，如果真的找不到了，才以find来搜寻。因为whereis只找系统中某些特定目录底下的文件而已，locate则是利用数据库来搜索文件名。

locate的使用更简单，直接在后面输入文件的部分名称后就能得到结果。但是此命令时通过数据库来寻找数据的/var/lib/mlocate/里面的数据。此数据库每天更新一次，可以设置，所以有可能找不到最新的文件。可以使用updatedb更新数据库。

#### whereis

```bash
whereis [-bmsu] 文件或目录名
选项与参数：
-l :可以列出whereis会去查询的几个主要目录而已
-b :只找binary格式的文件
-m :只找在说明文件manual路径下的文件
-s :只找source来源文件
-u :搜寻不在上述三个项目当中的其他特殊文件
```

#### locate

```
#更新数据库
sudo updatedb

locate [-ir] keyword
选项与参数：
-i :忽略大小写
-c :不输出文档名，仅计算找到的文件数量
-l :仅输出几行，例如-l 5,仅输出5行
-S :输出locate所使用的数据库文件的相关信息，包括该数据库记录的文件/目录数量等
-r :后面可接正则表达式的显示方式
```

#### find

语法`find path -option [-print] [-exec -ok command] {} \;`

功能最强大，但是速度很慢。

```bash
#将目前目录及其子目录下所有延伸档名是 c 的文件列出来
find . -name "*.c"
```

#### grep

用于搜索文件的内容。grep的处理过程基本上就是，为它提供一个搜索想要匹配的模式，指定要搜索的一个或一组文件（甚至是整个硬盘空间），然后grep就会输出能够与搜索模式匹配的各行内容的列表。

```bash
#检查一个文件中是否包含pain这个词
grep pain three_no_more_forever.txt
grep '^pain$' three_no_more_forever.txt
# 使用扩展正则，o表示只展示匹配到的内容
➜  ~ docker ps -a | grep -Eo "minions-spider-biquge[0-9]{0,}"
minions-spider-biquge5
minions-spider-biquge4
minions-spider-biquge3
minions-spider-biquge2
minions-spider-biquge1
minions-spider-biquge
```

| 支持的模式                         | grep命令选项               | 单独的命令 |
| ---------------------------------- | -------------------------- | ---------- |
| 基本的正则表达式                   | grep -G(--basic-regexp)    | grep       |
| 扩展的正则表达式                   | grep -E(--extended-regexp) | egrep      |
| 固定字符串的列表，匹配其中任何一项 | grep -F(--fixed-strings)   | fgrep      |
| Perl正则表达式                     | grep -P(--perl-regexp)     |            |

shell中单引号和双引号不同，单引号表示字符串，双引号则是让shell知道想要使用shell变量。如果要搜索精确的匹配结果，就使用单引号；如果要把shell变量结合到搜索内容中，就使用双引号；如果搜索关键字只包含数字和字母，完全不使用任何引号也没有问题。

# 8 文件与文件系统的压缩、打包与备份

## 8.2 Linux系统常见的压缩指令

| 后缀      | 程序                                 |
| --------- | ------------------------------------ |
| *.Z       | compress程序压缩的文件               |
| *.zip     | zip程序压缩的文件                    |
| *.gz      | gzip程序压缩的文件                   |
| *.bz2     | bzip2程序压缩的文件                  |
| *.xz      | xz程序压缩的文件                     |
| *.tar     | tar程序打包的数据，并没有压缩        |
| *.tar.gz  | tar程序打包的文件，并经过gzip的压缩  |
| *.tar.bz2 | tar程序打包的文件，并经过bzip2的压缩 |
| *.tar.xz  | tar程序打包的文件，并经过xz的压缩    |

### 8.2.1 gzip,zcat/zmore/zless/zgrep

gzip将文件压缩之后源文件就会被删除，只留下被压缩好的文件。zless这些命令时查看压缩文件的内容。gzip中的-c参数可以将原本要转成压缩文件的内容变成文字类型输出到屏幕上，并且可以通过>将输出文件存入执行的文件内，源文件仍然被保存。

### 8.2.2 bzip2,bzcat/bzmore/bzless/bzgrep

bzip2是为了替代gzip，提供了更佳的压缩比。功能与用法与gzip相似。

### 8.2.3 xz,xzcat/xzmore/xzless/xzgrep

压缩比更高，用法与上面两个几乎一致。

## 8.2.4 zip

```bash
#压缩文件，将当前目录下的所有文件和文件夹全部压缩成myfile.zip文件,－r表示递归压缩子目录下所有文件
zip -r myfile.zip ./*
#解压，把myfile.zip文件解压到 /home/sunny/，-o:不提示的情况下覆盖文件；-d:-d /home/sunny 指明将文件解压缩到/home/sunny目录下；
unzip -o -d /home/sunny myfile.zip
#删除，删除压缩文件中smart.txt文件
zip -d myfile.zip smart.tx
#添加文件，向压缩文件中myfile.zip中添加rpm_info.txt文件
zip -m myfile.zip ./rpm_info.txt
```

## 8.3 打包指令：tar

### 8.3.1 常见参数说明

说明： -c/-x/-t/u 不可同时出现

| 参数            | 参数说明                                                     |
| --------------- | ------------------------------------------------------------ |
| -c              | 新建打包文件，同 -v 一起使用 查看过程中打包文件名            |
| -x              | 解压文件， -C 解压到对应的文件目录。                         |
| -f              | 后面接要处理的文件                                           |
| -j              | 通过bzip2方式压缩或解压，最后以.tar.bz2 为后缀。压缩后大小小于.tar.gz |
| -J              | 通过xz方式压缩或解压，最后以.tar.xz为后缀                    |
| -z              | 通过gzip方式压缩或解压，最后以.tar.gz 为后缀                 |
| -v              | 压缩或解压过程中，显示出来过程                               |
| -t              | 查看打包文件中内容，重点文件名                               |
| -u              | 更新压缩文件中的内容。                                       |
| -p              | 保留绝对路径，即允许备份数据中含有根目录                     |
| -P              | 保留数据原来权限及属性。                                     |
| --exclude =FILE | 压缩过程中，不要将FILE打包                                   |
| man tar         | 查看更多参数                                                 |

压缩：tar -jvc -f filename.tar.bz2 <要被压缩的文件或目录名称>

查询：tar -jtv -f filename.tar.bz2

解压缩：tar -jvx -f filename.tar.bz2 -C <欲解压的目录>

```bash
[root@localhost Docker]# tar -jvc -f config.tar.bz2 ./config/
./config/
./config/config-1.0-SNAPSHOT.jar
./config/Dockerfile
./config/test

[root@localhost Docker]# tar -jtv -f config.tar.bz2 
drwxr-xr-x root/root         0 2019-12-07 08:25 ./config/
-rwxrw-rw- root/root  50614232 2019-05-27 09:12 ./config/config-1.0-SNAPSHOT.jar
-rwxrw-rw- root/root       329 2019-05-23 10:39 ./config/Dockerfile
-rwxr--r-- root/root       329 2019-12-07 08:12 ./config/test

[root@localhost Docker]# tar -jxv -f config.tar.bz2 -C config1
./config/
./config/config-1.0-SNAPSHOT.jar
./config/Dockerfile
./config/test

#此命令只解压文件中的一个文件
[root@localhost Docker]# tar -jxv -f config.tar.bz2 config/test
config/test

#排除t开头的文件，最前面加上time表示显示时间
[root@localhost Docker]# time tar -jvc -f config.tar.bz2 config --exclude=config/t*
config/
config/config-1.0-SNAPSHOT.jar
config/Dockerfile

real	0m6.708s
user	0m5.522s
sys	0m0.094s

```

仅备份比某个时刻还要新的文件：--newer,--newer-mtime。

```bash
# 只归档不压缩
tar -cf file.tar filepath
tar -xf file.tar -C filepath
```

### 并发打包pigz

```bash
# 压缩文件：-9压缩级别，-p使用线程数，-k压缩后保留源文件，压缩后生成filename.gz
pigz -9 -p 16 -k filename
# 解压文件，两种方式
gzip -d filename.gz
pigz -d filename.gz

# 压缩目录
tar cv filename | pigz -9 -p 16 -k > filename.tar.gz
# 解压目录，两种方式
tar -zvxf filename.tar.gz
pigz -d filename.tar.gz
```

## 8.4 XFS文件系统的备份与还原

### 8.4.1 XFS文件系统备份xfsdump

限制：

- 不支持没有挂载的文件系统备份，所以只能备份已挂载的
- 必须使用root的权限才能操作
- 只能备份XFS文件系统
- 备份下来的数据只能让xfsrestore解析
- 通过UUID来分辨各个备份档的，因此不能备份两个具有相同UUID的文件系统

## 8.5 光盘写入工具

### 8.5.1 mkisofs：建立映像档

### 8.5.2 cdrecord：光盘刻录工具

# 9 vim程序编辑器

## 9.2 vi的使用

vi共分为三种模式分别是：一般指令模式、编辑模式、指令列命令模式。、

- 一般指令模式（command mode）：vi打开一个文件默认的就是这个模式。在这个模式中，你可以使用上下左右案件来移动光标，可以使用删除字符或删除整列来处理文件内容，也可以使用复制、粘贴来处理你的文件数据
- 编辑模式（insert mode）：可以按下i,I,o,O,a,A,r,R等人一个字母之后进入编辑模式。此时你可以编辑文件内容，按Esc退出编辑模式。
- 指令列命令模式（command-line mode）：输入**:,/,?**三个中的任何一个按钮，就可进入此模式。

### vi常用快捷键

```
vi常用快捷键
vi常用快捷键
x 删除光标处的字符
 
dd 删除整行
 
i 在光标前插入文本
 
a 在光标后插入文本
 
o 当前行下插入新行
 
u 撤销最后一次修改
 
:e! 放弃所有修改，从上次保存开始处再编辑
 
:wq 保存退出
 
:q! 不保存退出
 
/pattern：从光标开始处向文件尾搜索pattern
?pattern：从光标开始处向文件首搜索pattern
 
vi常用快捷键
光标控制命令
命令    光标移动
h或^h    向左移一个字符
j或^j或^n   向下移一行
k或^p    向上移一行
l或空格    向右移一个字符
G    移到文件的最后一行
nG    移到文件的第n行
w    移到下一个字的开头
W    移到下一个字的开头，忽略标点符号
b    移到前一个字的开头
B    移到前一个字的开头，忽略标点符号
L    移到屏幕的最后一行
M    移到屏幕的中间一行
H    移到屏幕的第一行
e    移到下一个字的结尾
E    移到下一个字的结尾，忽略标点符号
(    移到句子的开头
)    移到句子的结尾
{    移到段落的开头
}    移到下一个段落的开头
0或|    移到当前行的第一列
n|    移到当前行的第n列
^    移到当前行的第一个非空字符
$    移到当前行的最后一个字符
+或return   移到下一行的第一个字符
-    移到前一行的第一个非空字符
 
在vi中添加文本
命令    插入动作
a    在光标后插入文本
A    在当前行插入文本
i    在光标前插入文本
I    在当前行前插入文本
o    在当前行的下边插入新行
O    在当前行的上边插入新行
:r file    读入文件file内容，并插在当前行后
:nr file   读入文件file内容，并插在第n行后
escape    回到命令模式
^v char    插入时忽略char的指定意义，这是为了插入特殊字符
 
在vi中删除文本
命令    删除操作
x    删除光标处的字符，可以在x前加上需要删除的字符数目
nx    从当前光标处往后删除n个字符
X    删除光标前的字符，可以在X前加上需要删除的字符数目
nX    从当前光标处往前删除n个字符
dw    删至下一个字的开头
ndw    从当前光标处往后删除n个字
dG    删除行，直到文件结束
dd    删除整行
ndd    从当前行开始往后删除
db    删除光标前面的字
ndb    从当前行开始往前删除n字
:n,md    从第m行开始往前删除n行
d或d$    从光标处删除到行尾
dcursor_command   删除至光标命令处，如dG将从当产胆行删除至文件的末尾
^h或backspace   插入时，删除前面的字符
^w    插入时，删除前面的字
 
修改vi文本
每个命令前面的数字表示该命令重复的次数
命令    替换操作
rchar    用char替换当前字符
R text escape   用text替换当前字符直到换下Esc键
stext escape   用text代替当前字符
S或cctext escape 用text代替整行
cwtext escape   将当前字改为text
Ctext escape   将当前行余下的改为text
cG escape   修改至文件的末尾
ccursor_cmd text escape 从当前位置处到光标命令位置处都改为text
 
在vi中查找与替换
命令    查找与替换操作
/text    在文件中向前查找text
?text    在文件中向后查找text
n    在同一方向重复查找
N    在相反方向重复查找
ftext    在当前行向前查找text
Ftext    在当前行向后查找text
ttext    在当前行向前查找text，并将光标定位在text的第一个字符
Ttext    在当前行向后查找text，并将光标定位在text的第一个字符
:set ic    查找时忽略大小写
:set noic   查找时对大小写敏感
:s/oldtext/newtext 用newtext替换oldtext
:m,ns/oldtext/newtext 在m行通过n，用newtext替换oldtext
&    重复最后的:s命令
:g/text1/s/text2/text3 查找包含text1的行，用text3替换text2
:g/text/command   在所有包含text的行运行command所表示的命令
:v/text/command   在所有不包含text的行运行command所表示的命令
 
在vi中复制文本
命令    复制操作
yy    将当前行的内容放入临时缓冲区
nyy    将n行的内容放入临时缓冲区
p    将临时缓冲区中的文本放入光标后
P    将临时缓冲区中的文本放入光标前
"(a-z)nyy   复制n行放入名字为圆括号内的可命名缓冲区，省略n表示当前行
"(a-z)ndd   删除n行放入名字为圆括号内的可命名缓冲区，省略n表示当前行
"(a-z)p    将名字为圆括号的可命名缓冲区的内容放入当前行后
"(a-z)P    将名字为圆括号的可命名缓冲区的内容放入当前行前
 
在vi中撤消与重复
命令    撤消操作
u    撤消最后一次修改
U    撤消当前行的所有修改
.    重复最后一次修改
,    以相反的方向重复前面的f、F、t或T查找命令
;    重复前面的f、F、t或T查找命令
"np    取回最后第n次的删除(缓冲区中存有一定次数的删除内容，一般为9)
n    重复前面的/或?查找命令
N    以相反方向重复前面的/或?命令
 
保存文本和退出vi
命令    保存和/或退出操作
:w    保存文件但不退出vi
:w file    将修改保存在file中但不退出vi
:wq或ZZ或:x   保存文件并退出vi
:q!    不保存文件，退出vi
:e!    放弃所有修改，从上次保存文件开始再编辑
 
vi中的选项
选项    作用
:set all   打印所有选项
:set nooption   关闭option选项
:set nu    每行前打印行号
:set showmode   显示是输入模式还是替换模式
:set noic   查找时忽略大小写
:set list   显示制表符(^I)和行尾符号
:set ts=8   为文本输入设置tab stops
:set window=n   设置文本窗口显示n行
 
vi的状态
选项    作用
:.=    打印当前行的行号
:=    打印文件中的行数
^g    显示文件名、当前的行号、文件的总行数和文件位置的百分比
:l    使用字母"l"来显示许多的特殊字符，如制表符和换行符
 
在文本中定位段落和放置标记
选项    作用
{    在第一列插入{来定义一个段落
[[    回到段落的开头处
]]    向前移到下一个段落的开头处
m(a-z)    用一个字母来标记当前位置，如用mz表示标记z
'(a-z)    将光标移动到指定的标记，如用'z表示移动到z
 
在vi中连接行
选项    作用
J    将下一行连接到当前行的末尾
nJ    连接后面n行
 
光标放置与屏幕调整
选项    作用
H    将光标移动到屏幕的顶行
nH    将光标移动到屏幕顶行下的第n行
M    将光标移动到屏幕的中间
L    将光标移动到屏幕的底行
nL    将光标移动到屏幕底行上的第n行
^e(ctrl+e)   将屏幕上滚一行
^y    将屏幕下滚一行
^u    将屏幕上滚半页
^d    将屏幕下滚半页
^b    将屏幕上滚一页
^f    将屏幕下滚一页
^l    重绘屏幕
z-return   将当前行置为屏幕的顶行
nz-return   将当前行下的第n行置为屏幕的顶行
z.    将当前行置为屏幕的中央
nz.    将当前行上的第n行置为屏幕的中央
z-    将当前行置为屏幕的底行
nz-    将当前行上的第n行置为屏幕的底行
 
vi中的shell转义命令
选项    作用
:!command   执行shell的command命令，如:!ls
:!!    执行前一个shell命令
:r!command   读取command命令的输入并插入，如:r!ls会先执行ls，然后读入内容
:w!command   将当前已编辑文件作为command命令的标准输入并执行command命令，如:w!grep all
:cd directory   将当前工作目录更改为directory所表示的目录
:sh    将启动一个子shell，使用^d(ctrl+d)返回vi
:so file   在shell程序file中读入和执行命令
 
vi中的宏与缩写
(避免使用控制键和符号，不要使用字符K、V、g、q、v、*、=和功能键)
选项    作用
:map key command_seq 定义一个键来运行command_seq，如:map e ea，无论什么时候都可以e移到一个字的末尾来追加文本
:map    在状态行显示所有已定义的宏
:umap key   删除该键的宏
:ab string1 string2 定义一个缩写，使得当插入string1时，用string2替换string1。当要插入文本时，键入string1然后按Esc键，系统就插入了string2
:ab    显示所有缩写
:una string   取消string的缩写
 
在vi中缩进文本
选项    作用
^i(ctrl+i)或tab   插入文本时，插入移动的宽度，移动宽度是事先定义好的
:set ai    打开自动缩进
:set sw=n   将移动宽度设置为n个字符
n<<    使n行都向左移动一个宽度
n>>    使n行都向右移动一个宽度，例如3>>就将接下来的三行每行都向右移动一个移动宽度
```

# 10 认识与学习bash

# 13 账户权限

## linux的帐号与群组

### UID与GID

登录linux的时候输入用户名，但是linux只认识用户的ID（UID）即一组数字。ID和帐号保存在`/etc/passwd`中，还有群组ID（Group ID，GID）保存在`/etc/group`中。

```bash
#查看用户信息
➜  /etc id korov  
uid=1000(korov) gid=1000(korov) groups=1000(korov)
```

### 使用者帐号

在以命令行登录系统的时候计算机会提供tty1~tty6的终端机提供的login接口（alt + F1~F6 进行终端机切换），我们输入帐号密码之后，系统帮我们做了什么？

1. 查看`/etc/passwd`中是否有你输入的帐号，如果没有则跳出，有的话将该帐号的UID与GID读出来，另外，将该帐号的家目录与shell设定也一并读出来
2. 进入`/etc/shadow`里面找出对应的帐号与UID，然后核对输入的帐号和密码是否相符。

#### `/etc/passwd`文件结构

每一行代表一个帐号，里面有很多帐号是系统正常运行所必要的，简称为系统帐号（bin,daemon等），不可以随便删掉他们。

```bash
less /etc/passwd
root:x:0:0:root:/root:/bin/zsh
bin:x:1:1:bin:/bin:/sbin/nologin
daemon:x:2:2:daemon:/sbin:/sbin/nologin
```

每行数据中以`:`作为分隔符，一共有七个数据：

1. 帐号名称

2. 密码：早期Unix系统的密码就是放在这个字段上，但是因为这个文件的特性时所有的程序都能够读取，很容易造成密码泄露，后来将这个密码保存到`/etc/shadow`中了。所以这里只保存了一个`x`

3. UID，通常linux对于UID有几个限制

   >| id范围                    | 该ID使用者特征                                               |
   >| ------------------------- | ------------------------------------------------------------ |
   >| 0（系统管理员）           | 当UID为0表示此帐号是系统管理员，如果你将其他帐号的UID改为0,则这个帐号也具有root权限。一个系统中的系统管理员不仅仅只有root |
   >| 1～999（系统帐号）        | 其中1～200是有distributions自行建立的帐号，201～999是当用户有系统帐号需求时可以使用的帐号UID |
   >| 1000～60000（可登入帐号） | 一般用户使用的UID                                            |

4. GID

5. 用户信息说明栏：无重要用途，用来做这个帐号的备注信息

6. 家目录

7. shell：用户登录系统时取得的shell

#### `/etc/shadow`文件结构

```bash
less /etc/shadow
root:$1$EZ.oNjh4$4jqcb0KigHKGcYYrie2.Y0::0:99999:7:::
bin:*:18078:0:99999:7:::
daemon:*:18078:0:99999:7:::
adm:*:18078:0:99999:7:::
```

以`:`作为分隔符，有九个字段：

1. 帐号名称
2. 密码：经过加密的密码
3. 最近更改密码的日期
4. 密码不可被更改的天数：表示这个帐号的密码在最近一次被更改后需要经过几天才能再被更改
5. 密码有效时间：强制用户在上次更改密码之后多长时间必须再次更改密码
6. 密码需要变更期限前的警告天数：当密码快要到期系统会更具这个字段的值发出警告，例如上面的root是密码快要到期之前7天就给用户发警告信息
7. 密码到期后的帐号宽限时间：密码到期之后还可以继续用的时间，如果这个时间也超过了，登录系统的时候系统会强制你必须重置密码
8. 帐号失效日期：帐号在这个日期之后无论密码是否到期，这个帐号都不能再使用
9. 保留：保留字段，暂时没有定义

#### `/etc/group`文件结构

```bash
less /etc/group
root:x:0:
bin:x:1:
daemon:x:2:
sys:x:3:
```

1. 组名
2. 群组密码：通常是给群组管理员使用，保存在`/etc/gshadow`中
3. GID
4. 此群组支持的帐号名称：如果某个帐号想要加入此群组，将该帐号填入这个字段即可

有效群组和初始群组：初始群组（GID）是当用户一登录系统就立刻拥有这个群组的相关权限。有效群组可以使用`groups`命令查看，输出的第一个群组就是有效群组，此时创建一个文件的话，则此文件为有效群组的。使用`newgrp groupname`命令可以切换有效群组。

## 帐号管理

useradd添加用户，passwd设置账户密码，usermod修改账户的某些设置，chage更详细的密码参数显示功能。userdel删除用户

```bash
useradd [-u UID] [-g 初始群组] [-G 次要群组] [-mM] [-c 说明栏] [-d 家目录绝对路径] [-s shell] 使用者帐号名
```

选项与参数：

- -u :后面接的是UID，一组数字。直接指定一个特定的UID给这个帐号
- -g :此群组的GID会被放到`/etc/passed`的第四个字段内
- -G :后面接的组名则是这个帐号还可以加入的群组。这个选项会修改`/etc/group`内的相关资料
- -M :强制不要建立用户家目录（系统帐号默认值）
- -m :强制要建立用户家目录（一般帐号默认值）
- -c :`/etc/passwd`第五栏的说明内容，可以随便设定
- -d :指定这个目录成为家目录。务必使用绝对路径
- -r :建立一个系统帐号
- -s :后面接一个shell，若没有指定则预设时`/bin/bash`
- -e :后面接一个日期，格式为[YYYY-MM-DD]此项可写入shadow第八字段，即帐号失效日期
- -f :后面接shadow的第七字段项目，制定密码是否会失效。0立刻失效，-1永不失效

groupadd新增群组，groupmod修改群组属性，groupdel删除群组，gpassed群组管理员

# 磁盘配额（Quota）的应用与实做

## Quota用途

针对服务器：

- 针对WWW server，例如：每个人的网页的空间容量限制
- 针对mail server,例如：每个人的邮件空间限制
- 针对file server，例如：每个人最大的可用网络硬盘空间

针对linux系统主机

- 限制某一群组所能使用的最大磁盘配额
- 限制某一用户的最大磁盘配额
- 限制某一目录（directory，project）的最大磁盘配额

限制：

- 在EXT文件系统家族仅能针对整个filesystem：EXT 文件系统家族在进行 quota 限制的时候,它仅能针对整个文件系统来进行设计,无法针对某个单一的目录来设计它的磁盘配额。 因此,如果你想要使用不同的文件系统进行 quota 时,请先搞清楚该文件系统支持的情况喔!因为 XFS 已经可以使用 project 模式来设计不同目录的磁盘配额。
- 核心必须支持quota：Linux 核心必须有支持 quota 这个功能才行:如果你是使用 CentOS 7.x 的预设核心, 嘿嘿!那恭喜你了,你的系统已经默认有支持 quota 这个功能啰!如果你是自行编译核心的, 那么请特别留意你是否已经『真的』开启了 quota 这个功能?否则底下的功夫将全部都视为『白工』。
- 只对一般身份使用者有效：
- 若启用SELinux，非所有目录均可设定quota

# 命令手册

## 命令的基本概念

### 通配符

有3种通配符：`*`(匹配0个或多个任意字符),`?`（匹配单个字符）,`[]`匹配一组单个字符例如`[12],[1-4]`,

## 组合命令

### 连续运行多个命令

多个命令使用`;`分割，shell会依次顺序执行每个命令，只有一个命令结束运行（无论成功或失败），才会运行下一个命令

多个命令使用`&&`分割，依次顺序执行每个命令，只有前面的命令运行成功，才运行下一个命令

多个命令使用`||`分割，只有前面的命令失败了（命令返回的状态码不为0）才会运行下一个命令

将一个命令插入到另一个命令中`$()`，命令替换是接受一个命令的输出，并将它插入到另一个命令中。

### 理解输入输出流

linux shell的三种输入输出流：标准输入流（standard input）、标准输出流（standard output）和标准错误流（standard error）。每种输入输出流都有一个文件描述符（或数字标识符）、一个常用的缩写名和一个可用的默认设备。

| 文件描述符 | 名称       | 常用缩写名 | 典型的默认设备 |
| ---------- | ---------- | ---------- | -------------- |
| 0          | 标准输入流 | stdin      | 键盘           |
| 1          | 标准输出流 | stdout     | 终端           |
| 2          | 标准错误流 | stderr     | 终端           |

### 管道符

`|`当把他放到两个命令之间时，他可以将第一个命令的输出作为第二个命令的输入，也就是`|`将第一个命令的stdout重定向到第二个命令的stdin。

## 监视系统资源

### 查看当前正在运行的所有进程

```bash
# a：表示所有用户，u：以面向用户的格式显示或显示拥有每个进程的用户，x：没有控制tty或终端屏幕的进程
ps aux
```

显示结果中有一列STAT表示进程状态：R（运行 running），S（休眠sleeping），T（停止stopped），Z（僵尸zombie）

Z意味着进程基本上一直处于挂起状态，不能停止，这时或许只有重启计算机才能完全关闭这个程序。

### 查看进程树

```bash
ps axjf
```

在linux世界中，进程并不是凭空出现的。通常，启动一个程序的同时，也会启动其他程序，例如linux系统中的所有进程都来自init，他是所有进程的源头，其pid为1.

PPID列表示此PID的父PID，有了PID或PPID，就能够终止任何失控的进程。

### 查看特定用户拥有的进程

```bash
ps U [username]
```

### 终止正在运行的进程

```bash
kill
```

kill命令能够向进程发出多种信号，例如“请关闭进程，同时清理占用的东西”、“请尽可能快的关闭进程”以及“马上关闭”。

| 信号编号 | 信号单词            | 含义                                                         |
| -------- | ------------------- | ------------------------------------------------------------ |
| -1       | -HUP：hang up，挂起 | 控制进程已经死了，关闭进程（如果用于系统服务，会导致重新加载配置文件并重启） |
| -15      | -TERM               | 正常中止进程，清除将要失控的进程和文件                       |
| -9       | -KILL               | 停止任何正在进行的处理，马上关闭                             |

通常，应该先试试-15（默认值），这样程序就可以关闭依赖于它的任何其他程序，关闭由它打开的文件，等等。在耐心等待了一会后，进程仍然在完全不受控制的运行，或者仍然没有响应，这时就可以使出更大的杀手锏，使用-9选项，-9相当于强制中断一个正在运行的进程即使这个进程还在处理他的作业，从而可能在系统中遗留下失去控制的临时文件或进程，所以这绝对不是个好想法。

### 查看正在运行的进程的动态更新列表

```bash
top
```

每秒刷新系统资源信息，在top界面输入k会出现对话窗口输入对应的pid可以kill对应的pid

推荐使用htop

#### top常用命令交互命令

1. `c`:是否显示进程的绝对路径
2. `d`或`s`:改变top界面的刷新频率
3. `l`,`t`,`m`:切换负载，任务，内存的显示模式
4. 回车或空格：手动刷新top的界面
5. `A`:切换交替显示模式，交替模式下会显示4个窗口，分别关注不同的字段（1.Def，默认字段组，2.Job，任务字段组，3.Mem，内存字段组，4.User，用户字段组），在交替模式下`a`移动到后一个窗口，`w`移动到前一个窗口，`g`输入一个数字选择指定窗口
6. `f`：选择想要显示的字段，空格选中和取消
7. `R`:切换反向排序
8. `i`:显示空闲任务
9. `V`:树视图
10. `x`或者`y`:`x`排序字段高亮显示，`y`将运行进程高亮显示
11. `u`:显示特定用户的进程
12. `n`或`#`：设置最大显示的任务数量
13. `k`：杀死一个任务
14. `<>`:`<`向左移动一列排序，`>`向右移动一列排序

#### htop命令详解



### 列出打开的文件

```bash
lsof
#列出某个用户打开的文件
lsof -u
#列出正在使用的特定文件的用户
lsof [file]
#列出特定程序的进程
lsof -c [program]
```

### 显示系统RAM的信息

```bash
[korov@korov-pc ~]$ free -h
              总计         已用        空闲      共享    缓冲/缓存    可用
内存：        14Gi       2.0Gi       9.7Gi       176Mi       2.8Gi        12Gi
交换：          0B          0B          0B
```

buffers/cache占用的较多，说明系统中有进程曾经读写过文件，但是不要紧，这部分内存是当空闲来用的。

cache中的数据跟对应文件中的数据一致，才能对cache进行释放。所以伴随着cache清除的行为，一般都是系统IO高。因为内核要对比cache中的数据和对应硬盘文件上的数据是否一致，如果不一致需要写回，之后才能回收。

buffer：如写磁盘时，先保存到磁盘缓冲区buffer，然后写入到磁盘

cache：即读磁盘时，数据从磁盘读出后，暂留在缓冲区cache，为后续程序的使用做准备。

### 显示文件系统的磁盘使用情况

```bash
df
df -hT
```

### 报告目录使用的文件空间

```bash
localhost :: ~ » du -h ./eggs 
16K	./eggs/minions_spider
16K	./eggs
chmo
#只报告目录使用的总空间
du -s
```

## 网络

### 查看网络接口状态

```bash
ifconfig
```

这个命令来自net-tools，但是这个工具已经停止维护，推荐使用`ip(ip a)`命令

### 跟踪数据包在两台主机之间经过的路由

```bash
localhost :: ~ » traceroute www.granneman.com
traceroute to www.granneman.com (23.239.25.194), 30 hops max, 60 byte packets
 1  _gateway (172.16.193.2)  1.187 ms  1.018 ms  0.945 ms
 2  Hiwifi.lan (192.168.199.1)  3.095 ms  5.343 ms  5.279 ms
 3  192.168.1.1 (192.168.1.1)  5.193 ms  5.259 ms  5.201 ms
 4  172.51.0.1 (172.51.0.1)  11.103 ms  11.018 ms  10.952 ms
 5  183.233.80.205 (183.233.80.205)  11.961 ms  12.282 ms  12.224 ms
 6  * * *
 7  111.24.5.213 (111.24.5.213)  10.137 ms 111.24.14.85 (111.24.14.85)  10.573 ms *
 8  111.24.5.166 (111.24.5.166)  11.725 ms 111.24.5.194 (111.24.5.194)  11.646 ms 111.24.5.178 (111.24.5.178)  12.806 ms
 9  221.176.22.158 (221.176.22.158)  19.908 ms 221.183.68.145 (221.183.68.145)  10.696 ms 221.176.22.158 (221.176.22.158)  16.632 ms
10  221.176.20.6 (221.176.20.6)  16.795 ms 221.183.25.121 (221.183.25.121)  11.740 ms 221.176.19.198 (221.176.19.198)  18.441 ms
11  221.183.55.53 (221.183.55.53)  16.381 ms  16.288 ms 221.183.55.81 (221.183.55.81)  16.421 ms
12  223.120.12.29 (223.120.12.29)  216.342 ms 223.120.12.105 (223.120.12.105)  269.729 ms 223.120.13.177 (223.120.13.177)  215.578 ms
13  223.120.6.70 (223.120.6.70)  213.750 ms  212.969 ms  211.621 ms
14  38.88.224.161 (38.88.224.161)  281.597 ms  280.857 ms 223.120.6.218 (223.120.6.218)  179.867 ms
15  be3669.ccr21.sfo01.atlas.cogentco.com (154.54.43.9)  278.401 ms be3670.ccr22.sfo01.atlas.cogentco.com (154.54.43.13)  289.525 ms  287.357 ms
16  be3359.ccr42.lax01.atlas.cogentco.com (154.54.3.69)  280.737 ms be3110.ccr32.slc01.atlas.cogentco.com (154.54.44.142)  281.520 ms *
17  be2932.ccr32.phx01.atlas.cogentco.com (154.54.45.161)  285.844 ms be3038.ccr22.den01.atlas.cogentco.com (154.54.42.98)  279.982 ms be3037.ccr21.den01.atlas.cogentco.com (154.54.41.146)  281.072 ms
18  be3035.ccr21.mci01.atlas.cogentco.com (154.54.5.90)  279.789 ms be3036.ccr22.mci01.atlas.cogentco.com (154.54.31.90)  279.722 ms  304.077 ms
19  be2927.ccr41.iah01.atlas.cogentco.com (154.54.29.221)  282.272 ms be2928.ccr42.iah01.atlas.cogentco.com (154.54.30.161)  277.562 ms be2432.ccr31.dfw01.atlas.cogentco.com (154.54.3.133)  290.822 ms
20  be2441.ccr31.dfw01.atlas.cogentco.com (154.54.41.66)  275.293 ms be2763.ccr41.dfw03.atlas.cogentco.com (154.54.28.74)  303.401 ms  305.464 ms
21  204.68.252.98 (204.68.252.98)  242.540 ms be2763.ccr41.dfw03.atlas.cogentco.com (154.54.28.74)  284.055 ms be2764.ccr41.dfw03.atlas.cogentco.com (154.54.47.214)  278.031 ms
22  45.79.12.3 (45.79.12.3)  249.274 ms 204.68.252.98 (204.68.252.98)  234.995 ms 45.79.12.3 (45.79.12.3)  250.505 ms
23  * * *
24  * * *
25  * * *
26  * * *
27  * * *
28  * * *
29  * * *
30  * * *
```

`* * *`表示在那一跳（hop）发生了一次5秒的超时。一连串的星号则说明从`45.79.12.3 (45.79.12.3)`开始，数据包转发到的那些路由器在某些地方除了问题。

## 使用网络

### 在计算机之间安全的传输文件

```bash
sftp root@172.16.193.141
```

| 命令 | 含义                                    |
| ---- | --------------------------------------- |
| cd   | 切换目录                                |
| exit | 关闭与远程ssh服务器的连接               |
| get  | 将指定的文件复制到本机                  |
| help | 获取与命令相关的帮助                    |
| lcd  | 将目录切换到本机                        |
| lls  | 列出本机上的文件                        |
| ls   | 列出远程ssh服务器上当前工作目录中的文件 |
| put  | 将指定的文件复制到远程ssh服务器         |
| rm   | 将指定的文件从远程ssh服务器删除         |

### 安全的传输和备份

```bash
rsync -v

rsync --verbose --progress --stats –recursive --times --perms --links --compress
 --rsh=ssh --delete /home/sam/documents/ will@wordsworth:/media/backup/documents

rsync -vrtplze ssh --progress --stats –delete /home/sam/documents/will@wordsworth:/media/backup/documents
```

rsync将一台主机中的文件备份到另外一台主机，但是传输数据的时候只会将数据中发生过变化的文件进行传输，大大减少数据的传输量。

示例命令解读：rsync命令先构建一个它必须要考虑的所有文件的列表(在这个例子中是107 805个文件),接着删除任何在目标位置( wordsworth)已经存在,但在源位置(coleridge)已经不存在的文件。删除完文件以后,rsync就开始复制所有发生过变化的文件,或者如果是同一个文件,但文件的部分内容有变化,rsync会只复制这个文件中发生变化的部分。在列出进行的变化之后, rsync会给出一些有关传输的总体信息。总共传输了120个文件,6702042249 字节(大约6.4 GB)中传输了15337159字节(大约14 MB)。在统计中还包含一些其他数据,但以上的信息是关键数据。

# 自我总结

## 2 切换java版本

```bash
#ubuntu中使用
sudo update-alternatives --config java
#arch系中使用
$ archlinux-java status
Available Java environments:
  java-11-openjdk
  java-8-openjdk/jre (default)
$ sudo archlinux-java set java-11-openjdk
```

## 3 查看端口占用

```bash
netstat -a | grep 8080
kill -9 pid
```

## 4 linux开放1024以下的端口号


```
# 例如开启80端口，可以将80端口转发到8080端口
tables -t nat -A PREROUTING -p tcp –dport 80 -j REDIRECT –to-ports 8080
iptables -t nat -A OUTPUT -p tcp -d 127.0.0.1 –dport 80 -j REDIRECT –to-ports 8080
#删除，重启之后此设置也会失效
iptables -t nat -F PREROUTING
iptables -t nat -F OUTPUT
```

## 6 linux中创建桌面图标

在/usr/share/applications中创建后缀为.desktop的文件,内容为下

```
[Desktop Entry]                                 #标签开始，说明这是一个Desktop Entry文件,每个.desktop文件都以这个.
Version = 1.0                                    #标明Desktop Entry的版本（可选）
Name=firefox                                   #程序名称
Name[en]=Firefox                            #不同语言的应用名称（可选）
Name[en_US]=Firefox                     #不同语言的应用名称（可选）
Encoding=UTF-8                             #编码方式（可选）
Comment=Firefox                            #程序描述
Exec=/opt/firefox/firefox                  #程序的启动命令，可以带参数运行
Icon=/opt/firefox/browser/icons/mozicon128.png                          #快捷方式图标绝对路径（可选）
Terminal=false                                  #是否在终端中运行，数值是布尔值（true 或是 false）（可选）
Categories=Application;Network;                                         #注明在菜单栏中显示的位置（可选）
Type=Application
#desktop的类型（必选），常见值有“Application”和“Link”，Application： 对于启动应用程序的菜单项，应输入此选项。Link: 对于链接到文件、文件夹或 FTP 站点的菜单项，应输入此选项。
```

## 8 创建link

例如在当前目录下创建一个链接`/opt/gradle/bin/gradle`的文件

```bash
ln -s /opt/gradle/bin/gradle ./gradle
```

## 9 服务器上传文件以及下载文件

```bash
# download file from server to local
scp username@hostname:/path/file /path/file
# upload file from local to server
scp /path/file username@hostname:/path/file
# copy file from one server to another
scp username@hostname:/path/file username@hostname:/path/file
```

## 关闭图形界面

```bash
#查看当前设置,multi-user.target 开机已命令模式启动，graphical.target 开机以图形界面启动
systemctl get-default
systemctl set-default multi-user.target
```

## 配置ssh登录

修改ssh配置文件`vim /etc/ssh/sshd_config`

```properties
PermitRootLogin yes
RSAAuthentication yes
PubkeyAuthentication yes
AuthorizedKeysFile      .ssh/authorized_keys
```

上传客户端的ssh公钥到服务器上：

```bash
scp ~/.ssh/id_rsa.pub root@172.16.193.141:/root/.ssh/id_rsa.pub_root
#在服务器上执行如下命令
cat /root/.ssh/id_rsa.pub_root >> /root/.ssh/authorized_keys
```

## vmware无法加载内核

```bash
sudo modprobe -a vmw_vmci vmmon
```

## 蓝牙失效

```bash
pulseaudio -k
```

## 查看进程io

```bash
#安装sysstat，查看磁盘的io
iostat
#安装iotop，可以查看进程的io
iotop
```

## sed

语法：`sed [-hnV][-e<script>][-f<script文件>][文本文件]`

选项：

```
-e<script>或--expression=<script>：以选项中的指定的script来处理输入的文本文件；
-f<script文件>或--file=<script文件>：以选项中指定的script文件来处理输入的文本文件；
-h或--help：显示帮助；
-n或--quiet或——silent：仅显示script处理后的结果；
-V或--version：显示版本信息。
```

sed命令

```
a\ 在当前行下面插入文本。
i\ 在当前行上面插入文本。
c\ 把选定的行改为新的文本。
d 删除，删除选择的行。
D 删除模板块的第一行。
s 替换指定字符
h 拷贝模板块的内容到内存中的缓冲区。
H 追加模板块的内容到内存中的缓冲区。
g 获得内存缓冲区的内容，并替代当前模板块中的文本。
G 获得内存缓冲区的内容，并追加到当前模板块文本的后面。
l 列表不能打印字符的清单。
n 读取下一个输入行，用下一个命令处理新的行而不是用第一个命令。
N 追加下一个输入行到模板块后面并在二者间嵌入一个新行，改变当前行号码。
p 打印模板块的行。
P(大写) 打印模板块的第一行。
q 退出Sed。
b lable 分支到脚本中带有标记的地方，如果分支不存在则分支到脚本的末尾。
r file 从file中读行。
t label if分支，从最后一行开始，条件一旦满足或者T，t命令，将导致分支到带有标号的命令处，或者到脚本的末尾。
T label 错误分支，从最后一行开始，一旦发生错误或者T，t命令，将导致分支到带有标号的命令处，或者到脚本的末尾。
w file 写并追加模板块到file末尾。  
W file 写并追加模板块的第一行到file末尾。  
! 表示后面的命令对所有没有被选定的行发生作用。  
= 打印当前行号码。  
# 把注释扩展到下一个换行符以前。  
```

sed替换标记

```
g 表示行内全面替换。  
p 表示打印行。  
w 表示把行写入一个文件。  
x 表示互换模板块中的文本和缓冲区中的文本。  
y 表示把一个字符翻译为另外的字符（但是不用于正则表达式）
\1 子串匹配标记
& 已匹配字符串标记
```

sed元字符集

```
^ 匹配行开始，如：/^sed/匹配所有以sed开头的行。
$ 匹配行结束，如：/sed$/匹配所有以sed结尾的行。
. 匹配一个非换行符的任意字符，如：/s.d/匹配s后接一个任意字符，最后是d。
* 匹配0个或多个字符，如：/*sed/匹配所有模板是一个或多个空格后紧跟sed的行。
[] 匹配一个指定范围内的字符，如/[ss]ed/匹配sed和Sed。  
[^] 匹配一个不在指定范围内的字符，如：/[^A-RT-Z]ed/匹配不包含A-R和T-Z的一个字母开头，紧跟ed的行。
\(..\) 匹配子串，保存匹配的字符，如s/\(love\)able/\1rs，loveable被替换成lovers。
& 保存搜索字符用来替换其他字符，如s/love/**&**/，love这成**love**。
\< 匹配单词的开始，如:/\<love/匹配包含以love开头的单词的行。
\> 匹配单词的结束，如/love\>/匹配包含以love结尾的单词的行。
x\{m\} 重复字符x，m次，如：/0\{5\}/匹配包含5个0的行。
x\{m,\} 重复字符x，至少m次，如：/0\{5,\}/匹配至少有5个0的行。
x\{m,n\} 重复字符x，至少m次，不多于n次，如：/0\{5,10\}/匹配5~10个0的行。
```

示例：

```bash
# 将file中的book替换成books
sed 's/book/books/' file

# 使用?代替原有的/分隔符防止大量转义，使用正则中的子串替换
sed 's?\(<url>\).*\(</url>\)?\1https://updates.jenkins-zh.cn/update-center.json\2?g' /var/jenkins_home/hudson.model.UpdateCenter.xml
```

## supervisor

```bash
# 安装
pip install supervisor
# 生成简单的配置文件，需要root权限
echo_supervisord_conf > /etc/supervisord.conf

# 启动
supervisord
# 查看后台进程
supervisorctl
```

```bash
echo_supervisord_conf > /etc/supervisord.conf

[program:foo]
command:/bin/cat
```

以上命令就会控制一个进程

## 压缩WSL磁盘空间

```
wsl --shutdown
diskpart

select vdisk file="C:\Users\korov\AppData\Local\Docker\wsl\data\ext4.vhdx"
compact vdisk
```

