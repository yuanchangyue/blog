package com.changyue.blogserver.crawler.manager;

import com.changyue.blogserver.config.CrawlerConfig;
import com.changyue.blogserver.crawler.model.CrawlerComponent;
import com.changyue.blogserver.crawler.model.ParseItem;
import com.changyue.blogserver.crawler.model.ProcessFlowData;
import com.changyue.blogserver.model.enums.CrawlerStatus;
import com.changyue.blogserver.process.ProcessFlow;
import com.changyue.blogserver.serivce.CrawlerPostAdditionalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.Scheduler;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author : 袁阊越
 * @description : 爬虫的管理器
 * @date : 2020-03-24 18:12
 */
@Slf4j
@Component
public class ProcessingFlowManager {

    @Autowired
    private CrawlerConfig crawlerConfig;

    @Autowired
    private CrawlerPostAdditionalService crawlerPostAdditionalService;

    @Resource
    private List<ProcessFlow> processFlowList;

    /**
     * 初始化方法 通过子类优先级 排序
     * 初始化spider
     */
    @PostConstruct
    public void initProcessingFlow() {
        if (null != processFlowList && !processFlowList.isEmpty()) {
            processFlowList.sort((o1, o2) -> {
                if (o1.getPriority() > o2.getPriority()) {
                    return 1;
                } else if (o1.getPriority() < o2.getPriority()) {
                    return -1;
                }
                return 0;
            });
        }
        //初始化spider
        Spider initSpider = initSpider();
        initSpider.thread(5);
        crawlerConfig.setSpider(initSpider);
    }


    /**
     * 初始化spider（PageProcessor，Downloader,Scheduler,Pipeline）
     *
     * @return spider
     */
    private Spider initSpider() {

        Spider spider = null;

        CrawlerComponent crawlerComponent = getComponent(processFlowList);

        PageProcessor pageProcessor = crawlerComponent.getPageProcessor();

        if (null != pageProcessor) {
            spider = Spider.create(pageProcessor);
        }

        if (null != spider && null != crawlerComponent.getScheduler()) {
            spider.setScheduler(crawlerComponent.getScheduler());
        }

        if (null != spider && null != crawlerComponent.getDownloader()) {
            spider.setDownloader(crawlerComponent.getDownloader());
        }

        List<Pipeline> pipelineList = crawlerComponent.getPipelineList();

        if (null != spider && null != pipelineList && !pipelineList.isEmpty()) {
            for (Pipeline pipeline : pipelineList) {
                spider.addPipeline(pipeline);
            }
        }

        return spider;
    }

    /**m
     * 将ProcessFlow分类封装为爬虫组件CrawlerComponent
     *
     * @param processFlowList 处理流列表
     * @return 爬虫组件
     */
    private CrawlerComponent getComponent(List<ProcessFlow> processFlowList) {
        CrawlerComponent component = new CrawlerComponent();
        for (ProcessFlow processFlow : processFlowList) {
            if (processFlow.getComponentType() == CrawlerStatus.ComponentType.PAGE_PROCESSOR) {
                component.setPageProcessor((PageProcessor) processFlow);
            } else if (processFlow.getComponentType() == CrawlerStatus.ComponentType.PIPELINE) {
                component.addPipeline((Pipeline) processFlow);
            } else if (processFlow.getComponentType() == CrawlerStatus.ComponentType.DOWNLOAD) {
                component.setDownloader((Downloader) processFlow);
            } else if (processFlow.getComponentType() == CrawlerStatus.ComponentType.SCHEDULER) {
                component.setScheduler((Scheduler) processFlow);
            }
        }
        return component;
    }

    /**
     * 开始爬虫任务
     *
     * @param parseItems 解析的对象
     * @param handelType 处理类型
     */
    private void startCrawler(List<ParseItem> parseItems, CrawlerStatus.HandelType handelType) {

        ProcessFlowData processFlowData = new ProcessFlowData();
        processFlowData.setParseItemList(parseItems);
        processFlowData.setHandelType(handelType);

        //初始化爬虫列表
        for (ProcessFlow processFlow : processFlowList) {
            processFlow.handle(processFlowData);
        }

        //开始爬虫
        crawlerConfig.getSpider().start();
    }

    /**
     * 正向爬虫
     */
    public void forwardHandle() {
        startCrawler(null, CrawlerStatus.HandelType.FORWARD);
    }

    /**
     * 方向爬虫
     */
    public void reverseHandle() {
        //获取需要方向爬虫的目标
        List<ParseItem> parseItems = crawlerPostAdditionalService.getIncrementParseItem(new Date());
        if (!parseItems.isEmpty()) {
            for (ParseItem parseItem : parseItems) {
                parseItem.setDocumentType(CrawlerStatus.DocumentType.PAGE.name());
                parseItem.setHandelType(CrawlerStatus.HandelType.REVERSE.name());
            }
            //开始反向爬虫
            startCrawler(parseItems, CrawlerStatus.HandelType.REVERSE);
        }
    }

}
