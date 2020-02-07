package com.changyue.blogserver.model.params;

import com.changyue.blogserver.model.dto.base.InputConvert;
import com.changyue.blogserver.model.entity.Category;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author : 袁阊越
 * @description : 类别参数
 * @date : 2020-02-07 18:58
 */
@Data
public class CategoryParam implements InputConvert<Category> {

    /**
     * 分类名称
     */
    @NotBlank(message = "分类名称不能为空")
    @Size(max = 20, message = "分类名称的字符长度不能超过 {max}")
    private String name;

    /**
     * 类别别名
     */
    @Size(max = 20, message = "分类别名的字符长度不能超过 {max}")
    private String slugName;

    /**
     * 类别描述
     */
    @Size(max = 100, message = "分类描述的字符长度不能超过 {max}")
    private String description;

    /**
     * 父类别
     */
    private Integer parentId = 0;


}
