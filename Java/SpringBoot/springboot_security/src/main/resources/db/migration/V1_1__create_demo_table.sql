-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`
(
    `id`          varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci  NOT NULL COMMENT '用户编号',
    `username`    varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci  NULL DEFAULT NULL COMMENT '用户账户',
    `password`    varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户密码',
    `enable`      bit(1)                                                  NULL DEFAULT NULL COMMENT '用户状态（0：正常 1：禁用）',
    `nickname`    char(20) CHARACTER SET utf8 COLLATE utf8_general_ci     NULL DEFAULT NULL COMMENT '姓名',
    `avatar`      varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
    `gender`      int(0)                                                  NULL DEFAULT NULL COMMENT '性别 (0: 未知 1: 男 2: 女)',
    `email`       char(30) CHARACTER SET utf8 COLLATE utf8_general_ci     NULL DEFAULT NULL COMMENT '邮箱',
    `phone`       char(20) CHARACTER SET utf8 COLLATE utf8_general_ci     NULL DEFAULT NULL COMMENT '联系方式',
    `deleted`     bit(1)                                                  NULL DEFAULT NULL COMMENT '逻辑删除',
    `create_by`   char(50) CHARACTER SET utf8 COLLATE utf8_general_ci     NULL DEFAULT NULL COMMENT '创建人',
    `create_time` datetime(0)                                             NULL DEFAULT NULL COMMENT '创建时间',
    `update_by`   char(50) CHARACTER SET utf8 COLLATE utf8_general_ci     NULL DEFAULT NULL COMMENT '修改人',
    `update_time` datetime(0)                                             NULL DEFAULT NULL COMMENT '修改时间',
    `locked`      bit(1)                                                  NULL DEFAULT NULL COMMENT '锁定状态',
    `remark`      text CHARACTER SET utf8 COLLATE utf8_general_ci         NULL COMMENT '备注',
    `tenant_id`   char(20) CHARACTER SET utf8 COLLATE utf8_general_ci     NULL DEFAULT NULL COMMENT '租户编号',
    `dept_id`     char(20) CHARACTER SET utf8 COLLATE utf8_general_ci     NULL DEFAULT NULL COMMENT '部门编号',
    `post_id`     char(20) CHARACTER SET utf8 COLLATE utf8_general_ci     NULL DEFAULT NULL COMMENT '岗位编号',
    `sign`        varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '签名',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_bin
  ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user`
VALUES ('1', 'admin', '$2a$10$V6T0bAvAe5ZK9DCBXdzzqe35rm1JvFj/7x7Oo2aFf.jm3B2MwV/lW', b'1', '夏娜',
        'https://portrait.gitee.com/uploads/avatars/user/1611/4835367_Jmysy_1578975358.png', 0, 'pearadmin@gmail.com',
        '15553726531', b'0', '1', '2021-05-02 22:05:57', '1', '2021-08-25 12:45:29', b'0', NULL, '1', '1',
        '1388197937639247873', '年 年 有 风 ，风 吹 年 年，慢 慢 即 漫 漫.');