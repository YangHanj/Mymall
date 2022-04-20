package iee.yh.Mymall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import iee.yh.common.utils.PageUtils;
import iee.yh.Mymall.ware.entity.WareInfoEntity;

import java.util.Map;

/**
 * 仓库信息
 *
 * @author yanghan
 * @email yanghan1214@163.com
 * @date 2022-04-03 09:36:22
 */
public interface WareInfoService extends IService<WareInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageByparams(Map<String, Object> params);
}

