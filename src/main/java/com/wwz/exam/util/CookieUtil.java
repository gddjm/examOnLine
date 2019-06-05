package com.wwz.exam.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieUtil {
    public static String getCookieValue(HttpServletRequest request, String cookie1Name) {
        Cookie[] cookies =request.getCookies();
        if (cookies == null || cookies.length <= 0){
            return null;
        }
        for (Cookie cookie : cookies){
            if (cookie.getName().equals(cookie1Name)){
                return cookie.getValue();
            }
        }
        return null;
    }
}
