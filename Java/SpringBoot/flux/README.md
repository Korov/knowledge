# 学习高并发相关知识

记录有关NIO，RPC等相关数据

# MySQL数据库

`docker run --name test_mysql -p 3307:3306 -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=test -d mysql:8.0.26`

```mysql
drop table if exists `user_info`;
create table if not exists `user_info`
(
    id        bigint primary key auto_increment,
    user_name varchar(255)
);

insert into `user_info`(id, user_name)
values (1, 'zhangsan'),
       (2, 'lisi');
```