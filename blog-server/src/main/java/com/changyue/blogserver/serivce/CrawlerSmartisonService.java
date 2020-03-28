package com.changyue.blogserver.serivce;

/**
 * @author : 袁阊越
 * @description :
 * @date : 2020-03-28 20:59
 */
public interface CrawlerSmartisonService {

    void saveCate(String url);

    void saveSite(String url);

    void saveArticle(String url);

    String parseArticle(String url);

}
