# 使用自己创建的镜像

## 创建镜像

```bash
docker rmi siem-arango:1.0
docker build -f Dockerfile -t siem-arango:1.0 .
```

## 删除多余文件

```bash
sudo rm -rf ./arango1/agency
sudo rm -rf ./arango1/coordinator
sudo rm -rf ./arango1/dbserver

sudo rm -rf ./arango2/agency
sudo rm -rf ./arango2/coordinator
sudo rm -rf ./arango2/dbserver

sudo rm -rf ./arango3/agency
sudo rm -rf ./arango3/coordinator
sudo rm -rf ./arango3/dbserver
```

## 启动容器

### 启动第一个容器

```bash
docker run --rm -it --name=siem-arango1 --network=docker_default --hostname=siem-arango1 -p 8529:8529 -v $PWD/arango1/config:/arango/arangodb3-3.6.6/config -v $PWD/arango1/agency:/var/lib/arangodb3/agency -v $PWD/arango1/dbserver:/var/lib/arangodb3/dbserver -v $PWD/arango1/coordinator:/var/lib/arangodb3/coordinator siem-arango:1.0 bash

#启动agent
/arango/arangodb3-3.6.6/usr/sbin/arangod \
-c /arango/arangodb3-3.6.6/config/agent.conf &

#启动DBSERVER
/arango/arangodb3-3.6.6/usr/sbin/arangod \
-c /arango/arangodb3-3.6.6/config/arangodb.conf &

#启动Coordinator
/arango/arangodb3-3.6.6/usr/sbin/arangod \
-c /arango/arangodb3-3.6.6/config/coordinator.conf &
```

### 启动第二个容器

```bash
docker run --rm -it --name=siem-arango2 --network=docker_default --hostname=siem-arango2 -p 8530:8529 -v $PWD/arango2/config:/arango/arangodb3-3.6.6/config -v $PWD/arango2/agency:/var/lib/arangodb3/agency -v $PWD/arango2/dbserver:/var/lib/arangodb3/dbserver -v $PWD/arango2/coordinator:/var/lib/arangodb3/coordinator siem-arango:1.0 bash

#启动agent
/arango/arangodb3-3.6.6/usr/sbin/arangod \
-c /arango/arangodb3-3.6.6/config/agent.conf &

#启动DBSERVER
/arango/arangodb3-3.6.6/usr/sbin/arangod \
-c /arango/arangodb3-3.6.6/config/arangodb.conf &

#启动Coordinator
/arango/arangodb3-3.6.6/usr/sbin/arangod \
-c /arango/arangodb3-3.6.6/config/coordinator.conf &
```

### 启动第三个容器

```bash
docker run --rm -it --name=siem-arango3 --network=docker_default --hostname=siem-arango3 -p 8531:8529 -v $PWD/arango3/config:/arango/arangodb3-3.6.6/config -v $PWD/arango3/agency:/var/lib/arangodb3/agency -v $PWD/arango3/dbserver:/var/lib/arangodb3/dbserver -v $PWD/arango3/coordinator:/var/lib/arangodb3/coordinator siem-arango:1.0 bash

/arango/arangodb3-3.6.6/usr/sbin/arangod \
-c /arango/arangodb3-3.6.6/config/agent.conf &

/arango/arangodb3-3.6.6/usr/sbin/arangod \
-c /arango/arangodb3-3.6.6/config/arangodb.conf &

/arango/arangodb3-3.6.6/usr/sbin/arangod \
-c /arango/arangodb3-3.6.6/config/coordinator.conf &
```

## 设置密码

设置`authentication=false`

通过Arangosh设置root用户密码

```bash
/arango/arangodb3-3.6.6/bin/arangosh
require("org/arangodb/users").update("root", "rizhiyi&2014");
```

设置`authentication=true`，重启（先kill进程然后使用相同的命令重启）

```bash
kill 15 <pid>
```

## 总结

若设置了`--agency.size 3`为了保证高可用必须确保有两台及以上的agent是可用的才可以。

备份数量必须小于等于DBServer的数量，shard的数量可以大于DBServer的数量

当可用的DBServer的数量小于Write concern*的时候只能读数据不能写数据

# 使用cent

## 启动容器

### 启动第一个容器

算了还是用子集创建的镜像吧，这个好麻烦

```
docker run --rm -it --name=siem-arango3 --network=docker_default --hostname=siem-arango3 -p 8531:8529 siem-arango:1.0 bash

docker run -it --name cent -p 8531:8529 centos:centos6.9 

docker cp ./arangodb3-3.6.6.tar.gz cent:/

mkdir /arango
tar -xzvf arangodb3-3.6.6.tar.gz -C /arango


/arango/arangodb3-3.6.6/usr/sbin/arangod \
-c /arango/arangodb3-3.6.6/config/agent.conf \
--javascript.startup-directory /arango/arangodb3-3.6.6/usr/share/arangodb3/js \
--javascript.app-path /arango/arangodb3-3.6.6/usr/share/arangodb3/js/apps \
--server.endpoint tcp://0.0.0.0:8531 \
--agency.my-address tcp://127.0.0.1:8531 \
--server.authentication false \
--agency.activate true \
--agency.size 1 \
--agency.endpoint tcp://127.0.0.1:8531 \
--agency.supervision true \
--database.directory /var/lib/arangodb3/agency &

/arango/arangodb3-3.6.6/usr/sbin/arangod \
-c /arango/arangodb3-3.6.6/config/arangodb.conf \
--javascript.startup-directory /arango/arangodb3-3.6.6/usr/share/arangodb3/js \
--javascript.app-path /arango/arangodb3-3.6.6/usr/share/arangodb3/js/apps \
--server.authentication=false \
--server.endpoint tcp://0.0.0.0:8526 \
--cluster.my-address tcp://127.0.0.1:8526 \
--cluster.my-role DBSERVER \
--cluster.agency-endpoint tcp://127.0.0.1:8531 \
--database.directory /var/lib/arangodb3/dbserver1 &

/arango/arangodb3-3.6.6/usr/sbin/arangod \
-c /arango/arangodb3-3.6.6/config/arangodb.conf \
--javascript.startup-directory /arango/arangodb3-3.6.6/usr/share/arangodb3/js \
--javascript.app-path /arango/arangodb3-3.6.6/usr/share/arangodb3/js/apps \
--server.authentication=false \
--server.endpoint tcp://0.0.0.0:8527 \
--cluster.my-address tcp://127.0.0.1:8527 \
--cluster.my-role DBSERVER \
--cluster.agency-endpoint tcp://127.0.0.1:8531 \
--database.directory /var/lib/arangodb3/dbserver2 &

/arango/arangodb3-3.6.6/usr/sbin/arangod \
-c /arango/arangodb3-3.6.6/config/coordinator.conf \
--javascript.startup-directory /arango/arangodb3-3.6.6/usr/share/arangodb3/js \
--javascript.app-path /arango/arangodb3-3.6.6/usr/share/arangodb3/js/apps \
--server.endpoint tcp://0.0.0.0:8529 \
--cluster.my-address tcp://127.0.0.1:8529 \
--server.authentication false \
--cluster.my-role COORDINATOR \
--cluster.agency-endpoint tcp://127.0.0.1:8531 \
--database.directory /var/lib/arangodb3/coordinator &
```

```bash
docker run --rm -it --name cent -p 8531:8529 centos:centos6.9 bash
```

# 使用ubuntu

```bash


docker run -d -it -p 8529:8529 --name ubuntu-arango ubuntu:20.04 bash
docker cp .\arangodb3-linux-3.6.6.tar.gz ubuntu-arango:/root

docker exec -it ubuntu-arango bash
cd /root
mkdir arango
tar -zvx -f arangodb3-linux-3.6.6.tar.gz -C ./arango
/root/arango/arangodb3-3.6.6/bin/arangodb create jwt-secret --secret=arangodb.secret
chmod 400 arangodb.secret
#本地测试 3个agent3个coordinator3个dbserver
/root/arango/arangodb3-3.6.6/bin/arangodb --starter.local --starter.data-dir=./localdata --auth.jwt-secret=./arangodb.secret
#在多个机器上启动， A,B,C表示机器ip，需要在每个机器上都执行次操作
arangodb --server.storage-engine=rocksdb --auth.jwt-secret=/etc/arangodb.secret --starter.data-dir=./data --starter.join A,B,C

arangodb --server.storage-engine=rocksdb --auth.jwt-secret=/etc/arangodb.secret --starter.data-dir=./data --cluster.agency-size=3 --starter.join A,B,C
```

# ArangoDB Starter

启动的时候每个机器上将会启动一个Agent，一个Coordinator，一个DB-Server。如果starter使用了8528端口，那么Coordiantor将使用8529（8528+1）端口，DB-Server将使用8530（8528+2）端口，Agent将使用8531（8528+3）可以使用`--starter.port 8528`修改启动端口。

```bash
docker run -d -it --name=siem-arango1 --network=arango_default --hostname=siem-arango1 -p 8529:8529 ubuntu:20.04 bash
docker run -d -it --name=siem-arango2 --network=arango_default --hostname=siem-arango2 -p 8530:8529 ubuntu:20.04 bash
docker run -d -it --name=siem-arango3 --network=arango_default --hostname=siem-arango3 -p 8531:8529 ubuntu:20.04 bash

docker cp .\arangodb3-linux-3.6.6.tar.gz siem-arango1:/root/
docker cp .\arangodb3-linux-3.6.6.tar.gz siem-arango2:/root/
docker cp .\arangodb3-linux-3.6.6.tar.gz siem-arango3:/root/
docker cp .\arangodb.secret siem-arango1:/root/arango/
docker cp .\arangodb.secret siem-arango2:/root/arango/
docker cp .\arangodb.secret siem-arango3:/root/arango/

docker exec -it siem-arango1 bash
docker exec -it siem-arango2 bash
docker exec -it siem-arango3 bash

cd /root
mkdir arango
tar -zvx -f arangodb3-linux-3.6.6.tar.gz -C ./arango

cd /root/arango

#server A
/root/arango/arangodb3-3.6.6/bin/arangodb --auth.jwt-secret=./arangodb.secret --starter.data-dir=./data --all.database.password=root123 --starter.join siem-arango1,siem-arango2,siem-arango3
#server B
/root/arango/arangodb3-3.6.6/bin/arangodb --auth.jwt-secret=./arangodb.secret --starter.data-dir=./data --all.database.password=root123 --starter.join siem-arango1,siem-arango2,siem-arango3
#server C
/root/arango/arangodb3-3.6.6/bin/arangodb --auth.jwt-secret=./arangodb.secret --starter.data-dir=./data --all.database.password=root123 --starter.join siem-arango1,siem-arango2,siem-arango3

docker stop siem-arango1
docker stop siem-arango2
docker stop siem-arango3
docker stop siem-arango4

docker rm siem-arango1
docker rm siem-arango2
docker rm siem-arango3
docker rm siem-arango4
```

```bash
#server A
/root/arango/arangodb3-3.6.6/bin/arangodb --auth.jwt-secret=./arangodb.secret --starter.data-dir=./data --all.database.password=root123 --cluster.agency-size=1 --starter.address=siem-arango1
#server B
/root/arango/arangodb3-3.6.6/bin/arangodb --auth.jwt-secret=./arangodb.secret --starter.data-dir=./data --all.database.password=root123 --starter.join siem-arango1
#server C
/root/arango/arangodb3-3.6.6/bin/arangodb --auth.jwt-secret=./arangodb.secret --starter.data-dir=./data --all.database.password=root123 --starter.join siem-arango1
```

```
docker cp .\password.txt siem-arango1:/root/arango/
docker cp .\password.txt siem-arango2:/root/arango/
docker cp .\password.txt siem-arango3:/root/arango/

/root/arango/arangodb3-3.6.6/bin/arangodb --auth.jwt-secret=./arango/arangodb.secret --starter.data-dir=./arango/data --all.database.password=root123 --starter.join siem-arango1,siem-arango2,siem-arango3

/root/arango/arangodb3-3.6.6/bin/arangodb --auth.jwt-secret=./arango/arangodb.secret --starter.data-dir=./arango/data --starter.join siem-arango1,siem-arango2,siem-arango3

#使用jwt登录arangosh，在arangosh中执行更换密码的命令
/root/arango/arangodb3-3.6.6/bin/arangosh --server.jwt-secret-keyfile=./arango/arangodb.secret

require("@arangodb/users").replace("root", "root123");

# 或者使用arangosh执行对应的文本中的命令
/root/arango/arangodb3-3.6.6/bin/arangosh --server.jwt-secret-keyfile=./arango/arangodb.secret < ./arango/password.txt
```

## 单机

```
docker run -d -it --name=siem-arango4 --network=arango_default --hostname=siem-arango4 -p 8532:8529 ubuntu:20.04 bash

docker cp .\arangodb3-linux-3.6.6.tar.gz siem-arango4:/root/

docker cp .\arangodb.secret siem-arango4:/root/arango/
docker cp .\arangodb.secret siem-arango4:/root/arango/

docker exec -it siem-arango4 bash

cd /root
mkdir arango
tar -zvx -f arangodb3-linux-3.6.6.tar.gz -C ./arango
/root/arango/arangodb3-3.6.6/bin/arangodb --starter.mode=single --all.database.password=root123 --auth.jwt-secret=./arango/arangodb.secret
```

