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

    /**
     * 保存分类
     */
    void saveCate(String url);

    /**
     * 保存站点
     */
    void saveSite(String url);

    /**
     * 保存文章
     */
    void saveArticle(String url);

    /**
     * 解析文章
     *
     * @param url 地址
     * @return 文章Str
     */
    String parseArticle(String url);

    /**
     * 通过站点查询文章
     *
     * @param pageIndex 页索引
     * @param pageSize  页数
     * @param siteId    站点ID
     * @return 分页文章列表
     */
    PageInfo<CrawlerSmartisonPost> getPostList(@Nonnull Integer pageIndex, @Nonnull Integer pageSize, @Nonnull Integer siteId);

    /**
     * 随机生成文章 推荐
     *
     * @return 文章列表
     */
    List<CrawlerSmartisonPost> randomList();

    /**
     * 通过id获得简单的文章（不包含文章内容）
     *
     * @param id id
     * @return 文章
     */
    CrawlerSmartisonPost getSimplyPost(Integer id);

    /**
     * 通过name获得简单的文章（不包含文章内容）
     *
     * @param postName 文章名字
     * @return 文章
     */
    List<CrawlerSmartisonPost> getSimplyPost(String postName);


}
