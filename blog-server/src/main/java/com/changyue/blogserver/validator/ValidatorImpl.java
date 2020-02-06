package com.changyue.blogserver.validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;


/**
 * @author : 袁阊越
 * @description : 验证JSR305
 * @date : 2020/2/5/005
 */
@Component
public class ValidatorImpl implements InitializingBean {

    private Validator validator;

    /**
     * 实验校验方法返回校验值
     */
    public ValidatorResult validator(Object bean) {
        ValidatorResult result = new ValidatorResult();
        //检查bean中的错误
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(bean);
        if (constraintViolations.size() > 0) {
            result.setHasError(true);
            constraintViolations.forEach(constraintViolation -> {
                String message = constraintViolation.getMessage();
                String propertyName = constraintViolation.getPropertyPath().toString();
                result.getErrorMsgMap().put(propertyName, message);
            });
        }
        return result;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //将hibernate validator 通过工厂的初始化方式使其实例化
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

}
