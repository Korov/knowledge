drop database if exists `transaction_demo`;
create database `transaction_demo` character set utf8mb4 collate utf8mb4_general_ci;
use `transaction_demo`;

# resources 属性被设置为唯一的，可以根据自己的需求增减属性
drop table if exists `transaction_log`;
CREATE TABLE `transaction_log` (
`id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
`state` varchar(1024) NOT NULL DEFAULT 'NEW' COMMENT 'NEW：新建；PUBLISHED：已经发布到MQ中；RECEIVED：MQ传送完消息并且被接收；COMPLETE：消息处理完毕',
`transaction_type` varchar(256) not null default '' comment '事务的类型，例如USER_CREATE',
`message` json not null comment '存储需要传递的信息',
`transaction_process` varchar(256) not null default 'NEW' comment '记录待处理的事件',
`desc` varchar(1024) NOT NULL DEFAULT '备注信息',
`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '数据更新时间',
PRIMARY KEY (`id`)) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='事务日志';


drop table if exists `user_info`;
create table `user_info`(
    `id` int(11) not null auto_increment,
    `name` varchar(256) not null default '',
    primary key (`id`)
) engine = InnoDB default charset =utf8mb4 comment '用户信息表';