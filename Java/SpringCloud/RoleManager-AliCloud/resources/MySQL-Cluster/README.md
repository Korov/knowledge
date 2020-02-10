步骤：
1. 执行setup.sh

2. 进入主数据库赋予用户相应权限
             
             >GRANT REPLICATION SLAVE ON *.* TO 'root'@'%';
             >flush privileges;
             >
             >#查看到主数据库的状态
             >
             >show master status;
             
3. 切换到从库依次执行

   > CHANGE MASTER TO
   >  MASTER_HOST='mysql-master',
   >  MASTER_PORT=3306,
   >  MASTER_USER='root',
   >  MASTER_PASSWORD='root123',
   >  MASTER_LOG_FILE='mysql-bin.000003',
   >  MASTER_LOG_POS=6143;
   > #MASTER_LOG_FILE和MASTER_LOG_POS分别对应上面主服务器中show master status显示的file属性和Position属性
   > start slave;
   > show slave status;
   > #其中Slave_IO_Running,Slave_SQL_Running必须为Yes，表示同步成功，否则执行，stop slave;将之前的动作重新执行一遍。之后我们在主库做的SQL语句执行，会同步到从库中来。