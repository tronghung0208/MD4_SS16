package com.example.project_modul4.service;

import java.util.List;

public interface IGenericService<T,ID> {
    List<T> getAll();
    Boolean add(T t);
    Boolean update(T t,ID id);
    T findById(ID id);
    void delete(ID id);

}
