package iee.yh.Mymall.order.dao;

import iee.yh.Mymall.order.entity.OrderSettingEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单配置信息
 * 
 * @author yanghan
 * @email yanghan1214@163.com
 * @date 2022-04-03 09:32:44
 */
@Mapper
public interface OrderSettingDao extends BaseMapper<OrderSettingEntity> {
	
}
