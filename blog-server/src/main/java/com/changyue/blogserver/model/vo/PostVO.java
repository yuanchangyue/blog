package com.changyue.blogserver.model.vo;

import com.changyue.blogserver.model.dto.CategoryDTO;
import com.changyue.blogserver.model.dto.TagDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author : 袁阊越
 * @description : 文章的视图对象
 * @date : 2020-02-03 15:26
 */
@Data
public class PostVO {

    private Integer id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 文章地址
     */
    private String url;

    /**
     * 原生内容
     */
    private String originalContent;

    /**
     * 格式内容
     */
    private String formatContent;

    /**
     * 文章简介
     */
    private String summary;

    /**
     * 收略图
     */
    private String thumbnail;

    /**
     * 文章访问量
     */
    private Long visits;

    /**
     * 是否允许评价
     */
    private Boolean disallowComment;

    /**
     * 优先权
     */
    private Integer topPriority;

    /**
     * 点赞
     */
    private Long likes;

    /**
     * 编辑时间
     */
    private Date editTime;

    /**
     * 标签集合
     */
    private List<TagDTO> tags;

    /**
     * 分类集合
     */
    private List<CategoryDTO> categories;

    public PostVO() {
    }
}
