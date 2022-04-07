package iee.yh.Mymall.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import iee.yh.common.utils.PageUtils;
import iee.yh.common.utils.Query;

import iee.yh.Mymall.product.dao.CategoryDao;
import iee.yh.Mymall.product.entity.CategoryEntity;
import iee.yh.Mymall.product.service.CategoryService;

import javax.annotation.Resource;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Resource
    private CategoryDao categoryDao;
    @Override
    public List<CategoryEntity> listWithTree() {
        //1、查出所有分类
        List<CategoryEntity> categoryEntities = categoryDao.selectList(null);
        //2、组装父子结构
        //2.1、查找一级分类
        List<CategoryEntity> level = categoryEntities.stream().filter(categoryEntity -> {
            return categoryEntity.getParentCid() == 0;
        }).map(menu->{
            menu.setChild(findChild(menu,categoryEntities));
            return menu;
        }).sorted((menu1,menu2)->{
            return (menu1.getSort()==null?0:menu1.getSort()) - (menu2.getSort()==null?0:menu2.getSort());
        }).collect(Collectors.toList());
        return level;
    }
    /**
     *
     * @param root 当前菜单
     * @param all 所有菜单
     * @return 当前菜单的所有子菜单
     */
    public List<CategoryEntity> findChild(CategoryEntity root,List<CategoryEntity> all){
        List<CategoryEntity> collect = all.stream().filter(category -> {
            return category.getParentCid().equals(root.getCatId());
        }).map(category -> {
            category.setChild(findChild(category,all));
            return category;
        }).sorted((menu1,menu2)->{
            return (menu1.getSort()==null?0:menu1.getSort()) - (menu2.getSort()==null?0:menu2.getSort()) ;
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public void removeMenuByIds(List<Long> asList) {
        //检查
        //TODO 1、检查当前需要删除的是否被其他地方引用
        //......
        //TODO end
        categoryDao.deleteBatchIds(asList);
    }

    @Override
    public Integer getSameCategory(CategoryEntity category) {
        Integer sameCategory = categoryDao.getSameCategory(category);
        return sameCategory;
    }

    @Override
    public void updateByIdForProduct(Integer id) {
        categoryDao.updateByIdForProduct(id);
    }

}