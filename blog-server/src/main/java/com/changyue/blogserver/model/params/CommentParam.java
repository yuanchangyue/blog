package com.changyue.blogserver.model.params;

import com.changyue.blogserver.model.dto.base.InputConvert;
import com.changyue.blogserver.model.entity.Comments;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author : 袁阊越
 * @description :
 * @date : 2020-04-03 10:44
 */
@Data
public class CommentParam implements InputConvert<Comments> {

    private Long id;

    private Integer userId;

    @NotBlank(message = "评论内容不能为空")
    @Size(max = 1023, message = "评论内容的字符长度不能超过 {max}")
    private String content;

    @Min(value = 1, message = "Post id must not be less than {value}")
    private Integer postId;

    @Min(value = 0, message = "PostComment parent id must not be less than {value}")
    private Long parentId = 0L;

    private String ipAddress;

    private Integer status;


    public CommentParam() {
    }

    public CommentParam(Integer userId, @NotBlank(message = "评论内容不能为空") @Size(max = 1023, message = "评论内容的字符长度不能超过 {max}") String content, @Min(value = 1, message = "Post id must not be less than {value}") Integer postId, @Min(value = 0, message = "PostComment parent id must not be less than {value}") Long parentId) {
        this.userId = userId;
        this.content = content;
        this.postId = postId;
        this.parentId = parentId;
    }
}
