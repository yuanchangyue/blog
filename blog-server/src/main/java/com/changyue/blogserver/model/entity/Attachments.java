package com.changyue.blogserver.model.entity;

import com.changyue.blogserver.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.io.Serializable;

/**
 * @author : 袁阊越
 * @description : 附件实体
 * @date : 2020/3/15
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Attachments extends BaseEntity {

    /**
     * id
     */
    private Integer id;


    /**
     * 高度
     */
    private Integer height;

    /**
     * 宽度
     */
    private Integer width;

    /**
     * 媒体类型
     */
    private String mediaType;

    /**
     * 名字
     */
    private String name;

    /**
     * 路径
     */
    private String path;

    /**
     * 大小
     */
    private Long size;

    /**
     * 后缀
     */
    private String suffix;

    /**
     * 缩略图
     */
    private String thumbPath;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 用户ID
     */
    private Integer userId;

}
