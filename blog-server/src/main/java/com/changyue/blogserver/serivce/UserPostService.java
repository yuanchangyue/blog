package com.changyue.blogserver.serivce;

import com.changyue.blogserver.model.entity.UserPost;
import com.changyue.blogserver.serivce.base.BaseService;

/**
 * @author : 袁阊越
 * @description : 用户收藏文章业务层
 * @date : 2020-04-10 23:33
 */
public interface UserPostService extends BaseService<UserPost, Integer> {


    /**
     * 通过爬虫文章ID/文章ID检查是否存在
     *
     * @param userPost 用户收藏信息
     * @return 是否存在
     */
    boolean isExist(UserPost userPost);

}
