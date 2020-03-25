package com.changyue.blogserver.crawler.parse;


import com.changyue.blogserver.model.enums.CrawlerStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author : 袁阊越
 * @description : 抓取规则的封装
 * @date : 2020/3/24
 */
@Getter
@Setter
@ToString
public class ParseRule {
    /**
     * 映射字段
     */
    private String field;
    /**
     * URL 校验规则
     */
    private String urlValidateRegular;

    /**
     * 解析规则类型
     */
    private CrawlerStatus.ParseRuleType parseRuleType;
    /**
     * 规则
     */
    private String rule;

    /**
     * 抓取内容列表
     */
    private List<String> parseContentList;

    public ParseRule() {
    }

    /**
     * 构造方法
     *
     * @param field         映射字段
     * @param parseRuleType 解析规则类型
     * @param rule          规则
     */
    public ParseRule(String field, CrawlerStatus.ParseRuleType parseRuleType, String rule) {
        this.field = field;
        this.parseRuleType = parseRuleType;
        this.rule = rule;
    }

    /**
     * 检查是否有效，如果内容为空则判断该类为空
     */
    public boolean isAvailability() {
        boolean isAvailability = false;
        if (null != parseContentList && !parseContentList.isEmpty()) {
            isAvailability = true;
        }
        return isAvailability;
    }

    /**
     * 获取合并后的内容
     */
    public String getMergeContent() {
        StringBuilder stringBuilder = new StringBuilder();
        if (null != parseContentList && !parseContentList.isEmpty()) {
            for (String str : parseContentList) {
                stringBuilder.append(str);
            }
        }
        return stringBuilder.toString();
    }

}
