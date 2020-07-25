package org.flink.example.java.batch;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Arrays;

/**
 * 统计一个文件中的单词出现的总次数，并把结果存储到文件中
 *
 * @author korov
 * @date 2020/7/25
 */
public class BatchWordCount {

    private static final Logger logger = LoggerFactory.getLogger(BatchWordCount.class);

    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        DataSource<String> text = env.readTextFile(String.join(File.separator, "file", "batch", "input.txt"));
        DataSet<Tuple2<String, Integer>> counts = text.flatMap(new Tokenizer()).groupBy(0).sum(1);
        counts.writeAsText(String.join(File.separator, "file", "batch", "result.txt")).setParallelism(1);
        env.execute("batch word count");
    }

    private static class Tokenizer implements FlatMapFunction<String, Tuple2<String, Integer>> {
        private static final long serialVersionUID = 3192575033757465188L;

        @Override
        public void flatMap(String value, Collector<Tuple2<String, Integer>> out) {
            String[] tokens = value.toLowerCase().split("\\W+");
            logger.info(Arrays.toString(tokens));
            for (String token : tokens) {
                if (token.length() > 0) {
                    logger.info(token);
                    out.collect(new Tuple2<String, Integer>(token, 1));
                }
            }
        }
    }
}
