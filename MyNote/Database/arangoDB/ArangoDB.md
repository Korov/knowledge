## Document

一种数据结构，数据类似与一张表，每个数据都存在表里的每一行中。每一行数据有一个惟一的不可修改的id，这个id可以在插入数据的时候指定，也可以留空白系统自动生成。

### 增

`INSERT document INTO collectionName`

document是由键值对组成的，可以时JSON等。key上的双引号是可选的。value值有以下几种：

- null
- boolean (true, false)
- number (integer and floating point)
- string
- array
- object

```
#使用的 Collection 是 Characters
# 插入数据，并返回刚刚插入的数据，两条命令必须一起用
INSERT {
    "name": "Ned",
    "surname": "Stark",
    "alive": true,
    "age": 41,
    "traits": ["A","H","C","N","P"]
} INTO Characters
RETURN NEW
```

name和surname是string，alive时boolean，age时number，traits是array，整个document是object

#### 批量插入

用let定义一个数组变量，然后使用迭代将数据插入

```
LET data = [
    { "name": "Robert", "surname": "Baratheon", "alive": false, "traits": ["A","H","C"] },
    { "name": "Jaime", "surname": "Lannister", "alive": true, "age": 36, "traits": ["A","F","B"] },
    { "name": "Catelyn", "surname": "Stark", "alive": false, "age": 40, "traits": ["D","H","C"] }
]

FOR d IN data
    INSERT d INTO Characters
```

### 查

#### 根据id查询

```
# 根据id查询数据，一次查询一条，和查询多条数据
RETURN DOCUMENT("Characters", "50830")
RETURN DOCUMENT("Characters/50830")
RETURN DOCUMENT("Characters", ["50830", "50060"])
RETURN DOCUMENT(["Characters/50830","Characters/50060"])
```

#### 根据collection查询

```
# 查询 Collection 中的所有数据，需要使用迭代的方式
FOR c IN Characters
    RETURN c
# 有序迭代，ASC升序，DESC降序
FOR user IN Characters
  SORT user._key ASC
  RETURN user
```

#### 使用FILTER查询

```
# 增加过滤
FOR user IN Characters
  FILTER user.age > 30
  SORT user.age DESC
  RETURN user
```

FILTER支持的查询条件：

- Equality condition
```
FOR c IN Characters
    FILTER c.name == "Ned"
    RETURN c
```
- Range conditions
```
FOR c IN Characters
    FILTER c.age >= 13
    RETURN c.name

# 返回指定列
FOR c IN Characters
    FILTER c.age < 13
    RETURN { name: c.name, age: c.age }
```
- Multiple conditions
```
FOR c IN Characters
    FILTER c.age < 13
    FILTER c.age != null
    RETURN { name: c.name, age: c.age }
```
- Alternative conditions
```
FOR c IN Characters
    FILTER c.name == "Jon" OR c.name == "Joffrey"
    RETURN { name: c.name, surname: c.surname }
```

#### 使用SORT和LIMIT

```
# 返回前5个
FOR c IN Characters
    LIMIT 5
    RETURN c.name

# 跳过前2个，返回之后的5个
FOR c IN Characters
    LIMIT 2, 5
    RETURN c.name
    
# 根据name排序
FOR c IN Characters
    SORT c.name
    LIMIT 10
    RETURN c.name
# 降序排序
FOR c IN Characters
    SORT c.name DESC
    LIMIT 10
    RETURN c.name
# 多列排序
FOR c IN Characters
    FILTER c.surname
    SORT c.surname, c.name
    LIMIT 10
    RETURN {
        surname: c.surname,
        name: c.name
    }
    
# 此处的FILTER可以过滤掉没有age的document
FOR c IN Characters
    FILTER c.age
    SORT c.age
    LIMIT 10
    RETURN {
        name: c.name,
        age: c.age
    }
```

#### 联合查询

Characters中的traits存的是traits collection中的key

```
FOR c IN Characters
    RETURN DOCUMENT("Traits", c.traits)
    
# 返回指定的列
FOR c IN Characters
    RETURN DOCUMENT("Traits", c.traits)[*].en
```

##### 将数据整合

```
FOR c IN Characters
    RETURN MERGE(c, { traits: DOCUMENT("Traits", c.traits)[*].en } )
    
[
  {
    "_id": "Characters/2861650",
    "_key": "2861650",
    "_rev": "_V1bzsXa---",
    "age": 41,
    "alive": false,
    "name": "Ned",
    "surname": "Stark",
    "traits": [
      "strong",
      "powerful",
      "loyal",
      "rational",
      "brave"
    ]
  },
  {
    "_id": "Characters/2861653",
    "_key": "2861653",
    "_rev": "_V1bzsXa--B",
    "age": 40,
    "alive": false,
    "name": "Catelyn",
    "surname": "Stark",
    "traits": [
      "beautiful",
      "powerful",
      "loyal"
    ]
  },
  ...
]
```

一下命令有同样的merge效果

```
FOR c IN Characters
  RETURN MERGE(c, {
    traits: (
      FOR key IN c.traits
        FOR t IN Traits
          FILTER t._key == key
          RETURN t.en
    )
  })
```

## 改

### 根据document中的key修改value

```
# 修改Document
UPDATE "50830" WITH { alive: true } IN Characters
RETURN NEW
```

#### 修改全部

```
REPLACE "50830" WITH {
    name: "Ned",
    surname: "Stark",
    alive: false,
    age: 41,
    traits: ["A","H","C","N","P"]
} IN Characters
RETURN NEW
```

#### 修改collection中所有的

```
FOR c IN Characters
    UPDATE c WITH { season: 1 } IN Characters
```

由于原document中没有season属性，更新之后所有的document中都会新增一个season属性

### 删

#### 根据id删除

```
REMOVE "50830" IN Characters
```

删除Collection中所有的document

```
FOR c IN Characters
    REMOVE c IN Characters
    
for edge in ThreatAttributeEdge_202108
filter edge._to in ["ThreatAttributeDoc/350009473","ThreatAttributeDoc/350009474","ThreatAttributeDoc/350009475"]
    REMOVE edge IN ThreatAttributeEdge_202108
```

## 常用命令

```bash
arangosh> db._create("mycollection")
arangosh> db.mycollection.save({ _key: "testKey", Hello : "World" })
arangosh> db._query('FOR my IN mycollection RETURN my._key').toArray()
```

```
arangosh> db._query(
........> 'FOR c IN @@collection FILTER c._key == @key RETURN c._key', {
........>   '@collection': 'mycollection', 
........>   'key': 'testKey'
........> }).toArray();
```

```
# 查看所有数据库
db._databases();
# 切换到指定数据库
db._useDatabase("siem_db");
```

### ES6

```typescript
var key = 'testKey';
aql`FOR c IN mycollection FILTER c._key == ${key} RETURN c._key`;
{ 
  "query" : "FOR c IN mycollection FILTER c._key == @value0 RETURN c._key", 
  "bindVars" : { 
    "value0" : "testKey" 
  } 
}
```

```
arangosh> var key = 'testKey';
arangosh> db._query(
........> aql`FOR c IN mycollection FILTER c._key == ${key} RETURN c._key`
........> ).toArray();
```

### statement

```
arangosh> stmt = db._createStatement( {
........> "query": "FOR i IN [ 1, 2 ] RETURN i * 2" } );

arangosh> c = stmt.execute();
```

### Arangosh

#### Database

```
#获取db name
db._name()
#获取db id
db._id()
db._isSystem()
db._properties()

#显示所有db
db._databases()
#切换db
db._useDatabase("name")
#删除db，只能在_system中删除
db._dropDatabase(name)
db._engine()
db._version()
```

##### 创建db

```
#创建db
db._createDatabase("name", options, users)
```

optinos将为新建的db中的collection设置默认值。

- sharding:Valid values are: `""` or `"single"`. Setting this option to `"single"` will enable the OneShard feature in the Enterprise Edition
- *replicationFactor*: Default replication factor. Special values include `"satellite"`, which will replicate the collection to every DB-Server, and `1`, which disables replication.
- *writeConcern*: how many copies of each shard are required to be in sync on the different DB-Servers. If there are less then these many copies in the cluster a shard will refuse to write. The value of *writeConcern* can not be larger than *replicationFactor*.

The optional *users* attribute can be used to create initial users for the new database. If specified, it must be a list of user objects. Each user object can contain the following attributes:

- *username*: the user name as a string. This attribute is mandatory.
- *passwd*: the user password as a string. If not specified, then it defaults to an empty string.
- *active*: a boolean flag indicating whether the user account should be active or not. The default value is *true*.
- *extra*: an optional JSON object with extra user information. The data contained in *extra* will be stored for the user but not be interpreted further by ArangoDB.

If no initial users are specified, a default user *root* will be created with an empty string password.

创建用户，需要切换到对应的数据库执行创建用户的命令

```
require("@arangodb/users").save(username, password, true);
require("@arangodb/users").update(username, password, true);
require("@arangodb/users").remove(username);
```

```
#只能在_system中执行
db._createDatabase("newDB", {}, [{ username: "newUser", passwd: "123456", active: true}])
```

#### Dollection

```
#返回collection没有则返回null
db._collection(collection-name)
db._create(collection-name)

#删除collection
collection.drop(options)
collection.truncate()
col = db._collection(collection-name)
col.drop();

collection.compact()
collection.properties()
#修改属性
collection.properties(properties)
#Returns an object containing statistics about the collection
collection.figures()
```

### Cursors

### 备份

```
# 备份指定database
arangodump --output-directory "/tmp/arangodump" --compress-output --overwrite true --server.endpoint tcp://192.168.1.19:8529 --server.username root --server.password "rizhiyi&2014" --server.authentication true --server.database siem_db --collection ThreatTraceDoc_202107 --collection threatTraceEdge_202107

# 备份还原所有database
arangodump --all-databases true --threads 4
arangorestore --all-databases true
```

### 还原

```
arangodb3-3.6.4/bin/arangorestore --server.endpoint tcp://172.17.0.4:8529 --server.username root --server.password "" --server.authentication true --server.database newdb3 --create-database true --input-directory "dump" --number-of-shards 6 --replication-factor 2
```

## 从单机迁移到集群

需要将单机中的数据备份出来，然后在集群中恢复，使用arangodump和arangorestore命令

分片（shards）：同一个数据库中的数据分散在不同的物理机器上

复制（replication）：将数据复制到另一台计算机上

## 集群

### 集群架构

ArangoDB的集群体系结构是CP主/主模型，没有单点故障。CP指存在网络分区的情况下，数据库更倾向于内部一致性而非可用性。主/主指客户端可以将请求发送到任意节点，并且在任意节点数据库都有相同的体验。没有单点故障是指即使一台机器完全故障也可以提供服务。

### 集群的结构

ArangoDB集群由许多ArangoDB实例组成，这些实例通过网络相互通信。

ArangoDB集群有三种不同的角色：

- Agents
- Coordinators
- DB-Servers

![](http://korov.myqnapcloud.cn:19000/images/cluster_topology.png)

#### Agent

集群当前的配置都保存在Agent中，是基于奇数个运行的ArangoDB实例的高可用弹性键值存储。一个或多个Agent形成Agency。Agency是一个集群的中心，用来存储配置信息。Agency为一个集群提供leader选举和其他同步服务。没有Agency集群中的其他所有角色都不可以运行。

Agents使用Raft consensus algorithm来实现容错，Raft consensus algorithm确保集群的无冲突配置管理。

Agency的核心是管理大型配置树。它支持对该树进行事务性的读写操作，其他服务器可以订阅HTTP回调以对树进行所有更改。

#### Coordinators

可以从外部访问，协调集群任务，例如执行查询和运行Foxx，Coordinators知道数据存储在哪里并且会优化在哪里执行用户提供的查询。Coordinators是无状态，因此可以随意的关闭和重启。

#### DB-Servers

数据实际存储的地方，DB-Servers存储数据shard，使用同步复制，DB-Servers可能成为shard的leader或follower。文档操作首先应用于leader，然后leader同步复制到所有follwer。不可以从外部直接访问DB-Server中的shard，应当通过Coordinators间接访问，

### 配置推荐

- 默认配置一个*Coordinator*和一个*DB-Server*运行在一台服务器上。*Agents*可以运行在单独的运算能力弱的机器上
- 如果需要运行大量的Foxx服务则需要部署更多的*Coordinator*在CPU更强的服务器上，因为Foxx都是运行在*Coordinator*上面。
- 如果需要更多的数据容量并且查询性能是较小的瓶颈，则需要部署更多的*DB-Server*
- 可以在运行应用程序的服务器上部署*Coordinator*，在其他位置部署Agents和DB-Server，这避免了应用程序服务器和数据库之间的网络跃点，从而减少了延迟。

### Sharding

将ArangoDB集群中的一个Collection中的数据分布存储在多个*DB-Servers*上，可以提高一个Collection的数据存储量以及吞吐量。

![](http://korov.myqnapcloud.cn:19000/images/cluster_sharding.png)

外部用户通过连接*Coordinator*进行数据读取和存储，*Coordinator*将自动确定当前的数据存储在哪里（读取的时候）或者数据将要存储在哪里（写入的时候）。shards的信息通过Agency在所有的Coordinators中共享。

可以指定每个Collection的Shards配置，多个shards整合成一个完整的Collection。ArangoDB通过计算值的hash来确定数据存储在哪一个shard，这个值默认是document的_key。

指定hash计算的key

```
db._create("sharded_collection", {"numberOfShards": 4, "shardKeys": ["country"]});
```

集群中某个shard故障，集群仍可以读取数据，只是故障机器上的shard不可读。

#### 工作方式

- *Coordinator*接受并分析查询
- 如果访问了集合，*Coordinator*将访问不同的DB-Servers获取对应的数据
- 这需要*Coordinator*和DB-Servers之间进行网络通讯

此外还需要浪费很多内存和CPU时间，因为*Coordinator*需要执行几个同步复杂的查询，*Coordinator*发送和接受数据，将最终数据构建好返回给访问者，此时*Coordinator*可能会成为系统的瓶颈。

### ACID

为确保持久性请在查询级别启用`waitForSync`确保查询的时候所有的数据修改都已经写入磁盘

设置collection的`writeConcern: 2`确保写入时至少一个备份同步了数据才算写入成功

- The RocksDB engine supports intermediate commits for larger document operations, potentially breaking the atomicity of transactions. To prevent this for individual queries you can increase `intermediateCommitSize` (default 512 MB) and `intermediateCommitCount` accordingly as query option.

### 同步备份

配置Collection的*replicationFactor*指定每个shard有几个备份。设置为1的时候数据只存储一份不会备份到其他的DB-Server中，推荐设置大于等于2.

存在多个replication，其中一个为leader其他为follwer。所有的写入都写入到leader中，leader发送给follwer，`writeConcern: 2`表明写入几个replication才算成功。读操作也是有leader提供。

同步复制功能将使读写都会有更高的延迟。

### 自动故障转移

#### follwer失败

如果持有某个shard的follower copy故障了，那么leader将不能把数据的变化同步到follower上，3秒后leader放弃这个follower并宣布他脱离了同步。

此时有以下两种情况发生

- 如果集群中有另外一台可用的DB-Serve，那么将会在这台DB-Server上创建一个follower来满足*replicationFactor*
- 如果集群中没有可用的DB-Server了，集群将继续运行但是不满足*replicationFactor*

若之后旧的DB-Server恢复了将会发生以下情况

- 如果一个新的follower已经建立那么旧的将不再follow此shard
- 如果还缺follower，旧DB-Server将继续follow此shard

#### leader失败

leader如果15秒没有向Agency发送心跳，Agency认为leader故障了，将推动DB-Servers进行leader选举，在in-sync replicas中选举出一个leader。

The following example will give you an idea of how *failover* has been implemented in ArangoDB Cluster:

1. The *leader* of a *shard* (let’s name it *DBServer001*) is going down.

2. A *Coordinator* is asked to return a document:

   127.0.0.1:8530@_system> db.test.document(“100069”)

3. The *Coordinator* determines which server is responsible for this document and finds *DBServer001*

4. The *Coordinator* tries to contact *DBServer001* and timeouts because it is not reachable.

5. After a short while the *supervision* (running in parallel on the *Agency*) will see that *heartbeats* from *DBServer001* are not coming in

6. The *supervision* promotes one of the *followers* (say *DBServer002*), that is in sync, to be *leader* and makes *DBServer001* a *follower*.

7. As the *Coordinator* continues trying to fetch the document it will see that the *leader* changed to *DBServer002*

8. The *Coordinator* tries to contact the new *leader* (*DBServer002*) and returns the result:
```
    {
        "_key" : "100069",
        "_id" : "test/100069",
        "_rev" : "513",
        "foo" : "bar"
    }
```

9. After a while the *supervision* declares *DBServer001* to be completely dead.

10. A new *follower* is determined from the pool of *DB-Servers*.

11. The new *follower* syncs its data from the *leader* and order is restored.

## 导入导出

### arangodump

```
arangodump --server.endpoint tcp://192.168.1.19：8529 --server.username root --server.password rizhiyi&2014 --server.authentication false --server.database mydb --all-databases true --output-directory "dump" --overwrite true
```

上面的命令会用指定的用户和密码链接将对应数据库中的所有非系统的collection的结构信息和数据一起导出。

To adjust this, there are the following command-line arguments:

- `--dump-data <bool>`: set to *true* to include documents in the dump. Set to *false* to exclude documents. The default value is *true*.
- `--include-system-collections <bool>`: whether or not to include system collections in the dump. The default value is *false*. **Set to \*true\* if you are using named graphs that you are interested in restoring.**

如果只想导出部分collection

```
arangodump --collection myusers --collection myvalues --output-directory "dump"
```

导出的时候对数据进行压缩，多线程导出

```bash
arangodump --threads 4 --output-directory "dump" --compress-output
```

### arangorestore

```bash
arangorestore --server.endpoint tcp://192.168.1.19：8529 --server.username root --server.password rizhiyi&2014 --server.authentication false --server.database mydb --all-databases true --input-directory "dump"
```

If you want to connect to a different database or dump all databases you can additionally use the following startup options:

- `--server.database <string>`: name of the database to connect to. Defaults to the `_system` database.
- `--all-databases true`: restore multiple databases from a dump which used the same option. Introduced in v3.5.0.

Since version 2.6 *arangorestore* provides the option *--create-database*. Setting this option to *true* will create the target database if it does not exist. When creating the target database, the username and passwords passed to *arangorestore* (in options *--server.username* and *--server.password*) will be used to create an initial user for the new database.

The option `--force-same-database` allows restricting arangorestore operations to a database with the same name as in the source dump’s `dump.json` file. It can thus be used to prevent restoring data into a “wrong” database by accident.

The following parameters are available to adjust this behavior:

- `--create-collection <bool>`: set to *true* to create collections in the target database. If the target database already contains a collection with the same name, it will be dropped first and then re-created with the properties found in the input directory. Set to *false* to keep existing collections in the target database. If set to *false* and *arangorestore* encounters a collection that is present in the input directory but not in the target database, it will abort. The default value is *true*.
- `--import-data <bool>`: set to *true* to load document data into the collections in the target database. Set to *false* to not load any document data. The default value  is *true*.
- `--include-system-collections <bool>`: whether or not to include system collections when re-creating collections or reloading data. The default value is *false*.