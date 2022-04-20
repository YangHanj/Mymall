package iee.yh.Mymall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import iee.yh.common.utils.PageUtils;
import iee.yh.Mymall.product.entity.ProductAttrValueEntity;

import java.util.List;
import java.util.Map;

/**
 * spu属性值
 *
 * @author yanghan
 * @email yanghan1214@163.com
 * @date 2022-04-02 17:37:04
 */
public interface ProductAttrValueService extends IService<ProductAttrValueEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveProductAttr(List<ProductAttrValueEntity> collect);

    List<ProductAttrValueEntity> baseAttrListForSpu(Long spuId);
}

