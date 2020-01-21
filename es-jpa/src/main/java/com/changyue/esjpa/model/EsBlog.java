package com.changyue.esjpa.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

/**
 * @program: es-jpa
 * @description: ElasticSearch博客 文档
 * @author: 袁阊越
 * @create: 2020-01-19 21:59
 */
@Getter
@Setter
@ToString
@Document(indexName = "blog", type = "blog")
public class EsBlog implements Serializable {

    @Id
    private String id;
    private String title;
    private String summary;
    private String content;

    public EsBlog() {
    }

    public EsBlog(String title, String summary, String content) {
        this.title = title;
        this.summary = summary;
        this.content = content;
    }
}
