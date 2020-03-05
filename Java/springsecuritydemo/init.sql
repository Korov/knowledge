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

DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details`
(
    `client_id`               varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户端标
识',
    `resource_ids`            varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL
        COMMENT '接入资源列表',
    `client_secret`           varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL
        COMMENT '客户端秘钥',
    `scope`                   varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL,
    `authorized_grant_types`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT
                                                                                                   NULL,
    `web_server_redirect_uri` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT
                                                                                                   NULL,
    `authorities`             varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL     DEFAULT NULL,
    `access_token_validity`   int(11)                                                 NULL     DEFAULT NULL,
    `refresh_token_validity`  int(11)                                                 NULL     DEFAULT NULL,
    `additional_information`  longtext CHARACTER SET utf8 COLLATE utf8_general_ci     NULL,
    `create_time`             timestamp(0)                                            NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE
        CURRENT_TIMESTAMP(0),
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
        '$2a$10$NlBC84MVb7F95EXYTXwLneXgCca6/GipyWR5NHm8K0203bSQMLpvm', 'ROLE_ADMIN,ROLE_USER,ROLE_API',
        'client_credentials,password,authorization_code,implicit,refresh_token', 'http://www.baidu.com',
        NULL, 7200, 259200, NULL, '2020-03-03 21:43:30', 0, 0, 'false');
INSERT INTO `oauth_client_details`
VALUES ('c2', 'res2',
        '$2a$10$NlBC84MVb7F95EXYTXwLneXgCca6/GipyWR5NHm8K0203bSQMLpvm', 'ROLE_API',
        'client_credentials,password,authorization_code,implicit,refresh_token', 'http://www.baidu.com',
        NULL, 31536000, 2592000, NULL, '2020-03-03 21:43:30', 0, 0, 'false');

INSERT INTO t_permission (id, code, description, url) VALUES ('1', 'p1', 'p1', 'p1');

INSERT INTO t_role_permission (role_id, permission_id) VALUES ('1', '1');

INSERT INTO t_user (id, username, password, fullname, mobile) VALUES ('1', 'zhangsan', '$2a$10$1oJq1Omjpzxe7F5T0G0EvucGGegckK24kGfi1hP87piQMeypEybkK', 'zhangsan', '12345534');

INSERT INTO t_user_role (role_id, user_id) VALUES ('1', '1');