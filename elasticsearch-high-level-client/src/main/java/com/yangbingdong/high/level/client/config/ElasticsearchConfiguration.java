package com.yangbingdong.high.level.client.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.sniff.SniffOnFailureListener;
import org.elasticsearch.client.sniff.Sniffer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author <a href="mailto:yangbingdong1994@gmail.com">yangbingdong</a>
 * @since
 */
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ElasticsearchEndpointProperty.class)
@RequiredArgsConstructor
@Slf4j
public class ElasticsearchConfiguration {
    private final ElasticsearchEndpointProperty elasticsearchEndpointProperty;

    @Bean(destroyMethod = "close")
    public RestHighLevelClient restHighLevelClient() {
        RestClientBuilder builder = getBuilderWithHttpHosts();
        configDefaultHeader(builder);
        configTimeout(builder);
        configMaxConnAndAuth(builder);

        SniffOnFailureListener sniffOnFailureListener = new SniffOnFailureListener();
        builder.setFailureListener(sniffOnFailureListener);

        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(builder);

        Sniffer sniffer = Sniffer.builder(restHighLevelClient.getLowLevelClient())
                                 .setSniffIntervalMillis(60000)
                                 .setSniffAfterFailureDelayMillis(30000)
                                 .build();
        sniffOnFailureListener.setSniffer(sniffer);

        return restHighLevelClient;
    }

    private void configDefaultHeader(RestClientBuilder builder) {
        Header[] defaultHeaders = new Header[] {new BasicHeader("Content-Type", "application/json")};
        builder.setDefaultHeaders(defaultHeaders);
    }

    private void configMaxConnAndAuth(RestClientBuilder builder) {

        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            if (elasticsearchEndpointProperty.getUsername() != null
                && elasticsearchEndpointProperty.getPassword() != null) {
                UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(elasticsearchEndpointProperty.getUsername(), elasticsearchEndpointProperty.getPassword());
                CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
                credentialsProvider.setCredentials(AuthScope.ANY, credentials);
                httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            }
            httpClientBuilder.setMaxConnTotal(elasticsearchEndpointProperty.getMaxConnectNum())
                             .setMaxConnPerRoute(elasticsearchEndpointProperty.getMaxConnectPerRoute());
            return httpClientBuilder;
        });
    }

    private void configTimeout(RestClientBuilder builder) {
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(elasticsearchEndpointProperty.getConnectTimeOut())
                                .setSocketTimeout(elasticsearchEndpointProperty.getSocketTimeOut())
                                .setConnectionRequestTimeout(elasticsearchEndpointProperty.getConnectionRequestTimeOut());
            return requestConfigBuilder;
        });
    }

    private RestClientBuilder getBuilderWithHttpHosts() {
        List<ElasticsearchEndpointProperty.Host> hosts = elasticsearchEndpointProperty.getHttpHosts();
        if (hosts.size() == 0) {
            ElasticsearchEndpointProperty.Host host = new ElasticsearchEndpointProperty.Host();
            host.setHostname("127.0.0.1");
            host.setPort(9200);
        }
        HttpHost[] httpHosts = new HttpHost[hosts.size()];
        for (int i = 0; i < hosts.size(); i++) {
            ElasticsearchEndpointProperty.Host host = hosts.get(i);
            httpHosts[i] = new HttpHost(host.getHostname(), host.getPort(), "http");
        }
        return RestClient.builder(httpHosts);
    }
}
