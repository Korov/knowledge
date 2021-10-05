package org.korov.flink.common.source;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.bson.Document;

import java.util.Optional;

/**
 * @author zhu.lei
 * @date 2021-05-05 13:26
 */
@Slf4j
public class MongoSource extends RichSourceFunction<Tuple3<String, String ,Long>> {
    private final String host;
    private final int port;
    private final String dbname;
    private final String collection;
    MongoClient mongoClient = null;

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
        mongoClient = new MongoClient(serverAddress, options);
    }

    @Override
    public void run(SourceContext<Tuple3<String, String ,Long>> ctx) throws Exception {
        if (mongoClient != null) {
            log.info("mongo source start");
            MongoDatabase db = mongoClient.getDatabase(dbname);
            MongoCollection<Document> mongoCollection = db.getCollection(collection);
            FindIterable<Document> documents = mongoCollection.find().limit(10);
            for (Document document : documents) {
                log.info("document:{}", document);
                Tuple3<String, String ,Long> tuple3 = Tuple3.of(document.getString("key"), document.getString("name"), document.getLong("timestamp"));
                ctx.collectWithTimestamp(tuple3, Optional.ofNullable(tuple3.f2).orElse(System.currentTimeMillis()));
            }
        }
        log.info("mongo source failed");
    }

    @Override
    public void cancel() {
    }

    @Override
    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
