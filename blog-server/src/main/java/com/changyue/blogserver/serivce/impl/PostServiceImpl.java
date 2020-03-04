package com.changyue.blogserver.serivce.impl;

import com.changyue.blogserver.dao.PostMapper;
import com.changyue.blogserver.exception.CreateException;
import com.changyue.blogserver.exception.NotFindException;
import com.changyue.blogserver.exception.UpdateException;
import com.changyue.blogserver.model.dto.UserDTO;
import com.changyue.blogserver.model.entity.*;
import com.changyue.blogserver.model.enums.PostStatus;
import com.changyue.blogserver.model.vo.PostVO;
import com.changyue.blogserver.serivce.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.Collectors;

/**
 * @author : 袁阊越
 * @description : 文章业务接口实现
 * @date : 2020-02-03 11:28
 */
@Slf4j
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private PostTagService postTagService;

    @Autowired
    private TagService tagService;

    @Autowired
    private PostCategoryService postCategoryService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Override
    public PageInfo<PostVO> pageBy(Integer pageIndex, Integer pageSize) {

        Assert.notNull(pageIndex, "页索引不能为空");
        Assert.notNull(pageSize, "页数不能为空");

        UserDTO currentUser = userService.getCurrentUser();

        PageHelper.startPage(pageIndex, pageSize);
        List<Post> posts = postMapper.listAllByUserId(currentUser.getId());
        List<PostVO> postVOS = this.convertTO(posts);

        return new PageInfo<>(postVOS, 3);
    }

    @Override
    @Transactional
    public PostVO createBy(Post createdPost, Set<Integer> tagIds, Set<Integer> categoryId) {

        Post post = createOrUpdate(createdPost, tagIds, categoryId);
        PostVO postVO = new PostVO();

        if (post.getId() != null && post.getStatus() != PostStatus.DRAFT.getStatusCode()) {
            //清空标签和类别
            postTagService.removeByPostId(post.getId());
            postCategoryService.removeByPostId(post.getId());

            List<Tag> tags = tagService.listAllByIds(new ArrayList<>(tagIds));
            List<Category> categories = categoryService.listAllByIds(new ArrayList<>(categoryId));

            //新增post tag
            log.debug("新增post tag : [{}]", tagIds);
            postTagService.createInBatch(tags.stream().map(tag ->
                    new PostTag(post.getId(), tag.getId())).collect(Collectors.toList()));

            //新增post category
            log.debug("新增post category : [{}]", categories);
            postCategoryService.createInBatch(categories.stream().map(category ->
                    new PostCategory(category.getId(), post.getId())).collect(Collectors.toList()));

            BeanUtils.copyProperties(post, postVO);
            postVO.setCategories(categoryService.convertTo(categories));
            postVO.setTags(tagService.convertTo(tags));

            log.debug("新增文章：[{}]", postVO.getTitle());
        }

        return postVO;
    }

    private Post createOrUpdate(Post post, Set<Integer> tagIds, Set<Integer> categoryId) {

        Assert.notNull(post, "文章的参数不能为空");

        //设置状态
        if (!StringUtils.isEmpty(post.getPassword()) && post.getStatus() == PostStatus.DRAFT.getStatusCode()) {
            post.setStatus(PostStatus.Encrypt.getStatusCode());
        }

        if (post.getId() == null) {
            //创建文章
            return create(post);
        }

        post.setEditTime(new Date());

        //更新文章
        if (postMapper.updateByPrimaryKeySelective(post) <= 0) {
            throw new UpdateException("文章更新失败");
        }

        return post;
    }

    @Override
    public Post create(Post post) {

        //获取当前的用户
        UserDTO currentUser = userService.getCurrentUser();
        //赋值当前的用户ID
        post.setUserId(currentUser.getId());

        //插入文章到数据库
        int effectNum = postMapper.insertSelective(post);
        if (effectNum <= 0) {
            throw new CreateException("文章创建失败").setErrData(post);
        }
        return post;
    }

    @Override
    @Transactional
    public Post updateBy(Post postToUpdate, Set<Integer> tagIds, Set<Integer> categoryIds) {
        return createOrUpdate(postToUpdate, tagIds, categoryIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeById(Integer id) {
        Assert.notNull(id, "文章的id不能为空");

        //移除文章相关联的标签
        postTagService.removeByPostId(id);
        log.debug("移除Id为[{}]文章相关联的标签", id);

        //移除文章相关联的类别
        postCategoryService.removeByPostId(id);
        log.debug("移除Id为[{}]文章相关联的类别", id);

        //移除文章
        int delete = postMapper.deleteByPrimaryKey(id);
        log.debug("移除Id为[{}]文章", id);

        return delete;
    }

    @Override
    public Post getBy(Integer status, String url) {

        Assert.notNull(status, "文章状态不能为空");
        Assert.hasText(url, "文章的地址不能为空");

        Post post = new Post();
        post.setStatus(status);
        post.setUrl(url);

        Optional<Post> posts = postMapper.findOneBySelective(post);

        return posts.orElseThrow(() -> new NotFindException("没有查询到该文章的信息").setErrData(post));
    }

    @Override
    public List<Post> listAllBy(Integer status) {

        Assert.notNull(status, "文章状态不能为空");

        Post post = new Post();
        post.setStatus(status);

        return postMapper.listAllBySelective(post);
    }

    @Override
    public long countLike() {
        return Optional.ofNullable(postMapper.countLike()).orElse(0L);
    }

    @Override
    public long countByStatus(Integer status) {
        return Optional.ofNullable(postMapper.countAllByStatus(status)).orElse(0L);
    }

    @Override
    public PostVO convertTO(Post post) {
        PostVO postVO = new PostVO();
        BeanUtils.copyProperties(post, postVO);
        return postVO;
    }

    @Override
    public List<PostVO> convertTO(List<Post> postList) {
        return postList.stream().map(this::convertTO).collect(Collectors.toList());
    }

}
