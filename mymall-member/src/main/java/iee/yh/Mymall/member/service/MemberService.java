package iee.yh.Mymall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import iee.yh.Mymall.member.vo.UserLoginVo;
import iee.yh.Mymall.member.vo.UserRegisterVo;
import iee.yh.common.utils.PageUtils;
import iee.yh.Mymall.member.entity.MemberEntity;

import java.util.Map;

/**
 * 会员
 *
 * @author yanghan
 * @email yanghan1214@163.com
 * @date 2022-04-03 09:25:14
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void register(UserRegisterVo registerVo);

    MemberEntity login(UserLoginVo userLoginVo);
}

