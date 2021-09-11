#  SQL语法

## 标识符和关键词

SELECT，UPDATE等是关键字，表名和列名时标识符。系统中一个标识符的长度不能超过NAMEDATALEN-1，默认的NAMEDATALEN值为64,可以在`src/include/pg_config_manual.h`中修改值的大小。

关键词和不被引号修饰的标识符是大小写不敏感的。被引号修饰的标识符是大小写敏感的。如果希望写一个可移植的应用，我们应该总是用引号修饰一个特定名字或者从不使用引号修饰。

在MySQL中使用反引号包裹表名和字段名，在PostgresSQL中使用双引号包裹表明或者字段名。

# 性能提示

## EXPLAIN

```
#预估使用时间
explain select *
from demo where name = 'minions';
#实际使用时间
explain analyze select *
from demo where name = 'minions';
```



# 索引

### 索引的分类

在PostgreSQL中，支持以下几类索引

- B-tree：最常用的索引，适合处理等值查询和范围查询
- Hash：只能处理简单的等值查询
- GiST：不是一种单独的索引类型，而是一种架构，可以在这种架构上实现很多不同的索引策略。GiST索引定义的特定操作符可以用于特定索引策略。

# 创建用户

```sql
CREATE USER testUser WITH PASSWORD '*****';
GRANT ALL PRIVILEGES ON DATABASE testDB TO testUser;
GRANT ALL PRIVILEGES ON all tables in schema public TO testUser;

REVOKE ALL PRIVILEGES ON DATABASE testDB FROM testUser;
```

```sql
1、查看某用户的表权限
select * from information_schema.table_privileges where grantee='user_name';
2、查看usage权限表
select * from information_schema.usage_privileges where grantee='user_name';
3、查看存储过程函数相关权限表
select * from information_schema.routine_privileges where grantee='user_name';
```

