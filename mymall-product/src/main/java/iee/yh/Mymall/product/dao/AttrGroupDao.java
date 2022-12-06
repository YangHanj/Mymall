package iee.yh.Mymall.product.dao;

import iee.yh.Mymall.product.entity.AttrGroupEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import iee.yh.Mymall.product.vo.SkuItemVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性分组
 * 
 * @author yanghan
 * @email yanghan1214@163.com
 * @date 2022-04-03 08:57:06
 */
@Mapper
public interface AttrGroupDao extends BaseMapper<AttrGroupEntity> {

    List<SkuItemVo.spuItemAttrGroupVo> getAttrGorupWithAttrsBySpuId(@Param("spuId") Long spuId, @Param("catalogId") Long catalogId);
}
