package com.example.project_modul4.controller;

import com.example.project_modul4.dto.response.RespProductDTO;
import com.example.project_modul4.dto.response.ResponseUserLoginDTO;
import com.example.project_modul4.dto.request.UserLoginDTO;
import com.example.project_modul4.dto.request.UserRegisterDTO;
import com.example.project_modul4.service.ProductService;
import com.example.project_modul4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ControllerUsers {
    @Autowired
    private UserService userService;
    @Autowired
    ProductService productService;

    @GetMapping("/login")
    public String login(@CookieValue(required = false, name = "email") String emailCookie, Model model) {
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        if (emailCookie != null) {
            userLoginDTO.setEmail(emailCookie);
            model.addAttribute("checked", true);
        }
        model.addAttribute("userLoginDTO", userLoginDTO);
        return "users/pages/login";
    }

    @PostMapping("/login")
    public String postLogin(@ModelAttribute("userLoginDTO") UserLoginDTO userLoginDTO,
                            HttpSession httpSession,
                            @RequestParam(required = false, name = "checked") Boolean isCheck,
                            HttpServletResponse response,
                            HttpServletRequest request) {
        ResponseUserLoginDTO user2 = userService.login(userLoginDTO);
        if(isCheck != null && isCheck){
            // khởi tạo cookie
            Cookie cookieEmail = new Cookie("email",userLoginDTO.getEmail());
            // sét thời gian tồn tại cookei.
            cookieEmail.setMaxAge(72*60*60);
            response.addCookie(cookieEmail);
        }else {
            Cookie[] cookie = request.getCookies();
            if(cookie != null){
                for (int i = 0; i < cookie.length ; i++) {
                    if(cookie[i].getName().equals("email")){
                        cookie[i].setMaxAge(0);
                        // Đặt lại giá trị của cookie trong response để nó được gửi về client và xóa đi
                        response.addCookie(cookie[i]);
                        break;
                    }
                }
            }

        }

        if(user2 != null){
            httpSession.setAttribute("username", user2);
            return "redirect:/";
        }
//        redirectAttributes.addFlashAttribute("err","Sai thông tin đăng nhập");
        return  "redirect:/login";
    }


    @GetMapping("/register")
    public String register(Model model) {
        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        model.addAttribute("userRegisterDTO", userRegisterDTO);
        return "users/pages/register";
    }

    @PostMapping("/register")
    public String postRegister(@Valid @ModelAttribute("userRegisterDTO") UserRegisterDTO userRegisterDTO,
                               BindingResult result) {
        if (!result.hasErrors()) {
            if (userService.register(userRegisterDTO)) {
                return ("redirect:/login");
            }
        }
        return "users/pages/register";
    }


    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("username");
        return "redirect:/";
    }


    @GetMapping("product-home")
    public String allProduct(Model model) {
        List<RespProductDTO> list = productService.getAll();
        model.addAttribute("list",list);
        return "users/pages/product";
    }

    @GetMapping("product-detail/{id}")
    public String productDetail(@PathVariable("id") Integer id, Model model){
        List<RespProductDTO> list = productService.getAll();
        RespProductDTO product = list.stream()
                .filter(p -> id.equals(p.getProductId()))
                .findFirst()
                .orElse(null);
        model.addAttribute("product",product);
        return "users/pages/product_detail";
    }


}
