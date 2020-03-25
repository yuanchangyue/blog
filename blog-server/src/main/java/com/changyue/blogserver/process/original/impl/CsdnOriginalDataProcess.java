package com.changyue.blogserver.process.original.impl;

import com.changyue.blogserver.config.CrawlerConfig;
import com.changyue.blogserver.crawler.model.CrawlerParseItem;
import com.changyue.blogserver.crawler.model.ParseItem;
import com.changyue.blogserver.crawler.model.ProcessFlowData;
import com.changyue.blogserver.model.enums.CrawlerStatus;
import com.changyue.blogserver.process.original.AbstractOriginalDataProcess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : 袁阊越
 * @description : CSDN 爬虫处理
 * @date : 2020-03-23 22:27
 */
@Component
@Slf4j
public class CsdnOriginalDataProcess extends AbstractOriginalDataProcess {

    @Autowired
    private CrawlerConfig crawlerConfig;

    @Override
    public int getPriority() {
        return 10;
    }

    @Override
    public List<ParseItem> parseOriginalRequestData(ProcessFlowData processFlowData) {

        List<ParseItem> parseItems = new ArrayList<>();

        List<String> crawlerUrlList = crawlerConfig.getCrawlerUrlList();

        if (!crawlerUrlList.isEmpty()) {

            log.info("正在初始化爬虫URL...");
            parseItems = crawlerUrlList.stream().map(url -> {

                CrawlerParseItem crawlerParseItem = new CrawlerParseItem();
                crawlerParseItem.setUrl(url + "?rnd=" + System.currentTimeMillis());
                crawlerParseItem.setDocumentType(CrawlerStatus.DocumentType.INIT.name());
                crawlerParseItem.setHandelType(processFlowData.getHandelType().name());

                return crawlerParseItem;
            }).collect(Collectors.toList());

        }
        log.info("完成初始化爬虫URL");
        return parseItems;
    }
}
