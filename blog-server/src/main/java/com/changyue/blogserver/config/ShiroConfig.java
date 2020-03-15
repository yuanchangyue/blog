package com.changyue.blogserver.config;

import com.changyue.blogserver.filter.CustomAccessControlFilter;
import com.changyue.blogserver.security.ShiroRealm;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
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
     * 配置过滤器
     */
    @Bean
    public ShiroFilterFactoryBean createShiroFilterFactoryBean(@Qualifier("mySecurityManager") DefaultWebSecurityManager defaultWebSecurityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);

        //添加过滤
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
        //将自定义的AuthenticationFilter注入shiroFilter中，注意是authc过滤器
        filters.put("authc", new CustomAccessControlFilter());
        shiroFilterFactoryBean.setFilters(filters);

        //设置拦截
        Map<String, String> map = new LinkedHashMap<>();
        //注意过滤器配置顺序 不能颠倒
        //anon. 配置不会被拦截的请求 顺序判断
        map.put("/api/user/login", "anon");
        map.put("/api/user/logout", "anon");
        map.put("/#/login","anon");

        //authc. 配置拦截的请求
        map.put("/**", "authc");

        map.put("/#/api/**", "authc");
        map.put("/api/**", "authc");
        map.put("/upload/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        return shiroFilterFactoryBean;
    }

    /**
     * 缓存
     */
    @Bean
    public EhCacheManager ehCacheManager() {
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return cacheManager;
    }

    /**
     * 配置安全管理器
     */
    @Bean("mySecurityManager")
    public DefaultWebSecurityManager createSecurityManager(@Qualifier("myShrioRealm") ShiroRealm shiroRealm) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(shiroRealm);
        // 注入缓存管理器;
        manager.setCacheManager(ehCacheManager());// 这个如果执行多次，也是同样的一个对象;
        return manager;
    }

    /**
     * 创建自定义realm
     */
    @Bean("myShrioRealm")
    public ShiroRealm shiroRealm() {
        return new ShiroRealm();
    }

    @Bean(name = "lifecycleBeanPostProcessor")
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     *
     * @return
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * 开启shiro的注解
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("mySecurityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(defaultWebSecurityManager);
        return advisor;
    }

}
