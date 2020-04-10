package com.changyue.blogserver.model.vo;

import com.changyue.blogserver.model.dto.UserDTO;
import lombok.Data;

/**
 * @author : 袁阊越
 * @description : 订阅视图对象
 * @date : 2020-04-10 16:48
 */
@Data
public class SubscriptionVO {

    /**
     * id
     */
    private Integer id;

    /**
     * 订阅的用户
     */
    private UserDTO user;

    /**
     * 站点信息
     */
    private SiteVO site;

}
