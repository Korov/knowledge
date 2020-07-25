package org.korov.flink.wordcount;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 先使用 nc -lp 9999 模拟服务器发送数据
 * 启动本程序
 * 在服务端键入字符此处会将统计结果打印出来
 *
 * @author korov
 * @date 2020/7/12
 */
public class StreamWordCount {
    private static final Logger logger = LoggerFactory.getLogger(StreamWordCount.class);

    public static void main(String[] args) throws Exception {
        String hostname = "localhost";
        int port = 9999;

        if (args.length == 2) {
            hostname = args[0];
            port = Integer.parseInt(args[1]);
        }

        logger.info("host:{}, port:{}", hostname, port);

        // 1.初始化流计算的环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        // 默认开启多线程，线程数与CPU数量相等，可以手动指定开启线程数量
        env.setParallelism(2);

        // 2.导入隐式转换

        // 3.读取数据
        DataStream<Tuple2<String, Integer>> dataStream = env
                .socketTextStream(hostname, port)
                .flatMap(new Splitter())
                .keyBy(new KeySelector<Tuple2<String, Integer>, Object>() {
                    private static final long serialVersionUID = 1136154280689082856L;

                    @Override
                    public Object getKey(Tuple2<String, Integer> stringIntegerTuple2) throws Exception {
                        return stringIntegerTuple2.getField(0);
                    }
                })
                /**
                 * 窗口机制时streaming到batch的桥梁。Tumbling window（滚动窗口，无重叠）
                 * Sliding window（滑动窗口，时间有重叠），Session window（会话窗口，无重叠）
                 */
                //.timeWindow(Time.seconds(5))
                .sum(1);

        // 4.转换和处理数据

        // 5.打印结果
        dataStream.print();
        // 6.启动流计算程序
        env.execute("WordCount");
    }

    public static class Splitter implements FlatMapFunction<String, Tuple2<String, Integer>> {
        private static final long serialVersionUID = -8930813781257644186L;

        @Override
        public void flatMap(String sentence, Collector<Tuple2<String, Integer>> out) throws Exception {
            for (String word : sentence.split(" ")) {
                logger.info("get:{}", word);
                out.collect(new Tuple2<String, Integer>(word, 1));
            }
        }
    }
}
