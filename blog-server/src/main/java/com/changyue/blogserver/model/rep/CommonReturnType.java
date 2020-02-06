package com.changyue.blogserver.model.rep;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @author : 袁阊越
 * @description : 返回通用错误
 * @date : 2020/2/5/005
 */
@Data
public class CommonReturnType<T> {
    /**
     * 返回结果代码 错误或者是失败
     */
    private Integer status;

    /**
     * 顺着代码一起返回的数据
     */
    private T data;

    public static <T> CommonReturnType<T> create(T data) {
        return CommonReturnType.create(HttpStatus.OK.value(), data);
    }

    public static <T> CommonReturnType<T> create(Integer status, T data) {
        CommonReturnType<T> commonReturnType = new CommonReturnType<>();
        commonReturnType.setData(data);
        commonReturnType.setStatus(status);
        return commonReturnType;
    }
}
