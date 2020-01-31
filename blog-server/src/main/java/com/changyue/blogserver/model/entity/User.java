package com.changyue.blogserver.model.entity;

import com.changyue.blogserver.model.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
public class User extends BaseEntity {


    private Integer id;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 用户描述
     */
    private String description;


    @Override
    public void prePersist() {
        super.prePersist();

        id = null;

        if (email == null) {
            email = "";
        }

        if (avatar == null) {
            avatar = "";
        }

        if (description == null) {
            description = "";
        }

    }
}
