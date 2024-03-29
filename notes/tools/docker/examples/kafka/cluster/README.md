首先需要启动zookeeper集群，需要匹配版本当前最新匹配版本是3.4.13。

启动好zookeeper集群之后启动Kafka集群

```bash
docker-compose -f docker-compose.yml up -d
```

测试：

```bash
#进入容器
docker exec -it kafka1 /bin/sh
#创建两个topic
./opt/kafka_*/bin/kafka-topics.sh --create --zookeeper zoo1:2181,zoo2:2181,zoo3:2181 --replication-factor 1 --partitions 10 --topic test-topic2
./opt/kafka_*/bin/kafka-topics.sh --create --zookeeper zoo1:2181,zoo2:2181,zoo3:2181 --replication-factor 1 --partitions 1 --topic test1
#查看topic
./opt/kafka_*/bin/kafka-topics.sh --describe --zookeeper zoo1:2181,zoo2:2181,zoo3:2181 --topic test1
#创建一个生产者
./opt/kafka_*/bin/kafka-console-producer.sh --broker-list 172.21.0.24:9092 --topic test-topic2
#创建一个消费者
./opt/kafka_*/bin/kafka-console-consumer.sh --bootstrap-server 172.21.0.24:9092 --topic test-topic2 --from-beginning
```

参数详解：

```yml
     #外部访问kafka的host，需要设置为宿主机的IP
     KAFKA_ADVERTISED_HOST_NAME: 192.168.31.88
     #宿主机映射到的端口
      KAFKA_ADVERTISED_PORT: 9093
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://192.168.31.88:9093
      #内部容器之间传输的端口
      KAFKA_LISTENERS: PLAINTEXT://kafka2:9092
```

