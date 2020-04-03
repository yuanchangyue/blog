package com.changyue.blogserver.model.vo;

import com.changyue.blogserver.model.dto.UserDTO;
import lombok.Data;

import java.util.Date;

/**
 * @author : 袁阊越
 * @description : 评论视图对象
 * @date : 2020-04-03 13:03
 */
@Data
public class CommentsVO {

    private Long id;

    /**
     * 用户
     */
    private UserDTO userDTO;

    /**
     * 评价内容
     */
    private String content;

    /**
     * ip地址
     */
    private String ipAddress;

    /**
     * 是否admin
     */
    private Byte isAdmin;

    /**
     * 父级id
     */
    private Long parentId;

    /**
     * 文章
     */
    private PostVO postVO;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;


}
