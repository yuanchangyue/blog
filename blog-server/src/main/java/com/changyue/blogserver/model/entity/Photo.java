package com.changyue.blogserver.model.entity;

import com.changyue.blogserver.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @author : 袁阊越
 * @description : 照片实体
 * @date : 2020/2/1/001
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Photo extends BaseEntity {

    private Integer id;

    /**
     * 照片名称
     */
    private String name;

    /**
     * 照片描述
     */
    private String description;

    /**
     * 照相时间
     */
    private Date takeTime;

    /**
     * 照片地点
     */
    private String location;

    /**
     * 收略图
     */
    private String thumbnail;

    /**
     * 照片地址
     */
    private String url;

    /**
     * 照片组名
     */
    private String team;

}
