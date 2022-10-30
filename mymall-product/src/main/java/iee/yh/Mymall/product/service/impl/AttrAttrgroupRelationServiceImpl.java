package iee.yh.Mymall.product.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import iee.yh.common.utils.PageUtils;
import iee.yh.common.utils.Query;
import iee.yh.Mymall.product.dao.AttrAttrgroupRelationDao;
import iee.yh.Mymall.product.entity.AttrAttrgroupRelationEntity;
import iee.yh.Mymall.product.vo.AttrGroupRelationVo;
import iee.yh.Mymall.product.service.AttrAttrgroupRelationService;


@Service("attrAttrgroupRelationService")
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationDao, AttrAttrgroupRelationEntity> implements AttrAttrgroupRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrAttrgroupRelationEntity> page = this.page(
                new Query<AttrAttrgroupRelationEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveBatch(List<AttrGroupRelationVo> relationVos) {
        List<AttrAttrgroupRelationEntity> collect = relationVos.stream().map(info -> {
            AttrAttrgroupRelationEntity attrgroupRelation = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(info, attrgroupRelation);
            return attrgroupRelation;
        }).collect(Collectors.toList());
        super.saveBatch(collect);

    }

}