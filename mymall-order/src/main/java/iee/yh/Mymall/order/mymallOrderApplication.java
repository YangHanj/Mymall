package iee.yh.Mymall.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "iee.yh.Mymall.order.dao")
public class mymallOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(mymallOrderApplication.class,args);
    }
}
