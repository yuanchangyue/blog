package com.changyue.blogserver.utils;

import com.changyue.blogserver.exception.BeanException;
import org.springframework.util.Assert;

import javax.annotation.Nonnull;

/**
 * @author : 袁阊越
 * @description : Bean的工具类
 * @date : 2020-02-01 10:24
 */
public class BeanUtils {

    public static <T> T transformFrom(Object source, @Nonnull Class<T> targetClass) {
        Assert.notNull(targetClass, "目标类不能为空");

        if (source == null) {
            return null;
        }
        try {
            //初始化
            T targetClassInstance = targetClass.newInstance();
            //复制属性
            org.springframework.beans.BeanUtils.copyProperties(source, targetClass);
            return targetClassInstance;
        } catch (Exception e) {
            throw new BeanException(targetClass.getName() + "初始化失败,或者属性转换失败");
        }
    }
}
