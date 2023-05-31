package org.korov.flink.basic;

import com.fasterxml.jackson.databind.JsonNode;
import com.ververica.cdc.connectors.mysql.source.MySqlSource;
import com.ververica.cdc.debezium.JsonDebeziumDeserializationSchema;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.korov.flink.common.utils.JSONUtils;

import java.util.Objects;

/**
 * @author korov
 */
@Slf4j
public class MySQLCdc {
    public static void main(String[] args) throws Exception {
        MySqlSource<String> mySqlSource = MySqlSource.<String>builder()
                .hostname("127.0.0.1")
                .port(3306)
                .databaseList("test") // set captured database
                .tableList("test.test") // set captured table
                .username("root")
                .password("")
                .serverTimeZone("UTC")
                .deserializer(new JsonDebeziumDeserializationSchema()) // converts SourceRecord to JSON String
                .build();

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // enable checkpoint
        env.enableCheckpointing(3000);

        env.fromSource(mySqlSource, WatermarkStrategy.noWatermarks(), "MySQL Source")
                // set 4 parallel source tasks
                .setParallelism(1)
                .addSink(new RichSinkFunction<String>() {

                    private RedisCommands<String, String> syncCommands;

                    private RedisClient redisClient;

                    private StatefulRedisConnection<String, String> connection;

                    @Override
                    public void open(Configuration parameters) throws Exception {
                        redisClient = RedisClient.create("redis://@192.168.50.100:6379/0");
                        connection = redisClient.connect();
                        syncCommands = connection.sync();
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

                        // {"before":{"id":4,"domain_id":4},"after":{"id":4,"domain_id":5},"source":{"version":"1.6.4.Final","connector":"mysql","name":"mysql_binlog_source","ts_ms":0,"snapshot":"false","db":"rizhiyi_system","sequence":null,"table":"siem_threat_info_all_1","server_id":0,"gtid":null,"file":"","pos":0,"row":0,"thread":null,"query":null},"op":"r","ts_ms":1685524128702,"transaction":null}
                        log.info("mysql record:{}", value);
                    }

                    @Override
                    public void finish() throws Exception {
                        connection.close();
                        redisClient.shutdown();
                    }
                }).setParallelism(1); // use parallelism 1 for sink to keep message ordering


        env.execute("Print MySQL Snapshot + Binlog");
    }
}
