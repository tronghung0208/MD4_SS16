package com.example.project_modul4.service;

import com.example.project_modul4.model.dao.CategoryDAO;
import com.example.project_modul4.model.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryDAO categoryDAO;

    @Override
    public List<Category> getAll() {
        return categoryDAO.getAll();
    }

    @Override
    public Boolean add(Category category) {
        return categoryDAO.add(category);
    }

    @Override
    public Boolean update(Category category, Integer id) {
        return categoryDAO.update(category,id);
    }

    @Override
    public Category findById(Integer id) {
        return categoryDAO.findById(id);
    }

    @Override
    public void delete(Integer id) {
categoryDAO.delete(id);
    }
}
