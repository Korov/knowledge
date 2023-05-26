package org.korov.flink.name.count.sink;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.bulk.CreateOperation;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.google.common.collect.ImmutableMap;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.metrics.Gauge;
import org.apache.flink.metrics.MetricGroup;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.bson.Document;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.korov.flink.name.count.enums.SinkType;
import org.korov.flink.name.count.model.NameModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZoneId;
import java.util.*;

/**
 * @author korov
 */
public class KeyAlertElasticSink extends RichSinkFunction<Tuple3<String, NameModel, Long>> {
    private static final Logger log = LoggerFactory.getLogger(KeyAlertElasticSink.class);
    private final String host;
    private final int port;
    private final String indexName;
    private final String userName;
    private final String password;
    private final SinkType sinkType;
    ElasticsearchClient elasticClient = null;
    private transient long elasticSinkCount = 0L;

    public KeyAlertElasticSink(String host, int port, String indexName, String userName, String password, SinkType sinkType) {
        this.host = host;
        this.port = port;
        this.indexName = indexName;
        this.userName = userName;
        this.password = password;
        this.sinkType = sinkType;
    }

    @Override
    public void invoke(Tuple3<String, NameModel, Long> value, Context context) {
        Long timestamp = value.f1.getTimestamp();
        String time = extractTime(timestamp);
        List<BulkOperation> bulkOperations = new ArrayList<>();
        CreateOperation.Builder<Map> builder = new CreateOperation.Builder<>();
        builder.id(UUID.randomUUID().toString()).document(ImmutableMap.of("key3", "value3"));

        Map<String, Object> document = new HashMap<>();
        document.put("key", value.f0);
        document.put("time", time);
        document.put("timestamp", timestamp);
        document.put("min_time", extractTime(value.f1.getMinTime()));
        document.put("max_time", extractTime(value.f1.getMaxTime()));
        document.put("count", value.f2);
        if (sinkType == SinkType.KEY_NAME) {
            document.put("name", value.f1.getName());
        } else if (sinkType == SinkType.KEY_NAME_VALUE) {
            document.put("name", value.f1.getName());
            try {
                document.put("message", Document.parse(value.f1.getMessage()));
            } catch (Exception e) {
            }
            document.put("raw_message", value.f1.getMessage());
            document.put("uuid", value.f1.getUuid());
        }
        builder.document(document);

        BulkOperation.Builder operationBuilder = new BulkOperation.Builder();
        operationBuilder.create(builder.build());
        bulkOperations.add(operationBuilder.build());

        BulkRequest.Builder bulkBuilder = new BulkRequest.Builder();
        bulkBuilder.index(indexName)
                .operations(bulkOperations);
        if (elasticClient == null) {
            log.error("elastic client is invalid");
            return;
        }
        try {
            BulkResponse bulkResponse = elasticClient.bulk(bulkBuilder.build());

            elasticSinkCount += bulkResponse.items().size();
        } catch (Exception e) {
            log.error("insert documents failed, index:{}", indexName, e);
        }
    }

    private String extractTime(Long timestamp) {
        Date date = new Date(timestamp);
        Calendar calendar = Calendar.getInstance(Locale.SIMPLIFIED_CHINESE);
        calendar.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Asia/Shanghai")));
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int millisecond = calendar.get(Calendar.MILLISECOND);
        return String.format("%d-%02d-%02d %02d:%02d:%02d.%03d", year, month, day, hour, minute, second, millisecond);
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);

        // 会统计数据并展示在界面的metrics中
        MetricGroup metricGroup = getRuntimeContext().getMetricGroup();
        metricGroup.gauge("elasticSinkCount", (Gauge<Long>) () -> elasticSinkCount);

        CredentialsProvider credentialsProvider = null;
        if (userName != null && password != null) {
            credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY,
                    new UsernamePasswordCredentials(userName, password));
        }

        RestClientBuilder restClientBuilder = RestClient.builder(
                new HttpHost(host, port));
        if (credentialsProvider != null) {
            CredentialsProvider finalCredentialsProvider = credentialsProvider;
            restClientBuilder.setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                @Override
                public HttpAsyncClientBuilder customizeHttpClient(
                        HttpAsyncClientBuilder httpClientBuilder) {
                    return httpClientBuilder
                            .setDefaultCredentialsProvider(finalCredentialsProvider);
                }
            });
        }
        ElasticsearchTransport transport = new RestClientTransport(
                restClientBuilder.build(), new JacksonJsonpMapper());
        elasticClient = new ElasticsearchClient(transport);
    }


    @Override
    public void close() {
        if (elasticClient != null) {
            elasticClient.shutdown();
        }
    }
}
