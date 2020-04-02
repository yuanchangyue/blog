package com.changyue.blogserver.mapper;

import com.changyue.blogserver.dao.CrawlerPostSiteMapper;
import com.changyue.blogserver.dao.CrawlerSmartisonPostMapper;
import com.changyue.blogserver.model.entity.CrawlerSmartisonPost;
import com.changyue.blogserver.model.params.SiteQuery;
import com.changyue.blogserver.model.vo.SiteVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author : 袁阊越
 * @description :
 * @date : 2020-03-30 21:52
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CrawlerSitePostMapperTest {
    @Autowired
    private CrawlerPostSiteMapper crawlerPostSiteMapper;

    @Autowired
    private CrawlerSmartisonPostMapper crawlerSmartisonPostMapper;

    @Test
    public void test() {
        SiteQuery siteQuery = new SiteQuery();
        siteQuery.setKeyWords("s");
        List<SiteVO> siteVOS = crawlerPostSiteMapper.listByAll(siteQuery);
        siteVOS.forEach(System.out::println);
    }

    @Test
    public void test2() {
        List<CrawlerSmartisonPost> postBySite = crawlerSmartisonPostMapper.findPostBySite(1842);
        postBySite.forEach(System.out::println);
    }

}
