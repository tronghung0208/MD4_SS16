package com.example.project_modul4.service;

import com.example.project_modul4.model.entity.User;

public interface UserAdminService extends IGenericService<User,Integer> {

    Boolean updateStatus(Integer id);
}
