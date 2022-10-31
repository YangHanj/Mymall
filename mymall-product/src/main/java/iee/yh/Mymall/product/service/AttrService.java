package iee.yh.Mymall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import iee.yh.Mymall.product.vo.AttrGroupRelationVo;
import iee.yh.Mymall.product.vo.AttrResponseVo;
import iee.yh.Mymall.product.vo.AttrVo;
import iee.yh.common.utils.PageUtils;
import iee.yh.Mymall.product.entity.AttrEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author yanghan
 * @email yanghan1214@163.com
 * @date 2022-04-03 08:57:06
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttr(AttrVo attr);

    PageUtils queryBasePage(Map<String, Object> params, Long catelogId,String attrtype);

    AttrResponseVo getAttrInfo(Long attrId);

    void updateAttr(AttrVo attr);

    List<AttrEntity> getRelationAttr(Long id);

    void deleteRelation(AttrGroupRelationVo[] attrGroupRelationVo);

    PageUtils getNoRelationAttr(Map<String, Object> params, Long id);

    List<Long> selectSearchAttrsIds(List<Long> attrIds);
}

