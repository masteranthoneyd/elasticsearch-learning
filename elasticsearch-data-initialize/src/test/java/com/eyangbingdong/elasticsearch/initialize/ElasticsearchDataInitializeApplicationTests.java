package com.eyangbingdong.elasticsearch.initialize;

import com.alibaba.fastjson.JSONObject;
import com.eyangbingdong.elasticsearch.initialize.dto.Sku;
import com.eyangbingdong.elasticsearch.initialize.initializer.MockDataInitializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ElasticsearchDataInitializeApplicationTests {
    @Autowired
    private MockDataInitializer mockDataInitializer;

    @Test
    void contextLoads() {
    }

    @Test
    void init() {
        mockDataInitializer.readDataFromExcelAndAddOneToTempIndex("prod_temp");
    }

    @Test
    void createIndice() {
        mockDataInitializer.createProdIndice("prod-v1");
    }

    @Test
    void getDocument() {
        Sku sku = mockDataInitializer.getDocument("prod_temp", "11010100100101");
        System.out.println(JSONObject.toJSONString(sku, true));
    }

    @Test
    void initAllData() {
        String indiceName = "prod-v1";
        mockDataInitializer.createProdIndice(indiceName);
        mockDataInitializer.initData(indiceName);
    }

}
