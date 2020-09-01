package com.eyangbingdong.elasticsearch.initialize.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.frameworkset.orm.annotation.ESId;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author <a href="mailto:yangbingdong1994@gmail.com">yangbingdong</a>
 * @since
 */
@Data
public class Sku {
    @ESId
    @CsvBindByName
    private String skuId;
    @CsvBindByName
    private String prodId;
    @CsvBindByName
    private BigDecimal commission;
    @CsvBindByName
    private String prodName;

    // @ExcelProperty("prodPrice")
    @CsvBindByName(column = "prodPrice")
    private BigDecimal salePrice;
    @CsvBindByName
    private BigDecimal referPrice;
    @CsvBindByName
    private Long prodTotal;
    @CsvBindByName
    private Long saledTotal;
    @CsvBindByName
    private String pic;
    @CsvBindByName
    private String countryId;
    @CsvBindByName
    private String countryName;
    @CsvBindByName
    private String secondCataId;
    @CsvBindByName
    private String secondCataName;
    @CsvBindByName
    private String thirdCataId;
    @CsvBindByName
    private String thirdCataName;
    @CsvBindByName
    private String firstCataId;
    @CsvBindByName
    private String firstCataName;
    @CsvBindByName
    private String brandId;
    @CsvBindByName
    private String brandName;
    @CsvBindByName
    private String brandNameEn;
    @CsvBindByName
    private BigDecimal cashReturnTotal;
    @CsvBindByName
    private String prodAttr;

    @CsvBindByName
    @CsvDate("yyyy-MM-dd HH:mm:ss")
    @JsonIgnore
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date updateTime;
}
