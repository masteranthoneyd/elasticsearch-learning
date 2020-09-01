package com.eyangbingdong.elasticsearch.initialize.connect;

import lombok.extern.slf4j.Slf4j;
import org.frameworkset.elasticsearch.boot.BBossESStarter;
import org.frameworkset.elasticsearch.client.ClientInterface;
import org.frameworkset.spi.BaseApplicationContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author <a href="mailto:yangbingdong1994@gmail.com">yangbingdong</a>
 * @since
 */
@Slf4j
@SpringBootTest
class ElasticsearchConnectionTest {

    @Autowired
    private BBossESStarter esStarter;

    @Test
    void connectTest() {
        ClientInterface restClient = esStarter.getRestClient();
        //验证环境,获取es状态
		String response = restClient.executeHttp("_cluster/state?pretty",ClientInterface.HTTP_GET);
	    log.info(response);
        BaseApplicationContext.shutdown();
    }

    @Test
    void initMockData() {

    }
}
