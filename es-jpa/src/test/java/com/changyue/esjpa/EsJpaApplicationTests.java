package com.changyue.esjpa;

<<<<<<< HEAD
import com.changyue.esjpa.model.EsBlog;
import com.changyue.esjpa.repository.EsBlogRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
=======
>>>>>>> 581777cc227a7ea37fc0411b3a7a9ad1ced306a4

@RunWith(SpringRunner.class)
@SpringBootTest
public class EsJpaApplicationTests {

    @Autowired
    EsBlogRepository esBlogRepository;

    @Test
    public void testEsBlogRepository() {

        EsBlog esBlog = new EsBlog();
        esBlog.setId("1");
        esBlog.setContent("this is a test msg");
        esBlog.setSummary("asd");
        esBlog.setTitle("msg");

        esBlogRepository.index(esBlog);
    }

}