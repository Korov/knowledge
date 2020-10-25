# nginx准备工作

## linux内核参数的优化

这里针对最通用，使用nginx支持更多并发的tcp网络参数做简单的说明

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

- file-max：表示进程可以同时打开最大的句柄数，这个参数直接限制最大并发连接数，根据实际情况配置
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

