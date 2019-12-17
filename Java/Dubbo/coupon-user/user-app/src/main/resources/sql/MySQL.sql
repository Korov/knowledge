DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`
(
    `id`        int(11)      NOT NULL AUTO_INCREMENT,
    `account`   varchar(255) NOT NULL DEFAULT '' COMMENT '账号',
    `address`   varchar(50)  NOT NULL DEFAULT '' COMMENT '地址',
    `password`  varchar(64)  NOT NULL DEFAULT '' COMMENT '密码',
    `phone`     char(11)              DEFAULT NULL COMMENT '电话号码',
    `point`     int(11)               DEFAULT NULL COMMENT '积分值',
    `remark`    varchar(50)  NOT NULL DEFAULT '',
    `tel_phone` char(11)     NOT NULL DEFAULT '' COMMENT '备份电话',
    `username`  varchar(15)  NOT NULL DEFAULT '' COMMENT '用户昵称',
    `zip_code`  varchar(6)   NOT NULL DEFAULT '' COMMENT '邮政编码',
    `balance`   bigint(20)            DEFAULT NULL COMMENT '账户金额',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 5
  DEFAULT CHARSET = utf8 COMMENT '用户基础信息表';
