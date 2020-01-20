package com.changyue.blogserver.model.params;

import com.changyue.blogserver.model.dto.base.InputConvert;
import com.changyue.blogserver.model.entity.Tag;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @program: blog-server
 * @description: 标签
 * @author: 袁阊越
 * @create: 2020-01-20 22:18
 */
@Data
public class TagParam implements InputConvert<Tag> {

    @NotBlank(message = "标签名称不能为空")
    @Size(max = 255, message = "标签名称的字符长度不能超过 {max}")
    private String name;

    @Size(max = 255, message = "标签别名的字符长度不能超过 {max}")
    private String slugName;

}
