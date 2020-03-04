package com.changyue.blogserver.serivce;

import com.changyue.blogserver.model.dto.CategoryDTO;
import com.changyue.blogserver.model.entity.Category;
import com.github.pagehelper.PageInfo;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : 袁阊越
 * @description : 类别业务接口
 * @date : 2020/3/4
 */
@Transactional(readOnly = true)
public interface CategoryService extends BaseService<Category, Integer> {

    /**
     * 按别名获取类别
     *
     * @param slugName 别名
     * @return 类别
     */
    @NonNull
    Category getBySlugName(@NonNull String slugName);

    /**
     * 按名称获取类别。
     *
     * @param name 名字
     * @return 类别
     */
    @Nullable
    Category getByName(@NonNull String name);

    /**
     * 按父ID列出类别。
     *
     * @param id 父Id
     * @return 类别列表
     */
    List<Category> listByParentId(@NonNull Integer id);

    /**
     * 分页全部
     *
     * @param pageIndex 页索引
     * @param pageSize  页数
     * @return 分页类别
     */
    @NonNull
    PageInfo<CategoryDTO> list(@NonNull Integer pageIndex, @NonNull Integer pageSize);

    /**
     * 删除类别同时删除文章类别
     *
     * @param categoryId 类别ID
     */
    @Transactional
    void removeCategoryAndPostCategory(@NonNull Integer categoryId);

    /**
     * 装换为DTO
     *
     * @param category 类别不能为空
     * @return 类别 dto
     */
    @NonNull
    CategoryDTO convertTo(@NonNull Category category);

    /**
     * 装换为DTO
     *
     * @param category 类别不能为空
     * @return 类别 dto
     */
    @NonNull
    List<CategoryDTO> convertTo(@NonNull List<Category> category);
}
