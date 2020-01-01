DROP DATABASE IF EXISTS `RoleManager`;
CREATE DATABASE `RoleManager`;
USE `RoleManager`;

drop table if exists tbale_user;

/*==============================================================*/
/* Table: tbale_user                                            */
/*==============================================================*/
create table tbale_user
(
    id                   bigint not null auto_increment,
    avtatar              varchar(1024) comment '用户头像',
    name                 varchar(1024) comment '用户姓名',
    age                  int default 0 comment '年龄',
    nickname             varchar(1024) comment '用户昵称',
    cardno               varchar(1024) comment '身份证号码',
    email                varchar(1024) comment '电子邮件',
    phone                varchar(1024) comment '电话号码',
    createtime           datetime comment '创建时间',
    updatetime           datetime comment '更新时间',
    pwd                  varchar(1024) comment '密码',
    salt                 varchar(1024) comment '盐加密',
    pwderrortime         varchar(1024) comment '密码错误次数',
    status               int default 0 comment '是否可用(0可用，1不可用)',
    statusremark         varchar(1024) comment '不可用原因',
    remark               varchar(1024) comment '备注',
    primary key (id)
) ENGINE = InnoDB AUTO_INCREMENT = 0 DEFAULT CHARSET = utf8 ROW_FORMAT = DYNAMIC COMMENT ='用户信息表';

