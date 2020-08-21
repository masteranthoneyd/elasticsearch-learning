package com.yangbingdong.high.level.client.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:yangbingdong1994@gmail.com">yangbingdong</a>
 * @since
 */
@ConfigurationProperties(prefix = "elasticsearch")
@Data
public class ElasticsearchEndpointProperty {

    private List<Host> httpHosts = new ArrayList<>();

    private String username;

    private String password;

    private int connectTimeOut = 5000;

    private int socketTimeOut = 15000;

    private int connectionRequestTimeOut = 5000;

    private int maxConnectNum = 100;

    private int maxConnectPerRoute = 100;

    @Data
    public static class Host {
        private String hostname;

        private int port;
    }
}
