package iee.yh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author yanghan
 * @date 2022/11/27
 */
@SpringBootApplication
@EnableDiscoveryClient
public class mymallAuthServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(mymallAuthServerApplication.class);
    }
}
