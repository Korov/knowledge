package org.korov.flink.common.source;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.korov.flink.common.model.Biquge;

import java.util.Date;

@Slf4j
public class MongoTest {
    @Test
    public void test(){
        Date date = new Date("2021-08-25 17:38:56.918363");
        log.info("debug");
    }

    @Test
    public void queryBiquge() {
        ServerAddress serverAddress = new ServerAddress("192.168.50.189", 27017);

        //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
        MongoCredential credential = MongoCredential.createScramSha256Credential("", "admin", "".toCharArray());

        MongoClientOptions options = MongoClientOptions.builder().maxConnectionIdleTime(6000).build();
        //通过连接认证获取MongoDB连接
        MongoClient mongoClient = new MongoClient(serverAddress, options);
        MongoDatabase db = mongoClient.getDatabase("kafka");
        MongoCollection<Document> mongoCollection = db.getCollection("value-record");
        Iterable<Document> documents = mongoCollection.find().limit(10);
        for (Document document : documents) {
            log.info("document:{}", document);
        }
    }
}
