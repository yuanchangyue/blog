package com.changyue.blogserver.service;

import com.changyue.blogserver.model.params.CommentParam;
import com.changyue.blogserver.model.vo.CommentsVO;
import com.changyue.blogserver.serivce.CommentsService;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author : 袁阊越
 * @description :
 * @date : 2020-04-03 12:05
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CommentsServiceTest {

    @Autowired
    private CommentsService commentsService;

    @Test
    public void testListLatest() {
        PageInfo<CommentsVO> listLatest = commentsService.getListLatest(1, 2, null);
        System.out.println(listLatest);
    }

    @Test
    public void testGetCommentList() {
        CommentParam commentParam = new CommentParam();
        commentParam.setUserId(13);
        PageInfo<CommentsVO> commentList = commentsService.getCommentList(1, 5, commentParam);
        System.out.println(commentList.getPageNum());
        System.out.println(commentList.getTotal());
    }

    @Test
    public void test() {
        CommentParam commentParam = new CommentParam();
        commentParam.setId(9L);
        commentParam.setStatus(1);
        commentsService.changeStatus(commentParam);
    }

}
