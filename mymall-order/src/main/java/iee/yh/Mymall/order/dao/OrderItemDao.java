package iee.yh.Mymall.order.dao;

import iee.yh.Mymall.order.entity.OrderItemEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单项信息
 * 
 * @author yanghan
 * @email yanghan1214@163.com
 * @date 2022-04-03 09:32:44
 */
@Mapper
public interface OrderItemDao extends BaseMapper<OrderItemEntity> {
	
}
