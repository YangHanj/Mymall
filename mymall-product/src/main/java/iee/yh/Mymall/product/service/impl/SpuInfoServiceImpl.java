package iee.yh.Mymall.product.service.impl;

import iee.yh.Mymall.product.entity.*;
import iee.yh.Mymall.product.feign.CouponFeignService;
import iee.yh.Mymall.product.service.*;
import iee.yh.Mymall.product.vo.*;
import iee.yh.common.to.SkuReductionTo;
import iee.yh.common.to.SpuBoundTo;
import iee.yh.common.to.es.SkuEsModel;
import iee.yh.common.utils.R;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import iee.yh.common.utils.PageUtils;
import iee.yh.common.utils.Query;

import iee.yh.Mymall.product.dao.SpuInfoDao;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.xml.crypto.Data;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Autowired
    private SpuInfoDescService spuInfoDescService;
    @Autowired
    private SpuImagesService spuImagesService;
    @Autowired
    private AttrService attrService;
    @Autowired
    private ProductAttrValueServiceImpl productAttrValueService;
    @Autowired
    private SkuInfoService skuInfoService;
    @Autowired
    private SkuImagesService skuImagesService;
    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;
    @Autowired
    private CouponFeignService couponFeignService;
    @Transactional
    @Override
    //TODO 高级部分完善
    public void saveSpuInfo(SpuSaveVo spuSaveVo) {
        /**
         * pms_spu_info : spu基本信息
         * pms_spu_images : 图片集信息
         * pms_spu_desc : 描述信息
         * pms_product_attr_values : spu的attr属性
         * pms_sku_info : sku的基本属性
         * pms_sku_images : sku的图片信息
         * pms_sku_sale_attr_value : sku销售属性
         *
         */
        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
        BeanUtils.copyProperties(spuSaveVo,spuInfoEntity);
        spuInfoEntity.setCreateTime(new Date());
        spuInfoEntity.setUpdateTime(new Date());
        this.saveBaseSpuInfo(spuInfoEntity);

        List<String> decripts = spuSaveVo.getDecript();
        SpuInfoDescEntity spuInfoDescEntity = new SpuInfoDescEntity();
        spuInfoDescEntity.setSpuId(spuInfoEntity.getId());
        spuInfoDescEntity.setDecript(String.join(",",decripts));
        spuInfoDescService.saveSpuInfoDesc(spuInfoDescEntity);

        List<String> images = spuSaveVo.getImages();
        spuImagesService.saveImages(spuInfoEntity.getId(),images);

        List<BaseAttrs> baseAttrs = spuSaveVo.getBaseAttrs();
        List<ProductAttrValueEntity> collect = baseAttrs.stream().map(info -> {
            ProductAttrValueEntity productAttrValueEntity = new ProductAttrValueEntity();
            productAttrValueEntity.setAttrId(info.getAttrId());
            AttrEntity attr = attrService.getById(info.getAttrId());
            productAttrValueEntity.setAttrName(attr.getAttrName());
            productAttrValueEntity.setAttrValue(info.getAttrValues());
            productAttrValueEntity.setQuickShow(info.getShowDesc());
            productAttrValueEntity.setSpuId(spuInfoEntity.getId());
            return productAttrValueEntity;
        }).collect(Collectors.toList());

        productAttrValueService.saveProductAttr(collect);

        SpuBoundTo spuBoundTo = new SpuBoundTo();
        BeanUtils.copyProperties(spuSaveVo.getBounds(),spuBoundTo);
        spuBoundTo.setSpuId(spuInfoEntity.getId());
        R r = couponFeignService.saveSpuBounds(spuBoundTo);
        if (r.getCode() != 0)
            log.error("远程保存Spu积分信息失败");
        List<Skus> skus = spuSaveVo.getSkus();
        if (skus != null && skus.size()>=1){
            skus.forEach(info -> {
                String defaultImage = "";
                for (Images image : info.getImages()) {
                    if (image.getDefaultImg() == 1)
                        defaultImage = image.getImgUrl();
                }
                SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
                BeanUtils.copyProperties(info,skuInfoEntity);
                skuInfoEntity.setBrandId(spuInfoEntity.getBrandId());
                skuInfoEntity.setCatalogId(spuInfoEntity.getCatalogId());
                skuInfoEntity.setSaleCount(0l);
                skuInfoEntity.setSpuId(spuInfoEntity.getId());
                skuInfoEntity.setSkuDefaultImg(defaultImage);
                skuInfoService.saveSkuInfo(skuInfoEntity);

                Long skuId = skuInfoEntity.getSkuId();
                List<SkuImagesEntity> skuImagesEntities = info.getImages().stream().map(img -> {
                    SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
                    skuImagesEntity.setSkuId(skuId);
                    skuImagesEntity.setImgUrl(img.getImgUrl());
                    skuImagesEntity.setDefaultImg(img.getDefaultImg());
                    return skuImagesEntity;
                }).filter(i->{
                    return !StringUtils.isEmpty(i.getImgUrl());
                }).collect(Collectors.toList());
                skuImagesService.saveBatch(skuImagesEntities);

                List<Attr> attr = info.getAttr();
                List<SkuSaleAttrValueEntity> skuSaleAttrValueEntities = attr.stream().map(a -> {
                    SkuSaleAttrValueEntity skuSaleAttrValueEntity = new SkuSaleAttrValueEntity();
                    BeanUtils.copyProperties(a, skuSaleAttrValueEntity);
                    skuSaleAttrValueEntity.setSkuId(skuId);
                    return skuSaleAttrValueEntity;
                }).collect(Collectors.toList());
                skuSaleAttrValueService.saveBatch(skuSaleAttrValueEntities);

                SkuReductionTo skuReductionTo = new SkuReductionTo();
                BeanUtils.copyProperties(info,skuReductionTo);
                skuReductionTo.setSkuId(skuId);
                if (skuReductionTo.getFullCount() > 0 || skuReductionTo.getFullPrics().compareTo(new BigDecimal(0)) == 1 ){
                    R r1 = couponFeignService.saveSkuReduction(skuReductionTo);
                    if (r1.getCode() != 0)
                        log.error("远程保存sku优惠信息失败");
                }
            });
        }
    }

    @Override
    public void saveBaseSpuInfo(SpuInfoEntity spuInfoEntity) {
        this.baseMapper.insert(spuInfoEntity);
    }

    @Override
    public PageUtils queryPageBycondition(Map<String, Object> params) {
        QueryWrapper<SpuInfoEntity> queryWrapper = new QueryWrapper();
        if (!StringUtils.isEmpty((String) params.get("key"))){
            String key = (String) params.get("key");
            queryWrapper.and((q)->{
                q.eq("id",key).or().like("spu_name",key);
            });
        }
        if (!StringUtils.isEmpty((String) params.get("status"))){
            String status = (String) params.get("status");
            queryWrapper.eq("publish_status",status);
        }
        if (!StringUtils.isEmpty((String) params.get("brandId"))){
            String brandId = (String) params.get("brandId");
            queryWrapper.eq("brand_id",brandId);
        }
        if (!StringUtils.isEmpty((String) params.get("catelogId"))){
            String catelogId = (String) params.get("catelogId");
            queryWrapper.eq("catalog_id",catelogId);
        }
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }

    @Autowired
    BrandService brandService;

    @Resource
    CategoryService categoryService;

    @Override
    public void up(Long spuId) {
        List<SkuEsModel> upProducts = new ArrayList<>();

        // 查询当前spuid对应的商品信息
        List<SkuInfoEntity> skuInfoEntities = skuInfoService.getSkusBySpuId(spuId);

        // 查询sku规格属性
        List<ProductAttrValueEntity> productAttrValueEntities = productAttrValueService.baseAttrListForSpu(spuId);
        List<Long> attrIds = productAttrValueEntities.stream().map( attr -> attr.getAttrId() ).collect(Collectors.toList());


        List<Long> searchAttrIds =  attrService.selectSearchAttrsIds(attrIds);
        Set<Long> idSet = new HashSet<>(searchAttrIds);

        List<SkuEsModel.Attrs> collect = productAttrValueEntities.stream()
                .filter(item -> idSet.contains(item.getAttrId()))
                .map(item -> {
                    SkuEsModel.Attrs attrs = new SkuEsModel.Attrs();
                    BeanUtils.copyProperties(item, attrs);
                    return attrs;
                }).collect(Collectors.toList());


        // 封装sku信息
        skuInfoEntities.stream().map(sku -> {
            // 商品上架
            SkuEsModel skuEsModel = new SkuEsModel();
            BeanUtils.copyProperties(sku,skuEsModel);
            skuEsModel.setSkuPrice(sku.getPrice());
            skuEsModel.setSkuImg(sku.getSkuDefaultImg());
            //TODO 查询是否有库存
            //TODO 热度评分

            // 查询品牌和分类的名字信息
            BrandEntity byId = brandService.getById(skuEsModel.getBrandId());
            skuEsModel.setBrandImg(byId.getLogo());
            skuEsModel.setBrandName(byId.getName());

            CategoryEntity byId1 = categoryService.getById(skuEsModel.getCatalogId());
            skuEsModel.setCatalogName(byId1.getName());

            skuEsModel.setAttrs(collect);

            return skuEsModel;
        }).collect(Collectors.toList());

        // todo 发送es执行保存 （search服务）

;    }
}