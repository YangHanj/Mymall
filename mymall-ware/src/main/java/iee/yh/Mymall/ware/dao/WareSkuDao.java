package iee.yh.Mymall.ware.dao;

import iee.yh.Mymall.ware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品库存
 * 
 * @author yanghan
 * @email yanghan1214@163.com
 * @date 2022-04-03 09:36:22
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {

    Long getSkuStock(Long sku);
}
