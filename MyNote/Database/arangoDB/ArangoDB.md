#  Document

一种数据结构，数据类似与一张表，每个数据都存在表里的每一行中。每一行数据有一个惟一的不可修改的id，这个id可以在插入数据的时候指定，也可以留空白系统自动生成。

## 增

`INSERT document INTO collectionName`

document是由键值对组成的，可以时JSON等。key上的双引号是可选的。value值有以下几种：

- null
- boolean (true, false)
- number (integer and floating point)
- string
- array
- object

```AQL
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

### 批量插入

用let定义一个数组变量，然后使用迭代将数据插入

```AQL
LET data = [
    { "name": "Robert", "surname": "Baratheon", "alive": false, "traits": ["A","H","C"] },
    { "name": "Jaime", "surname": "Lannister", "alive": true, "age": 36, "traits": ["A","F","B"] },
    { "name": "Catelyn", "surname": "Stark", "alive": false, "age": 40, "traits": ["D","H","C"] }
]

FOR d IN data
    INSERT d INTO Characters
```

## 查

### 根据id查询

```AQL
# 根据id查询数据，一次查询一条，和查询多条数据
RETURN DOCUMENT("Characters", "50830")
RETURN DOCUMENT("Characters/50830")
RETURN DOCUMENT("Characters", ["50830", "50060"])
RETURN DOCUMENT(["Characters/50830","Characters/50060"])
```

### 根据collection查询

```AQL
# 查询 Collection 中的所有数据，需要使用迭代的方式
FOR c IN Characters
    RETURN c
# 有序迭代，ASC升序，DESC降序
FOR user IN Characters
  SORT user._key ASC
  RETURN user
```

### 使用FILTER查询

```AQL
# 增加过滤
FOR user IN Characters
  FILTER user.age > 30
  SORT user.age DESC
  RETURN user
```

FILTER支持的查询条件：

- Equality condition

  >```
  >FOR c IN Characters
  >    FILTER c.name == "Ned"
  >    RETURN c
  >```

- Range conditions

  >```
  >FOR c IN Characters
  >    FILTER c.age >= 13
  >    RETURN c.name
  >
  ># 返回指定列
  >FOR c IN Characters
  >    FILTER c.age < 13
  >    RETURN { name: c.name, age: c.age }
  >```

- Multiple conditions

  >```
  >FOR c IN Characters
  >    FILTER c.age < 13
  >    FILTER c.age != null
  >    RETURN { name: c.name, age: c.age }
  >```

- Alternative conditions

  >```
  >FOR c IN Characters
  >    FILTER c.name == "Jon" OR c.name == "Joffrey"
  >    RETURN { name: c.name, surname: c.surname }
  >```

### 使用SORT和LIMIT

```AQL
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

### 联合查询

Characters中的traits存的是traits collection中的key

```AQL
FOR c IN Characters
    RETURN DOCUMENT("Traits", c.traits)
    
# 返回指定的列
FOR c IN Characters
    RETURN DOCUMENT("Traits", c.traits)[*].en
```

#### 将数据整合

```AQL
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

```AQL
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

```AQL
# 修改Document
UPDATE "50830" WITH { alive: true } IN Characters
RETURN NEW
```

### 修改全部

```AQL
REPLACE "50830" WITH {
    name: "Ned",
    surname: "Stark",
    alive: false,
    age: 41,
    traits: ["A","H","C","N","P"]
} IN Characters
RETURN NEW
```

### 修改collection中所有的

```AQL
FOR c IN Characters
    UPDATE c WITH { season: 1 } IN Characters
```

由于原document中没有season属性，更新之后所有的document中都会新增一个season属性

## 删

### 根据id删除

```AQL
REMOVE "50830" IN Characters
```

删除Collection中所有的document

```AQL
FOR c IN Characters
    REMOVE c IN Characters
```

# 常用命令

```bash
arangosh> db._create("mycollection")
arangosh> db.mycollection.save({ _key: "testKey", Hello : "World" })
arangosh> db._query('FOR my IN mycollection RETURN my._key').toArray()
```

```bash
arangosh> db._query(
........> 'FOR c IN @@collection FILTER c._key == @key RETURN c._key', {
........>   '@collection': 'mycollection', 
........>   'key': 'testKey'
........> }).toArray();
```

```bash
# 查看所有数据库
db._databases();
# 切换到指定数据库
db._useDatabase("siem_db");
```



## ES6

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

```bash
arangosh> var key = 'testKey';
arangosh> db._query(
........> aql`FOR c IN mycollection FILTER c._key == ${key} RETURN c._key`
........> ).toArray();
```

## statement

```bash
arangosh> stmt = db._createStatement( {
........> "query": "FOR i IN [ 1, 2 ] RETURN i * 2" } );

arangosh> c = stmt.execute();
```

## Cursors

## 备份

```bash
/opt/rizhiyi/parcels/arangodb/bin/arangodump --output-directory "/tmp/arangodump" --overwrite true --server.endpoint tcp://192.168.1.19:8529 --server.username root --server.password "rizhiyi&2014" --server.authentication true --server.database siem_db
```

## 还原

```bash
arangodb3-3.6.4/bin/arangorestore --server.endpoint tcp://172.17.0.4:8529 --server.username root --server.password "" --server.authentication true --server.database newdb3 --create-database true --input-directory "dump" --number-of-shards 6 --replication-factor 2
```

# 从单机迁移到集群

需要将单机中的数据备份出来，然后在集群中恢复，使用arangodump和arangorestore命令

分片（shards）：同一个数据库中的数据分散在不同的物理机器上

复制（replication）：将数据复制到另一台计算机上