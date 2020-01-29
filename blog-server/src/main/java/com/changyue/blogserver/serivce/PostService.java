package com.changyue.blogserver.serivce;

import com.changyue.blogserver.model.entity.Post;
import com.changyue.blogserver.model.params.PostParam;
import com.changyue.blogserver.model.params.PostQuery;
import com.changyue.blogserver.serivce.base.CrudService;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author Administrator
 */
public interface PostService extends CrudService<Post, Integer> {

    /**
     * 分页的文章
     *
     * @param postQuery 文章查询的条件
     * @param pageable  分页
     * @return 分页的文章
     */
    @Nonnull
    Page<Post> pageBy(@NonNull PostQuery postQuery, @NotNull Pageable pageable);

    /**
     * 创建文章
     *
     * @param postParam  文化内容
     * @param tagIds     标题Id集合
     * @param categoryId 类别Id集合
     * @return 文章
     */
    @Nonnull
    Post createBy(@Nonnull PostParam postParam, Set<Integer> tagIds, Set<Integer> categoryId);

}
