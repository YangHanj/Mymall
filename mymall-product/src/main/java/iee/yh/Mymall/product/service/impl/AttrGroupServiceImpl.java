package iee.yh.Mymall.product.service.impl;

import iee.yh.Mymall.product.entity.AttrEntity;
import iee.yh.Mymall.product.service.AttrService;
import iee.yh.Mymall.product.vo.AttrGroupWithAttrsVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
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

import iee.yh.Mymall.product.dao.AttrGroupDao;
import iee.yh.Mymall.product.entity.AttrGroupEntity;
import iee.yh.Mymall.product.service.AttrGroupService;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catelogid) {
        String key = (String) params.get("key");
        //select * from attrgroup where catelogid = ? and (attr_group_id = ? or attr_group_name like %key%)
        QueryWrapper<AttrGroupEntity> attrGroupEntityQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(key)){
            attrGroupEntityQueryWrapper.and(obj->{
                obj.eq("attr_group_id",key).or().like("attr_group_name",key);
            });
        }
        if (catelogid == 0){
            return new PageUtils(this.page(
                    new Query<AttrGroupEntity>().getPage(params),
                    attrGroupEntityQueryWrapper
            ));
        }else {
            attrGroupEntityQueryWrapper.eq("catelog_id",catelogid);
            IPage<AttrGroupEntity> page = this.page(new Query<AttrGroupEntity>().getPage(params),
                                                    attrGroupEntityQueryWrapper);
            return new PageUtils(page);
        }
    }

    @Autowired
    private AttrService attrService;
    @Override
    public List<AttrGroupWithAttrsVo> getAttrGorupWithAttrsByCatelogId(Long catelog_id) {
        //利用分类编号查询分组信息
        List<AttrGroupEntity> groupEntities = this.baseMapper.selectList(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelog_id));
        if (groupEntities == null || groupEntities.size() == 0)
            return null;
        //利用分组信息查询对应的属性
        List<AttrGroupWithAttrsVo> collect = groupEntities.stream().map(info -> {
            AttrGroupWithAttrsVo attrGroupWithAttrsVo = new AttrGroupWithAttrsVo();
            BeanUtils.copyProperties(groupEntities, attrGroupWithAttrsVo);
            List<AttrEntity> relationAttr = attrService.getRelationAttr(info.getAttrGroupId());
            attrGroupWithAttrsVo.setAttrs(relationAttr);
            return attrGroupWithAttrsVo;
        }).collect(Collectors.toList());
        return collect;
    }

}