package iee.yh.Mymall.member.dao;

import iee.yh.Mymall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author yanghan
 * @email yanghan1214@163.com
 * @date 2022-04-03 09:25:14
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
