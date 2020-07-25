package org.korov.flink.wordcount;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

import java.io.File;

/**
 * 批处理方式计算字符数量
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
        String filePath = String.join(File.separator, "file", "data.txt");
        DataSet<String> inputDataSet = env.readTextFile(filePath);
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
