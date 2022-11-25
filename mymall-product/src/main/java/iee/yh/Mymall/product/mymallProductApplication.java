package iee.yh.Mymall.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan(value = "iee.yh.Mymall.product.dao")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "iee.yh.Mymall.product.feign")
@EnableCaching
@EnableAspectJAutoProxy
public class mymallProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(mymallProductApplication.class,args);
    }
}
