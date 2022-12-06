package iee.yh.ssoserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author yanghan
 * @date 2022/12/3
 */
@Controller
public class loginController {

    @RequestMapping("/login.html")
    public String login(@RequestParam("redirect_url") String url, Model model,
                        @CookieValue(value = "sso_token",required = false) String cookie){

        if (!StringUtils.isEmpty(cookie))
            return "redirect:"+url+"?token="+cookie;
        model.addAttribute("redirect_url",url);
        return "login";
    }

    @Autowired
    StringRedisTemplate redisTemplate;


    @PostMapping("/dologin")
    public String dologin(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          @RequestParam("url") String url,
                          HttpServletResponse response,
                          Model model){
        // TODO 检查用户名和密码
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            model.addAttribute("msg","登录失败，重新登录!");
            model.addAttribute("redirect_url",url);
            return "login";
        }
        // 密码账号成功 存入redis
        String uuid = UUID.randomUUID().toString().replace("-","");
        redisTemplate.opsForValue().set("uuid",username,1, TimeUnit.MINUTES);
        // 给浏览器存入cookie 表示用户已经登录(保存在 登录服务器 域名下)
        Cookie sso_token = new Cookie("sso_token", uuid);
        sso_token.setMaxAge(2*60);
        response.addCookie(sso_token);
        // 登录成功跳转
        return "redirect:"+url+"?token="+uuid;
    }
}
