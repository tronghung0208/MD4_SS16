package com.example.project_modul4.controller;

import com.example.project_modul4.model.entity.CartItem;
import com.example.project_modul4.model.entity.Order;
import com.example.project_modul4.service.CartService;
import com.example.project_modul4.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CheckOutController {
    @Autowired
    CartService cartService;
    @Autowired
    OrderService orderService;
    @GetMapping("/check_out")
    public String checkOut(Model model){
        List<CartItem> cartItemList = cartService.getAll();
        model.addAttribute("cartItemList",cartItemList);
        Order order=new Order();
        model.addAttribute("oder",order);
        Double total=cartService.total();
        model.addAttribute("total",total);

        return "users/pages/check_out";
    }
    @PostMapping("/check_out")
    public String postCheckout(@ModelAttribute("order") Order order, HttpSession httpSession){

        if(orderService.add(order)){
            httpSession.removeAttribute("carts");
            return "redirect:/";
        }
        return "redirect:/checkout";
    }


}
