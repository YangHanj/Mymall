package iee.yh.Mymall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@MapperScan(value = "iee.yh.Mymall.product.dao")
@EnableDiscoveryClient
public class mymallProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(mymallProductApplication.class,args);
    }
}