# 1 docker基本命令

启动        systemctl start docker
守护进程重启   sudo systemctl daemon-reload
重启docker服务   systemctl restart  docker
重启docker服务  sudo service docker restart
关闭docker   service docker stop
关闭docker  systemctl stop docker

docker images 查看镜像

```bash
docker build -t eureka:1.0 .
# 创建了一个eureka镜像，版本1.0，后面的.表示dockerfile的相对位置
docker run -d -p 7001:7001 eureka:1.0
# 启动镜像
```

```bash
#将镜像rabbitmq:3.8-rc-alpine保存为rabbitmq3.8.tar到当前文件夹
docker save rabbitmq:3.8-rc-alpine -o rabbitmq3.8.tar
#从当前文件夹加载rabbitmq3.8.tar为docker中的镜像
docker load -i rabbitmq3.8.tar
```

```bash
docker stop `docker ps -a -q`;
docker rm `docker ps -a -q`;
# 停止并删除所有容器
docker rmi `docker images -q`;
# 删除所有镜像
```

```bash
docker logs -f -t --since="2019-05-04" --tail=100 container_name
--since : 此参数指定了输出日志开始日期，即只输出指定日期之后的日志。
-f : 查看实时日志
-t : 查看日志产生的日期
-tail=10 : 查看最后的10条日志。
container_name : 容器名
```

```bash
#docker进入容器的方式
docker run -it mysql:8.0.11 /bin/bash
#重启容器
docker restart 容器名称或者容器id
```

# 2 dockerfile

Dockerfile 由一行行命令语句组成， 并且支持以＃开头的注释行。
一般而言， Dockerfile 主体内容分为四部分：**基础镜像信息、维护者信息、镜像操作指令和容器启动时执行指令。**
首行可以通过注释来指定解析器命令， 后续通过注释说明镜像的相关信息。主体部分首
先使用FROM指令指明所基于的镜像名称， 接下来一般是使用LABEL指令说明维护者信息。
后面则是镜像操作指令， 例如RUN指令将对镜像执行跟随的命令。**每运行一条RUN指令，**
**镜像添加新的一层， 并提交**。最后是CMD指令， 来指定运行容器时的操作命令。

Dockerfile中的指令及说明

- FROM：指定所创建镜像的基础镜像
- MAINTAINER：镜像维护者的姓名和邮箱地址
- RUN：运行指定命令，可以在容器构建时在容器内部执行
- EXPOSE：声明镜像内服务监听的端口，当前容器对外暴露出的端口
- WORKDIR：配置工作目录，指定在创建容器后，终端默认登录进来的工作目录
- ENV：指定环境变量，用来在构建镜像过程中设置环境变量，就像代码中的变量一样，在容器中该变量仍然存在
- ADD：添加本地或者URL内容到镜像，并带有解压功能
- COPY：复制内容到镜像，从本地复制到目标主机
- CMD：启动容器时指定默认执行的命令，dockerfile中可以有多个cmd指令，但只有最后一个生效，cmd会被docker run之后的参数替换
- ENTRYPOINT：指定镜像的默认入口命令，不会被替换，会追加
- ONBUILD：创建子镜像时指定自动执行的操作指令，触发器
- LABEL：为生成的镜像添加元数据标签信息
- VOLUME：创建一个数据卷挂载点
- USER：指定运行容器时的用户名或UID
- STOPSIGNAL：指定退出的信号值
- HEALTHCHECK：配置所启动容器如何进行健康检查
- SHELL：指定默认shell类型

1.ARG：
定义创建镜像过程中使用的变量。
格式为ARG <name>[=<default value>]
在执行docker build 时， 可以通过-build-arg[=l 来为变量赋值。当镜像编译成功后， ARG 指定的变量将不再存在(ENV 指定的变量将在镜像中保留）。Docker 内置了一些镜像创建变量， 用户可以直接使用而无须声明， 包括（不区分大小
写） HTTP PROXY 、HTTPS PROXY 、FTP PROXY 、NO PROXY。

2. FROM
指定所创建镜像的基础镜像。（若本地已经加载该镜像则from会从本地加载该镜像，本地未找到则从远端加载该镜像）
格式为FROM <image> [AS <name>] 或FROM <image>: <tag> [AS <name>]
或FROM <image>@<digest> [AS <name>] 。
任何Dockerfile 中第一条指令必须为FROM 指令。并且， 如果在同一个Dockerfile 中创
建多个镜像时， 可以使用多个FROM 指令（每个镜像一次）。
为了保证镜像精简， 可以选用体积较小的镜像如Alpine或Debian 作为基础镜像。例如：
ARG VERSION=9.3
FROM debian:${VERSION}
3. LABEL
LABEL 指令可以为生成的镜像添加元数据标签信息。这些信息可以用来辅助过滤出特
定镜像。
格式为LABEL <key>=<value> <key>=<value> <key>=<value> ...。
例如：
LABEL version="l.0.0-rc3"
LABEL author="yeasy@github" date="2020-01-01"
LABEL description="This 七ex七illustra七es\
tha七label-values can span mul七iple lines."
4. EXPOSE
声明镜像内服务监听的端口。
格式为EXPOSE <par七> [<par巨/<pro七ocol>... ]。
例如：
EXPOSE 22 80 8443
注意该指令只是起到声明作用， 并不会自动完成端口映射。
如果要映射端口出来， 在启动容器时可以使用-P 参数(Docker 主机会自动分配一个宿主
机的临时端口）或-p HOST_PORT:CONTAINER_PORT 参数（具体指定所映射的本地端口）。
5. ENV
指定环境变量， 在镜像生成过程中会被后续RUN指令使用， 在镜像启动的容器中也会存在。
格式为ENV <key> <value>或ENV <key>=<value>
例如：
ENV APP_VERSION=l.0.0
ENV APP_HOME=/usr/local/app
ENV PATH $PATH:/usr/local/bin
指令指定的环境变量在运行时可以被覆盖掉， 如docker run --env <key>=<value>
built_image。
注意当一条ENV 指令中同时为多个环境变量赋值并且值也是从环境变量读取时， 会为
变量都赋值后再更新。如下面的指令， 最终结果为keyl=valuel key2=value2:
ENV keyl=value2
ENV keyl=valuel key2=${keyl)
6.ENTRYPOINT
指定镜像的默认入口命令， 该入口命令会在启动容器时作为根命令执行， 所有传人值作
为该命令的参数。
支持两种格式：
ENTRYPOINT ["executable", "paraml ", "param2"]: exec 调用执行；
ENTRYPOINT command param 1 param2: shell 中执行。
此时， CMD指令指定值将作为根命令的参数。
每个Dockerfile 中只能有一个ENTRYPOINT, 当指定多个时， 只有最后一个起效。
在运行时， 可以被--entrypoint 参数覆盖掉， 如docker run --entrypoint。

# 3 案例

## 3.1 自定义centos

要求：登录后进入定义的默认路径，vim编辑器，支持ifconfig

```dockerfile
FROM centos:8
MAINTAINER Korov <korov9@163.com>
ENV mypath /tmp
#进入容器后直接到mypath变量定义的目录下
WORKDIR $mypath

#安装相应软件
RUN yum -y install vim
RUN yum -y install net-tools

#暴露80端口
EXPOSE 80
#启动容器时默认执行的命令
CMD /bin/bash
```

```bash
#Dockerfile2为当前路径下指定的Dockerfile文件名
docker build -f Dockerfile2 -t mycent:1.0 .

#测试进入的默认文件夹，ifconfig和vim
[root@localhost centos8]# docker run -it mycent:1.0
[root@81ef9229c910 tmp]# pwd
/tmp
[root@81ef9229c910 tmp]# ifconfig
eth0: flags=4163<UP,BROADCAST,RUNNING,MULTICAST>  mtu 1500
        inet 172.17.0.2  netmask 255.255.0.0  broadcast 172.17.255.255
        ether 02:42:ac:11:00:02  txqueuelen 0  (Ethernet)
        RX packets 8  bytes 656 (656.0 B)
        RX errors 0  dropped 0  overruns 0  frame 0
        TX packets 0  bytes 0 (0.0 B)
        TX errors 0  dropped 0 overruns 0  carrier 0  collisions 0

lo: flags=73<UP,LOOPBACK,RUNNING>  mtu 65536
        inet 127.0.0.1  netmask 255.0.0.0
        loop  txqueuelen 1000  (Local Loopback)
        RX packets 0  bytes 0 (0.0 B)
        RX errors 0  dropped 0  overruns 0  frame 0
        TX packets 0  bytes 0 (0.0 B)
        TX errors 0  dropped 0 overruns 0  carrier 0  collisions 0

[root@81ef9229c910 tmp]# vi demo.txt
[root@81ef9229c910 tmp]# ls
demo.txt  ks-script-0n44nrd1  ks-script-w6m6m_20
```

## 3.2 CMD和ENTRYPOINT 命令

cmd给出的是一个容器的默认的可执行体。也就是容器启动以后，默认的执行的命令。重点就是这个“默认”。意味着，如果docker run没有指定任何的执行命令或者dockerfile里面也没有entrypoint，那么，就会使用cmd指定的默认的执行命令执行。同时也从侧面说明了entrypoint的含义，它才是真正的容器启动以后要执行命令。
所以这句话就给出了cmd命令的一个角色定位，它主要作用是默认的容器启动执行命令。（注意不是“全部”作用）

## 3.3 Redis

进入Redis docker的命令：

```bash
docker exec -it containerID redis-cli
```

## 3.4 Kafka

```shell
docker load -i kafka.tar;
docker load -i zookeeper.tar;
docker run -d --name zookeeper -p 2181:2181 -t wurstmeister/zookeeper;
docker run -d --name kafka --publish 9092:9092 --link zookeeper --env KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 --env KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 --env KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://192.168.106.143:9092 --volume /etc/localtime:/etc/localtime wurstmeister/kafka;
```

以上启动Kafka，接下来需要进入Kafka的docker修改文件

```bash
#修改上面的启动参数之后不用修改了
docker exec -it kafka /bin/sh
vi /opt/kafka_2.12-2.2.0/config/server.properties
```

![image-20191129181514247](picture\image-20191129181514247.png)

```bash
#进入Kafka
docker exec -it kafka /bin/sh
#进入Kafka的bin目录下
cd /opt/kafka_*/bin
#创建一个topic，并设置partition的数量为1
./kafka-topics.sh --create --zookeeper zookeeper:2181 --replication-factor 1 --partitions 1 --topic mykafka
#查看创建的topic
./kafka-topics.sh --describe --zookeeper zookeeper:2181 --topic mykafka
#创建一个生产者发送消息
./kafka-console-producer.sh --broker-list localhost:9092 --topic mykafka
#创建一个消费者接受消息
./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic mykafka --from-beginning
```

## 3.5 安装mysql

```bash
#获取最新的mysql镜像
docker pull mysql
#启动mysql，并设置初始密码为人root123,-v为设置容器中内存的挂在路径
docker run --name mysql --restart=always -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root123 -v /home/korov/Install/Docker/MySQL/data:/var/lib/mysql:rw -v /home/korov/Install/Docker/MySQL/log:/var/log/mysql:rw -v /home/korov/Install/Docker/MySQL/config/my.cnf:/etc/mysql/my.cnf:rw -d mysql:latest
```

