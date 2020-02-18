package com.changyue.blogserver.utils;

import com.changyue.blogserver.validator.ValidatorImpl;
import com.changyue.blogserver.validator.ValidatorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author : 袁阊越
 * @description : bean验证封装的工具
 * @date : 2020-02-15 13:38
 */
@Slf4j
public class ValidatorUtils {

    @Autowired
    private static ValidatorImpl validator;

    public static void verification(Object categoryParam) {
        ValidatorResult result = validator.validator(categoryParam);
        if (result.isHasError()) {
            log.debug("创建tag失败[{}]",result.getErrorMsgMap());
        }
    }



}
