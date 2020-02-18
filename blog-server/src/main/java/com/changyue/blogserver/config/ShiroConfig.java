package com.changyue.blogserver.config;

import com.changyue.blogserver.security.ShiroRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author : 袁阊越
 * @description : Shiro配置类
 * @date : 2020-02-18 17:35
 */
@Configuration
public class ShiroConfig {


    /**
     * 创建自定义realm
     */
    @Bean
    public ShiroRealm createShrioRealm() {
        return new ShiroRealm();
    }

    /**
     * 配置安全管理器
     */
    @Bean
    public SecurityManager createSecurityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(new ShiroRealm());
        return manager;
    }

    /**
     * 配置过滤器
     */
    @Bean
    public ShiroFilterFactoryBean createShiroFilterFactoryBean() {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(createSecurityManager());

        Map<String, String> map = new LinkedHashMap<>();

        //注意过滤器配置顺序 不能颠倒
        //anon. 配置不会被拦截的请求 顺序判断
        map.put("/logout", "anon");
        map.put("/login", "anon");
        //authc. 配置拦截的请求
        map.put("/**", "authc");

        //配置shiro默认登录界面地址，前后端分离中登录界面跳转应由前端路由控制，后台仅返回json数据
        shiroFilterFactoryBean.setLoginUrl("/unauth");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        return shiroFilterFactoryBean;
    }

    /**
     *开启shiro的注解
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(createSecurityManager());
        return advisor;
    }

}
