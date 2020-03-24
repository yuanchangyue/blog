package com.changyue.blogserver.service;

import com.changyue.blogserver.model.rep.Result;
import com.changyue.blogserver.model.params.FullTextQuery;
import com.changyue.blogserver.serivce.ElasticsearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author : 袁阊越
 * @description : es测试
 * @date : 2020-03-23 16:58
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticsearchServiceTest {

    @Autowired
    private ElasticsearchService elasticsearchService;

    @Test
    public void testSearchArticle() {
        FullTextQuery fullTextQuery = new FullTextQuery();
        fullTextQuery.setTitle("心情");
        Result result = elasticsearchService.searchArticle(fullTextQuery);
        System.out.println(result.getData());
    }

}
