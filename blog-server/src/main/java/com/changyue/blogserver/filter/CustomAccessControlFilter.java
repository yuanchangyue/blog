package com.changyue.blogserver.filter;

import com.alibaba.fastjson.JSONObject;
import com.changyue.blogserver.model.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : 袁阊越
 * @description : 自定义访问控制过滤器
 * @date : 2020-03-01 15:48
 */
@Configuration
@Configurable
public class CustomAccessControlFilter extends AccessControlFilter {


    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object o) throws Exception {
        return false;
    }

    /**
     * 放行Options请求。前后端分离中，跨域会发送preflighted请求，
     * 于是在GET/POST之前会发送一个OPTIONS的请求，
     * OPTIONS不带shiro的'Authorization'字段，导致不通过。返回未认证。
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {

        Subject user = SecurityUtils.getSubject();

        User sysUser = (User) user.getPrincipal();

        if (null != sysUser) {
            return Boolean.TRUE;
        }

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //放行options
        if (request.getMethod().toUpperCase().equals("OPTIONS")) {
            response.addHeader("Access-Control-Allow-Methods", "GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,PATCH");
            response.addHeader("Access-Control-Allow-Headers", "Content-Type, Accept, Authorization");
            response.setHeader("Access-Control-Allow-Origin", "http://localhost:8081");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
        }

        //当前用户没有登录，并且是Ajax请求
        if ("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) {
            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("code", "300");
            resultMap.put("data", "登陆已经过期，请重新登录！");
            response.setHeader("Access-Control-Allow-Origin", "http://localhost:8081");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().write(JSONObject.toJSONString(resultMap));
            return false;
        }

        return false;
    }

}
