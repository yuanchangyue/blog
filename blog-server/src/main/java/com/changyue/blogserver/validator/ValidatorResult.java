package com.changyue.blogserver.validator;


import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : 袁阊越
 * @description : 验证结果
 * @date : 2020/2/5/005
*/
public class ValidatorResult {
    /**
     * 是否有错
     */
    private boolean hasError = false;

    /**
     * 存放错误的信息
     * 如 propertyName ：message
     */
    private Map<String, String> errorMsgMap = new HashMap<>();

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public Map<String, String> getErrorMsgMap() {
        return errorMsgMap;
    }

    public void setErrorMsgMap(Map<String, String> errorMsgMap) {
        this.errorMsgMap = errorMsgMap;
    }

    /**
     * 实现通用的格式化字符串信息获取的错误信息结果的msg方法
     *
     * @return 错误信息
     */
    public String getErrMsg() {
        return StringUtils.join(Arrays.toString(errorMsgMap.values().toArray()) + ",");
    }

}
