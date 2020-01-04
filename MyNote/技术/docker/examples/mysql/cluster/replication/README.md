#### 1 PXC集群

##### 准备工作

```bash
#拉取镜像
docker pull percona/percona-xtradb-cluster
#名字太长改名
docker tag percona/percona-xtradb-cluster pxc
#出于安全考虑，需要给PXC集群实例创建Docker内I不网络。可以通过docker inspect net1查看网络，和docker network rm net1删除网络
docker network create --subnet=172.18.0.0/16 net1
#创建五个数据卷，将数据存储在宿主机中
docker volume create --name v1
docker volume create --name v2
docker volume create --name v3
docker volume create --name v4
docker volume create --name v5
#查看数据库卷的位置
docker inspect v1
#创建数据卷用作备份文件
docker volume create backup
```

##### 正式安装

```bash
#创建第一个MySQL节点，创建后需要等待2分钟左右再创建第2个节点
docker run -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=abc123456 -e CLUSTER_NAME=PXC -e XTRABACKUP_PASSWORD=abc123456 -v v1:/var/lib/mysql -v backup:/data --privileged --name=node1 --net=net1 --ip 172.18.0.2 pxc
#创建第2个MySQL节点
docker run -d -p 3307:3306 -e MYSQL_ROOT_PASSWORD=abc123456 -e CLUSTER_NAME=PXC -e XTRABACKUP_PASSWORD=abc123456 -e CLUSTER_JOIN=node1 -v v2:/var/lib/mysql -v backup:/data --privileged --name=node2 --net=net1 --ip 172.18.0.3 pxc
#创建第3个MySQL节点
docker run -d -p 3308:3306 -e MYSQL_ROOT_PASSWORD=abc123456 -e CLUSTER_NAME=PXC -e XTRABACKUP_PASSWORD=abc123456 -e CLUSTER_JOIN=node1 -v v3:/var/lib/mysql --privileged --name=node3 --net=net1 --ip 172.18.0.4 pxc
#创建第4个MySQL节点
docker run -d -p 3309:3306 -e MYSQL_ROOT_PASSWORD=abc123456 -e CLUSTER_NAME=PXC -e XTRABACKUP_PASSWORD=abc123456 -e CLUSTER_JOIN=node1 -v v4:/var/lib/mysql --privileged --name=node4 --net=net1 --ip 172.18.0.5 pxc
#创建第5个MySQL节点
docker run -d -p 3310:3306 -e MYSQL_ROOT_PASSWORD=abc123456 -e CLUSTER_NAME=PXC -e XTRABACKUP_PASSWORD=abc123456 -e CLUSTER_JOIN=node1 -v v5:/var/lib/mysql -v backup:/data --privileged --name=node5 --net=net1 --ip 172.18.0.6 pxc
```

##### 创建负载均衡

```bash
#拉取haproxy
docker pull haproxy
#在宿主机中修改配置文件
vi /home/soft/haproxy.cfg
```

配置文件内容

```
global
    #工作目录
    chroot /usr/local/etc/haproxy
    #日志文件，使用rsyslog服务中local5日志设备（/var/log/local5），等级info
    log 127.0.0.1 local5 info
    #守护进程运行
    daemon
defaults
    log global
    mode    http
    #日志格式
    option  httplog
    #日志中不记录负载均衡的心跳检测记录
    option  dontlognull
    #连接超时（毫秒）
    timeout connect 5000
    #客户端超时（毫秒）
    timeout client  50000
    #服务器超时（毫秒）
    timeout server  50000
#监控界面   
listen  admin_stats
    #监控界面的访问的IP和端口
    bind  0.0.0.0:8888
    #访问协议
    mode        http
    #URI相对地址
    stats uri   /dbs
    #统计报告格式
    stats realm     Global\ statistics
    #登陆帐户信息
    stats auth  admin:abc123456
#数据库负载均衡
listen  proxy-mysql
    #访问的IP和端口
    bind  0.0.0.0:3306  
    #网络协议
    mode  tcp
    #负载均衡算法（轮询算法）
    #轮询算法：roundrobin
    #权重算法：static-rr
    #最少连接算法：leastconn
    #请求源IP算法：source 
    balance  roundrobin
    #日志格式
    option  tcplog
    #在MySQL中创建一个没有权限的haproxy用户，密码为空。Haproxy使用这个账户对MySQL数据库心跳检测
    option  mysql-check user haproxy
    server  MySQL_1 172.18.0.2:3306 check weight 1 maxconn 2000  
    server  MySQL_2 172.18.0.3:3306 check weight 1 maxconn 2000  
    server  MySQL_3 172.18.0.4:3306 check weight 1 maxconn 2000 
    server  MySQL_4 172.18.0.5:3306 check weight 1 maxconn 2000
    server  MySQL_5 172.18.0.6:3306 check weight 1 maxconn 2000
    #使用keepalive检测死链
    option  tcpka  
```

```bash
#创建第一个Haproxy负载均衡服务器
docker run -it -d -p 4001:8888 -p 4002:3306 -v /home/soft/haproxy:/usr/local/etc/haproxy --name h1 --privileged --net=net1 --ip 172.18.0.7 haproxy
#进入h1容器启动Hasroxy
docker exec -it h1 bash
haproxy -f /usr/local/etc/haproxy/haproxy.cfg
```

访问http://ip:4001/dbs查看是否启动成功

##### 使用keepalive实现高可用

![image-20191230224348573](F:/MyGitHub/ChinaGitHub/gitee/MyNote/技术/docker/picture/image-20191230224348573.png)

安装keepalive

```bash
yum install keepalived
systemctl start keepalived
```

期间需要修改配置文件/etc/keepalived/keepalived.conf

![图片描述](F:/MyGitHub/ChinaGitHub/gitee/MyNote/技术/docker/picture/5baf4b9000011b0807820397.png)

- interface 网卡设备
- virtual_router_id 虚拟路由标识，MASTER和BACKUP的虚拟路由标识必须一致。标识可以是0~255
- priority MASTER权重要高于BACKUP数字越大优先级越高
- advert_int  MASTER与BACKUP节点间同步检查的时间间隔，单位为妙。主备之间必须一致
- authentication 主从服务器验证方式，主备必须使用相同的密码才能正常通信
- virtual_ipaddress 虚拟IP地址。可以设置多个虚拟IP地址 每行一个

##### 备份数据

mysqldump属于冷备份；冷备份是关闭数据库时候的备份方式，通常做法是拷贝数据文件；冷备份是最简单最安全的一种备份方式；大型网站无法做到关闭业务备份数据，所以冷备份不是最佳选择。

热备份是在系统运行的状态下备份数据，也是难度最大的备份，常见的热备份有LVM和XtraBackup（推荐）

XtraBackup优点：

- XtraBackup备份过程不锁表、快速可靠、
- XtraBackup备份过程不会打断正在执行的事务
- XtraBackup能够基于压缩等功能节约磁盘空间和流量

LVM备份：

- 需要锁表，只能读
- 全量备份，备份时间长，占用空间大
- 增量备份只备份变化的那部分数据

这里使用XtraBackup备份，进入容器安装XtraBackup，并执行备份

```bash
docker exec -it node1 bash
apt-get update
apt-get installpercona-xtrabackup-24
innobackupex --user=root --password=abc123456 /data/backup/full
```

##### 还原数据

数据可以热备份，但只能冷恢复。最好创建一个空白的MySQL还原数据然后再建立PXC集群。

还原数据前要将未提交的事务回滚，还原数据之后重启MySQL

```bash
rm -rf /var/lib/mysql/*
innobackupex --user=root --password=abc123456 --apply-back /data/backup/full/2018/04-15105-09-07/ 回滚
innobackupex --user=root --password=abc123456 --copy-back /data/backup/full/2018/04-15105-09-07/ 还原
```

#### 2 Replication

```bash
docker pull mysql
```

启动主从服务器（注意启动之前要确保my.cnf的权限是644，否则会被忽略，卡了我一天）

```bash
#创建一个MySQL使用的网络
docker network create --driver bridge --subnet 172.20.0.0/16 net_mysql
docker run --name mysql-master --net net_mysql --restart=always -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root123 \
-v `pwd`/master/data:/var/lib/mysql:rw \
-v `pwd`/master/mysql-files:/var/lib/mysql-files:rw \
-v `pwd`/master/log:/var/log/mysql:rw \
-v /etc/localtime:/etc/localtime:ro \
-v `pwd`/master/config/my.cnf:/etc/mysql/my.cnf:rw \
-d mysql:8.0.11;
docker run --name mysql-slave  --net net_mysql --link mysql-master:mysql-master --restart=always -p 3307:3306 -e MYSQL_ROOT_PASSWORD=root123 \
-v `pwd`/slave/data:/var/lib/mysql:rw \
-v `pwd`/slave/mysql-files:/var/lib/mysql-files:rw \
-v `pwd`/slave/log:/var/log/mysql:rw \
-v /etc/localtime:/etc/localtime:ro \
-v `pwd`/slave/config/my.cnf:/etc/mysql/my.cnf:rw \
-d mysql:8.0.11;
```

使用数据库连接工具进行连接，先连接主服务器的数据库依次执行

```MySQL 
#修改添加用户的加密方式
CREATE USER 'korov'@'%' IDENTIFIED WITH mysql_native_password BY 'korov';

GRANT REPLICATION SLAVE ON *.* TO 'root'@'%';
flush privileges;
show master status;#可以看到主数据库的状态
```

切换到从数据库，依次执行

```mysql
CHANGE MASTER TO
 MASTER_HOST='mysql-master',
 MASTER_PORT=3306,
 MASTER_USER='root',
 MASTER_PASSWORD='root123',
 MASTER_LOG_FILE='mysql-bin.000003',
 MASTER_LOG_POS=6143;
#MASTER_LOG_FILE和MASTER_LOG_POS分别对应上面主服务器中show master status显示的file属性和Position属性
start slave;
show slave status;
#其中Slave_IO_Running,Slave_SQL_Running必须为Yes，表示同步成功，否则执行，stop slave;将之前的动作重新执行一遍。之后我们在主库做的SQL语句执行，会同步到从库中来。
```

注意：`mysql -h remothost -P port -uroot -p`可以远程登录。