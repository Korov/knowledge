package org.korov.flink.name.count;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author korov
 */
class KafkaToElasticWithCdcTest {
    public static final String[] args = new String[]{
            "--elastic_host", "192.168.50.100",
            "--elastic_port", "9200",
            "--elastic_index", "kafka_test",
            "--kafka_addr", "192.168.1.19:9092",
            "--kafka_topic", "flink_siem",
            "--kafka_group", "elastic_consumer_test",
            "--mysql_host", "127.0.0.1",
            "--mysql_port", "3306",
            "--mysql_dbs", "db1,db2",
            "--mysql_tables", "db1.table1 db2.table2",
            "--mysql_user", "root",
            "--mysql_password", "***",
            "--mysql_timezone", "UTC",
            "--redis_addr", "192.168.50.100:6379",
            "--redis_db", "0",
    };

    @Test
    void main() throws Exception {
        KafkaToElasticWithCdc.main(args);
    }
}