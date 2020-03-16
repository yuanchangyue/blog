package com.changyue.blogserver.model.params;

import lombok.Data;

/**
 * @author : 袁阊越
 * @description : 附件查询
 * @date : 2020-03-16 10:41
 */
@Data
public class AttachmentQuery {

    /**
     * 关键字
     */
    private String keyWords;

    /**
     * 类型
     */
    private String mediaType;

}
