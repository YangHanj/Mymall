package iee.yh.Mymall.Auth.controller;

import iee.yh.Mymall.Auth.util.EmailTask;
import iee.yh.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author yanghan
 * @date 2022/11/28
 */
@RestController
@RequestMapping("/sms")
public class SmsSenfController {

    @Resource
    EmailTask emailTask;

    @Autowired
    StringRedisTemplate redisTemplate;

    final String SMS_CODE_PREFIX = "sms:code:";

    @PostMapping("/sendcode")
    public R sendCode(@RequestParam("mail")String mail){
        String redisCode = redisTemplate.opsForValue().get(SMS_CODE_PREFIX + mail);
        if (!StringUtils.isEmpty(redisCode)){
            // 防止重复提交
            Long time = Long.parseLong(redisCode.split("_")[1]);
            // 2分钟之后不能再次发送验证码
            if (System.currentTimeMillis() - time <= 2 * 60000l ){
                return R.error("重复请求邮件验证码");
            }
        }
        String code = UUID.randomUUID().toString().substring(0, 5);
        redisTemplate.opsForValue().set (
                SMS_CODE_PREFIX + mail ,
                code + "_" + System.currentTimeMillis(),
                10,
                TimeUnit.MINUTES);
        emailTask.sendMail(mail,code);
        return R.ok();
    }
}
