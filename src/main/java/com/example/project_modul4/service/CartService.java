package com.example.project_modul4.service;

import com.example.project_modul4.model.entity.CartItem;

public interface CartService extends IGenericService<CartItem,Integer> {
    public double total();
}
