package iee.yh.test;

import com.alibaba.fastjson.JSON;
import iee.yh.Mymall.Search.config.ElasticConfig;
import iee.yh.Mymall.Search.mymallSearchApplication;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author yanghan
 * @date 2022/10/25
 */
@SpringBootTest(classes = mymallSearchApplication.class)
@RunWith(SpringRunner.class)
public class SearchTest {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Test
    public void contextLoads(){
        System.out.println(restHighLevelClient);
    }

    @Test
    public void indexData(){

        IndexRequest indexRequest = new IndexRequest("users");
        indexRequest.id("1");
        // indexRequest.source("username","zhangsan","age",18,"gender","男");
        User user = new User("zhangsan","男",18);
        String string = JSON.toJSONString(user);
        indexRequest.source(string, XContentType.JSON);
        indexRequest.timeout(new TimeValue(1,TimeUnit.SECONDS));

//        restHighLevelClient.indexAsync(indexRequest, ElasticConfig.COMMON_OPTIONS, new ActionListener<IndexResponse>() {
//            @Override
//            public void onResponse(IndexResponse indexResponse) {
//                System.out.println("成功");
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//                System.out.println("失败");
//            }
//        });
        IndexResponse index = null;
        try {
            index = restHighLevelClient.index(indexRequest, ElasticConfig.COMMON_OPTIONS);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(index);
    }

    @Test
    public void searchTest(){
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("users"); // 需要参与检索的索引
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("age",18));
        searchRequest.source(searchSourceBuilder); // 检索条件

        SearchResponse search = null;
        try {
            search = restHighLevelClient.search(searchRequest, ElasticConfig.COMMON_OPTIONS);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(search);
    }

    class User{
        private String username;
        private String gender;
        private Integer age;

        public User(String username, String gender, Integer age) {
            this.username = username;
            this.gender = gender;
            this.age = age;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }
}
