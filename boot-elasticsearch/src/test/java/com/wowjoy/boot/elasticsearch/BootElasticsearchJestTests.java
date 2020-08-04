package com.wowjoy.boot.elasticsearch;

import com.wowjoy.boot.elasticsearch.bean.Article;
import io.searchbox.action.Action;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class BootElasticsearchJestTests {

    @Autowired
    private JestClient jestClient;

    @Test
    public void testJest() throws IOException {
        /**
         * 在ES中索引（保存）一个文档：
         */
        Article article = new Article()
                .setId(1)
                .setTitle("最新消息")
                .setContent("今天天气会比较冷，记得多穿点衣服，以防感冒~ 2018年12月6日10:19:19")
                .setAuthor("记者");


        /**
         * 创建一个索引
         */
        Index index = new Index.Builder(article)
                .index("ar1")
                .type("news")
                .id("1")
                .build();

        // 执行
        jestClient.execute(index);
    }

    /**
     * 测试搜索
     */
    @Test
    public void testJestSearch() throws IOException {
        String searchJson = "{\n" +
                "    \"query\" : {\n" +
                "        \"match\" : {\n" +
                "            \"content\" : \"比较冷\"\n" +
                "        }\n" +
                "    }\n" +
                "}";

        Search search = new Search.Builder(searchJson).addIndex("ar1").addType("news").build();
        SearchResult result = jestClient.execute(search);
        log.info("搜索结果：{}", result.getJsonString());
    }

}
