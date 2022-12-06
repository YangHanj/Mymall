package iee.yh.Mymall.Auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author yanghan
 * @date 2022/11/28
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/reg.html").setViewName("reg");
        registry.addViewController("/login.html").setViewName("login");
    }
}
