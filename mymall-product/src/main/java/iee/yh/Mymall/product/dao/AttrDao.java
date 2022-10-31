package iee.yh.Mymall.product.dao;

import iee.yh.Mymall.product.entity.AttrEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品属性
 * 
 * @author yanghan
 * @email yanghan1214@163.com
 * @date 2022-04-03 08:57:06
 */
@Mapper
public interface AttrDao extends BaseMapper<AttrEntity> {

    List<Long> selectSearchAttrsIds(@Param("attrIds") List<Long> attrIds);
}
