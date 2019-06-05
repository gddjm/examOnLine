package com.wwz.exam.interceptor;


import com.wwz.exam.annotation.AdminIntercept;
import com.wwz.exam.domain.Admin;
import com.wwz.exam.service.AdminService;
import com.wwz.exam.util.CookieUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class AdminLoginInterceptor implements HandlerInterceptor {
    @Autowired
    AdminService adminService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        //判断如果不是请求control方法直接返回true
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod method = (HandlerMethod) handler;
        //判断访问的control是否添加LoginRequired注解
        AdminIntercept loginRequired = method.getMethodAnnotation(AdminIntercept.class);
        if (loginRequired != null) {
            if (!isLogin(request, response)) {
                response.sendRedirect("/user/login_tip");
                return false;
            }
            return true;
        } else {
            return true;
        }
    }

    private boolean isLogin(HttpServletRequest request, HttpServletResponse response) {
        String paramToken = request.getParameter(AdminService.COOKI_NAME_TOKEN);
        String cookieToken = CookieUtil.getCookieValue(request, AdminService.COOKI_NAME_TOKEN);

        if (StringUtils.isEmpty(paramToken) && StringUtils.isEmpty(cookieToken)) {
            return false;
        }
        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;

        Admin admin = adminService.getByToken(response, token);
        if (admin == null) {
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object
            o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse
            httpServletResponse, Object o, Exception e) throws Exception {

    }


}