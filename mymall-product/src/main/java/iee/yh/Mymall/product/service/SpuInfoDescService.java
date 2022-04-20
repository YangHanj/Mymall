package iee.yh.Mymall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import iee.yh.common.utils.PageUtils;
import iee.yh.Mymall.product.entity.SpuInfoDescEntity;

import java.util.List;
import java.util.Map;

/**
 * spu信息介绍
 *
 * @author yanghan
 * @email yanghan1214@163.com
 * @date 2022-04-02 17:37:04
 */
public interface SpuInfoDescService extends IService<SpuInfoDescEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSpuInfoDesc(SpuInfoDescEntity spuInfoDescEntity);
}

