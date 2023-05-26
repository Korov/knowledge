package org.korov.flink.common.source;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.CreateRequest;
import co.elastic.clients.elasticsearch.core.CreateResponse;
import co.elastic.clients.elasticsearch.core.InfoResponse;
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

import java.io.IOException;
import java.util.Map;

public class ElasticTest {


    @Test
    public void connect() throws IOException {

        ElasticsearchClient client = createClient();
        InfoResponse response = client.info();
        System.out.println(response);
    }

    @Test
    public void put() throws IOException {
        ElasticsearchClient client = createClient();
        CreateRequest.Builder<Map<String, String>> builder = new CreateRequest.Builder<>();
        CreateRequest<Map<String, String>> request = builder.id("5").document(ImmutableMap.of("key", "value")).index("map_index").build();
        CreateResponse response = client.create(request);
        System.out.println(response.toString());
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
