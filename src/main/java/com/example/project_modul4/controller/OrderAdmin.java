package com.example.project_modul4.controller;

import com.example.project_modul4.model.dao.OrderDao;
import com.example.project_modul4.model.entity.Order;
import com.example.project_modul4.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class OrderAdmin {
    @Autowired
    private OrderService orderService;

    // Hiển thị danh sách order
    @RequestMapping("/order")
    public String allOrder(Model model){
        List<Order> orderList = orderService.getAll();
        model.addAttribute("orderList",orderList);

        return "admin/order/index";
    }

}
