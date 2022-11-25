package iee.yh.Mymall.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import iee.yh.Mymall.product.annotation.MyCacheAnno;
import iee.yh.Mymall.product.service.CategoryBrandRelationService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    private Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Resource
    private CategoryDao categoryDao;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redissonClient;


    @Override
    @MyCacheAnno(
            name = "mall-product",
            ttl = 360 * 1000
    )
    public List<CategoryEntity> listWithTree(){
        return listWithTreeFromDB();
    }


    // @Cacheable(value = "catagory",key = "'mall-product'")
//    @Override
//    public List<CategoryEntity> listWithTree(){
//        // 查询缓存
//        String catalogJSON = redisTemplate.opsForValue().get("catalogJSON");
//        // Double Check
//        if (StringUtils.isEmpty(catalogJSON)){
//            List<CategoryEntity> categoryEntities = null;
//            // 加锁 防止缓存击穿
//            RLock lock = redissonClient.getLock("product-lock");
//            lock.lock();
//            try{
//                catalogJSON = redisTemplate.opsForValue().get("catalogJSON");
//                if (StringUtils.isEmpty(catalogJSON)){
//                    // 缓存中没有则查询数据库
//                    categoryEntities = listWithTreeFromDB();
//                    // 放入缓存
//                    catalogJSON = JSON.toJSONString(categoryEntities);
//                    redisTemplate.opsForValue().set("catalogJSON",catalogJSON);
//                    return categoryEntities;
//                }
//            }finally {
//                if (lock.isLocked() && lock.isHeldByCurrentThread())
//                    lock.unlock();
//            }
//        }
//        logger.info("缓存生效!");
//        // 反序列化（JSON --> List）
//        List<CategoryEntity> res = JSON.parseObject(catalogJSON, new TypeReference<List<CategoryEntity>>(){});
//        return res;
////        List<CategoryEntity> categoryEntities = null;
////        // 加锁 防止缓存击穿
////        RLock lock = redissonClient.getLock("product-lock");
////        lock.lock();
////        try{
////            String catalogJSON = redisTemplate.opsForValue().get("catagory::mall-product");
////            if (StringUtils.isEmpty(catalogJSON)){
////                // 缓存中没有则查询数据库
////                categoryEntities = listWithTreeFromDB();
////                // 放入缓存
////                catalogJSON = JSON.toJSONString(categoryEntities);
////                redisTemplate.opsForValue().set("catagory::mall-product",catalogJSON);
////                return categoryEntities;
////            }
////        }finally {
////            if (lock.isLocked() && lock.isHeldByCurrentThread())
////                lock.unlock();
////        }
////        return categoryEntities;
//    }


    private List<CategoryEntity> listWithTreeFromDB() {
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

    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> paths= new ArrayList<>();
        findParentPath(catelogId,paths);
        Collections.reverse(paths);
        return (Long[]) paths.toArray(new Long[paths.size()]);
    }

    private void findParentPath(Long catelogId,List paths){
        CategoryEntity byId = this.getById(catelogId);
        paths.add(byId.getCatId());
        if (byId.getParentCid() != 0){
            findParentPath(byId.getParentCid(),paths);
        }
        return;
    }

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Transactional
    @Override
    public void updateByIdDetail(CategoryEntity category) {
        // 更新采用延时双删策略
        // step 1、先删缓存
        redisTemplate.delete("catalogJSON");
        // step 2、更新缓存
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(),category.getName());
        // step 3、延时删除(异步)
        CompletableFuture.supplyAsync(()->{
            try {
                TimeUnit.SECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return redisTemplate.delete("catalogJSON");
        });
        //TODO 更新其他关联
    }

    @Override
    public List<CategoryEntity> getLeve1Categorys() {
        return baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid",0));
    }
}