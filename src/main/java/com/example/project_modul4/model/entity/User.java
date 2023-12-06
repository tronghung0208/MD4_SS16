package com.example.project_modul4.model.entity;

public class User {
    private Integer userId;
    private String userName;
    private String email;
    private String password;
    private String address;
    private String phone;
    private Byte role = 0;
    private Byte status = 0;

    public User() {
    }

    public User(Integer userId, String userName, String email, String password, String address, String phone, Byte role, Byte status) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.role = role;
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Byte getRole() {
        return role;
    }

    public void setRole(Byte role) {
        this.role = role;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}
