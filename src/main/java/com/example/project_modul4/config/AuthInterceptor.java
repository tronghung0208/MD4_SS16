package com.example.project_modul4.config;

import com.example.project_modul4.dto.response.ResponseUserLoginDTO;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession httpSession = request.getSession();
        ResponseUserLoginDTO admin = (ResponseUserLoginDTO) httpSession.getAttribute("admin");
        if (admin != null){
            return true;
        }
        response.sendRedirect("/lg-admin");
        return false;
    }
}
