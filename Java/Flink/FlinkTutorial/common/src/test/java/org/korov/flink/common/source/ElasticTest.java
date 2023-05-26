package org.korov.flink.common.source;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.bulk.CreateOperation;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.google.common.collect.ImmutableMap;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ElasticTest {

    private static final Logger log = LoggerFactory.getLogger(ElasticTest.class);

    @Test
    public void connect() throws IOException {
        ElasticsearchClient client = createClient();
        InfoResponse response = client.info();
        log.info(response.toString());
    }

    @Test
    public void put() throws IOException {
        ElasticsearchClient client = createClient();
        CreateRequest.Builder<Map<String, String>> builder = new CreateRequest.Builder<>();
        CreateRequest<Map<String, String>> request = builder.id("5").document(ImmutableMap.of("key", "value")).index("map_index").build();
        CreateResponse response = client.create(request);
        System.out.println(response.toString());
    }

    @Test
    public void bulk() throws IOException {
        List<BulkOperation> list = new ArrayList<>();

        CreateOperation.Builder<Map> builder = new CreateOperation.Builder<>();
        builder.id("8").document(ImmutableMap.of("key3", "value3"));

        BulkOperation.Builder operationBuilder = new BulkOperation.Builder();
        operationBuilder.create(builder.build());
        list.add(operationBuilder.build());


        builder = new CreateOperation.Builder<>();
        builder.id("9").document(ImmutableMap.of("key4", "value4"));

        operationBuilder = new BulkOperation.Builder();
        operationBuilder.create(builder.build());
        list.add(operationBuilder.build());

        BulkRequest.Builder bulkBuilder = new BulkRequest.Builder();
        bulkBuilder.index("map_index")
                .operations(list);

        ElasticsearchClient client = createClient();
        BulkResponse response = client.bulk(bulkBuilder.build());
        System.out.println(response.toString());
    }

    @Test
    public void get() throws IOException {
        ElasticsearchClient client = createClient();
        GetRequest.Builder builder = new GetRequest.Builder();
        builder.id("5").index("map_index");
        GetRequest request = builder.build();
        GetResponse<Map> response = client.get(request, Map.class);
        System.out.println(response.toString());
    }

    @Test
    public void update() throws IOException {
        ElasticsearchClient client = createClient();

        UpdateRequest.Builder<Map, Map> builder = new UpdateRequest.Builder<>();
        UpdateRequest request = builder.doc(ImmutableMap.of("key", "update_value")).id("5").index("map_index").build();
        UpdateResponse<Map> updateResponse = client.update(request, Map.class);
        System.out.println(updateResponse.toString());
    }


    private ElasticsearchClient createClient() {
        final CredentialsProvider credentialsProvider =
                new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("elastic", "password"));

        RestClient restClient = RestClient.builder(
                        new HttpHost("192.168.50.100", 9200))
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(
                            HttpAsyncClientBuilder httpClientBuilder) {
                        return httpClientBuilder
                                .setDefaultCredentialsProvider(credentialsProvider);
                    }
                })
                .build();
        ElasticsearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper());
        return new ElasticsearchClient(transport);
    }
}
