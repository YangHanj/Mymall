package iee.yh.Mymall.coupon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "iee.yh.Mymall.coupon.dao")
public class mymallCouponApplication {
    public static void main(String[] args) {
        SpringApplication.run(mymallCouponApplication.class,args);
    }
}
