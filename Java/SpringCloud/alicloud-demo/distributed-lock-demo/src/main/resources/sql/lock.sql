drop database if exists `lock_demo`;
create database `lock_demo` character set utf8mb4 collate utf8mb4_general_ci;
use `lock_demo`;

# resources 属性被设置为唯一的，可以根据自己的需求增减属性
drop table if exists `table_lock_method`;
CREATE TABLE `table_lock_method` (
`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
`resources` varchar(64) NOT NULL DEFAULT '' COMMENT '锁定的资源名称',
`state` int NOT NULL COMMENT '1:未分配；2：已分配',
`version` int NOT NULL COMMENT '版本号',
`time_begin` long not null comment '线程开始时间',
`time_valid` long not null comment '线程有效时间',
`desc` varchar(1024) NOT NULL DEFAULT '备注信息',
`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '保存数据时间，自动生成',
PRIMARY KEY (`id`),
UNIQUE KEY `key_method_name` (`resources`) USING BTREE ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='锁定中的方法';