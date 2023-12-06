package com.example.project_modul4.service;

import com.example.project_modul4.dto.response.ResponseUserLoginDTO;
import com.example.project_modul4.dto.request.UserLoginDTO;
import com.example.project_modul4.dto.request.UserRegisterDTO;

import java.util.List;

public interface UserService {

    Boolean register(UserRegisterDTO user);
    ResponseUserLoginDTO login(UserLoginDTO user);

    ResponseUserLoginDTO loginAdmin(UserLoginDTO user);

    List<ResponseUserLoginDTO> listUser();
    List<String> uniquelist(String string);


}
