package com.changyue.blogserver.process.original.impl;

import com.changyue.blogserver.crawler.model.CrawlerParseItem;
import com.changyue.blogserver.crawler.model.ParseItem;
import com.changyue.blogserver.crawler.model.ProcessFlowData;
import com.changyue.blogserver.model.enums.CrawlerStatus;
import com.changyue.blogserver.process.original.AbstractOriginalDataProcess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : 袁阊越
 * @description : CSDN 爬虫原始数据初始化
 * @date : 2020-03-23 22:27
 */
@Component
@Slf4j
public class CsdnOriginalDataProcess extends AbstractOriginalDataProcess {


    @Override
    public int getPriority() {
        return 10;
    }

    @Override
    public List<ParseItem> parseOriginalRequestData(ProcessFlowData processFlowData) {

        List<ParseItem> parseItems = new ArrayList<>();
        List<String> crawlerUrlList = crawlerConfig.getCsdnCrawlerUrlList();

        if (!crawlerUrlList.isEmpty()) {
            log.info("正在初始化Csdn爬虫URL...");
            parseItems = getParseItems(processFlowData, crawlerUrlList);
        }
        log.info("完成初始化Csdn爬虫URL");

        return parseItems;
    }

    public static List<ParseItem> getParseItems(ProcessFlowData processFlowData, List<String> crawlerUrlList) {
        return crawlerUrlList.stream().map(url -> {
            CrawlerParseItem crawlerParseItem = new CrawlerParseItem();
            crawlerParseItem.setDocumentType(CrawlerStatus.DocumentType.INIT.name());
            crawlerParseItem.setHandelType(processFlowData.getHandelType().name());
            if (!url.contains("reader.smartisan.com")) {
                crawlerParseItem.setUrl(url + "?rnd=" + System.currentTimeMillis());
            }
            crawlerParseItem.setUrl(url);
            return crawlerParseItem;
        }).collect(Collectors.toList());
    }


}
