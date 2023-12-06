package com.example.project_modul4.service;

import com.example.project_modul4.model.dao.UserAdminDAO;
import com.example.project_modul4.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserAdminServiceImpl implements UserAdminService{
    @Autowired
    private UserAdminDAO userAdminDAO;
    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public Boolean add(User user) {
        return null;
    }

    @Override
    public Boolean update(User user, Integer integer) {
        return null;
    }

    @Override
    public User findById(Integer integer) {
        return null;
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public Boolean updateStatus(Integer id) {

        return userAdminDAO.updateStatus(id);
    }
}
