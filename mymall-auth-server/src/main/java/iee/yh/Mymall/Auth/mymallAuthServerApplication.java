package iee.yh.Mymall.Auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;

/**
 * @author yanghan
 * @date 2022/11/27
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "iee.yh.Mymall.Auth.feign")
@EnableSpringHttpSession
public class mymallAuthServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(mymallAuthServerApplication.class,args);
    }
}
