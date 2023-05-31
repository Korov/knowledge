package org.korov.flink.basic;

import com.fasterxml.jackson.databind.JsonNode;
import com.ververica.cdc.connectors.mysql.source.MySqlSource;
import com.ververica.cdc.debezium.JsonDebeziumDeserializationSchema;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.korov.flink.common.utils.JSONUtils;

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
                .addSink(new SinkFunction<String>() {
                    @Override
                    public void invoke(String value, Context context) throws Exception {
                        JsonNode jsonNode = JSONUtils.jsonToNode(value, JSONUtils.DEFAULT_MAPPER);
                        // {"before":{"id":4,"domain_id":4},"after":{"id":4,"domain_id":5},"source":{"version":"1.6.4.Final","connector":"mysql","name":"mysql_binlog_source","ts_ms":0,"snapshot":"false","db":"rizhiyi_system","sequence":null,"table":"siem_threat_info_all_1","server_id":0,"gtid":null,"file":"","pos":0,"row":0,"thread":null,"query":null},"op":"r","ts_ms":1685524128702,"transaction":null}
                        log.info("mysql record:{}", value);
                    }
                }).setParallelism(1); // use parallelism 1 for sink to keep message ordering


        env.execute("Print MySQL Snapshot + Binlog");
    }
}
