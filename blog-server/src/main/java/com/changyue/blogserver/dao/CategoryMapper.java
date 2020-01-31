package com.changyue.blogserver.dao;

import com.changyue.blogserver.dao.base.BaseMapper;
import com.changyue.blogserver.model.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Mapper
@Repository
public interface CategoryMapper extends BaseMapper<Category> {

    /**
     * 按类别名称计数。
     *
     * @param name 类别名称不能为空
     * @param id   类别ID不能为null
     * @return 数量
     */
    long countByNameOrId(@Nullable @Param("name") String name,
                         @Nullable @Param("id") Integer id);

    /**
     * 按别名获取类别
     *
     * @param slugName 别名
     * @return 类别
     */
    Optional<Category> getBySlugName(@NonNull @Param("slugName") String slugName);

    /**
     * 按名称获取类别。
     *
     * @param name 名字
     * @return 类别
     */
    Optional<Category> getByName(@NonNull @Param("name") String name);

    /**
     * 按父ID列出类别。
     *
     * @param parentId 父Id.
     * @return 类别列表
     */
    List<Category> findByParentId(@NonNull @Param("parentId") Integer parentId);

    /**
     * 通过类别id的数组查询全部
     *
     * @param ids id数组
     * @return 类别列表
     */
    List<Category> findAllById(@NonNull @Param("ids") Collection<Integer> ids);

}
