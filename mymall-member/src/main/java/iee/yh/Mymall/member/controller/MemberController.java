package iee.yh.Mymall.member.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import iee.yh.Mymall.member.vo.UserLoginVo;
import iee.yh.Mymall.member.vo.UserRegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import iee.yh.Mymall.member.entity.MemberEntity;
import iee.yh.Mymall.member.service.MemberService;
import iee.yh.common.utils.PageUtils;
import iee.yh.common.utils.R;



/**
 * 会员
 *
 * @author yanghan
 * @email yanghan1214@163.com
 * @date 2022-04-03 09:25:14
 */
@RestController
@RequestMapping("member/member")
public class MemberController {
    @Autowired
    private MemberService memberService;


    @PostMapping("/register")
    public R register(@RequestBody UserRegisterVo registerVo){
        memberService.register(registerVo);
        return R.ok();
    }


    @PostMapping("/login")
    public R login(@RequestBody UserLoginVo userLoginVo){
        MemberEntity member = memberService.login(userLoginVo);
        if (member == null)
            return R.error();
        else
            return R.ok();
    }
}
