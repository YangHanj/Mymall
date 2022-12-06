package iee.yh.Mymall.Auth.controller;

import iee.yh.Mymall.Auth.feign.MemberFeignService;
import iee.yh.Mymall.Auth.vo.UserLoginVo;
import iee.yh.Mymall.Auth.vo.UserRegisterVo;
import iee.yh.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author yanghan
 * @date 2022/11/28
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    MemberFeignService memberFeignService;

    final String SMS_CODE_PREFIX = "sms:code:";

    @PostMapping("/register")
    public String register(@Valid UserRegisterVo registerVo, BindingResult result, RedirectAttributes attributes){
        Map<String,String> map = null;
        if (result.hasErrors()){
            map = result.getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(FieldError::getField,FieldError::getDefaultMessage));
            attributes.addFlashAttribute("errors",map);
            // 校验出错
            return "redirect:http://auth.mymall.com/reg.html";
        }
        // 真正注册，远程调用
        // step 1 验证码校验
        String code = registerVo.getCode();
        String value = redisTemplate.opsForValue().get(SMS_CODE_PREFIX + registerVo.getMail());
        if ( !StringUtils.isEmpty(value) && code.equals(value.substring(0,5))){
            // 验证码通过,进行注册
            redisTemplate.delete(SMS_CODE_PREFIX + registerVo);
            R registerres = memberFeignService.register(registerVo);
            if (registerres.getCode() == 0){
                // success
                return "redirect:/login.html";
            }else {
                // error
                return "redirect:http://auth.mymall.com/reg.html";
            }
        }else {
            map.put("code","验证码错误");
            attributes.addFlashAttribute("errors",map);
            // 校验出错
            return "redirect:http://auth.mymall.com/reg.html";
        }
    }

    @PostMapping("/login")
    public String login(UserLoginVo userLoginVo){
        R login = memberFeignService.login(userLoginVo);
        if (login.getCode() == 0)
            return "redirect:http://mymall.com";
        else
            return "redirect:http://auth.mymall.com";
    }
}
