package com.changyue.blogserver.model.elsatic;


import lombok.Data;

/**
 * @author : 袁阊越
 * @description : es 文章类
 * @date : 2020/2/18/018
*/
@Data
public class Article {
    private Integer id;
    private String author;
    private String title;
    private String content;
}
