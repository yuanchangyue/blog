package com.changyue.blogserver.model.entity;

import lombok.Data;

/**
 * @author : 袁阊越
 * @description : 爬虫的文章频道
 * @date : 2020-03-25 12:08
 */
@Data
public class CrawlerPostChannelLabel {

    /**
     * id
     */
    private Integer id;

    /**
     * 频道id
     */
    private Integer channelId;

    /**
     * 标签id
     */
    private Integer labelId;

    /**
     * 排序
     */
    private Integer ord;
}
