package com.changyue.blogserver.serivce;

import com.changyue.blogserver.model.entity.CrawlerCsdnPost;

import java.util.List;

/**
 * @author : 袁阊越
 * @description : 爬虫文章的业务接口层
 * @date : 2020/3/25
 */
public interface CrawlerCsdnPostService {

    /**
     * 保存
     *
     * @param crawlerCsdnPost 爬虫的文章
     */
    void savePost(CrawlerCsdnPost crawlerCsdnPost);

    /**
     * 更新
     *
     * @param crawlerCsdnPost 爬虫的文章
     */
    void modifyPost(CrawlerCsdnPost crawlerCsdnPost);

    /**
     * 删除
     *
     * @param url 地址
     */
    void deleteByUrl(String url);

    /**
     * 查询
     *
     * @param crawlerCsdnPost 爬虫的文章
     * @return 爬虫的文章列表
     */
    List<CrawlerCsdnPost> getList(CrawlerCsdnPost crawlerCsdnPost);

}
