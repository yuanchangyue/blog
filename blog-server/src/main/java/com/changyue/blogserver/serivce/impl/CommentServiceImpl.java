package com.changyue.blogserver.serivce.impl;

import com.changyue.blogserver.dao.CommentsMapper;
import com.changyue.blogserver.model.dto.UserDTO;
import com.changyue.blogserver.model.entity.Comments;
import com.changyue.blogserver.model.enums.CommentState;
import com.changyue.blogserver.model.params.CommentParam;
import com.changyue.blogserver.model.vo.CommentsVO;
import com.changyue.blogserver.model.vo.PostVO;
import com.changyue.blogserver.serivce.CommentsService;
import com.changyue.blogserver.serivce.PostService;
import com.changyue.blogserver.serivce.UserService;
import com.changyue.blogserver.utils.PageInfoUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : 袁阊越
 * @description : 评论业务接口实现类
 * @date : 2020-04-03 10:49
 */
@Service
public class CommentServiceImpl implements CommentsService {

    @Autowired
    private CommentsMapper commentsMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @Override
    @Transactional
    public Comments create(Comments comments) {
        Assert.notNull(comments, "评论不能为空");
        if (commentsMapper.insertSelective(comments) >= 1) {
            return comments;
        }
        return null;
    }

    @Override
    @Transactional
    public Comments createBy(@Nonnull CommentParam commentParam) {
        Assert.notNull(commentParam, "评论的参数不能为空");
        Comments comments = commentParam.convertTo();
        //设置审核
        comments.setStatus(CommentState.NOT_CHECKED.getCode());
        return create(comments);
    }

    @Override
    public PageInfo<CommentsVO> getListLatest(Integer pageIndex, Integer pageSize, @Nonnull CommentParam commentParam) {

        Assert.notNull(pageIndex, "页索引不能为空");
        Assert.notNull(pageSize, "页数不能为空");

        PageHelper.startPage(pageIndex, pageSize);
        List<Comments> comments = commentsMapper.listLatest(commentParam);

        return new PageInfo<>(convertTo(comments), 3);
    }

    @Override
    public PageInfo<CommentsVO> getCommentList(Integer pageIndex, Integer pageSize,CommentParam commentParam) {

        Assert.notNull(pageIndex, "页索引不能为空");
        Assert.notNull(pageSize, "页数不能为空");

        PageHelper.startPage(pageIndex, pageSize);
        List<Comments> comments = commentsMapper.listBy(commentParam);
        PageInfo<Comments> commentsPageInfo = new PageInfo<>(comments, 3);

        PageInfo<CommentsVO> commentsVOPageInfo = PageInfoUtil.PageInfo2PageInfoDTO(commentsPageInfo);

        List<CommentsVO> commentsVOS = convertTo(comments);

        commentsVOS.forEach(commentsVO -> commentsVOPageInfo.getList().add(commentsVO));

        return commentsVOPageInfo;
    }

    @Override
    public int changeStatus(@Nonnull CommentParam commentParam) {
        Comments com = commentParam.convertTo();
        return commentsMapper.updateByPrimaryKeySelective(com);
    }


    private List<CommentsVO> convertTo(List<Comments> comments) {
        return comments.stream().map(c -> {
            CommentsVO commentsVO = new CommentsVO();
            UserDTO userDTO = userService.convertTO(userService.getByUserId(c.getUserId()));
            UserDTO commentUser = userService.convertTO(userService.getByUserId(c.getCommentUserId()));
            PostVO postVO = postService.getByPostId(c.getPostId());
            BeanUtils.copyProperties(c, commentsVO);
            commentsVO.setCommentDTO(commentUser);
            commentsVO.setUserDTO(userDTO);
            commentsVO.setPostVO(postVO);
            return commentsVO;
        }).collect(Collectors.toList());
    }

    @Override
    public int removeById(Long id) {
        Assert.notNull(id, "id不能为空");
        return commentsMapper.removeById(id);
    }
}
