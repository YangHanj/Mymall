package iee.yh.Mymall.ThirdParty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author yanghan
 * @date 2022/4/7
 */
@SpringBootApplication
@EnableDiscoveryClient
public class mymallThirdPartyApplication {
    public static void main(String[] args) {
        SpringApplication.run(mymallThirdPartyApplication.class,args);
    }
}
