# dockerApache kafka实战

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

副本，他们存在的唯一目的就是防止数据丢失。副本分为两类：领导副本（leader replica）和追随者副本（follower replica）。追随者副本是不能提供服务给客户端的，它只是被动的想领导者副本获取数据，而一旦领导者副本所在的broker宕机，Kafka就会从剩余的replica中选举出新的leader继续提供服务。

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
- unclean.leader.election.enable：是否开启unclean leader选举。当ISR变空并且leader宕机了，若此参数设置为false则不允许从剩下存活的非ISR副本中选择一个当leader。如果允许会造成消息数据的丢失。默认为false
- delete.topic.enable：是否允许Kafka删除topic。
- log.retention.{hours|minutes|ms}：设置消息数据的留存时间。如果同时设置，优先选取ms的设置，minutes次之，hours最后。默认的留存时间是7天。当前较新版本的Kafka会根据消息的时间戳信息进行留存与否的判断。对于没有时间戳的老版本信息格式，Kafka会根据日志文件的最近修改时间进行判断。
- log.retention.bytes：设置Kafka集群要为每个消息日志保存多大的数据。对于大小超过该参数的分区日志而言，Kafka会自动清理该分区的过期日志段文件。默认值为-1，表示Kafka永远不会根据消息日志文件总大小来删除日志。
- min.insync.replicas：该参数其实与producer端的acks参数配合使用。acks=-1表示producer端寻求最高等级的持久化保证，而此参数也只有在acks=-1时才有意义。它指定了broker端必须成功响应clients消息发送的最少副本数。假如broker端无法满足该条件，则clients的消息发送并不会被视为成功。它与acks配合使用可以令Kafka集群达成最高等级的消息持久化。-1为全部副本都写入才成功。
- num.network.threads：控制了一个broker在后台用于处理网络请求的线程数，默认是3。
- num.io.threads：控制broker端实际处理网络请求的线程数，默认是8，即Kafka broker默认创建8个线程以轮询方式不停的监听转发过来的网络请求并进行实时处理。
- message.max.bytes：broker能够接收的最大消息大小，默认是977KB。

### 3.5.2 topic级别参数

所谓的topic级别，是指覆盖broker端全局参数。每个topic都可以设置自己的参数值。

- delete.retention.ms：每个topic可以设置自己的日志留存时间以覆盖全局默认值
- max.message.bytes：覆盖全局的message.max.bytes，即为每个topic指定不同的最大消息尺寸
- retention.bytes：覆盖全局的log.retention.bytes，每个topic设置不同的日志留存尺寸。

### 3.5.3 GC参数

根据用户机器选择合适的垃圾收集器。同时因为Kafka broker主要使用的是堆外内存，因此并不需要为JVM分配太多的内存。

### 3.5.5 OS参数

主要针对Linux系统

- 文件描述符限制：Kafka会频繁的创建并修改文件系统中的文件，包括消息的日志文件、索引文件及各种元数据管理文件等。大致数量为分区数\*（分区总大小/日志段大小）\*3。
- Socket缓冲区大小：指的是OS级别的缓冲区大小。若是内网则Kafka将其自己的参数设置为64KB足够使用，但对于跨地区的数据传输而言需要同时增加Kafka和OS中的Socket缓存，建议大于等于128KB
- 最好使用Ext4或XFS文件系统：XFS的写入时间大约是160毫秒，而使用Ext4大约是250毫秒
- 关闭swap：具体命令为sysctl vm.swappiness=<一个较小的数>，即大幅降低对swap空间的使用，以免极大地拉低性能。
- 设置更长的flush时间：Kafka依赖OS页缓存的“刷盘”功能实现消息真正写入物理磁盘，默认的刷盘间隔是5秒。建议增加至2分钟

# 4 producer开发

producer的首要功能就是向某个topic分区发送一条消息，首先需要通过分区器（partitioner）确定向topic的哪个分区写入消息。Kafka producer提供了一个默认的分区器，对于每条待发送的消息而言，如果该消息制订了key那么分区器会根据key的哈希值来选择目标分区，若没有指定key则分区器使用轮询的方式确认分区目标（可以最大限度的确保消息在所有分区上的均匀性）。用户也可以跳过分区器直接指定要发送到的分区。用户也可以自己实现分区策略。

确认了目标分区后，producer要做的第二件事就是寻找这个分区对应的leader。

工作流程：

1. producer首先使用一个线程（用户主线程，也就是启动producer的线程）将待发送的消息封装进一个ProducerRecord类实例（*我们可以在此时指定partition和key*）
2. 然后将其序列化之后发送给partitioner(分区器，*分区器通过获取topic的元数据和ProducerRecord中的key判定这个数据发送到哪一个partition*)
3. 再由后者确定了目标分区后一同发送到位于producer程序中的一块内存缓冲区中。
4. 而producer的另一个工作线程（I/O发送线程）则负责实时地从该缓冲区中提取出准备就绪的消息封装进一个批次（batch，每个batch中的数据都是发往同一个topic的同一个partition），统一发送给对应的broker。
5. broker在收到这些消息时会返回一个响应。如果消息成功写入kafka，就返回一个RecordMetaData对象，它包含了主题和分区信息，以及记录在分区里的偏移量。如果写入失败，则会返回一个错误，生产者在收到错误之后会尝试重新发送消息，几次之后如果还是失败，就返回错误信息。

![image-20191129164720558](picture\image-20191129164720558.png)

```java
public class ProducerTest {
    public static void main(String[] args) {
        Properties properties = new Properties();
        // 以下三项必须指定
        properties.put("bootstrap.servers", "192.168.106.143:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        properties.put("acks", "-1");
        properties.put("retries", 3);
        properties.put("batch.size", 323804);
        properties.put("linger.ms", 10);
        properties.put("buffer.memory", 33554432);
        properties.put("max.block.ma", 3000);

        Producer<String, String> producer = new KafkaProducer<>(properties);
        for (int i = 0; i < 100; i++) {
            // topic:mykafka,后面的两个参数分别为键和值，键和值对象的类型必须与序列化器和生产者对象相匹配
            // 发送并忘记
            producer.send(new ProducerRecord<>("mykafka", Integer.toString(i), Integer.toString(i)));
            // 同步发送
            producer.send(new ProducerRecord<>("mykafka", Integer.toString(i), Integer.toString(i))).get();
            // 异步发送
            producer.send(new ProducerRecord<>("mykafka1", Integer.toString(i), Integer.toString(i + 1)), new Callback() {
                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception == null) {
                        // 消息发送成功
                    } else if (exception instanceof RetriableException) {
                        // 处理可重试瞬时异常
                    } else {
                        // 处理不可重试异常
                        producer.close(Duration.ofDays(0));
                    }
                }
            });
        }
        producer.close();
    }
}
```

- bootstrap.servers：该参数指定了一组host:port对，用于想Kafka broker服务器的连接，例如k1:9092,k2:9092,k3:9092。
- key.serializer：被发送到broker端的任何消息的格式都必须是字节数组，因此消息的各个组件必须首先做序列化，然后才能发送到broker。该参数就是为消息的key做序列化之用的。本实例中指定了使用org.apache.kafka.common.serialization.StringSerializer做序列化，该类会将一个字符串类型转换成字节数组。也可以指定自定义的序列化类。
- value.serializer：对消息体进行序列化，将消息的value部分转换成字节数组。可以指定做序列化的类。

**构造kafkaProducer**

```Java
//此方法不需要指定key.serializer和value.serializer
Serializer<String> keySerializer = new StringSerializer();
Serializer<String> valueSerializer = new StringSerializer();

Producer<String, String> producer = new KafkaProducer(properties, keySerializer, valueSerializer);
```

**4.消息发送**

Kafka producer发送消息的主方法是send方法。发送消息主要有以下3种方式：

1. 发送并忘记：把消息发送给服务器，但并不关心它是否正常到达，这种方式有时候会丢失一些消息
2. 同步发送：我们使用send()方法发送消息，它会返回一个Future对象，调用get()方法进行等待，就可以知道消息是否发送成功
3. 异步发送：调用send()方法，并指定一个回调函数，服务器在返回响应时调用该函数

### 4.2.2 producer主要参数

**acks**：用于控制producer生产消息的持久性。对于producer而言，Kafka在乎的是“已提交”消息的持久性。一旦消息被成功提交，那么只要有任何一个保存了该消息的副本存活，这条消息就会别视为不会丢失的。acks有3个取值：0、1和all。

0表示producer完全不理睬leader broker端的处理结果。此时producer发送消息后立即开启下一条消息的发送。此时用户无法通过回调机制感知任何发送过程中的失败，所以不确保消息会被成功发送。但此设置下producer的吞吐量最高

all或-1：表示当发送消息时，leader broker不仅会将消息写入本地日志，同时还会等待ISR中所有其他副本都成功写入他们各自的本地日志后，才发送响应结果给producer。吞吐量最低

1：默认参数值。producer发送消息后leader broker仅将该消息写入本地日志，然后便发送响应结果给producer，此时只要leader broker一直存活，Kafka就能够保证这条消息不丢失。

**buffer.memory**：指定了producer端用于缓存消息的缓冲区大小，单位事字节，默认值是33554432（32MB）。Java版的producer启动时会首先创建一块内存缓冲区用于保存待发送的消息，然后由另一个专属线程负责从缓冲区中读取消息执行真正地发送。

**compression.type**：设置producer端是否压缩消息，默认值是none。

**retries**：遇到错误时重试的次数。默认为0。谨慎设置有可能造成消息的重复发送和消息乱序。另外，producer两次重试之间会停顿一段时间，以防止频繁地重试对系统带来冲击。这段时间是可以配置的，由参数retry.backoff.ms指定，默认是100毫秒。

**batch.size**：producer最重要的参数之一。producer会将发往同一分区的多条消息封装进一个batch中，当batch满了的时候producer会发送batch中的所有消息。不过producer并不总是等待batch满了才发送消息，很有可能batch还有很多空闲空间时就发送batch。默认值是16384（16KB）。可以适当增大。

**linger.ms**：上面提到batch未满就发送，这是一种在吞吐量和延时之间的权衡。此参数就是控制消息发送延时行为的。默认为0，表示消息需要被立即发送，无须关系batch是否已被填满。

**client.id**：该参数可以时任意的字符串，服务器会用它来识别消息的来源，还可以用在日志和配额指标里。

**max.in.flight.requests.per.connection**：指定了生产者在收到服务器响应之前可以发送多少个消息。他的值越高，就会占用越多的内存，不过也会提升吞吐量。把它设为1可以保证消息是按照发送的顺序写入服务器的，即使发生了重试

**max.request.size**：控制producer端能够发送的最大消息的大小。默认1048576字节

**request.timeout.ms**：当producer发送请求给broker后，broker需要在规定的时间范围内将处理结果返回给producer，这段时间便是由该参数控制的，默认30秒。

**metadata.fetch.timeout.ms**：指定了生产者在获取元数据（比如目标分区的leader是谁）时等待服务器返回响应的时间。如果等待响应超时，那么生产者要么重试发送数据，要么返回一个错误。

**timeout.ms**：指定了broker等待同步副本返回消息确认的时间，与acks的配置相匹配，如果在指定时间内没有收到同步副本的确认，那么broker就会返回一个错误。

**max.block.ms**：指定了在调用send()方法或使用partitionsFor()方法获取元数据时生产者的阻塞时间，当生产者的发送缓冲区已满，或者没有可用的元数据时，这些方法就会阻塞。在阻塞时间达到max.block.ms时，生产者会抛出超时异常

**receive.buffer.bytes**和**send.buffer.bytes**：分别指定了TCP socker接收和发送数据包的缓冲区大小。如果他们被设为-1,就使用操作系统的默认值。如果生产者或者消费者与broker处于不同的数据中心，那么可以适当增大这些值，因为跨数据中心的网络一般都有比较高的延迟和比较低的带宽。

## 4.3 消息分区机制

Kafka的默认partitioner会尽力确保具有相同key的所有消息都会被发送到相同的分区上；若没有指定key，则会用轮询的方式来确保消息在topic的所有分区上均匀分配。

可以通过实现org.apache.kafka.clients.producer.Partitioner接口，并在构造KafkaProducer的Properties对象中设置partitioner.class参数替换分区器。

## 4.4 消息序列化

Kafka支持用户给broker发送各种类型的消息。它可以是一个字符串、一个整数、一个数组或是其他任意的对象类型，序列化器负载在producer发送前将消息转换成字节数组；与之相反解序列化器则用于将consumer接收到的字节数组转换成相应的兑现。

## 4.5 producer拦截器

对于producer而言，interceptor使得用户在消息发送前以及producer回调逻辑前有机会对消息做一些定制化需求，比如修改消息等。同时，producer允许用户指定多个interceptor按序作用于同一条消息从而形成一个拦截链。拦截器需要实现org.apache.kafka.clients.producer.ProducerInterceptor。

## 4.6 无消息丢失配置

producer端通过一个io线程将缓存中的数据发送到broker，若此时producer崩溃则缓存中的数据会丢失。此外若同时发送record1和record2,record1因为某些原因发送失败，并且设置了重试机制，那么就可能造成record2先发送record1后发送，乱序现象。

解决办法：

- block.on.buffer.full=true：新版本已经被max.block.ms替代可以不用设置此参数，内存缓冲区被填满时producer处于阻塞状态并停止接收新的消息而不是抛出异常
- acks=all or -1
- retries = Integer.MAX_VALUE
- max.in.flight.requests.per.connection=1：防止topic同分区下的消息乱序问题。其实际效果是限制了producer在单个broker连接上能够发送的未响应请求的数量。
- 使用带回调机制的send发送消息，KafkaProducer.send(record,callback)
- Callback逻辑中显示的立即关闭producer，使用close(0)：处理消息乱序问题，失败后立即关闭，不再发送此消息
- unclean.leader.election.enable=false
- replication.factor=3
- min.insync.factor=2：设置某条消息至少被写入到ISR中的多少个副本才算成功。
- replication.factor>min.insync.replicas
- enable.auto.commit=false

## 4.8 多线程处理

两种基本的使用方法：

- 多线程单KafkaProducer实例
- 多线程多KafkaProducer实例

**多线程单KafkaProducer实例**：全局构造一个KafkaProducer实例，然后再多个线程中共享使用。KafkaProducer是线程安全的。

**多线程多KafkaProducer实例**：每个producer主线程都构造一个KafkaProducer实例，并且保证此实例在该线程中封闭。

![image-20191130015150689](picture\image-20191130015150689.png)

# 5 consumer开发

### 5.1.1 消费者

两类：消费者组，独立消费者。消费者组是由多个消费者实例构成一个整体进行消费的，而独立消费者则单独执行消费操作。

### 5.1.2 消费者组

定义：消费者组使用一个消费者组名标记自己，topic的每条消息都只会被发送到每个订阅他的消费者组的一个消费者实例上。

一个群组里的消费者订阅的是同一个主题，每个消费者接收主题一部分分区的消息。

消费者组是用于实现高伸缩性、高容错性的consumer机制。组内多个consumer实例可以同时读取Kafka消息，而且一旦有某个consumer挂了，consumer group会立即将已崩溃的consumer负责的分区转交给其他consumer来负责，从而保证整个group可以继续工作，不会丢失数据--重平衡机制（rebalance）。

rebalance本质上是一种协议，规定了一个consumer group下所有consumer如何达成一致来分配订阅topic的所有分区。例如某个消费者组有20个消费者实例，此消费者组订阅了一个具有100个分区的topic，那么正常情况下，消费者组平均会为每个consumer分配5个分区，这个过程叫做rebalance。

一个topic下的一个分区只能被消费者组中的一个实例消费，若消费者组的消费者数量多于分区数量则消费者空闲。

### 5.1.3 位移（offset）

这里的offset指的是consumer端的offset，与分区日志中的offset是不同的含义。每个consumer实例都会为它消费的分区维护自己的位置信息来记录当前消费了多少条消息。

### 5.1.4 位移提交

consumer客户端需要定期地向Kafka集群汇报自己消费数据的进度，这一过程被称为位移提交（offset commit）。

## 5.2 构建consumer

```Java
public class ConsumerTest {
    public static void main(String[] args) {
        String topicName = "mykafka1";
        String groupId = "mykafkagroup";
        Properties properties = new Properties();
        // 以下四项必须指定
        properties.put("bootstrap.servers", "192.168.106.143:9092");
        properties.put("group.id", groupId);
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("auto.offset.reset", "earliest");

        // 创建消费者实例
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        // 订阅topic
        consumer.subscribe(Arrays.asList(topicName));
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1000));
                records.forEach(record ->
                        System.out.printf("offset = %d, key = %s, value = %s\n",
                                record.offset(), record.key(), record.value()));
            }
        } finally {
            consumer.close();
        }
    }

}
```

### 5.2.2 consumer脚本命令

脚本名称为kafka-console-consumer，在Linux平台下它位于<kafka 目录>/bin下，windows平台下它位于<kafka 目录>/bin/windows下。

kafka-console-consumer脚本常见的参数：

- --bootstrap-servers：指定Kafka broker列表，多台broker则以逗号分割。这与Java API中的bootstrap.servers参数有相同的含义。
- --topic：指定要消费的topic名
- --from-beginning：是否指定从头消费，与Java API中的auto.offset.reset=earliest效果一致

```sh
bin/kafka-console-consumer.sh --bootstrap-servers localhost:9092 --topic test --from-beginning
```



### 5.2.3 consumer主要参数

**session.timeout.ms**：消费组协调者（group coordinator）检测失败的时间，某个消费者实例崩溃了之后coordinator会在相应时间内感应到并做出相应处理。

**max.poll.interval.ma**：consumer处理逻辑最大时间。指consumer完成整个处理所用的时间

**auto.offset.reset**：指定了消费者在读取一个没有偏移量（offset）的分区或者偏移量无效的情况下（因消费者长时间失效，包含偏移量的记录已经过时井被删除）该作何处理，默认值是 `latest`。earliest指定从最早的位移开始消费，这里最早的位移不一定是0；latest：指定从罪行位移处开始消费；none：指定如果未发现位移信息或位移越界，则抛出异常。

**enable.auto.commit**：指定了消费者是否自动提交偏移量，默认值是 `true`，自动提交。设为 `false` 可以程序自己控制何时提交偏移量。如果设为 `true`，需要通过配置 `auto.commit.interval.ms` 属性来控制提交的频率。

**fetch.max.bytes**：consumer端单次获取数据的最大字节数。

**max.poll.records**：控制单次poll调用返回的最大消息数。默认500。

**heartbeat.interval.ms**：设置一个较低的值，让group下的其他consumer能够更快地感知新一轮rebalance开启了。

**connections.max.idle.ms**：Kafka定期关闭空闲Socket，默认9分钟。

## 5.3 订阅topic

### 5.3.1 订阅topic列表

consumer group订阅topic非常简单，使用下面的语句就可以实现

```java
consuemr.subscribe(Arrays.asList("topic1", "topic2", "topic3"));
```

如果使用独立的consumer，则可以使用下面的语句实现

```java
TopicPartition topicPartition1 = new TopicPartition("topic-name", 0);
TopicPartition topicPartition2 = new TopicPartition("topic-name", 1);
consumer.assign(Arrays.asList(topicPartition1, topicPartition2));
```

### 5.3.2 基于正则表达式订阅topic

```java
consumer.subscribe(Pattern.compile("kafka-.*"), new ConsumerRebalanceListener()...);
```

使用基于正则表达式的订阅就必须指定ConsumerRebalanceListener。该类是一个回调接口，用户需要通过实现这个接口来实现consumer分区分配方案发生变更时的逻辑。如果用户使用的是自动提交 （enable.auto.commit=true)，则通常不需要理会这个类，使用下列的实现类就可以了

```java
consumer.subscribe(Pattern.compile("kafka-.*"), new NoOpConsumerRebalanceListener()...);
```

但是，如果用户是手动提交位移的，则至少要在ConsumerRebalanceListener实现类的onPartitionsRevoked方法中处理分区分配方案变更时的位移提交。

# 7 管理Kafka集群

## 7.1 集群管理

### 7.1.1 启动broker

在生产环境中强烈推荐使用`-daemon`参数启动服务器，代码如下

```sh
bin/kafka-server-start.sh -daemon <path>/server.properties
```

### 7.1.2 关闭broker

正确关闭broker的分两种情况讨论。

#### 1. 前台方式启动broker进程时

所谓前台启动broker是指，在Linux中断不加nohub或-daemon参数的方式直接启动broker。关闭broker只需要在该终端上按Ctrl + C组合键发出SIGINT信号终止进程即可。Kafka broker进程会捕捉SIGINT信号并执行broker关闭逻辑。

#### 2. 后台方式启动broker进程时

当nohub或-daemon参数的方式直接启动broker后，需要使用Kafka的脚本停止：

```sh
bin/kafka-server-stop.sh
```

它会关闭当前机器中所有的Kafka broker进程。

### 7.1.3 设置JMX端口

Kafka提供了丰富的JMX指标用于实时监控集群运行的健康程度。

```sh
JMX_PORT=9997 bin/kafka-server-start.sh -daemon <path>/server.properties
```

## 7.2 topic管理

### 7.2.1 创建topic

Kafka创建topic的途径有4种：

- 通过执行kafka-topics.sh命令工具创建
- 通过显示发送CreateTopicsRequest请求创建topic
- 通过发送MetadataRequest请求且broker端设置了auto.create.topics.enable为true
- 通过向ZooKeeper的/brokers/topics路径下写入以topic名称命名的子结点。

![image-20200111164237850](picture\image-20200111164237850.png)

示例：创建一个topic，名为test-topic，6个分区，每个分区3个副本，同时指定该topic的日志留存时间3天

```sh
bin/kafka-topics.sh --create --zookeeper localhost:2181 --partitions 6 --replication-factor 3 --topic test-topic --config delete.retention.ms=259200000
```

手动指定分区在集群上的分配。假设我们有一个3台broker构成的Kafka集群，broker id分别是0、1、2。现在我们创建一个topic，名为test-topic2，分区数是4，副本因子是2。我们手动分配方案如下：

分区1：0、1；分区2：1、2；分区3：0、2；分区4：1、2；

```sh
bin/kafka-topics.sh --create --zookeeper localhost:2181 --topic test-topic2 --replica-assignment 0:1,1:2,0:2,1:2
```

指定--replica-assignment之后，不用再制定--partitions和--replication-factor，因为脚本可以从手动分配方案中计算出topic的分区数和副本因子数

### 7.2.2 删除topic

删除topic有3种方式：

- 使用kafka-topics脚本
- 构造DeleteTopicsRequest请求
- 直接向ZooKeeper的/admin/delete_topics下写入子结点

```sh
bin/kafka-topics.sh --delete --zookeeper localhost:2181 --topic test-topic
```

### 7.2.3 查询topic列表

```sh
bin/kafka-topics.sh --zookeeper localhost:2181 --list
```

### 7.2.4 查询topic详情

```sh
bin/kafka-topics.sh --zookeeper localhost:2181 --describe --topic test-topic2
```

### 7.2.5 修改topic

增加topic分区，以test-topic2为例，当前的分区数4，增加到10

```sh
bin/kafka-topics.sh --alter --zookeeper localhost:2181 --partitions 10 --topic test-topic2
```

官方不推荐使用这种方法，推荐使用kafka-config.sh。

为已有topic增加一个topic级别的参数。假设我们要为上面的test-topic2设置cleanup.policy=compact

```sh
bin/kafka-config.sh --zookeeper zoo1:2181,zoo2:2181 --alter --entity-type topics --entity-name test-topic2 --add-config cleanup.policy=compact
```

## 7.4 consumer相关管理

### 7.4.2 重设消费者位移



# 自我总结