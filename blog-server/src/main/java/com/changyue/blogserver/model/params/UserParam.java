package com.changyue.blogserver.model.params;

import com.changyue.blogserver.model.dto.base.InputConvert;
import com.changyue.blogserver.model.entity.User;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


/**
 * @author : 袁阊越
 * @description : 用户参数
 * @date : 2020/2/3/003
 */
@Data
public class UserParam implements InputConvert<User> {

    @NotBlank(message = "用户名不能为空")
    @Size(min = 3, max = 32, message = "用户名的长度必须在 {min} - {max} 之间")
    private String username;

    @NotBlank(message = "用户昵称不能为空")
    @Size(min = 3, max = 32, message = "用户昵称的长度必须在 {min} - {max} 之间")
    private String nickname;

    @Email(message = "电子邮件地址的格式不正确")
    @NotBlank(message = "电子邮件地址不能为空")
    @Size(max = 127, message = "电子邮件的字符长度不能超过 {max}")
    private String email;

    @Size(min = 8, max = 32, message = "密码的字符长度必须在 {min} - {max} 之间")
    private String password;

    @Size(max = 1023, message = "头像链接地址的字符长度不能超过 {max}")
    private String avatar;

    @Size(max = 1023, message = "用户描述的字符长度不能超过 {max}")
    private String description;
}
