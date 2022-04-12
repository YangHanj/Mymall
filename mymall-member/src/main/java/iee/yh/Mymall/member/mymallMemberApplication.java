package iee.yh.Mymall.member;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@MapperScan(value = "iee.yh.Mymall.member.dao")
@EnableDiscoveryClient
public class mymallMemberApplication {
    public static void main(String[] args) {
        SpringApplication.run(mymallMemberApplication.class,args);
    }
}
