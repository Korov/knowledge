# Redis in Action

# 1 初识Redis

Redis提供了5种不同类型的数据结构，各式各样的问题都可以很自然地映射到这些数据结构上，除此之外，通过复制、持久化和客户端分片等特性，用户可以很方便地将Redis扩展成一个能够包含数百GB数据、每秒处理上百万次请求的系统。

## 1.1 Redis简介

Redis是一个速度非常快的非关系数据库，它可以存储键与5种不同类型的值之间的映射，它可以将存储在内存的键值对数据持久化到硬盘，可以使用复制特性来扩展读性能，还可以使用客户端分片来扩展写性能。

### 1.1.1 Redis与其他数据库软件的对比

**Redis不是用表**。高性能键值缓存服务器memcached与Redis对比：两者都可以用于存储键值映射，彼此的性能也相差无几，但Redis能够自动以两种不同的方式将数据写入硬盘，并且Redis除了能存储普通的字符串键之外，还可以存储其他4种数据结构，而memcached只能存储普通的字符串键。

### 1.1.2 附加特性

Redis有两种不同形式的持久化方法，他们都可以用小而紧凑的格式将存储在内存中的数据写入硬盘：

1. 时间点转储，转储操作既可以在“指定时间段内有指定数量的写操作执行”这一条被满足时执行，又可以通过调用两条转储到硬盘命令中的任何一条来执行
2. 将所有修改了数据库的命令都写入一个追加文件里面，用户可以根据数据的重要程度，将只追加写入设置为不同步，每秒同步一次或者每写入一个命令就同步一次。

## 1.2 Redis数据结构简介

Redis可以存储键与5种不同数据结构类型之间的映射，这5种数据结构类型分别为STRING（字符串）、LIST（列表）、SET（集合）、HASH（散列）、和ZSET（有序集合）。

| 结构类型 | 结构存储的值                                                 | 结构的读写能力                                               |
| -------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| STRING   | 可以是字符串、证书或者浮点数                                 | 对整个字符串或者字符串的其中一部分执行操作；对整数和浮点数执行自增或者自减操作 |
| LIST     | 一个链表，链表上的每个节点都包含了一个字符串                 | 从链表的两端推入或者弹出元素；根据偏移量对链表进行修剪；读取单个或者多个元素；根据值查找或者移除元素 |
| SET      | 包含字符串的无序收集器，并且被包含的每个字符串都是独一无二的、各不相同 | 添加、获取、移除单个元素；检查一个元素是否存在于集合中；计算交集、并集、差集；从几何里面随机获取元素 |
| HASH     | 包含键值对的无序散列表                                       | 添加、获取、移除单个键值对；获取所有键值对                   |
| ZSET     | 字符串成员与浮点数分值之间的有序映射，元素的排列顺序由分值的大小决定 | 添加、获取、移除单个元素；根据分值范围或者成员来获取元素     |

### 1.2.1 Redis中的字符串

![image-20191126205034438](picture\image-20191126205034438.png)

| 命令 | 行为                                               | 示例                                      |
| ---- | -------------------------------------------------- | ----------------------------------------- |
| GET  | 获取存储在给定键值中的值                           | get hello                                 |
| SET  | 设置存储在给定键中的值                             | set hello world(key为hello，value为world) |
| DEL  | 删除存储在给定键中的值（这个命令可以用于所有类型） | del hello                                 |

### 1.2.2 Redis中的列表

![image-20191126205430753](picture\image-20191126205430753.png)

| 命令   | 行为                           | 示例                                                         |
| ------ | ------------------------------ | ------------------------------------------------------------ |
| LPUSH  | 将给定值推入列表的左端         |                                                              |
| RPUSH  | 将给定值推入列表的右端         | rpush list-key item(可以跟随多个值)                          |
| LRANGE | 获取列表在给定范围上的所有值   | lrange key start stop(start表示起始位置从0开始，stop表示结束位置-1表示最后) |
| LINDEX | 获取列表在给定位置上的单个元素 | lindex key index                                             |
| LPOP   | 从列表的左端弹出一个值         |                                                              |
| RPOP   | 从列表的右端弹出一个值         |                                                              |

### 1.2.3 Redis的集合

![image-20191126210647676](picture\image-20191126210647676.png)

| 命令      | 行为                                         | 示例                          |
| --------- | -------------------------------------------- | ----------------------------- |
| SADD      | 将给定元素添加到集合                         | sadd key member [members ...] |
| SMEMBERS  | 返回集合包含的所有元素                       | smembers key                  |
| SISMEMBER | 检查给定元素是否存在于集合中                 | sismember key member          |
| SREM      | 如果给定的元素存在于集合中，那么移除这个元素 | srem key member [members ...] |

### 1.2.4 Redis的散列

![image-20191126211239902](picture\image-20191126211239902.png)

| 命令    | 行为                                     | 示例                       |
| ------- | ---------------------------------------- | -------------------------- |
| HSET    | 在散列里面关联起给定的键值对             | hset key field value       |
| HGET    | 获取指定散列的值                         | hget key field             |
| HGETALL | 获取散列包含的所有键值对                 | hgetall key                |
| HDEL    | 如果给定键存在于散列里面，那么移除这个键 | hdel key field [field ...] |

### 1.2.5 Redis的有序集合

![image-20191126211806660](picture\image-20191126211806660.png)

这是一个**集合**，里面存储一个member（成员）和一个score（分值），分值必须为浮点数，可以根据成员访问元素，又可以根据分值以及分值的排列顺序来访问元素。有序集合使用分值来排序。

| 命令          | 行为                                                       | 实例                                                         |
| ------------- | ---------------------------------------------------------- | ------------------------------------------------------------ |
| ZADD          | 将一个带有给定分值的成员添加到有序集合里面                 | zadd key score member                                        |
| ZRANGE        | 根据元素在有序排列中所处的位置，从有序集合里面获取多个元素 | zrange key start stop [withscores]                           |
| ZRANGEBYSCORE | 获取有序集合在给定分值范围内的所有元素                     | ZRANGEBYSCORE key min max [WITHSCORES] [LIMIT offset count]，`min` 和 `max` 可以是 `-inf` 和 `+inf`代表无限大和无限小。默认为大于等于min小于等于max。limit代表分页查询，offset起始位置，count获取的数量 |
| ZREM          | 如果给定成员存在于有序集合，那么移除这个成员               | zrem key member [member ...]                                 |

# 3 Redis命令

## 3.1 字符串

字符串是一个由字节组成的序列，可以存储以下3种类型的值：

1. 字节串（byte string）
2. 整数
3. 浮点数

可以通过给定的任意数值对存储着整数或者浮点数的字符串执行自增或者自减操作，在有需要的时候，Redis还会将整数转换成浮点数。整数的取值范围和系统的长整数的取值范围相同（32位系统上，整数就是32位有符号整数），而浮点数的取值范围和精度则与IEEE754标准的双精度浮点数（double）相同。

**Redis中的自增命令和自减命令**

| 命令        | 描述                         | 示例                      |
| ----------- | ---------------------------- | ------------------------- |
| INCR        | 将键存储的值加1              | incr key                  |
| DECR        | 将键存储的值减1              | decr key                  |
| INCRBY      | 将键存储的值加上指定整数     | incrby key increment      |
| DECRBY      | 将键存储的值减去指定整数     | decrby key decrement      |
| INCRBYFLOAT | 将键存储的值加上指定的浮点数 | incrbyfloat key increment |

**Redis处理子串和二进制位的命令**

| 命令     | 描述                                                         | 示例                                  |
| -------- | ------------------------------------------------------------ | ------------------------------------- |
| APPEND   | 将value追加到给定键当前存储的值得末尾                        | append key value                      |
| GETRANGE | 获取一个由偏移量start至偏移量end范围内所有字符串组成的子串，包括start和end在内 | getrange key start end                |
| SETRANGE | 将从start偏移量开始的子串设置为给定值                        | setrange key offset value             |
| GETBIT   | 将字节串看作是二进制位串，并返回位串中偏移量为offset的二进制位的值 | getbit key offset                     |
| SETBIT   | 将字节串看做是二进制位串，并将位串中偏移量为offset的二进制位的值设置为value | setbit key offset value               |
| BITCOUNT | 统计二进制位串里面值为1的二进制位的数量，如果给定了可选的start偏移量和end偏移量，那么只对偏移量指定范围内的二进制位进行统计局 | bitcount key [start end]              |
| BITOP    | 对一个或多个二进制位串执行包括并（and）、或（or）、异或（xor）、非（not）在内的任意一种按位运算操作，并将计算得出的结果保存在间destkey键里面 | bitop operation destkey key [key ...] |

在使用setrange或者setbit命令对字符串进行写入的时候，如果字符串当前长度不能满足写入的要求，那么Redis会自动地使用空字节（null）来将字符串扩展至所需的长度，然后才执行写入或者更新操作。在使用getrange读取字符串的时候，超出字符串末尾的数据会被视为空串，而在使用getbit读取二进制位串的时候，超出字符串末尾的二进制位会被视为是0。

## 3.2 列表

以下几个命令可以将元素从一个列表移动到另一个列表，或者阻塞执行命令的客户端直到有其他客户端给列表添加元素为止

| 命令       | 描述                                                         | 示例                                  |
| ---------- | ------------------------------------------------------------ | ------------------------------------- |
| BLPOP      | 从第一个非空列表中弹出位于最左端的元素，或者在timeout秒之内阻塞并等待可弹出的元素出现 | blpop key [key ...] timeout           |
| BRPOP      | 从第一个非空列表中弹出位于最右端的元素，或者在timeout秒之内阻塞并等待可弹出的元素出现 | brpop key [key ...] timeout           |
| RPOPLPUSH  | 从source（key值）列表中弹出位于最右端的元素，然后将这个元素推入destination列表的最左端，并向用户返回这个元素 | rpoplpush source destination          |
| BRPOPLPUSH | 从source（key值）列表中弹出位于最右端的元素，然后将这个元素推入destination列表的最左端，并向用户返回这个元素；如果source为空，那么在timeout秒之内阻塞并等待可弹出的元素出现 | brpoplpush source destination timeout |

## 3.3 集合

| 命令        | 描述                                                         | 示例                            |
| ----------- | ------------------------------------------------------------ | ------------------------------- |
| SCARD       | 返回集合包含的元素的数量                                     | scard key                       |
| SRANDMEMBER | 从集合里面随机地返回一个或多个元素，当count为正数时，命令返回的随机元素不会重复，当count为负数时，命令返回的随机元素可能会出现重复 | srandmember key [count]         |
| SPOP        | 随机地移除集合中的一个或多个元素，并返回被移除的元素         | spop key [count]                |
| SMOVE       | 如果集合source包含元素Item，那么从集合source里面移除元素item，并将元素添加到集合destination中；如果item被成功移除，那么命令返回1，否则返回0。 | smove source destination member |

**用于组合和处理多个集合的Redis命令**

| 命令        | 描述                                                         | 示例                                  |
| ----------- | ------------------------------------------------------------ | ------------------------------------- |
| SDIFF       | 返回那些存在第一个集合但并不存在于其他集合中的元素           | sdiffstore key [key ...]              |
| SDIFFSTORE  | 将那些存在第一个集合但并不存在于其他集合中的元素存储到destination中，差集 | sdiffstore destination key [key ...]  |
| SINTER      | 返回那些同时存在于所有集合中的元素，交集                     | sinter key [key ...]                  |
| SINTERSTORE | 将那些同时存在于所有集合的元素存储到destination，交集        | sinterstore destination key [key ...] |
| SUNION      | 返回那些至少存在于一个集合中的元素，并集                     | sunion key [key ...]                  |
| SUNIONSTORE | 将那些至少存在于一个集合中的元素存储到destination中，并集    | sunion destination key [key ...]      |

## 3.4 散列

| 命令  | 描述                           | 示例                                    |
| ----- | ------------------------------ | --------------------------------------- |
| HMGET | 从散列里面获取一个或多个键的值 | hmget key field [field ...]             |
| HMSET | 为散列里面的一个或多个键设置值 | hmset key field value [field value ...] |
| HLEN  | 返回散列包含的键值对数量       | hlen key                                |

**Redis散列的更高级特性**

| 命令         | 描述                               | 示例                             |
| ------------ | ---------------------------------- | -------------------------------- |
| HEXISTS      | 检查给定键是否存在于散列中         | hexists key field                |
| HKEYS        | 获取散列包含的所有键               | hkeys key                        |
| HVALS        | 获取散列包含的所有值               | hvals key                        |
| HGETALL      | 获取散列包含的所有键值对           | hgetall key                      |
| HINCRBY      | 将键key存储的值加上整数increment   | hincrby key field increment      |
| HINCRBYFLOAT | 将键key存储的值加上浮点数increment | hincrbyfloat key field increment |

## 3.5 有序集合

| 命令    | 描述                               | 示例                         |
| ------- | ---------------------------------- | ---------------------------- |
| ZCARD   | 返回有序集合包含的成员数量         | zcard key                    |
| ZINCRBY | 将member成员的分值加上increment    | zincrby key increment member |
| ZCOUNT  | 返回分值介于min和max之间的成员数量 | zcount key min max           |
| ZRANK   | 返回成员member在有序集合中的排名   | zrank key member             |
| ZSCORE  | 返回成员member的分值               | zscore key member            |

**有序集合的范围型数据获取命令和范围型数据删除命令，以及并集命令和交集命令**

| 命令             | 描述                                                         | 示例                                                         |
| ---------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| ZREVERANK        | 返回有序集合里成员member的排名，成员按照分值从大到小排列     | zrevrank key member                                          |
| ZREVRANGE        | 返回有序集合给定排名范围内的成员，成员按照分值从大到小       | zrevrange key start stop [withscores]                        |
| ZRANGEBYSCORE    | 返回有序集合中，分值介于min和max之间的所有成员               | zrangebyscore key min max [withscores] [limit offset count]  |
| ZREVRANGEBYSCORE | 获取有序集合中分值介于min和max之间的所有成员，并按照分值从大到小的顺序来返回他们 | zrevrangebyscore key min max [withscores] [limit offset count] |
| ZREMRANGEBYRANK  | 移除有序集合中排名介于start和stop之间的所有成员              | zremrangebyrank key start stop                               |
| ZREMRANGEBYSCORE | 移除有序集合中分值介于min和max之间的所有成员                 | zremrangebyscore key min max                                 |
| ZINTERSTORE      | 对给定的有序集合执行类似于集合的交集运算，numkeys表示对多少个集合进行交集运算，sum表示对分值进行加操作（默认） | ZINTERSTORE destination numkeys key [key ...] [WEIGHTS weight] [AGGREGATE SUM\|MIN\|MAX] |
| ZUNIONSTORE      | 对给定的有序集合执行类似于集合的并集运算                     | ZUNIONSTORE destination numkeys key [key ...] [WEIGHTS weight] [AGGREGATE SUM\|MIN\|MAX] |

## 3.6 发布于订阅

发布于订阅（pub/sub）的特点是订阅者（listener）负责订阅频道（channel），发送者（publisher）负责向频道发送二进制字符串消息（binary string message）。每当有消息被发送至给定频道时，频道所有订阅者都会收到消息。

| 命令         | 描述                                                         | 示例                                 |
| ------------ | ------------------------------------------------------------ | ------------------------------------ |
| SUBSCRIBE    | 订阅给定的一个或多个频道                                     | subscribe channel [channel ...]      |
| UNSUBSCRIBE  | 退订给定的一个或多个频道，如果执行时没有给定任何频道，那么退订所有频道 | unsubscribe [channel [channel ...]]  |
| PUBLISH      | 向给定频道发送消息                                           | publish channel message              |
| PSUBSCRIBE   | 订阅与给定模式想匹配的所有频道                               | psubscribe pattern [pattern ...]     |
| PUNSUBSCRIBE | 退订给定的模式，如果执行时没有给定任何模式，那么退订所有模式 | punsubscribe [pattern [pattern ...]] |

慎用：Redis系统的稳定性不高，过多的发布，很少的订阅，可能导致内存急剧膨胀，导致性能下降，甚至Redis直接被杀死。数据传输的可靠性不高，断线的时候有可能会丢失所有断线期间发布的消息。

## 3.7 其他命令

### 3.7.1 排序

| 命令 | 描述                                                         | 示例                                                         |
| ---- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| SORT | 根据给定的选项，对输入列表、集合或者有序集合进行排序，然后返回或者存储排序的结果 | sort key [by pattern] [limit offset count] [get pattern [get pattern ...]] [asc\|desc] [alpha] [store destination] |

 排序默认以数字作为对象，值被解释为双精度浮点数，然后进行比较 

 因为 SORT 命令默认排序对象为数字， 当需要对字符串进行排序时， 需要显式地在 SORT 命令之后添加 `ALPHA` 修饰符： SORT website ALPHA

**使用外部key进行排序**

![image-20191127150824089](picture\image-20191127150824089.png)

将uid存入一个list中，把name和level存入字符串中，

```bash
lpush uid 1
set user_name_1 admin
set user_level_1 9999
#下面依次是2,3,4
```

```bash
redis 127.0.0.1:6379> SORT uid
1) "1"      # admin
2) "2"      # jack
3) "3"      # peter
4) "4"      # mary

redis 127.0.0.1:6379> SORT uid BY user_level_*
1) "2"      # jack , level = 10
2) "3"      # peter, level = 25
3) "4"      # mary, level = 70
4) "1"      # admin, level = 9999
```

 `user_level_*` 是一个占位符， 它先取出 `uid` 中的值， 然后再用这个值来查找相应的键 ， 比如在对 `uid` 列表进行排序时， 程序就会先取出 `uid` 的值 `1` 、 `2` 、 `3` 、 `4` ， 然后使用 `user_level_1` 、 `user_level_2` 、 `user_level_3` 和 `user_level_4` 的值作为排序 `uid` 的权重。 

**GET选项**

 使用 `GET` 选项， 可以根据排序的结果来取出相应的键值 ， 比如说， 以下代码先排序 `uid` ， 再取出键 `user_name_{uid}` 的值： 

```bash
redis 127.0.0.1:6379> SORT uid GET user_name_*
1) "admin"
2) "jack"
3) "peter"
4) "mary"

#组合使用BY和GET
redis 127.0.0.1:6379> SORT uid BY user_level_* GET user_name_*
1) "jack"       # level = 10
2) "peter"      # level = 25
3) "mary"       # level = 70
4) "admin"      # level = 9999
```

将哈希表作为GET或BY的参数

```bash
HMSET user_info_1 name admin level 9999
#等
redis 127.0.0.1:6379> SORT uid BY user_info_*->level
1) "2"
2) "3"
3) "4"
4) "1"

redis 127.0.0.1:6379> SORT uid BY user_info_*->level GET user_info_*->name
1) "jack"
2) "peter"
3) "mary"
4) "admin"
```

### 3.7.2 基于Redis事务

Redis有5个命令可以让用户在不被打断的情况下对多个键执行操作，他们分别是WATCH,MULTI,EXEC,UNWATCH和DISCARD。

Redis的基本事务需要用到multi和exec命令，这种事务可以让一个客户端在不被其他客户打断的情况下执行多个命令。和关系数据库那种可以在执行的过程中进行回滚的事务不同，在Redis里面，被multi和exec命令包含的所有命令会一个接一个的执行，事务中任意命令执行失败，其余的命令依然被执行，直到所有命令都执行完毕为止。当一个事务执行完毕之后，Redis才会处理其他客户端的命令。

当Redis从一个客户端那里接受到multi命令时，Redis会将这个客户端之后发送的所有命令都放入到一个队列里面，直到这个客户端发送exec命令为止，然后Redis就会在不被打断的情况下，一个接一个的执行存储队列里面的命令。

- DISCARD：取消事务，放弃执行事务块内的所有命令
- UNWATCH：取消WATCH命令对所有key的监视
- WATCH key [key ...]：监视一个或多个key，如果在事务执行之前这个key被其他命令所改动，那么事务将被打断

```bash
redis 127.0.0.1:6379> MULTI
OK

redis 127.0.0.1:6379> PING
QUEUED

redis 127.0.0.1:6379> SET greeting "hello"
QUEUED

redis 127.0.0.1:6379> DISCARD
OK
```



### 3.7.3 键的过期时间

| 命令      | 描述                                               | 示例                                 |
| --------- | -------------------------------------------------- | ------------------------------------ |
| PERSIST   | 移除键的过期时间                                   | persist key                          |
| TTL       | 查看给定键距离过期还有多少秒                       | ttl key                              |
| EXPIRE    | 让给定键在指定的秒数之后过期                       | expire key seconds                   |
| EXPIREAT  | 将给定键的过期时间设置为给定的UNIX时间戳           | expireat key timestamp               |
| PTTL      | 查看给定键距离过期时间还有多少毫秒                 | pttl key                             |
| PEXPIRE   | 让给定键在指定的毫秒数之后过期                     | pexpire key milliseconds             |
| PEXPIREAT | 将一个毫秒级精度的UNIX时间戳设置为给定键的过期时间 | pexpireat key milliseconds-timestamp |

# 4 数据安全与性能保障

本章主要内容：

- 将数据持久化至硬盘
- 将数据恢复至其他机器
- 处理系统故障
- Redis事务
- 非实物型流水线
- 诊断性能问题

## 4.1 持久化选项

Redis提供了两种不同的持久化方法来讲数据存储到硬盘里面。一种方法交**快照**，它可以将存在某一时刻的所有数据都写入硬盘里面。另一种方法叫**只追加文件**（AOF），它会在执行写命令时，将被执行的写命令复制到硬盘里面。

```bash
RDB配置
1:命令格式
save <seconds> <changes>
 当用户设置了多个save的选项配置，只要其中任一条满足，Redis都会触发一次BGSAVE操作，
 比如：900秒之内至少一次写操作、300秒之内至少发生10次写操作、60秒之内发生至少10000次写操作都会触发发生快照操作
save 900 1(900秒之内至少一次写操作)
save 300 10(300秒之内至少10次写操作)
save 60 10000(60秒之内至少10000次写操作)
*禁用RDB，save ""
2:文件名
dbfilename:dump.rdb(默认)
3：写配置
stop-writes-on-bgsave-error:yes(默认后台写错误则停止写快照文件)
4：rdb文件是否需要压缩
rdbcompression:yes(默认压缩)
5：rdb校验
rdbchecksum:yes(默认存储快照后，让redis使用CRC64校验)

AOF配置
1:开启AOF
appendonly：no(默人不开启，修改为yes开启)
2：文件名
appendfilename:appendonly.aof(默认)
3：同步持久化选项
appendsync:(always:同步持久化、everysencond:每秒、no:不开启)
4：rewrite
no-appendsync-on-rewrite:no(保持数据完整性)
auto-aof-rerite-min-size:100(基准大于1倍开始触发rewrite)
auto-aof-rewrite-percentage:64mb（文件大于64mb触发rewrite）

AOF文件会越来越大，当文件大小超过设定的域值时，redis会启动AOF的文件内容压缩，只保留可以恢复的数据的最小指令集<bgrewriteaof>
1：原理
主进程会fork出一条新的进程对文件重写，遍历新进程的内存数据，每条记录有一条set语句。重写aof文件的操作并没有读取旧的aof文件，而是将整个内存的数据内容用命令的方式重写了一个新的aof文件
2:触发
配置文件中设置了aofrewrite策略、当aof的大小超过了设定的值、当有客户端要求rewrite操作时。redis会记录上次重写的AOF的大小，默认设置当前AOF文件大小是上次的rewrite后的大小的1倍且文件大于64mb
3:配置
no-appendsync-on-rewrite:no(保持数据完整性)
auto-aof-rerite-min-size:100(基准大于1倍开始触发rewrite)
auto-aof-rewrite-percentage:64mb（文件大于64mb触发rewrite）


RDB触发
1：触发
save/bgsave
2：恢复
将dump.rdb移到redis启动目录即可
3：停止
redis-cli config set save ""
4:修复
redis-check-dump
**shutdown/flushall/save都会立即commit到dump.rdb，所以，一旦flushall，rdb文件就会清空内容

AOF触发
1：触发
一旦AOF配置完成，重启就立即触发
2:恢复
重启redis重新加载
3：加载
如果aof和rdb同时存在，服务器启动，redis默认加载AOF
4：修复
redis-check-aof --fix appendonly.aof
**flushall也会写入到aof文件中，一旦恢复，所有数据将被清空

共享选项，这个选项决定了快照文件和AOF文件的保存位置
dir ./  
```

## 4.2 复制

Redis采用类类似关系数据库的方式来实现复制，使用一个主服务器（master）向多个从服务器（slave）发送更新，并使用从服务器来处理所有读请求。

### 4.2.1 对Redis的复制相关选项进行配置

当从服务器连接主服务器的时候，主服务器会执行BGSAVE操作。因此为了正确地使用复制特性，用户需要保证主服务器已经正确地设置了RDB的配置包括dir选项和dbfilename选项，并且这两个选项所指示的路径和文件对于Redis进程来说都是可写的。

开启从服务器所必须的选项只有slaveof一个。如果用户在启动Redis服务器的时候，指定了一个包含slaveof host port选项的配置文件，那么Redis服务器将根据选项给顶的IP和端口号来链接主服务器。对于一个正在运行的Redis服务器，可以通过发送slaveof no one命令来让服务器终止复制操作，不再接受主服务器的数据更新；也可以通过发送slaveof host port 命令来让服务器开始复制一个新的主服务器。

### 4.2.2 Redis复制的启动过程

| 步骤 | 主服务器操作                                                 | 从服务器操作                                                 |
| ---- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 1    | 等待命令进入                                                 | 连接（或者重连接）主服务器，发送SYNC命令                     |
| 2    | 开始执行BGSAVE，并使用缓冲区记录BGSAVE之后执行的所有写命令   | 根据配置选项来决定是继续使用现有的数据（如果有的话）来处理客户端的命令请求，还是向发送请求的客户端返回错误 |
| 3    | BGSAVE执行完毕，向从服务器发送快照文件，并在发送期间继续使用缓冲区记录被执行的写命令 | 丢弃所有旧数据（如果有的话），开始载入主服务器发来的快照文件 |
| 4    | 快照文件发送完毕，开始想从服务器发送存储在缓冲区里面的写命令 | 完成对快照文件的解释操作，想往常一样开始接受命令请求         |
| 5    | 缓冲区存储的写命令发送完毕，从现在开始，每执行一个写命令，就想服务器发送相同的写命令 | 执行主服务器发来的所有存储在缓冲区里面的写命令；并从现在开始，接收并执行主服务器传来的每个写命令 |

**从服务器在进行同步时，会清空自己的所有数据。**

## 4.5 非事务型流水线

 Redis是一种基于客户端-服务端模型以及请求/响应协议的TCP服务。这意味着通常情况下一个请求会遵循以下步骤 ：

1.  客户端向服务端发送一个查询请求，并监听Socket返回，通常是以阻塞模式，等待服务端响应。 
2.  服务端处理命令，并将结果返回给客户端 

 Redis 管道技术可以在服务端未响应时，客户端可以继续向服务端发送请求，并最终一次性读取所有服务端的响应。 

将多条命令一起传递到Redis执行，减少交互次数，可以极大提高Redis的性能。

```Python
import redis
import time
#使用管道执行一串命令，速度有明显提升
r = redis.Redis(password='xiemanrui')
s = time.time()
p = r.pipeline()
for i in range(10000):
    p.set('foo', 'bar%s' % i)
p.execute()
print(time.time() - s)
```

# Redis应用

## 分布式锁

在Redisson框架实现分布式锁的思路，就使用watchDog机制实现锁的续期。当加锁成功后，同时开启守护线程，默认有效期是30秒，每隔10秒就会给锁续期到30秒，只要持有锁的客户端没有宕机，就能保证一直持有锁，直到业务代码执行完毕由客户端自己解锁，如果宕机了自然就在有效期失效后自动解锁。

但是聪明的同学可能又会问，你这个锁只能加一次，不可重入。可重入锁意思是在外层使用锁之后，内层仍然可以使用，那么可重入锁的实现思路又是怎么样的呢？在Redisson实现可重入锁的思路，使用Redis的哈希表存储可重入次数，当加锁成功后，使用hset命令，value(重入次数)则是1。

如果同一个客户端再次加锁成功，则使用hincrby自增加一。

解锁时，先判断可重复次数是否大于0，大于0则减一，否则删除键值，释放锁资源。

上面的加锁方法是加锁后立即返回加锁结果，如果加锁失败的情况下，总不可能一直轮询尝试加锁，直到加锁成功为止，这样太过耗费性能。所以需要利用发布订阅的机制进行优化。步骤如下：当加锁失败后，订阅锁释放的消息，自身进入阻塞状态。当持有锁的客户端释放锁的时候，发布锁释放的消息。当进入阻塞等待的其他客户端收到锁释放的消息后，解除阻塞等待状态，再次尝试加锁。

# 自我总结

## 1.切换db

每个Redis默认有16个db，通过select index切换到指定的db

