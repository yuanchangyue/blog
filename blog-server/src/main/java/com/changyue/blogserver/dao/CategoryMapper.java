package com.changyue.blogserver.dao;

import com.changyue.blogserver.dao.base.BaseMapper;
import com.changyue.blogserver.model.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author : 袁阊越
 * @description : 类别数据接口层
 * @date : 2020/2/7/007
 */
@Mapper
@Repository
public interface CategoryMapper extends BaseMapper<Category> {

    /**
     * 按类别名称计数。
     *
     * @param category 类别
     * @return 数量
     */
    long count(Category category);

    /**
     * 按别名获取类别
     *
     * @param slugName 别名
     * @return 类别
     */
    Optional<Category> findBySlugName(@NonNull @Param("slugName") String slugName);

    /**
     * 按名称获取类别。
     *
     * @param name 名字
     * @return 类别
     */
    Optional<Category> findByName(@NonNull @Param("name") String name);

    /**
     * 按用户ID获取类别列表。
     *
     * @param userId 用户Id
     * @return 类别
     */
    List<Category> listAllByUserId(@NonNull @Param("userId") Integer userId);

    /**
     * 按父ID列出类别。
     *
     * @param parentId 父Id.
     * @return 类别列表
     */
    List<Category> listByParentId(@NonNull @Param("parentId") Integer parentId);

    /**
     * 通过类别id的数组查询全部
     *
     * @param categoryIds id数组
     * @return 类别列表
     */
    List<Category> listCategoryByIds(@Param("categoryIds") List<Integer> categoryIds);

    /**
     * 查询用户所属类别为null的类别
     * @param userId 用户id
     * @return 类别列表
     */
    List<Category> listCategoryByNull(@NonNull @Param("userId") Integer userId);

    /**
     * 通过文章id查询全部
     *
     * @param postId 文章Id
     * @return 类别列表
     */
    List<Category> listCategoryByPostId(@Param("postId") Integer postId);

    /**
     * 最新的分类
     * @return 类别列表
     */
    List<Category> listLatest();

    /**
     * 通过用户Id删除分类
     * @param userId 用户id
     * @return 是否删除
     */
    int deleteByUserId(@Param("userId") Integer userId);
}
