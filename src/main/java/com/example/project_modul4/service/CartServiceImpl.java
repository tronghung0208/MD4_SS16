package com.example.project_modul4.service;

import com.example.project_modul4.model.entity.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    HttpSession httpSession;
    private List<CartItem> cartItems = new ArrayList<>();


    @Override
    public List<CartItem> getAll() {
        cartItems = (List<CartItem>) httpSession.getAttribute("carts");
        return cartItems != null ? cartItems : new ArrayList<>();
    }


    @Override
    public Boolean add(CartItem cartItem) {
        cartItems = getAll();
        CartItem cartItem1 = checkProduct(cartItem.getProduct().getProductId(), cartItems);
        if (cartItem1 == null) {
            cartItems.add(cartItem);
        } else {
            cartItem1.setQuantity(cartItem1.getQuantity() + cartItem.getQuantity());
        }
        httpSession.setAttribute("carts", cartItems);
        return true;
    }

    @Override
    public Boolean update(CartItem cartItem, Integer id) {
        cartItems = (List<CartItem>) httpSession.getAttribute("carts");
        CartItem cartItem1 = checkProduct(id, cartItems);
        if (cartItem1 == null) {
            cartItems.add(cartItem);
        } else {
            cartItem1.setQuantity(cartItem.getQuantity());
        }
        httpSession.setAttribute("carts", cartItems);
        return true;
    }

    @Override
    public CartItem findById(Integer id) {

        return null;
    }

    @Override
    public void delete(Integer productId) {
        cartItems = (List<CartItem>) httpSession.getAttribute("carts");
        cartItems.removeIf(item -> item.getProduct().getProductId().equals(productId));
        httpSession.setAttribute("carts", cartItems);
    }


    public CartItem checkProduct(Integer productId, List<CartItem> cartItems) {
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProduct().getProductId().equals(productId)) {
                return cartItem;
            }
        }
        return null;
    }


    @Override
    public double total() {
        cartItems = (List<CartItem>) httpSession.getAttribute("carts");
        double total = 0;

        if (cartItems != null) {
            for (CartItem c : cartItems
            ) {
                total += c.getQuantity() * c.getProduct().getPrice();
            }
        }
        return total;
    }

    public void clearCart() {
        httpSession.removeAttribute("carts");
    }
}
