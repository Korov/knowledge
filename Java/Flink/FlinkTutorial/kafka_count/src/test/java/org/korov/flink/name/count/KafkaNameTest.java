package org.korov.flink.name.count;

import com.google.common.collect.ImmutableList;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class KafkaNameTest {
    @Test
    public void test() throws Exception {
        List<String> sourceList = ImmutableList.<String>builder()
                .add("value1")
                .add("value2")
                .build();

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setRuntimeMode(RuntimeExecutionMode.STREAMING);

        DataStream<String> stream = env.fromCollection(sourceList);
        DataStream<Map<String, Long>> mapStream = stream.map(new MapFunction<String, Map<String, Long>>() {
            @Override
            public Map<String, Long> map(String value) throws Exception {
                Map<String, Long> keyTime = new HashMap<>();
                keyTime.put(value, System.currentTimeMillis());
                log.info("key:{}, time:{}", value, keyTime.get(value));
                return keyTime;
            }
        });
        env.execute("map-stream");
    }
}
