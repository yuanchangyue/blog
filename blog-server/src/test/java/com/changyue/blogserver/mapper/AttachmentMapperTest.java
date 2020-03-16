package com.changyue.blogserver.mapper;

import com.changyue.blogserver.dao.AttachmentsMapper;
import com.changyue.blogserver.model.entity.Attachments;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author : 袁阊越
 * @description :
 * @date : 2020-01-31 19:45
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AttachmentMapperTest {

    @Autowired
    private AttachmentsMapper attachmentsMapper;

    @Test
    public void testListAllByUserId() {
        List<Attachments> attachmentsList = attachmentsMapper.listAllByUserId(13);
        attachmentsList.forEach(System.out::println);
    }

    @Test
    public void test() {
        List<String> allMediaType = attachmentsMapper.listAllMediaType(13);
        allMediaType.forEach(System.out::println);
    }

}
