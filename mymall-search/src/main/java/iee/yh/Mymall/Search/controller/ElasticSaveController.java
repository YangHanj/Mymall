package iee.yh.Mymall.Search.controller;

import iee.yh.Mymall.Search.service.ProductSaveService;
import iee.yh.common.exception.BizCode;
import iee.yh.common.to.es.SkuEsModel;
import iee.yh.common.utils.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search/save")
public class ElasticSaveController {

    private static Logger logger = LoggerFactory.getLogger(ElasticSaveController.class);

    @Autowired
    private ProductSaveService productSaveService;

    @PostMapping("/product")
    public R productStatusUp(@RequestBody List<SkuEsModel> skuEsModels){
        Boolean flag = false;
        try {
            flag = productSaveService.productStatusUp(skuEsModels);
        }catch (Exception e){
            logger.error("商品上架错误"+e);
            return R.error(BizCode.PRODUCT_UP_EXCEPTION.getCode(),BizCode.PRODUCT_UP_EXCEPTION.getMsg());
        }
        if (!flag){
            return R.ok();
        }else return R.error(BizCode.PRODUCT_UP_EXCEPTION.getCode(),BizCode.PRODUCT_UP_EXCEPTION.getMsg());
    }
}
