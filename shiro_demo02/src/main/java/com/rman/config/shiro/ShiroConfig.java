package com.rman.config.shiro;

import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @Description :
 * @Author : ziming
 * @Date: 2020-04-22 17:24
 */
@Configuration
public class ShiroConfig {

    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     * @return
     */
    /**
     * 安全实体数据源
     * 获取用户实体
     *
     * @return
     */
    @Bean
    public UserRealm userRealm(RedisCacheManager redisCacheManager) {
        UserRealm userRealm = new UserRealm();
//        userRealm.setCredentialsMatcher(hashedCredentialsMatcher());

        userRealm.setCachingEnabled(true);
        //启用身份验证缓存，即缓存AuthenticationInfo信息，默认false
        userRealm.setAuthenticationCachingEnabled(true);
        //缓存AuthenticationInfo信息的缓存名称
        userRealm.setAuthenticationCacheName("authenticationCache");
        //启用授权缓存，即缓存AuthorizationInfo信息，默认false
        userRealm.setAuthorizationCachingEnabled(true);
        //缓存AuthorizationInfo信息的缓存名称
        userRealm.setAuthorizationCacheName("authorizationCache");
        //设置缓存管理器
        userRealm.setCacheManager(redisCacheManager);
        return userRealm;
    }
    @Bean("redisManager")
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost("192.168.8.188:6379");
        redisManager.setPassword("1qaz2wsx");
        return redisManager;
    }
    @Bean("redisSessionDAO")
    public RedisSessionDAO redisSessionDAO(RedisManager redisManager) {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager);
        return redisSessionDAO;
    }
    /**
     * shiro session的管理
     */
    @Bean("redisSessionManager")
    public DefaultWebSessionManager redisSessionManager(RedisSessionDAO redisSessionDAO) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO);
        return sessionManager;
    }
    @Bean("redisCacheManager")
    public RedisCacheManager redisCacheManager(RedisManager redisManager) {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager);
        return redisCacheManager;
    }

    @Bean("securityManager")
    public DefaultWebSecurityManager getManager(UserRealm userRealm,DefaultWebSessionManager redisSessionManager,RedisCacheManager redisCacheManager) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(userRealm);
        manager.setSessionManager(redisSessionManager);
        manager.setCacheManager(redisCacheManager);
        return manager;

    }

    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        HashMap<String, Filter> filterMap = new HashMap<>(16);
        filterMap.put("jwt",new JWTFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        shiroFilterFactoryBean.setSecurityManager(securityManager);

        shiroFilterFactoryBean.setUnauthorizedUrl("/401");

        LinkedHashMap<String, String> filterRulerMap = new LinkedHashMap<>();
        filterRulerMap.put("/**","jwt");
        filterRulerMap.put("/401","anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterRulerMap);

        return shiroFilterFactoryBean;
    }
    /**
     *  开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)即可实现此功能
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * 开启aop注解支持
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(org.apache.shiro.mgt.SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
