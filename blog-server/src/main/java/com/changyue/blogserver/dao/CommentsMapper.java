package com.changyue.blogserver.dao;

import com.changyue.blogserver.dao.base.BaseMapper;
import com.changyue.blogserver.model.entity.Comments;
import com.changyue.blogserver.model.params.CommentParam;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 评论数据访问接口
 */
@Repository
@Mapper
public interface CommentsMapper extends BaseMapper<Comments> {

    /**
     * 最新的列表
     *
     * @param commentParam 评论
     * @return 评论列表
     */
    List<Comments> listLatest(CommentParam commentParam);

    /**
     * 通过文章id查找评论
     *
     * @return 评论列表
     */
    List<Comments> listBy(CommentParam commentParam);


    int removeById(Long id);
}
