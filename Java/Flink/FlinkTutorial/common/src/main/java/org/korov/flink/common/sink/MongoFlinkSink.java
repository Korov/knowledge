package org.korov.flink.common.sink;

import com.google.common.collect.ImmutableList;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.bson.Document;
import org.korov.flink.common.model.KeyValue;

import java.util.ArrayList;
import java.util.List;

public class MongoFlinkSink extends RichSinkFunction<KeyValue> {
    private final String host;
    private final int port;
    private final String dbname;
    private final String collection;
    MongoClient mongoClient = null;

    public MongoFlinkSink(String host, int port, String dbname, String collection) {
        this.host = host;
        this.port = port;
        this.dbname = dbname;
        this.collection = collection;
    }

    @Override
    public void invoke(KeyValue value, Context context) {
        if (mongoClient != null) {
            MongoDatabase db = mongoClient.getDatabase(dbname);
            MongoCollection<Document> mongoCollection = db.getCollection(collection);
            List<Document> documents = new ArrayList<>();
            Document document = new Document();
            document.append("key", value.getKey());
            document.append("count", value.getCount());
            document.append("timestamp", value.getTimestamp());
            documents.add(document);

            mongoCollection.insertMany(documents);
        }
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        ServerAddress serverAddress = new ServerAddress(host, port);
        List<MongoCredential> credential = new ArrayList<MongoCredential>();

        //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
        MongoCredential mongoCredential = MongoCredential.createScramSha256Credential("admin", "admin", "admin".toCharArray());

        credential.add(mongoCredential);
        MongoClientOptions options = MongoClientOptions.builder().maxConnectionIdleTime(6000).build();
        //通过连接认证获取MongoDB连接
        mongoClient = new MongoClient(ImmutableList.of(serverAddress), credential, options);
    }


    @Override
    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}
