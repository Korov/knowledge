# 一 背景与基础

# 第一章 何为容器，为何需要他

## 1.1 容器与虚拟机的比较

虚拟机和容器的根本目标不相同，虚拟机的目的时要完整的模拟另一个环境，而容器的目的时使应用程序能够移植，并把所有依赖关系包含进去。

# 第二章 安装

```bash
$ curl https://get.docker.com > /tmp/install.sh 
$ cat /tmp/install.sh 
... 
$ chmod +x /tmp/install.sh
$ /tmp/install.sh 
```

### 2.1.1 将SELinux置于宽容模式下运行

需要安装SELinux安全模块。

刚开始使用 Docker 时，建议以宽容（permissive）模式运行 SELinux，这样 SELinux 将只
把错误写进日志，而非强制执行。如果以强制（enforcing）模式运行 SELinux，那么很有可
能在执行书中的范例时，会遇到各种莫名其妙的“权限不足”（Permission Denied）错误。
要查看你的 SELinux 处于什么模式，可以通过执行 sestatus 命令的结果得知。要将 SELinux 设为宽容模式，只需执行 sudo setenforce 0。

### 2.1.2 不使用sudo命令执行docker

```bash
sudo groupadd docker     #添加docker用户组
sudo gpasswd -a $USER docker     #将登陆用户加入到docker用户组中
newgrp docker     #更新用户组
docker ps    #测试docker命令是否可以使用sudo正常使用
```

# 第三章 迈出第一步

`docker run -i -t debian /bin/bash`。-i和-t表示我们想要一个附有tty的交互会话，/bin/bash参数表示你想获得一个bash shell。当你退出shell时，容器就会停止。

docker diff命令可以查看容器中那些文件做过什么操作。

清理已经停止的容器：`$ docker rm -v $(docker ps -aq -f status=exited)`

docker commit可以讲运行或者停止状态的容器创建成镜像，你需要提供的参数有容器的名称（“cowsay”），新镜像的名称（“cowsayimages”），以及用来存放镜像的仓库名称（“test”）：`docker commit cowsay test/cowsayimage`。

# 第四章 docker基本概念

## 4.4 容器互联

docker的连接（link）是允许同一主机上的容器互相通信的最简单方法。当使用docker默认的联网模型时，容器之间的通信将通过docker的内部网路，这意味着主机网络无法看见这些通信。

使用docker的连接也会把目标容器的别名ID添加到主容器中的/etc/hosts，允许主容器通过别名找到容器目标。