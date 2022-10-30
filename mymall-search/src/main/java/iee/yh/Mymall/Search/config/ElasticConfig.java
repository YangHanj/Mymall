package iee.yh.Mymall.Search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yanghan
 * @date 2022/10/25
 */
@Configuration
public class ElasticConfig {


    @Bean
    public RestHighLevelClient getRestHighLevelClient(){
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("43.138.71.234", 9200, "http")
                )
        );
        return restHighLevelClient;
    }

    public static final RequestOptions COMMON_OPTIONS;

    static {
        COMMON_OPTIONS = RequestOptions.DEFAULT.toBuilder().build();
    }

}
