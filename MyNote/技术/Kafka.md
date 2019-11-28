# Apache kafka实战

# 1 认识Apache Kafka

## 1.2 消息引擎系统

![image-20191127232934697](picture\image-20191127232934697.png)

在设计一个消息引擎系统时需要考虑两个重要因素：

- 消息设计
- 传输协议设计

## 1.3 Kafka概要设计

Kafka在设计之初就需要考虑以下4个方面的问题：

- 吞吐量/延时
- 消息持久化
- 负载均衡和故障转移
- 伸缩性

### 1.3.1 吞吐量/延时

Kafka具有高吞吐量、低延时的特性。Kafka的写入操作很快，这主要得益与它对磁盘的使用方法不同。Kafka每次写入操作其实都只是把数据写入到操作系统的页缓存中，然后由操作系统自行决定什么时候把页缓存中的数据写回磁盘上。这样做的优势：

- 操作系统页缓存是在内存中分配的，所以消息写入的速度非常快
- Kafka不必直接与底层的文件系统打交道。所有繁琐的I/O操作都交由操作系统来处理
- Kafka写入操作采用追加写入的方式，避免了磁盘随机写操作

Kafka的消费端如何做到高吞吐和低延时的。Kafka在读取消息时会首先尝试从OS的页缓存中读取，如果命中便把消息直接发送到网络的Socket上。这个过程就是利用Linux平台的sendfile系统调用做到的，这种技术就是零拷贝技术。

Kafka通过以下4点达到高吞吐低延时的设计目标：

- 大量使用操作系统页缓存，内存操作速度快且命中率高
- Kafka不直接参与物理I/O操作，而是交由最擅长此事的操作系统来完成
- 采用追加写入方式，摒弃了缓慢的磁盘随机读写操作
- 使用sendfile为代表的零拷贝技术加强网络间的数据传输效率

### 1.3.2 消息持久化

Kafka中所有的数据都会立即被写入文件系统的持久化日志中，之后Kafka服务器才会返回结果给客户端通知他们消息已经被成功写入。这样做既实时保存了数据，又减少了Kafka程序对内存的消耗，从而将节省出的内存留给页缓存使用，进一步提升了整体性能。

### 1.3.3 负载均衡和故障转移

Kafka通过智能化的分区领导者选举来实现负载均衡的。Kafka使用会话机制来实现故障转移。每台Kafka服务器启动后会以会话的形式把自己注册到zookeeper服务器上。一旦该服务器运转出现问题，与zookeeper的会话便不能维持从而超时失效，此时Kafka集群会选举出另一台服务器来完全替代这台服务器继续提供服务。

### 1.3.4 伸缩性

每台Kafka服务器上的状态统一交由zookeeper保管。扩展Kafka只需要启动新的Kafka服务器并注册到zookeeper中即可。

## 1.4 Kafka基本概念与术语

定位：分布式流式处理平台。

![image-20191128163747119](picture\image-20191128163747119.png)

Kafka服务器的官方名字叫做**broker**，而在Kafka集群中的每条消息都归属于一个topic。

### 1.4.1 消息

Kafka中的消息格式由很多字段组成，其中的很多字段都是用于关系消息的元数据字段，对用户来说是完全透明的。Kafka消息格式共经历了3次变迁，他们分别被称为V0，V1，V2。目前大部分用户使用的都是V1格式。

![image-20191128164221815](picture\image-20191128164221815.png)

消息由消息头部，key和value组成。

- key：消息键，对消息做partition时使用，即决定消息被保存在某topic下的那个partition
- value：消息体，保存实际的消息数据
- timestamp：消息发送时间戳，用于流式处理及其他依赖时间的处理语义，如果不指定则取当前时间。

### 1.4.2 topic和partition

topic只是一个逻辑概念，代表了一类消息，也可以认为是消息被发送到的地方。通常我们可以使用topic来区分实际业务。

Kafka中的topic通常都会被多个消费者订阅，因此出于性能的考量，Kafka采用了topic-partition-message的三级结构来分散负载。从本质上说，每个topic都由若干个partition组成。

![image-20191128170509823](picture\image-20191128170509823.png)

Kafka中的partition是不可修改的有序消息序列，每个partition有自己专属的partition号，通常从0开始，用户对partition唯一能做的操作就是在消息序列的尾部追加写入消息。partition上的每条消息都会被分配一个唯一的序列号--该序列号被称为位移（offset）。该位移值是从0开始顺序递增的整数。

Kafka的partition实际上并没有太多的业务含义，他的引入就是单纯的为了提升系统的吞吐量，因此在创建Kafka topic的时候可以根据集群实际配置设置具体的partition数量，实现整体性能的最大化。

**Kafka中的一条消息其实就是一个<topic,partition,offset>三元组**，通过该元组我们可以在Kafka集群中找到唯一对应的那条消息。

### 1.4.4 replica

副本，他们存在的唯一目的就是防止数据丢失。副本分为两类：领到副本（leader replica）和追随者副本（follower replica）。追随者副本是不能提供服务给客户端的，它只是被动的想领导者副本获取数据，而一旦领导者副本所在的broker宕机，Kafka就会从剩余的replica中选举出新的leader继续提供服务。

### 1.4.6 ISR

in-sync replica，与leader replica保持同步的replica集合。

Kafka为partition动态维护一个replica集合，该集合中的所有replica保存的额消息日志都与leader replica保持同步状态，只有这个集合中的replica才能被选举为leader，也只有该集合中所有replica都接收到同一条消息，Kafka才会将该消息置于“已提交”状态，即认为这条消息发送成功。

当某个replica因为某种原因其进度滞后于leader replica，滞后到某种程度之后ISR将会把此replica踢出，当其进度追赶上来后会重新将其加入ISR。

# 3 Kafka线上环境部署

单节点安装Kafka可以参考docker笔记中的安装方法。

## 3.3 多节点环境安装

### 3.3.1 安装多节点zookeeper集群

若zookeeper集群中有2n+1个服务器，那么此集群最多容忍n台服务器宕机而保证依然提供服务。原因，若集群不满足“**半数以上服务器存活**”的条件，zookeeper集群将停止服务。例如5台zookeeper集群可以容忍2台宕机，3台集群可以容忍1台宕机。这就是为什么zookeeper集群结点通常是奇数的原因。

配置zoo.cfg文件

![image-20191128221556270](picture\image-20191128221556270.png)

- tickTime：zookeeper最小的时间单位，用于丈量心跳时间和超时时间等。通常设置成默认值2秒即可
- dataDir：非常重要的参数！zookeeper会在内存中保存系统快照，并定期写入该路径指定的文件夹中。生产环境中需要注意该文件夹的磁盘占用情况
- clientPort：zookeeper监听苦户端连接的端口，一般设置成默认值2181即可
- initLimit：指定follower结点初始时连接leader结点的最大tick次数。假设是5，表示follwer必须要在5*tickTime时间内连接上leader，否则将被视为超时
- syncLimit：设定了follower结点与leader结点进行同步的最大时间。也是以tickTime为单位进行指定的
- server.X=host:port:port：这里的X必须是一个全局唯一的数字，且需要与myid文件中的数字相对应。一般设置X值为1-255之间的整数。这行的后面还配置了两个端口，通常是2888和3888.第一个端口用于使follower结点连接leader结点，而第二个端口则用于leader选举

每个zookeeper服务器都有一个唯一的ID。这个ID主要用在两个地方：一个就是刚刚我们配置的zoo.cfg文件，另一个则是myid文件。myid文件位于zoo.cfg中dataDir配置的目录下，其内容也很简单，仅是一个数字，即ID。

具体过程省略

### 3.3.2 安装多节点Kafka

安装多节点Kafka比安装多节点的zookeeper要简单的多，我们只需要创建多份配置文件，然后指定他们启动Kafka服务即可。

具体过程省略

## 3.5 参数设置

### 3.5.1 broker端参数

broker端参数需要在Kafka目录下的config/server.properties文件中进行设置。当前对于绝大多数的broker端参数而言，Kafka尚不支持动态修改--这就是说，如果新增，修改亦或是删除某些broker参数的话，需要重启对应的broker服务器。

- broker.id：Kafka使用唯一的一个正数来标识每个broker，该参数默认是-1。如果不指定，Kafka会自动生成一个唯一值。
- log.dirs：非常重要的参数！指定了Kafka持久化消息的目录。该参数可以设置多个目录，以逗号分隔，比如/home/kafka1,/home/kafka2。推荐指定多个目录，这样Kafka可以把负载均匀的分配到多个目录下。若用户机上有N块物理硬盘，那么设置N个目录（挂载在不同磁盘上）是一个很好的选择，N个磁头可以同时执行写操作，极大地提升了吞吐量。若不设置改参数，Kafka默认使用/tmp/kafka-logs作为消息保存的目录。
- zookeeper.connect：非常重要的参数，此参数没有默认值，是必须要设置的。该参数也可以是一个CSV（comma-separated values）列表，例如：zk1:2181,zk2:2181,zk3:2181。如果要使用一套zookeeper环境管理多套Kafka集群，那么设置该参数的时候就必须指定zookeeper的chroot，例如：zk1:2181,zk2:2181,zk3:2181/kafka_cluster1，结尾的/kafka_cluster1就是chroot，他是可选的配置，如果不指定则默认使用zookeeper的根路径。在实际使用中，配置chroot可以起到很好的隔离效果。这样管理Kafka集群将变得更加容易。
- listeners：broker监听器的CSV列表，格式是`[协议]://[主机名]:[端口],[[协议]://[主机名]:[端口]]`该参数主要用于客户端连接broker使用，可以认为是broker端开放给clients的监听端口。如果不指定主机名，则表示绑定默认网卡；如果主机名是0.0.0.0，则表示绑定所有网卡。Kafka当前支持的协议类型包括PLAINTEXT、SSL及SASL_SSL等。对于新版本的Kafka，建议配置listeners就够了，host.name和port已经过时。
- advertised.listeners：与listeners类似，用于发布给clients的监听器，不过该参数主要用于IaaS环境，比如云上的机器通常都配有多块网卡（私网网卡和公网网卡）。对于这种机器，用户可以设置该参数绑定公网IP供外部clients使用，然后配置上面的listeners来绑定私网IP供broker间通信使用。当然不设置也是可以的，只是云上的机器很容易出现clients无法获取数据的问题。