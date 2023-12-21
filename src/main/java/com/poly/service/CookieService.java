package com.poly.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * CookieService
 */
@Service
public class CookieService {
    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpServletResponse response;

    public Cookie get(String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equalsIgnoreCase(name))
                    ;
                return cookie;
            }
        }
        return null;
    }

    public String getValue(String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equalsIgnoreCase(name)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public Cookie add(String name, String value, int hours) {
        Cookie cookies = new Cookie(name, value);
        cookies.setPath("/");
        cookies.setMaxAge(hours * 60 * 60);
        response.addCookie(cookies);
        return cookies;
    }

    public void remove(String name) {
        Cookie cookies = new Cookie(name, null);
        cookies.setMaxAge(0);
        cookies.setSecure(true);
        cookies.setHttpOnly(true);
        cookies.setPath("/");
        response.addCookie(cookies);
    }

}