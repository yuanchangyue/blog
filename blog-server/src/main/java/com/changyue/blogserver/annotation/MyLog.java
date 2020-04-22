package com.changyue.blogserver.annotation;

import java.lang.annotation.*;

/**
 * @author : 袁阊越
 * @description : 日志注解
 * @date : 2020-04-22 20:14
 */
@Target(ElementType.METHOD) //注解放置的目标位置,METHOD是可注解在方法级别上
@Retention(RetentionPolicy.RUNTIME) //注解在哪个阶段执行
@Documented //生成文档
public @interface MyLog {
    String value() default "";
}
