package com.changyue.blogserver.serivce.impl;

import com.changyue.blogserver.dao.UserPostMapper;
import com.changyue.blogserver.model.dto.UserDTO;
import com.changyue.blogserver.model.entity.CrawlerSmartisonPost;
import com.changyue.blogserver.model.entity.UserPost;
import com.changyue.blogserver.model.vo.CollectionVO;
import com.changyue.blogserver.model.vo.PostVO;
import com.changyue.blogserver.serivce.CrawlerSmartisonService;
import com.changyue.blogserver.serivce.PostService;
import com.changyue.blogserver.serivce.UserPostService;
import com.changyue.blogserver.serivce.UserService;
import com.changyue.blogserver.utils.PageInfoUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : 袁阊越
 * @description : 用户收藏文章业务实现类
 * @date : 2020-04-10 23:36
 */
@Service
public class UserPostServiceImpl implements UserPostService {

    @Autowired
    private UserPostMapper userPostMapper;

    @Autowired
    private PostService postService;

    @Autowired
    private CrawlerSmartisonService crawlerSmartisonService;

    @Autowired
    private UserService userService;


    @Override
    public boolean isExist(UserPost userPost) {
        Assert.notNull(userPost, "用户收藏信息不能为空");
        return userPostMapper.findOneBySelective(userPost).isPresent();
    }

    @Override
    public UserPost getByUserPost(UserPost userPost) {
        Assert.notNull(userPost, "用户收藏信息不能为空");
        return userPostMapper.findOneBySelective(userPost).orElse(null);
    }

    @Override
    public PageInfo<CollectionVO> pageBy(Integer pageIndex, Integer pageSize, Integer userId) {

        //根据收藏关系生成分页
        PageHelper.startPage(pageIndex, pageSize);
        List<UserPost> userPosts = userPostMapper.listByUserId(userId);
        PageInfo<UserPost> userPostPageInfo = new PageInfo<>(userPosts, 3);

        //转换为视图分页信息
        PageInfo<CollectionVO> collectionVOPageInfo = PageInfoUtil.PageInfo2PageInfoDTO(userPostPageInfo);

        if (!userPosts.isEmpty()) {
            //转化
            List<CollectionVO> collectionVOS = convertTo(userPosts);
            //添加到视图的分页中
            collectionVOS.forEach(collectionVO -> collectionVOPageInfo.getList().add(collectionVO));
        }

        return collectionVOPageInfo;
    }

    /**
     * userPost 转换为 CollectionVO的对象
     *
     * @param userPosts 收藏的关系
     * @return 收藏视图对象列表
     */
    private List<CollectionVO> convertTo(List<UserPost> userPosts) {
        return userPosts.stream().map(userPost -> {
            CollectionVO collectionVO = new CollectionVO();
            collectionVO.setId(userPost.getId());
            if (null != userPost.getPostId()) {
                PostVO byPostId = postService.getByPostId(userPost.getPostId());
                collectionVO.setPost(byPostId);
            }
            if (null != userPost.getCrawlerPostId()) {
                CrawlerSmartisonPost byId = crawlerSmartisonService.getSimplyPost(userPost.getCrawlerPostId());
                collectionVO.setCrawlerPost(byId);
            }
            UserDTO userDTO = userService.convertTO(userService.getByUserId(userPost.getUserId()));
            collectionVO.setUser(userDTO);
            return collectionVO;
        }).collect(Collectors.toList());
    }


    @Override
    @Transactional
    public UserPost create(UserPost userPost) {
        Assert.notNull(userPost, "用户收藏信息不能为空");
        if (!isExist(userPost)) {
            userPostMapper.insertSelective(userPost);
        }
        return userPost;
    }

    @Override
    public int removeById(Integer id) {
        Assert.notNull(id, "id不能为空");
        return userPostMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional
    public void remove(UserPost userPost) {
        Assert.notNull(userPost, "用户收藏信息不能为空");
        UserPost byUserPost = getByUserPost(userPost);
        if (null == byUserPost) {
            UserPost up = new UserPost();
            up.setUserId(userPost.getUserId());
            up.setPostId(userPost.getCrawlerPostId());
            byUserPost = getByUserPost(up);
        }
        removeById(byUserPost.getId());
    }
}
