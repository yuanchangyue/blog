package com.changyue.blogserver.serivce;

import com.changyue.blogserver.handler.Result;
import com.changyue.blogserver.model.entity.Post;
import com.changyue.blogserver.model.enums.ElasticsearchStatus;
import com.changyue.blogserver.model.params.HomeQuery;

import javax.annotation.Nonnull;

/**
 * @author : 袁阊越
 * @description : es 业务接口
 * @date : 2020-03-22 19:11
 */
public interface ElasticsearchService {

    /**
     * 文章
     *
     * @param post 文章内容
     * @return 结果
     */
    @Nonnull
    Result indexArticle(@Nonnull Post post, @Nonnull ElasticsearchStatus elasticsearchStatus);

    /**
     * 全文检索
     *
     * @param homeQuery 查询
     * @return 结果
     */
    @Nonnull
    Result searchArticle(@Nonnull HomeQuery homeQuery);

    /**
     * 移除文章
     *
     * @param id 文章id
     * @return 结果
     */
    Result removeArticle(@Nonnull String id);

}
