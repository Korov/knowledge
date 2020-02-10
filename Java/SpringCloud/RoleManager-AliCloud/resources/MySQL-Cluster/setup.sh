#!/bin/bash

# 限制配置文件的权限
chown -R root master/config
chgrp -R root master/config
chown -R root slave/config
chgrp -R root slave/config

#创建一个MySQL使用的网络
docker network create --driver bridge --subnet 172.20.0.0/16 net_mysql

#启动项目
docker run --name mysql-master --net net_mysql --restart=always -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root123 \
-v `pwd`/master/data:/var/lib/mysql:rw \
-v `pwd`/master/mysql-files:/var/lib/mysql-files:rw \
-v `pwd`/master/log:/var/log/mysql:rw \
-v /etc/localtime:/etc/localtime:ro \
-v `pwd`/master/config/my.cnf:/etc/mysql/my.cnf:rw \
-d mysql;
docker run --name mysql-slave  --net net_mysql --link mysql-master:mysql-master --restart=always -p 3307:3306 -e MYSQL_ROOT_PASSWORD=root123 \
-v `pwd`/slave/data:/var/lib/mysql:rw \
-v `pwd`/slave/mysql-files:/var/lib/mysql-files:rw \
-v `pwd`/slave/log:/var/log/mysql:rw \
-v /etc/localtime:/etc/localtime:ro \
-v `pwd`/slave/config/my.cnf:/etc/mysql/my.cnf:rw \
-d mysql;