package com.changyue.blogserver.handler;

import com.changyue.blogserver.model.enums.ResultStatus;
import com.changyue.blogserver.model.rep.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : 袁阊越
 * @description : 异常处理类
 * @date : 2020-03-14 20:42
 */
@Slf4j
@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result parameterExceptionHandler(MethodArgumentNotValidException e) {
        log.info("参数出错：[{}]", e.getMessage());
        BindingResult exceptions = e.getBindingResult();
        if (exceptions.hasErrors()) {
            List<ObjectError> errors = exceptions.getAllErrors();
            if (!errors.isEmpty()) {
                // 这里列出了全部错误参数，按正常逻辑，只需要第一条错误即可
                FieldError fieldError = (FieldError) errors.get(0);
                return new Result(ResultStatus.PARAMETER_ERROR.getCode(), fieldError.getDefaultMessage());
            }
        }
        return new Result(ResultStatus.PARAMETER_ERROR);
    }

}
