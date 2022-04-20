package iee.yh.Mymall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import iee.yh.common.utils.PageUtils;
import iee.yh.Mymall.ware.entity.PurchaseDetailEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author yanghan
 * @email yanghan1214@163.com
 * @date 2022-04-03 09:36:22
 */
public interface PurchaseDetailService extends IService<PurchaseDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageByParams(Map<String, Object> params);

    List<PurchaseDetailEntity> listDetailByPurchase(Long id);
}

