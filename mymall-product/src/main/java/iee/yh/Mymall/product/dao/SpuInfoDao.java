package iee.yh.Mymall.product.dao;

import iee.yh.Mymall.product.entity.SpuInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * spu信息
 * 
 * @author yanghan
 * @email yanghan1214@163.com
 * @date 2022-04-02 17:37:04
 */
@Mapper
public interface SpuInfoDao extends BaseMapper<SpuInfoEntity> {
	void updateSpuStatus(@Param("spuId") Long spuId,@Param("code") int code);
}
