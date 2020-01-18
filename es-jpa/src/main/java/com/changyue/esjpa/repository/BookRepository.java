package com.changyue.esjpa.repository;

import com.changyue.esjpa.model.Article;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @program: es-jpa
 * @description:
 * @author: 袁阊越
 * @create: 2020-01-18 23:59
 */
public interface BookRepository extends ElasticsearchRepository<Article,Integer> {

}
