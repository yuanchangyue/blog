package com.changyue.blogserver.model.elsatic;


import lombok.Data;

/**
 * @program: blog-server
 * @description:
 * @author: 袁阊越
 * @create: 2020-01-18 22:49
 */
@Data
public class Article {
    private Integer id;
    private String author;
    private String title;
    private String content;
}
