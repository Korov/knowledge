# Postgres对比MySQL

## 支持主流的多表连接查询

### nest loop

嵌套循环的方式，一个循环嵌套另一个循环，没有索引的时候的复杂度是`O(m*n)`，这样的复杂度对于大数据集是非常劣势的，解决办法是通过索引来提升性能

### sort merge-join

首先对两个表按照关联的字段进行排序，分别从两个表中取出一行数据进行匹配，如果合适就放入结果集；不匹配将较小的那行丢掉继续匹配另一个表的下一行，依次处理直到将两表的数据取完。merge join很大一部分开销花在排序上，也是同等条件下差于hash join的一个主要原因。

对于本身就需要排序的数据来说这种连接方式更适合

### hash join

将两表中较小的表生成hash表，然后根据关联条件用大表中的值取hash表中get数据，这个很快可以得到对应的结果集，这个hash表是放在内存中的，如果内存中放不下则dump到外存。

但是只适用于等值连接，对于>,<这些范围连接条件还是用nest loop连接。

个人认为这个hash表需要临时创建，如果我提前建立好索引我的速度应该是快于hash join的，但是如果hash表是放在内存的是不是有速度加成，这个需要验证一下。

## 在线操作功能

MySQL表中加列，基本上是新建一个表，建索引时也会锁定整张表

Postgres增加空值的列时，本质上只是在系统表上把列定义上，无须对物理结构做更新，加列可以瞬间完成，支持建索引的过程中不锁定更新操作

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

