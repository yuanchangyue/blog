package com.changyue.blogserver.model.dto;

import lombok.Data;

import java.util.Date;

/**
 * @program: blog-server
 * @description: 用户
 * @author: 袁阊越
 * @create: 2020-01-20 19:23
 */
@Data
public class UserDTO {

    private Integer id;

    private String username;

    private String nickname;

    private String email;

    private String avatar;

    private String description;

    private Date createTime;

    private Date updateTime;

}
