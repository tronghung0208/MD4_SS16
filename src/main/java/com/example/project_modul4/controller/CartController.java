package com.example.project_modul4.controller;

import com.example.project_modul4.dto.response.RespProductDTO;
import com.example.project_modul4.dto.response.ResponseUserLoginDTO;
import com.example.project_modul4.model.entity.CartItem;
import com.example.project_modul4.model.entity.Product;
import com.example.project_modul4.model.entity.User;
import com.example.project_modul4.service.CartService;
import com.example.project_modul4.service.CartServiceImpl;
import com.example.project_modul4.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CartController {
    @Autowired
    ProductService productService;
    @Autowired
    CartService cartService;
    @Autowired
    CartServiceImpl cartServiceImpl;
    ResponseUserLoginDTO loginUser = new ResponseUserLoginDTO();

    private ModelMapper modelMapper = new ModelMapper();

    @GetMapping("/cart")
    public String cart(Model model) {
        List<CartItem> cartItemList = cartService.getAll();
        model.addAttribute("cartItemList", cartItemList);

        double total = cartServiceImpl.total();

        if (total != 0) {
            model.addAttribute("total", total);
        }

        return "users/pages/cart";
    }

    @PostMapping("/cart")
    public String postCart(@RequestParam("productId") Integer productId, @RequestParam("quantity") Integer quantity, HttpSession httpSession) {
        loginUser = (ResponseUserLoginDTO) httpSession.getAttribute("username");
        if (loginUser == null) {
            return "redirect:/login";
        }
        RespProductDTO product = productService.findById(productId);
        CartItem cartItem = new CartItem();
        cartItem.setProduct(modelMapper.map(product, Product.class));
        cartItem.setQuantity(quantity);
        cartService.add(cartItem);
        System.out.println(productId);
        return "redirect:/cart";
    }

    @PostMapping("/update-cart")
    public String updateCart(@RequestParam("productId") Integer productId,
                             @RequestParam("quantity") Integer quantity,
                             HttpSession session) {
        CartItem cartItem = new CartItem();
        RespProductDTO product = productService.findById(productId); // Thay thế bằng phương thức lấy sản phẩm từ service
        cartItem.setProduct(modelMapper.map(product, Product.class));
        cartItem.setQuantity(quantity);
        cartService.update(cartItem, productId);
        // Lưu giỏ hàng vào session
        session.setAttribute("carts", cartService.getAll());
        System.out.println(cartServiceImpl.total());
        return "redirect:/cart";
    }

    @GetMapping("/delete-cart/{productId}")
    public String deleteCartItem(@PathVariable("productId") Integer productId, HttpSession session) {

        // Xóa sản phẩm có productId khỏi giỏ hàng
        cartService.delete(productId);

        // Lưu giỏ hàng vào session

        // Chuyển hướng về trang giỏ hàng
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model) {
        // Xóa toàn bộ giỏ hàng (Cách xóa tùy thuộc vào cơ sở dữ liệu hoặc cách bạn lưu trữ giỏ hàng)
       cartServiceImpl.clearCart();
        // Redirect hoặc hiển thị thông báo thành công
        return "redirect:/";
    }

}
