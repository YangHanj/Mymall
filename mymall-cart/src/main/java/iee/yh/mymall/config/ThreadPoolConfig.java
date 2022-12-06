package iee.yh.mymall.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yanghan
 * @date 2022/11/27
 */
@Configuration
public class ThreadPoolConfig {
    @Bean
    public ThreadPoolExecutor getThreadPoolExecutor(){
        return new ThreadPoolExecutor(16,
                16,
                10,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(1000),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
    }
}
