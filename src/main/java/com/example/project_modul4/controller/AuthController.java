package com.example.project_modul4.controller;

import com.example.project_modul4.dto.response.ResponseUserLoginDTO;
import com.example.project_modul4.dto.request.UserLoginDTO;
import com.example.project_modul4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("")
public class AuthController {
    @Autowired
    private UserService userService;
    @GetMapping("/lg-admin")
    public String lgAdmin(Model model){
        UserLoginDTO user = new UserLoginDTO();
        model.addAttribute("user", user);
        return "admin/login_admin";
    }

    @PostMapping("/lg-admin")
    public String postLogon(@ModelAttribute("user") UserLoginDTO user, HttpSession httpSession){
        ResponseUserLoginDTO userAdmin = userService.loginAdmin(user);
        if(userAdmin != null){
            httpSession.setAttribute("admin",userAdmin);
            return "redirect:/admin";
        }
        return "redirect:/lg-admin";
    }


    @GetMapping("/logoutadmin")
    public String logout(HttpSession session){
        session.removeAttribute("admin");
        return "redirect:/";
    }
}
