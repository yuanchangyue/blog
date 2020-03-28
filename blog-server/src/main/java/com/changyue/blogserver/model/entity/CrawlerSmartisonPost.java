package com.changyue.blogserver.model.entity;

import com.changyue.blogserver.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;


@EqualsAndHashCode(callSuper = true)
@Data
public class CrawlerSmartisonPost extends BaseEntity {

    private Integer id;

    private String title;

    private String headpic;

    private String brief;

    private String originalUrl;

    private String url;

    private Date pubTime;

    private String prePic1;

    private String prePic2;

    private String prePic3;

    private Integer siteId;

    private String siteName;

    private String content;

}
