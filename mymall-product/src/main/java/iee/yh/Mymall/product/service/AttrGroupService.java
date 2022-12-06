package iee.yh.Mymall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import iee.yh.Mymall.product.vo.AttrGroupWithAttrsVo;
import iee.yh.Mymall.product.vo.SkuItemVo;
import iee.yh.common.utils.PageUtils;
import iee.yh.Mymall.product.entity.AttrGroupEntity;

import java.util.List;
import java.util.Map;

/**
 * 属性分组
 *
 * @author yanghan
 * @email yanghan1214@163.com
 * @date 2022-04-03 08:57:06
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPage(Map<String, Object> params, Long catelogid);

    List<AttrGroupWithAttrsVo> getAttrGorupWithAttrsByCatelogId(Long catelog_id);

    List<SkuItemVo.spuItemAttrGroupVo> getAttrGorupWithAttrsBySpuId(Long spuId,Long catalogId);
}

