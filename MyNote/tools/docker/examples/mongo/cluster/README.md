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

