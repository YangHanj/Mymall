package iee.yh.Mymall.ware.service.impl;

import iee.yh.Mymall.ware.vo.SkuHasstockVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import iee.yh.common.utils.PageUtils;
import iee.yh.common.utils.Query;

import iee.yh.Mymall.ware.dao.WareSkuDao;
import iee.yh.Mymall.ware.entity.WareSkuEntity;
import iee.yh.Mymall.ware.service.WareSkuService;


@Service("wareSkuService")
public class WareSkuServiceImpl extends ServiceImpl<WareSkuDao, WareSkuEntity> implements WareSkuService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),
                new QueryWrapper<WareSkuEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageByParams(Map<String, Object> params) {
        QueryWrapper<WareSkuEntity> queryWrapper = new QueryWrapper<>();
        String skuId = (String) params.get("skuId");
        String wareId = (String) params.get("wareId");
        if (!StringUtils.isEmpty(skuId)){
            queryWrapper.eq("sku_id",skuId);
        }
        if (!StringUtils.isEmpty(wareId)){
            queryWrapper.eq("ware_id",wareId);
        }
        IPage<WareSkuEntity> page = this.page(
                new Query<WareSkuEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }

    @Override
    public List<SkuHasstockVo> getSKuHasStock(List<Long> skuIds) {
        List<SkuHasstockVo> collect = skuIds.stream().map(sku -> {
            SkuHasstockVo skuHasstockVo = new SkuHasstockVo();
            // 查询当前sku的库存
            Long count = baseMapper.getSkuStock(sku);
            skuHasstockVo.setSkuId(sku);
            skuHasstockVo.setHasstock(count == null ? false : count > 0);
            return skuHasstockVo;
        }).collect(Collectors.toList());
        return collect;
    }

}