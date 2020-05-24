package com.changyue.blogserver.serivce;

import com.changyue.blogserver.model.entity.Post;
import com.changyue.blogserver.model.params.PostQuery;
import com.changyue.blogserver.model.vo.PostVO;
import com.changyue.blogserver.serivce.base.BaseService;
import com.github.pagehelper.PageInfo;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Set;

/**
 * @author : 袁阊越
 * @description : 文章业务层接口
 * @date : 2020/2/3/003
 */
public interface PostService extends BaseService<Post, Integer> {

    /**
     * 分页的文章
     *
     * @param pageIndex 页索引
     * @param pageSize  页数
     */
    @NonNull
    PageInfo<PostVO> pageBy(@NonNull Integer pageIndex, @NonNull Integer pageSize);

    /**
     * 分页的文章
     *
     * @param pageIndex 页索引
     * @param pageSize  页数
     * @param postQuery 文章查询
     */
    @NonNull
    PageInfo<PostVO> pageByQuery(@NonNull Integer pageIndex, @NonNull Integer pageSize, @NonNull PostQuery postQuery);

    /**
     * 最新的文章
     *
     * @return 文章列表
     */
    List<PostVO> latestPost();

    /**
     * 通过id查找文章
     *
     * @param postId 文章id（条件）
     * @return
     */
    @NonNull
    PostVO getByPostId(@NonNull Integer postId);

    /**
     * 获得password通过id
     *
     * @param postId id
     * @return 加密密码
     */
    String getPasswordById(@NonNull Integer postId);

    /**
     * 创建文章
     *
     * @param createdPost 文章内容
     * @param tagIds      标题Id集合
     * @param categoryId  类别Id集合
     * @return 文章
     */
    @NonNull
    PostVO createBy(@NonNull Post createdPost, Set<Integer> tagIds, Set<Integer> categoryId);


    /**
     * 按文章，标签ID集和类别ID集更新文章。
     *
     * @param postToUpdate 要更新的文章不能为空
     * @param tagIds       标签ID集
     * @param categoryIds  类别ID集
     * @return 更新的文章
     */
    @NonNull
    PostVO updateBy(@NonNull Post postToUpdate, Set<Integer> tagIds, Set<Integer> categoryIds);

    /**
     * 按文章状态和网址获取文章。
     *
     * @param status 文章状态不能为空
     * @param url    文章网址不能为空
     * @return 文章信息
     */
    @NonNull
    Post getBy(@NonNull Integer status, @NonNull String url);

    /**
     * 按文章状态列出所有
     *
     * @param status 文章状态不能为空
     * @return 文章列表
     */
    @NonNull
    List<Post> listAllBy(@NonNull Integer status);

    /**
     * 更新文章状态
     *
     * @param postId 文章id
     * @param status 文章状态
     * @return 是否成功
     */
    boolean updateStatus(Integer postId, Integer status);

    /**
     * 统计点赞数
     *
     * @return 点赞数
     */
    long countLike();

    /**
     * 按状态计数文章。
     *
     * @param status status
     * @return 文章数
     */
    long countByStatus(Integer status);

    /**
     * 增加点赞量
     *
     * @param likes  点赞
     * @param postId 文章
     */
    void increaseLike(long likes, Integer postId);

    /**
     * 通过ID查找ES文档的ID
     *
     * @param id id
     * @return ES文档ID
     */
    String getDocumentIdById(Integer id);

    /**
     * 通过user id 获取文章ids
     *
     * @param userId 用户ID
     * @return 文章id集合
     */
    List<Integer> getPostIdsByUserId(Integer userId);

    /**
     * 转化成PostVO
     *
     * @param post 文章
     * @return 文章视图
     */
    PostVO convertTO(Post post);

    /**
     * 批量转化成PostVO
     *
     * @param postList 文章列表
     * @return 文章视图列表
     */
    List<PostVO> convertTO(List<Post> postList);

}
