package com.changyue.blogserver.serivce.impl;

import com.changyue.blogserver.dao.PostMapper;
import com.changyue.blogserver.exception.CreateException;
import com.changyue.blogserver.exception.NotFindException;
import com.changyue.blogserver.exception.UpdateException;
import com.changyue.blogserver.model.rep.Result;
import com.changyue.blogserver.model.dto.CategoryDTO;
import com.changyue.blogserver.model.dto.TagDTO;
import com.changyue.blogserver.model.dto.UserDTO;
import com.changyue.blogserver.model.entity.*;
import com.changyue.blogserver.model.enums.ElasticsearchStatus;
import com.changyue.blogserver.model.enums.PostStatus;
import com.changyue.blogserver.model.params.PostQuery;
import com.changyue.blogserver.model.vo.PostVO;
import com.changyue.blogserver.serivce.*;
import com.changyue.blogserver.utils.PageInfoUtil;
import com.changyue.blogserver.utils.ShiroUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.searchbox.core.DocumentResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.*;
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

    @Autowired
    private ElasticsearchService elasticsearchService;

    @Override
    public PageInfo<PostVO> pageBy(Integer pageIndex, Integer pageSize) {

        Assert.notNull(pageIndex, "页索引不能为空");
        Assert.notNull(pageSize, "页数不能为空");

        //分页
        PageHelper.startPage(pageIndex, pageSize);
        List<Post> posts = postMapper.listAllByUserId(ShiroUtils.getUser().getId());
        PageInfo<Post> postPageInfo = new PageInfo<>(posts, 3);

        //创建出VO列表
        PageInfo<PostVO> postVOPageInfo = PageInfoUtil.PageInfo2PageInfoDTO(postPageInfo);

        //转换成VO
        List<PostVO> postVOS = posts.stream().map(this::convertTO).collect(Collectors.toList());
        for (int i = 0; i < posts.size(); i++) {
            postVOS.get(i).setTags(tagService.getListByPostId(posts.get(i).getId()));
            postVOS.get(i).setCategories(categoryService.getListCategoryByPostId(posts.get(i).getId()));
        }

        //添加到pageInfo中
        postVOS.forEach(postVO -> postVOPageInfo.getList().add(postVO));

        return postVOPageInfo;
    }

    @Override
    public PageInfo<PostVO> pageByQuery(Integer pageIndex, Integer pageSize, PostQuery postQuery) {

        Assert.notNull(postQuery, "文章查询条件不能空");
        Assert.notNull(pageSize, "页数不能为空");
        Assert.notNull(pageIndex, "页索引不能为空");

        PageHelper.startPage(pageIndex, pageSize);
        List<PostVO> posts = postMapper.listAllByQuery(postQuery, ShiroUtils.getUser().getId());

        return new PageInfo<>(posts, 3);
    }

    @Override
    public PostVO getByPostId(Integer postId) {

        //获取类别
        Set<Integer> categoryIds = postCategoryService.listCategoryPostId(postId);
        List<CategoryDTO> listCategoryByIds = new ArrayList<>();
        if (!categoryIds.isEmpty()) {
            listCategoryByIds = categoryService.getListCategoryByIds(new ArrayList<>(categoryIds));
        }

        //获取标签
        Set<Integer> tagsIds = postTagService.listTagsByPostId(postId);
        List<TagDTO> listByTagIds = new ArrayList<>();
        if (!tagsIds.isEmpty()) {
            listByTagIds = tagService.getListByIds(new ArrayList<>(tagsIds));
        }

        //合并
        PostVO postVO = this.convertTO(postMapper.selectByPrimaryKey(postId).orElse(null));
        postVO.setCategories(listCategoryByIds);
        postVO.setTags(listByTagIds);

        return postVO;
    }

    @Override
    public String getPasswordById(Integer postId) {
        return null;
    }

    @Override
    @Transactional
    public PostVO createBy(Post createdPost, Set<Integer> tagIds, Set<Integer> categoryIds) {
        return createOrUpdateBy(tagIds, categoryIds, createdPost);
    }

    /**
     * 处理文章分类和标签
     *
     * @param tagIds      标签的id集合
     * @param categoryIds 分类的id集合
     * @param post        文章
     * @return 文章视图层
     */
    private PostVO createOrUpdateBy(Set<Integer> tagIds, Set<Integer> categoryIds, Post post) {

        Post createdOrUpdate = this.createOrUpdate(post);
        PostVO postVO = new PostVO();

        if (createdOrUpdate.getId() != null && createdOrUpdate.getStatus() != PostStatus.DRAFT.getStatusCode()) {

            //清空标签和类别
            postTagService.removeByPostId(createdOrUpdate.getId());
            postCategoryService.removeByPostId(createdOrUpdate.getId());

            //没有选择标签
            if (tagIds.isEmpty()) {
                tagIds.add(0);
            }
            //没有选择类别
            if (categoryIds.isEmpty()) {
                categoryIds.add(0);
            }

            List<Tag> tags = tagService.listAllByIds(new ArrayList<>(tagIds));
            List<Category> categories = categoryService.listAllByIds(new ArrayList<>(categoryIds));

            //新增post tag
            log.debug("新增post tag : [{}]", tagIds);
            postTagService.createInBatch(tags.stream().map(tag ->
                    new PostTag(createdOrUpdate.getId(), tag.getId())).collect(Collectors.toList()));

            //新增post category
            log.debug("新增post category : [{}]", categories);
            postCategoryService.createInBatch(categories.stream().map(category ->
                    new PostCategory(category.getId(), createdOrUpdate.getId())).collect(Collectors.toList()));

            BeanUtils.copyProperties(createdOrUpdate, postVO);
            postVO.setCategories(categoryService.convertTo(categories));
            postVO.setTags(tagService.convertTo(tags));

            log.debug("新增文章：[{}]", postVO.getTitle());
        }

        return postVO;
    }

    private Post createOrUpdate(Post post) {
        Assert.notNull(post, "文章的参数不能为空");

        //设置状态
        if (!StringUtils.isEmpty(post.getPassword()) && post.getStatus() != PostStatus.DRAFT.getStatusCode()) {
            post.setStatus(PostStatus.Encrypt.getStatusCode());
        }

        if (post.getId() == null) {
            //创建文章
            return create(post);
        }

        post.setEditTime(new Date());

        return update(post);
    }

    @Override
    @Transactional
    public Post update(Post post) {
        Assert.notNull(post, "文章不能为空");

        //更新ES文章
        elasticsearchService.modifyArticle(post, ElasticsearchStatus.CREATION);

        //更新文章
        if (postMapper.updateByPrimaryKeySelective(post) <= 0) {
            throw new UpdateException("文章更新失败");
        }
        return post;
    }

    @Override
    @Transactional
    public Post create(Post post) {

        //获取当前的用户
        UserDTO currentUser = userService.getCurrentUser();
        //赋值当前的用户ID
        post.setUserId(currentUser.getId());

        //创建文档, 并为文章的赋值es Id
        Result result = elasticsearchService.indexArticle(post, ElasticsearchStatus.CREATION);
        DocumentResult documentResult = (DocumentResult) result.getData();
        post.setDocumentId(documentResult.getId());

        //插入文章到数据库
        int effectNum = postMapper.insertSelective(post);
        if (effectNum <= 0) {
            throw new CreateException("文章创建失败").setErrData(post);
        }
        return post;
    }

    @Override
    @Transactional
    public PostVO updateBy(Post postToUpdate, Set<Integer> tagIds, Set<Integer> categoryIds) {
        return createOrUpdateBy(tagIds, categoryIds, postToUpdate);
    }

    @Override
    @Transactional
    public void removeInBatch(Collection<Integer> ids) {
        if (ids.isEmpty()) {
            return;
        }
        //批量删除
        ids.forEach(this::removeById);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeById(Integer id) {
        Assert.notNull(id, "文章的id不能为空");

        //移除es中的文章
        elasticsearchService.removeArticle(this.getDocumentIdById(id));

        //移除文章相关联的标签
        postTagService.removeByPostId(id);
        log.debug("移除Id为[{}]文章相关联的标签", id);

        //移除文章相关联的类别
        postCategoryService.removeByPostId(id);
        log.debug("移除Id为[{}]文章相关联的类别", id);

        //移除文章
        log.debug("移除Id为[{}]文章", id);
        return postMapper.deleteByPrimaryKey(id);
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
    public boolean updateStatus(Integer postId, Integer status) {
        Assert.notNull(postId, "post Id 不能为空");
        Assert.notNull(status, "status Id 不能为空");
        return postMapper.updateStatus(postId, status) > 0;
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
    public String getDocumentIdById(Integer id) {
        Assert.notNull(id, "post Id 不能为空");
        return postMapper.findDocumentIdById(id);
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
