package com.changyue.blogserver.ulits;

import com.changyue.blogserver.config.properties.BlogProperties;
import com.changyue.blogserver.model.elsatic.Article;
import com.changyue.blogserver.utils.EsQueryUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : 袁阊越
 * @description : es test
 * @date : 2020-03-23 10:58
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class EsTest {

    @Autowired
    private JestClient jestClient;

    @Test
    public void test() {
        Search search = new Search.Builder(EsQueryUtils.createQueryJsonByTitle("心情"))
                .addIndex(BlogProperties.ES_INDEX)
                .addType(BlogProperties.ES_TYPE).build();
        try {
            SearchResult execute = jestClient.execute(search);
            JsonArray jsonElements = execute.getJsonObject().get("hits").getAsJsonObject().get("hits").getAsJsonArray();

            List<Article> articles = new ArrayList<>();
            Gson gson = new Gson();

            jsonElements.forEach(jsonElement -> {
                JsonObject object = jsonElement.getAsJsonObject().get("_source").getAsJsonObject();
                Article article = gson.fromJson(object, Article.class);
                articles.add(article);
            });
            articles.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testJson() {
        System.out.println(EsQueryUtils.createQueryJsonByContent("晴天"));
        System.out.println(EsQueryUtils.createQueryJsonByTitle("晴天"));
    }

}
