package com.changyue.blogserver.serivce.impl;

import com.changyue.blogserver.dao.UserSiteMapper;
import com.changyue.blogserver.model.dto.UserDTO;
import com.changyue.blogserver.model.entity.CrawlerPostSite;
import com.changyue.blogserver.model.entity.User;
import com.changyue.blogserver.model.entity.UserSite;
import com.changyue.blogserver.model.vo.SiteVO;
import com.changyue.blogserver.model.vo.SubscriptionVO;
import com.changyue.blogserver.serivce.CrawlerPostSiteService;
import com.changyue.blogserver.serivce.UserService;
import com.changyue.blogserver.serivce.UserSiteService;
import com.changyue.blogserver.utils.PageInfoUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : 袁阊越
 * @description : 用户订阅业务接口实现类
 * @date : 2020-04-10 15:49
 */
@Service
@Slf4j
public class UserSiteServiceImpl implements UserSiteService {

    @Autowired
    private UserSiteMapper userSiteMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private CrawlerPostSiteService crawlerPostSiteService;

    @Override
    @Transactional
    public UserSite create(UserSite userSite) {
        Assert.notNull(userSite, "用户的订阅信息不能为空");
        if (!isExist(userSite)) {
            userSiteMapper.insert(userSite);
        }
        return userSite;
    }

    @Override
    public boolean isExist(UserSite userSite) {
        Assert.notNull(userSite, "用户的订阅信息不能为空");
        return userSiteMapper.findUserSite(userSite.getUserId(), userSite.getSiteId()) != null;
    }

    @Override
    public void remove(UserSite userSite) {
        Assert.notNull(userSite, "用户的订阅信息不能为空");
        userSiteMapper.deleteBy(userSite.getUserId(),userSite.getSiteId());
    }

    @Override
    public PageInfo<SubscriptionVO> pageBy(@Nonnull Integer pageIndex, @Nonnull Integer pageSize, Integer userId) {

        Assert.notNull(pageIndex, "页索引不能为空");
        Assert.notNull(pageSize, "页数不能为空");
        Assert.notNull(userId, "用户id不能为空");

        PageHelper.startPage(pageIndex, pageSize);

        List<UserSite> userSiteByUserId = userSiteMapper.findUserSiteByUserId(userId);

        PageInfo<UserSite> userSitePageInfo = new PageInfo<>(userSiteByUserId, 3);

        PageInfo<SubscriptionVO> subscriptionVOPageInfo = PageInfoUtil.PageInfo2PageInfoDTO(userSitePageInfo);

        List<SubscriptionVO> subscriptionVOList = convertTo(userSiteByUserId);

        subscriptionVOList.forEach(subscriptionVO -> subscriptionVOPageInfo.getList().add(subscriptionVO));

        return subscriptionVOPageInfo;
    }

    private List<SubscriptionVO> convertTo(List<UserSite> userSiteByUserId) {
        return userSiteByUserId.stream().map(userSite -> {
            SubscriptionVO subscriptionVO = new SubscriptionVO();

            subscriptionVO.setId(userSite.getId());

            CrawlerPostSite crawlerPostSite = crawlerPostSiteService.getById(String.valueOf(userSite.getSiteId()));
            SiteVO siteVO = crawlerPostSiteService.convertTo(crawlerPostSite);

            User byId = userService.getByUserId(userSite.getUserId());
            UserDTO userDTO = userService.convertTO(byId);

            subscriptionVO.setSite(siteVO);
            subscriptionVO.setUser(userDTO);

            return subscriptionVO;

        }).collect(Collectors.toList());

    }
}
