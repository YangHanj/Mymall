package iee.yh.Mymall.Auth.config;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * @author yanghan
 * @date 2022/12/2
 */
@Configuration
public class SessionConfig {

    @Bean
    public CookieSerializer cookieSerializer(){
        DefaultCookieSerializer defaultCookieSerializer = new DefaultCookieSerializer();
        defaultCookieSerializer.setDomainName("mymall.com");
        defaultCookieSerializer.setCookieName("mymallsession");
        return defaultCookieSerializer;
    }

    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer(){
        return new GenericFastJsonRedisSerializer();
    }
}
