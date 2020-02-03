package com.changyue.blogserver.model.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author : 袁阊越
 * @description : 照片传输对象
 * @date : 2020/2/1/001
 */
@Data
public class PhotoDTO {

    private Integer id;

    private String name;

    private String thumbnail;

    private Date takeTime;

    private String url;

    private String team;

    private String location;

    private String description;

}
