package com.changyue.blogserver.process.parse.impl;

import com.changyue.blogserver.crawler.model.CrawlerParseItem;
import com.changyue.blogserver.model.entity.CrawlerPost;
import com.changyue.blogserver.model.enums.CrawlerStatus;
import com.changyue.blogserver.process.parse.AbstractHtmlParsePipeline;
import com.changyue.blogserver.process.thread.CrawlerThreadPool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author : 袁阊越
 * @description :
 * @date : 2020-03-25 21:21
 */
@Component
@Slf4j
public class CrawlerHtmlParsePipeline extends AbstractHtmlParsePipeline<CrawlerParseItem> {


    /**
     * 处理数据
     */
    @Override
    protected void handleHtmlData(CrawlerParseItem parseItem) {
        long currentTimeMillis = System.currentTimeMillis();
        log.info("启动多线程,处理数据：url:[{}]", parseItem.getUrl());
        CrawlerThreadPool.submit(() -> {
            if (CrawlerStatus.HandelType.FORWARD.name().equals(parseItem.getHandelType())) {
                log.info("正在处理数据。url:[{}],handType:[{}]", parseItem.getUrl(), parseItem.getHandelType());
                saveParseItem(parseItem);
            }
        });
        log.info("多线程处理数据完成。url:[{}],handType:[{}],用时:[{}]", parseItem.getUrl(), parseItem.getHandelType(), System.currentTimeMillis() - currentTimeMillis);
    }

    /**
     * 保存数据
     *
     * @param parseItem 解析的数据
     */
    private void saveParseItem(CrawlerParseItem parseItem) {
        long currentTimeMillis = System.currentTimeMillis();

        if (null != parseItem) {
            String url = parseItem.getUrl();
            String handelType = parseItem.getHandelType();

            log.info("正在保存数据，url：[{}],handelType:[{}]", url, handelType);
            CrawlerPost crawlerPost = savePost(parseItem);

            if (null != crawlerPost) {
                //保存附加信息
                savePostAdditional(parseItem, crawlerPost);
                //保存评论
                if (StringUtils.isNotEmpty(parseItem.getContent())) {
                    savePostComment(parseItem, crawlerPost);
                }

                //发送消息
                sendMessageForCrawlerPost(crawlerPost.getId());
            }
            log.info("保存数据完成，url：[{}],handelType:[{}],用时:[{}]", url, handelType, System.currentTimeMillis() - currentTimeMillis);
        }
    }

    /**
     * 发送消息审核爬虫的文章
     *
     * @param crawlerPostId ID
     */
    private void sendMessageForCrawlerPost(Integer crawlerPostId) {

    }

    /**
     * 保存评论
     *
     * @param parseItem
     * @param crawlerPost
     */
    private void savePostComment(CrawlerParseItem parseItem, CrawlerPost crawlerPost) {

    }

    /**
     * 保存附加信息
     *
     * @param parseItem
     * @param crawlerPost
     */
    private void savePostAdditional(CrawlerParseItem parseItem, CrawlerPost crawlerPost) {

    }

    /**
     * 保存爬虫文章
     *
     * @param parseItem
     * @return
     */
    private CrawlerPost savePost(CrawlerParseItem parseItem) {

        return null;
    }

    /**
     * 处理前置一些不规则参数
     * 如：阅读量 123
     */
    @Override
    protected void handleParameter(Map<String, Object> itemsAll) {
        String readCount = itemsAll.get("readCount").toString();
        if (StringUtils.isNotEmpty(readCount)) {
            readCount = readCount.split(" ")[1];
            if (StringUtils.isNotEmpty(readCount)) {
                itemsAll.put("readCount", readCount);
            }
        }
    }

    @Override
    public int getPriority() {
        return 180;
    }
}
