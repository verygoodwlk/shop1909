package com.qf.aop;

import com.qf.entity.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;

@Component
@Aspect
public class LoginAop {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 增强方法
     *
     * 切点表达式 - 表示增强哪些指定的方法
     *
     * @return
     */
    @Around("@annotation(IsLogin)")
    public Object isLogin(ProceedingJoinPoint proceedingJoinPoint){

        //判断登录状态
        //1、获得cookie
        //2、通过cookie访问redis
        //3、拿到登录信息（要么为空、要么非空）
        // -> 为空（未登录）
        // -> 判断@IsLogin(mustLog = true) -> 强制跳转到登录页面

        // -> 非空（已登录）
        // -> 用户信息保存起来，让开发者能够在controller获得


        //null.xxx
        //获得登录的token
        String loginToken = null;
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("loginToken")) {
                    loginToken = cookie.getValue();
                    break;
                }
            }
        }

        //判断是否登录
        User user = null;
        if(loginToken != null){
            user = (User) redisTemplate.opsForValue().get(loginToken);
        }

        //判断是否登录
        if(user == null){
            //说明未登录
            //判断@IsLogin注解的方法返回值

            //获得@IsLogin注解
            MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
            Method method = methodSignature.getMethod();//代表增强方法的反射对象
            IsLogin isLogin = method.getAnnotation(IsLogin.class);
            boolean flag = isLogin.mustLogin();

            if(flag){
                //当前页面的url
                //http://localhost:80/a/b?name=xiaoming&key=value
                String returnUrl = request.getRequestURL().toString() + "?" + request.getQueryString();

                //编码url
                try {
                    returnUrl = URLEncoder.encode(returnUrl, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                //强制跳转到登录页面
                String loginUrl = "http://localhost:8082/sso/tologin?returnUrl=" + returnUrl;
                return "redirect:" + loginUrl;
            }
        }

        //如果登录user就不为空，如果不登录user就为空
        UserHolder.setUser(user);

        //指定调用目标方法
        Object result = null;
        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        //清空threadlocal
        UserHolder.setUser(null);

        return result;
    }
}
