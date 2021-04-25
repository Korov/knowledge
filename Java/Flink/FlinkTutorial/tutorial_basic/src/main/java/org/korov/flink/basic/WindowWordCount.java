package org.korov.flink.basic;

import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkGenerator;
import org.apache.flink.api.common.eventtime.WatermarkGeneratorSupplier;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MINUTES;

/**
 * 先使用 nc -lp 9999 模拟服务器发送数据
 * 启动本程序
 * 在服务端键入字符此处会将统计结果打印出来
 *
 * @author korov
 */
public class WindowWordCount {
    private static final Logger logger = LoggerFactory.getLogger(WindowWordCount.class);

    public static void main(String[] args) throws Exception {
        tumblingEventTimeWindows(args);
    }


    /**
     * EventTime滚动窗口
     */
    public static void tumblingEventTimeWindows(String[] args) throws Exception {
        String hostname = "127.0.0.1";
        int port = 9999;

        if (args.length == 2) {
            hostname = args[0];
            port = Integer.parseInt(args[1]);
        }

        logger.info("host:{}, port:{}", hostname, port);
        // 1.初始化流计算的环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setRuntimeMode(RuntimeExecutionMode.STREAMING);

        // 默认开启多线程，线程数与CPU数量相等，可以手动指定开启线程数量
        env.setParallelism(2);

        // 3.读取数据
        DataStream<Tuple3<String, Long, Integer>> dataStream = env
                .socketTextStream(hostname, port)
                .flatMap(new FlatMapFunction<String, Tuple3<String, Long, Integer>>() {
                    @Override
                    public void flatMap(String value, Collector<Tuple3<String, Long, Integer>> out) throws Exception {
                        for (String word : value.split(" ")) {
                            logger.info("get:{}", word);
                            out.collect(new Tuple3<String, Long, Integer>(word, System.currentTimeMillis(), 1));
                        }
                    }
                })
                .assignTimestampsAndWatermarks(WatermarkStrategy.<Tuple3<String, Long, Integer>>forBoundedOutOfOrderness(Duration.ofSeconds(3))
                        .withTimestampAssigner(new SerializableTimestampAssigner<Tuple3<String, Long, Integer>>() {
                            // 使用第二个元素作为event time
                            @Override
                            public long extractTimestamp(Tuple3<String, Long, Integer> element, long recordTimestamp) {
                                return element.f1;
                            }
                        }))
                .keyBy(new KeySelector<Tuple3<String, Long, Integer>, String>() {
                    private static final long serialVersionUID = 1136154280689082856L;

                    @Override
                    public String getKey(Tuple3<String, Long, Integer> stringIntegerTuple3) {
                        return stringIntegerTuple3.getField(0);
                    }
                })
                // 一个滚动窗口，每分钟的20秒开始，例如第一分钟的20秒开始，第二分钟的20秒开始另一个窗口，默认的offset为0
                .window(TumblingEventTimeWindows.of(Time.of(1, MINUTES)))
                .sum(2);

        // 5.打印结果
        dataStream.print();
        // 流式处理必须显示执行
        env.execute("StreamWordCount");
    }

    /**
     * ProcessingTime滚动窗口
     */
    public static void tumblingProcessingTimeWindows(String[] args) throws Exception {
        String hostname = "127.0.0.1";
        int port = 9999;

        if (args.length == 2) {
            hostname = args[0];
            port = Integer.parseInt(args[1]);
        }

        logger.info("host:{}, port:{}", hostname, port);
        // 1.初始化流计算的环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setRuntimeMode(RuntimeExecutionMode.STREAMING);

        // 默认开启多线程，线程数与CPU数量相等，可以手动指定开启线程数量
        env.setParallelism(2);

        // 3.读取数据
        DataStream<Tuple2<String, Integer>> dataStream = env
                .socketTextStream(hostname, port)
                .flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
                    @Override
                    public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws Exception {
                        for (String word : value.split(" ")) {
                            logger.info("get:{}", word);
                            out.collect(new Tuple2<String, Integer>(word, 1));
                        }
                    }
                })
                .keyBy(new KeySelector<Tuple2<String, Integer>, String>() {
                    private static final long serialVersionUID = 1136154280689082856L;

                    @Override
                    public String getKey(Tuple2<String, Integer> stringIntegerTuple2) {
                        return stringIntegerTuple2.getField(0);
                    }
                })
                // 一个滚动窗口，默认的offset为0
                .window(TumblingProcessingTimeWindows.of(Time.of(1, TimeUnit.MINUTES)))
                .sum(1);

        // 5.打印结果
        dataStream.print();
        // 流式处理必须显示执行
        env.execute("StreamWordCount");
    }

}
