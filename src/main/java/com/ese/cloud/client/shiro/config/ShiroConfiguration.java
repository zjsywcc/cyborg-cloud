package com.ese.cloud.client.shiro.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import com.ese.cloud.client.shiro.realm.LoginShiroRealm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro配置
 * Created by rencong on 16/9/10.
 */
@Configuration
public class ShiroConfiguration {

    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，以为在
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     *
     Filter Chain定义说明
     1、一个URL可以配置多个Filter，使用逗号分隔
     2、当设置多个过滤器时，全部验证通过，才视为通过
     3、部分过滤器可指定参数，如perms，roles
     *
     */
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager){
        System.out.println("ShiroConfiguration.shirFilter()");
        ShiroFilterFactoryBean shiroFilterFactoryBean  = new ShiroFilterFactoryBean();

        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);



        //拦截器.
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String,String>();

        //配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");

        //<!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->


        //静态资源文件不需要验证
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/images/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/myjs/**", "anon");
        //登录验证接口
        filterChainDefinitionMap.put("/api/sms/**", "anon");
        filterChainDefinitionMap.put("/login/login", "anon");
        //注册接口
        filterChainDefinitionMap.put("/register", "anon");
        filterChainDefinitionMap.put("/api/report/*", "anon");
        filterChainDefinitionMap.put("/mobile/*", "anon");
        filterChainDefinitionMap.put("/upload/*", "anon");

        filterChainDefinitionMap.put("/**", "authc");
        //filterChainDefinitionMap.put("/**", "anon");



        // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/index/login");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index/index");
        //未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/error/403");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }


    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        //设置realm
        securityManager.setRealm(new LoginShiroRealm());
        //注入缓存管理器
        securityManager.setCacheManager(ehCacheManager());
        //记住我
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }


    /**
     * 记住密码
     * cookie对象
     */
    @Bean
    public SimpleCookie remembeeMecookie(){
      System.out.println("ShiroConfiguration.rememberMeCookie()");
      //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
      SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
      //<!-- 记住我cookie生效时间3天 ,单位秒;-->
      simpleCookie.setMaxAge(259200);
      return simpleCookie;

    }

    /**
     * Cookie管理对象
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(){

      System.out.println("ShiroConfiguration.rememberMeManager()");
      CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();
      rememberMeManager.setCookie(remembeeMecookie());

      return rememberMeManager;
    }


    @Bean
    public EhCacheManager ehCacheManager(){
        EhCacheManager ehCacheManager = new EhCacheManager();
        return ehCacheManager;
    }


    /**
     * ShiroDialect，为了在thymeleaf里使用shiro的标签的bean
     * @return
     */
    @Bean(name = "shiroDialect")
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }


}
