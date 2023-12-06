package com.example.project_modul4.model.entity;

import com.example.project_modul4.dto.response.ResponseUserLoginDTO;

import java.sql.Date;

public class Order {
    private int orderId;
    private ResponseUserLoginDTO user;
    private double totalAmount;
    private Date orderDate;
    private String name;
    private String email;
    private String phone;
    private String address;
    private double totalPrice;

    public Order() {
    }

    public Order(int orderId, ResponseUserLoginDTO user, double totalAmount, Date orderDate, String name, String email, String phone, String address, double totalPrice) {
        this.orderId = orderId;
        this.user = user;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.totalPrice = totalPrice;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public ResponseUserLoginDTO getUser() {
        return user;
    }

    public void setUser(ResponseUserLoginDTO user) {
        this.user = user;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
