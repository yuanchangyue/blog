package com.changyue.blogserver.model.params;

import com.changyue.blogserver.model.dto.base.InputConvert;
import com.changyue.blogserver.model.entity.Attachments;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author : 袁阊越
 * @description : 附件参数
 * @date : 2020/3/15
*/
@Data
public class AttachmentParam implements InputConvert<Attachments> {

    @NotBlank(message = "附件名称不能为空")
    @Size(max = 255, message = "附件名称的字符长度不能超过 {max}")
    private String name;



}
