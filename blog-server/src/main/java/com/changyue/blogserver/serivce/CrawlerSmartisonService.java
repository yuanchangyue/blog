package com.changyue.blogserver.serivce;

import com.changyue.blogserver.model.entity.CrawlerSmartisonPost;
import com.changyue.blogserver.serivce.base.BaseService;
import com.github.pagehelper.PageInfo;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author : 袁阊越
 * @description : smartison 业务接口层
 * @date : 2020-03-28 20:59
 */
public interface CrawlerSmartisonService extends BaseService<CrawlerSmartisonPost, Integer> {

    void saveCate(String url);

    void saveSite(String url);

    void saveArticle(String url);

    String parseArticle(String url);

    PageInfo<CrawlerSmartisonPost> getPostList(@Nonnull Integer pageIndex, @Nonnull Integer pageSize, @Nonnull Integer siteId);

    /**
     * 随机生成文章 推荐
     *
     * @return 文章列表
     */
    List<CrawlerSmartisonPost> randomList();


}
