package com.changyue.esjest.model;

import io.searchbox.annotations.JestId;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @program: blog-server
 * @description:
 * @author: 袁阊越
 * @create: 2020-01-18 22:49
 */
@Getter
@Setter
@ToString
public class Article {
    @JestId
    private Integer id;
    private String author;
    private String title;
    private String content;
}
