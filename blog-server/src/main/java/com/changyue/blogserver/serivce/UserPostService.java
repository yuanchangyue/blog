package com.changyue.blogserver.serivce;

import com.changyue.blogserver.model.entity.UserPost;
import com.changyue.blogserver.model.vo.CollectionVO;
import com.changyue.blogserver.serivce.base.BaseService;
import com.github.pagehelper.PageInfo;

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

    /**
     * 查找用户和文章之间的关系
     *
     * @param userPost 条件
     * @return 收藏信息
     */
    UserPost getByUserPost(UserPost userPost);

    /**
     * 获取收藏的文章
     *
     * @param pageIndex 页索引
     * @param pageSize  页数
     * @param userId    用户ID
     * @return 分页收藏信息
     */
    PageInfo<CollectionVO> pageBy(Integer pageIndex, Integer pageSize, Integer userId);

}
