package com.changyue.blogserver.process.processor;

import com.changyue.blogserver.crawler.helper.CrawlerHelper;
import com.changyue.blogserver.crawler.model.CrawlerParseItem;
import com.changyue.blogserver.crawler.model.ParseItem;
import com.changyue.blogserver.crawler.model.ProcessFlowData;
import com.changyue.blogserver.model.enums.CrawlerStatus;
import com.changyue.blogserver.process.AbstractProcessFlow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : 袁阊越
 * @description : 页面解析器基类
 * @date : 2020-03-24 14:03
 */
@Slf4j
public abstract class AbstractCrawlerPageProcessor extends AbstractProcessFlow implements PageProcessor {

    /**
     * RETRY_TIME 重试次数
     */
    private static final int RETRY_TIME = 3;

    /**
     * SLEEP_TIME 间隔时间 1s
     */
    private static final int SLEEP_TIME = 1000;

    /**
     * TIME_OUT 超时时间 10s
     */
    private static final int TIME_OUT = 10000;

    @Autowired
    private CrawlerHelper crawlerHelper;

    @Resource
    private CrawlerPostManager crawlerPostManager;


    @Override
    public void handle(ProcessFlowData processFlowData) {
    }

    @Override
    public CrawlerStatus.ComponentType getComponentType() {
        return CrawlerStatus.ComponentType.PAGE_PROCESSOR;
    }

    /**
     * 爬虫的核心逻辑
     */
    @Override
    public void process(Page page) {

        long currentTimeMillis = System.currentTimeMillis();

        String handleType = crawlerHelper.getHandleType(page.getRequest());

        log.info("开始解析页面,url:[{}],handleType:[{}]", page.getUrl(), handleType);

        //调用管理器
        crawlerPostManager.handle(page);

        log.info("开始解析页面,url:[{}],handleType:[{}],用时：[{}]", page.getUrl(), handleType, System.currentTimeMillis() - currentTimeMillis);

    }

    @Override
    public Site getSite() {

        //抓取网站的相关配置，包括编码、抓取间隔、重试次数等
        Site site = Site.me().setRetryTimes(RETRY_TIME).setSleepTime(SLEEP_TIME).setTimeOut(TIME_OUT);

        //设置上header信息
        Map<String, String> headerMap = getHeaderMap();
        if (null != headerMap && !headerMap.isEmpty()) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                site.addHeader(entry.getKey(), entry.getValue());
            }
        }
        return site;
    }

    /**
     * 添加数据到爬取列表
     *
     * @param urlList      爬取的URL列表
     * @param request      上一个爬取的对象
     * @param documentType 需要处理的文档类型
     */
    public void addSpiderRequest(List<String> urlList, Request request, CrawlerStatus.DocumentType documentType) {

        if (null != urlList && !urlList.isEmpty()) {
            List<ParseItem> parseItems = urlList.stream().map(url -> {
                CrawlerParseItem crawlerParseItem = new CrawlerParseItem();
                crawlerParseItem.setUrl(url);
                crawlerParseItem.setHandelType(crawlerHelper.getHandleType(request));
                crawlerParseItem.setDocumentType(documentType.name());
                return crawlerParseItem;
            }).collect(Collectors.toList());

            //添加到爬虫
            addSpiderRequest(parseItems);

        }
    }

    /**
     * 处理页面
     *
     * @param page 页面
     */
    public abstract void handle(Page page);

    /**
     * 是否需要处理类型
     *
     * @param handleType 处理类型
     */
    public abstract boolean isNeedHandlerType(String handleType);

    /**
     * 是否需要文档类型
     *
     * @param documentType 文档类型
     */
    public abstract boolean isNeedDocumentType(String documentType);


}
