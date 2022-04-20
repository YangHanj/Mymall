package iee.yh.Mymall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import iee.yh.common.utils.PageUtils;
import iee.yh.Mymall.product.entity.SpuImagesEntity;

import java.util.List;
import java.util.Map;

/**
 * spu图片
 *
 * @author yanghan
 * @email yanghan1214@163.com
 * @date 2022-04-02 17:37:04
 */
public interface SpuImagesService extends IService<SpuImagesEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveImages(Long id, List<String> images);
}

