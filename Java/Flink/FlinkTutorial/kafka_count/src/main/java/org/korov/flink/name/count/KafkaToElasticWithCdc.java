package org.korov.flink.name.count;

import com.fasterxml.jackson.databind.JsonNode;
import com.ververica.cdc.connectors.mysql.source.MySqlSource;
import com.ververica.cdc.debezium.JsonDebeziumDeserializationSchema;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.contrib.streaming.state.EmbeddedRocksDBStateBackend;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.korov.flink.common.utils.JSONUtils;
import org.korov.flink.name.count.deserialization.KeyAlertDeserializer;
import org.korov.flink.name.count.enums.SinkType;
import org.korov.flink.name.count.model.NameModel;
import org.korov.flink.name.count.sink.KeyAlertElasticSink;

import java.time.Duration;
import java.util.Objects;

/**
 * 将kafka中的数据格式化之后发送到ElasticSearch中，同时通过flink cdc获取全量的数据库中数据，将告警的所有数据补全
 * org.korov.flink.name.count.KafkaToElastic
 * <p>
 * --elastic_host 192.168.50.100 --elastic_port 9200 --elastic_index kafka_test --kafka_addr 192.168.1.19:9092 --kafka_topic flink_siem --kafka_group elastic_consumer_test --mysql_host 127.0.0.1 --mysql_port 3306 --mysql_dbs db1,db2 --mysql_tables db1.table1,db2.table2 --mysql_user root --mysql_password *** --mysql_timezone UTC --redis_addr 192.168.50.100:6379 --redis_db 0
 *
 * @author korov
 */
@Slf4j
public class KafkaToElasticWithCdc {
    public static void main(String[] args) throws Exception {
        Options options = new Options();
        options.addOption(Option.builder().longOpt("elastic_host").hasArg(true).required(true).build());
        options.addOption(Option.builder().longOpt("elastic_port").hasArg(true).required(true).build());
        options.addOption(Option.builder().longOpt("elastic_index").hasArg(true).required(true).build());

        options.addOption(Option.builder().longOpt("kafka_addr").hasArg(true).required(true).build());
        options.addOption(Option.builder().longOpt("kafka_topic").hasArg(true).required(true).build());
        options.addOption(Option.builder().longOpt("kafka_group").hasArg(true).required(true).build());

        options.addOption(Option.builder().longOpt("mysql_host").hasArg(true).required(true).build());
        options.addOption(Option.builder().longOpt("mysql_port").hasArg(true).required(true).build());
        options.addOption(Option.builder().longOpt("mysql_dbs").hasArg(true).required(true).build());
        options.addOption(Option.builder().longOpt("mysql_tables").hasArg(true).required(true).build());
        options.addOption(Option.builder().longOpt("mysql_user").hasArg(true).required(true).build());
        options.addOption(Option.builder().longOpt("mysql_password").hasArg(true).required(true).build());
        options.addOption(Option.builder().longOpt("mysql_timezone").hasArg(true).required(true).build());

        options.addOption(Option.builder().longOpt("redis_addr").hasArg(true).required(true).build());
        options.addOption(Option.builder().longOpt("redis_db").hasArg(true).required(true).build());

        CommandLine cmd = new DefaultParser().parse(options, args);
        String elasticHost = cmd.getOptionValue("elastic_host");
        int elasticPort = Integer.parseInt(cmd.getOptionValue("elastic_port"));
        String elasticIndex = cmd.getOptionValue("elastic_index");

        String kafkaAddr = cmd.getOptionValue("kafka_addr");
        String kafkaTopic = cmd.getOptionValue("kafka_topic");
        String kafkaGroup = cmd.getOptionValue("kafka_group");

        String mysqlHost = cmd.getOptionValue("mysql_host");
        int mysqlPort = Integer.parseInt(cmd.getOptionValue("mysql_port"));
        String[] mysqlDbs = cmd.getOptionValue("mysql_dbs").split(",");
        String[] mysqlTables = cmd.getOptionValue("mysql_tables").split(",");
        String mysqlUser = cmd.getOptionValue("mysql_user");
        String mysqlPassword = cmd.getOptionValue("mysql_password");
        String mysqlTimezone = cmd.getOptionValue("mysql_timezone");

        String redisAddr = cmd.getOptionValue("redis_addr");
        int redisDb = Integer.parseInt(cmd.getOptionValue("redis_db"));

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setRuntimeMode(RuntimeExecutionMode.STREAMING);
        env.getConfig().setUseSnapshotCompression(true);
        env.getCheckpointConfig().setCheckpointInterval(5 * 60000);
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE);
        env.getCheckpointConfig().setMinPauseBetweenCheckpoints(60000);
        env.getCheckpointConfig().setCheckpointTimeout(5 * 60000);
        env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);
        env.getCheckpointConfig().setExternalizedCheckpointCleanup(
                CheckpointConfig.ExternalizedCheckpointCleanup.DELETE_ON_CANCELLATION);

        EmbeddedRocksDBStateBackend rocksDbStateBackend = new EmbeddedRocksDBStateBackend(true);
        rocksDbStateBackend.setDbStoragePath("file:////opt/flink/rocksdb");
        env.setStateBackend(rocksDbStateBackend);
        env.getCheckpointConfig().setCheckpointStorage("file:////opt/flink/savepoints");
        env.enableCheckpointing(10000, CheckpointingMode.EXACTLY_ONCE);


        MySqlSource<String> mySqlSource = MySqlSource.<String>builder()
                .hostname(mysqlHost)
                .port(mysqlPort)
                .databaseList(mysqlDbs)
                .tableList(mysqlTables)
                .username(mysqlUser)
                .password(mysqlPassword)
                .serverTimeZone(mysqlTimezone)
                .deserializer(new JsonDebeziumDeserializationSchema())
                .build();

        DataStream<String> mysqlStream = env.fromSource(mySqlSource, WatermarkStrategy.noWatermarks(), "MySQL Source")
                .setParallelism(1);
        mysqlStream.addSink(new RichSinkFunction<String>() {
            private RedisCommands<String, String> syncCommands;
            private RedisClient redisClient;
            private StatefulRedisConnection<String, String> connection;

            @Override
            public void open(Configuration parameters) throws Exception {
                super.open(parameters);
                redisClient = RedisClient.create(String.format("redis://@%s/%s", redisAddr, redisDb));
                connection = redisClient.connect();
                syncCommands = connection.sync();
            }

            @Override
            public void finish() throws Exception {
                connection.close();
                redisClient.shutdown();
            }

            @Override
            public void invoke(String value, Context context) throws Exception {
                JsonNode jsonNode = JSONUtils.jsonToNode(value, JSONUtils.DEFAULT_MAPPER);
                JsonNode opNode = jsonNode.get("op");
                if (opNode != null) {
                    String op = opNode.textValue();
                    if (Objects.equals("c", op) || Objects.equals("u", op)) {
                        JsonNode afterNode = jsonNode.get("after");
                        if (afterNode != null) {
                            JsonNode keyNode = afterNode.get("id");
                            if (keyNode != null) {
                                String key = keyNode.toString();
                                String record = afterNode.toString();
                                syncCommands.set(key, record);
                            }
                        }
                    } else if (Objects.equals("d", op)) {
                        JsonNode beforeNode = jsonNode.get("before");
                        if (beforeNode != null) {
                            JsonNode keyNode = beforeNode.get("id");
                            if (keyNode != null) {
                                String key = keyNode.toString();
                                syncCommands.del(key);
                            }
                        }
                    }
                }
            }
        });


        KafkaSource<Tuple3<String, NameModel, Long>> kafkaSource = KafkaSource.<Tuple3<String, NameModel, Long>>builder()
                .setBootstrapServers(kafkaAddr)
                .setGroupId(kafkaGroup)
                .setStartingOffsets(OffsetsInitializer.committedOffsets(OffsetResetStrategy.EARLIEST))
                .setTopics(kafkaTopic)
                .setDeserializer(new KeyAlertDeserializer())
                .build();

        DataStream<Tuple3<String, NameModel, Long>> stream = env.fromSource(kafkaSource,
                WatermarkStrategy.<Tuple3<String, NameModel, Long>>forBoundedOutOfOrderness(Duration.ofMinutes(5))
                        .withTimestampAssigner(new SerializableTimestampAssigner<Tuple3<String, NameModel, Long>>() {
                            @Override
                            public long extractTimestamp(Tuple3<String, NameModel, Long> element, long recordTimestamp) {
                                try {
                                    return element.f1.getTimestamp();
                                } catch (Exception e) {
                                    log.error("get name key timestamp failed", e);
                                    return System.currentTimeMillis();
                                }
                            }
                        }).withIdleness(Duration.ofMinutes(5)), "kafka-source");

        KeyAlertElasticSink elasticValueSink = new KeyAlertElasticSink(elasticHost, elasticPort, elasticIndex, null, null, SinkType.KEY_NAME_VALUE);
        stream.addSink(elasticValueSink).name("elastic-value-sink");
        env.execute(String.format("kafka:[%s,%s,%s] to elastic:[%s:%s,%s]", kafkaAddr, kafkaTopic, kafkaGroup, elasticHost, elasticPort, elasticIndex));
    }
}
