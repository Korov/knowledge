DROP DATABASE IF EXISTS `RoleManager`;
CREATE DATABASE `RoleManager`;
USE `RoleManager`;

drop table if exists table_user;
create table table_user
(
    id           bigint not null auto_increment,
    avtatar      varchar(1024) comment '用户头像',
    name         varchar(1024) comment '用户姓名',
    age          int default 0 comment '年龄',
    nickname     varchar(1024) comment '用户昵称',
    cardno       varchar(1024) comment '身份证号码',
    email        varchar(1024) comment '电子邮件',
    phone        varchar(1024) comment '电话号码',
    createtime   datetime comment '创建时间',
    updatetime   datetime comment '更新时间',
    pwd          varchar(1024) comment '密码',
    salt         varchar(1024) comment '盐加密',
    pwderrortime varchar(1024) comment '密码错误次数',
    status       int default 0 comment '是否可用(0可用，1不可用)',
    statusremark varchar(1024) comment '不可用原因',
    remark       varchar(1024) comment '备注',
    primary key (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='用户信息表';

drop table if exists table_group;
create table table_group
(
    id         bigint auto_increment comment '组id',
    parent_id  bigint        null comment '父id',
    group_name varchar(1024) null comment '组名称',
    group_code varchar(1024) null comment '组代码',
    primary key (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='用户组表';

drop table if exists table_group_role;
create table table_group_role
(
    group_id bigint not null comment '组id',
    role_id  bigint not null comment '角色id',
    primary key (group_id, role_id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='用户组角色关联表';

drop table if exists table_permission;
create table table_permission
(
    id              bigint auto_increment comment '权限id',
    parent_id       bigint        not null comment '父id',
    permission_name varchar(1024) null comment '权限名称',
    permission_code varchar(1024) null comment '权限代码',
    primary key (id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='权限表';

drop table if exists table_role;
create table table_role
(
    id        bigint auto_increment comment '角色id',
    parent_id bigint        null comment '父id',
    role_name varchar(1024) null comment '角色名称',
    role_code varchar(1024) null comment '角色代码'
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='角色表';

drop table if exists table_role_permission;
create table table_role_permission
(
    role_id       bigint not null comment '角色id',
    permission_id bigint not null comment '权限id',
    primary key (role_id, permission_id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='角色权限表';

drop table if exists table_user_group;
create table table_user_group
(
    user_id  bigint not null comment '用户id',
    group_id bigint not null comment '组id',
    primary key (user_id, group_id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='用户用户组表';

drop table if exists table_user_role;
create table table_user_role
(
    role_id bigint not null comment '角色id',
    user_id bigint not null,
    primary key (role_id, user_id)
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='用户角色表';

INSERT INTO table_group_role (group_id, role_id)
VALUES (1, 1);
INSERT INTO table_group_role (group_id, role_id)
VALUES (1, 2);
INSERT INTO table_group_role (group_id, role_id)
VALUES (2, 3);

INSERT INTO table_role (id, parent_id, role_name, role_code)
VALUES (1, null, 'p1', 'p1');
INSERT INTO table_role (id, parent_id, role_name, role_code)
VALUES (2, null, 'p2', 'p2');
INSERT INTO table_role (id, parent_id, role_name, role_code)
VALUES (3, null, 'p3', 'p3');
INSERT INTO table_role (id, parent_id, role_name, role_code)
VALUES (4, null, 'p4', 'p4');

INSERT INTO table_user (id, avtatar, name, age, nickname, cardno, email, phone, createtime, updatetime, pwd, salt,
                        pwderrortime, status, statusremark, remark)
VALUES (1, null, 'user1', 20, 'zhangsan', '123453246532456434', 'demo@demo.com', '13131313131', '2020-02-18 10:40:18',
        '2020-02-18 10:40:18', '$2a$10$1oJq1Omjpzxe7F5T0G0EvucGGegckK24kGfi1hP87piQMeypEybkK', null, null, 1, null,
        null);
INSERT INTO table_user (id, avtatar, name, age, nickname, cardno, email, phone, createtime, updatetime, pwd, salt,
                        pwderrortime, status, statusremark, remark)
VALUES (2, null, 'user1', 20, 'user2', '123453246532456434', 'demo@demo.com', '13131313131', '2020-02-18 11:02:11',
        '2020-02-18 11:02:12', '$2a$10$1oJq1Omjpzxe7F5T0G0EvucGGegckK24kGfi1hP87piQMeypEybkK', null, null, 0, null,
        null);
INSERT INTO table_user (id, avtatar, name, age, nickname, cardno, email, phone, createtime, updatetime, pwd, salt,
                        pwderrortime, status, statusremark, remark)
VALUES (16, null, null, null, 'demo', null, null, null, null, null, null, null, null, null, null, null);

INSERT INTO table_user_group (user_id, group_id)
VALUES (1, 1);
INSERT INTO table_user_group (user_id, group_id)
VALUES (1, 2);

INSERT INTO table_user_role (role_id, user_id)
VALUES (1, 1);
INSERT INTO table_user_role (role_id, user_id)
VALUES (1, 2);


DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`
(
    `client_id`               varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户端标识',
    `resource_ids`            varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '接入资源列表',
    `client_secret`           varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL COMMENT '客户端秘钥',
    `scope`                   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL,
    `authorized_grant_types`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL,
    `web_server_redirect_uri` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL,
    `authorities`             varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL,
    `access_token_validity`   int(11)                                                 NULL     DEFAULT NULL,
    `refresh_token_validity`  int(11)                                                 NULL     DEFAULT NULL,
    `additional_information`  longtext CHARACTER SET utf8 COLLATE utf8_general_ci     NULL,
    `create_time`             timestamp(0)                                            NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
    `archived`                tinyint(4)                                              NULL     DEFAULT NULL,
    `trusted`                 tinyint(4)                                              NULL     DEFAULT NULL,
    `autoapprove`             varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL,
    PRIMARY KEY (`client_id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci COMMENT = '接入客户端信息'
  ROW_FORMAT = Dynamic;
INSERT INTO `oauth_client_details`
VALUES ('c1', 'res1',
        '$2a$10$Csc2DEvx7IiCbHoM1vAvLusYoHofpSaV20zw71T6sqYPIhVwOLuca', 'ROLE_ADMIN,ROLE_USER,ROLE_API',
        'client_credentials,password,authorization_code,implicit,refresh_token', 'http://www.baidu.com',
        NULL, 7200, 259200, NULL, '2020-03-03 21:43:30', 0, 0, 'false');
INSERT INTO `oauth_client_details`
VALUES ('c2', 'res2',
        '$2a$10$Csc2DEvx7IiCbHoM1vAvLusYoHofpSaV20zw71T6sqYPIhVwOLuca', 'ROLE_API',
        'client_credentials,password,authorization_code,implicit,refresh_token', 'http://www.baidu.com',
        NULL, 31536000, 2592000, NULL, '2020-03-03 21:43:30', 0, 0, 'false');

DROP TABLE IF EXISTS `oauth_code`;
CREATE TABLE `oauth_code`
(
    `create_time`    timestamp(0)                                            NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `code`           varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL,
    `authentication` blob                                                    NULL,
    INDEX `code_index` (`code`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8
  COLLATE = utf8_general_ci
  ROW_FORMAT = Compact;

