package com.changyue.blogserver.serivce;

import com.changyue.blogserver.model.entity.Comments;
import com.changyue.blogserver.model.params.CommentParam;
import com.changyue.blogserver.model.vo.CommentsVO;
import com.changyue.blogserver.serivce.base.BaseService;
import com.github.pagehelper.PageInfo;
import org.springframework.lang.NonNull;

import javax.annotation.Nonnull;

/**
 * @author : 袁阊越
 * @description : 评价业务接口层
 * @date : 2020-04-03 10:47
 */
public interface CommentsService extends BaseService<Comments, Long> {


    /**
     * 新增评论
     *
     * @param commentParam 评论入参
     * @return 评论
     */
    Comments createBy(@Nonnull CommentParam commentParam);


    /**
     * 查找最新的评论列表
     *
     * @param commentParam 入参
     * @return 评论列表
     */
    PageInfo<CommentsVO> getListLatest(@NonNull Integer pageIndex, @NonNull Integer pageSize, @Nonnull CommentParam commentParam);

    /**
     * 查找评论列表
     *
     * @return 评论列表
     */
    PageInfo<CommentsVO> getCommentList(@NonNull Integer pageIndex, @NonNull Integer pageSize, @Nonnull CommentParam commentParam);


    /**
     * 更改状态
     *
     * @return 影响
     */
    int changeStatus(@Nonnull CommentParam commentParam);


}
