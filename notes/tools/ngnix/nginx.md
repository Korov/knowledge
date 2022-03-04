# `nginx`准备工作

## `linux`内核参数的优化

这里针对最通用，使用`nginx`支持更多并发的`tcp`网络参数做简单的说明

修改`/etc/sysctl.conf`来更改内核参数，例如最常用的配置：

```properties
fs.file-max = 999999
net.ipv4.tcp_tw_reuse = 1
net.ipv4.tcp_keepalive_time = 600
net.ipv4.tcp_fin_timeout = 30
net.ipv4.tcp_max_tw_buckets = 5000
net.ipv4.ip_local_port_range = 1024 61000
net.ipv4.tcp_rmem = 4096 32768 262142
net.ipv4.tcp_wmem = 4096 32768 262142
net.core.netdev_max_backlog = 8096
net.core.rmem_default = 262144
net.core.wmem_default = 262144
net.core.rmem_max = 2097152
net.core.wmem_max = 2097152
net.ipv4.tcp_syncookies = 1
net.ipv4.tcp_max_syn.backlog=1024
```

然后执行`sysctl-p`命令，使上述修改生效

- `file-max`：表示进程可以同时打开最大的句柄数，这个参数直接限制最大并发连接数，根据实际情况配置
- tcp_tw_reuse：1表示允许将TIME-WAIT状态的socket重新用于新的TCP连接，这对于服务器来说很有意义，因为服务器上总会有大量TIME-WAIT状态的连接
- tcp_keepalive_time：表示当keepalive启用时，TCP发送keepalive消息的频率，默认2小时，若将其设置的小一些，可以更快的清理无效的连接
- tcp_fin_timeout：表示当前服务器主动关闭连接时，socket保持在FIN-WAIT-2状态的最大时间
- tcp_max_tw_buckets：表示操作系统允许TIME-WAIT套接字数量的最大值，如果超过这个数字，TIME-WAIT套接字将立刻被清楚并打印警告信息。默认为180000,过多的TIME-WAIT套接字会使服务器变慢
- tcp_max_syn_backlog:这个参数表示TCP三次握手建立阶段接收SYN请求队列的最大
  长度,默认为1024,将其设置得大一些可以使出现Nginx繁忙来不及accept新连接的情况时,
  Linux不至于丢失客户端发起的连接请求。
- ip_local_port_range:这个参数定义了在UDP和TCP连接中本地(不包括连接的远端)
  端口的取值范围。
- net.ipv4.tcp_rmem:这个参数定义了TCP接收缓存(用于TCP接收滑动窗口)的最小
  值、默认值、最大值。
- net.ipv4.tcp_wmem:这个参数定义了TCP发送缓存(用于TCP发送滑动窗口)的最小
  值、默认值、最大值。
- netdev_max_backlog:当网卡接收数据包的速度大于内核处理的速度时,会有一个队列
  保存这些数据包。这个参数表示该队列的最大值。
- rmem_default:这个参数表示内核套接字接收缓存区默认的大小。
- wmem_default:这个参数表示内核套接字发送缓存区默认的大小。
- rmem_max:这个参数表示内核套接字接收缓存区的最大大小。
- wmem_max:这个参数表示内核套接字发送缓存区的最大大小。

## nginx的命令行控制

### 启动方式

```bash
#默认启动方式
nginx
#指定配置文件启动方式
nginx -c nginx.conf
#使用-p参数指定nginx的安装目录
nginx -p /path/nginx/
#指定全局配置项的启动方式
nginx -g ""
#测试配置信息是否有错误
nginx -t
#显示版本信息 -v 只显示版本信息， -V 显示更详细的参数

#快速的停止服务
nginx -s stop
#优雅的停止服务，nginx正常的处理完当前所有请求再停止服务
nginx -s quit
#重启服务
nginx -s reload
```

# Nginx的配置

## 运行中的nginx进程之间的关系

部署nginx时都是使用一个master进程来管理多个worker进程，一般情况下，worker进程的数量与服务器上的cpu核心数相等，每一个worker进程都时繁忙的，他们在真正的提供互联网服务，master进程则很清闲，只负责监控管理worker进程，worker进程之间通过共享内存，原子操作等一些进程间通信机制来实现负载均衡等功能。

当任意一个worker进程出现错误从而导致coredump时，master进程会立刻启动新的worker进程继续服务。

## nginx配置的通用语法

### 块配置

块配置项由一个块配置项名和一对大括号组成。具体示例如下：

```java
events {
    ...
}
```

块配置项一定会用大括号把一系列所属的配置全包含进来，表示大括号内的配置同时生效。所有的事件类配置都要在events块中，http、server等配置也遵循这个规定。

配置项可以嵌套，内层块直接继承外层块。

## nginx服务的基本配置

nginx在运行时，至少必须加载几个核心模块和一个事件类型模块。这些模块运行时所支持的配置项称为基本配置--所有其他模块执行时都依赖的配置项。

按照用户使用时预期的功能，分成以下4类：

1. 用于调试、定位问题的配置项
2. 正常运行的必备配置项
3. 优化性能的配置项
4. 事件类配置项

### 用于调试进程和定位问题的配置项

#### 是否以守护进程方式运行nginx

```
#默认 on
daemon on|off
```

守护进程是脱离终端并且在后台运行的进程。它脱离终端是为了避免进程执行过程中的信息在任何终端上显示，这样一来，进程也不会被任何终端所产生的信心所打断。

#### 是否以master/worker方式工作

```
默认 on
master_process on|off
```

#### error日志的设置

```
默认 error_log logs/error.log error
error_log pathfile level;
```

level级别：debug,info,notice,warn,error,crit,alert,emerg从左至右级别依次增大。

#### 是否处理几个特殊的调试点

```
debug_points[stop|abort]
```

这个配置项也是用来帮助用户跟踪调试nginx的，nginx在一些关键的错误逻辑中设置了调试点。如果设置了debug_point为stop，那么nginx的代码执行到这些调试点时就会发出SINGSTOP信号以用于调试。如果设置为abort则会生成一个coredump文件可以用gdb来查看nginx当时的各种信息。

#### 仅对指定的客户端输出debug级别日志

```
debug_connection[ip|cidr]
```

这个配置项实际上输入事件类配置，因此它必须放在`events {}`中。

```
events {
    debug_connection 10.224.66.14;
    debug_connection 10.224.57.0/24;
}
```

这样，仅仅来自以上ip地址的请求才会输出debug级别的日志，其他请求仍然沿用error_log中配置的日志级别。（需确保在执行configure时已经加入了--with-debug参数，否则不会生效）

#### 限制coredump核心转储文件的大小

```
worker_rlimit_core size;
```

#### 指定coredump文件生成目录

```
working_directory path;
```

### 正常运行的配置项

#### 定义环境变量

```
env VAR|VAR=VALUE

env TESTPATH=/tmp/;
```

可以让用户直接设置操作系统上的环境变量

#### 嵌入其他配置文件

```
include pathfile;
```

可以将其他配置文件嵌入到当前的nginx.conf文件中，它的参数可以是绝对路径，也可以是相对路径。可以含有通配符*的文件名，同时可以一次嵌入多个配置文件

#### pid文件的路径

```
pid path/file;
#default
pid logs/nginx.pid;
```

保存master进程id的pid文件存放路径。默认与configure执行时的参数"--pid-path"所指定的路径时相同的，也可以随时修改，但应确保nginx有权在相应的目标中创建pid文件，该文件直接影响nginx是否可以正常运行。

#### nginx worker进程运行的用户及用户组

```
user username[groupname];
#default
user nobody nobody;
```

user用于设置master进程启动后，fork出的worker进程运行在哪个用户和用户组下。当按照user username设置时用户组和用户名相同。

#### 执行nginx worker进程可以打开的最大的句柄描述符个数

```
worker_rlimit_nofile limit;
```

设置一个worker进程可以打开的最大的文件句柄数。

#### 限制信号队列

```
woker_rlimit_sigpending limit;
```

设置每个用户发往nginx的信号度列的大小。当某个用户的信号队列满了，这个用户再发送的信号量会被丢掉。

### 性能优化的配置项

#### worker进程个数

```
worker_processes number;
#default
worker_processes 1;
```

每个worker进程都是单线程的进程，他们会调用各个模块以实现多种多样的功能。如果这些模块确认不会出现阻塞式的调用，那有多少cpu内核就应该配置多少个进程，反之，如果有可能出现阻塞式调用，那么需要配置稍多一些的worker进程。

#### 绑定worker进程到指定的cpu内核

```
worker_cpu_affinity cpumask[cpumask...]
#example
worker_processes 4;
worker_cpu_affinity 1000 0100 0010 0001;
```

#### ssl硬件加速

```
ssl_engine device;
```

#### 系统调用gettimeofday的执行频率

```
timer_resolution t;
```

#### worker进程优先级

```
worker_priority nice;
#default
worker_priority 0;
```

### 事件类配置项

#### 是否打开accept锁

```
accept_mutex [on|off]
#default
accept_mutext on;
```

accept_mutext时nginx的负载均衡锁，accept_mutext这把锁可以让多个worker进程轮流的、序列化的与新客户端建立TCP连接。当某一个worker进程建立的连接数量达到worker_connections配置的最大连接数的7/8时，会大大的减少该worker进程试图简历新TCP连接的机会，以此实现所有worker进程之上处理的客户端请求数量接近。

#### lock文件的路径

```
lock_file path/file;
#default
lock_file logs/nginx.lock;
```

accept锁可能需要这个lock文件。

#### 使用accept锁后到真正建立连接之间的延迟时间

```
accept_mutex_delay Nms;
#default
accept_mutex_delay 500ms;
```

使用accept锁后，同一时间只有一个worker进程能够取到accept锁，这个accept锁不是阻塞锁，如果取不到会立刻返回。如果一个worker进程试图取accept锁而没有取到，它至少要等accept_mutex_delay定义的时间间隔后才能再次试图取锁。

#### 批量简历新连接

```
multi_accept [on|off];
#defalut
multi_accept off;
```

#### 选择事件模型

```
use [kqueue|rtsig|epoll|/dev/poll|select|poll|eventport];
```

#### 每个worker的最大连接数

```
worker_connections number;
```

定义每个worker进程可以同时处理的最大连接数。

# 三大功能

## 反向代理

正向代理是给客户端的ip增加马甲，不可以被别人识别，反向代理则是给服务器的ip增加马甲，客户端不知道服务器的真正ip地址

## 负载均衡

## 动静分离

将所有的静态资源（图片，压缩包等放到一个服务器中），其他的代码部署分开访问

# nginx常用命令

```bash
#热加载
nginx -s reload
```

