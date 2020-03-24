package com.changyue.blogserver.process.processor.impl;

import com.changyue.blogserver.crawler.helper.CrawlerHelper;
import com.changyue.blogserver.crawler.model.CrawlerConfigProperty;
import com.changyue.blogserver.model.enums.CrawlerEnum;
import com.changyue.blogserver.process.processor.AbstractCrawlerPageProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.selector.Html;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : 袁阊越
 * @description : 下一页页面处理
 * @date : 2020-03-24 15:50
 */
@Component
@Slf4j
public class CrawlerHelpPageProcessor extends AbstractCrawlerPageProcessor {

    @Autowired
    private CrawlerConfigProperty crawlerConfigProperty;

    @Autowired
    private CrawlerHelper crawlerHelper;

    @Override
    public void handle(Page page) {

        //处理类型
        String handleType = crawlerHelper.getHandleType(page.getRequest());

        long currentTimeMillis = System.currentTimeMillis();

        //请求的url
        String requestUrl = page.getUrl().get();

        log.info("正在解析下一页,当前的url:[{}],处理的类型为：[{}]", requestUrl, handleType);

        //获取下一页的需要解析的xPath
        String helpCrawlerXpath = crawlerConfigProperty.getHelpCrawlerXpath();

        //获取下一页的分页需要范围
        Integer crawlerHelpNextPagingSize = crawlerConfigProperty.getCrawlerHelpNextPagingSize();

        //用户空间中的文章列表的url
        List<String> targetUrlList = page.getHtml().xpath(helpCrawlerXpath).links().all();

        //判断是否分页
        if (null != crawlerHelpNextPagingSize && crawlerHelpNextPagingSize > 0) {
            //分页处理,处理第一页以后的页面
            List<String> afterPageUrlList = getAfterPageUrlList(requestUrl, crawlerHelpNextPagingSize);
            if (null != afterPageUrlList && !afterPageUrlList.isEmpty()) {
                //将分页数据添加到目标url
                targetUrlList.addAll(afterPageUrlList);
            }
        }

        //爬虫
        addSpiderRequest(targetUrlList, page.getRequest(), CrawlerEnum.DocumentType.PAGE);

        log.info("解析下一页完成,当前的url:[{}],处理的类型为：[{}],用时：[{}]s", requestUrl, handleType, System.currentTimeMillis() - currentTimeMillis);
    }


    /**
     * 获取第一页之后的页面url数据
     *
     * @param url      用户空间的url
     * @param pageSize 分页大小
     * @return 分页页面url数据
     */
    public List<String> getAfterPageUrlList(String url, Integer pageSize) {

        //生成分页的url
        List<String> pagingUrlList = generatedPagingUrlList(url, pageSize);

        //分页里面的文章url
        return getHelpPagingPostUrl(pagingUrlList);

    }

    /**
     * 分页里面的文章url
     *
     * @param pagingUrlList 分页的url
     * @return 分页文章url
     */
    private List<String> getHelpPagingPostUrl(List<String> pagingUrlList) {

        //当前时间
        long currentTimeMillis = System.currentTimeMillis();

        List<String> targetPostUrlList = new ArrayList<>();

        boolean isError = false;

        log.info("正在解析分页页面的文章url");

        if (!pagingUrlList.isEmpty()) {
            for (String url : pagingUrlList) {

                log.info("正在解析分页页面,url:[{}]", url);

                String htmlData = getOriginalRequestHtmlData(url, null);

                //检查当前获取html数据是否有效
                boolean validate = crawlerHelper.getDataValidateCallBack().validate(htmlData);

                if (validate) {
                    //用户空间中的文章列表的url
                    List<String> targetUrlList = new Html(htmlData).xpath(crawlerConfigProperty.getHelpCrawlerXpath()).links().all();

                    if (!targetUrlList.isEmpty()) {
                        targetPostUrlList.addAll(targetUrlList);
                    } else {
                        isError = true;
                    }
                }

                //出错跳出循环
                if (isError) {
                    break;
                }
            }
        }

        log.info("解析分页页面的文章url完成，用时：[{}]s", System.currentTimeMillis() - currentTimeMillis);

        return targetPostUrlList;
    }

    private static final String PAGE_SUFFIX = "/article/list/";

    /**
     * 拼接url  例如：https://blog.csdn.net/qq_16855077/article/list/3
     *
     * @param url      url
     * @param pageSize 页面大小
     * @return pagingUrlList
     */
    private List<String> generatedPagingUrlList(String url, Integer pageSize) {
        ArrayList<String> pagingUrlList = new ArrayList<>();
        for (int i = 2; i < pageSize; i++) {
            pagingUrlList.add(url + PAGE_SUFFIX + i);
        }
        return pagingUrlList;
    }


    /**
     * 是否需要处理类型
     *
     * @param handleType 处理类型
     */
    @Override
    public boolean isNeedHandlerType(String handleType) {
        return CrawlerEnum.HandelType.FORWARD.name().equals(handleType);
    }

    /**
     * 是否需要文档类型
     *
     * @param documentType 文档类型
     */
    @Override
    public boolean isNeedDocumentType(String documentType) {
        return CrawlerEnum.DocumentType.INIT.name().equals(documentType);
    }

    @Override
    public int getPriority() {
        return 110;
    }
}
