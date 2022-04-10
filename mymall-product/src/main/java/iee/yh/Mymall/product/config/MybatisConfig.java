package iee.yh.Mymall.product.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author yanghan
 * @date 2022/4/9
 */
@Configuration
@EnableTransactionManagement
@MapperScan("iee.yh.Mymall.product.dao")
public class MybatisConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setOverflow(false);
        paginationInterceptor.setLimit(500);//单页最大
        return paginationInterceptor;
    }
}
