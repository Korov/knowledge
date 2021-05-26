package org.korov.flink.common.sink;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.korov.flink.common.model.NameModel;

import java.util.ArrayList;
import java.util.List;

public class KeyAlertMongoSink extends RichSinkFunction<Tuple3<String, NameModel, Long>> {
    private final String host;
    private final int port;
    private final String dbname;
    private final String collection;
    MongoClient mongoClient = null;

    public KeyAlertMongoSink(String host, int port, String dbname, String collection) {
        this.host = host;
        this.port = port;
        this.dbname = dbname;
        this.collection = collection;
    }

    @Override
    public void invoke(Tuple3<String, NameModel, Long> value, Context context) {
        if (mongoClient != null) {
            MongoDatabase db = mongoClient.getDatabase(dbname);
            MongoCollection<Document> mongoCollection = db.getCollection(collection);
            List<Document> documents = new ArrayList<>();
            Document document = new Document();
            document.append("key", value.f0);
            document.append("name", value.f1.getName());
            document.append("timestamp", value.f1.getTimestamp());
            ObjectMapper mapper = new ObjectMapper();
            try {
                document.append("value", mapper.writeValueAsString(value.f1));
            } catch (JsonProcessingException e) {
                document.append("exception", e.toString());
            }
            document.append("message", value.f1.getMessage());
            document.append("count", value.f2);
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
