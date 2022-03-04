 

# 结构

一个完整的ArangoDB集群需要以下数据：Agents，Coordinators，DB-Servers。

![https://www.arangodb.com/docs/stable/images/cluster_topology.png](picture/docker-compose.yaml)

## Agent

集群的配置中心

### 参数

激活Agency：`--agency.activate true`，Agent数量：`--agency.size 3`启动至少3个Agent，Agency才会启动，各个Agent需要能彼此发现，`--agency.endpoint`，`--agency.my-address`

```bash
arangod --server.endpoint tcp://0.0.0.0:5001 \
  --agency.my-address=tcp://127.0.0.1:5001 \
  --server.authentication false \
  --agency.activate true \
  --agency.size 3 \
  --agency.endpoint tcp://127.0.0.1:5001 \
  --agency.supervision true \
  --database.directory agent1 &
   
arangod --server.endpoint tcp://0.0.0.0:5002 \
  --agency.my-address=tcp://127.0.0.1:5002 \
  --server.authentication false \
  --agency.activate true \
  --agency.size 3 \
  --agency.endpoint tcp://127.0.0.1:5001 \
  --agency.supervision true \
  --database.directory agent2 &

arangod --server.endpoint tcp://0.0.0.0:5003 \
  --agency.my-address=tcp://127.0.0.1:5003 \
  --server.authentication false \
  --agency.activate true \
  --agency.size 3 \
  --agency.endpoint tcp://127.0.0.1:5001 \
  --agency.supervision true \
  --database.directory agent3 &
```



## Coordinator

解析AQL优化

### 启动命令

```bash
arangod --server.authentication=false \
  --server.endpoint tcp://0.0.0.0:7001 \
  --cluster.my-address tcp://127.0.0.1:7001 \
  --cluster.my-role COORDINATOR \
  --cluster.agency-endpoint tcp://127.0.0.1:5001 \
  --cluster.agency-endpoint tcp://127.0.0.1:5002 \
  --cluster.agency-endpoint tcp://127.0.0.1:5003 \
  --database.directory coordinator1 &
  
arangod --server.authentication=false \
  --server.endpoint tcp://0.0.0.0:7002 \
  --cluster.my-address tcp://127.0.0.1:7002 \
  --cluster.my-role COORDINATOR \
  --cluster.agency-endpoint tcp://127.0.0.1:5001 \
  --cluster.agency-endpoint tcp://127.0.0.1:5002 \
  --cluster.agency-endpoint tcp://127.0.0.1:5003 \
  --database.directory coordinator2 &
```

## DB Server

数据存储

### 启动命令

```shell
arangod --server.authentication=false \
  --server.endpoint tcp://0.0.0.0:6001 \
  --cluster.my-address tcp://127.0.0.1:6001 \
  --cluster.my-role DBSERVER \
  --cluster.agency-endpoint tcp://127.0.0.1:5001 \
  --cluster.agency-endpoint tcp://127.0.0.1:5002 \
  --cluster.agency-endpoint tcp://127.0.0.1:5003 \
  --database.directory dbserver1 &

arangod --server.authentication=false \
  --server.endpoint tcp://0.0.0.0:6002 \
  --cluster.my-address tcp://127.0.0.1:6002 \
  --cluster.my-role DBSERVER \
  --cluster.agency-endpoint tcp://127.0.0.1:5001 \
  --cluster.agency-endpoint tcp://127.0.0.1:5002 \
  --cluster.agency-endpoint tcp://127.0.0.1:5003 \
  --database.directory dbserver2 &
```

# 创建网络

```
docker network create --driver bridge --subnet 172.25.0.0/16 arango
```

一个使用了外网，一个直接使用内网