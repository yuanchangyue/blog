package com.changyue.blogserver.dao;

import com.changyue.blogserver.dao.base.BaseMapper;

import com.changyue.blogserver.model.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

/**
 * @author 袁阊越
 * @date 2020/1/31/031
 * @description 标签接口
 */
@Repository
public interface TagMapper extends BaseMapper<Tag> {

    /**
     * 按名称或别名统计数量
     *
     * @param name     标签名称不能为空
     * @param slugName 标签别名不能为null
     * @return 标签数量
     */
    long countByNameOrSlugName(@Param("name") String name,
                               @Param("slugName") String slugName);

    /**
     * 按别名获取标签
     *
     * @param slugName 别名
     * @return 标签
     */
    Optional<Tag> findBySlugName(@NonNull @Param("slugName") String slugName);

    /**
     * 按名称获取标签
     *
     * @param name name
     * @return 标签
     */
    Optional<Tag> findByName(@NonNull String name);

    /**
     * 通过id数组查询标签
     *
     * @param tagIds id数组
     * @return 标签列表
     */
    List<Tag> findTagByIds(@Param("tagIds")List<Integer> tagIds);

    /**
     * 通过userId查找标签类别
     *
     * @param userId 用户id
     * @return 标签列表
     */
    List<Tag> listAllByUserId(@Param("userId") Integer userId);

}
