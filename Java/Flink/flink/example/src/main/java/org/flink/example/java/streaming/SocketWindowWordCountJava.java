package org.flink.example.java.streaming;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

/**
 * 单词计数之滑动窗口计算
 * 以换行符为分隔符获取数据
 *
 * @author korov
 */
public class SocketWindowWordCountJava {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        String hostname = "localhost";
        int port = 9999;
        String delimiter = "\n";
        // 连接socket获取数据
        DataStreamSource<String> text = env.socketTextStream(hostname, port, delimiter);

        DataStream<WordWithCount> windowCounts = text.flatMap(new FlatMapFunction<String, WordWithCount>() {
            private static final long serialVersionUID = -4603926806173660949L;

            @Override
            public void flatMap(String s, Collector<WordWithCount> collector) throws Exception {
                String[] splits = s.split("\\s");
                for (String word : splits) {
                    collector.collect(new WordWithCount(word, 1));
                }
            }
        }).keyBy(new KeySelector<WordWithCount, Object>() {
            private static final long serialVersionUID = 902504272556923107L;

            @Override
            public Object getKey(WordWithCount value) throws Exception {
                return value.word;
            }
        }).window(SlidingEventTimeWindows.of(Time.seconds(2), Time.seconds(1)))// 指定时间窗口大小为2秒，指定时间间隔为1秒
                .sum("count");

        /* 使用reduce函数
        .reduce(new ReduceFunction<WordWithCount>() {
                                    public WordWithCount reduce(WordWithCount a, WordWithCount b) throws Exception {

                                        return new WordWithCount(a.word,a.count+b.count);
                                    }
                                })*/
        windowCounts.print().setParallelism(1);
        env.execute("Socket window count!");

    }

    public static class WordWithCount {
        public String word;
        public long count;

        public WordWithCount() {
        }

        public WordWithCount(String word, long count) {
            this.word = word;
            this.count = count;
        }

        @Override
        public String toString() {
            return "WordWithCount{" +
                    "word='" + word + '\'' +
                    ", count=" + count +
                    '}';
        }
    }
}
