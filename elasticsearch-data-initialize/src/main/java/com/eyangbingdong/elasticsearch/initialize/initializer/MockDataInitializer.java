package com.eyangbingdong.elasticsearch.initialize.initializer;

import com.alibaba.fastjson.JSONObject;
import com.eyangbingdong.elasticsearch.initialize.dto.Sku;
import com.eyangbingdong.elasticsearch.initialize.util.CvsUtil;
import lombok.extern.slf4j.Slf4j;
import org.frameworkset.elasticsearch.ElasticSearchException;
import org.frameworkset.elasticsearch.boot.BBossESStarter;
import org.frameworkset.elasticsearch.client.ClientInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:yangbingdong1994@gmail.com">yangbingdong</a>
 * @since
 */
@Component
@Slf4j
public class MockDataInitializer {

    @Autowired
    private BBossESStarter bBossESStarter;

    public void readDataFromExcelAndAddOneToTempIndex(String tempIndexName) {
        List<Sku> skus = readFromCvs();
        log.info("Init data: {}", JSONObject.toJSONString(skus.get(0), true));
        ClientInterface restClient = bBossESStarter.getRestClient();
        restClient.addDocument(tempIndexName, skus.get(0));
        String indexMapping = restClient.getIndexMapping(tempIndexName);
        log.info("Init mapping: \n{}", indexMapping);
    }

    private List<Sku> readFromCvs() {
        List<Sku> skus = CvsUtil.readVsvToBean("prod.csv", Sku.class);
        Objects.requireNonNull(skus)
               .parallelStream()
               .forEach(s -> {
                // s.setCreateTime(LocalDateTime.ofInstant(s.getCreateDate().toInstant(), ZoneId.systemDefault()));
                // s.setUpdateTime(s.getCreateTime().plusDays(2L));
                   s.setCreateTime(s.getCreateDate());
                   s.setUpdateTime(s.getCreateDate());
            });
        return skus;
    }

    public void initData(String indexName) {
        List<Sku> skus = readFromCvs();
        ClientInterface restClient = bBossESStarter.getRestClient();
        restClient.addDocuments(indexName, skus);
    }

    public void createProdIndice(String indiceName) {
        ClientInterface clientUtil = bBossESStarter.getConfigRestClient("esmapper/dsl.xml");
        try {
            boolean exist = clientUtil.existIndice(indiceName);
            if (exist) {
                clientUtil.dropIndice(indiceName);
            }
            clientUtil.createIndiceMapping(indiceName, "createProdIndice");
            String demoIndice = clientUtil.getIndice(indiceName);
            System.out.println("after createIndiceMapping response:" + demoIndice);
        } catch (ElasticSearchException e) {
            e.printStackTrace();
        }
    }

    public Sku getDocument(String indiceName, String docId) {
        return bBossESStarter.getRestClient().getDocument(indiceName, docId, Sku.class);
    }
}
