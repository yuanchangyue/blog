package com.changyue.blogserver.utils;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author : 袁阊越
 * @description : 反射工具类
 * @date : 2020/2/1/001
 */
public class ReflectionUtils {

    private ReflectionUtils() {
    }

    /**
     * 获取泛型
     *
     * @param superType    父类型不能为空(父类类或父类接口）
     * @param genericTypes 通用类型数组
     * @return 接口的参数化类型；如果不匹配，则返回null
     */
    @Nullable
    public static ParameterizedType getParameterizedType(@NonNull Class<?> superType, Type... genericTypes) {
        Assert.notNull(superType, "父类型不能为空");

        ParameterizedType currentType = null;
        for (Type genericType : genericTypes) {
            //
            if (genericType instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) genericType;
                if (parameterizedType.getRawType().getTypeName().equals(superType.getTypeName())) {
                    currentType = parameterizedType;
                    break;
                }
            }
        }

        return currentType;
    }

    /**
     * 获取泛型
     *
     * @param interfaceType       接口类型不能为空
     * @param implementationClass 接口的实现类不能为空
     * @return 接口的参数化类型；如果不匹配，则返回空
     */
    @Nullable
    public static ParameterizedType getParameterizedType(@NonNull Class<?> interfaceType, Class<?> implementationClass) {
        Assert.notNull(interfaceType, "接口类型不能为空");
        Assert.isTrue(interfaceType.isInterface(), "给定类型必须是接口");

        if (implementationClass == null) {
            return null;
        }

        // 获取参数化类型  getGenericInterfaces包括泛型
        ParameterizedType currentType = getParameterizedType(interfaceType, implementationClass.getGenericInterfaces());

        if (currentType != null) {
            // 返回当前类型
            return currentType;
        }

        Class<?> superclass = implementationClass.getSuperclass();

        return getParameterizedType(interfaceType, superclass);
    }

    /**
     * Gets parameterized type by super class.
     *
     * @param superClassType super class type must not be null
     * @param extensionClass extension class
     * @return parameterized type or null
     */
    @Nullable
    public static ParameterizedType getParameterizedTypeBySuperClass(@NonNull Class<?> superClassType, Class<?> extensionClass) {

        if (extensionClass == null) {
            return null;
        }

        return getParameterizedType(superClassType, extensionClass.getGenericSuperclass());
    }
}
