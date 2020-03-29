package com.changyue.blogserver.service;

import com.changyue.blogserver.model.vo.MenuVo;
import com.changyue.blogserver.model.vo.RouterVO;
import com.changyue.blogserver.serivce.UserAuthorityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author : 袁阊越
 * @description : 用户权限业务测试
 * @date : 2020-03-29 14:54
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserAuthorityServiceTest {

    @Autowired
    private UserAuthorityService userAuthorityService;

    @Test
    public void testGetMenuTreeBy() {
        List<MenuVo> menuTreeBy = userAuthorityService.getMenuTreeBy(1);
        menuTreeBy.forEach(System.out::println);
    }

    @Test
    public void testGet() {
        List<RouterVO> routerList = userAuthorityService.getRouterList(2);
        routerList.forEach(System.out::println);
    }

}
