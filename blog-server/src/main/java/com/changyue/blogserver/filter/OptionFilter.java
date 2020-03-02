package com.changyue.blogserver.filter;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : 袁阊越
 * @description :
 * @date : 2020-03-02 10:42
 */
@Slf4j
public class OptionFilter extends FormAuthenticationFilter {

    /**
     * 放行Options请求。
     * 前后端分离中，跨域会发送preflighted请求，
     * 于是在GET/POST之前会发送一个OPTIONS的请求，
     * OPTIONS不带shiro的'Authorization'字段，导致不通过。返回未认证。
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (request instanceof HttpServletRequest) {
            if (((HttpServletRequest) request).getMethod().toUpperCase().equals("OPTIONS")) {
                return true;
            }
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse res = (HttpServletResponse) response;
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setStatus(HttpServletResponse.SC_OK);
        res.setCharacterEncoding("UTF-8");
        PrintWriter writer = res.getWriter();
        Map<String, Object> map= new HashMap<>();
        map.put("code", 300);
        map.put("message", "未登录");
        writer.write(JSON.toJSONString(map));
        writer.close();
        return false;
    }
}

