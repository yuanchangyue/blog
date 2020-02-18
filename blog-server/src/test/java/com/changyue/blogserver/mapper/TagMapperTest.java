package com.changyue.blogserver.mapper;

import com.changyue.blogserver.dao.TagMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author : 袁阊越
 * @description :
 * @date : 2020-01-31 19:45
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TagMapperTest {

    @Autowired
    private TagMapper tagMapper;

    @Test
    public void testTag() {
        System.out.println("tagMapper.countByNameOrSlugName(null, null) = " + tagMapper.countByNameOrSlugName(null, null));
    }

}
