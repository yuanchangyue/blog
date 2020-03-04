package com.changyue.blogserver.model.params;

import com.changyue.blogserver.model.dto.base.InputConvert;
import com.changyue.blogserver.model.entity.Tag;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author : 袁阊越
 * @description : 标签参数
 * @date : 2020/2/3/003
 */
@Data
public class TagParam implements InputConvert<Tag> {

    @NotBlank(message = "标签名称不能为空")
    @Size(max = 6, message = "标签名称的字符长度不能超过 {max}")
    private String name;

    @NotBlank(message = "标签类别不能为空")
    @Size(max = 10, message = "标签别名的字符长度不能超过 {max}")
    private String slugName;

    private Integer userId;

}
