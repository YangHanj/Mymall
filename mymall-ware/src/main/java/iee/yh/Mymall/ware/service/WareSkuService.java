package iee.yh.Mymall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import iee.yh.Mymall.ware.vo.SkuHasstockVo;
import iee.yh.common.utils.PageUtils;
import iee.yh.Mymall.ware.entity.WareSkuEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品库存
 *
 * @author yanghan
 * @email yanghan1214@163.com
 * @date 2022-04-03 09:36:22
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageByParams(Map<String, Object> params);

    List<SkuHasstockVo> getSKuHasStock(List<Long> skuIds);
}

