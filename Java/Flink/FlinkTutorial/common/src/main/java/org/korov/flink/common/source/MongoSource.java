package org.korov.flink.common.source;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.state.ListState;
import org.apache.flink.api.common.state.ListStateDescriptor;
import org.apache.flink.api.common.state.ReducingState;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeutils.base.LongSerializer;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.runtime.state.FunctionInitializationContext;
import org.apache.flink.runtime.state.FunctionSnapshotContext;
import org.apache.flink.streaming.api.checkpoint.CheckpointedFunction;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.bson.Document;

import java.util.List;
import java.util.Optional;

/**
 * @author zhu.lei
 * @date 2021-05-05 13:26
 */
@Slf4j
public class MongoSource extends RichSourceFunction<Tuple3<String, String, Long>> implements CheckpointedFunction {
    private final String host;
    private final int port;
    private final String dbname;
    private final String collection;
    MongoClient mongoClient = null;

    /**
     * current offset for exactly once semantics
     */
    private Long offset = 0L;

    /**
     * flag for job cancellation
     */
    private volatile boolean isRunning = true;

    /**
     * Our state object.
     */
    private ListState<Long> state;

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
    public void run(SourceContext<Tuple3<String, String, Long>> ctx) throws Exception {
        final Object lock = ctx.getCheckpointLock();
        while (isRunning) {
            // output and state update are atomic
            synchronized (lock) {
                if (mongoClient != null) {
                    log.info("mongo source start");
                    MongoDatabase db = mongoClient.getDatabase(dbname);
                    MongoCollection<Document> mongoCollection = db.getCollection(collection);
                    FindIterable<Document> documents = mongoCollection.find().skip(offset.intValue());
                    for (Document document : documents) {
                        offset++;
                        Tuple3<String, String, Long> tuple3 = Tuple3.of(document.getString("key"), document.getString("name"), document.getLong("timestamp"));
                        ctx.collectWithTimestamp(tuple3, Optional.ofNullable(tuple3.f2).orElse(System.currentTimeMillis()));
                    }
                }
                log.info("mongo source failed");
            }
        }
    }

    @Override
    public void cancel() {
        isRunning = false;
    }

    @Override
    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }

    @Override
    public void snapshotState(FunctionSnapshotContext context) throws Exception {
        state.clear();
        state.add(offset);
    }

    @Override
    public void initializeState(FunctionInitializationContext context) throws Exception {
        state = context.getOperatorStateStore().getListState(new ListStateDescriptor<>(
                "state",
                LongSerializer.INSTANCE));

        // restore any state that we might already have to our fields, initialize state
        // is also called in case of restore.
        for (Long l : state.get()) {
            offset = l;
        }
    }
}
