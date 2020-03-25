package com.changyue.blogserver.serivce;

import com.changyue.blogserver.model.entity.CrawlerPost;

import java.util.List;

/**
 * @author : 袁阊越
 * @description : 爬虫文章的业务接口层
 * @date : 2020/3/25
 */
public interface CrawlerPostService {

    /**
     * 保存
     *
     * @param crawlerPost 爬虫的文章
     */
    void savePost(CrawlerPost crawlerPost);

    /**
     * 更新
     *
     * @param crawlerPost 爬虫的文章
     */
    void modifyPost(CrawlerPost crawlerPost);

    /**
     * 删除
     *
     * @param url 地址
     */
    void deleteByUrl(String url);

    /**
     * 查询
     *
     * @param crawlerPost 爬虫的文章
     * @return 爬虫的文章列表
     */
    List<CrawlerPost> getList(CrawlerPost crawlerPost);

}
