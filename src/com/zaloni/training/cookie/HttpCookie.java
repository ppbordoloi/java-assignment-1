package com.zaloni.training.cookie;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpCookie {
    public static void login(HttpServletResponse response) {
        Cookie cookie = new Cookie("logged_in", "yes");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }
    
    public static void logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("logged_in", "no");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    public static boolean isLoggedIn(HttpServletRequest request, HttpServletResponse response, boolean redirect) throws IOException {
        Cookie[] cookies = request.getCookies();
        for (Cookie c: cookies) {
            if (c.getName().equalsIgnoreCase("logged_in") && c.getValue().equalsIgnoreCase("yes")) {
                return true;
            }
        }
        if (redirect) {
            response.sendRedirect(request.getContextPath() + "/login");
        }
        return false;
    }
}
