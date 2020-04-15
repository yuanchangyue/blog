package com.changyue.blogserver.serivce.impl;

import com.changyue.blogserver.config.properties.BlogProperties;
import com.changyue.blogserver.exception.ElasticSearchException;
import com.changyue.blogserver.model.rep.Result;
import com.changyue.blogserver.model.elsatic.Article;
import com.changyue.blogserver.model.entity.Post;
import com.changyue.blogserver.model.enums.ElasticsearchStatus;
import com.changyue.blogserver.model.enums.ResultStatus;
import com.changyue.blogserver.model.params.FullTextQuery;
import com.changyue.blogserver.serivce.ElasticsearchService;
import com.changyue.blogserver.utils.EsQueryUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.searchbox.client.JestClient;
import io.searchbox.core.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : 袁阊越
 * @description : es 业务实现类
 * @date : 2020-03-22 19:15
 */
@Service
@Slf4j
public class ElasticsearchServiceImpl implements ElasticsearchService {

    @Autowired
    private JestClient jestClient;

    @Override
    public Result indexArticle(@Nonnull Post post, ElasticsearchStatus elasticsearchStatus) {

        //构建出文章
        Article article = new Article();
        BeanUtils.copyProperties(post, article);
        article.setTag(elasticsearchStatus.getCode());

        //索引
        Index index = new Index.Builder(article)
                .index(BlogProperties.ES_INDEX)
                .type(BlogProperties.ES_TYPE).build();

        DocumentResult result;
        try {
            result = jestClient.execute(index);
        } catch (IOException e) {
            log.debug("es 文章添加失败：[{}]", e.getMessage());
            throw new ElasticSearchException("es 文章添加失败");
        }

        log.info("es 文章[{}]添加成功", post.getTitle());
        return Result.create(ResultStatus.OPERATION_SUCCESS, result);
    }

    @Override
    public Result searchArticle(@Nonnull FullTextQuery fullTextQuery) {
        Search search = new Search.Builder(EsQueryUtils.createQuery(fullTextQuery))
                .addIndex(BlogProperties.ES_INDEX)
                .addType(BlogProperties.ES_TYPE).build();

        List<Article> articles = new ArrayList<>();

        try {
            SearchResult result = jestClient.execute(search);
            JsonArray hits = result.getJsonObject().get("hits").getAsJsonObject().get("hits").getAsJsonArray();
            Gson gson = new Gson();

            hits.forEach(jsonElement -> {
                JsonObject object = jsonElement.getAsJsonObject().get("_source").getAsJsonObject();
                Article article = gson.fromJson(object, Article.class);
                articles.add(article);
            });

        } catch (IOException e) {
            log.debug("es 文章查询失败：[{}]", e.getMessage());
            throw new ElasticSearchException("es 文章查询失败");
        }

        log.info("es 文章查询成功:[{}]", articles);
        return Result.create(ResultStatus.OPERATION_SUCCESS, articles);

    }

    @Override
    public Result removeArticle(@Nonnull String id) {
        Delete delete = new Delete.Builder(id)
                .index(BlogProperties.ES_INDEX)
                .type(BlogProperties.ES_TYPE).build();
        DocumentResult result;
        try {
            result = jestClient.execute(delete);
        } catch (IOException e) {
            log.debug("es 文章删除失败：[{}]", e.getMessage());
            throw new ElasticSearchException("es 文章删除失败");
        }
        log.info("es 文章删除失败：[{}]", id);
        return Result.create(ResultStatus.OPERATION_SUCCESS, result);
    }

    @Override
    public Result modifyArticle(@Nonnull Post post, @Nonnull ElasticsearchStatus elasticsearchStatus) {

        Article article = new Article();
        BeanUtils.copyProperties(post, article);
        article.setTag(elasticsearchStatus.getCode());

        Index index = new Index.Builder(article)
                .index(BlogProperties.ES_INDEX)
                .type(BlogProperties.ES_TYPE)
                .id(post.getDocumentId())
                .build();

        DocumentResult result;
        try {
            result = jestClient.execute(index);
        } catch (IOException e) {
            log.debug("es 文章修改失败：[{}]", e.getMessage());
            throw new ElasticSearchException("es 文章修改失败");
        }

        log.debug("es 文章修改成功");
        return Result.create(ResultStatus.OPERATION_SUCCESS, result);
    }

}
