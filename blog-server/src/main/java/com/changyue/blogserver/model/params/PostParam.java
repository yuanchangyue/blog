package com.changyue.blogserver.model.params;

import com.changyue.blogserver.model.dto.base.InputConvert;
import com.changyue.blogserver.model.entity.Post;
import io.netty.channel.MessageSizeEstimator;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

/**
 * @author : ChangYue
 * @description : 文章参数
 * @date : 2020-01-21 09:53
 */
@Data
public class PostParam implements InputConvert<Post> {

    @NotBlank(message = "文章标题不能为空")
    @Size(max = 100, message = "文章标题的字符长度不能超过 {max}")
    private String title;

    private Integer status;

    private String url;

    @NotBlank(message = "文章内容不能空")
    private String originalContent;

    @NotBlank(message = "文章内容不能空")
    private String formatContent;

    private String summary;

    @Size(max = 255, message = "文章缩略图链接的字符长度不能超过 {max}")
    private String thumbnail;

    private Boolean disallowComment = false;

    @Size(max = 255, message = "文章密码的字符长度不能超过 {max}")
    private String password;

    private Integer topPriority = 0;

    private Date createTime;

    private Set<Integer> tagIds;

    private Set<Integer> categoryIds;

}
