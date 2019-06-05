package com.wwz.exam.config;

import com.wwz.exam.interceptor.AdminLoginInterceptor;
import com.wwz.exam.interceptor.StudentLoginInterceptor;
import com.wwz.exam.interceptor.TeacherLoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class LoginConfiguration extends WebMvcConfigurerAdapter {
    @Autowired
    AdminLoginInterceptor adminLoginInterceptor;
    @Autowired
    StudentLoginInterceptor studentLoginInterceptor;
    @Autowired
    TeacherLoginInterceptor teacherLoginInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminLoginInterceptor);
        registry.addInterceptor(studentLoginInterceptor);
        registry.addInterceptor(teacherLoginInterceptor);

    }

}