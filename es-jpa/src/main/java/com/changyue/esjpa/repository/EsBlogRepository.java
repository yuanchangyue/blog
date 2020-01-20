package com.changyue.esjpa.repository;

import com.changyue.esjpa.model.EsBlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @program: es-jpa
 * @description: ElasticSearch博客接口
 * @author: 袁阊越
 * @create: 2020-01-19 22:04
 */
public interface EsBlogRepository extends ElasticsearchRepository<EsBlog, String> {
    /**
     * 分页去重查询博客
     *
     * @param title    标题
     * @param summary  简介
     * @param content  内容
     * @param pageable 分页
     * @return 分页的博客
     */
    Page<EsBlog> findDistinctEsBlogByTitleContainingOrSummaryContainingOrContentContaining(String title, String summary, String content, Pageable pageable);
}
