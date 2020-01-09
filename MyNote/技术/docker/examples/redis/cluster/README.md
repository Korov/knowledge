配置文件是最容易出问题的,这里面有一些注意事项:

    requirepass和masterauth不能启用
    因为使用redis-trib连接集群时是不能指定密码的,如果开启了requirepass或者masterauth会导致集群连接失败,所以应该等集群创建好后再修改密码,这个后文会说
    
    bind
    表示设置redis监听哪个ip,设置了监听之后,只有使用这些ip才能访问这个redis服务,不指定则默认所有ip都能访问该redis服务
    注意:这里的ip指的是redis的ip而非访问方的ip,bind并不直接限制哪些ip能够访问redis,显示ip访问是限制监听后的效果,如果想限制ip访问应使用Linux防火墙功能.
    比方说,在生产条件下的redis服务器有3个ip(外网ip 122.122.122.122,局域网ip192.128.0.1,本地ip127.0.0.1),则为了安全,bind后面只应该写局域网ip和本地ip,这样就只有局域网用户(包括本机)可以通过192.128.0.1访问redis服务,间接起到限制ip访问的作用.
    
    protected-mode
        作用:
        禁止公网访问redis cache，加强redis安全的
        启用条件:
            没有bind IP
            没有设置requirepass访问密码
        解释:
        由于前面提及的原因,保护模式会开启导致无法通过公网访问,故这里需要关闭保护模式,但注意集群建好后要及时添加密码,增强安全性
    
    daemonize 和 pidfile
    实测开启守护模式(daemonize yes)容器会启动失败,因为是使用docker,所以前台启动也没什么关系,pidfile的文件名和端口号一致是一个良好的习惯
集群总线端口
Redis集群中每个redis实例（可能一台机部署多个实例）会使用两个Tcp端口，一个用于给客户端（redis-cli或应用程序等）使用的端口，另一个是用于集群中实例相互通信的内部总线端口，且第二个端口比第一个端口一定大10000.内部总线端口通信使用特殊协议，以便实现集群内部高带宽低时延的数据交换。所以配置redis实例时只需要指明第一个端口就可以了。
但是由于我们使用的是docker,所以要将这个端口暴露出来,否则集群无法建立(使用redis-trib时会一直显示Waiting for the cluster to join)
修改redis.conf文件权限,否则后面写入访问密码到文件的时候会提示Permission denied

```bash
chown -R root redis1/config
chgrp -R root redis1/config
chown -R root redis2/config
chgrp -R root redis2/config
chown -R root redis3/config
chgrp -R root redis3/config
chown -R root redis4/config
chgrp -R root redis4/config
chown -R root redis5/config
chgrp -R root redis5/config
chown -R root redis6/config
chgrp -R root redis6/config

chmod 644 redis1/config/redis.conf
chmod 644 redis2/config/redis.conf
chmod 644 redis3/config/redis.conf
chmod 644 redis4/config/redis.conf
chmod 644 redis5/config/redis.conf
chmod 644 redis6/config/redis.conf

#后台启动
docker-compose -f docker-compose.yaml up -d
#创建集群，Redis容器和启动集群命令都需要使用host网络，此处的ip地址需要使用宿主机的ip
docker run --net host --rm -it zvelo/redis-trib create --replicas 1 korov-linux.com:6061 korov-linux.com:6062 korov-linux.com:6063 korov-linux.com:6064 korov-linux.com:6065 korov-linux.com:6066

进入某个Redis容器，通过`redis-cli -p 6061 -c`以集群方式进入容器set一个值之后去别的容器查看值是否存在
```
