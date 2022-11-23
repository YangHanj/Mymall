package iee.yh.Mymall.Search.service.impl;

import com.alibaba.fastjson.JSON;
import iee.yh.Mymall.Search.config.ElasticConfig;
import iee.yh.Mymall.Search.constant.EsConstatnt;
import iee.yh.Mymall.Search.service.ProductSaveService;
import iee.yh.common.to.es.SkuEsModel;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductSaveServiceImpl implements ProductSaveService {

    private Logger logger = LoggerFactory.getLogger(ProductSaveServiceImpl.class);

    @Autowired
    private RestHighLevelClient restHighLevelClient;


    @Override
    public Boolean productStatusUp(List<SkuEsModel> skuEsModels) {
        // 保存进入es
        // 建立索引 product 建立映射关系
        // 保存数据
        BulkRequest bulkRequest = new BulkRequest();
        for (SkuEsModel skuEsModel : skuEsModels) {
            // 构造保存请求
            IndexRequest indexRequest = new IndexRequest();
            indexRequest.index(EsConstatnt.PRODUCT_INDEX);
            indexRequest.id(skuEsModel.getSkuId().toString());
            String jsonString = JSON.toJSONString(skuEsModel);
            indexRequest.source(jsonString, XContentType.JSON);
            bulkRequest.add(indexRequest);
        }
        try {
            BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, ElasticConfig.COMMON_OPTIONS);
            List<String> collect = Arrays.stream(bulk.getItems()).map(item -> item.getId()).collect(Collectors.toList());
            logger.error("商品上架成功:{}",collect);
            return bulk.hasFailures();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}