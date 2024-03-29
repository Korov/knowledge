Seata是一款开源的分布式事务解决方案，致力于提供高性能和简单易用的分布式事务服务。Seata 将为用户提供了 AT、TCC、SAGA 和 XA 事务模式，为用户打造一站式的分布式解决方案。

## AT模式

### 前提：

1.  基于支持本地ACID事务的关系型数据库
    
2.  Java应用，通过JDBC访问数据库
    

### 整体机制

两阶段提交协议的演变：

1.  一阶段：业务数据和回滚日志记录在同一个本地事务中提交，释放本地锁和连接资源。
    
2.  二阶段：
    
    1.  提交异步化，非常快速地完成。
        
    2.  回滚通过一阶段的回滚日志进行反向补偿。
        
    

### 写隔离

1.  一阶段本地事务提交前，需要确保先拿到 全局锁 。
    
2.  拿不到 全局锁 ，不能提交本地事务。
    
3.  拿 全局锁 的尝试被限制在一定范围内，超出范围将放弃，并回滚本地事务，释放本地锁。
    

以一个示例来说明：

两个全局事务 tx1 和 tx2，分别对 a 表的 m 字段进行更新操作，m 的初始值 1000。

tx1 先开始，开启本地事务，拿到本地锁，更新操作 m = 1000 - 100 = 900。本地事务提交前，先拿到该记录的 全局锁 ，本地提交释放本地锁。 tx2 后开始，开启本地事务，拿到本地锁，更新操作 m = 900 - 100 = 800。本地事务提交前，尝试拿该记录的 全局锁 ，tx1 全局提交前，该记录的全局锁被 tx1 持有，tx2 需要重试等待 全局锁 。

![image](http://korov.myqnapcloud.cn:19000/images/TB1zaknwVY7gK0jSZKzXXaikpXa-702-521.png)

tx1 二阶段全局提交，释放 全局锁 。tx2 拿到 全局锁 提交本地事务。

![image](http://korov.myqnapcloud.cn:19000/images/TB1xW0UwubviK0jSZFNXXaApXXa-718-521.png)

如果 tx1 的二阶段全局回滚，则 tx1 需要重新获取该数据的本地锁，进行反向补偿的更新操作，实现分支的回滚。

此时，如果 tx2 仍在等待该数据的 全局锁，同时持有本地锁，则 tx1 的分支回滚会失败。分支的回滚会一直重试，直到 tx2 的 全局锁 等锁超时，放弃 全局锁 并回滚本地事务释放本地锁，tx1 的分支回滚最终成功。

因为整个过程 全局锁 在 tx1 结束前一直是被 tx1 持有的，所以不会发生 脏写 的问题。

### 读隔离

在数据库本地事务隔离级别 读已提交（Read Committed） 或以上的基础上，Seata（AT 模式）的默认全局隔离级别是 读未提交（Read Uncommitted） 。

如果应用在特定场景下，必需要求全局的 读已提交 ，目前 Seata 的方式是通过 SELECT FOR UPDATE 语句的代理。

SELECT FOR UPDATE 语句的执行会申请 全局锁 ，如果 全局锁 被其他事务持有，则释放本地锁（回滚 SELECT FOR UPDATE 语句的本地执行）并重试。这个过程中，查询是被 block 住的，直到 全局锁 拿到，即读取的相关数据是 已提交 的，才返回。

出于总体性能上的考虑，Seata 目前的方案并没有对所有 SELECT 语句都进行代理，仅针对 FOR UPDATE 的 SELECT 语句。

### 工作机制

以一个示例来说明整个 AT 分支的工作过程。

业务表： `product`

  
| Field | Type         | Key |
| ----- | ------------ | --- |
| id    | bigint(20)   | PRI |
| name  | varchar(100) |     |
| since | varchar(100) |     |


AT 分支事务的业务逻辑： `update product set name = 'GTS' where name = 'TXC';`

### 一阶段

过程：

1.  解析SQL：得到 SQL 的类型（UPDATE），表（product），条件（where name = 'TXC'）等相关的信息。
    
2.  查询前镜像：根据解析得到的条件信息，生成查询语句，定位数据。 `select id, name, since from product where name = 'TXC';`
    

得到前镜像：

<table><colgroup><col> <col> <col></colgroup><tbody><tr><td><p>id</p></td><td><p>name</p></td><td><p>since</p></td></tr><tr><td><p>1</p></td><td><p>TXC</p></td><td><p>2014</p></td></tr></tbody></table>

1.  执行业务 SQL：更新这条记录的 name 为 'GTS'。
    
2.  查询后镜像：根据前镜像的结果，通过 主键 定位数据。 `select id, name, since from product where id = 1;`
    
    <table><tbody><tr><td><i title="Note"></i></td><td><p>注意事项</p><p>得到前镜像：</p><table><colgroup><col> <col> <col></colgroup><tbody><tr><td><p>id</p></td><td><p>name</p></td><td><p>since</p></td></tr><tr><td><p>1</p></td><td><p>GTS</p></td><td><p>2014</p></td></tr></tbody></table></td></tr></tbody></table>
    
3.  插入回滚日志：把前后镜像数据以及业务 SQL 相关的信息组成一条回滚日志记录，插入到 UNDO\_LOG 表中。
    
    ```
    {
    "branchId": 641789253,
    "undoItems": [{
    "afterImage": {
    "rows": [{
    "fields": [{
    "name": "id",
    "type": 4,
    "value": 1
    }, {
    "name": "name",
    "type": 12,
    "value": "GTS"
    }, {
    "name": "since",
    "type": 12,
    "value": "2014"
    }]
    }],
    "tableName": "product"
    },
    "beforeImage": {
    "rows": [{
    "fields": [{
    "name": "id",
    "type": 4,
    "value": 1
    }, {
    "name": "name",
    "type": 12,
    "value": "TXC"
    }, {
    "name": "since",
    "type": 12,
    "value": "2014"
    }]
    }],
    "tableName": "product"
    },
    "sqlType": "UPDATE"
    }],
    "xid": "xid:xxx"
    }
    ```
    
4.  提交前，向 TC 注册分支：申请 product 表中，主键值等于 1 的记录的 全局锁 。
    
5.  本地事务提交：业务数据的更新和前面步骤中生成的 UNDO LOG 一并提交。
    
6.  将本地事务提交的结果上报给 TC。
    

### 二阶段-回滚

1.  收到 TC 的分支回滚请求，开启一个本地事务，执行如下操作。
    
2.  通过 XID 和 Branch ID 查找到相应的 UNDO LOG 记录。
    
3.  数据校验：拿 UNDO LOG 中的后镜与当前数据进行比较，如果有不同，说明数据被当前全局事务之外的动作做了修改。这种情况，需要根据配置策略来做处理，详细的说明在另外的文档中介绍。
    
4.  根据 UNDO LOG 中的前镜像和业务 SQL 的相关信息生成并执行回滚的语句： `update product set name = 'TXC' where id = 1;`
    
5.  提交本地事务。并把本地事务的执行结果（即分支事务回滚的结果）上报给 TC。
    

### 二阶段-提交

1.  收到 TC 的分支提交请求，把请求放入一个异步任务的队列中，马上返回提交成功的结果给 TC。
    
2.  异步任务阶段的分支提交请求将异步和批量地删除相应 UNDO LOG 记录。
    

### 回滚日志表

```
-- 注意此处0.7.0+ 增加字段 context
CREATE TABLE `undo_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `branch_id` bigint(20) NOT NULL,
  `xid` varchar(100) NOT NULL,
  `context` varchar(128) NOT NULL,
  `rollback_info` longblob NOT NULL,
  `log_status` int(11) NOT NULL,
  `log_created` datetime NOT NULL,
  `log_modified` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
```

## TCC模式

回顾总览中的描述：一个分布式的全局事务，整体是 两阶段提交 的模型。全局事务是由若干分支事务组成的，分支事务要满足 两阶段提交 的模型要求，即需要每个分支事务都具备自己的：

-   一阶段 prepare 行为
    
-   二阶段 commit 或 rollback 行为
    

![TB14Kguw1H2gK0jSZJnXXaT1FXa 853 482](http://korov.myqnapcloud.cn:19000/images/TB14Kguw1H2gK0jSZJnXXaT1FXa-853-482.png)

根据两阶段行为模式的不同，我们将分支事务划分为 Automatic (Branch) Transaction Mode 和 Manual (Branch) Transaction Mode.

AT 模式基于支持本地 ACID 事务 的 关系型数据库：

-   一阶段 prepare 行为：在本地事务中，一并提交业务数据更新和相应回滚日志记录。
    
-   二阶段 commit 行为：马上成功结束，自动 异步批量清理回滚日志。
    
-   二阶段 rollback 行为：通过回滚日志，自动 生成补偿操作，完成数据回滚。
    

相应的，TCC 模式，不依赖于底层数据资源的事务支持：

-   一阶段 prepare 行为：调用 自定义 的 prepare 逻辑。
    
-   二阶段 commit 行为：调用 自定义 的 commit 逻辑。
    
-   二阶段 rollback 行为：调用 自定义 的 rollback 逻辑。
    

所谓 TCC 模式，是指支持把自定义的分支事务纳入到全局事务的管理中。

TCC（Try-Confirm-Cancel） 实际上是服务化的两阶段提交协议，业务开发者需要实现这三个服务接口，第一阶段服务由业务代码编排来调用 Try 接口进行资源预留，所有参与者的 Try 接口都成功了，事务管理器会提交事务，并调用每个参与者的 Confirm 接口真正提交业务操作，否则调用每个参与者的 Cancel 接口回滚事务。

TCC 模式需要用户根据自己的业务场景实现 Try、Confirm 和 Cancel 三个操作；事务发起方在一阶段执行 Try 方式，在二阶段提交执行 Confirm 方法，二阶段回滚执行 Cancel 方法。

TCC 三个方法描述：

1.  Try：资源的检测和预留；
    
2.  Confirm：执行的业务操作提交；要求 Try 成功 Confirm 一定要能成功；
    
3.  Cancel：预留资源释放；
    

### 设计：

-   允许空回滚：Cancel 接口设计时需要允许空回滚。在 Try 接口因为丢包时没有收到，事务管理器会触发回滚，这时会触发 Cancel 接口，这时 Cancel 执行时发现没有对应的事务 xid 或主键时，需要返回回滚成功。让事务服务管理器认为已回滚，否则会不断重试，而 Cancel 又没有对应的业务数据可以进行回滚。
    
-   防悬挂控制：悬挂的意思是：Cancel 比 Try 接口先执行，出现的原因是 Try 由于网络拥堵而超时，事务管理器生成回滚，触发 Cancel 接口，而最终又收到了 Try 接口调用，但是 Cancel 比 Try 先到。按照前面允许空回滚的逻辑，回滚会返回成功，事务管理器认为事务已回滚成功，则此时的 Try 接口不应该执行，否则会产生数据不一致，所以我们在 Cancel 空回滚返回成功之前先记录该条事务 xid 或业务主键，标识这条记录已经回滚过，Try 接口先检查这条事务xid或业务主键如果已经标记为回滚成功过，则不执行 Try 的业务操作。
    
-   幂等控制：幂等性的意思是：对同一个系统，使用同样的条件，一次请求和重复的多次请求对系统资源的影响是一致的。因为网络抖动或拥堵可能会超时，事务管理器会对资源进行重试操作，所以很可能一个业务操作会被重复调用，为了不因为重复调用而多次占用资源，需要对服务设计时进行幂等控制，通常我们可以用事务 xid 或业务主键判重来控制。
    

## Sega模式

Saga模式是SEATA提供的长事务解决方案，在Saga模式中，业务流程中每个参与者都提交本地事务，当出现某一个参与者失败则补偿前面已经成功的参与者，一阶段正向服务和二阶段补偿服务都由业务开发实现。

![TB1Y2kuw7T2gK0jSZFkXXcIQFXa 445 444](http://korov.myqnapcloud.cn:19000/images/TB1Y2kuw7T2gK0jSZFkXXcIQFXa-445-444.png)

长事务解决方案。Saga 是一种补偿协议，在 Saga 模式下，分布式事务内有多个参与者，每一个参与者都是一个冲正补偿服务，需要用户根据业务场景实现其正向操作和逆向回滚操作。

分布式事务执行过程中，依次执行各参与者的正向操作，如果所有正向操作均执行成功，那么分布式事务提交。如果任何一个正向操作执行失败，那么分布式事务会退回去执行前面各参与者的逆向回滚操作，回滚已提交的参与者，使分布式事务回到初始状态。

Saga 模式适用于业务流程长且需要保证事务最终一致性的业务系统，Saga 模式一阶段就会提交本地事务，无锁、长流程情况下可以保证性能。

事务参与者可能是其它公司的服务或者是遗留系统的服务，无法进行改造和提供 TCC 要求的接口，可以使用 Saga 模式。

### 适用场景

-   业务流程长、业务流程多
    
-   参与者包含其它公司或遗留系统服务，无法提供 TCC 模式要求的三个接口
    

优势：

-   一阶段提交本地事务，无锁，高性能
    
-   事件驱动架构，参与者可异步执行，高吞吐
    
-   补偿服务易于实现
    

缺点：

-   不保证隔离性
    
    <table><tbody><tr><td><i title="Note"></i></td><td>=== 由于 Saga 事务不保证隔离性, 在极端情况下可能由于脏写无法完成回滚操作, 比如举一个极端的例子, 分布式事务内先给用户A充值, 然后给用户B扣减余额, 如果在给A用户充值成功, 在事务提交以前, A用户把余额消费掉了, 如果事务发生回滚, 这时则没有办法进行补偿了。这就是缺乏隔离性造成的典型的问题, 实践中一般的应对方法是： 业务流程设计时遵循“宁可长款, 不可短款”的原则, 长款意思是客户少了钱机构多了钱, 以机构信誉可以给客户退款, 反之则是短款, 少的钱可能追不回来了。所以在业务流程设计上一定是先扣款。 有些业务场景可以允许让业务最终成功, 在回滚不了的情况下可以继续重试完成后面的流程, 所以状态机引擎除了提供“回滚”能力还需要提供“向前”恢复上下文继续执行的能力, 让业务最终执行成功, 达到最终一致性的目的。 ===</td></tr></tbody></table>
    

### 设计

允许空补偿，防悬挂控制，幂等控制，自定义事务恢复策略