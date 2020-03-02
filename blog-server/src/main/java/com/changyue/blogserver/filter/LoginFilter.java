package com.changyue.blogserver.filter;

import com.alibaba.fastjson.JSONObject;
import com.changyue.blogserver.model.entity.User;
import com.changyue.blogserver.utils.constant.ConstantProperties;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : 袁阊越
 * @description : 登陆的过滤器
 * @date : 2020-03-01 15:48
 */
@Configuration
@Configurable
@EnableConfigurationProperties(ConstantProperties.class)
public class LoginFilter extends AccessControlFilter {

    @Autowired
    ConstantProperties constantProperties;

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {

        Subject user = SecurityUtils.getSubject();

        User sysUser = (User) user.getPrincipal();

        if (null != sysUser) {
            return Boolean.TRUE;
        }

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String basePath = request.getRequestURL().toString();

        Map<String, String> resultMap = new HashMap<>();
        // ajax请求
        if ("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) {
            // "当前用户没有登录，并且是Ajax请求！");
            resultMap.put("code", "300");
            resultMap.put("message", "会话已经过期，请重新登录！");
            resultMap.put("url", "http://localhost:8081/#/login");
            response.setHeader("Access-Control-Allow-Origin","http://localhost:8081");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().write(JSONObject.toJSONString(resultMap));
            return false;
        }

        //重定向
        WebUtils.issueRedirect(request, response, "http://localhost:8081" + "?redirect=" + "http://localhost:8081/#/login");
        return false;
    }

}
