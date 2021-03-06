package iee.yh.Mymall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import iee.yh.Mymall.product.dao.AttrAttrgroupRelationDao;
import iee.yh.Mymall.product.dao.AttrGroupDao;
import iee.yh.Mymall.product.dao.CategoryDao;
import iee.yh.Mymall.product.entity.AttrAttrgroupRelationEntity;
import iee.yh.Mymall.product.entity.AttrGroupEntity;
import iee.yh.Mymall.product.entity.CategoryEntity;
import iee.yh.Mymall.product.service.CategoryService;
import iee.yh.Mymall.product.vo.AttrGroupRelationVo;
import iee.yh.Mymall.product.vo.AttrResponseVo;
import iee.yh.Mymall.product.vo.AttrVo;
import iee.yh.common.constant.ProductConstant;
import iee.yh.common.utils.R;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import iee.yh.common.utils.PageUtils;
import iee.yh.common.utils.Query;

import iee.yh.Mymall.product.dao.AttrDao;
import iee.yh.Mymall.product.entity.AttrEntity;
import iee.yh.Mymall.product.service.AttrService;
import org.springframework.transaction.annotation.Transactional;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Autowired
    private AttrAttrgroupRelationDao relationDao;
    @Transactional
    @Override
    public void saveAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr,attrEntity);
        //??????????????????
        this.save(attrEntity);
        //??????????????????
        if (attr.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() && attr.getAttrGroupId() != null){
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            attrAttrgroupRelationEntity.setAttrGroupId(attr.getAttrGroupId());
            attrAttrgroupRelationEntity.setAttrId(attrEntity.getAttrId());
            relationDao.insert(attrAttrgroupRelationEntity);
        }
    }

    @Autowired
    private AttrGroupDao attrGroupDao;
    @Autowired
    private CategoryDao categoryDao;
    @Override
    public PageUtils queryBasePage(Map<String, Object> params, Long catelogId,String attrtype) {
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper();
        queryWrapper.eq("attr_type",
                        "base".equalsIgnoreCase(attrtype)
                                ?
                                ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()
                                :
                                ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());
        if (catelogId != 0){
            //??????????????????????????????
            queryWrapper.eq("catelog_id",catelogId);
        }
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)){
            //????????????key ?????????key????????????
            queryWrapper.and((wrapper)->{
                wrapper.eq("attr_id",key).or().like("attr_name",key);
            });
        }
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                queryWrapper
        );
        PageUtils pageUtils = new PageUtils(page);
        List<AttrEntity> records = page.getRecords();
        List<AttrResponseVo> attr_id = records.stream().map(attrEntity -> {
            AttrResponseVo attrResponseVo = new AttrResponseVo();
            BeanUtils.copyProperties(attrEntity, attrResponseVo);
            if ("base".equalsIgnoreCase(attrtype)){
                //???id
                Long attrGroupId = relationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>()
                        .eq("attr_id", attrEntity.getAttrId())).getAttrGroupId();
                if (attrGroupId != null) {
                    AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrGroupId);
                    //??????????????????
                    attrResponseVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }
            }
            CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatelogId());
            if (categoryEntity != null) {
                //??????????????????
                attrResponseVo.setCatelogName(categoryEntity.getName());
            }
            return attrResponseVo;
        }).collect(Collectors.toList());
        pageUtils.setList(attr_id);
        return pageUtils;
    }

    @Autowired
    private CategoryService categoryService;
    @Override
    public AttrResponseVo getAttrInfo(Long attrId) {
        AttrEntity attrEntity = this.getById(attrId);
        AttrResponseVo attrResponseVo = new AttrResponseVo();
        BeanUtils.copyProperties(attrEntity,attrResponseVo);

        if (attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()){
            //????????????
            AttrAttrgroupRelationEntity attrgroupRelation = relationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrId));
            if (attrgroupRelation != null){
                attrResponseVo.setAttrGroupId(attrgroupRelation.getAttrGroupId());
                AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrgroupRelation.getAttrGroupId());
                if (attrGroupEntity != null)
                    attrResponseVo.setGroupName(attrGroupEntity.getAttrGroupName());
            }
        }
        //????????????
        Long catelogId = attrEntity.getCatelogId();
        Long[] catelogPath = categoryService.findCatelogPath(catelogId);
        attrResponseVo.setCatelogPath(catelogPath);

        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
        if (categoryEntity != null)
            attrResponseVo.setCatelogName(categoryEntity.getName());
        return attrResponseVo;
    }

    @Transactional
    @Override
    public void updateAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr,attrEntity);
        this.updateById(attrEntity);
        if (attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()){
            Integer attr_id = relationDao.selectCount(new QueryWrapper<AttrAttrgroupRelationEntity>()
                    .eq("attr_id", attr.getAttrId()));
            AttrAttrgroupRelationEntity attrgroupRelation = new AttrAttrgroupRelationEntity();
            attrgroupRelation.setAttrGroupId(attr.getAttrGroupId());
            attrgroupRelation.setAttrId(attr.getAttrId());
            if (attr_id > 0){
                relationDao.update(attrgroupRelation,new UpdateWrapper<AttrAttrgroupRelationEntity>().eq("attr_id",attr.getAttrId()));
            }else
                relationDao.insert(attrgroupRelation);
        }
    }

    @Override
    public List<AttrEntity> getRelationAttr(Long id) {
        List<AttrAttrgroupRelationEntity> attrAttrgroupRelationEntityList = relationDao.selectList(
                new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_group_id", id));
        List<Long> ids = attrAttrgroupRelationEntityList.stream().map(info -> {
            return info.getAttrId();
        }).collect(Collectors.toList());
        if (ids != null && ids.size() > 0){
            List<AttrEntity> attrEntities = this.listByIds(ids);
            return attrEntities;
        }else return null;
    }

    @Override
    public void deleteRelation(AttrGroupRelationVo[] attrGroupRelationVo) {
        //??????????????????????????????????????????????????????
//        for (AttrGroupRelationVo groupRelationVo : attrGroupRelationVo) {
//            relationDao.delete(new QueryWrapper<AttrAttrgroupRelationEntity>()
//                    .eq("attr_id",groupRelationVo.getAttrId())
//                    .eq("attr_group_id",groupRelationVo.getAttrGroupId()));
//        }
        List<AttrAttrgroupRelationEntity> attrAttrgroupRelationEntities = Arrays.asList(attrGroupRelationVo).stream().map(info -> {
            AttrAttrgroupRelationEntity attrAttrgroupRelationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(info, attrAttrgroupRelationEntity);
            return attrAttrgroupRelationEntity;
        }).collect(Collectors.toList());
        if (attrAttrgroupRelationEntities != null)
            relationDao.deleteBatchRelation(attrAttrgroupRelationEntities);
    }

    /**
     * ???????????????????????????????????????
     * @param params
     * @param id
     * @return
     */
    @Override
    public PageUtils getNoRelationAttr(Map<String, Object> params, Long id) {
        /**
         * TODO
         * 1????????????????????????????????????
         * 2????????????????????????????????????
         * 3?????????????????????????????????????????????????????????????????????
         */
        //????????????????????????????????????????????????????????????
        AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(id);
        Long catelogId = attrGroupEntity.getCatelogId();
        //?????????????????????????????????????????????????????????
        //***?????????????????????????????????????????????????????????????????????
        List<AttrGroupEntity> groupEntities = attrGroupDao.selectList(new QueryWrapper<AttrGroupEntity>()
                                                .eq("catelog_id", catelogId));
        //***???????????????????????????
        List<Long> groupIdLists = groupEntities.stream().map(info -> {
            return info.getAttrGroupId();
        }).collect(Collectors.toList());
        List<AttrAttrgroupRelationEntity> attrAttrgroupRelationEntityList = relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>()
                                                                            .in("attr_group_id", groupIdLists));
        //***?????????????????????????????????????????????id
        List<Long> attrIdLists = attrAttrgroupRelationEntityList.stream().map(info -> {
            return info.getAttrId();
        }).collect(Collectors.toList());
        String key = (String) params.get("key");
        IPage<AttrEntity> iPage = null;
        QueryWrapper<AttrEntity> wapper = new QueryWrapper<AttrEntity>()
                                                .eq("catelog_id", catelogId)
                                                .eq("attr_type",ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode());
        if (attrIdLists != null && attrIdLists.size() > 0)
            //????????????????????????????????????????????????id
            wapper.notIn("attr_id", attrIdLists);
        if (!StringUtils.isEmpty(key)){
            iPage = this.page(new Query<AttrEntity>().getPage(params),
                            wapper.and(w->{
                                    w.eq("attr_id",key).or().like("attr_name",key);
                                })
            );
        }else
            iPage = this.page(new Query<AttrEntity>().getPage(params),wapper);
        return new PageUtils(iPage);
    }

}