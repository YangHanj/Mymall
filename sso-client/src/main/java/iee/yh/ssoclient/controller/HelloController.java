package iee.yh.ssoclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yanghan
 * @date 2022/12/3
 */
@Controller
public class HelloController {


    @Value("${sso.server.url}")
    public String ssoserverUrl;


    /**
     * @param model
     * @param session
     * @param token
     * @return
     */
    @GetMapping("/hello2")
    public String Hello2Controller(Model model, HttpSession session,
                                   @RequestParam(value = "token",required = false) String token,
                                   HttpServletRequest request){
        if (!StringUtils.isEmpty(token)){
            // 登录成功
            // TODO 获取用户信息 存入session
            session.setAttribute("loginUser","yang");
            session.setMaxInactiveInterval(2*60);
        }
        // step 1 判断是否的登录
        Object loginUser = session.getAttribute("loginUser");
        if (loginUser == null){
            // step 2 跳转登录服务器
            String url = request.getRequestURL().toString();
            return "redirect:"+ssoserverUrl+"?redirect_url="+url;
        }else {
            List<String> emps = new ArrayList<>();
            emps.add("zhangsan");
            emps.add("lisi");
            model.addAttribute("emps",emps);
            return "list";
        }
    }
}
