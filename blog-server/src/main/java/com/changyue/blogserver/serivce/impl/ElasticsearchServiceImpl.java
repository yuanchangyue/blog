package com.changyue.blogserver.serivce.impl;

import com.changyue.blogserver.config.properties.BlogProperties;
import com.changyue.blogserver.exception.ElasticSearchException;
import com.changyue.blogserver.model.elsatic.Article;
import com.changyue.blogserver.model.entity.Post;
import com.changyue.blogserver.model.enums.ElasticsearchStatus;
import com.changyue.blogserver.model.enums.ResultStatus;
import com.changyue.blogserver.model.rep.Result;
import com.changyue.blogserver.serivce.ElasticsearchService;
import com.changyue.blogserver.utils.EsQueryUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.searchbox.client.JestClient;
import io.searchbox.core.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
    public DocumentResult indexArticle(@Nonnull Post post, ElasticsearchStatus elasticsearchStatus) {

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
        return result;
    }

    @Override
    public List<Article> searchArticle(@Nonnull String fullTextQuery) {
        Search search = new Search.Builder(EsQueryUtils.createHighSearch(fullTextQuery))
                .addIndex(BlogProperties.ES_INDEX)
                .addType(BlogProperties.ES_TYPE).build();

        List<Article> articles = new ArrayList<>();

        try {
            SearchResult result = jestClient.execute(search);
            JsonArray hits = result.getJsonObject().get("hits").getAsJsonObject().get("hits").getAsJsonArray();
            Gson gson = new Gson();

            hits.forEach(jsonElement -> {
                JsonObject source = jsonElement.getAsJsonObject().get("_source").getAsJsonObject();
                JsonElement highlight1 = jsonElement.getAsJsonObject().get("highlight");
                if (highlight1==null){
                    return;
                }
                JsonObject highlight = highlight1.getAsJsonObject();
                if (highlight != null) {
                    Article article = gson.fromJson(source, Article.class);
                    //高亮搜索不为空就重新赋值
                    if (null != highlight) {
                        if (null != highlight.get("title")) {
                            String title = highlight.get("title").toString();
                            if (StringUtils.isNotEmpty(title)) {
                                title = StringUtils.remove(title.substring(2, title.length() - 2), "\\");
                                article.setTitle(title);
                            }
                        }
                        if (null != highlight.get("summary")) {
                            String summary = highlight.get("summary").toString();
                            if (StringUtils.isNotEmpty(summary)) {
                                summary = StringUtils.remove(summary.substring(2, summary.length() - 2), "\\");
                                article.setSummary(summary);
                            }
                        }
                    }
                    articles.add(article);
                }
            });
        } catch (IOException e) {
            log.debug("es 文章查询失败：[{}]", e.getMessage());
            throw new ElasticSearchException("es 文章查询失败");
        }
        log.info("es 文章查询成功:[{}]", articles);
        return articles;
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
    public Result modifyArticle(@Nonnull Post post, @Nonnull String documentId, @Nonnull ElasticsearchStatus elasticsearchStatus) {

        Article article = new Article();
        BeanUtils.copyProperties(post, article);
        article.setTag(elasticsearchStatus.getCode());

        Index index = new Index.Builder(article)
                .index(BlogProperties.ES_INDEX)
                .type(BlogProperties.ES_TYPE)
                .id(documentId)
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
