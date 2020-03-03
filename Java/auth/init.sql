drop database if exists `user_db`;
create database `user_db`;
use `user_db`;

drop table if exists `t_user`;
create table `t_user`
(
    id       varchar(128),
    username varchar(128),
    password varchar(128),
    fullname varchar(128),
    mobile   varchar(128)
)ENGINE = InnoDB AUTO_INCREMENT = 0 DEFAULT CHARSET = utf8;

drop table if exists `t_permission`;
create table `t_permission`
(
    id       varchar(128),
    code varchar(128),
    description varchar(128),
    url varchar(128)
)ENGINE = InnoDB AUTO_INCREMENT = 0 DEFAULT CHARSET = utf8;

drop table if exists `t_role_permission`;
create table `t_role_permission`
(
    role_id       varchar(128),
    permission_id varchar(128)
)ENGINE = InnoDB AUTO_INCREMENT = 0 DEFAULT CHARSET = utf8;

drop table if exists `t_user_role`;
create table `t_user_role`
(
    role_id       varchar(128),
    user_id varchar(128)
)ENGINE = InnoDB AUTO_INCREMENT = 0 DEFAULT CHARSET = utf8;

INSERT INTO t_permission (id, code, description, url) VALUES ('1', 'p1', 'p1', 'p1');

INSERT INTO t_role_permission (role_id, permission_id) VALUES ('1', '1');

INSERT INTO t_user (id, username, password, fullname, mobile) VALUES ('1', 'zhangsan', '$2a$10$1oJq1Omjpzxe7F5T0G0EvucGGegckK24kGfi1hP87piQMeypEybkK', 'zhangsan', '12345534');

INSERT INTO t_user_role (role_id, user_id) VALUES ('1', '1');