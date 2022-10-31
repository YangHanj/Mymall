package iee.yh.Mymall.product.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import iee.yh.common.utils.PageUtils;
import iee.yh.common.utils.Query;

import iee.yh.Mymall.product.dao.SkuInfoDao;
import iee.yh.Mymall.product.entity.SkuInfoEntity;
import iee.yh.Mymall.product.service.SkuInfoService;


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

}