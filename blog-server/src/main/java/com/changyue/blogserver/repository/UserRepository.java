package com.changyue.blogserver.repository;

import com.changyue.blogserver.model.entity.User;
import com.changyue.blogserver.repository.base.BaseRepository;
import lombok.NonNull;

import javax.validation.constraints.Null;
import java.util.Optional;

/**
 * @program: blog-server
 * @description: 用户repository
 * @author: ChangYue
 * @create: 2020-01-20 16:04
 */
public interface UserRepository extends BaseRepository<User, Integer> {

    /**
     * 通过用户名获取用户。
     *
     * @param username 用户名
     * @return 用户
     */
    Optional<User> findByUsername(@NonNull String username);

    /**
     * 通过电子邮件获取用户.
     *
     * @param email 电子邮件不能为空
     * @return 用户
     */
    Optional<User> findByEmail(@NonNull String email);

}
