package com.example.project_modul4.model.dao;

import com.example.project_modul4.model.entity.User;

public interface UserAdminDAO extends IGenericDAO<User,Integer> {
    Boolean updateStatus(Integer id);
    User findById(Integer id);
}
