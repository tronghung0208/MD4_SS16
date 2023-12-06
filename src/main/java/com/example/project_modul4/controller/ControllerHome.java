package com.example.project_modul4.controller;

import com.example.project_modul4.dto.response.RespProductDTO;
import com.example.project_modul4.dto.response.ResponseUserLoginDTO;
import com.example.project_modul4.model.entity.CartItem;
import com.example.project_modul4.model.entity.Product;
import com.example.project_modul4.service.CartService;
import com.example.project_modul4.service.CategoryService;
import com.example.project_modul4.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@PropertySource("classpath:config.properties")
public class ControllerHome {
    @Value("${path}")
    private String pathUpload;
    @Autowired
    ProductService productService;
    @Autowired
    CartService cartService;
    @Autowired
    CategoryService categoryService;
    ResponseUserLoginDTO loginUser = new ResponseUserLoginDTO();
    private ModelMapper modelMapper = new ModelMapper();
    List<CartItem> cartItems=new ArrayList<>();
    @RequestMapping("/")
    public String home(Model model){
        List<RespProductDTO> list = productService.getAll();
        List<RespProductDTO> listProductTrending = (List<RespProductDTO>) list.stream()
                .filter(product -> 1==product.getCategory().getCategoryId())
                .collect(Collectors.toList());
        model.addAttribute("listProductTrending",listProductTrending);

        List<RespProductDTO> listProductFeatured = (List<RespProductDTO>) list.stream()
                .filter(product -> 2==product.getCategory().getCategoryId())
                .collect(Collectors.toList());
        model.addAttribute("listProductFeatured",listProductFeatured);

        List<RespProductDTO> listProductBestSale = (List<RespProductDTO>) list.stream()
                .filter(product -> 3==product.getCategory().getCategoryId())
                .collect(Collectors.toList());
        model.addAttribute("listProductBestSale",listProductBestSale);

        return "users/pages/home";
    }
    @PostMapping("/cart-home")
    public String postCartHome(@RequestParam("productId") Integer productId, @RequestParam("quantity") Integer quantity, HttpSession httpSession) {
        loginUser = (ResponseUserLoginDTO) httpSession.getAttribute("username");
        if (loginUser == null) {
            return "redirect:/login";
        }
        RespProductDTO product = productService.findById(productId);
        CartItem cartItem = new CartItem();
        cartItem.setProduct(modelMapper.map(product, Product.class));
        cartItem.setQuantity(1);
        cartService.add(cartItem);
        System.out.println(productId);
        return "redirect:/cart";
    }

}
