package com.spring.mvc.interceptor;

import com.spring.mvc.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@Slf4j
public class AfterLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 로그인을 했으면 로그인창 및 회원가입 창에 못들어가게 할 것임
        if (LoginUtil.isLogin(request.getSession())) {
            log.info("request denied : ( {} )", request.getRequestURI());
            response.sendRedirect("/");
            return false;
        }

        return true;

    }
}
