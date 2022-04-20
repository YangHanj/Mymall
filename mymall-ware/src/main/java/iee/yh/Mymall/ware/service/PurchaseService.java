package iee.yh.Mymall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import iee.yh.Mymall.ware.vo.MergeVo;
import iee.yh.common.utils.PageUtils;
import iee.yh.Mymall.ware.entity.PurchaseEntity;

import java.util.List;
import java.util.Map;

/**
 * 采购信息
 *
 * @author yanghan
 * @email yanghan1214@163.com
 * @date 2022-04-03 09:36:22
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageUnreceive();

    void mergePurchase(MergeVo mergeVo);

    void received(List<Long> ids,String username);
}

