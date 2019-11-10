# 2 SQL基础

## 2.2 SQL使用入门

### 2.2.1 SQL分类

SQL语句主要可以划分为以下3个类别：

- DDL（Data Definition Languages）语句：数据定义语言，这些语句定义了不同的数据段、数据表、列、索引等数据库对象。常用的语句关键字主要包括create、drop、alter等。
- DML（Data Manipulation Language）语句：数据操纵语句，用于添加、删除、更新和查询数据库记录，并检查数据完整性，常用的语句关键字主要包括insert、delete、udpate 和select 等。
- DCL（Data Control Language）语句：数据控制语句，用于控制不同数据段直接的许可和访问级别的语句。这些语句定义了数据库、表、字段、用户的访问权限和安全级别。主要的语句关键字包括grant、revoke 等。

### 2.2.2 DDL语句

#### 1.创建数据库

CREATE DATABASE dbname;//创建数据库

SHOW DATABASES；// 查看当前所有的数据库

USE dbname;//使用该数据库

#### 2.删除数据库

drop database dbname;

#### 3.创建表

CREATE TABLE tablename (column_name_1 column_type_1 constraints，column_name_2 column_type_2 constraints ， ……column_name_n column_type_nconstraints）

因为MySQL 的表名是以目录的形式存在于磁盘上，所以表名的字符可以用任何目录名允许的字符。column_name 是列的名字，column_type 是列的数据类型，contraints 是这个列的约束条件，在后面的章节中会详细介绍。

表创建完毕后，如果需要查看一下表的定义，可以使用如下命令：DESC tablename

也可以使用更详细的查看语句：SHOW CREATE TABLE tablename;

#### 4.删除表

DROP TABLE tablename

#### 5.修改表

修改表类型

ALTER TABLE tablename MODIFY [COLUMN] column_definition [FIRST | AFTER col_name]

增加表字段

ALTER TABLE tablename ADD [COLUMN] column_definition [FIRST | AFTER col_name]

删除表字段

ALTER TABLE tablename DROP [COLUMN] col_name

字段改名

ALTER TABLE tablename CHANGE [COLUMN] old_col_name column_definition
[FIRST|AFTER col_name]

改表名

ALTER TABLE tablename RENAME [TO] new_tablename

### 2.2.3 DML语句

#### 1.插入记录

```sql
INSERT INTO tablename (field1,field2,……fieldn) VALUES(value1,value2,……valuesn);
```



#### 2.更新记录

```sql
UPDATE tablename SET field1=value1，field2.=value2，……fieldn=valuen [WHERE CONDITION]
```

在MySQL 中，update 命令可以同时更新多个表中数据，语法如下：

```sql
UPDATE t1,t2…tn set t1.field1=expr1,tn.fieldn=exprn [WHERE CONDITION]
```



#### 3.删除记录

DELETE FROM tablename [WHERE CONDITION]

在MySQL 中可以一次删除多个表的数据，语法如下：
DELETE t1,t2…tn FROM t1,t2…tn [WHERE CONDITION]

#### 4.查询记录

SELECT * FROM tablename [WHERE CONDITION]

排序和限制

SELECT * FROM tablename [WHERE CONDITION] [ORDER BY field1 [DESC|ASC] ， field2[DESC|ASC]，……fieldn [DESC|ASC]]

分页查询

SELECT ……[LIMIT offset_start,row_count]

聚合

SELECT [field1,field2,……fieldn] fun_name
FROM tablename
[WHERE where_contition]
[GROUP BY field1,field2,……fieldn
[WITH ROLLUP]]
[HAVING where_contition]

fun_name 表示要做的聚合操作，也就是聚合函数，常用的有sum（求和）、count(*)（记录数）、max（最大值）、min（最小值）。

GROUP BY 关键字表示要进行分类聚合的字段，比如要按照部门分类统计员工数量，部门就应该写在group by 后面。

WITH ROLLUP 是可选语法，表明是否对分类聚合后的结果进行再汇总。

HAVING 关键字表示对分类后的结果再进行条件的过滤。

**表连接**

当需要同时显示多个表中的字段时，就可以用表连接来实现这样的功能。

外连接有分为左连接和右连接，具体定义如下。
左连接：包含所有的左边表中的记录甚至是右边表中没有和它匹配的记录
右连接：包含所有的右边表中的记录甚至是左边表中没有和它匹配的记录

**子查询**

某些情况下，当我们查询的时候，需要的条件是另外一个select 语句的结果，这个时候，就要用到子查询。用于子查询的关键字主要包括in、not in、=、!=、exists、not exists 等。

select * from emp where deptno in(select deptno from dept);

### 2.2.4 DCL语句

```sql
创建一个数据库用户z1，具有对sakila 数据库中所有表的SELECT/INSERT 权限：
grant select,insert on sakila.* to 'z1'@'localhost' identified by '123';
由于权限变更，需要将z1 的权限变更，收回INSERT，只能对数据进行SELECT 操作：
revoke insert on sakila.* from 'z1'@'localhost';
```

## 2.3 帮助的使用

### 2.3.1 按照层次看帮助

```bash
显示所有可供查询的分类
mysql> ? contents
You asked for help about help category: "Contents"
For more information, type 'help <item>', where <item> is one of the following
categories:
   Account Management
   Administration
   Components
   Compound Statements
   Data Definition
   Data Manipulation
   Data Types
   Functions
   Functions and Modifiers for Use with GROUP BY
   Geographic Features
   Help Metadata
   Language Structure
   Plugins
   Storage Engines
   Table Maintenance
   Transactions
   User-Defined Functions
   Utility
```

可以使用“? Data Type”进一步查看感兴趣的分类。

### 2.3.2 快速查阅帮助

用关键字进行快速查询。例如：? show。

# 3 MySQL支持的数据类型

## 3.1 数值类型

MySQL 支持所有标准SQL 中的数值类型，其中包括严格数值类型（INTEGER、SMALLINT、DECIMAL 和NUMERIC），以及近似数值数据类型（FLOAT、REAL 和DOUBLE PRECISION），并在此基础上做了扩展。扩展后增加了TINYINT、MEDIUMINT 和BIGINT 这3 种长度不同的整型，并增加了BIT 类型，用来存放位数据。其中INT 是INTEGER 的同名词，DEC 是DECIMAL 的同名词。

| 整数类型     | 字节 | 最小值                 | 最大值                        |
| ------------ | ---- | ---------------------- | ----------------------------- |
| TINYINT      | 1    | 有符号-128，无符号0    | 有符号 127，无符号 255        |
| SMALLINT     | 2    | 有符号-32768，无符号 0 | 有符号 32767，无符号 65535 |
| MEDIUMINT    | 3    | 有符号-8388608，无符号 0  | 有符号 8388607，无符号 1677215 |
| INT、INTEGER | 4    | 有符号-2147483648，无符号 0 | 有符号 2147483647，无符号 4294967295 |
| BIGINT       | 8    | 有符号-9223372036854775808，无符号 0 | 有符号 9223372036854775807，无符号 18446744073709551615 |

| 浮点数类型 | 字节 | 最小值                   | 最大值                   |
| ---------- | ---- | ------------------------ | ------------------------ |
| FLOAT      | 4    | ±1.175494351E-38         | ±3.402823466E+38         |
| DOUBLE     | 8    | ±2.2250738585072014E-308 | ±1.7976931348623157E+308 |

| 定点数类型             | 字节 | 描述                                                         |
| ---------------------- | ---- | ------------------------------------------------------------ |
| DEC(M,D)，DECIMAL(M,D) | M+2  | 最大取值范围与DOUBLE 相同，给定DECIMAL 的有效取值范围由M 和D决定 |

| 位类型 | 字节 | 最小值 | 最大值  |
| ------ | ---- | ------ | ------- |
| BIT(M) | 1～8 | BIT(1) | BIT(64) |

对于整型数据，MySQL 还支持在类型名称后面的小括号内指定显示宽度，例如int(5)表示当数值宽度小于5 位的时候在数字前面填满宽度，如果不显示指定宽度则默认为int(11)。一般配合zerofill 使用，顾名思义，zerofill 就是用“0”填充的意思，也就是在数字位数不够的空间用字符“0”填满。

## 3.2 日期时间类型

| 日期和时间类型 | 字节 | 最小值              | 最大值              |
| -------------- | ---- | ------------------- | ------------------- |
| DATE           | 4    | 1000-01-01          | 9999-12-31          |
| DATETIME       | 8    | 1000-01-01 00:00:00 | 9999-12-31 23:59:59 |
| TIMESTAMP      | 4    | 19700101080001      | 2038 年的某个时刻   |
| TIME           | 3    | -838:59:59          | 838:59:59           |
| YEAR           | 1    | 1901                | 2155                |

## 3.3 字符串类型

MySQL 包括了CHAR、VARCHAR、BINARY、VARBINARY、BLOB、TEXT、ENUM 和SET 等多种字符串类型。

| 字符串类型     | 字节 | 描述及存储需求                                         |
| -------------- | ---- | ------------------------------------------------------ |
| CHAR（M）      | M    | M 为0～255 之间的整数                                  |
| VARCHAR（M）   |      | M 为0～65535 之间的整数，值的长度+1 个字节             |
| TINYBLOB       |      | 允许长度0～255 字节，值的长度+1 个字节                 |
| BLOB           |      | 允许长度0～65535 字节，值的长度+2 个字节               |
| MEDIUMBLOB     |      | 允许长度0～167772150 字节，值的长度+3 个字节           |
| LONGBLOB       |      | 允许长度 0～4294967295 字节，值的长度+4 个字节         |
| TINYTEXT       |      | 允许长度0～255 字节，值的长度+2 个字节                 |
| TEXT           |      | 允许长度0～65535 字节，值的长度+2 个字节               |
| MEDIUMTEXT     |      | 允许长度0～167772150 字节，值的长度+3 个字节           |
| LONGTEXT       |      | 允许长度 0～4294967295 字节，值的长度+4 个字节         |
| VARBINARY（M） |      | 允许长度0～M 个字节的变长字节字符串，值的长度+1 个字节 |
| BINARY（M）    | M    | 允许长度0～M 个字节的定长字节字符串                    |

# 4 MySQL中的运算符

## 4.1 算术运算符

| 运算符         | 作用           |
| -------------- | -------------- |
| +              | 加法           |
| -              | 减法           |
| *              | 乘法           |
| /,DIV          | 除法，返回商   |
| %,MOD,MOD(a,b) | 除法，返回余数 |

## 4.2 比较运算符

| 运算符         | 作用                       |
| -------------- | -------------------------- |
| =              | 等于                       |
| <>或!=         | 不等于                     |
| <=>            | NULL 安全的等于(NULL-safe) |
| <              | 小于                       |
| <=             | 小于等于                   |
| >              | 大于                       |
| >=             | 大于等于                   |
| BETWEEN        | 存在与指定范围             |
| IN             | 存在于指定集合             |
| IS NULL        | 为NULL                     |
| IS NOT NULL    | 不为NULL                   |
| LIKE           | 通配符匹配                 |
| REGEXP 或RLIKE | 正则表达式匹配             |

**BETWEEN**：select 4 BETWEEN 3 AND 7; 大于等于3，小于等于7。

**IN**：a IN(value1,value2,...)

**LIKE**：a LIKE %123%，当a 中含有字符串“123”时，则返回值为1，否则返回0。

**REGEXP**：str REGEXP str_pat，当str 字符串中含有str_pat相匹配的字符串时，则返回值为1，否则返回0。SELECT 'a' REGEXP '[dfc]','a' REGEXP '[a-z]';

## 4.3 逻辑运算符

| 运算符   | 作用     |
| -------- | -------- |
| NOT 或！ | 逻辑非   |
| AND 或&& | 逻辑与   |
| OR 或    | 逻辑或   |
| XOR      | 逻辑异或 |

“XOR”表示逻辑异或。当任意一个操作数为NULL 时，返回值为NULL。对于非NULL 的操作数，如果两个的逻辑真假值相异，则返回结果1；否则返回0。

## 4.4 位运算符

| 运算符 | 作用             |
| ------ | ---------------- |
| &      | 位与（位AND）    |
| \|     | 位或 （位OR ）   |
| ^      | 位异或（位 XOR） |
| ~      | 位取反           |
| >>     | 位右移           |
| <<     | 位左移           |

## 4.5 运算符的优先级

| 优先级顺序 | 运算符                                             |
| ---------- | -------------------------------------------------- |
| 1          | :=                                                 |
| 2          | \|\|，OR, XOR                                      |
| 3          | &&, AND                                            |
| 4          | NOT                                                |
| 5          | BETWEEN, CASE, WHEN, THEN, ELSE                    |
| 6          | =, <=>, >=, >, <=, <, <>, !=, IS, LIKE, REGEXP, IN |
| 7          | \|                                                 |
| 8          | &                                                  |
| 9          | <<, >>                                             |
| 10         | -, +                                               |
| 11         | *, /, DIV, %, MOD                                  |
| 12         | ^                                                  |
| 13         | - (一元减号), ~ (一元比特反转)                     |
| 14         | !                                                  |

# 5 常用函数

## 5.1 字符串函数

| 函数                  | 功能                                                         |
| --------------------- | ------------------------------------------------------------ |
| CANCAT(S1,S2,…Sn)     | 连接S1,S2,…Sn 为一个字符串                                   |
| INSERT(str,x,y,instr) | 将字符串str 从第x 位置开始，y 个字符长的子串替换为字符串instr |
| LOWER(str)            | 将字符串str 中所有字符变为小写                               |
| UPPER(str)            | 将字符串str 中所有字符变为大写                               |
| LEFT(str ,x)          | 返回字符串str 最左边的x 个字符                               |
| RIGHT(str,x)          | 返回字符串str 最右边的x 个字符                               |
| LPAD(str,n ,pad)      | 用字符串pad 对str 最左边进行填充，直到长度为n 个字符长度     |
| RPAD(str,n,pad)       | 用字符串pad 对str 最右边进行填充，直到长度为n 个字符长度     |
| LTRIM(str)            | 去掉字符串str 左侧的空格                                     |
| RTRIM(str)            | 去掉字符串str 行尾的空格                                     |
| REPEAT(str,x)         | 返回str 重复x 次的结果                                       |
| REPLACE(str,a,b)      | 用字符串b 替换字符串str 中所有出现的字符串a                  |
| STRCMP(s1,s2)         | 比较字符串s1 和s2                                            |
| TRIM(str)             | 去掉字符串行尾和行头的空格                                   |
| SUBSTRING(str,x,y)    | 返回从字符串str x 位置起y 个字符长度的字串                   |

## 5.2 数值函数

| 函数          | 功能                                 |
| ------------- | ------------------------------------ |
| ABS(x)        | 返回x 的绝对值                       |
| CEIL(x)       | 返回大于x 的最大整数值               |
| FLOOR(x)      | 返回小于x 的最大整数值               |
| MOD(x，y)     | 返回x/y 的模                         |
| RAND()        | 返回0 到1 内的随机值                 |
| ROUND(x,y)    | 返回参数x 的四舍五入的有y 位小数的值 |
| TRUNCATE(x,y) | 返回数字x 截断为y 位小数的结果       |

## 5.3 日期和时间函数

| 函数                              | 功能                                         |
| --------------------------------- | -------------------------------------------- |
| CURDATE()                         | 返回当前日期                                 |
| CURTIME()                         | 返回当前时间                                 |
| NOW()                             | 返回当前的日期和时间                         |
| UNIX_TIMESTAMP(date)              | 返回日期date 的UNIX 时间戳                   |
| FROM_UNIXTIME                     | 返回UNIX 时间戳的日期值                      |
| WEEK(date)                        | 返回日期date 为一年中的第几周                |
| YEAR(date)                        | 返回日期date 的年份                          |
| HOUR(time)                        | 返回time 的小时值                            |
| MINUTE(time)                      | 返回time 的分钟值                            |
| MONTHNAME(date)                   | 返回date 的月份名                            |
| DATE_FORMAT(date,fmt)             | 返回按字符串fmt 格式化日期date 值            |
| DATE_ADD(date,INTERVAL expr type) | 返回一个日期或时间值加上一个时间间隔的时间值 |
| DATEDIFF(expr,expr2)              | 返回起始时间expr 和结束时间expr2 之间的天数  |

## 5.4 流程函数

| 函数                                                    | 功能                                              |
| ------------------------------------------------------- | ------------------------------------------------- |
| IF(value,t,f)                                           | 如果value 是真，返回t；否则返回f                  |
| IFNULL(value1,value2)                                   | 如果value1 不为空返回value1，否则返回value2       |
| CASE WHEN [value1]THEN[result1]…ELSE[default]END        | 如果value1 是真，返回result1，否则返回default     |
| CASE [expr] WHEN [value1]THEN[result1]…ELSE[default]END | 如果expr 等于value1，返回result1，否则返回default |

## 5.5 其他常用函数

| 函数           | 功能                                                    |
| -------------- | ------------------------------------------------------- |
| DATABASE()     | 返回当前数据库名                                        |
| VERSION()      | 返回当前数据库版本                                      |
| USER()         | 返回当前登录用户名                                      |
| INET_ATON(IP)  | 返回IP 地址的数字表示。select INET_ATON('192.168.1.1'); |
| INET_NTOA(num) | 返回数字代表的IP 地址。                                 |
| PASSWORD(str)  | 返回字符串str 的加密版本                                |
| MD5()          | 返回字符串str 的MD5 值                                  |

# 7 表类型（存储引擎）的选择

## 7.1 MySQL存储引擎概述

MySQL 5.0 支持的存储引擎包括MyISAM、InnoDB、BDB、MEMORY、MERGE、EXAMPLE、NDB Cluster、ARCHIVE、CSV、BLACKHOLE、FEDERATED 等，其中InnoDB 和BDB 提供事务安全表，其他存储引擎都是非事务安全表。

show engines \G;可以查询当前版本支持的数据库引擎。

## 7.2 各种存储引擎的特性

![image-20191110214221567](picture\image-20191110214221567.png)

### 7.2.1 MyISAM

MyISAM 不支持事务、也不支持外键，其优势是访问的速度快，对事务完整性没有要求或者以SELECT、INSERT 为主的应用基本上都可以使用这个引擎来创建表。

每个MyISAM 在磁盘上存储成3 个文件，其文件名都和表名相同，但扩展名分别是：

- .frm（存储表定义）
- .MYD（MYData，存储数据）
- .MYI（MYIndex，存储索引）。

要指定索引文件和数据文件的路径，需要在创建表的时候通过DATA DIRECTORY 和INDEXDIRECTORY 语句指定，也就是说不同MyISAM 表的索引文件和数据文件可以放置到不同的路径下。文件路径需要是绝对路径，并且具有访问权限。

MyISAM 类型的表可能会损坏，原因可能是多种多样的，损坏后的表可能不能访问，会提示需要修复或者访问后返回错误的结果。MyISAM 类型的表提供修复的工具，可以用CHECKTABLE 语句来检查MyISAM 表的健康，并用REPAIR TABLE 语句修复一个损坏的MyISAM 表。表损坏可能导致数据库异常重新启动，需要尽快修复并尽可能地确认损坏的原因。

MyISAM 的表又支持3 种不同的存储格式，分别是：

- 静态（固定长度）表
- 动态表
- 压缩表

### 7.2.2 InnoDB

InnoDB 存储引擎提供了具有提交、回滚和崩溃恢复能力的事务安全。但是对比MyISAM的存储引擎，InnoDB 写的处理效率差一些并且会占用更多的磁盘空间以保留数据和索引。

#### 1.自动增长列

对于InnoDB 表，自动增长列必须是索引。如果是组合索引，也必须是组合索引的第一列，但是对于MyISAM 表，自动增长列可以是组合索引的其他列，这样插入记录后，自动增长列是按照组合索引的前面几列进行排序后递增的。

#### 2.外键约束

MySQL 支持外键的存储引擎只有InnoDB，在创建外键的时候，要求父表必须有对应的索引，子表在创建外键的时候也会自动创建对应的索引。

在创建索引的时候，可以指定在删除、更新父表时，对子表进行的相应操作，包RESTRICT、CASCADE、SET NULL 和NO ACTION。其中RESTRICT 和NO ACTION 相同，是指限制在子表有关联记录的情况下父表不能更新；CASCADE 表示父表在更新或者删除时，更新或者删除子表对应记录；SET NULL 则表示父表在更新或者删除的时候，子表的对应字段被SET NULL。选择后两种方式的时候要谨慎，可能会因为错误的操作导致数据的丢失。

当某个表被其他表创建了外键参照，那么该表的对应索引或者主键禁止被删除。

#### 3.存储方式

InnoDB 存储表和索引有以下两种方式：

- 使用共享表空间存储，这种方式创建的表的表结构保存在.frm 文件中，数据和索引保存在innodb_data_home_dir 和innodb_data_file_path 定义的表空间中，可以是多个文件。
- 使用多表空间存储，这种方式创建的表的表结构仍然保存在.frm 文件中，但是每个表的数据和索引单独保存在.ibd 中。如果是个分区表，则每个分区对应单独的.ibd文件，文件名是“表名+分区名”，可以在创建分区的时候指定每个分区的数据文件的位置，以此来将表的IO 均匀分布在多个磁盘上。

### 7.2.3 MEMORY

MEMORY 存储引擎使用存在内存中的内容来创建表。每个MEMORY 表只实际对应一个磁盘文件，格式是.frm。MEMORY 类型的表访问非常得快，因为它的数据是放在内存中的，并且默认使用HASH 索引，但是一旦服务关闭，表中的数据就会丢失掉。

# 8 选择合适的数据类型

## 8.1 CHAR和VARCHAR

CHAR属于固定长度的字符类型，而VARCHAR 属于可变长度的字符类型。

CHAR的处理速度很快，但是浪费空间。对于那些长度变化不大并且对查询速度有较高要求的数据可以考虑使用CHAR 类型来存储。

MyISAM 存储引擎：建议使用固定长度的数据列代替可变长度的数据列。

MEMORY 存储引擎：目前都使用固定长度的数据行存储，因此无论使用CHAR 或VARCHAR 列都没有关系。两者都是作为CHAR 类型处理。

InnoDB 存储引擎：建议使用VARCHAR 类型。对于InnoDB 数据表，内部的行存储格式没有区分固定长度和可变长度列（所有数据行都使用指向数据列值的头指针），因此在本质上，使用固定长度的CHAR 列不一定比使用可变长度VARCHAR 列性能要好。因而，主要的性能因素是数据行使用的存储总量。由于CHAR 平均占用的空间多于VARCHAR，因此使用VARCHAR 来最小化需要处理的数据行的存储总量和磁盘I/O 是比较好的。

## 8.2 TEXT与BLOB

需要保存大文本的时候才使用。二者之间的主要差别是BLOB 能用来保存二进制数据，比如照片；而TEXT 只能保存字符数据，比如一篇文章或者日记。

BLOB 和TEXT 值会引起一些性能问题，特别是在执行了大量的删除操作时。

删除操作会在数据表中留下很大的“空洞”，以后填入这些“空洞”的记录在插入的性能上会有影响。为了提高性能，建议定期使用OPTIMIZE TABLE 功能对这类表进行碎片整理，避免因为“空洞”导致性能问题。

## 8.3 浮点数与定点数

浮点数一般用于表示含有小数部分的数值。当一个字段被定义为浮点类型后，如果插入数据的精度超过该列定义的实际精度，则插入值会被四舍五入到实际定义的精度值，然后插入，四舍五入的过程不会报错。

定点数不同于浮点数，定点数实际上是以字符串形式存放的，所以定点数可以更加精确的保存数据。如果实际插入的数值精度大于实际定义的精度，则MySQL 会进行警告（默认的SQLMode 下），但是数据按照实际精度四舍五入后插入；如果SQLMode 是在TRADITIONAL（传统模式）下，则系统会直接报错，导致数据无法插入。

## 8.4 日期类型选择

根据实际需要选择能够满足应用的最小存储的日期类型。如果应用只需要记录“年份”，那么用1 个字节来存储的YEAR 类型完全可以满足，而不需要用4 个字节来存储的DATE 类型。这样不仅仅能节约存储，更能够提高表的操作效率。

如果要记录年月日时分秒，并且记录的年份比较久远，那么最好使用DATETIME，而不要使用TIMESTAMP。因为TIMESTAMP 表示的日期范围比DATETIME 要短得多。

如果记录的日期需要让不同时区的用户使用，那么最好使用TIMESTAMP，因为日期类型中只有它能够和实际时区相对应。

# 9 字符集

# 10 索引的设计和使用

## 10.1 索引概述

所有MySQL 列类型都可以被索引，对相关列使用索引是提高SELECT 操作性能的最佳途径。根据存储引擎可以定义每个表的最大索引数和最大索引长度，每种存储引擎（如MyISAM、InnoDB、BDB、MEMORY 等）对每个表至少支持16 个索引，总索引长度至少为256 字节。大多数存储引擎有更高的限制。

MyISAM 和InnoDB 存储引擎的表默认创建的都是BTREE 索引。MySQL 目前还不支持函数索引，但是支持前缀索引，即对索引字段的前N 个字符创建索引。前缀索引的长度跟存储引擎相关，对于MyISAM 存储引擎的表，索引的前缀长度可以达到1000 字节长，而对于InnoDB 存储引擎的表，索引的前缀长度最长是767 字节。请注意前缀的限制应以字节为单位进行测量，而CREATE TABLE 语句中的前缀长度解释为字符数。在为使用多字节字符集的列指定前缀长度时一定要加以考虑。

MySQL 中还支持全文本（FULLTEXT）索引，该索引可以用于全文搜索。

```sql
CREATE [UNIQUE|FULLTEXT|SPATIAL] INDEX index_name
[USING index_type]
ON tbl_name (index_col_name,...)
index_col_name:
col_name [(length)] [ASC | DESC]

// 删除索引
DROP INDEX index_name ON tbl_name
```

## 10.2 设计索引的原则

- 搜索的索引列，不一定是所要选择的列。换句话说，最适合索引的列是出现在WHERE子句中的列，或连接子句中指定的列，而不是出现在SELECT 关键字后的选择列表中的列。
- 使用惟一索引。考虑某列中值的分布。索引的列的基数越大，索引的效果越好。例如，存放出生日期的列具有不同值，很容易区分各行。而用来记录性别的列，只含有“ M”和“F”，则对此列进行索引没有多大用处，因为不管搜索哪个值，都会得出大约一半的行。
- 使用短索引。如果对字符串列进行索引，应该指定一个前缀长度，只要有可能就应该这样做。
- 利用最左前缀。在创建一个n 列的索引时，实际是创建了MySQL 可利用的n 个索引。多列索引可起几个索引的作用，因为可利用索引中最左边的列集来匹配行。这样的列集称为最左前缀。
- 不要过度索引。每个额外的索引都要占用额外的磁盘空间，并降低写操作的性能。在修改表的内容时，索引必须进行更新，有时可能需要重构，因此，索引越多，所花的时间越长。
- 对于InnoDB 存储引擎的表，记录默认会按照一定的顺序保存，如果有明确定义的主键，则按照主键顺序保存。如果没有主键，但是有唯一索引，那么就是按照唯一索引的顺序保存。如果既没有主键又没有唯一索引，那么表中会自动生成一个内部列，按照这个列的顺序保存。按照主键或者内部列进行的访问是最快的，所以InnoDB 表尽量自己指定主键，当表中同时有几个列都是唯一的，都可以作为主键的时候，要选择最常作为访问条件的列作为主键，提高查询的效率。另外，还需要注意，InnoDB 表的普通索引都会保存主键的键值，所以主键要尽可能选择较短的数据类型，可以有效地减少索引的磁盘占用，提高索引的缓存效果。

## 10.3 BTREE和HASH

HASH 索引有一些重要的特征需要在使用的时候特别注意，如下所示。

- 只用于使用=或<=>操作符的等式比较
- 优化器不能使用HASH 索引来加速ORDER BY 操作。
- MySQL 不能确定在两个值之间大约有多少行。如果将一个MyISAM 表改为HASH 索引的MEMORY 表，会影响一些查询的执行效率。
- 只能使用整个关键字来搜索一行

而对于BTREE 索引，当使用>、<、>=、<=、BETWEEN、!=或者<>，或者LIKE 'pattern'（其中'pattern'不以通配符开始）操作符时，都可以使用相关列上的索引。