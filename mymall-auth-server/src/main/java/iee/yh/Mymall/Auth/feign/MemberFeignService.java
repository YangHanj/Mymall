package iee.yh.Mymall.Auth.feign;

import iee.yh.Mymall.Auth.vo.UserLoginVo;
import iee.yh.Mymall.Auth.vo.UserRegisterVo;
import iee.yh.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author yanghan
 * @date 2022/11/29
 */
@FeignClient("member")
public interface MemberFeignService {
    @PostMapping("member/member/register")
    R register(@RequestBody UserRegisterVo registerVo);

    @PostMapping("/login")
    R login(@RequestBody UserLoginVo userLoginVo);
}
