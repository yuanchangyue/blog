package com.changyue.blogserver.service;

import com.changyue.blogserver.model.vo.SubscriptionVO;
import com.changyue.blogserver.serivce.UserSiteService;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author : 袁阊越
 * @description :
 * @date : 2020-04-10 17:17
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserSiteServiceTest {

    @Autowired
    private UserSiteService userSiteService;


    @Test
    public void test() {
        PageInfo<SubscriptionVO> subscriptionVOPageInfo = userSiteService.subscribeList(1, 10, 13);
        System.out.println(subscriptionVOPageInfo.getList());
    }

}
