package com.changyue.blogserver.model.dto.base;

import com.changyue.blogserver.utils.ReflectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.Nullable;

import java.lang.reflect.ParameterizedType;
import java.util.Objects;

/**
 * @program: blog-server
 * @description: 转换传入的params为数据对象
 * @author: ChangYue
 * @create: 2020-01-20 16:50
 */
public interface InputConvert<DOMAIN> {

    default DOMAIN converTo() {

        //获取泛型
        ParameterizedType type = parameterizedType();

        Class<DOMAIN> domainClass = (Class<DOMAIN>) type.getActualTypeArguments()[0];

        return com.changyue.blogserver.utils.BeanUtils.transformFrom(this, domainClass);
    }

    /**
     * 获得泛型
     *
     * @return ParameterizedType
     */
    @Nullable
    default ParameterizedType parameterizedType() {
        return ReflectionUtils.getParameterizedType(InputConvert.class, this.getClass());
    }

    @Nullable
    default void update(DOMAIN domain) {
        BeanUtils.copyProperties(this, domain);
    }

}
