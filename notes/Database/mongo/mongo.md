 

# [Mongo](https://docs.mongoing.com/mongodb-crud-operations/query-documents)

# mongo shell

## 启动shell：

```bash
# 5版本之后推荐使用mongosh之前使用mongo
mongosh --username alice --password --authenticationDatabase admin --host mongodb0.examples.com --port 27017
```

## 切换数据库

```bash
# 数据库不需要手动创建，切换的时候会自动创建数据库
use database
# collection也不需要手动创建，插入的时候会自动创建
db.user.insertOne({username: "korov"})
# 查询user集合中的所有数据
db.user.find()
db.user.find().pretty()
# 统计user集合中数据的数量
db.user.count()
# 若collection中有特殊字符
db.getCollection("key-count").find()

# 条件查询，查询user集合中username为korov1的数据
db.user.insertOne({username:"korov1"})
db.user.find({username:"korov1"})
# 多条件查询时，条件之间的关系默认为and关系， 查询_id时必须用ObjectId包裹要查询的id
db.user.find({_id:ObjectId("5ff495366341e441451e17bd"), username:"korov1"})
# 和上面的查询是等价的
db.user.find({$and: [{_id:ObjectId("5ff495366341e441451e17bd")}, {username:"korov1"}]})
# or的条件查询
db.user.find({$or:[{username:"korov"},{username:"korov1"}]})

# in, not in的条件查询
db.user.find({username:{$in:["korov","korov1"]}})

# 更新数据库，找到username为korov1，若set中的属性已经存在则更新，没有则新增
db.user.updateOne({username:"korov1"}, {$set:{country:"china"}})
# 更新所有符合条件的数据
db.user.updateMany({username:"korov1"}, {$set:{country:"china1"}})
# 替换操作，将username这个字段删掉，新增country字段
db.user.findOneAndReplace({username:"korov1"}, {country:"china1"})
# 删掉某个属性
db.user.updateOne({_id:ObjectId("5ff495366341e441451e17bd")}, {$unset:{lalla:"lllll"}})

# 复杂更新
db.user.updateOne({username:"korov1"}, {$set:{favorites:{cities:["Chicago", "Cheyenne"],movies:["Casablanca","For a Few Dollars More","The Sting"]}}})
# 查询一个键名为favorites的对象，内部键名为movies作为新的匹配条件
db.user.find({"favorites.movies":"Casablanca"})
# 查询num 大于1995并且小于19995的数据， $gte,$lte分别式大于等于和小于等于
db.user.find({num: {"$gt":1995， "$lt":19995}})
```

## 修改数据名，collection名

```javascript
db.copyDatabase('old_name', 'new_name');
use old_name;
db.dropDatabase('old_name');

db.adminCommand({renameCollection: "db.collection1", to:"db.collection2"})

// 删除Collection
db.getCollection("test").drop();
```

## 修改collection中的字段

### 删除字段

The above link no longer covers '$unset'ing. Nic Cottrell's comment below is the way to go now. Be sure to add {multi: true} if you want to remove this field from all of the documents in the  collection; otherwise, it will only remove it from the first document it finds that matches. See this for updated documentation: 

```javascript
db.example.update({},{$unset:{words:1}},false,true)
db.example.update({}, {$unset: {words:1}} , {multi: true});
```

### 重命名字段

```javascript
db.user.updateMany( {}, { $rename: { "user": "name" } } )
```

## 创建collection

```javascript
db.getCollection("seen_urls").drop();
db.createCollection("seen_urls", {storageEngine: {wiredTiger: {configString: "block_compressor=zstd"}}});
db.getCollection("seen_urls").stats();
```

## 高级更新

```

```

## 显示正在使用的数据库
```javascript
db
# 展示所有的db
show dbs;
# 展示所有collection
show collections;
```

### 插入一个文件

```
db.inventory.insertOne(  
        { item: "canvas", qty: 100, tags: ["cotton"], size: { h: 28, w: 35.5, uom: "cm" } }
)
```

### 插入多个文件

```
db.inventory.insertMany([
        { item: "journal", qty: 25, tags: ["blank", "red"], size: { h: 14, w: 21, uom: "cm" } }, 
        { item: "mat", qty: 85, tags: ["gray"], size: { h: 27.9, w: 35.5, uom: "cm" } },
        { item: "mousepad", qty: 25, tags: ["gel", "blue"], size: { h: 19, w: 22.85, uom: "cm" } }
    ])
```

## 查看db和collection状态

```javascript
db.stats();

db.getCollection("kafka-key-count").stats();
```

# 体系结构

## 数据存储结构

mongodb默认数据目录是`/data/db`，他负责存储所有的mongo的数据文件。在mongo内部每个数据库都包含一个`.ns`文件和一些数据库文件，而且这些数据文件会随着数据量的增加而变得越来越多。

`WiredTiger.wt`文件是mongodb的元数据文件，存储了其他数据库表的元数据信息。

## 引擎

### WiredTiger Storage Engine

#### Document Level Concurrency

支持文档级别的并发写控制，因此，多个客户端可以同时修改不同的文档

### GridFS

支持超大文件（16MB），例如视频啥的。

# 使用索引创建和查询

## explain

explain展示查询语句的执行过程

使用索引的explain

```javascript
db.alert.find({_id:ObjectId("607a9d48136fac5326b2e1ea")}).explain()
```

结果：（totalKeysExamined使用了一个索引，totalDocsExamined显示只扫描了一个docs）

```json
{
  "queryPlanner": {
    "plannerVersion": 1,
    "namespace": "admin.alert",
    "indexFilterSet": false,
    "parsedQuery": {
      "_id": {
        "$eq": "607a9d48136fac5326b2e1ea"
      }
    },
    "winningPlan": {
      "stage": "IDHACK"
    },
    "rejectedPlans": []
  },
  "executionStats": {
    "executionSuccess": true,
    "nReturned": 1,
    "executionTimeMillis": 0,
    "totalKeysExamined": 1,
    "totalDocsExamined": 1,
    "executionStages": {
      "stage": "IDHACK",
      "nReturned": 1,
      "executionTimeMillisEstimate": 0,
      "works": 2,
      "advanced": 1,
      "needTime": 0,
      "needYield": 0,
      "saveState": 0,
      "restoreState": 0,
      "isEOF": 1,
      "keysExamined": 1,
      "docsExamined": 1
    },
    "allPlansExecution": []
  },
  "serverInfo": {
    "host": "6c0425b9ab19",
    "port": 27017,
    "version": "4.4.5",
    "gitVersion": "ff5cb77101b052fa02da43b8538093486cf9b3f7"
  },
  "ok": 1.0
}
```

未使用索引的查询

```javascript
totalDocsExamined
```

结果（totalKeysExamined使用了0个索引，totalDocsExamined扫描了2171905个docs）

```json
{
  "queryPlanner": {
    "plannerVersion": 1,
    "namespace": "admin.alert",
    "indexFilterSet": false,
    "parsedQuery": {
      "key": {
        "$eq": "spl_alert"
      }
    },
    "winningPlan": {
      "stage": "PROJECTION_DEFAULT",
      "transformBy": {
        "value.alertName": "同一源IP针对多目标进行高危端口攻击告警"
      },
      "inputStage": {
        "stage": "COLLSCAN",
        "filter": {
          "key": {
            "$eq": "spl_alert"
          }
        },
        "direction": "forward"
      }
    },
    "rejectedPlans": []
  },
  "executionStats": {
    "executionSuccess": true,
    "nReturned": 303190,
    "executionTimeMillis": 1097,
    "totalKeysExamined": 0,
    "totalDocsExamined": 2171905,
    "executionStages": {
      "stage": "PROJECTION_DEFAULT",
      "nReturned": 303190,
      "executionTimeMillisEstimate": 104,
      "works": 2171907,
      "advanced": 303190,
      "needTime": 1868716,
      "needYield": 0,
      "saveState": 2171,
      "restoreState": 2171,
      "isEOF": 1,
      "transformBy": {
        "value.alertName": "同一源IP针对多目标进行高危端口攻击告警"
      },
      "inputStage": {
        "stage": "COLLSCAN",
        "filter": {
          "key": {
            "$eq": "spl_alert"
          }
        },
        "nReturned": 303190,
        "executionTimeMillisEstimate": 70,
        "works": 2171907,
        "advanced": 303190,
        "needTime": 1868716,
        "needYield": 0,
        "saveState": 2171,
        "restoreState": 2171,
        "isEOF": 1,
        "direction": "forward",
        "docsExamined": 2171905
      }
    },
    "allPlansExecution": []
  },
  "serverInfo": {
    "host": "6c0425b9ab19",
    "port": 27017,
    "version": "4.4.5",
    "gitVersion": "ff5cb77101b052fa02da43b8538093486cf9b3f7"
  },
  "ok": 1.0
}
```

## 创建索引

```javascript
# keys为你要创建的索引字段，1为指定按升序创建索引，-1降序
db.collection.createIndex(keys, options)
# 给alert集合中的所有文档的key键建立升序索引
db.alert.createIndex({key:1})
# 给alert集合中的所有文档的key键建立升序索引，value键建立降序索引(组合索引，一个索引里面两个key)
db.alert.createIndex({key:1，value：-1})
# 后台创建索引
db.values.createIndex({open: 1, close: 1}, {background: true, unique:true})
```

`createIndex()`接收可选参数，可选参数列表如下：

| parameter          | type          | description                                                  |
| ------------------ | ------------- | ------------------------------------------------------------ |
| background         | Boolean       | 创建索引过程会阻塞其他数据库操作，true指定为以后台方式创建索引，默认false |
| unique             | Boolean       | 建立的所以是否为唯一索引，默认false                          |
| name               | string        | 索引的名称，如果未指定默认通过连接索引的字段名和排序顺序生成一个索引名称 |
| dropDups           | Boolean       | 3.0+版本已废弃。在建立唯一索引时是否删除重复记录,指定 true 创建唯一索引。默认值为 **false**. |
| sparse             | Boolean       | 对文档中不存在的字段数据不启用索引；这个参数需要特别注意，如果设置为true的话，在索引字段中不会查询出不包含对应字段的文档.。默认值为 **false**. |
| expireAfterSeconds | integer       | 指定一个以秒为单位的数值，完成 TTL设定，设定集合的生存时间。 |
| v                  | index version | 索引的版本号。默认的索引版本取决于mongod创建索引时运行的版本。 |
| weights            | document      | 索引权重值，数值在 1 到 99,999 之间，表示该索引相对于其他索引字段的得分权重。 |
| default_language   | string        | 对于文本索引，该参数决定了停用词及词干和词器的规则的列表。 默认为英语 |
| language_override  | string        | 对于文本索引，该参数指定了包含在文档中的字段名，语言覆盖默认的language，默认值为 language. |



```javascript
# 查看所有index
db.alert.getIndexes()

[
  {
    "key": {
      "_id": 1
    },
    "name": "_id_",
    "v": 2
  },
  {
    "key": {
      "key": 1
    },
    "name": "key_1",
    "v": 2
  }
]

# 查看索引的大小
db.alert.totalIndexSize();
# 通过索引名称删除对应索引
db.alert.dropIndex("index_name")
# 删除集合中的索引索引
db.alert.dropIndexes()
```

创建索引之后的查询

```javascript
db.alert.find({key:"spl_alert"}, {"value.alertName":"同一源IP针对多目标进行高危端口攻击告警"}).explain()
```

结果（使用了索引相比于上一次的查询使用了200百万数据，这次只查询了30万的数据）

```json
{
  "queryPlanner": {
    "plannerVersion": 1,
    "namespace": "admin.alert",
    "indexFilterSet": false,
    "parsedQuery": {
      "key": {
        "$eq": "spl_alert"
      }
    },
    "winningPlan": {
      "stage": "PROJECTION_DEFAULT",
      "transformBy": {
        "value.alertName": "同一源IP针对多目标进行高危端口攻击告警"
      },
      "inputStage": {
        "stage": "FETCH",
        "inputStage": {
          "stage": "IXSCAN",
          "keyPattern": {
            "key": 1
          },
          "indexName": "key_1",
          "isMultiKey": false,
          "multiKeyPaths": {
            "key": []
          },
          "isUnique": false,
          "isSparse": false,
          "isPartial": false,
          "indexVersion": 2,
          "direction": "forward",
          "indexBounds": {
            "key": [
              "[\"spl_alert\", \"spl_alert\"]"
            ]
          }
        }
      }
    },
    "rejectedPlans": []
  },
  "executionStats": {
    "executionSuccess": true,
    "nReturned": 303190,
    "executionTimeMillis": 480,
    "totalKeysExamined": 303190,
    "totalDocsExamined": 303190,
    "executionStages": {
      "stage": "PROJECTION_DEFAULT",
      "nReturned": 303190,
      "executionTimeMillisEstimate": 66,
      "works": 303191,
      "advanced": 303190,
      "needTime": 0,
      "needYield": 0,
      "saveState": 303,
      "restoreState": 303,
      "isEOF": 1,
      "transformBy": {
        "value.alertName": "同一源IP针对多目标进行高危端口攻击告警"
      },
      "inputStage": {
        "stage": "FETCH",
        "nReturned": 303190,
        "executionTimeMillisEstimate": 40,
        "works": 303191,
        "advanced": 303190,
        "needTime": 0,
        "needYield": 0,
        "saveState": 303,
        "restoreState": 303,
        "isEOF": 1,
        "docsExamined": 303190,
        "alreadyHasObj": 0,
        "inputStage": {
          "stage": "IXSCAN",
          "nReturned": 303190,
          "executionTimeMillisEstimate": 19,
          "works": 303191,
          "advanced": 303190,
          "needTime": 0,
          "needYield": 0,
          "saveState": 303,
          "restoreState": 303,
          "isEOF": 1,
          "keyPattern": {
            "key": 1
          },
          "indexName": "key_1",
          "isMultiKey": false,
          "multiKeyPaths": {
            "key": []
          },
          "isUnique": false,
          "isSparse": false,
          "isPartial": false,
          "indexVersion": 2,
          "direction": "forward",
          "indexBounds": {
            "key": [
              "[\"spl_alert\", \"spl_alert\"]"
            ]
          },
          "keysExamined": 303190,
          "seeks": 1,
          "dupsTested": 0,
          "dupsDropped": 0
        }
      }
    },
    "allPlansExecution": []
  },
  "serverInfo": {
    "host": "6c0425b9ab19",
    "port": 27017,
    "version": "4.4.5",
    "gitVersion": "ff5cb77101b052fa02da43b8538093486cf9b3f7"
  },
  "ok": 1.0
}
```

# 分片

## 介绍

要对已经填充数据的集合进行分片，该集合必须具有以分片键开头的索引。分片一个空集合时，如果该集合还没有针对指定分片键的适当索引，则mongodb会创建支持索引。

**chunks**：mongodb将分片数据拆分成块，每个分块都有一个基于分片键的上下范围

**Bncer and Even Chunk Distribution 均衡器和均匀分配**：均衡器通过在后台迁移各个分片上的块，来实现集群的所有分片中块的均匀分布。

### 分片的策略

#### 哈希分片

计算分片键字段的哈希值，然后根据散列的分片键值为每个块分配一个范围。使用基于哈希值的数据分发有助于更均匀的数据分发，但是对分片键的基于范围的查询需要查询所有的分片。

```javascript
sh.shardCollection(
  "database.collection",
    # 1表示是范围分片
  { "fieldA" : 1, "fieldB" : 1, "fieldC" : "hashed" }
)
```



#### 范围分片

范围分片根据分片键的值将数据划分为多个范围，然后基于分片键的值分配每个块的范围。有可能导致数据分布不均匀，但是对基于范围查询友好

```javascript
sh.shardCollection( "database.collection", { <shard key> } )
```



## 分片键

分片键决定了集合内的文档如何在集群的多个分片间的分布状况。分片键要么是一个索引字段，要么是一个存在于集合内所有文档中的复合索引字段。

MongoDB使用分片键值范围对集合中的数据进行分区。每个范围都定义了一个分片键值的非重叠范围，并且与一个chunk(数据块，下同)相关联。MongoDB尝试在集群中的各个分片之间平均分配数据块。 分片键与数据块分配的有效性直接相关。

```javascript
# 为了将一个集合分片，你必须在sh.shardCollection（）方法中指定目标集合和分片键：
# namespace参数由字符串<database>.<collection>组成，该字符串指定目标集合的完整命名空间
# key参数由包含一个字段和该字段的索引遍历方向的文档组成。
sh.shardCollection( namespace, key )
```

#### 改变一个文档的分片键的值

更新分片键的值时

- `必须`在`事务`中或以`可重试写入`方式在mongos上运行。 不要直接在分片上执行操作。
- 您必须在查询过滤器的完整分片键上包含相等条件。 例如，如果一个分片集合内使用`{country：1，userid：1}`作为分片键，要想更新文档的分片键，则必须在查询过滤器中包含`country：<value>，userid：<value>`。 也可以根据需要在查询中包括其他字段。

```javascript
db.collection.replaceOne()
db.collection.updateOne()
  
db.collection.findOneAndReplace()
db.collection.findOneAndUpdate()
db.collection.findAndModify()

db.sales.updateOne(
  { _id: 12345, location: "" },
  { $set: { location: "New York"} }
)
```

## 改变shard key

### reshard

reshard会导致collection两秒的写被锁定。确保你的存储空间有当前collection大小的1.2倍，确保IO使用率在50%以下，确保CPU使用率在80%以下

```javascript
# 使用mongosh
db.adminCommand({
  reshardCollection: "<database>.<collection>",
  key: <shardkey>
});
```

### refine

```javascript
# 原先只有一个key：customer_id，现在增加了一个key：order_id
db.adminCommand( {
   refineCollectionShardKey: "test.orders",
   key: { customer_id: 1, order_id: 1 }
} ) 
```



# 权限

## 创建admin超级管理员

```javascript
db.createUser(  
  { user: "admin",  
    customData：{description:"superuser"},
    pwd: "admin",  
    roles: [ { role: "userAdminAnyDatabase", db: "admin" } ]  
  }  
) 
```

user字段，为新用户的名字；
pwd字段，用户的密码；
cusomData字段，为任意内容，例如可以为用户全名介绍；
roles字段，指定用户的角色，可以用一个空数组给新用户设定空角色。在roles字段,可以指定内置角色和用户定义的角色。
超级用户的role有两种，userAdmin或者userAdminAnyDatabase(比前一种多加了对所有数据库的访问,仅仅是访问而已)。
db是指定数据库的名字，admin是管理数据库。
不能用admin数据库中的用户登录其他数据库。注：当我用admin登录的时候，切换到test数据库，test数据库中有个用户test_user，此时使用 show users 才能查看到此用户。

## 创建一个不受访问限制的超级用户

```javascript
db.createUser(
    {
        user:"root",
        pwd:"pwd",
        roles:["root"]
    }
)
```

## 创建一个业务数据库管理员用户

```javascript
db.createUser({
    user:"user001",
    pwd:"123456",
    customData:{
        name:'jim',
        email:'jim@qq.com',
        age:18,
    },
    roles:[
        {role:"readWrite",db:"db001"},
        {role:"readWrite",db:"db002"},
        'read'// 对其他数据库有只读权限，对db001、db002是读写权限
    ]
})
```

> 1. 数据库用户角色：read、readWrite；
> 2. 数据库管理角色：dbAdmin、dbOwner、userAdmin;
> 3. 集群管理角色：clusterAdmin、clusterManager、4. clusterMonitor、hostManage；
> 4. 备份恢复角色：backup、restore；
> 5. 所有数据库角色：readAnyDatabase、readWriteAnyDatabase、userAdminAnyDatabase、dbAdminAnyDatabase
> 6. 超级用户角色：root
> 7. 内部角色：__system

> 1. Read：允许用户读取指定数据库
> 2. readWrite：允许用户读写指定数据库
> 3. dbAdmin：允许用户在指定数据库中执行管理函数，如索引创建、删除，查看统计或访问system.profile
> 4. userAdmin：允许用户向system.users集合写入，可以在指定数据库里创建、删除和管理用户
> 5. clusterAdmin：只在admin数据库中可用，赋予用户所有分片和复制集相关函数的管理权限。
> 6. readAnyDatabase：只在admin数据库中可用，赋予用户所有数据库的读权限
> 7. readWriteAnyDatabase：只在admin数据库中可用，赋予用户所有数据库的读写权限
> 8. userAdminAnyDatabase：只在admin数据库中可用，赋予用户所有数据库的userAdmin权限
> 9. dbAdminAnyDatabase：只在admin数据库中可用，赋予用户所有数据库的dbAdmin权限。
> 10. root：只在admin数据库中可用。超级账号，超级权限

## 查看创建的用户

```javascript
// 显示当前数据库中的用户
show users 

// 只能查到当前数据库中的数据
db.runCommand({usersInfo:"userName"})

// 显示所有用户
use admin;
db.system.users.find();
```

## 修改密码

```javascript
use admin
db.changeUserPassword("username", "xxx")

db.runCommand(
    {
        updateUser:"username",
        pwd:"xxx",
        customData:{title:"xxx"}
    }
)
```

## 删除用户

```
db.dropUser('user001')
```



# 备份恢复

## mongodump

```bash
mongodump --uri="mongodb://admin:admin@127.0.0.1:27017/kafka"  --authenticationDatabase="admin" --authenticationMechanism="SCRAM-SHA-256" --db="kafka" --collection="alert" -q='{ "a": { "$gte": 3 }, "date": { "$lt": { "$date": "2016-01-01T00:00:00.000Z" } } }' --gzip --out="/backup"
```

## mongorestore

```bash
# 若没有指定--nsInclude="admin.alert"，则恢复所有
mongorestore --uri="mongodb://admin:admin@127.0.0.1:27017/admin" --authenticationDatabase="admin" --authenticationMechanism="SCRAM-SHA-256" --nsInclude="admin.alert" --gzip  --dir="/backup"

将数据库app中带有user前缀的表都转移到app-dev中，功能真是太强大了
mongorestore --uri="mongodb://admin:admin@nas.korov.org:27017/backup" --authenticationDatabase="admin" --authenticationMechanism="SCRAM-SHA-256" --nsInclude="kafka.value-record" --nsFrom='kafka.value-record' --nsTo='backup.value-record' --gzip  --dir="/backup/kafka"
```

## mongoexport

```bash
mongoexport --uri="mongodb://admin:admin@127.0.0.1:27017/admin" --authenticationDatabase="admin" --authenticationMechanism="SCRAM-SHA-256" --collection="logriver" --type="json" --out="/logriver.json"
```

## mongoimport

```bash
mongoimport --uri="mongodb://admin:admin@127.0.0.1:27017/admin" --authenticationDatabase="admin" --authenticationMechanism="SCRAM-SHA-256" --collection="logriver" --type="json" --file="/logriver.json"
```
