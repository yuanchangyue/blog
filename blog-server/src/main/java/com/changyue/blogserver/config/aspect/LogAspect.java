package com.changyue.blogserver.config.aspect;

import com.alibaba.fastjson.JSON;
import com.changyue.blogserver.annotation.MyLog;
import com.changyue.blogserver.model.entity.Logs;
import com.changyue.blogserver.serivce.LogsService;
import com.changyue.blogserver.utils.IpUtils;
import com.changyue.blogserver.utils.ShiroUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @author : 袁阊越
 * @description : 日志切面
 * @date : 2020-04-22 20:17
 */
@Aspect
@Component
public class LogAspect {

    @Autowired
    private LogsService logsService;

    //定义切点 @Pointcut
    //在注解的位置切入代码
    @Pointcut("@annotation(com.changyue.blogserver.annotation.MyLog)")
    public void logPointCut() {
    }

    //切面 配置通知
    @AfterReturning("logPointCut()")
    public void saveSysLog(JoinPoint joinPoint) {

        //保存日志
        Logs logs = new Logs();

        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();


        MyLog myLog = method.getAnnotation(MyLog.class);
        if (myLog != null) {
            String value = myLog.value();
            logs.setOperation(value);//保存获取的操作
        }


        String className = joinPoint.getTarget().getClass().getName();

        String methodName = method.getName();
        logs.setMethod(className + "." + methodName);

        Object[] args = joinPoint.getArgs();
        String params = JSON.toJSONString(args);
        logs.setParams(params);

        logs.setCreatetime(new Date());
        logs.setUsername(ShiroUtils.getUser().getUsername());

        //获取用户ip地址
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        logs.setIpAddress(IpUtils.getIpAddr(request));

        logsService.create(logs);
    }

}
