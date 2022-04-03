package iee.yh.Mymall.ware;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "iee.yh.Mymall.ware.dao")
public class mymallWareApplication {
    public static void main(String[] args) {
        SpringApplication.run(mymallWareApplication.class,args);
    }
}
