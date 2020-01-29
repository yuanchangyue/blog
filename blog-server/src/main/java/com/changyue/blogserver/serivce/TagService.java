package com.changyue.blogserver.serivce;

import com.changyue.blogserver.model.dto.TagDTO;
import com.changyue.blogserver.model.entity.Tag;
import com.changyue.blogserver.serivce.base.CrudService;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * @program: blog-server
 * @description: 标签业务接口
 * @author: 袁阊越
 * @create: 2020-01-20 21:14
 */

public interface TagService extends CrudService<Tag, Integer> {

    /**
     * 按子弹名称获取标签
     *
     * @param slugName slug name
     * @return Tag
     */
/*    @NonNull
    Tag getBySlugNameOfNonNull(@NonNull String slugName);*/

    /**
     * Get tag by slug name
     *
     * @param slugName slug name
     * @return tag
     */
    @NonNull
    Tag getBySlugName(@NonNull String slugName);

    /**
     * Get tag by tag name.
     *
     * @param name name
     * @return Tag
     */
    @Nullable
    Tag getByName(@NonNull String name);

    /**
     * Converts to tag dto.
     *
     * @param tag tag must not be null
     * @return tag dto
     */
    @NonNull
    TagDTO convertTo(@NonNull Tag tag);

    /**
     * Converts to tag dtos.
     *
     * @param tags tag list
     * @return a list of tag output dto
     */
    @NonNull
    List<TagDTO> convertTo(@Nullable List<Tag> tags);
}
