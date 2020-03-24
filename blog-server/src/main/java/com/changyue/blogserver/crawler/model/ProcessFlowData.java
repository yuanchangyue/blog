package com.changyue.blogserver.crawler.model;


import com.changyue.blogserver.model.enums.CrawlerEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author : 袁阊越
 * @description : 流程数据
 * @date : 2020/3/23
*/
@Getter
@Setter
public class ProcessFlowData {

    /**
     * 抓取对象列表
     */
    private List<ParseItem> parseItemList;

    /**
     * 处理类型
     */
    private CrawlerEnum.HandelType handelType = CrawlerEnum.HandelType.FORWARD;

}
