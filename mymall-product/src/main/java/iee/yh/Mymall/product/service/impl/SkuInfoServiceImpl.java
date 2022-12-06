package iee.yh.Mymall.product.service.impl;

import iee.yh.Mymall.product.entity.SkuImagesEntity;
import iee.yh.Mymall.product.entity.SpuInfoDescEntity;
import iee.yh.Mymall.product.service.*;
import iee.yh.Mymall.product.vo.SkuItemVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Function;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import iee.yh.common.utils.PageUtils;
import iee.yh.common.utils.Query;

import iee.yh.Mymall.product.dao.SkuInfoDao;
import iee.yh.Mymall.product.entity.SkuInfoEntity;

import javax.annotation.Resource;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                new QueryWrapper<SkuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveSkuInfo(SkuInfoEntity skuInfoEntity) {
        this.baseMapper.insert(skuInfoEntity);
    }

    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {
        QueryWrapper<SkuInfoEntity> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty((String) params.get("key"))){
            String key = (String) params.get("key");
            queryWrapper.and((q)->{
                q.eq("sku_id",key).or().like("sku_name",key);
            });
        }
        if (!StringUtils.isEmpty((String) params.get("catelogId"))){
            String catelogId = (String) params.get("catelogId");
            if (!"0".equalsIgnoreCase(catelogId))
                queryWrapper.eq("catalog_id",catelogId);
        }
        if (!StringUtils.isEmpty((String) params.get("brandId"))){
            String brandId = (String) params.get("brandId");
            if (!"0".equalsIgnoreCase(brandId))
                queryWrapper.eq("brand_id",brandId);
        }
        String max = (String) params.get("max");
        String min = (String) params.get("min");
        if (!StringUtils.isEmpty(min))
            queryWrapper.ge("price",min);
        if (!StringUtils.isEmpty(max) && "0".equalsIgnoreCase(max))
            queryWrapper.le("price",max);
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }

    @Override
    public List<SkuInfoEntity> getSkusBySpuId(Long spuId) {
        List<SkuInfoEntity> list = this.list(new QueryWrapper<SkuInfoEntity>().eq("spu_id",spuId));
        return list;
    }

    @Resource
    SkuImagesService imagesService;

    @Resource
    SpuInfoDescService spuInfoDescService;

    @Resource
    AttrGroupService attrGroupService;

    @Resource
    SkuSaleAttrValueService skuSaleAttrValueService;

    @Resource
    ThreadPoolExecutor threadPoolExecutor;

    @Override
    public SkuItemVo item(Long skuId) {
        SkuItemVo skuItemVo = new SkuItemVo();

        CompletableFuture<SkuInfoEntity> InfoFuture = CompletableFuture.supplyAsync(() -> {
            // 1、sku基本信息 pms_sku_info表
            SkuInfoEntity info = getById(skuId);
            skuItemVo.setInfo(info);
            return info;
        }, threadPoolExecutor);

        CompletableFuture<Void> SaleAttrFuture = InfoFuture.thenAcceptAsync(info -> {
            Long spuId = info.getSpuId();
            List<SkuItemVo.skuItemSaleAttrVo> saleAttrVos = skuSaleAttrValueService.getSaleAttrsBySpuId(spuId);
            skuItemVo.setsKuItemSaleAttrVo(saleAttrVos);
        }, threadPoolExecutor);

        CompletableFuture<Void> DescFuture = InfoFuture.thenAcceptAsync(info -> {
            Long spuId = info.getSpuId();
            SpuInfoDescEntity spuInfo = spuInfoDescService.getById(spuId);
            skuItemVo.setDesp(spuInfo);
        }, threadPoolExecutor);

        CompletableFuture<Void> BaseAttrFuture = InfoFuture.thenAcceptAsync(info -> {
            Long spuId = info.getSpuId();
            Long catalogId = info.getCatalogId();
            List<SkuItemVo.spuItemAttrGroupVo> attrGroupVos = attrGroupService.getAttrGorupWithAttrsBySpuId(spuId, catalogId);
            skuItemVo.setGroupAttrs(attrGroupVos);
        }, threadPoolExecutor);

        CompletableFuture<Void> ImagesFutrue = CompletableFuture.runAsync(() -> {
            List<SkuImagesEntity> images = imagesService.getImagesBySkuId(skuId);
            skuItemVo.setImages(images);
        }, threadPoolExecutor);

        try {
            CompletableFuture.allOf(SaleAttrFuture, DescFuture, BaseAttrFuture, ImagesFutrue).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
//        // 1、sku基本信息 pms_sku_info表
//        SkuInfoEntity info = getById(skuId);
//        skuItemVo.setInfo(info);
//        Long catalogId = info.getCatalogId();
//        // 2、sku图片信息 pms_sku_images
//        List<SkuImagesEntity> images = imagesService.getImagesBySkuId(skuId);
//        skuItemVo.setImages(images);
//        // 3、spu销售属性组合
//        Long spuId = info.getSpuId();
//        List<SkuItemVo.skuItemSaleAttrVo> saleAttrVos = skuSaleAttrValueService.getSaleAttrsBySpuId(spuId);
//        skuItemVo.setsKuItemSaleAttrVo(saleAttrVos);
//        // 4、共享spu信息 pms_spu_info_desc
//        SpuInfoDescEntity spuInfo = spuInfoDescService.getById(spuId);
//        skuItemVo.setDesp(spuInfo);
//        // 5、获取spu规格参数信息
//        List<SkuItemVo.spuItemAttrGroupVo> attrGroupVos = attrGroupService.getAttrGorupWithAttrsBySpuId(spuId, catalogId);
//        skuItemVo.setGroupAttrs(attrGroupVos);
        return null;
    }

}