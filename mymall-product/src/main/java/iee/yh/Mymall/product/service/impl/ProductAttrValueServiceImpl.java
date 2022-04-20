package iee.yh.Mymall.product.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import iee.yh.common.utils.PageUtils;
import iee.yh.common.utils.Query;

import iee.yh.Mymall.product.dao.ProductAttrValueDao;
import iee.yh.Mymall.product.entity.ProductAttrValueEntity;
import iee.yh.Mymall.product.service.ProductAttrValueService;


@Service("productAttrValueService")
public class ProductAttrValueServiceImpl extends ServiceImpl<ProductAttrValueDao, ProductAttrValueEntity> implements ProductAttrValueService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductAttrValueEntity> page = this.page(
                new Query<ProductAttrValueEntity>().getPage(params),
                new QueryWrapper<ProductAttrValueEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveProductAttr(List<ProductAttrValueEntity> collect) {
        this.saveBatch(collect);
    }

    @Override
    public List<ProductAttrValueEntity> baseAttrListForSpu(Long spuId) {
        List<ProductAttrValueEntity> res = this.baseMapper.selectList(new QueryWrapper<ProductAttrValueEntity>()
                .eq("spu_id", spuId));
        return res;
    }

}