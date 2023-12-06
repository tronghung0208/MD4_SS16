package com.example.project_modul4.model.dao;

import com.example.project_modul4.dto.request.UserLoginDTO;
import com.example.project_modul4.model.entity.User;

import java.util.List;

public interface UserDAO {
    Boolean register(User user);
    User login(UserLoginDTO userLoginDTO);

    List<User> listUser();
    List<String> uniquelist(String string);
}
