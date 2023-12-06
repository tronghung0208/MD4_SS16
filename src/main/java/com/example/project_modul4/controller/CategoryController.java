package com.example.project_modul4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CategoryController {
    @RequestMapping("/category")
    public String category(){
        return "/index";
    }

}
