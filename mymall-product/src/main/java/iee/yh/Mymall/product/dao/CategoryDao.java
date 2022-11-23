package iee.yh.Mymall.product.dao;

import iee.yh.Mymall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import iee.yh.Mymall.product.service.CategoryService;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品三级分类
 * 
 * @author yanghan
 * @email yanghan1214@163.com
 * @date 2022-04-02 17:37:04
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
    Integer getSameCategory(CategoryEntity category);

    void updateByIdForProduct(@Param("cat_id") Integer id);

}
