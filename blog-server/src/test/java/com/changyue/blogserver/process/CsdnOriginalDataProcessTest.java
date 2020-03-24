package com.changyue.blogserver.process;

import com.changyue.blogserver.crawler.model.ParseItem;
import com.changyue.blogserver.crawler.model.ProcessFlowData;
import com.changyue.blogserver.process.original.impl.CsdnOriginalDataProcess;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author : 袁阊越
 * @description :
 * @date : 2020-03-23 22:57
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CsdnOriginalDataProcessTest {

    @Autowired
    private CsdnOriginalDataProcess csdnOriginalDataProcess;

    @Test
    public void test() {
        List<ParseItem> parseItems = csdnOriginalDataProcess.parseOriginalRequestData(new ProcessFlowData());
        parseItems.forEach(System.out::println);
    }
}
