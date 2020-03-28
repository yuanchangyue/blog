package com.changyue.blogserver.model.entity;


import com.changyue.blogserver.utils.ZipUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * 文章
 */
@Data
public class CrawlerCsdnPost {
    /**
     * id
     */
    private Integer id;
    /**
     * 任务ID
     */
    private Integer taskId;
    /**
     * 标题
     */
    private String title;
    /**
     * 作者
     */
    private String name;
    /**
     * 类型
     */
    private int type;
    /**
     * 频道ID
     */
    private Integer channelId;
    /**
     * 标签
     */
    private String labels;
    /**
     * 原文时间
     */
    private Date originalTime;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 提交时间
     */
    private Date submitTime;
    /**
     * 状态
     */
    private Byte status;
    /**
     * 发布时间
     */
    private Date publishTime;
    /**
     * 原因
     */
    private String reason;
    /**
     * 来自
     */
    private String from;
    /**
     * 任务讯号
     */
    private Integer no;
    /**
     * 内容
     */
    private String content;
    /**
     * 关联标签ID
     */
    private String labelIds;

    public String getUnCompressContent() {
        if (StringUtils.isNotEmpty(content)) {
            return ZipUtils.gunzip(content);
        }
        return content;
    }


}
