package com.changyue.blogserver.model.parse;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : 袁阊越
 * @description : Html 样式
 * @date : 2020/3/26
 */
@Getter
@Setter
public class HtmlStyle {

    /**
     * 存放style样式的map
     */
    private Map<String, String> styleMap = new HashMap<>();

    public void addStyle(String key, String value) {
        styleMap.put(key, value);
    }

    public void addStyle(Map<String, String> map) {
        styleMap.putAll(map);
    }

    /**
     * 获得样式
     */
    public String getCssStyle() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : styleMap.entrySet()) {
            sb.append(entry.getKey()).append(":'").append(entry.getValue()).append("',");
        }
        return StringUtils.removeEnd(sb.toString(), ",");
    }

}
