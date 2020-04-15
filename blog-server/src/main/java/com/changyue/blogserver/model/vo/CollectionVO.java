package com.changyue.blogserver.model.vo;

import com.changyue.blogserver.model.dto.UserDTO;
import com.changyue.blogserver.model.entity.CrawlerSmartisonPost;
import lombok.Data;

/**
 * @author : 袁阊越
 * @description : 收藏视图对象
 * @date : 2020-04-14 16:58
 */
@Data
public class CollectionVO {

    private Integer id;

    /**
     * 用户创作文章
     */
    private PostVO post;

    /**
     * 爬虫的文章
     */
    private CrawlerSmartisonPost crawlerPost;

    /**
     * 用户
     */
    private UserDTO user;

}
