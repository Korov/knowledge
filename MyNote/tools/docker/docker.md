# 1 docker基本命令

## 1.1 docker服务操作

启动        systemctl start docker
守护进程重启   sudo systemctl daemon-reload
重启docker服务   systemctl restart  docker
重启docker服务  sudo service docker restart
关闭docker   service docker stop
关闭docker  systemctl stop docker

限制内存，cpu：``-m,--memory=a，--memory-swap=b(-1不限制大小)，且b > a`,

| 选项                   | 描述                                                         |
| ---------------------- | ------------------------------------------------------------ |
| `-m`,`--memory`        | 内存限制，格式是数字加单位，单位可以为 b,k,m,g。最小为 4M    |
| `--memory-swap`        | 内存+交换分区大小总限制。格式同上。必须必`-m`设置的大        |
| `--memory-reservation` | 内存的软性限制。格式同上                                     |
| `--oom-kill-disable`   | 是否阻止 OOM killer 杀死容器，默认没设置                     |
| `--oom-score-adj`      | 容器被 OOM killer 杀死的优先级，范围是[-1000, 1000]，默认为 0 |
| `--memory-swappiness`  | 用于设置容器的虚拟内存控制行为。值为 0~100 之间的整数        |
| `--kernel-memory`      | 核心内存限制。格式同上，最小为 4M                            |

| 选项                  | 描述                                                    |
| --------------------- | ------------------------------------------------------- |
| `--cpuset-cpus=""`    | 允许使用的 CPU 集，值可以为 0-3,0,1                     |
| `-c`,`--cpu-shares=0` | CPU 共享权值（相对权重）                                |
| `cpu-period=0`        | 限制 CPU CFS 的周期，范围从 100ms~1s，即[1000, 1000000] |
| `--cpu-quota=0`       | 限制 CPU CFS 配额，必须不小于1ms，即 >= 1000            |
| `--cpuset-mems=""`    | 允许在上执行的内存节点（MEMs），只对 NUMA 系统有效      |

## 1.2 镜像创建和启动

```bash
# 创建了一个eureka镜像，版本1.0，后面的.表示dockerfile的相对位置
docker build -t eureka:1.0 .
# 启动镜像
docker run -d -p 7001:7001 eureka:1.0
# 删除所有镜像
docker rmi `docker images -q`;
```

### docker images

```bash
docker images [OPTIONS] [REPOSITORY[:TAG]]
# 查看所有的镜像
List images

Options:
  -a, --all             Show all images (default hides intermediate images)
      --digests         Show digests
  -f, --filter filter   Filter output based on conditions provided
      --format string   Pretty-print images using a Go template
      --no-trunc        Don't truncate output
  -q, --quiet           Only show numeric IDs
  
# 示例
docker images redis 
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
redis               latest              36304d3b4540        5 weeks ago         104MB

# filter，是一个 key=value 
# 找出tag为none的
docker images -f "dangling=true"

# format,选项使用Go模板打印出制定格式的列表
.ID – 镜像ID
.Repository – 镜像存储库名称
.Tag – 镜像tag
.Digest – 镜像digest
.CreatedSince – 从镜像创建到现在过去的时间
.CreatedAt – 镜像创建的时间
.Size – 镜像硬盘占用大小
docker images --format "{{.ID}}: {{.Repository}}"
```

## 1.3 容器相关操作

```bash
# 查看正在运行的容器
docker ps
# 查看所有容器
docker ps -a

#重启容器
docker restart 容器名称或者容器id

docker stop `docker ps -aq`;
docker rm `docker ps -aq`;
# 停止并删除所有容器
```

## 1.4 查看容器日志

```bash
docker logs -f -t --since="2019-05-04" --tail=100 container_name
--since : 此参数指定了输出日志开始日期，即只输出指定日期之后的日志。
-f : 查看实时日志
-t : 查看日志产生的日期
-tail=10 : 查看最后的10条日志。
container_name : 容器名
```

## 1.5 进入容器内部

```bash
#docker进入镜像的方式
docker run -it mysql:8.0.11 /bin/bash
#进入容器命令
docker exec -it kafka /bin/sh
#以root用户身份进入容器
docker exec -it -u root kafka /bin/sh
```

## 启动容器时指定网络

```bash
docker run --rm -it --name=spider-minions1 --network=minions --hostname=spider-minions --ip=172.31.0.19 --link=kafka-minions:kafka-minions.com spider-minions:1.0 bash
```

## 1.6 查看容器所有信息

此命令可以查看此容器的所有信息

```bash
docker inspect 容器名
```

## 1.7 镜像、容器、文件转换

```bash
#将镜像rabbitmq:3.8-rc-alpine保存为rabbitmq3.8.tar到当前文件夹
docker save rabbitmq:3.8-rc-alpine -o rabbitmq3.8.tar
#从当前文件夹加载rabbitmq3.8.tar为docker中的镜像
docker load -i rabbitmq3.8.tar
#将jenkins容器转为myjenkins镜像
docker commit jenkins myjenkins
#将jenkins容器保存为jenkins.tar文件
docker export jenkins -o jenkins.tar
#jenkins.tar文件转为镜像，是容器转出的文件
cat ./jenkins.tar | sudo docker import - imagename:latest
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
- VOLUME：创建一个数据卷挂载点，这里只是声明该挂载点，可以在run的时候使用-v指定，如果run的时候没有制定，则会在/var/lib/docker/volumes目录下创建一个目录来绑定匿名卷。
- USER：指定运行容器时的用户名或UID
- STOPSIGNAL：指定退出的信号值
- HEALTHCHECK：配置所启动容器如何进行健康检查
- SHELL：指定默认shell类型

1.ARG：
定义创建镜像过程中使用的变量。
格式为`ARG <name>[=<default value>]`
在执行docker build 时， 可以通过`docker build --build-arg  name=value` 来为变量赋值。当镜像编译成功后， ARG 指定的变量将不再存在(ENV 指定的变量将在镜像中保留）。Docker 内置了一些镜像创建变量， 用户可以直接使用而无须声明， 包括（不区分大小
写 HTTP_PROXY 、HTTPS_PROXY 、FTP_PROXY 、NO_PROXY)。

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

# volume和mount

使用数据卷实现本地数据持久化：
最开始 -v 或者 --volume 选项是给单独容器使用， --mount 选项是给集群服务使用。但是从 Docker 17.06 开始，也可以在单独容器上使用 --mount。通常来讲 --mount 选项也更加具体(explicit)和”啰嗦”(verbose)，最大的区别是

    -v 选项将所有选项集中到一个值
    --mount 选项将可选项分开

如果需要指定 volume driver 选项，那么必须使用 --mount

    -v 或 --volume: 包含三个 field，使用 : 来分割，所有值需要按照正确的顺序。第一个 field 是 volume 的名字，并且在宿主机上唯一，对于匿名 volume，第一个field通常被省略；第二个field是宿主机上将要被挂载到容器的path或者文件；第三个field可选，比如说 ro（只读）rw（读写）
    
    --mount: 包含多个 key-value 对，使用逗号分割。--mount 选项更加复杂，但是各个值之间无需考虑顺序。
        type，可以为 bind（绑定数据卷，映射到主机路径下）, volume（普通数据卷，映射到主机路径下）, tmpfs（临时数据卷，只存于内存中）, 通常为 volume
        source 也可以写成 src，对于 named volumes，可以设置 volume 的名字，对于匿名 volume，可以省略
        destination 可以写成 dst或者 target 该值会挂载到容器
        readonly 可选，如果使用，表示只读
        volume-opt 可选，可以使用多次
docker run -p 3306:3306 --name mymysql -v $PWD/conf:/etc/mysql/conf.d -v $PWD/logs:/logs -v $PWD/data:/var/lib/mysql -e 
docker run -p 3306:3306 --name mymysql --mount source=$PWD/conf,destination=/etc/mysql/conf.d --mount source=$PWD/logs,destination=/logs --mount source=$PWD/data,destination=/var/lib/mysql -e 

#创建数据卷容器，数据卷容器也是一个容器
创建dbdata容器
docker run it -v /dbdata --ame dbdata ubuntu
可以在其他容器中使用--volumes-from 来挂载dbdata 容器中的数据卷，例如创建dbl 和db2 两个容器，并从dbdata 容器挂载数据卷：
$ docker run -it --volumes-from dbdata -name dbl ubuntu
$ docker run -it --volumes-from dbdata －口ame db2 ubuntu
此时， 容器dbl 和db2 都挂载同一个数据卷到相同的／ dbdata 目录，三个容器任何一方在该目录下的写人，其他容器都可以看到。
可以多次使用--volumes-from 参数来从多个容器挂载多个数据卷，还可以从其他已经挂载了容器卷的容器来挂载数据卷：
$ docker run -d --name db3 --volumes-from dbl training/postgres
如果删除了挂载的容器（包括dbdata 、db 工和db2 ），数据卷并不会被自动删除。如果要删除一个数据卷，必须在删除最后一个还挂载着它的容器时显式使用docker rm -v 命令来指定同时删除关联的容器。

可以利用数据卷容器对其中的数据卷进行备份、恢复，以实现数据的迁移。
1. 备份
使用下面的命令来备份dbdata 数据卷容器内的数据卷：
$ docker run -volumes-from dbdata -v $ (pwd) : /backup - -name worker ubuntu tar
cvf /backup/backup.tar /dbdata
这个命令稍微有点复杂，具体分析下。
首先利用ubuntu 镜像创建了一个容器worker 。使用－ -volumes-from dbdata 参数
来让worker 容器挂载db data 容器的数据卷（ 即dbdata 数据卷）；使用-v $ (pwd) : /backup
参数来挂载本地的当前目录到worker容器的/backup 目录。
worker 容器启动后，使用tarcvf/backup/backup.tar /dbdata 命令将/dbdata
下内容备份为容器内的/backup/backup.tar ，即宿主主机当前目录下的backup.tar 。
2. 恢复
如果要恢复数据到一个容器，可以按照下面的操作。
首先创建一个带有数据卷的容器d bdata2:
$ docker run -v /dbdata --name dbdata2 ubuntu /bin/bash
然后创建另一个新的容器，挂载dbda ta2 的容器，并使用untar 解压备份文件到所挂
载的容器卷中：
$docker run --volumes-from dbdata2 -v $(pwd) :/backup busybox tar xvf
/backup/backup.tar

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

# 4 Docker Compose

模板文件最新版本是3，版本1的compose文件结构十分简单，每个顶级元素为服务名，次级元素为服务容器的配置信息：

```yaml
webapp:
    image: example/web
    ports:
        - "80:80"
    volumes:
        - "/data"
```

版本2和3扩展了compose的语法，同时尽量保持跟旧版本的兼容，除了可以声明网络和存储信息外，最大的不同一是添加了版本信息，另一个时需要讲所有的服务放到service根下面。

```yaml
version: "3"
services:
    webapp:
        image: example/web
        deploy:
            restart_policy:
                # none,on-failure,any(default)
                condition: on-failure
                delay: 5s
                max_attempts: 3
                window: 120s
            replicas: 2
            resources:
                limits:
                    cpus: '0.50'
                    memory: 50M
                reservations:
                    cpus: '0.25'
                    memory: 20M
        ports:
            - "80:80"
        networks:
            - mynet
        volumes:
            - "/data"
networks:
    mynet:
```

**每个服务都必须通过image指令指定镜像或build指令（需要Dockerfile）等来自动构建生成镜像。如果使用build指令，在Dockerfile中设置的选项（例如CMD、EXPOSE）将自动被获取，无须在docker-compose.yml中再次设置**

## 4.0 docker compose使用

同一个docker compose文件中的服务时可以通过服务的名称进行相互link的。要link外部的话就需要使用external_links。

### 4.0.1 常用命令

#### docker-compose常用命令

```bash
docker-compose [-f <arg>...] [options] [COMMAND] [ARGS...]
```

-f，–file FILE指定Compose模板文件，默认为docker-compose.yml，可以多次指定。
-p，–project-name NAME指定项目名称，默认将使用所在目录名称作为项目名。
-x-network-driver 使用Docker的可拔插网络后端特性（需要Docker 1.9+版本）
-x-network-driver DRIVER指定网络后端的驱动，默认为bridge（需要Docker 1.9+版本）
-verbose输出更多调试信息
-v，–version打印版本并退出

#### docker-compose up

```bash
docker-compose up [options] [--scale SERVICE=NUM...] [SERVICE...]
```

-d 在后台运行服务容器
–no-color 不使用颜色来区分不同的服务的控制输出
–no-deps 不启动服务所链接的容器
–force-recreate 强制重新创建容器，不能与–no-recreate同时使用
–no-recreate 如果容器已经存在，则不重新创建，不能与–force-recreate同时使用
–no-build 不自动构建缺失的服务镜像
–build 在启动容器前构建服务镜像
–abort-on-container-exit 停止所有容器，如果任何一个容器被停止，不能与-d同时使用
-t, –timeout TIMEOUT 停止容器时候的超时（默认为10秒）
–remove-orphans 删除服务中没有在compose文件中定义的容器
–scale SERVICE=NUM 设置服务运行容器的个数，将覆盖在compose中通过scale指定的参数
`docker-compose up`
启动所有服务
`docker-compose up -d`
在后台所有启动服务
-f 指定使用的Compose模板文件，默认为docker-compose.yml，可以多次指定。
`docker-compose -f docker-compose.yml up -d`

#### docker-compose ps

列出项目中所有容器

#### docker-compose stop

```bash
docker-compose stop [options] [SERVICE...]
```

-t, –timeout TIMEOUT 停止容器时候的超时（默认为10秒）
`docker-compose stop`
停止正在运行的容器，可以通过docker-compose start 再次启动

#### docker-compose down

```bash
docker-compose down [options]
```

停止和删除容器、网络、卷、镜像。
选项包括：
–rmi type，删除镜像，类型必须是：all，删除compose文件中定义的所有镜像；local，删除镜像名为空的镜像
-v, –volumes，删除已经在compose文件中定义的和匿名的附在容器上的数据卷
–remove-orphans，删除服务中没有在compose中定义的容器
`docker-compose down`
停用移除所有容器以及网络相关

#### 7、docker-compose logs

`docker-compose logs [options] [SERVICE...]`
查看服务容器的输出。默认情况下，docker-compose将对不同的服务输出使用不同的颜色来区分。可以通过–no-color来关闭颜色。
`docker-compose logs`
查看服务容器的输出

#### 8、docker-compose build

`docker-compose build [options] [--build-arg key=val...] [SERVICE...]`
构建（重新构建）项目中的服务容器。
选项包括：
–compress 通过gzip压缩构建上下环境
–force-rm 删除构建过程中的临时容器
–no-cache 构建镜像过程中不使用缓存
–pull 始终尝试通过拉取操作来获取更新版本的镜像
-m, –memory MEM为构建的容器设置内存大小
–build-arg key=val为服务设置build-time变量
服务容器一旦构建后，将会带上一个标记名。可以随时在项目目录下运行docker-compose build来重新构建服务

#### 9、docker-compose pull

`docker-compose pull [options] [SERVICE...]`
拉取服务依赖的镜像。
选项包括：
–ignore-pull-failures，忽略拉取镜像过程中的错误
–parallel，多个镜像同时拉取
–quiet，拉取镜像过程中不打印进度信息
`docker-compose pull`
拉取服务依赖的镜像

#### 10、docker-compose restart

`docker-compose restart [options] [SERVICE...]`
重启项目中的服务。
选项包括：
-t, –timeout TIMEOUT，指定重启前停止容器的超时（默认为10秒）
`docker-compose restart`
重启项目中的服务

#### 11、docker-compose rm

`docker-compose rm [options] [SERVICE...]`
删除所有（停止状态的）服务容器。
选项包括：
–f, –force，强制直接删除，包括非停止状态的容器
-v，删除容器所挂载的数据卷
`docker-compose rm`
删除所有（停止状态的）服务容器。推荐先执行docker-compose stop命令来停止容器。

#### 12、docker-compose start

`docker-compose start [SERVICE...]`
`docker-compose start`
启动已经存在的服务容器。

#### 13、docker-compose run

`docker-compose run [options] [-v VOLUME...] [-p PORT...] [-e KEY=VAL...] SERVICE [COMMAND] [ARGS...]`
在指定服务上执行一个命令。
`docker-compose run ubuntu ping www.baidu.com`
在指定容器上执行一个ping命令。

#### 14、docker-compose scale

`docker-compose scale web=3 db=2`
设置指定服务运行的容器个数。通过service=num的参数来设置数量

#### 15、docker-compose pause

`docker-compose pause [SERVICE...]`
暂停一个服务容器

#### 16、docker-compose kill

```
docker-compose kill [options] [SERVICE...]`
通过发送SIGKILL信号来强制停止服务容器。 
支持通过-s参数来指定发送的信号，例如通过如下指令发送SIGINT信号：
`docker-compose kill -s SIGINT
```

#### 17、dokcer-compose config

`docker-compose config [options]`
验证并查看compose文件配置。
选项包括：
–resolve-image-digests 将镜像标签标记为摘要
-q, –quiet 只验证配置，不输出。 当配置正确时，不输出任何内容，当文件配置错误，输出错误信息
–services 打印服务名，一行一个
–volumes 打印数据卷名，一行一个

#### 18、docker-compose create

`docker-compose create [options] [SERVICE...]`
为服务创建容器。
选项包括：
–force-recreate：重新创建容器，即使配置和镜像没有改变，不兼容–no-recreate参数
–no-recreate：如果容器已经存在，不需要重新创建，不兼容–force-recreate参数
–no-build：不创建镜像，即使缺失
–build：创建容器前，生成镜像

#### 19、docker-compose exec

`docker-compose exec [options] SERVICE COMMAND [ARGS...]`
选项包括：
-d 分离模式，后台运行命令。
–privileged 获取特权。
–user USER 指定运行的用户。
-T 禁用分配TTY，默认docker-compose exec分配TTY。
–index=index，当一个服务拥有多个容器时，可通过该参数登陆到该服务下的任何服务，例如：docker-compose exec –index=1 web /bin/bash ，web服务中包含多个容器

#### 20、docker-compose port

`docker-compose port [options] SERVICE PRIVATE_PORT`
显示某个容器端口所映射的公共端口。
选项包括：
–protocol=proto，指定端口协议，TCP（默认值）或者UDP
–index=index，如果同意服务存在多个容器，指定命令对象容器的序号（默认为1）

#### 21、docker-compose push

`docker-compose push [options] [SERVICE...]`
推送服务依的镜像。
选项包括：
–ignore-push-failures 忽略推送镜像过程中的错误

#### 22、docker-compose stop

`docker-compose stop [options] [SERVICE...]`
显示各个容器运行的进程情况。

#### 23、docker-compose unpause

`docker-compose unpause [SERVICE...]`
恢复处于暂停状态中的服务。

#### 24、docker-compose version

`docker-compose version`
打印版本信息。

### 4.0.2 docker-compose模板文件

Docker-Compose标准模板文件应该包含version、services、networks 三大部分，最关键的是services和networks两个部分

#### 2、image

image是指定服务的镜像名称或镜像ID。如果镜像在本地不存在，Compose将会尝试拉取镜像。

```
services: 
    web: 
        image: hello-world
```

#### 3、build

服务除了可以基于指定的镜像，还可以基于一份Dockerfile，在使用up启动时执行构建任务，构建标签是build，可以指定Dockerfile所在文件夹的路径。Compose将会利用Dockerfile自动构建镜像，然后使用镜像启动服务容器。
`build: /path/to/build/dir`
也可以是相对路径，只要上下文确定就可以读取到Dockerfile。
`build: ./dir`
设定上下文根目录，然后以该目录为准指定Dockerfile。

```
build:
  context: ../
  dockerfile: path/of/Dockerfile
```

build都是一个目录，如果要指定Dockerfile文件需要在build标签的子级标签中使用dockerfile标签指定。
如果同时指定image和build两个标签，那么Compose会构建镜像并且把镜像命名为image值指定的名字。

#### 4、context

context选项可以是Dockerfile的文件路径，也可以是到链接到git仓库的url，当提供的值是相对路径时，被解析为相对于撰写文件的路径，此目录也是发送到Docker守护进程的context

```
build:
  context: ./dir
```

#### 5、dockerfile

使用dockerfile文件来构建，必须指定构建路径

```
build:
  context: .
  dockerfile: Dockerfile-alternate
```

dockerfile指令不能跟image同时使用，否则Compose将不确定根据哪个指令来生成最终的服务镜像。

#### 6、command

使用command可以覆盖容器启动后默认执行的命令。
`command: bundle exec thin -p 3000`

#### 7、container_name

Compose的容器名称格式是：<项目名称><服务名称><序号>
可以自定义项目名称、服务名称，但如果想完全控制容器的命名，可以使用标签指定：
`container_name: app`

#### 8、depends_on

在使用Compose时，最大的好处就是少打启动命令，但一般项目容器启动的顺序是有要求的，如果直接从上到下启动容器，必然会因为容器依赖问题而启动失败。例如在没启动数据库容器的时候启动应用容器，应用容器会因为找不到数据库而退出。depends_on标签用于解决容器的依赖、启动先后的问题。

```
version: '2'
services:
  web:
    build: .
    depends_on:
      - db
      - redis
  redis:
    image: redis
  db:
    image: postgres
```

上述YAML文件定义的容器会先启动redis和db两个服务，最后才启动web 服务。

#### 9、pid

`pid: "host"`
将PID模式设置为主机PID模式，跟主机系统共享进程命名空间。容器使用pid标签将能够访问和操纵其他容器和宿主机的名称空间。

#### 10、ports

ports用于映射端口的标签。
使用HOST:CONTAINER格式或者只是指定容器的端口，宿主机会随机映射端口。

```
ports:
 - "3000"
 - "8000:8000"
 - "49100:22"
 - "127.0.0.1:8001:8001"
```

当使用HOST:CONTAINER格式来映射端口时，如果使用的容器端口小于60可能会得到错误得结果，因为YAML将会解析xx:yy这种数字格式为60进制。所以建议采用字符串格式。

#### 11、extra_hosts

添加主机名的标签，会在/etc/hosts文件中添加一些记录。

```
extra_hosts:
 - "somehost:162.242.195.82"
 - "otherhost:50.31.209.229"
```

启动后查看容器内部hosts：

```
162.242.195.82  somehost
50.31.209.229   otherhost
```

#### 12、volumes

挂载一个目录或者一个已存在的数据卷容器，可以直接使用 [HOST:CONTAINER]格式，或者使用[HOST:CONTAINER:ro]格式，后者对于容器来说，数据卷是只读的，可以有效保护宿主机的文件系统。
Compose的数据卷指定路径可以是相对路径，使用 . 或者 .. 来指定相对目录。
数据卷的格式可以是下面多种形式：

```
volumes:
  // 只是指定一个路径，Docker 会自动在创建一个数据卷（这个路径是容器内部的）。
  - /var/lib/mysql
  // 使用绝对路径挂载数据卷
  - /opt/data:/var/lib/mysql
  // 以 Compose 配置文件为中心的相对路径作为数据卷挂载到容器。
  - ./cache:/tmp/cache
  // 使用用户的相对路径（~/ 表示的目录是 /home/<用户目录>/ 或者 /root/）。
  - ~/configs:/etc/configs/:ro
  // 已经存在的命名的数据卷。
  - datavolume:/var/lib/mysql
```

如果不使用宿主机的路径，可以指定一个volume_driver。
`volume_driver: mydriver`

#### 13、volumes_from

从另一个服务或容器挂载其数据卷：

```
volumes_from:
   - service_name    
     - container_name
```

#### 14、dns

自定义DNS服务器。可以是一个值，也可以是一个列表。

```
dns：8.8.8.8
dns：
    - 8.8.8.8    
      - 9.9.9.9
```

#### 15、dns_search

配置DNS搜索域。可以是一个值，也可以是一个列表。

```
dns_search：example.com
dns_search：
    - domain1.example.com
    - domain2.example.com
```

#### 16、entrypoint

在Dockerfile中有一个指令叫做ENTRYPOINT指令，用于指定接入点。
在docker-compose.yml中可以定义接入点，覆盖Dockerfile中的定义：
`entrypoint: /code/entrypoint.sh`

#### 17、env_file

在docker-compose.yml中可以定义一个专门存放变量的文件。
如果通过docker-compose -f FILE指定配置文件，则env_file中路径会使用配置文件路径。
如果有变量名称与environment指令冲突，则以后者为准。格式如下：
`env_file: .env`
或者根据docker-compose.yml设置多个：

```
env_file:
  - ./common.env
  - ./apps/web.env
  - /opt/secrets.env
```

如果在配置文件中有build操作，变量并不会进入构建过程中。

#### 18、cap_add

增加指定容器的内核能力（capacity）。
让容器具有所有能力可以指定：

```
cap_add:
    - ALL
```

#### 19、cap_drop

去掉指定容器的内核能力（capacity）。
去掉NET_ADMIN能力可以指定：

```
cap_drop:
    - NET_ADMIN
```

#### 20、cgroup_parent

创建了一个cgroup组名称为cgroups_1:
`cgroup_parent: cgroups_1`

#### 21、devices

指定设备映射关系，例如：

```
devices:
    - "/dev/ttyUSB1:/dev/ttyUSB0"
```

#### 22、expose

暴露端口，但不映射到宿主机，只允许能被连接的服务访问。仅可以指定内部端口为参数，如下所示：

```
expose:
    - "3000"
    - "8000"
```

#### 23、extends

基于其它模板文件进行扩展。例如，对于webapp服务定义了一个基础模板文件为common.yml：

```
# common.yml
webapp:
    build: ./webapp
    environment:
        - DEBUG=false
        - SEND_EMAILS=false
```

再编写一个新的development.yml文件，使用common.yml中的webapp服务进行扩展：

```
# development.yml
web:
    extends:
        file: common.yml
        service: webapp
    ports:
        - "8000:8000"
    links:
        - db
    environment:
        - DEBUG=true
db:
    image: mysql
```

后者会自动继承common.yml中的webapp服务及环境变量定义。
extends限制如下：
A、要避免出现循环依赖
B、extends不会继承links和volumes_from中定义的容器和数据卷资源
推荐在基础模板中只定义一些可以共享的镜像和环境变量，在扩展模板中具体指定应用变量、链接、数据卷等信息

#### 24、external_links

链接到docker-compose.yml外部的容器，可以是非Compose管理的外部容器。

```
external_links:
    - redis_1
    - project_db_1:mysql
    - project_db_1:postgresql
```

#### 25、labels

为容器添加Docker元数据（metadata）信息。例如，可以为容器添加辅助说明信息：

```
labels：
    com.startupteam.description: "webapp for a strtup team"
```

#### 26、links

链接到其它服务中的容器。使用服务名称（同时作为别名），或者“服务名称:服务别名”（如 SERVICE:ALIAS），例如：

```
links:
    - db
    - db:database
    - redis
```

使用别名将会自动在服务容器中的/etc/hosts里创建。例如：

```
172.17.2.186  db
172.17.2.186  database
172.17.2.187  redis
```

#### 27、log_driver

指定日志驱动类型。目前支持三种日志驱动类型：

```
log_driver: "json-file"
log_driver: "syslog"
log_driver: "none"
```

#### 28、log_opt

日志驱动的相关参数。例如：

```
log_driver: "syslog"log_opt: 
    syslog-address: "tcp://192.168.0.42:123"
```

#### 29、net

设置网络模式。

```
net: "bridge"
net: "none"
net: "host"
```

#### 30、security_opt

指定容器模板标签（label）机制的默认属性（用户、角色、类型、级别等）。例如，配置标签的用户名和角色名：

```
security_opt:
    - label:user:USER
    - label:role:ROLE
```

#### 31、环境变量

环境变量可以用来配置Docker-Compose的行为。
COMPOSE_PROJECT_NAME
设置通过Compose启动的每一个容器前添加的项目名称，默认是当前工作目录的名字。
COMPOSE_FILE
设置docker-compose.yml模板文件的路径。默认路径是当前工作目录。
DOCKER_HOST
设置Docker daemon的地址。默认使用unix:///var/run/docker.sock。 DOCKER_TLS_VERIFY
如果设置不为空，则与Docker daemon交互通过TLS进行。
DOCKER_CERT_PATH
配置TLS通信所需要的验证(ca.pem、cert.pem 和 key.pem)文件的路径，默认是 ~/.docker 。

## 4.1 安装

可以通过修改版本好来安装和升级

```bash
curl -L https://github.com/docker/compose/releases/download/1.25.0/docker-compose-`uname -s`-`uname -m` -o /usr/local/bin/docker-compose
chmod +x /usr/local/bin/docker-compose
```

通过pip安装

```bash
apt install python3-pip
pip install --upgrade pip -i http://mirrors.aliyun.com/pypi/simple/ --trusted-host mirrors.aliyun.com
pip install docker-compose -i http://mirrors.aliyun.com/pypi/simple/ --trusted-host mirrors.aliyun.com
```

设置国内 镜像源

```bash
mkdir -p ~/.pip
touch  ~/.pip/pip.conf

#内容
[global]
timeout = 6000
index-url = https://mirrors.aliyun.com/pypi/simple/
trusted-host = mirrors.aliyun.com
```

## 4.3 创建nacos集群

```bash
#获取最新的示例文件
git clone https://github.com/nacos-group/nacos-docker.git

#执行nacos-docker/example中的脚本
docker-compose -f ./cluster-hostname.yaml up

#停止和启动，删除
docker-compose -f ./cluster-hostname.yaml stop
docker-compose -f ./cluster-hostname.yaml start
docker-compose -f ./cluster-hostname.yaml rm
```

启动完成后登录localhost:8848/nacos进入管理界面，默认用户名和密码为nacos/nacos

## 4.4 安装seata

docker-compose.yaml配置文件

```yaml
version: "3"
services:
  seata-server:
    image: seataio/seata-server
    hostname: seata-server
    ports:
      - "8091:8091"
    environment:
      - SEATA_PORT=8091
      - STORE_MODE=file
```



# Docker是怎样隔离的

## Linux namespace

每个进程能看到的东西

linux允许创建以下namespace：mount，pid，network，ipc

## Linux Control Groups(cgroups)

限制每个进程所能使用的资源

# Docker数据管理

容器中的管理数据主要有两种方式：

- 数据卷（Data Volumes）：容器内数据直接映射到本地主机环境
- 数据卷容器（Data Volume Containers）：使用特定容器维护数据卷

创建容器时将主机本地的任意路径挂载到容器内作为数据卷，这种形式创建的数据卷称为绑定数据卷。

使用`docker run`命令的时候，可以使用`-mount`选项来使用数据卷。`-mount`选项支持三种类型的数据卷包括：

- `volume`:普通数据卷，映射到主机`/var/lib/docker/volumes`路径下
- `bind`:绑定数据卷，映射到主机指定路径下
- `tmpfs`：临时数据卷，只存在于内存中

示例（绑定数据卷到容器的`/opt/webapp`路径下）：

```bash
docker run -d -p --name web --mount type=bind,source=/webapp,destination=/opt/webapp training/webapp python app.py

# 相当于
docker run -d -p --name web -v /webapp:/opt/webapp training/webapp python app.py
```

Docker挂载数据卷的默认权限时读写（rw），用户也可以通过`ro`指定位只读

```bash
docker run -d -p --name web -v /webapp:/opt/webapp:ro training/webapp python app.py
```

## 数据卷容器

数据卷容器是一个容器，他的目的是专门提供数据卷给其他容器挂载

首先创建一个数据卷容器`dbdata`，并在其中创建一个数据卷挂载到`/dbdata`:

```bash
docker run -it -v /dbdata --name dbdata ubuntu
```

然后可以在其他容器中使用`--volumes-from`来挂载`dbdata`目录，三个容器任何一方在该目录下的写入，其他容器都可以看到

```bash
docker run -it --volumes-from dbdata --name db1 ubuntu
docker run -it --volumes-from dbdata --name db2 ubuntu
```

可以多次使用`--volumes-from`参数来从多个容器挂载多个数据卷，还可以从其他已经挂载了容器卷的容器来挂载数据卷

```bash
docker run -d --name db3 --volumes-from db1 training/postgres
```

**使用`--volumes-from`参数所挂载数据卷的容器自身并不需要保持在运行状态**

如果删除了挂载的容器（包括dadata、db1和db2），数据卷并不会自动倍删除。如果要删除一个数据卷，必须在删除最后一个还挂载着她的容器时显示使用`docker rm -v`命令来指定删除关联的容器。

## 利用数据卷容器来迁移数据

```bash
docker run --volumes-from dbdata -v $(pwd):/backup --name worker ubuntu tar cvf /backup/backup.tar /dbdata
```

首先利用ubuntu镜像创建了一个容器worker。使用`--volumes-from dbdata`参数来让worker容器挂载dbdata容器的数据卷（即dbdata数据卷）；使用`-v $(pwd):/backup`来挂载本地的当前目录到worker容器的`/backup`目录。worker容器启动后，使用`tar cvf /backup/backup.tar /dbdata`命令将`/dbdata`下内容备份位容器内的`/backup/backup.tar`，即宿主机当前目录下的`backup.tar`。

# 个人总结

## 切换国内镜像

修改或新增`/etc/docker/daemon.json`

```json
{
    "registry-mirrors": ["https://ntb32z44.mirror.aliyuncs.com"]
}
```

或者使用如下命令（推荐）

```bash
curl -sSL https://get.daocloud.io/daotools/set_mirror.sh | sh -s http://f1361db2.m.daocloud.io
```

重启docker生效

```bash
systemctl restart docker.service
```

## 常用docker镜像：

```sh
docker pull mysql;
docker pull redis;
docker pull rabbitmq;
docker pull openjdk:8;
docker pull openjdk;
docker pull mongo;
docker pull nginx;
docker pull tomcat;
docker pull wurstmeister/kafka;
docker pull wurstmeister/zookeeper;
docker pull zookeeper;
docker pull webcenter/activemq;
```

## 文件拷贝

```bash
docker cp originpath destinationpath

# 将当前文件中的 file.txt 文件拷贝到 ubuntu-test 这个容器的 /root 路径下
docker cp ./file.txt ubuntu-test:/root
```

## 外部执行docker中的命令

```bash
docker exec -u root jenkins /bin/bash -c 'cat /var/jenkins_home/secrets/initialAdminPassword'
```

## --link参数

启动docker ,需要使用--link使两个容器可以通信，--link 容器名:别名  。可以在此容器中使用 ping nacos通信。

每个容器都有自己的IP，是通过主机的docker0进行拓扑的，使用--link则会直接链接到此容器的IP上不会通过主机进行映射。这个让我更深入了解容器是一个单独的虚拟机。

## 创建一个network

```bash
docker network create --driver bridge --subnet 172.19.0.0/16 br17219
```

驱动为bridge，名称为br17219。

## docker info中出现警告

```bash
vi /etc/sysctl.conf
添加如下内容
net.bridge.bridge-nf-call-ip6tables = 1
net.bridge.bridge-nf-call-iptables = 1
执行：
sysctl -p
```

## idea集成docker

开启2375端口：

```bash
sudo vim /usr/lib/systemd/system/docker.service
#在文件末尾添加如下内容
[Service]
ExecStart=
ExecStart=/usr/bin/dockerd -H tcp://0.0.0.0:2375 -H unix://var/run/docker.sock

#重启
sudo systemctl daemon-reload
sudo systemctl restart docker
```

idea中打开file->settings->build,execution...->docker，选择TCP socket，Engine API URL：`tcp://localhost:2375`。

## 添加当前用户到docker

```bash
sudo groupadd docker
sudo gpasswd -a ${USER} docker
sudo systemctl restart docker.service
newgrp docker
```

## 镜像推送

```bash
#推送到阿里云
#登录
docker login --username=tb393160548 registry.cn-hangzhou.aliyuncs.com
#重命名镜像
docker tag [ImageId] registry.cn-hangzhou.aliyuncs.com/korov/myubuntu:[镜像版本号]
#镜像推送到远端
docker push registry.cn-hangzhou.aliyuncs.com/korov/myubuntu:[镜像版本号]
#拉取远端镜像
docker pull registry.cn-hangzhou.aliyuncs.com/korov/myubuntu:[镜像版本号]

#推送到docker hub
#登录
docker login
#重命名镜像
docker tag [ImageId] korov/ubuntu:[镜像版本号]
#镜像推送到远端
docker push korov/ubuntu:[镜像版本号]
#拉取远端镜像
docker pull korov/ubuntu:[镜像版本号]
```

## 设置镜像语言和时区

### ubuntu

#### 更换国内源

```bash
sed -i 's/archive.ubuntu.com/mirrors.aliyun.com/g' /etc/apt/sources.list
```

#### 语言

```bash
apt-get install language-pack-zh-hans
locale-gen zh_CN.UTF-8
echo "export LC_ALL=zh_CN.UTF-8">> /etc/profile
source /etc/profile
```

#### 时区

```bash
apt-get install tzdata
```

## docker开启远程访问

```bash
# 编辑文件
sudo nvim /usr/lib/systemd/system/docker.service
# 找到对应的行数加上 -H tcp://0.0.0.0:2375
ExecStart=/usr/bin/dockerd -H tcp://0.0.0.0:2375

# 重启docker
sudo systemctl daemon-reload
sudo systemctl restart docker
```

访问`http://localhost:2375/images/json`
