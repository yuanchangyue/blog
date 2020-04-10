package com.changyue.blogserver.serivce;

import com.changyue.blogserver.model.entity.UserSite;
import com.changyue.blogserver.model.vo.SubscriptionVO;
import com.changyue.blogserver.serivce.base.BaseService;
import com.github.pagehelper.PageInfo;

import javax.annotation.Nonnull;

/**
 * @author : 袁阊越
 * @description : 用户订阅业务接口
 * @date : 2020-04-10 15:48
 */
public interface UserSiteService extends BaseService<UserSite, Integer> {

    /**
     * 订阅关系是否存在
     *
     * @param userSite 订阅信息
     * @return 是否存在
     */
    boolean isExist(@Nonnull UserSite userSite);


    /**
     * 订阅列表（分页）
     *
     * @param userId 用户ID
     * @return 订阅列表
     */
    PageInfo<SubscriptionVO> subscribeList(@Nonnull Integer pageIndex, @Nonnull Integer pageSize,@Nonnull Integer userId);

}
