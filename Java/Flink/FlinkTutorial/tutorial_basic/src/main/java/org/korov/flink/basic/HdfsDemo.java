package org.korov.flink.basic;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.util.Properties;

/**
 * @author zhu.lei
 * @date 2023-06-30 18:50
 */
public class HdfsDemo {
    public static void main(String[] args) throws Exception {
        // 创建执行环境 流批已经合为一个了
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        String configPath = System.getenv("HADOOP_CONF_DIR");
        String coreSite = configPath + System.lineSeparator() + "core-site.xml";
        String hdfsSite = configPath + System.lineSeparator() + "hdfs-site.xml";
        // String coreSite = "src/test/resources/hadoop_config/core-site.xml";
        // String hdfsSite = "src/test/resources/hadoop_config/hdfs-site.xml";


        Configuration conf = new Configuration();
        conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
        conf.addResource(new Path(coreSite));
        conf.addResource(new Path(hdfsSite));
        Properties properties = System.getProperties();
        properties.setProperty("HADOOP_USER_NAME", "rizhiyi");
        FileSystem fileSystem = FileSystem.get(conf);
        FileStatus fileStatus = fileSystem.getFileStatus(new Path("/test"));
        System.out.println(fileStatus.isDirectory());
        System.out.println(fileSystem.getStatus().toString());
        env.execute();
    }
}
