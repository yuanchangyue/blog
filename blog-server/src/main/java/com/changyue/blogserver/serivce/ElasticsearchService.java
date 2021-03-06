package com.changyue.blogserver.serivce;

import com.changyue.blogserver.model.elsatic.Article;
import com.changyue.blogserver.model.entity.Post;
import com.changyue.blogserver.model.enums.ElasticsearchStatus;
import com.changyue.blogserver.model.rep.Result;
import io.searchbox.core.DocumentResult;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * @author : 袁阊越
 * @description : es 业务接口
 * @date : 2020-03-22 19:11
 */
public interface ElasticsearchService {

    /**
     * 新增文章
     *
     * @param post                文章内容
     * @param elasticsearchStatus 标示状态
     * @return 结果
     */
    DocumentResult indexArticle(@Nonnull Post post, @Nonnull ElasticsearchStatus elasticsearchStatus);

    /**
     * 全文检索
     *
     * @param fullTextQuery 查询
     * @return 结果
     */
    List<Article> searchArticle(@Nonnull String fullTextQuery);

    /**
     * 移除文章
     *
     * @param id 文章id
     * @return 结果
     */
    Result removeArticle(@Nonnull String id);

    /**
     * 修改文章
     *
     * @param post                文章内容
     * @param elasticsearchStatus 标示状态
     * @return 结果
     */
    Result modifyArticle(@Nonnull Post post,@Nonnull String documentId, @Nonnull ElasticsearchStatus elasticsearchStatus);

}
