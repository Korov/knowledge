# Oracle用户

oracle用户的概念对于Oracle数据库至关重要，在现实环境当中一个服务器一般只会安装一个Oracle实例，一个Oracle用户代表着一个用户群，他们通过该用户登录数据库，进行数据库对象的创建、查询等开发。

每一个用户对应着该用户下的N多对象，因此，在实际项目开发过程中，不同的项目组使用不同的Oracle用户进行开发，不相互干扰。也可以理解为一个Oracle用户既是一个业务模块，这些用户群构成一个完整的业务系统，不同模块间的关联可以通过Oracle用户的权限来控制，来获取其它业务模块的数据和操作其它业务模块的某些对象。

## 创建用户

```sql
-- Create the user 
create user student--用户名
  identified by "123456"--密码
  default tablespace USERS--表空间名
  temporary tablespace temp --临时表空间名
  profile DEFAULT    --数据文件（默认数据文件）
  account unlock; -- 账户是否解锁（lock:锁定、unlock解锁）
```

通过上面语句，可以创建一个student用户，但是该用户现在还不能登录数据库，因为它没有登录数据库权限，最少他需要一个create session系统权限才能登录数据库。

## 用户权限

Oracle数据库用户权限分为：系统权限和对象权限两种：

- 系统权限：比如：create session可以和数据库进行连接权限、create table、create view 等具有创建数据库对象权限。
- 对象权限：比如：对表中数据进行增删改查操作，拥有数据库对象权限的用户可以对所拥有的对象进行相应的操作。

## 数据库角色

oracle数据库角色是若干系统权限的集合，给Oracle用户进行授数据库角色，就是等于赋予该用户若干数据库系统权限。常用的数据库角色如下：

CONNECT角色：connect角色是Oracle用户的基本角色，connect权限代表着用户可以和Oracle服务器进行连接，建立session（会 话）。

RESOURCE角色：resouce角色是开发过程中常用的角色。 RESOURCE给用户提供了可以创建自己的对象，包括：表、视图、序列、过程、触发器、索引、包、类型等。

DBA角色：DBA角色是管理数据库管理员该有的角色。它拥护系统了所有权限，和给其他用户授权的权限。SYSTEM用户就具有DBA权限。

语法：授权

```sql
--GRANT 对象权限 on 对象 TO 用户    
grant select, insert, update, delete on JSQUSER to STUDENT;
 
--GRANT 系统权限 to 用户
grant select any table to STUDENT;
 
--GRANT 角色 TO 用户
grant connect to STUDENT;--授权connect角色
grant resource to STUDENT;--授予resource角色
```

语法：取消用户权限

```sql
-- Revoke 对象权限 on 对象 from 用户 
revoke select, insert, update, delete on JSQUSER from STUDENT;
 
-- Revoke 系统权限  from 用户
revoke SELECT ANY TABLE from STUDENT;
 
-- Revoke 角色（role） from 用户
revoke RESOURCE from STUDENT;
```

语法：Oracle用户的其他操作

```sql
--修改用户信息
alter user STUDENT
  identified by ******  --修改密码
  account lock;--修改用户处于锁定状态或者解锁状态 （LOCK|UNLOCK ）
```

# 案例所需表结构

```sql
-- Create table
create table STUINFO
(
  stuid      VARCHAR2(11) not null,
  stuname    VARCHAR2(50) not null,
  sex        CHAR(1) not null,
  age        NUMBER(2) not null,
  classno    VARCHAR2(7) not null,
  stuaddress VARCHAR2(100) default '地址未录入',
  grade      CHAR(4) not null,
  enroldate  DATE,
  idnumber   VARCHAR2(18) default '身份证未采集' not null
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table STUINFO
  is '学生信息表';
-- Add comments to the columns 
comment on column STUINFO.stuid
  is '学号';
comment on column STUINFO.stuname
  is '学生姓名';
comment on column STUINFO.sex
  is '学生性别';
comment on column STUINFO.age
  is '学生年龄';
comment on column STUINFO.classno
  is '学生班级号';
comment on column STUINFO.stuaddress
  is '学生住址';
comment on column STUINFO.grade
  is '年级';
comment on column STUINFO.enroldate
  is '入学时间';
comment on column STUINFO.idnumber
  is '身份证号';
-- Create/Recreate primary, unique and foreign key constraints 
alter table STUINFO
  add constraint PK_STUINFO primary key (STUID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
   
  -- Create table
create table CLASS
(
  classno        VARCHAR2(7) not null,
  classname      VARCHAR2(50),
  monitorid      VARCHAR2(11),
  monitorname    VARCHAR2(50),
  headmasterid   VARCHAR2(8),
  headmastername VARCHAR2(50),
  classaddress   VARCHAR2(50),
  enterdate      DATE
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table CLASS
  is '班级信息表';
-- Add comments to the columns 
comment on column CLASS.classno
  is '班级号';
comment on column CLASS.classname
  is '班级名称';
comment on column CLASS.monitorid
  is '班长学号';
comment on column CLASS.monitorname
  is '班长姓名';
comment on column CLASS.headmasterid
  is '班主任教师号';
comment on column CLASS.headmastername
  is '班主任姓名';
comment on column CLASS.classaddress
  is '班级地址';
comment on column CLASS.enterdate
  is '录入时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table CLASS
  add constraint PK_CLASS primary key (CLASSNO)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255;
 
 
-- Create table
create table COURSE
(
  courseid   VARCHAR2(9) not null,
  schyear    VARCHAR2(4),
  term       VARCHAR2(4),
  coursename VARCHAR2(100)
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table COURSE
  is '课程表';
-- Add comments to the columns 
comment on column COURSE.courseid
  is '课程id';
comment on column COURSE.schyear
  is '学年';
comment on column COURSE.term
  is '学期';
comment on column COURSE.coursename
  is '课程名称';
-- Create/Recreate primary, unique and foreign key constraints 
alter table COURSE
  add constraint PK_COURSE primary key (COURSEID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
   
  -- Create table
create table STUCOURSE
(
  selectid   VARCHAR2(18) not null,
  stuid      VARCHAR2(11),
  courseid   VARCHAR2(9),
  schyear    VARCHAR2(4),
  term       VARCHAR2(4),
  redo       VARCHAR2(1),
  selectdate DATE
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table STUCOURSE
  is '学生选课表';
-- Add comments to the columns 
comment on column STUCOURSE.selectid
  is '选课id';
comment on column STUCOURSE.stuid
  is '学号';
comment on column STUCOURSE.courseid
  is '课程id';
comment on column STUCOURSE.schyear
  is '年度';
comment on column STUCOURSE.term
  is '学期';
comment on column STUCOURSE.redo
  is '是否重修';
comment on column STUCOURSE.selectdate
  is '选课时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table STUCOURSE
  add constraint PK_STUCOURSE primary key (SELECTID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255;
 
 
-- Create table
create table SCORE
(
  scoreid  VARCHAR2(18) not null,
  stuid    VARCHAR2(11),
  courseid VARCHAR2(9),
  score    NUMBER,
  scdate   DATE
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table SCORE
  is '学生成绩表';
-- Add comments to the columns 
comment on column SCORE.scoreid
  is '学生成绩id';
comment on column SCORE.stuid
  is '学生学号';
comment on column SCORE.courseid
  is '课程id(年度+上下学期+课程序列)';
comment on column SCORE.score
  is '成绩';
comment on column SCORE.scdate
  is '成绩录入时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table SCORE
  add constraint PK_SCORE primary key (SCOREID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
```

# oracle语句

## 建表

### Oracle字段数据类型

常用的Oracle列字段的数据类型如下：

| **数据类型 **    | **类型解释 **                                                |
| ---------------- | ------------------------------------------------------------ |
| VARCHAR2(length) | 字符串类型：存储可变的长度的字符串，length:是字符串的最大长度，默认不填的时候是1，最大长度不超过4000。 |
| CHAR(length)     | 字符串类型：存储固定长度的字符串，length:字符串的固定长度大小，默认是1，最大长度不超过2000。 |
| NUMBER(a,b)      | 数值类型：存储数值类型，可以存整数，也可以存浮点型。a代表数值的最大位数：包含小数位和小数点，b代表小数的位数。例子：number(6,2)，输入123.12345，实际存入：123.12 。number(4,2)，输入12312.345，实际春如：提示不能存入，超过存储的指定的精度。 |
| DATA             | 时间类型：存储的是日期和时间，包括年、月、日、时、分、秒。例子：内置函数sysdate获取的就是DATA类型 |
| TIMESTAMP        | 时间类型：存储的不仅是日期和时间，还包含了时区。例子：内置函数systimestamp获取的就是timestamp类型 |
| CLOB             | 大字段类型：存储的是大的文本，比如：非结构化的txt文本，字段大于4000长度的字符串。 |
| BLOB             | 二进制类型：存储的是二进制对象，比如图片、视频、声音等转换过来的二进制对象 |

### create table语句

```sql
-- Create table
create table STUDENT.stuinfo
(
  stuid      varchar2(11) not null,--学号：'S'+班号（7位数）+学生序号（3位数）(1)
  stuname    varchar2(50) not null,--学生姓名
  sex        char(1) not null,--性别
  age        number(2) not null,--年龄
  classno    varchar2(7) not null,--班号：'C'+年级（4位数）+班级序号（2位数）
  stuaddress varchar2(100) default '地址未录入',--地址 (2)
  grade      char(4) not null,--年级
  enroldate  date,--入学时间
  idnumber   varchar2(18) default '身份证未采集' not null--身份证
)
tablespace USERS --(3)
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table STUDENT.stuinfo --(4)
  is '学生信息表';
-- Add comments to the columns 
comment on column STUDENT.stuinfo.stuid -- (5)
  is '学号';
comment on column STUDENT.stuinfo.stuname
  is '学生姓名';
comment on column STUDENT.stuinfo.sex
  is '学生性别';
comment on column STUDENT.stuinfo.age
  is '学生年龄';
comment on column STUDENT.stuinfo.classno
  is '学生班级号';
comment on column STUDENT.stuinfo.stuaddress
  is '学生住址';
comment on column STUDENT.stuinfo.grade
  is '年级';
comment on column STUDENT.stuinfo.enroldate
  is '入学时间';
comment on column STUDENT.stuinfo.idnumber
  is '身份证号';
  
-- 添加约束
-- Create/Recreate primary, unique and foreign key constraints 
alter table STUDENT.STUINFO
  add constraint pk_stuinfo_stuid primary key (STUID);
  --把stuid当做主键，主键字段的数据必须是唯一性的（学号是唯一的）
   
-- Create/Recreate check constraints 
alter table STUDENT.STUINFO
  add constraint ch_stuinfo_age
  check (age>0 and age<=50);--给字段年龄age添加约束，学生的年龄只能0-50岁之内的
   
alter table STUDENT.STUINFO
  add constraint ch_stuinfo_sex
  check (sex='1' or sex='2');
   
alter table STUDENT.STUINFO
  add constraint ch_stuinfo_GRADE
  check (grade>='1900' and grade<='2999');
```

（1）处： not null 表示学号字段（stuid）不能为空。

（2）处：default 表示字段stuaddress不填时候会默认填入‘地址未录入’值。

（3）处：表示表stuinfo存储的表空间是users，storage表示存储参数：区段(extent)一次扩展64k，最小区段数为1，最大的区段数不限制。

（4）处：comment on table 是给表名进行注释。

（5）处：comment on column 是给表字段进行注释。

## 查询（select） 

```sql
select *|列名|表达式 from 表名 where 条件 order by 列名
```

备份查询数据命令结构

```sql
create table 表名 as select 语句

create table student.stuinfo_2018 as select * from student.stuinfo ;
select * from student.stuinfo_2018;
```

## 插入（insert into）

insert 命令结构：

```sql
insert into 表名（列名1,列名2,列名3.....）values(值1,值2,值3.....);
```

insert插入一个select的结果集

```sql
 INSERT INTO 表 SELECT 子句;
 
delete  from student.stuinfo t where t.stuid in (select b.stuid from student.stuinfo_2018 b );
insert into student.stuinfo select * from student.stuinfo_2018;
select * from student.stuinfo;
```

## 更新（update）

update命令结构：

```sql
update 表名 set 列名1=值1,列名2=值2,列名3=值3..... where 条件

update student.stuinfo t
   set t.age = '24', t.idnumber = '3503021994XXXXXXXX'
 where t.stuname = '张三';
commit;
select * from student.stuinfo t where t.stuname='张三';
```

update 利用另外一张表关联更新本表数据的命令结构如下：

```sql
update 表1 set 列名=（select 列名 from 表2 where 表1.列名=表2.列名） 
       where exists (select 1 from 表2 where 表1.列名=表2.列名)
       
       
update student.stuinfo t
   set (age, idnumber) =
       (select age, idnumber from student.stuinfo_2018 b where b.stuid = t.stuid)
 where exists (select 1
          from student.stuinfo_2018 b
         where b.stuid = t.stuid
           and b.stuname = '张三');
```

## 删除（delete）

delete命令结构：

```sql
delete from 表名 where 条件
```

truncate命令也是数据删除命令，他是直接把Oracle表数据一次删除的命令，truncate命令是一个DDL命令，不同于delete是DML命令。

```sql
truncate table 表名；
```

# Oracle运算符

## Oracle算术运算符

Oracle算术运算符包括+、-、*、/四个，其中/获得的结果是浮点数。

求2018年上学期数学的平均成绩

```sql
select a.*, b.coursename, c.stuname
  from score a, course b, stuinfo c
 where a.courseid = b.courseid
   and a.stuid = c.stuid;
 
select b.coursename, sum(a.score) / count(1)
  from score a, course b
 where a.courseid = b.courseid
   and a.courseid = 'R20180101'
 group by b.coursename;
```

## Oracle关系运算符

| 符号   | 解释     |
| ------ | -------- |
| =      | 等于     |
| >      | 大于     |
| <      | 小于     |
| <>或!= | 不等于   |
| >=     | 大于等于 |
| <=     | 小于等于 |

## Oracle逻辑运算符

Oracle的逻辑运算符有三个：AND、OR、NOT。

## Oracle字符串连接符||

Oracle中利用字符串连接符||（即双竖线）来连接查询结果

```sql
select '姓名：' || c.stuname || ', 课程：' || b.coursename || ', 成绩：' || a.score || '分。' as sxcj
  from score a, course b, stuinfo c
 where a.courseid = b.courseid
   and a.stuid = c.stuid
```

# Oracle 条件查询

Oracle条件查询时经常使用=、IN、LIKE、BETWEEN...AND来作为条件查询的操作符。使用与mysql一致。

# Oracle集合运算

Oracle集合运算就是把多个查询结果组合成一个查询结果，oralce的集合运算包括：INTERSECT(交集)、UINION ALL(交集重复)、UINION(交集不重复)、MINUS(补集)。

1、INTERSECT(交集)，返回两个查询共有的记录。

2、UNION ALL(并集重复)，返回各个查询的所有记录，包括重复记录。

3、UNION(并集不重复)，返回各个查询的所有记录，不包括重复记录 （重复的记录只取一条）。

4、MINUS(补集)，返回第一个查询检索出的记录减去第二个查询检索出的记录之后剩余的记录。 

当我们使用Oracle集合运算时，要注意每个独立查询的字段名的列名尽量一致（列名不同时，取第一个查询的列名）、列的数据类型、列的个数要一致，不然会报错。

## INTERSECT(交集)：

```sql
select * from stuinfo 
intersect
select * from stuinfo_2018;
```

## UNION ALL(并集重复)

```sql
select * from stuinfo 
union all
select * from stuinfo_2018;
```

## UNION(并集不重复)

```sql
select * from stuinfo 
union 
select * from stuinfo_2018;
```

## MINUS(补集)

```sql
select * from stuinfo 
minus
select * from stuinfo_2018;
```

# Oracle连接查询

Oracle连接查询，包含内关联(inner jion )和外关联(outer  join)，其中外关联又分为左外关联（left outer join）、右外关联（right outer join）和全外关联（full  outer join）其中外关联可以使用（+）来表示。

## 内连接

Oracle内连接：两张表通过某个字段进行内关联，查询结果是通过该字段按关系运算符匹配出的数据行。其中可以包括：

1、等值连接：在连接条件中使用等于号(=)运算符比较被连接列的列值，其查询结果中列出被连接表中的所有列。

2、不等连接：在连接条件使用除等于运算符以外的其它比较运算符比较被连接的列的列值，这些关系运算符包括>、>=、<=、!>、!<、<>。

```sql
select a.stuid,
       a.stuname,
       a.classno,
       b.classno,
       b.classname,
       b.monitorid,
       b.monitorname,
       b.classaddress
  from stuinfo a, class b
 where a.classno = b.classno;
```

## 外连接：

外连接，返回到查询结果集合中的不仅包含符合连接条件的行，而且还包括左表(左外连接或左连接))、右表(右外连接或右连接)或两个边接表(全外连接)中的所有数据行。

1、left join(左联接)等价于（left outer join）返回包括左表中的所有记录和右表中联结字段相等的记录。没有匹配上的null填充

2、right join(右联接)等价于（right outer join）返回包括右表中的所有记录和左表中联结字段相等的记录。没有匹配上的null填充

3.、full join （全连接）等价于（full outer join）查询结果等于左外连接和右外连接的和。没有匹配上的null填充

```sql
--左外连接（stuinfo表中数据都存在,stuinfo_2018不在stuinfo中存在的学生相关字段为null值）
select a.*, b.stuid, b.stuname
  from stuinfo a
  left join stuinfo_2018 b
    on a.stuid = b.stuid;
--左外连接（利用（+）在右边）另外一种写法
select a.*, b.stuid, b.stuname
  from stuinfo a,stuinfo_2018 b 
  where a.stuid=b.stuid(+);
  
  --右外连接（stuinfo_2018表中数据都存在,stuinfo不在stuinfo_2018存在的学生相关字段为null值）
select a.*, b.stuid, b.stuname
  from stuinfo a
  right join stuinfo_2018 b
    on a.stuid = b.stuid;
--右外连接（利用（+）在左边）另外一种写法
select a.*, b.stuid, b.stuname
  from stuinfo a,stuinfo_2018 b 
  where a.stuid(+)=b.stuid;
  
   --(全外连接（stuinfo、stuinfo_2018表中数据都存在,
 --stuinfo不在stuinfo_2018存在的学生相关字段为null值,
 --stuinfo_2018不在stuinfo存在的学生相关字段为null值）
select a.*, b.stuid, b.stuname
  from stuinfo a
  full join stuinfo_2018 b
    on a.stuid = b.stuid;
```

# Oracle伪列

Oracle的伪列是Oracle表在存储的过程中或查询的过程中，表会有一些附加列，称为伪列。伪列就像表中的字段一样，但是表中并不存储。伪列只能查询，不能增删改。Oracle的伪列有：rowid、rownum

## ORACLE ROWID

Oracle表中的每一行在数据文件中都有一个物理地址， ROWID  伪列返回的就是该行的物理地址。使用 ROWID 可以快速的定位表中的某一行。 ROWID 值可以唯一的标识表中的一行。通过Oracle  select 查询出来的ROWID，返回的就是该行数据的物理地址。

```sql
select t.*,t.rowid from stuinfo t ;
select t.*,t.rowid from stuinfo t where t.rowid='AAAShjAAEAAAAEFAAD';
```

## ORACLE ROWNUM

ORACLE ROWNUM表示的Oracle查询结果集的顺序，ROWNUM为每个查询结果集的行标识一个行号，第一行返回1，第二行返回2，依次顺序递增。

ROWNUM 与 ROWID 不同， ROWID 是插入记录时生成， ROWNUM 是查询数据时生成。ROWID 标识的是行的物理地址。 ROWNUM 标识的是查询结果中的行的次序。

```sql
select t.stuid,t.stuname,t.sex,t.classno,t.stuaddress ,rownum  from stuinfo t ;
```

ROWNUM经常用来限制查询的结果返回的行数，求前几行或前几名的数据。

```sql
select * from (select t.stuid, t.stuname, t.sex, t.classno, t.stuaddress, t.age, rownum
  from stuinfo t
 order by t.age asc) where rownum <=4;
```

# Oracle 函数

Oracle 数据库中主要使用两种类型的函数：

1、单行函数：对每一个函数应用在表的记录中时，只能输入一行中的列值作为输入参数(或常数)，并且返回一个结果。

例如1：MOD(X,Y) 是求余函数，返回的X除以Y的余数，其中X和Y可以是列值，也可以是常数。

例如2：TO_CHAR(X,'YYYYMMDD')是时间类型转字符串的函数，其中X可以是行中某一时间类型（date）的列，也可以是一个时间类型的常数。

常用的单行函数大致以下几类：

1. 字符串函数：对字符串进行操作，例如：TO_CHAR()、SUBSTR()、DECODE()等等。
2. 数值函数：对数值进行计算或操作，返回一个数字。例如：ABS()、MOD()、ROUND()等等。
3. 转换函数：将一种数据类型转换成另外一种类型：例如：TO_CHAR()、TO_NUMBER()、TO_DATE()等等。
4. 日期函数：对时间和日期进行操作的函数。例如：TRUNC()、SYSDATE()、ADD_MONTHS()等等。

2、聚合函数：聚合函数同时可以对多行数据进行操作，并返回一个结果。比如 SUM(x)返回结果集中 x 列的总合。

## Oracle字符型函数

Oracle字符型函数是单行函数当中的一种，是用来处理字符串类型的函数，通过接收字符串参数，然后经过操作返回字符串结果的函数。

常用的函数如下表：

| **函数 **                | **说明 **                                                    | **案例 **                                                    | **结果 **      |
| ------------------------ | ------------------------------------------------------------ | ------------------------------------------------------------ | -------------- |
| ASCII(X)                 | 求字符X的ASCII码                                             | select ASCII('A') FROM DUAL;                                 | 65             |
| CHR(X)                   | 求ASCII码对应的字符                                          | select CHR(65) FROM DUAL;                                    | 'A'            |
| LENGTH(X)                | 求字符串X的长度                                              | select LENGTH('ORACLE技术圈')from DUAL;                      | 9              |
| CONCATA(X,Y)             | 返回连接两个字符串X和Y的结果                                 | select CONCAT('ORACLE','技术圈') from DUAL;                  | ORACLE技术圈   |
| INSTR(X,Y[,START])       | 查找字符串X中字符串Y的位置，可以指定从Start位置开始搜索，不填默认从头开始 | SELECT INSTR('ORACLE技术圈','技术') FROM DUAL;               | 7              |
| LOWER(X)                 | 把字符串X中大写字母转换为小写                                | SELECT LOWER('ORACLE技术圈') FROM DUAL;                      | oracle技术圈   |
| UPPER(X)                 | 把字符串X中小写字母转换为大写                                | SELECT UPPER('Oracle技术圈') FROM DUAL;                      | ORACLE技术圈   |
| INITCAP(X)               | 把字符串X中所有单词首字母转换为大写，其余小写。              | SELECT INITCAP('ORACLE is good ') FROM DUAL;                 | Oracle Is Good |
| LTRIM(X[,Y])             | 去掉字符串X左边的Y字符串，Y不填时，默认的是字符串X左边去空格 | SELECT LTRIM('--ORACLE技术圈','-') FROM DUAL;                | ORACLE技术圈   |
| RTRIM(X[,Y])             | 去掉字符串X右边的Y字符串，Y不填时，默认的是字符串X右边去空格 | SELECT RTRIM('ORACLE技术圈--','-') FROM DUAL;                | ORACLE技术圈   |
| TRIM(X[,Y])              | 去掉字符串X两边的Y字符串，Y不填时，默认的是字符串X左右去空格 | SELECT TRIM('--ORACLE技术圈--','-') FROM DUAL;               | ORACLE技术圈   |
| REPLACE(X,old,new）      | 查找字符串X中old字符，并利用new字符替换                      | SELECT REPLACE('ORACLE技术圈','技术圈','技术交流') FROM DUAL; | ORACLE技术交流 |
| SUBSTR(X,start[,length]) | 截取字符串X，从start位置（其中start是从1开始）开始截取长度为length的字符串，length不填默认为截取到字符串X末尾 | SELECT SUBSTR('ORACLE技术圈',1,6) FROM DUAL;                 | ORACLE         |
| RPAD(X,length[,Y])       | 对字符串X进行右补字符Y使字符串长度达到length长度             | SELECT RPAD('ORACLE',9,'-') from DUAL;                       | ORACLE---      |
| LPAD(X,length[,Y])       | 对字符串X进行左补字符Y使字符串长度达到length长度             | SELECT LPAD('ORACLE',9,'-') from DUAL;                       | ---ORACLE      |

## Oracle日期型函数

Oracle日期类型函数是操作日期、时间类型的相关数据，返回日期时间类型或数字类型结果，常用的函数有：SYSDATE()、ADD_MONTHS（）、LAST_DAY（）、TRUNC()、ROUND()等等。

### 系统日期、时间函数：

**SYSDATE函数：**该函数没有参数，可以得到系统的当前时间。

```sql
select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') from dual;
```

**SYSTIMESTAMP函数：**该函数没有参数，可以得到系统的当前时间，该时间包含时区信息，精确到微秒。

```sql
select systimestamp from dual;
```

### 数据库时区函数：

**DBTIMEZONE函数：**该函数没有输入参数，返回数据库时区。

```sql
select dbtimezone from dual;
```

### 给日期加上指定的月份函数：

**ADD_MONTHS（r,n）函数：**该函数返回在指定日期r上加上一个月份数n后的日期。其中

r：指定的日期。

n：要增加的月份数，如果N为负数，则表示减去的月份数。

```sql
select to_char(add_months(to_date('2018-11-12','yyyy-mm-dd'),1),'yyyy-mm-dd'),
       to_char(add_months(to_date('2018-10-31','yyyy-mm-dd'),1),'yyyy-mm-dd'),
       to_char(add_months(to_date('2018-09-30','yyyy-mm-dd'),1),'yyyy-mm-dd')        
  from dual;
```

### 月份最后一天函数:

**LAST_DAY(r)函数：**返回指定r日期的当前月份的最后一天日期。

```sql
select last_day(sysdate) from dual;
```

### 指定日期后一周的日期函数:

**NEXT_DAY(r,c)函数：**返回指定R日期的后一周的与r日期字符（c：表示星期几）对应的日期。

```sql
 select next_day(to_date('2018-11-12','yyyy-mm-dd'),'星期四') from dual;
```

### 返回指定日期中特定部分的函数：

**EXTRACT（time）函数：**返回指定time时间当中的年、月、日、分等日期部分。

```sql
select  extract( year from timestamp '2018-11-12 15:36:01') as year,
        extract( month from timestamp '2018-11-12 15:36:01') as month,        
        extract( day from timestamp '2018-11-12 15:36:01') as day,  
        extract( minute from timestamp '2018-11-12 15:36:01') as minute,
        extract( second from timestamp '2018-11-12 15:36:01') as second
 from dual;
```

### 返回两个日期间的月份数：

**MONTHS_BETWEEN(r1,r2)函数：**该函数返回r1日期和r2日期直接的月份。当r1>r2时，返回的是正数，假如r1和r2是不同月的同一天，则返回的是整数，否则返回的小数。当r1<r2时，返回的是负数。

```sql
select months_between(to_date('2018-11-12', 'yyyy-mm-dd'),
                      to_date('2017-11-12', 'yyyy-mm-dd')) as zs, --整数
       months_between(to_date('2018-11-12', 'yyyy-mm-dd'),
                      to_date('2017-10-11', 'yyyy-mm-dd')) as xs, --小数
       months_between(to_date('2017-11-12', 'yyyy-mm-dd'),
                      to_date('2018-10-12', 'yyyy-mm-dd')) as fs --负数
  from dual;
```

### 日期截取函数：

**ROUND（r[,f]）函数：**将日期r按f的格式进行四舍五入。如果f不填，则四舍五入到最近的一天。

```sql
select sysdate, --当前时间
       round(sysdate, 'yyyy') as year, --按年
       round(sysdate, 'mm') as month, --按月
       round(sysdate, 'dd') as day, --按天
       round(sysdate) as mr_day, --默认不填按天
       round(sysdate, 'hh24') as hour --按小时
  from dual;
```

**TRUNC（r[,f]）函数：**将日期r按f的格式进行截取。如果f不填，则截取到当前的日期。

```sql
select sysdate, --当前时间
       trunc(sysdate, 'yyyy') as year, --按年
       trunc(sysdate, 'mm') as month, --按月
       trunc(sysdate, 'dd') as day, --按天
       trunc(sysdate) as mr_day, --默认不填按天
       trunc(sysdate, 'hh24') as hour --按小时
  from dual;
```

## Oracle数值型函数

Oracle数值型函数可以是输入一个数值，并返回一个数值的函数，我们经常用到函数如下表：

| **函数 **    | **解释 **                                                    | **案例 **                                                    | **结果 **        |
| ------------ | ------------------------------------------------------------ | ------------------------------------------------------------ | ---------------- |
| ABS(X)       | 求数值X的绝对值                                              | select abs(-9) from dual;                                    | 9                |
| COS(X)       | 求数值X的余弦                                                | select cos(1) from dual;                                     | 0.54030230586814 |
| ACOS(X)      | 求数值X的反余弦                                              | select acos(1) from dual;                                    | 0                |
| CEIL(X)      | 求大于或等于数值X的最小值                                    | select ceil(7.8) from dual;                                  | 8                |
| FLOOR(X)     | 求小于或等于数值X的最大值                                    | select floor(7.8) from dual;                                 | 7                |
| log(x,y)     | 求x为底y的对数                                               | select log(2,8) from dual;                                   | 3                |
| mod(x,y)     | 求x除以y的余数                                               | select mod(13,4) from dual;                                  | 1                |
| power(x,y)   | 求x的y次幂                                                   | select power(2,4) from dual;                                 | 16               |
| sqrt(x)      | 求x的平方根                                                  | select sqrt(16) from dual;                                   | 4                |
| round(x[,y]) | 求数值x在y位进行四舍五入。y不填时，默认为y=0;当y>0时，是四舍五入到小数点右边y位。当y<0时，是四舍五入到小数点左边\|y\|位。 | select round(7.816, 2), round(7.816), round(76.816, -1)  from dual; | 7.82 / 8 / 80    |
| trunc(x[,y]) | 求数值x在y位进行直接截取y不填时，默认为y=0;当y>0时，是截取到小数点右边y位。当y<0时，是截取到小数点左边\|y\|位。 | select trunc(7.816, 2), trunc(7.816), trunc(76.816, -1)  from dual; | 7.81 / 7 / 70    |

## Oracle转换函数

Oracle转换函数是进行不同数据类型转换的函数，是我们平常数据库开发过程当中用的最多的内置函数。常用的函数有to_char()、to_number()、to_date()等等。详细分析如下表：

| **函数 **                    | **解释 **                                                    | **案例 **                                                    | **结果 **                               |
| ---------------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ | --------------------------------------- |
| asciistr(x)                  | 把字符串x转换为数据库字符集对应的ASCII值                     | select asciistr('Oracle技术圈')  from dual;                  | Oracle\6280\672F\5708                   |
| bin_to_num(x1[x2...])        | 把二进制数值转换为对应的十进制数值                           | select bin_to_num(1,0,0) from dual;                          | 4                                       |
| cast(x as type)              | 数据类型转换函数，该函数可以把x转换为对应的type的数据类型，基本上用于数字，字符，时间类型安装数据库规则进行互转， | select cast('123' as number) num,cast(123 as varchar2(3)) as ch,cast(to_date('20181112','yyyymmdd') as varchar2(12)) as time  from dual; | 123/'123'/12-11月-18(三列值，用"/"隔开) |
| convert(x,d_chset[,r_chset]) | 字符串在字符集间的转换函数，对字符串x按照原字符集r_chset转换为目标字符集d_chset，当r_chset不填时，默认选择数据库服务器字符集。 | select CONVERT('oracle技术圈','US7ASCII','ZHS16GBK') from dual; | oracle???                               |
| to_char(x[,f])               | 把字符串或时间类型x按格式f进行格式化转换为字符串。           | select to_char(123.46,'999.9') from dual;  select to_char(sysdate,'yyyy-mm-dd') from dual; | 123.52018-11-13                         |
| to_date(x[,f])               | 可以把字符串x按照格式f进行格式化转换为时间类型结果。         | select to_date('2018-11-13','yyyy-mm-dd') from dual;         | 2018/11/13                              |
| to_number(x[,f])             | 可以把字符串x按照格式f进行格式化转换为数值类型结果。         | select to_number('123.74','999.99') from dual                | 123.74                                  |

## Oracle聚合函数

Oracle聚合函数同时可以对多行数据进行操作，并返回一个结果。比如经常用来计算一些分组数据的总和和平均值之类，常用函数有AVG()、SUM()、MIN()、MAX()等等。

AVG([distinct ] expr)：该函数可以求列或列组成的表达式expr的平均值，返回的是数值类型。其中 [distinct](http://www.oraclejsq.com/article/010100256.html)是可选参数，表示是否去掉重复行。

count（*|[distinct]expr）函数可以用来计算查询结果的条数或行数。函数中必须指定列名或者表达式expr，不然就要全选使用*号。

MAX([distinct] expr)、MIN([distinct] expr)函数可以返回指定列或列组成的表达式expr中的最大值或最小值。该函数也通常和where条件、group by分组结合在一起使用。

SUM([distinct] expr)函数可以对指定列或列组成的表达式expr进行求和，假如不使用分组group by ,那就是按照整表作为一个分组。

# Oracle子查询

Oracle子查询就是嵌套查询，他把select  查询的结果作为另外一个select、update或delete语句的条件，它的本质就是where条件查询中的一个条件表达式。其中我们数据库开发过程中，子查询可以根据查询结果的行数的多少，可以区分为单行子查询和多行子查询。

1、单行子查询：向外部返回的结果为空或者返回一行。

2、多行子查询：向外部返回的结果为空、一行、或者多行。

Oracle单行子查询是利用where条件“=”关联查询结果的，如果单行子查询返回多行会报单行子查询不能返回多行的数据库错误，下面利用案例讲解子查询。

```sql
select *
  from stuinfo t
 where t.classno in (select b.classno
                       from class b
                      where b.classname = '信息科学2班（18）');
 
/*虽然在这利用内关联也可以查出结果，
  而且效率更好，但是在一些没有关联关
  系的时候可以利用子查询 */
select t.*
  from stuinfo t, class b
 where t.classno = b.classno
   and b.classname = '信息科学2班（18）';
```

Oracle多行子查询则需要利用IN关键字来接收子查询的多行结果。也可以用量化关键字ANY、ALL和关系运算符>、>=、=、<、<=来组合使用。

ANY关键字：表示子查询结果当中的任意一个。假如：>ANY(子查询)，表示：只要大于子查询当中的任意一个值，这个条件就满足。

ALL关键字：表示子查询中的所有结果。假如：>ALL(子查询)，表示：必须大于子查询当中的所有结果才能满足这个条件。

```sql
select * from stuinfo t where t.classno in (select b.classno from class b);

--年龄只要大于当中子查询的最小值26岁即可
select * from stuinfo t where t.age>any(26,27,28);
--年龄必须大于子查询当中的最大值28岁才可以
select * from stuinfo t where t.age>all(26,27,28);
```

# Oracle synonym 同义词

Oracle synonym  同义词是数据库当前用户通过给另外一个用户的对象创建一个别名，然后可以通过对别名进行查询和操作，等价于直接操作该数据库对象。Oracle同义词常常是给表、视图、函数、过程、包等制定别名，可以通过CREATE 命令进行创建、ALTER 命令进行修改、DROP 命令执行删除操作。 

Oracle synonym 同义词按照访问权限分为私有同义词、公有同义词。

私有同义词：私有同义词只能当前用户可以访问，前提：当前用户具有create synonym 权限。

公有同义词：公有同义词只能具有DBA用户才能进行创建，所有用户都可以访问的。

```sql
CREATE [OR REPLACE] [PUBLIC] SYSNONYM [当前用户.]synonym_name
 
FOR [其他用户.]object_name;
```

1、create [or replace] 命令[create建表](http://www.oraclejsq.com/article/010100139.html)命令一样，当当前用户下同义词对象名已经存在的时候，就会删除原来的同义词，用新的同义词替代上。

2、[public]：创建的是公有同义词，在实际开发过程中比较少用，因为创建就代表着任何用户都可以通过自己用户访问操作该对象，一般我们访问其他用户对象时，需要该用户进行授权给我们。

3、用户名.object_name：[oralce用户](http://www.oraclejsq.com/article/010100133.html)对象的权限都是自己用户进行管理的，需要其他用户的某个对象的操作权限，只能通过对象拥有者（用户）进行授权给当前用户。或者当前用户具有系统管理员权限（DBA），即可通过用户名.object_name操作该对象。

同义词删除只能通过同义词拥有者的用户或者具有DBA权限的用户才能删除。

```sql
DROP [PUBLIC] SYNONYM [用户.]sysnonym_name;
```

# Oracle序列

Oracle序列Sequence是用来生成连续的整数数据的对象，它经常用来作为业务中无规则的主键。Oracle序列可以是升序列也可以是降序列。

```sql
CREATE SEQUENCE sequence_name
[MAXVALUE num|NOMAXVALUE]
[MINVALUE num|NOMINVALUE]
[START WITH num]
[INCREMENT BY increment]
[CYCLE|NOCYCLE]
[CACHE num|NOCACHE]
```

**语法解析：**

1、MAXVALUE/MINVALUE：指定的是序列的最大值和最小值。

2、NOMAXVALUE/NOMINVALUE：不指定序列的最大值和最小值，使用系统的默认选项，升序的最大值：10^27次方，降序是-1。升序最小值：1，降序的最小值：-10^26。

3、START WITH：指定从某一个整数开始，升序默认是1，降序默认是-1。

4、CYCLE | NOCYCLE:表示序列达到最大值或者最小值的时候，是否重新开始。CYCLE：重新开始，NOCYCLE：不重新开始。

5、CACHE：使用 CACHE 选项时，该序列会根据序列规则预生成一组序列号。保留在内存中，当使用下一个序列号时，可以更快的响应。当内存中的序列号用完时，系统再生成一组新的序列号，并保存在缓存中，这样可以提高生成序列号的效率 。

6、NOCACHE：不预先在内存中生成序列号。

# Oracle视图

```sql
CREATE [OR REPLACE]  VIEW view_name
AS
SELECT查询
[WITH READ ONLY CONSTRAINT]
```

# Oracle索引

## 索引的类别：

适当的使用索引可以提高数据检索速度，那Oracle有哪些类型的索引呢？

**1、b-tree索引：**Oracle数据中最常见的索引，就是b-tree索引，create index创建的normal就是b-tree索引，没有特殊的必须应用在哪些数据上。

**2、bitmap位图索引：**位图索引经常应用于列数据只有几个枚举值的情况，比如上面说到过的性别字段，或者我们经常开发中应用的代码字段。这个时候使用bitmap位图索引，查询效率将会最快。

**3、函数索引：**比如经常对某个字段做查询的时候经常是带函数操作的，那么此时建一个函数索引就有价值了。例如：trim（列名）或者substr(列名)等等字符串操作函数，这个时候可以建立函数索引来提升这种查询效率。

**4、hash索引：**hash索引可能是访问数据库中数据的最快方法，但它也有自身的缺点。创建hash索引必须使用hash集群，相当于定义了一个hash集群键，通过这个集群键来告诉oracle来存储表。因此，需要在创建HASH集群的时候指定这个值。存储数据时，所有相关集群键的行都存储在一个数据块当中，所以只要定位到hash键，就能快速定位查询到数据的物理位置。

**5、reverse反向索引：**这个索引不经常使用到，但是在特定的情况下，是使用该索引可以达到意想不到的效果。如：某一列的值为{10000,10001,10021,10121,11000,....}，假如通过b-tree索引，大部分都密集发布在某一个叶子节点上，但是通过反向处理后的值将变成{00001,10001,12001,12101,00011,...}，很明显的发现他们的值变得比较随机，可以比较平均的分部在各个叶子节点上，而不是之前全部集中在某一个叶子节点上，这样子就可大大提高检索的效率。

**6、分区索引和分区表的全局索引：**这两个索引是应用在分区表上面的，前者的分区索引是对分区表内的单个分区进行数据索引，后者是对分区表的全表进行全局索引。分区表的介绍，可以后期再做单独详解，这里就不累述了。

```sql
create[unique]|[bitmap] index index_name --UNIQUE表示唯一索引、BITMAP位图索引
on table_name(column1,column2...|[express])--express表示函数索引
[tablespace tab_name] --tablespace表示索引存储的表空间
[pctfree n1]    --索引块的空闲空间n1
[storage         --存储块的空间
 (
    initial 64K  --初始64k
    next 1M
    minextents 1
    maxextents unlimited
 
)];

alter index index_old rename to index_new;--重新命名索引
alter index index_name coalesce;--合并索引
alter index index_name rebuild;--重新构造
drop index index_name;
```

**语法解析：**

1、UNIQUE:指定索引列上的值必须是唯一的。称为唯一索引，BITMAP表示位图索引。

2、index_name：指定索引名。

3、tabl_name：指定要为哪个表创建索引。

4、column_name：指定要对哪个列创建索引。我们也可以对多列创建索引，这种索引称为组合索引。也可以是函数表达式，这种就是函数索引。

# Oracle分区详解和创建

Oracle在实际业务生产环境中，经常会遇到随着业务量的逐渐增加，表中的数据行数的增多，Oracle对表的管理和性能的影响也随之增大。对表中数据的查询、表的备份的时间将大大提高，以及遇到特定情况下，要对表中数据进行恢复，也随之数据量的增大而花费更多的时间。这个时候，Oracle数据库提供了分区这个机制，通过把一个表中的行进行划分，归为几部分。可以减少大数据量表的管理和性能问题。利用这种分区方式把表数据进行划分的机制称为表分区，各个分区称为分区表。

## Oracle创建分区

Oracle分区也是通过create table命令组成，但是对表进行分区时，得考虑一个字段作为分区建，通常按值的范围来划分分区，所以这里考虑使用成绩的录入时间进行分区。具体代码如下：

```sql
-- Create table
create table STUDENT.SCORE
(
  scoreid  VARCHAR2(18) not null,
  stuid    VARCHAR2(11),
  courseid VARCHAR2(9),
  score    NUMBER,
  scdate   DATE
)
partition by range(scdate)(
partition p_score_2018 values less than (TO_DATE('2019-01-01 00:00:00','yyyy-mm-ddhh24:mi:ss'))
 TABLESPACE TS_2018,
partition p_score_2019 values less than (TO_DATE('2020-01-01 00:00:00','yyyy-mm-ddhh24:mi:ss'))
 TABLESPACE TS_2019,
partition p_score_2020 values less than (MAXVALUE)
 TABLESPACE TS_2020
);
-- Add comments to the table 
comment on table STUDENT.SCORE
  is '学生成绩表';
-- Add comments to the columns 
comment on column STUDENT.SCORE.scoreid
  is '学生成绩id';
comment on column STUDENT.SCORE.stuid
  is '学生学号';
comment on column STUDENT.SCORE.courseid
  is '课程id(年度+上下学期+课程序列)';
comment on column STUDENT.SCORE.score
  is '成绩';
comment on column STUDENT.SCORE.scdate
  is '成绩录入时间';
-- Create/Recreate primary, unique and foreign key constraints 
alter table STUDENT.SCORE
  add constraint PK_SCORE primary key (SCOREID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
```

这里使用命令partition by  range对成绩的录入日期（scdate）进行分区，如录入日期小于2019年的会被放入分区p_score_2018当中，2019年数据会被放入p_score_2019这个分区当中，大于2019年数据都会被放入到p_score_2020这个分区当中。

这里不必为最后一个分区指定最大值，maxvalue关键字会告诉Oracle使用这个分区来存储前面几个分区当中不能存储的数据。

上面实例展示的是Oracle按照值的范围进行分区，Oracle还支出散列分区，通过某一个字段，把表中的数据散列在各个分区中。可以通过关键字partition by hash，可以把分区散列到不同的表空间当中。

Oracle还支持列表分区（partition by list），它是通过按照指定分区建的值归并到各个分区，其实这里学生成绩表也可以考虑按照课程进行列表分区。

总结：Oracle分区对大型表（数据量大）有重大的性能提升，所以在表结构设计时，需要提前按照相关业务需求进行相应的改进。

## 在分区表上创建索引

```sql
create index idx_score_stuid on student.score(stuid)
local
(
partition idx_score_stuid_1 tablespace TS_2018,
partition idx_score_stuid_2 tablespace TS_2019,
partition idx_score_stuid_3 tablespace TS_2020
)
```

请注意local关键字。在这个create index命令中没有指定范围，而是由local  关键字告诉Oracle为score表的每一个分区创建一个单独的索引，因此，每一个表分区对应着一个索引分区。每一个索引分区存储在不同的表空间上，可以大大提高I/O和查询效率。

Oracle分区表也可以创建全局索引，全局索引和普通表的索引一样，是对整表的数据进行创建索引。例如，可以对学生成绩表的（score）的课程ID（COURSEID）创建全局索引，具体代码如下：

```sql
create index STUDENT.IDX_SCORE_COURSEID 
on STUDENT.SCORE (courseid)
global;
```

这里，虽然分区索引比全局索引更容易管理，而且在分区当中查询效率更高，但是全局索引在全表进行唯一性检索时的速度可能会比局部索引更快，因为全局检索唯一性时，需要跨区。

# oracle merge into命令

Oracle merge into命令，顾名思义就是“有则更新，无则插入”，这个也是merge into  命令的核心思想，在实际开发过程中，我们会经常遇到这种通过两表互相关联匹配更新其中一个表的某些字段的业务，有时还要处理不匹配的情况下的业务。这个时候你会发现随着表的数据量增加，类似这种业务场景的执行效率会比较慢，那是因为你需要多次重复查询两表中的数据，而通过merge into命令，只需要一次关联即可完成“有则更新，无则插入”的业务场景，大大提高语句的执行效率。

```sql
merge into A 
using B 
on (A.id = B.id)
when matched then
  update set A.col=B.col
when not matched then
  insert 语句;
```

语法解析：利用B表通过A.id=B.id的条件来匹配A表，当满足条件时，可以对A表进行更新，当不满足条件时，可以利用inert语句插入相关数据。

# oracle分析函数_开窗函数详解

Oracle分析函数是Oracle系统自带函数中的一种，是Oracle专门用来解决具有复杂统计需求的函数，它可以对数据进行分组然后基于组中数据进行分析统计，最后在每组数据集中的每一行中返回这个统计值。

Oracle分析函数不同于分组统计（group by），group by只能按照分组字段返回一个固定的统计值，但是不能在原来的数据行上带上这个统计值，而Oracle分析函数正是Oracle专门解决这类统计需求所开发出来的函数。

Oracle分析函数都会带上一个开窗函数over（），所以常把两者结合一起讲解。

**Oracle分析函数的语法结构：**

```sql
select table.column, 
Analysis_function()OVER(
[partition by 字段]
[order by 字段 [windos]]
) as 统计值
from table
```

语法解析：

  1、Analysis_function：指定分析函数名，常用的分析函数有sum、max、first_value、last_value、rank、row_number等等。

  2、over()：开窗函数名，partition by指定进行数据分组的字段，order by指定进行排序的字段，windos指定数据窗口（即指定分析函数要操作的行数），使用的语法形式大概如下：

```sql
over(partition by xxx order by yyy rows between zzz)
```

在原始数据上附带上每门课的最高成绩和最低成绩。

```sql
select c.stuname,
       b.coursename,
       t.score,
       --获取组中成绩最大值
       max(t.score) over(partition by t.courseid) as score_max,
       --获取组中成绩最小值
       min(t.score) over(partition by t.courseid) as score_min,
       --分组窗口的第一个值 (指定窗口为组中第一行到末尾行)
       first_value(t.score) over(partition by t.courseid 
       order by t.score desc ROWS BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING) as score_first,
       --分组窗口的最后一个值(指定窗口为组中第一行到末尾行)
       last_value(t.score) over(partition by t.courseid 
       order by t.score desc ROWS BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING) as score_last,
        
       --分组窗口的第一个值 （不指定窗口）
       first_value(t.score) over(partition by t.courseid order by t.score desc ) as score_first_1,
       --分组窗口的最后一个值（不指定窗口）
       last_value(t.score) over(partition by t.courseid order by t.score desc ) as score_last_1
         
  from STUDENT.SCORE t, student.course b, student.stuinfo c
 where t.courseid = b.courseid
   and t.stuid = c.stuid
```

通过数据，我们可以发现：

1、min和max分析函数是直接获取组中的最小值和最大值。

2、first_value和last_value是返回窗口的第一行和最后一行数据，由于我们通过成绩字段对分组内的数据进行了降序排序，所以也可以达到在一定的窗口内获取最大值和最小值的功能。

3、排序不指定窗口时，就是按照组内的第一行到当前行作为窗口，然后取出窗口的第一行和最后一行。

4、窗口子语句当中的第一行是 unbounded preceding，当前行是 current row，最后一行是 unbounded following，所以正是利用窗口范围是第一行到最后一行，得到同一课程内的最大成绩和最小成绩。

利用row_number、rank获取学生课程成绩的排名，具体代码如下：

```sql
select c.stuname,
       b.coursename,
       t.score,
       --组内排名
       row_number() over(partition by t.courseid order by t.score desc) as "row_number排名",
       --组内排名
       rank() over(partition by t.courseid order by t.score desc) as "rank排名"
 
  from STUDENT.SCORE t, student.course b, student.stuinfo c
 where t.courseid = b.courseid
   and t.stuid = c.stuid
```

通过数据可以看出：

1、ROW_NUMBER函数排名是返回一个唯一的值，当碰到相同数据时，排名按照记录集中记录的顺序依次递增。

2、rank函数返回一个唯一的值，但是当碰到相同的数据时，此时所有相同数据的排名是一样的，同时会在最后一条相同记录和下一条不同记录的排名之间空出排名。比如数学成绩都是84分的两个人并列第二名，但是“张三丰”同学就是直接是第四名。

3、我们经常会利用row_number函数的排名机制（排名的唯一性）来过滤重复数据，即按照某一个特定的排序条件，通过获取排名为1的数据来获取重复数据当中最新的数据值。

# Oracle行转列（PIVOT）

```sql
SELECT * FROM （数据查询集）
PIVOT 
(
 SUM(Score/*行转列后 列的值*/) FOR 
 coursename/*需要行转列的列*/ IN (转换后列的值)
)


select * from  (select c.stuname,
       b.coursename,
       t.score
  from STUDENT.SCORE t, student.course b, student.stuinfo c
 where t.courseid = b.courseid
   and t.stuid = c.stuid )  /*数据源*/
PIVOT 
(
 SUM(score/*行转列后 列的值*/) 
 FOR coursename/*需要行转列的列*/ IN ('英语(2018上学期)' as 英语,'数学(2018上学期)' as 数学,'语文(2018上学期)' as 语文 )
) ;
```

## Oracle列转行_unpivot

```sql
select 字段 from 数据集
unpivot（自定义列名/*列的值*/ for 自定义列名 in（列名））


select stuname, coursename ,score from
score_copy  t
unpivot
(score for coursename in (英语,数学,语文))
```

# oracle优化

## 设置autotrace

| 序号 | 命令                        | 解释                             |
| ---- | --------------------------- | -------------------------------- |
| 1    | SET AUTOTRACE OFF           | 此为默认值，即关闭Autotrace      |
| 2    | SET AUTOTRACE ON EXPLAIN    | 只显示执行计划                   |
| 3    | SET AUTOTRACE ON STATISTICS | 只显示执行的统计信息             |
| 4    | SET AUTOTRACE ON            | 包含2,3两项内容                  |
| 5    | SET AUTOTRACE TRACEONLY     | 与ON相似，但不显示语句的执行结果 |

## EXPLAIN PLAN FOR

EXPLAIN PLAN FOR SELECT * FROM DAVE;

## 优化执行顺序

oracle中的执行顺序是从右到左，从上到下，from多个表是先从最右边的查，where多个条件时先从右边开始，确保from时表的数据先查少的，再查多的，where时先用可以过滤多的

## 使用where子句代替having

having对检索出来的数据进行再次过滤，会进行排序、总计等操作

## 通过内部函数提高SQL效率

## 尽量使用exists代替in，使用not exists代替not in

## 避免在索引列使用not、is null、is not null以及使用计算

## 总是使用索引列的第一列

如果索引建立在多个列上，只有在他第一个列被where子句引用时，优化器才会选择使用该索引。当仅使用索引的第二个列时，优化器使用了全表扫描而忽略了索引

## 避免索引列类型转换、

当比较不同数据类型时，oracle会自动对列进行简单的类型转换，当索引列发生类型转换时这个索引将不会被用到

## 当心where子句

昂where子句中有!=,||,+等操作符时索引会失效

# 自我总结