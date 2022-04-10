package iee.yh.Mymall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import iee.yh.Mymall.product.entity.CategoryBrandRelationEntity;
import iee.yh.common.utils.PageUtils;


import java.util.Map;

/**
 * 品牌分类关联
 *
 * @author yanghan
 * @email yanghan1214@163.com
 * @date 2022-04-10 08:55:14
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveDetail(CategoryBrandRelationEntity categoryBrandRelation);

    void updateBrand(Long brandId, String name);

    void updateCategory(Long catId, String name);
}

