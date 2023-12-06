package com.example.project_modul4.model.dao;

import java.util.List;

public interface IGenericDAO<T,ID> {
    List<T> getAll();
    Boolean add(T t);
    Boolean update(T t,ID id);
    T findById(ID id);
    void delete(ID id);
}
