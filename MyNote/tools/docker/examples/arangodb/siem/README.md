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
-c /arango/arangodb3-3.6.6/config/agent.conf \
--javascript.startup-directory /arango/arangodb3-3.6.6/usr/share/arangodb3/js \
--javascript.app-path /arango/arangodb3-3.6.6/usr/share/arangodb3/js/apps \
--server.endpoint tcp://0.0.0.0:8531 \
--agency.my-address tcp://siem-arango1:8531 \
--server.authentication false \
--agency.activate true \
--agency.size 3 \
--agency.supervision true \
--database.directory /var/lib/arangodb3/agency &

#启动DBSERVER
/arango/arangodb3-3.6.6/usr/sbin/arangod \
-c /arango/arangodb3-3.6.6/config/arangodb.conf \
--javascript.startup-directory /arango/arangodb3-3.6.6/usr/share/arangodb3/js \
--javascript.app-path /arango/arangodb3-3.6.6/usr/share/arangodb3/js/apps \
--server.authentication=false \
--server.endpoint tcp://0.0.0.0:8530 \
--cluster.my-address tcp://siem-arango1:8530 \
--cluster.my-role DBSERVER \
--cluster.agency-endpoint tcp://siem-arango1:8531 \
--cluster.agency-endpoint tcp://siem-arango2:8531 \
--cluster.agency-endpoint tcp://siem-arango3:8531 \
--database.directory /var/lib/arangodb3/dbserver &

#启动Coordinator
/arango/arangodb3-3.6.6/usr/sbin/arangod \
-c /arango/arangodb3-3.6.6/config/coordinator.conf \
--javascript.startup-directory /arango/arangodb3-3.6.6/usr/share/arangodb3/js \
--javascript.app-path /arango/arangodb3-3.6.6/usr/share/arangodb3/js/apps \
--server.endpoint tcp://0.0.0.0:8529 \
--cluster.my-address tcp://siem-arango1:8529 \
--server.authentication false \
--cluster.my-role COORDINATOR \
--cluster.agency-endpoint tcp://siem-arango1:8531 \
--cluster.agency-endpoint tcp://siem-arango2:8531 \
--cluster.agency-endpoint tcp://siem-arango3:8531 \
--database.directory /var/lib/arangodb3/coordinator &
```

### 启动第二个容器

```bash
docker run --rm -it --name=siem-arango2 --network=docker_default --hostname=siem-arango2 -p 8530:8529 -v $PWD/arango2/config:/arango/arangodb3-3.6.6/config -v $PWD/arango2/agency:/var/lib/arangodb3/agency -v $PWD/arango2/dbserver:/var/lib/arangodb3/dbserver -v $PWD/arango2/coordinator:/var/lib/arangodb3/coordinator siem-arango:1.0 bash

#启动agent
/arango/arangodb3-3.6.6/usr/sbin/arangod \
-c /arango/arangodb3-3.6.6/config/agent.conf \
--javascript.startup-directory /arango/arangodb3-3.6.6/usr/share/arangodb3/js \
--javascript.app-path /arango/arangodb3-3.6.6/usr/share/arangodb3/js/apps \
--server.endpoint tcp://0.0.0.0:8531 \
--agency.my-address tcp://siem-arango2:8531 \
--server.authentication false \
--agency.activate true \
--agency.size 3 \
--agency.supervision true \
--database.directory /var/lib/arangodb3/agency &

#启动DBSERVER
/arango/arangodb3-3.6.6/usr/sbin/arangod \
-c /arango/arangodb3-3.6.6/config/arangodb.conf \
--javascript.startup-directory /arango/arangodb3-3.6.6/usr/share/arangodb3/js \
--javascript.app-path /arango/arangodb3-3.6.6/usr/share/arangodb3/js/apps \
--server.authentication=false \
--server.endpoint tcp://0.0.0.0:8530 \
--cluster.my-address tcp://siem-arango2:8530 \
--cluster.my-role DBSERVER \
--cluster.agency-endpoint tcp://siem-arango1:8531 \
--cluster.agency-endpoint tcp://siem-arango2:8531 \
--cluster.agency-endpoint tcp://siem-arango3:8531 \
--database.directory /var/lib/arangodb3/dbserver &

#启动Coordinator
/arango/arangodb3-3.6.6/usr/sbin/arangod \
-c /arango/arangodb3-3.6.6/config/coordinator.conf \
--javascript.startup-directory /arango/arangodb3-3.6.6/usr/share/arangodb3/js \
--javascript.app-path /arango/arangodb3-3.6.6/usr/share/arangodb3/js/apps \
--server.endpoint tcp://0.0.0.0:8529 \
--cluster.my-address tcp://siem-arango2:8529 \
--server.authentication false \
--cluster.my-role COORDINATOR \
--cluster.agency-endpoint tcp://siem-arango1:8531 \
--cluster.agency-endpoint tcp://siem-arango2:8531 \
--cluster.agency-endpoint tcp://siem-arango3:8531 \
--database.directory /var/lib/arangodb3/coordinator &
```

### 启动第三个容器

```bash
docker run --rm -it --name=siem-arango3 --network=docker_default --hostname=siem-arango3 -p 8531:8529 -v $PWD/arango3/config:/arango/arangodb3-3.6.6/config -v $PWD/arango3/agency:/var/lib/arangodb3/agency -v $PWD/arango3/dbserver:/var/lib/arangodb3/dbserver -v $PWD/arango3/coordinator:/var/lib/arangodb3/coordinator siem-arango:1.0 bash

#启动agent
/arango/arangodb3-3.6.6/usr/sbin/arangod \
-c /arango/arangodb3-3.6.6/config/agent.conf \
--javascript.startup-directory /arango/arangodb3-3.6.6/usr/share/arangodb3/js \
--javascript.app-path /arango/arangodb3-3.6.6/usr/share/arangodb3/js/apps \
--server.endpoint tcp://0.0.0.0:8531 \
--agency.my-address tcp://siem-arango3:8531 \
--server.authentication false \
--agency.activate true \
--agency.size 3 \
--agency.endpoint tcp://siem-arango1:8531 \
--agency.endpoint tcp://siem-arango2:8531 \
--agency.endpoint tcp://siem-arango3:8531 \
--agency.supervision true \
--database.directory /var/lib/arangodb3/agency &

#启动DBSERVER
/arango/arangodb3-3.6.6/usr/sbin/arangod \
-c /arango/arangodb3-3.6.6/config/arangodb.conf \
--javascript.startup-directory /arango/arangodb3-3.6.6/usr/share/arangodb3/js \
--javascript.app-path /arango/arangodb3-3.6.6/usr/share/arangodb3/js/apps \
--server.authentication=false \
--server.endpoint tcp://0.0.0.0:8530 \
--cluster.my-address tcp://siem-arango3:8530 \
--cluster.my-role DBSERVER \
--cluster.agency-endpoint tcp://siem-arango1:8531 \
--cluster.agency-endpoint tcp://siem-arango2:8531 \
--cluster.agency-endpoint tcp://siem-arango3:8531 \
--database.directory /var/lib/arangodb3/dbserver &

#启动Coordinator
/arango/arangodb3-3.6.6/usr/sbin/arangod \
-c /arango/arangodb3-3.6.6/config/coordinator.conf \
--javascript.startup-directory /arango/arangodb3-3.6.6/usr/share/arangodb3/js \
--javascript.app-path /arango/arangodb3-3.6.6/usr/share/arangodb3/js/apps \
--server.endpoint tcp://0.0.0.0:8529 \
--cluster.my-address tcp://siem-arango3:8529 \
--server.authentication false \
--cluster.my-role COORDINATOR \
--cluster.agency-endpoint tcp://siem-arango1:8531 \
--cluster.agency-endpoint tcp://siem-arango2:8531 \
--cluster.agency-endpoint tcp://siem-arango3:8531 \
--database.directory /var/lib/arangodb3/coordinator &
```

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


