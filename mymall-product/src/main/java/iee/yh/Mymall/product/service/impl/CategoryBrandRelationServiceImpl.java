package iee.yh.Mymall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import iee.yh.Mymall.product.dao.BrandDao;
import iee.yh.Mymall.product.dao.CategoryDao;
import iee.yh.Mymall.product.entity.BrandEntity;
import iee.yh.Mymall.product.entity.CategoryEntity;
import iee.yh.Mymall.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import iee.yh.common.utils.PageUtils;
import iee.yh.common.utils.Query;

import iee.yh.Mymall.product.dao.CategoryBrandRelationDao;
import iee.yh.Mymall.product.entity.CategoryBrandRelationEntity;
import iee.yh.Mymall.product.service.CategoryBrandRelationService;

import javax.annotation.Resource;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Autowired
    private BrandDao brandDao;
    @Autowired
    private CategoryDao categoryDao;
    @Override
    public void saveDetail(CategoryBrandRelationEntity categoryBrandRelation) {
        //品牌ID
        Long brandId = categoryBrandRelation.getBrandId();
        //分类ID
        Long catelogId = categoryBrandRelation.getCatelogId();
        //查询详细名称
        BrandEntity brandEntity = brandDao.selectById(brandId);
        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
        categoryBrandRelation.setBrandName(brandEntity.getName());
        categoryBrandRelation.setCatelogName(categoryEntity.getName());
        this.save(categoryBrandRelation);
    }

    @Override
    public void updateBrand(Long brandId, String name) {
        CategoryBrandRelationEntity categoryBrandRelationEntity = new CategoryBrandRelationEntity();
        categoryBrandRelationEntity.setBrandId(brandId);
        categoryBrandRelationEntity.setBrandName(name);
        this.update(categoryBrandRelationEntity,new UpdateWrapper<CategoryBrandRelationEntity>().eq("brand_id",brandId));
    }

    @Override
    public void updateCategory(Long catId, String name) {
        this.baseMapper.updateCategory(catId,name);
    }

    @Override
    public List<BrandEntity> getBrandWithCategory(Long catId) {
        List<CategoryBrandRelationEntity> categoryBrandRelationEntitys = this.baseMapper.selectList(
                            new QueryWrapper<CategoryBrandRelationEntity>().eq("catelog_id",catId));

        List<Long> brands = categoryBrandRelationEntitys.stream().map(info -> {
            return info.getBrandId();
        }).collect(Collectors.toList());

        List<BrandEntity> collect = brands.stream().map(brand -> {
            BrandEntity brandEntity = brandDao.selectById(brand);
            return brandEntity;
        }).collect(Collectors.toList());
        return collect;
    }

}