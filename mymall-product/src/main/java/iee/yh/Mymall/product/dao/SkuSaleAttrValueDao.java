package iee.yh.Mymall.product.dao;

import iee.yh.Mymall.product.entity.SkuSaleAttrValueEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import iee.yh.Mymall.product.vo.SkuItemVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * sku销售属性&值
 * 
 * @author yanghan
 * @email yanghan1214@163.com
 * @date 2022-04-02 17:37:04
 */
@Mapper
public interface SkuSaleAttrValueDao extends BaseMapper<SkuSaleAttrValueEntity> {

    List<SkuItemVo.skuItemSaleAttrVo> getSaleAttrsBySpuId(Long spuId);

    List<String> getSkuSaleAttrValuesAsStringList(@Param("skuId") Long skuId);

}
