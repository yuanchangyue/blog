package com.changyue.blogserver.serivce;

import com.changyue.blogserver.model.entity.User;
import com.changyue.blogserver.model.params.UserParam;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Optional;


public interface UserService extends BaseService<User, Integer> {

    /**
     * 获取当前用户。
     *
     * @return user
     */
    @NonNull
    Optional<User> getCurrentUser();

    /**
     * 通过用户名获取用户。
     *
     * @param username 用户名不能为空
     * @return user
     */
    @NonNull
    Optional<User> getByUsername(@NonNull String username);

    /**
     * 通过用户名获取非null用户。
     *
     * @param username 用户名
     * @return 用户信息
     */
    @NonNull
    User getByUsernameOfNonNull(@NonNull String username);

    /**
     * 通过电子邮件获取用户。
     *
     * @param email 电子邮件不能为空
     * @return user
     */
    @NonNull
    Optional<User> getByEmail(@NonNull String email);

    /**
     * 通过电子邮件获取非null用户。
     *
     * @param email 电子邮件
     * @return 用户信息
     */
    @NonNull
    User getByEmailOfNonNull(@NonNull String email);

    /**
     * 更新用户密码.
     *
     * @param oldPassword 旧密码不能为空
     * @param newPassword 新密码不能为空
     * @param userId      用户ID不能为空
     * @return 更新的用户详细信息
     */
    @NonNull
    User updatePassword(@NonNull String oldPassword, @NonNull String newPassword, @NonNull Integer userId);


    /**
     * 创建一个用户。
     *
     * @param userParam 用户参数不能为空。
     * @return 创建的用户
     */
    @NonNull
    User createBy(@NonNull UserParam userParam);

    /**
     * 检查密码是否与用户密码匹配。
     *
     * @param user          用户信息不能为空
     * @param plainPassword 普通密码
     * @return 如果给定的密码与用户密码匹配，则为true；否则为true。否则为假
     */
    boolean passwordMatch(@NonNull User user, @Nullable String plainPassword);


}
