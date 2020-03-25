package com.changyue.blogserver.serivce;

import javax.annotation.Nonnull;

/**
 * @author : 袁阊越
 * @description : 爬虫文章标签业务接口
 * @date : 2020-03-25 12:43
 */
public interface CrawlerPostLabelService {

    /**
     * 通过label查询ids
     *
     * @param labels 标签，多个以逗号分割
     * @return id，多个以逗号分割
     */
    String getPostLabelIdByLabelName(@Nonnull String labels);


    /**
     * 通过label查询postChannel
     *
     * @param labels id，多个以逗号分割
     * @return postChannel  返回0 不存在
     */
    Integer getPostChannelByLabelIds(@Nonnull String labels);

}
