package com.example.project_modul4.service;

import com.example.project_modul4.dto.response.ResponseUserLoginDTO;
import com.example.project_modul4.model.dao.OrderDao;
import com.example.project_modul4.model.entity.CartItem;
import com.example.project_modul4.model.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    OrderDao orderDao;
    @Autowired
    HttpSession httpSession;
    private List<CartItem> cartItems = new ArrayList<>();
    @Override
    public List<Order> getAll() {
        return orderDao.getAll();
    }

    @Override
    public Boolean add(Order order) {
        cartItems = (List<CartItem>) httpSession.getAttribute("carts");
        ResponseUserLoginDTO user = (ResponseUserLoginDTO) httpSession.getAttribute("username");
        if(cartItems != null){
            if(user != null){
                double totalAmount = 0;
                double totalPrice = 0;
                for (CartItem cartItem: cartItems) {
                    totalAmount = totalAmount + cartItem.getQuantity();
                    totalPrice = totalPrice + (cartItem.getProduct().getPrice()*cartItem.getQuantity());
                }
                order.setTotalAmount(totalAmount);
                order.setUser(user);
                order.setTotalPrice(totalPrice);
                return orderDao.add(order);
            }
        }
        return false;
    }

    @Override
    public Boolean update(Order order, Integer integer) {
        return null;
    }

    @Override
    public Order findById(Integer integer) {
        return null;
    }

    @Override
    public void delete(Integer integer) {

    }
}
