package com.changyue.blogserver.serivce;

import com.changyue.blogserver.model.dto.UserDTO;
import com.changyue.blogserver.model.entity.User;
import com.changyue.blogserver.model.params.UserParam;
import com.changyue.blogserver.model.vo.UserVO;
import com.changyue.blogserver.serivce.base.BaseService;
import com.github.pagehelper.PageInfo;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author : 袁阊越
 * @description : 用户业务接口层
 * @date : 2020/2/18/018
 */
public interface UserService extends BaseService<User, Integer> {

    /**
     * 转化用户视图对象
     *
     * @param user 用户
     * @return 用户视图对象
     */
    UserVO getUserForView(User user);

    /**
     * 获取当前用户
     *
     * @return 用户
     */
    @NonNull
    UserDTO getCurrentUser();

    /**
     * 通过用户名获取用户。
     *
     * @param username 用户名不能为空
     * @return user
     */
    User getByUsername(@NonNull String username);


    /**
     * 通过电子邮件获取用户。
     *
     * @param email 电子邮件不能为空
     * @return user
     */
    Optional<User> getByEmail(@NonNull String email);

    /**
     * 按照用户名查找用户列表
     *
     * @param username 用户名
     * @return 用户列表
     */
    List<UserDTO> listUser(@NonNull String username);

    /**
     * 按照用户名查找用户列表(分页)
     *
     * @param username 用户名
     * @return 用户列表
     */
    PageInfo<UserVO> listUser(@Nonnull Integer pageIndex, @Nonnull Integer pageSize, @NonNull String username);

    /**
     * 更新用户密码.
     *
     * @param oldPassword 旧密码不能为空
     * @param newPassword 新密码不能为空
     * @param userId      用户ID不能为空
     * @return 更新的用户详细信息
     */
    User updatePassword(@NonNull String oldPassword, @NonNull String newPassword, String againPassword, @NonNull Integer userId);

    /**
     * 创建一个用户。
     *
     * @param userParam 用户参数不能为空。
     * @return 创建的用户
     */
    User createBy(@NonNull UserParam userParam);

    /**
     * 通过userId查找用户
     *
     * @param userId 用户id
     * @return 用户
     */
    User getByUserId(Integer userId);

    /**
     * 查找用户角色
     *
     * @param userId 用户id
     * @return 用户角色集
     */
    Set<String> getUserRoleNames(Integer userId);

    /**
     * 查找用户权限
     *
     * @param userId 用户id
     * @return 用户权限集
     */
    Set<String> getUserPermissionNames(Integer userId);

    /**
     * 转化成UserDTO
     *
     * @param user 用户
     * @return 用户的DTO
     */
    UserDTO convertTO(User user);

    /**
     * 修改用户的头像
     *
     * @param url 地址
     * @return 是否成功
     */
    boolean modifyAvatar(String url);

    /**
     * 检查密码是否与用户密码匹配。
     *
     * @param user          用户信息不能为空
     * @param plainPassword 普通密码
     * @return 如果给定的密码与用户密码匹配，则为true；否则为true。否则为假
     */
    boolean passwordMatch(@NonNull User user, @Nullable String plainPassword);

    /**
     * 验证用户
     *
     * @param username 用户名
     * @param password 密码
     * @return 是否信息匹配
     */
    boolean verifyUser(@NonNull String username, @NonNull String password);

}
