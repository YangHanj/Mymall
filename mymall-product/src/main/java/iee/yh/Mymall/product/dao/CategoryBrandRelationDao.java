package iee.yh.Mymall.product.dao;

import iee.yh.Mymall.product.entity.CategoryBrandRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 品牌分类关联
 * 
 * @author yanghan
 * @email yanghan1214@163.com
 * @date 2022-04-10 08:55:14
 */
@Mapper
public interface CategoryBrandRelationDao extends BaseMapper<CategoryBrandRelationEntity> {

    void updateCategory(@Param("id") Long catId, @Param("name") String name);
}
