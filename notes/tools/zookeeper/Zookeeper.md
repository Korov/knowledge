### 客户端脚本

进入Zookeeper的bin目录之后执行如下命令

```
# 连接本地2181端口
./zkCli.sh
# 连接远程服务
./zkCli.sh -server ip:port
# 查看帮助
[zk: localhost:2181(CONNECTED) 5] help
ZooKeeper -server host:port cmd args
addauth scheme auth
close
config [-c] [-w] [-s]
connect host:port
create [-s] [-e] [-c] [-t ttl] path [data] [acl]
delete [-v version] path
deleteall path
delquota [-n|-b] path
get [-s] [-w] path
getAcl [-s] path
history
listquota path
ls [-s] [-w] [-R] path
ls2 path [watch]
printwatches on|off
quit
reconfig [-s] [-v version] [[-file path] | [-members serverID=host:port1:port2;port3[,...]*]] | [-add serverId=host:port1:port2;port3[,...]]* [-remove serverId[,...]*]
redo cmdno
removewatches path [-c|-d|-a] [-l]
rmr path
set [-s] [-v version] path data
setAcl [-s] [-v version] [-R] path acl
setquota -n|-b val path
stat [-w] path
sync path
```

#### 创建

使用create创建一个Zookeeper节点用法如下：

```
create [-s] [-e] path data acl
# 创建一个/zk-book的节点，节点的数据内容是123，没有任何权限控制
create /zk-book 123
```

`-s` `-e` 分别指定节点特性：顺序节点或临时节点。默认情况下不加 `-s -e` 参数的是持久节点。 `acl` 进行权限控制，缺省情况下不做任何权限控制。

#### 读取

`ls` ：列出指定节点下的所有子节点，用法： `ls [-s] [-w] [-R] path` 。

```
[zk: localhost:2181(CONNECTED) 2] ls /
[zk-book, zookeeper]
[zk: localhost:2181(CONNECTED) 3] ls /zk-book
[]
# ls -s 展示更详细的信息
[zk: localhost:2181(CONNECTED) 8] ls -s /zk-book
[]cZxid = 0x2
ctime = Wed Mar 10 14:42:17 UTC 2021
mZxid = 0x2
mtime = Wed Mar 10 14:42:17 UTC 2021
pZxid = 0x2
cversion = 0
dataVersion = 0
aclVersion = 0
ephemeralOwner = 0x0
dataLength = 3
numChildren = 0
[zk: localhost:2181(CONNECTED) 20] ls -R /
/
/zk-book
/zookeeper
/zookeeper/config
/zookeeper/quota
```

`get` ：获取指定节点的数据内容和属性信息，用法： `get [-s] [-w] path`

```
[zk: localhost:2181(CONNECTED) 4] get /zk-book
123
[zk: localhost:2181(CONNECTED) 21] get -s /zk-book
123
cZxid = 0x2
ctime = Wed Mar 10 14:42:17 UTC 2021
mZxid = 0x2
mtime = Wed Mar 10 14:42:17 UTC 2021
pZxid = 0x2
cversion = 0
dataVersion = 0
aclVersion = 0
ephemeralOwner = 0x0
dataLength = 3
numChildren = 0
```

`stat` ：获取节点统计信息，用法：`stat [-w] path`

```
[zk: localhost:2181(CONNECTED) 12] stat /zk-book
cZxid = 0x2
ctime = Wed Mar 10 14:42:17 UTC 2021
mZxid = 0x2
mtime = Wed Mar 10 14:42:17 UTC 2021
pZxid = 0x2
cversion = 0
dataVersion = 0
aclVersion = 0
ephemeralOwner = 0x0
dataLength = 3
numChildren = 0
```

#### 更新

`set` ：更新指定节点数据，用法： `set [-s] [-v version] path data`

```
[zk: localhost:2181(CONNECTED) 1] set -s /zk-book 789
cZxid = 0x2
ctime = Wed Mar 10 14:42:17 UTC 2021
mZxid = 0x5
mtime = Wed Mar 10 15:28:59 UTC 2021
pZxid = 0x2
cversion = 0
dataVersion = 2
aclVersion = 0
ephemeralOwner = 0x0
dataLength = 3
numChildren = 0
[zk: localhost:2181(CONNECTED) 2] get /zk-book
789
```

节点数据已经改变，并且节点中的 `dataVersion` 也改变了

#### 删除

`delete` : 删除指定节点，用法： `delete [-v version] path`

```
[zk: localhost:2181(CONNECTED) 4] delete /zk-book
[zk: localhost:2181(CONNECTED) 5] ls /
[zookeeper]
```

`deleteall` : 用法： `deleteall path`

#### 权限控制

zookeeper通过ACL权限控制列表来控制其对znode节点的访问权限，主要的操作权限有以下5种

权限列表   

| 名称   | 简写 | 权限说明                                     |
| ------ | ---- | -------------------------------------------- |
| CREATE | c    | 允许创建当前节点下的子节点                   |
| DELETE | d    | 允许删除当前节点下的子节点，仅限下一级       |
| READ   | r    | 允许读取节点数据以及显示子节点的列表         |
| WRITE  | w    | 允许设置当前节点的数据                       |
| ADMIN  | a    | 管理员权限，允许设置或读取当前节点的权限列表 |


ACL权限特点：

1.  zookeeper的权限是基于znode节点的，需要对每个节点设置权限
    
2.  znode节点支持同时设置多种权限方案和多个权限。当znode有多种权限的时候只要有一个权限允许当前操作，即可执行当前操作，最终权限 取多个权限之间的并集
    
3.  子节点不会继承父节点的权限，客户端没有权限访问当前节点的时候，但是可以有权限访问当前节点的子节点
    
4.  使用 `setAcl` 命令对节点进行权限设置会覆盖掉原来的权限
    

##### 相关命令

添加认证用户： `addauth scheme auth`

`scheme` 权限控制模式，分为world,auth,digest,ip和super; `auth` 具体权限信息。

`addauth digest zuser:123456` :通过digest模式添加一个zuser用户，密码为123456

设置权限： `setAcl [-s] [-v version] [-R] path acl`。 `-v version` 这是权限列表的版本信息，版本不一致设置会失败。 `-R` 递归设置权限，只设置当前节点下已有的子节点的权限，新创建的节点不会继承该权限。

```
[zk: localhost:2181(CONNECTED) 4] setAcl -s -v 1 -R /zk-book auth::cdrw
[zk: localhost:2181(CONNECTED) 5] getAcl -s /zk-book
'digest,'zuser:x
: cdrw
cZxid = 0xa
ctime = Sat Mar 13 11:39:14 UTC 2021
mZxid = 0xa
mtime = Sat Mar 13 11:39:14 UTC 2021
pZxid = 0xa
cversion = 0
dataVersion = 0
aclVersion = 2
ephemeralOwner = 0x0
dataLength = 3
numChildren = 0
```

不同模式区别：

1.  `world` 对所有人有效( `setAcl /zk-book world:anyone:cdrw` )
    
2.  `ip` 对指定ip有效( `setAcl /zk-book ip:127.0.0.1:cdrw,ip:127.0.0.2:cdrwa` )
    
3.  `auth` 给当前会话中，权限设置之前，所有授权过的所有用户(授权之前必须在当前会话中添加授权用户，否则报错)赋予权限( `setAcl /zk-book auth::cdrw` )
    
4.  `digest` 对指定用户设置权限，需要提供用户名和密码，其中密码是明文密码进行SHA1之后再进行BASE64的编码。可以对同一个用户的不同密码设置不同的权限(通过命令加密 `echo -n zuser:123456 | openssl dgst -binary -sha1 | openssl base64` `setAcl /zk-node1 digest:zuser:cOXZibgeyYdN0OeSxQ8uPespT0g=:cdrwa` )
    

<table><tbody><tr><td><i title="Note"></i></td><td><p>注意事项</p><div><ol><li><p>一个用户可以拥有多个密码，多个密码都有效</p></li><li><p>认证用户添加完成之后，认证将对该会话中添加认证之后的所有操作都有效，一旦会话结束，认证失败</p></li></ol></div></td></tr></tbody></table>

```
# 添加认证用户
addauth scheme auth
# 获取指定节点的权限列表
getAcl [-s] path
# 设置指定节点权限列表
setAcl [-s] [-v version] path acl
```

### Java客户端API使用

#### 创建会话

构造方法

```
ZooKeeper(String connectString, int sessionTimeout, Watcher watcher)
ZooKeeper(String connectString, int sessionTimeout, Watcher watcher, boolean canBeReadOnly)
ZooKeeper(String connectString, int sessionTimeout, Watcher watcher, long sessionId, byte[] sessionPasswd)
```

1.  `connectString`：192.168.1.1:2181,192.168.1.2:2181
    
2.  `sessionTimeout` :会话的超时时间，毫秒为单位。Zookeeper客户端和服务器之间会通过心跳检测机制来维持会话的有效性，一旦在 `sessionTimeout` 时间内没有进行有效的心跳检测，会话就会失效
    
3.  `watcher` :事件通知处理器
    
4.  `canBeReadOnly` :在zookeeper集群中，一个机器如果和集群中过半及以上的机器失去了网络连接，那么这个机器将不再处理客户端请求。但在某些使用场景下，当zookeeper服务器发生此类故障的时候，我们还是希望zookeeper服务器能够提供读服务，此参数是否开启此功能
    
5.  `sessionId` 和 `sessionPasswd` : 代表会话的id和会话密钥，这两个参数能够唯一确定一个会话。同时客户端使用这两个参数可以实现客户端会话的复用，从而达到恢复会话的效果。
    

<table><tbody><tr><td><i title="Note"></i></td><td><p>注意事项</p><p>zookeeper客户端和服务端会话的建立是一个异步的过程，构造方法在处理完客户端初始化工作后立即返回，在大多数情况下，此时并没有真正建立好一个会话，在会话的声明周期中处于 <code>CONNECTING</code> 的状态。当会话真正创建完毕后，zookeeper服务端会向会话对应的客户端发送一个事件通知，客户端在获取这个通知之后，才算真正建立了会话。</p></td></tr></tbody></table>

增删改查接口都有

还有一个 `zkclient` 的开源包对原有的包的功能进行了升级， `Curator` 更强的开源包