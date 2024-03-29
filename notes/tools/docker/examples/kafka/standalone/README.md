# 使用旧版本的kafka

```bash
docker run -d --name zookeeper -p 2181:2181 -t wurstmeister/zookeeper;
docker run -d --name kafka --publish 9092:9092 --link zookeeper \
--env KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 \
--env KAFKA_CREATE_TOPICS="flink_siem:6:1,flink_siem_original_event:6:1" \
--env KAFKA_ADVERTISED_LISTENERS="OUTSIDE://192.168.50.114:9092" \
--env KAFKA_ADVERTISED_PORT=9092 \
--env KAFKA_LISTENERS="OUTSIDE://:9092" \
--env KAFKA_LISTENER_SECURITY_PROTOCOL_MAP="OUTSIDE:PLAINTEXT" \
--env KAFKA_INTER_BROKER_LISTENER_NAME="OUTSIDE" \
--volume /etc/localtime:/etc/localtime wurstmeister/kafka:2.12-2.4.1;
```

以上启动Kafka，接下来需要进入Kafka的docker修改文件

KAFKA_LISTENERS是内网使用的，KAFKA_ADVERTISED_LISTENERS外网使用的时候才配置，配置的是宿主机的ip加端口

```bash
#修改上面的启动参数之后不用修改了
docker exec -it kafka /bin/sh
vi /opt/kafka_2.12-2.2.0/config/server.properties
```

![image-20191129181514247](..\..\..\picture\image-20191129181514247.png)

```bash
#进入Kafka
docker exec -it kafka /bin/sh
#进入Kafka的bin目录下
cd /opt/kafka_*/bin
#创建一个topic，并设置partition的数量为1
./opt/kafka_*/bin/kafka-topics.sh --create --zookeeper zookeeper:2181 --replication-factor 1 --partitions 1 --topic test
#查看创建的topic
./opt/kafka_*/bin/kafka-topics.sh --describe --zookeeper zookeeper:2181 --topic test
#创建一个生产者发送消息
./opt/kafka_*/bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test
#创建一个消费者接受消息
./opt/kafka_*/bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning
```

# 使用bitman的kafka

kafka2.8版本支持不用zookeeper

```
docker run -d --name kafka --publish 9092:9092 \
--env KAFKA_CFG_ZOOKEEPER_CONNECT=linux.korov.org:2181 \
--env ALLOW_PLAINTEXT_LISTENER=yes \
--env KAFKA_CFG_LISTENERS=PLAINTEXT://:9092 \
--env KAFKA_CFG_ADVERTISED_LISTENERS=PLAINTEXT://linux.korov.org:9092 \
--env KAFKA_CFG_AUTO_CREATE_TOPICS_ENABLE=true \
--volume /etc/localtime:/etc/localtime bitnami/kafka:3.1.0;
```

```bash
/opt/bitnami/kafka/bin/kafka-topics.sh --create --zookeeper zookeeper:2181 --topic mytopic --partitions 3 --replication-factor 3
Created topic "mytopic".
```

