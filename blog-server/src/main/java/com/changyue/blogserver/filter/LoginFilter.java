package com.changyue.blogserver.filter;

import com.alibaba.fastjson.JSONObject;
import com.changyue.blogserver.model.entity.User;
import com.changyue.blogserver.utils.constant.ConstantUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

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
public class LoginFilter extends AccessControlFilter {

    private HttpServletRequest request;
    private HttpServletResponse response;

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {

        Subject user = SecurityUtils.getSubject();

        User sysUser = (User) user.getPrincipal();

        if (null != sysUser) {
            return Boolean.TRUE;
        }

        this.request = (HttpServletRequest) servletRequest;
        this.response = (HttpServletResponse) servletResponse;

        String basePath = request.getRequestURL().toString();
        // ajax请求
        if ("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) {
            Map<String, String> resultMap = new HashMap<>();
            // "当前用户没有登录，并且是Ajax请求！");
            resultMap.put("code", "300");
            resultMap.put("message", "会话已经过期，请重新登录！");//当前用户没有登录！
            resultMap.put("url", ConstantUtils.URL);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JSONObject.toJSONString(resultMap));
            return false;
        }
        //重定向
        WebUtils.issueRedirect(request, response, ConstantUtils.URL + "?redirect=" + basePath);
        return false;
    }

}
