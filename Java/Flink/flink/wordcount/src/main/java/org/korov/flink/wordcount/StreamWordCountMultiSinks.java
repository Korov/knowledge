package org.korov.flink.wordcount;

/**
 * 先使用 nc -lp 9999 模拟服务器发送数据
 * 启动本程序
 * 在服务端键入字符此处会将统计结果打印出来
 *
 * @author korov
 * @date 2020/7/12
 */
public class StreamWordCountMultiSinks {
    // private static final Logger logger = LoggerFactory.getLogger(StreamWordCountMultiSinks.class);

    public static void main(String[] args) throws Exception {
        /*// 1.初始化流计算的环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(2);
        // 2.导入隐式转换

        // 3.读取数据
        DataStream<String> dataStream = env.fromElements("value1", "value2", "value3", "value4", "value5");

        // 5.打印结果
        dataStream.print();
        // dataStream.writeAsCsv("file:///home/korov/work/gitrepo/knowledge/Java/Flink/flink/csv", FileSystem.WriteMode.OVERWRITE);
        // 单线程写入会将demo.txt作为文件写入，多线成将会把demo.txt作为文件夹，多少个线程文件夹下就创建多少个文件写入数据
        // dataStream.writeAsText("file:///home/korov/work/gitrepo/knowledge/Java/Flink/flink/demo.txt", FileSystem.WriteMode.OVERWRITE).setParallelism(1);
        dataStream.writeAsText("demo.txt", FileSystem.WriteMode.OVERWRITE).setParallelism(1);

        //StreamingFileSink sink = StreamingFileSink.forRowFormat(new Path("file:///home/korov/work/gitrepo/knowledge/Java/Flink/flink/demo1"), new SimpleStringEncoder<String>("UTF-8")).build();
        //dataStream.addSink(sink);
        // 6.启动流计算程序
        env.execute("WordCount");*/
    }
}
