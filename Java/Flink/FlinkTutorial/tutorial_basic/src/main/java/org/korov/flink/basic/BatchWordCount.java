package org.korov.flink.basic;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.util.Collector;

import java.io.File;

/**
 * 批处理方式计算字符数量，此种处理方式已经不被推荐
 *
 * @author korov
 * @date 2020/7/12
 */
public class BatchWordCount {
    public static void main(String[] args) throws Exception {
        // 创建一个批处理的执行环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        // 从文件中读取数据
        File file = new File("tutorial_basic/src/main/resources/world_count/hello.txt");

        DataSet<String> inputDataSet = env.readTextFile(file.getAbsoluteFile().getPath());
        DataSet<Tuple2<String, Integer>> resultDataSet = inputDataSet
                .flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
                    private static final long serialVersionUID = -249748905332135566L;

                    @Override
                    public void flatMap(String s, Collector<Tuple2<String, Integer>> out) throws Exception {
                        String[] value = s.toLowerCase().split(" ");
                        for (String word : value) {
                            out.collect(new Tuple2<String, Integer>(word, 1));
                        }
                    }
                }).groupBy(0).sum(1);
        resultDataSet.print();
    }
}
