### Spring Boot 事务支持

#### Spring 事务

Spring并不直接管理事务，而是提供了许多内置事务管理实现，常用的有 `DataSourceTransactionManager`, `JdoTransactionManager`, `JpaTransactionManager` 以及 `HibernateTransactionManager` 等。

##### Spring 声明式事务

Spring配置文件中关于事务配置总是由三部分组成，分别是 `DataSource`, `TransactionManager` 和代理机制。无论哪种配置方式，一般变化的只是代理机制部分，`DataSource`, `TransactionManager` 这两部分只会根据数据访问方式有所变化，比如使用 `Hibernate` 进行数据访问时， `DataSource` 实现为 `SessionFactory`， ``TransactionManager`的实现为 `HibernateTransactionManager``。

##### Spring 注解事务行为

当事务方法被另一个事务方法调用时，必须指定事务应该如何传播。例如，方法可能继续在现有的事务中运行，也可能开启一个新事务，并在自己的事务中运行。事务的传播行为可以在 `@Transactional` 中的属性指定，Spring定义了7种传播行为，

1.  PROPAGATION\_REQUIRED：如果当前没有事务，就新建一个事务；如果已经存在一个事务，就加入这个事务
    
2.  PROPAGATION\_SUPPORTS：支持当前事务，如果当前没有事务，就以非事务方式执行
    
3.  PROPAGATION\_MANDATORY：使用当前的事务，如果当前没有事务就抛出异常
    
4.  PROPAGATION\_REQUIRES\_NEW：新建事务，如果当前存在事务，就把当前事务挂起
    
5.  PROPAGATION\_NOT\_SUPPORTED：以非事务方式执行操作，如果当前存在事务，就把当前事务挂起
    
6.  PROPAGATION\_NEVER：以非事务方式执行，如果当前存在事务，就抛出异常
    
7.  PROPAGATION\_NESTED：如果当前存在事务，就在嵌套事务内执行；如果当前没有事务，就执行PROPAGATION\_REQUIRED类似的操作
    

Spring定义了5种事务的隔离级别：

1.  ISOLATION\_DEFAULT：使用数据库默认的事务隔离级别，另外4个与JDBC的隔离级别相对应
    
2.  ISOLATION\_READ\_UNCOMMITTED：事务最低的隔离级别，允许另一个事务可以看到这个事务未提交的数据，这种隔离级别会产生脏读、不可重复读和幻读
    
3.  ISOLATION\_READ\_COMMITTED：
    
4.  ISOLATION\_REPEATABLE\_READ：
    
5.  ISOLATION\_SERIALIZABLE：
    

`@Transactional` propagation定义传播行为，isolation定义隔离级别，timeout设置事务的过期时间，readOnly指定当前事务是否是只读事务。rollbackfor（norollbackfor）指定哪个或者哪些异常可以引起（或不可以引起）事务回滚。