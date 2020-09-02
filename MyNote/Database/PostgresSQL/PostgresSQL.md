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