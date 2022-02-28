package com.example.classs.aspect;

import com.example.classs.exception.UnauthorizedException;
import com.example.classs.util.CookieUtil;
import com.github.benmanes.caffeine.cache.Cache;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 校验用户是否登录
 *
 * @author 晓敏
 * @create 2020-04-07 16:42
 */
@Aspect
@Component
public class LoginAspect {

    private Logger logger = LoggerFactory.getLogger(LoginAspect.class);

    @Autowired
    private Cache<String, Object> caffeineCache;

    //切入点,对登录方法不进行切入
    @Pointcut("execution(* com.example.classs.controller.*.*(..))"
            + "&& !execution(* com.example.classs.controller.LoginController.*(..))" //登录方法
            + "&& !execution(* com.example.classs.controller.CatalogController.*(..))")//前台展示不切入
    public void login() {
    }

    /**
     * 前置增强,校验redis中用户的信息
     */
    @Before("login()")
    public void before() {
        //1、得到request对象
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
        //2、获取客户端保存的cookie
        Cookie usernameCookie = CookieUtil.getCookie(request, "username");
        //3、判断cookie是否存在
        if (Objects.isNull(usernameCookie)) {
            logger.warn("【登录校验】Cookie中查询不到username");
            //未授权
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED);
        }
        String username = usernameCookie.getValue();
        Cookie auth = CookieUtil.getCookie(request, username);
        if (Objects.isNull(auth)) {
            logger.warn("【登录校验】Cookie中查询不到token");
            //未授权
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED);
        }
        //4、从Redis中查询token是否存在
        Object token = caffeineCache.getIfPresent(username);
        if (Objects.isNull(token)) {
            logger.warn("【登录校验】缓存中查不到token");
            //未授权
            CookieUtil.setCookie(response, username, null, "/");
            CookieUtil.setCookie(response, "username", null, "/");
            throw new UnauthorizedException(HttpStatus.UNAUTHORIZED);

        }
        System.out.println(token);
    }
}
