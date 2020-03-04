package com.changyue.blogserver.mapper;

import com.changyue.blogserver.dao.TagMapper;
import com.changyue.blogserver.model.entity.Tag;
import org.elasticsearch.action.get.TransportGetAction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;

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
    public void testCountByNameOrSlugName() {
        System.out.println("tagMapper.countByNameOrSlugName(null, null) = " + tagMapper.countByNameOrSlugName(null, null));
    }

    @Test
    public void testSelectByPrimaryKey() {
        Optional<Tag> tag = tagMapper.selectByPrimaryKey(36);
        System.out.println("tag = " + tag);
    }

    @Test
    public void testFindTagByIds() {
        ArrayList<Integer> collect = new ArrayList<>();
        collect.add(39);
        collect.add(40);

        List<Tag> tagByIds = tagMapper.findTagByIds(collect);
        tagByIds.forEach(System.out::println);
    }

}
