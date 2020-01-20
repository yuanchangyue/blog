package com.changyue.blogserver.model.dto.base;

import com.changyue.blogserver.utils.ReflectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.lang.Nullable;

import java.lang.reflect.ParameterizedType;

/**
 * @program: blog-server
 * @description: 转换传入的params为数据对象
 * @author: ChangYue
 * @create: 2020-01-20 16:50
 */
public interface InputConvert<DOMAIN> {

    default DOMAIN converTo() {
        ParameterizedType type = parameterizedType();
        Class<DOMAIN> domainClass = (Class<DOMAIN>) type.getActualTypeArguments()[0];
        //domainClass.get
        return null;
    }

    @Nullable
    default ParameterizedType parameterizedType() {
        return ReflectionUtils.getParameterizedType(InputConvert.class, this.getClass());
    }

    @Nullable
    default void update(DOMAIN domain) {
        BeanUtils.copyProperties(this, domain);
    }

}
