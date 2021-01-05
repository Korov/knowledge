 

# mongo shell操作mongodb

## mongo shell里使用CRUD

启动shell：

```bash
mongo
```

切换数据库

```bash
# 数据库不需要手动创建，切换的时候会自动创建数据库
use database
# collection也不需要手动创建，插入的时候会自动创建
db.user.insert({username: "korov"})
# 查询user集合中的所有数据
db.user.find()
# 统计user集合中数据的数量
db.user.count()

# 条件查询，查询user集合中username为korov1的数据
db.user.insertOne({username:"korov1"})
db.user.find({username:"korov1"})
# 多条件查询时，条件之间的关系默认为and关系， 查询_id时必须用ObjectId包裹要查询的id
db.user.find({_id:ObjectId("5ff495366341e441451e17bd"), username:"korov1"})
# 和上面的查询是等价的
db.user.find({$and: [{_id:ObjectId("5ff495366341e441451e17bd")}, {username:"korov1"}]})
# or的条件查询
db.user.find({$or:[{username:"korov"},{username:"korov1"}]})

# 更新数据库，找到username为korov1，若set中的属性已经存在则更新，没有则新增
db.user.updateOne({username:"korov1"}, {$set:{country:"china"}})
# 更新所有符合条件的数据
db.user.updateMany({username:"korov1"}, {$set:{country:"china1"}})
# 替换操作，将username这个字段删掉，新增country字段
db.user.findOneAndReplace({username:"korov1"}, {country:"china1"})
# 删掉某个属性
db.user.updateOne({_id:ObjectId("5ff495366341e441451e17bd")}, {$unset:{lalla:"lllll"}})
```

