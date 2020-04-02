package com.changyue.blogserver.serivce;

import com.changyue.blogserver.model.dto.TagDTO;
import com.changyue.blogserver.model.entity.Tag;
import com.changyue.blogserver.serivce.base.BaseService;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;


import java.util.List;

/**
 * @author : 袁阊越
 * @date : 2020-01-20 21:14
 * @description : 标签业务接口
 */
public interface TagService extends BaseService<Tag, Integer> {

    /**
     * 按子弹名称获取标签
     *
     * @param slugName slug name
     * @return tag
     */
    @Nullable
    Tag getBySlugName(@NonNull String slugName);

    /**
     * 按标签名称获取标签。
     *
     * @param tagName 名称
     * @return Tag
     */
    @Nullable
    Tag getByName(@NonNull String tagName);


    /**
     * 最新的tag
     *
     * @return 标签列表
     */
    List<TagDTO> getListLatest();

    /**
     * 通过ids查询全部list
     *
     * @param ids id合集
     * @return 标签列表
     */
    List<TagDTO> getListByIds(List<Integer> ids);

    /**
     * 通过文章Id查找标签类别
     *
     * @param postId 文章id
     * @return 标签列表
     */
    List<TagDTO> getListByPostId(Integer postId);

    /**
     * 通过文章Id查找标签类别
     *
     * @return 标签列表
     */
    List<TagDTO> getListByUserId();

    /**
     * 查找标签类别
     *
     * @return 标签列表
     */
    List<TagDTO> getListBy();

    /**
     * 转换为标签dto。
     *
     * @param tag 标记不能为null
     * @return tag dto
     */
    @NonNull
    TagDTO convertTo(@NonNull Tag tag);

    /**
     * list转换为标签dto
     *
     * @param tags 标签列表
     * @return tag dto
     */
    @NonNull
    List<TagDTO> convertTo(@Nullable List<Tag> tags);

}
