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
import org.korov.flink.common.enums.SinkType;
import org.korov.flink.common.model.NameModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class KeyAlertMongoSink extends RichSinkFunction<Tuple3<String, NameModel, Long>> {
    private static final Logger log = LoggerFactory.getLogger(KeyAlertMongoSink.class);
    private final String host;
    private final int port;
    private final String dbname;
    private final String collection;
    private final SinkType sinkType;
    MongoClient mongoClient = null;
    MongoClient localMongoClient = null;

    public KeyAlertMongoSink(String host, int port, String dbname, String collection, SinkType sinkType) {
        this.host = host;
        this.port = port;
        this.dbname = dbname;
        this.collection = collection;
        this.sinkType = sinkType;
    }

    @Override
    public void invoke(Tuple3<String, NameModel, Long> value, Context context) {
        List<Document> documents = new ArrayList<>();
        Document document = new Document();
        document.append("key", value.f0);
        document.append("timestamp", value.f1.getTimestamp());
        document.append("count", value.f2);
        if (sinkType == SinkType.KEY_NAME) {
            document.append("name", value.f1.getName());
        } else if (sinkType == SinkType.KEY_NAME_VALUE) {
            document.append("name", value.f1.getName());
            document.append("message", value.f1.getMessage());
            document.append("uuid", value.f1.getUuid());
        }
        documents.add(document);
        if (mongoClient == null) {
            return;
        }
        try {
            MongoDatabase db = mongoClient.getDatabase(dbname);
            MongoCollection<Document> mongoCollection = db.getCollection(collection);
            mongoCollection.insertMany(documents);
        } catch (Exception e) {
            log.error("insert documents filed, collection:{}", collection, e);
        }

        if (localMongoClient == null) {
            return;
        }
        try {
            MongoDatabase db = localMongoClient.getDatabase(dbname);
            MongoCollection<Document> mongoCollection = db.getCollection(collection);
            mongoCollection.insertMany(documents);
        } catch (Exception e) {
            log.error("insert documents filed, local collection:{}", collection, e);
        }
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        ServerAddress serverAddress = new ServerAddress(host, port);
        //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
        MongoCredential mongoCredential = MongoCredential.createScramSha256Credential("kafka", "kafka", "kafka".toCharArray());
        MongoClientOptions options = MongoClientOptions.builder().maxConnectionIdleTime(6000).build();
        //通过连接认证获取MongoDB连接
        mongoClient = new MongoClient(ImmutableList.of(serverAddress), mongoCredential, options);

        ServerAddress localServerAddress = new ServerAddress("192.168.50.100", 27017);
        MongoClientOptions localOptions = MongoClientOptions.builder().maxConnectionIdleTime(6000).build();
        //通过连接认证获取MongoDB连接
        localMongoClient = new MongoClient(ImmutableList.of(localServerAddress), localOptions);
    }


    @Override
    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
        if (localMongoClient != null) {
            localMongoClient.close();
        }
    }
}
