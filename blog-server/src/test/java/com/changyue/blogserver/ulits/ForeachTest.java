package com.changyue.blogserver.ulits;

import com.changyue.blogserver.model.dto.CategoryDTO;
import com.changyue.blogserver.model.entity.Category;
import com.changyue.blogserver.model.vo.PostVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author : 袁阊越
 * @description :
 * @date : 2020-03-06 10:40
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ForeachTest {

    @Test
    public void test() {

        List<PostVO> postVOS = new ArrayList<>();

        PostVO postVO_1 = new PostVO();
        PostVO postVO_2 = new PostVO();

        List<CategoryDTO> categories = new ArrayList<>();

        CategoryDTO categoryDTO_1 = new CategoryDTO(1);
        CategoryDTO categoryDTO_2 = new CategoryDTO(2);
        CategoryDTO categoryDTO_3 = new CategoryDTO(3);

        categories.add(categoryDTO_1);
        categories.add(categoryDTO_2);
        categories.add(categoryDTO_3);

        postVO_1.setCategories(categories);
        postVO_2.setCategories(categories);

        postVOS.add(postVO_1);
        postVOS.add(postVO_2);

        for (PostVO postVO : postVOS) {
            List<CategoryDTO> collect = postVO.getCategories()
                    .stream()
                    .filter(categoryDTO -> categoryDTO.getId().equals(2))
                    .collect(Collectors.toList());
            postVO.setCategories(collect);
        }

        postVOS.forEach(System.out::println);

    }

}
