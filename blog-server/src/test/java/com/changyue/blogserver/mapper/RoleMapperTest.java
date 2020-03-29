package com.changyue.blogserver.mapper;

import com.changyue.blogserver.dao.RoleMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author : 袁阊越
 * @description : 角色mapper test
 * @date : 2020-03-29 15:24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleMapperTest {
    @Autowired
    private RoleMapper roleMapper;

    @Test
    public void test() {
        List<Integer> roleIdByUserId = roleMapper.findRoleIdByUserId(2);
        System.out.println(roleIdByUserId);
    }
}
