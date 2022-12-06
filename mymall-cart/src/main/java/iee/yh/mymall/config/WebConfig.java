package iee.yh.mymall.config;

import iee.yh.mymall.interceptor.CartInterceptor;
import iee.yh.mymall.vo.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author yanghan
 * @date 2022/12/4
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    CartInterceptor cartInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(cartInterceptor).addPathPatterns("/cart/*");
    }
}
