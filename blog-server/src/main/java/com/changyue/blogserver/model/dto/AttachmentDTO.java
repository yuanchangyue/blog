package com.changyue.blogserver.model.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author : 袁阊越
 * @description : 附件DTO
 * @date : 2020/3/15
*/
@Data
public class AttachmentDTO {

    private Integer id;

    private String name;

    private String path;

    private String thumbPath;

    private String mediaType;

    private String suffix;

    private Integer width;

    private Integer height;

    private Long size;

    private String type;

    private Date createTime;
}
