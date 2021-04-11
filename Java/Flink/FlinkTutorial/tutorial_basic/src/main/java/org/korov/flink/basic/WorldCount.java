package org.korov.flink.basic;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.charset.Charset;

public class WorldCount {
    private static final Logger logger = LoggerFactory.getLogger(WorldCount.class);

    public static void main(String[] args) throws Exception {
        // 创建执行环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        // 从文件中读取数据
        File file = new File("tutorial_basic/src/main/resources/world_count/hello.txt");
        if (!file.exists()) {
            logger.info("file not exist");
        }
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
