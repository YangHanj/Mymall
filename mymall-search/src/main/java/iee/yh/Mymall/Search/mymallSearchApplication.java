package iee.yh.Mymall.Search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author yanghan
 * @date 2022/10/25
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient
public class mymallSearchApplication {
    public static void main(String[] args) {
        SpringApplication.run(mymallSearchApplication.class,args);
    }
}
