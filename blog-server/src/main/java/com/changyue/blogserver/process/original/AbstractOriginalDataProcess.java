package com.changyue.blogserver.process.original;

import com.changyue.blogserver.config.CrawlerConfig;
import com.changyue.blogserver.crawler.model.ParseItem;
import com.changyue.blogserver.crawler.model.ProcessFlowData;
import com.changyue.blogserver.model.enums.CrawlerStatus;
import com.changyue.blogserver.process.AbstractProcessFlow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author : 袁阊越
 * @description : 处理爬虫数据
 * @date : 2020-03-23 22:25
 */
@Slf4j
public abstract class AbstractOriginalDataProcess extends AbstractProcessFlow {

    @Autowired
    public CrawlerConfig crawlerConfig;

    @Override
    public void handle(ProcessFlowData processFlowData) {

        //从工作流中拿到解析的对象
        List<ParseItem> parseItemList = processFlowData.getParseItemList();

        if (!(null != parseItemList && !parseItemList.isEmpty())) {
            log.info("正在获取初始化的URL");
            parseItemList = parseOriginalRequestData(processFlowData);
            log.info("获取初始化的URL完成，一共有：[{}]", parseItemList.size());
        }

        if (!parseItemList.isEmpty()) {
            //添加到spider
            addSpiderRequest(parseItemList);
        } else {
            log.info("获取初始化的URL失败");
        }

    }


    @Override
    public CrawlerStatus.ComponentType getComponentType() {
        return CrawlerStatus.ComponentType.NORMAL;
    }

    /**
     * 解析初始化需要请求的数据
     *
     * @param processFlowData 流程的数据
     * @return 解析list
     */
    public abstract List<ParseItem> parseOriginalRequestData(ProcessFlowData processFlowData);

}
