package com.changyue.blogserver.process.parse;

import com.changyue.blogserver.crawler.helper.CrawlerHelper;
import com.changyue.blogserver.crawler.model.ParseItem;
import com.changyue.blogserver.crawler.model.ProcessFlowData;
import com.changyue.blogserver.model.enums.CrawlerStatus;
import com.changyue.blogserver.process.AbstractProcessFlow;
import com.changyue.blogserver.utils.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Map;

/**
 * @author : 袁阊越
 * @description : Html解析抽象类，定义了公用的方法以及抽象模板
 * Pipeline负责抽取结果的处理，包括计算、持久化到文件、数据库等。
 * Pipeline定义了结果保存的方式，如果你要保存到指定数据库，则需要编写对应的Pipeline。
 * WebMagic默认提供了“输出到控制台”和“保存到文件”两种结果处理方案。
 * @date : 2020-03-25 20:35
 */
@Slf4j
public abstract class AbstractHtmlParsePipeline<T> extends AbstractProcessFlow implements Pipeline {

    @Autowired
    private CrawlerHelper crawlerHelper;

    @Override
    public void handle(ProcessFlowData processFlowData) {

    }

    @Override
    public CrawlerStatus.ComponentType getComponentType() {
        return CrawlerStatus.ComponentType.PIPELINE;
    }

    /**
     * 处理
     *
     * @param resultItems 保存结果（Map）
     * @param task        任务
     */
    @Override
    public void process(ResultItems resultItems, Task task) {
        long currentTimeMillis = System.currentTimeMillis();

        String url = resultItems.getRequest().getUrl();
        String handleType = crawlerHelper.getHandleType(resultItems.getRequest());
        String documentType = crawlerHelper.getDocumentType(resultItems.getRequest());

        log.info("正在解析抽取后的数据. url:[{}],handType:[{}],documentType:[{}]", url, handleType, documentType);

        //判断是否为文当的类型
        if (!CrawlerStatus.DocumentType.PAGE.name().equals(handleType)) {
            log.info("解析失败,当前解析的文档类型不符合。url:[{}],handType:[{}],documentType:[{}]", url, handleType, documentType);
            return;
        }

        ParseItem parseItem = crawlerHelper.getParseItem(resultItems.getRequest());

        if (null != parseItem && StringUtils.isNotEmpty(url)) {

            //拿到处理的数据
            Map<String, Object> itemsAll = resultItems.getAll();

            //处理参数和评论等
            handleParameter(itemsAll);

            if (url.equals(parseItem.getInitialUrl())) {

                //将map中的数据 赋值到parseItem中
                ReflectionUtils.setPropertie(parseItem, itemsAll, true);
                parseItem.setHandelType(handleType);

                handleHtmlData((T) parseItem);
            }

            log.info("解析抽取后的数据结束. url:[{}],handType:[{}],documentType:[{}],用时:[{}]", url, handleType, documentType, System.currentTimeMillis() - currentTimeMillis);
        }

    }

    protected abstract void handleHtmlData(T parseItem);

    protected abstract void handleParameter(Map<String, Object> itemsAll);

}
















