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
            "--kafka_addr", "192.168.50.100:9092",
            "--kafka_topic", "flink_siem",
            "--kafka_group", "elastic_consumer_test",
            "--mysql_host", "192.168.50.100",
            "--mysql_port", "3306",
            "--mysql_dbs", "test",
            "--mysql_tables", "test.test",
            "--mysql_user", "root",
            "--mysql_password", "test",
            // "--mysql_timezone", "SYSTEM",
            "--redis_addr", "192.168.50.100:6379",
            "--redis_db", "0",
    };

    @Test
    void main() throws Exception {
        KafkaToElasticWithCdc.main(args);
    }
}