package com.changyue.blogserver.model.params;

import com.changyue.blogserver.model.dto.base.InputConvert;
import com.changyue.blogserver.model.entity.Photo;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @author : 袁阊越
 * @description : 照片参数
 * @date : 2020/2/1/001
 */
public class PhotoParam implements InputConvert<Photo> {

    @NotBlank(message = "照片名称不能为空")
    private String name;

    private String description;

    private Date takeTime;

    private String location;

    @NotBlank(message = "照片缩略图链接地址不能为空")
    private String thumbnail;

    @NotBlank(message = "照片链接地址不能为空")
    private String url;

    private String team;
}
