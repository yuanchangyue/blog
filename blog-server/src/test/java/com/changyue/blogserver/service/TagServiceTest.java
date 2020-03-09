package com.changyue.blogserver.service;

import com.changyue.blogserver.model.dto.TagDTO;
import com.changyue.blogserver.model.entity.User;
import com.changyue.blogserver.model.params.UserParam;
import com.changyue.blogserver.serivce.TagService;
import com.changyue.blogserver.serivce.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TagServiceTest {

    @Autowired
    private TagService tagService;

    @Test
    public void test() {
        List<TagDTO> listByUserId = tagService.getListByUserId();
        listByUserId.forEach(System.out::println);
    }

}
