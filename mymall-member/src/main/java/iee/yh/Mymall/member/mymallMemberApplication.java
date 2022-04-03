package iee.yh.Mymall.member;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "iee.yh.Mymall.member.dao")
public class mymallMemberApplication {
    public static void main(String[] args) {
        SpringApplication.run(mymallMemberApplication.class,args);
    }
}
