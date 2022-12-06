package iee.yh.mymall.interceptor;

import iee.yh.mymall.config.MyThradLocal;
import iee.yh.mymall.vo.UserInfoTo;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * 在执行目标方法之前判断用户登录状态
 * @author yanghan
 * @date 2022/12/4
 */
@Component
public class CartInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        HttpSession session = request.getSession();
        UserInfoTo userInfoTo = new UserInfoTo();
        Object loginUser = session.getAttribute("loginUser");
        if (loginUser != null){
            // 登录
            // TODO xxx
            userInfoTo.setUserId(10001l);
        }
        for (Cookie cookie : request.getCookies()) {
            String name = cookie.getName();
            if (name.equals("user-key")){
                userInfoTo.setUserKey(cookie.getValue());
                userInfoTo.setTempUser(true);
            }
        }
        if (StringUtils.isEmpty(userInfoTo.getUserKey())){
            String uuid = UUID.randomUUID().toString();
            userInfoTo.setUserKey(uuid);
        }
        MyThradLocal.getInstance().set(userInfoTo);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        UserInfoTo userInfoTo = (UserInfoTo) MyThradLocal.getInstance().get();
        if (userInfoTo.getTempUser())
            return;
        Cookie cookie = new Cookie("user-key", userInfoTo.getUserKey());
        cookie.setDomain("mymall.com");
        cookie.setMaxAge(30 * 24 * 60 * 60);
        response.addCookie(cookie);
    }
}
