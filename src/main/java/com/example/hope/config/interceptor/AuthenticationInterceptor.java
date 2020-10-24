package com.example.hope.config.interceptor;

import com.example.hope.annotation.Admin;
import com.example.hope.annotation.PassToken;
import com.example.hope.annotation.UserLoginToken;
import com.example.hope.common.utils.JwtUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("Authorization");

        // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();

        if(method.isAnnotationPresent(PassToken.class)){
            return true;
        }

        if(method.isAnnotationPresent(UserLoginToken.class)){

            if(token == null){
                throw new RuntimeException("无token,请重新登陆");
            }

            JwtUtils.parseJWT(token);

            return true;
        }

        if(method.isAnnotationPresent(Admin.class)){

            if(token == null){
                throw new RuntimeException("无token,请重新登陆");
            }

            if(!JwtUtils.is_admin(token)){
                throw new RuntimeException("权限不够");
            }

            JwtUtils.parseJWT(token);

            return true;

        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
