package iee.yh.Mymall.coupon.dao;

import iee.yh.Mymall.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author yanghan
 * @email yanghan1214@163.com
 * @date 2022-04-03 09:12:46
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
