package com.changyue.blogserver.model.dto;

import com.changyue.blogserver.utils.ReflectionUtils;
import org.springframework.lang.Nullable;

import java.lang.reflect.ParameterizedType;

/**
 * @program: blog-server
 * @description: 转换传入的params为数据对象
 * @author: ChangYue
 * @create: 2020-01-20 16:50
 */
public interface ConvertToEntity<DOMAIN> {

    default DOMAIN converTo() {
        ParameterizedType type = parameterizedType();
        Class<DOMAIN> domainClass = (Class<DOMAIN>) type.getActualTypeArguments()[0];
        //domainClass.get

        return null;
    }

    @Nullable
    default ParameterizedType parameterizedType() {
        return ReflectionUtils.getParameterizedType(ConvertToEntity.class, this.getClass());
    }

}
