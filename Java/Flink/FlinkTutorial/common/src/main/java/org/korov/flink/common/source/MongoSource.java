package org.korov.flink.common.source;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.bson.Document;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author zhu.lei
 * @date 2021-05-05 13:26
 */
public class MongoSource extends RichSourceFunction<Tuple3<String, String, Long>> {
    private final String host;
    private final int port;
    private final String dbname;
    private final String collection;
    MongoClient mongoClient = null;
    private AtomicBoolean isRunning = new AtomicBoolean(false);

    public MongoSource(String host, int port, String dbname, String collection) {
        this.host = host;
        this.port = port;
        this.dbname = dbname;
        this.collection = collection;
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        ServerAddress serverAddress = new ServerAddress(host, port);

        //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
        MongoCredential credential = MongoCredential.createScramSha256Credential("admin", "admin", "admin".toCharArray());

        MongoClientOptions options = MongoClientOptions.builder().maxConnectionIdleTime(6000).build();
        //通过连接认证获取MongoDB连接
        mongoClient = new MongoClient(serverAddress, credential, options);
    }

    @Override
    public void run(SourceContext<Tuple3<String, String, Long>> ctx) {
        if (mongoClient != null) {
            MongoDatabase db = mongoClient.getDatabase(dbname);
            MongoCollection<Document> mongoCollection = db.getCollection(collection);
            FindIterable<Document> documents = mongoCollection.find();
            for (Document document : documents) {
                Tuple3<String, String, Long> value = new Tuple3<>();
                value.setFields(document.getString("key"), document.getString("value"), 1L);
                ctx.collectWithTimestamp(value, value.f2);
            }
        }
    }

    @Override
    public void cancel() {
        if (!isRunning.get()) {
            isRunning.compareAndSet(false, true);
        }
    }

    @Override
    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
