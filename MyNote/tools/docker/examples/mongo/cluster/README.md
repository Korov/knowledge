# 方式一

```bash
docker-compose up -d

# 设置分片
docker-compose exec configsvr01 sh -c "mongo < /scripts/init-configserver.js"

docker-compose exec shard01-a sh -c "mongo < /scripts/init-shard01.js"
docker-compose exec shard02-a sh -c "mongo < /scripts/init-shard02.js"
docker-compose exec shard03-a sh -c "mongo < /scripts/init-shard03.js"

# 初始化router
docker-compose exec router01 sh -c "mongo < /scripts/init-router.js"


# 启动分片
docker-compose exec router01 mongo --port 27017
// Enable sharding for database `MyDatabase`
sh.enableSharding("MyDatabase")

// Setup shardingKey for collection `MyCollection`**
db.adminCommand( { shardCollection: "MyDatabase.MyCollection", key: { supplierId: "hashed" } } )
# 就在这个container中查看分片状态
sh.status()
# 查看数据库状态
use MyDatabase
db.stats()
db.MyCollection.getShardDistribution()

```

```bash
# 查看每个分片的状态
docker exec -it rydell-shard-01-node-a bash -c "echo 'rs.status()' | mongo --port 27017" 
docker exec -it rydell-shard-02-node-a bash -c "echo 'rs.status()' | mongo --port 27017" 
docker exec -it rydell-shard-03-node-a bash -c "echo 'rs.status()' | mongo --port 27017" 
```

# 方式二

## 启动容器

一个config，一个router一个server

```bash
docker network create --driver bridge --subnet 172.31.0.0/16 mongo_net

# 启动config server
docker run --name mongo_server -d --network=mongo_net --hostname=mongo_server mongo:5.0.3 mongod --port 27017 --configsvr --replSet rs-config-server

# 启动router
docker run -p 27017:27017 --name mongo_router -d --network=mongo_net --hostname=mongo_router mongo:5.0.3 mongos --port 27017 --configdb rs-config-server/mongo_server:27017 --bind_ip_all

# 启动分片
docker run --name mongo_shard -d --network=mongo_net --hostname=mongo_shard mongo:5.0.3 mongod --port 27017 --shardsvr --replSet mongo_shard
# 启动分片的备份
docker run --name mongo_shard_replicat -d --network=mongo_net --hostname=mongo_shard_replicat mongo:5.0.3 mongod --port 27017 --shardsvr --replSet mongo_shard
```

## 初始化config server

```bash
# 进入docker
docker exec -it mongo_server bash
# 打开mongo连接
mongosh
# 执行以下语句
rs.initiate({_id: "rs-config-server", configsvr: true, version: 1, members: [ { _id: 0, host : 'mongo_server:27017' } ] })
```

## 初始化分片

```bash
# 进入docker
docker exec -it mongo_shard bash
# 打开mongo连接
mongosh
# 执行以下语句
rs.initiate({_id: "mongo_shard", version: 1, members: [ { _id: 0, host : "mongo_shard:27017" }, { _id: 1, host : "mongo_shard_replicat:27017" } ] })
```

## 初始话router

```bash
# 进入docker
docker exec -it mongo_router bash
# 打开mongo连接
mongosh
# 执行以下语句
sh.addShard("mongo_shard/mongo_shard:27017")
sh.addShard("mongo_shard/mongo_shard_replicat:27017")

# 指定数据库启动分片
sh.enableSharding("MyDatabase")
# Setup shardingKey for collection `MyCollection`**
db.adminCommand( { shardCollection: "MyDatabase.MyCollection", key: { supplierId: "hashed" } } )
# 就在这个container中查看分片状态
sh.status()
# 查看数据库状态
use MyDatabase
db.stats()
db.MyCollection.getShardDistribution()
```

## 查看分片状态

```bash
# 进入docker
docker exec -it mongo_shard bash
# 打开mongo连接
mongosh
# 执行以下语句
rs.status()
```

