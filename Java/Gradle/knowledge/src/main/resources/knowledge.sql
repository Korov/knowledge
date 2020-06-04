create database if not exists knowledge;
use knowledge;

drop table if exists managers;
create table managers
(
    securities_code                varchar(256) comment '证券代码',
    statistics_deadline            varchar(256) comment '统计截止日期',
    full_name                      varchar(256) comment '姓名',
    job_category                   varchar(256) comment '职务类别',
    specific_duties                varchar(256) comment '职务类别',
    gender                         varchar(256) comment '职务类别',
    age                            varchar(256) comment '职务类别',
    educational_background         varchar(256) comment '职务类别',
    start_date_of_current_position varchar(256) comment '职务类别',
    end_date_of_current_position   varchar(256) comment '职务类别'
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='管理员表';

drop table if exists managers_result;
create table managers_result
(
    securities_code                varchar(256) comment '证券代码',
    statistics_deadline            varchar(256) comment '统计截止日期',
    full_name                      varchar(256) comment '姓名',
    job_category                   varchar(256) comment '职务类别',
    specific_duties                varchar(256) comment '职务类别',
    gender                         varchar(256) comment '职务类别',
    age                            varchar(256) comment '职务类别',
    educational_background         varchar(256) comment '职务类别',
    start_date_of_current_position varchar(256) comment '职务类别',
    end_date_of_current_position   varchar(256) comment '职务类别'
) ENGINE = InnoDB
  AUTO_INCREMENT = 0
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='管理员结果表';