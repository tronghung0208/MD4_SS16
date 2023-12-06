package com.example.project_modul4.service;

import com.example.project_modul4.dto.response.ResponseUserLoginDTO;
import com.example.project_modul4.dto.request.UserLoginDTO;
import com.example.project_modul4.dto.request.UserRegisterDTO;
import com.example.project_modul4.model.dao.UserDAO;
import com.example.project_modul4.model.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class UserServiceIpml implements UserService{
    @Autowired
    private UserDAO userDAO;
    @Override
    public Boolean register(UserRegisterDTO userRegisterDTO) {
        ModelMapper modelMapper = new ModelMapper();
        User user = modelMapper.map(userRegisterDTO, User.class);
        return userDAO.register(user);
    }

    @Override
    public ResponseUserLoginDTO login(UserLoginDTO user) {
        ModelMapper modelMapper = new ModelMapper();
        User user1 = userDAO.login(user);
        if(user1 != null){
            ResponseUserLoginDTO rpDTO = modelMapper.map(user1, ResponseUserLoginDTO.class);
            return rpDTO;
        }
        return null;
    }

    @Override
    public ResponseUserLoginDTO loginAdmin(UserLoginDTO user) {
        ResponseUserLoginDTO userAdmin = login(user);
        if(userAdmin != null){
            if(userAdmin.getRole() == 1){
                return userAdmin;
            }
        }

        return null;
    }

    @Override
    public List<ResponseUserLoginDTO> listUser() {
        ModelMapper modelMapper = new ModelMapper();
        List<User> userList = userDAO.listUser();

        if(userList != null && !userList.isEmpty()){
            List<ResponseUserLoginDTO> list = new ArrayList<>();

            for (User user : userList) {
                list.add(modelMapper.map(user, ResponseUserLoginDTO.class));
            }
            return list;
        }
        return null;
    }

    @Override
    public List<String> uniquelist(String string) {
        return userDAO.uniquelist(string);
    }
}
