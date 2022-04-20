package iee.yh.Mymall.ware;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan(value = "iee.yh.Mymall.ware.dao")
@EnableDiscoveryClient
@EnableTransactionManagement
public class mymallWareApplication {
    public static void main(String[] args) {
        SpringApplication.run(mymallWareApplication.class,args);
    }
}
