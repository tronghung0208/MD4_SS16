package com.example.project_modul4.model.dao;

import com.example.project_modul4.model.entity.Category;
import com.example.project_modul4.util.ConnectionDatabase;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository

public class CategoryDAOImpl implements CategoryDAO {
    @Override
    public List<Category> getAll() {
        List<Category> categoryList = new ArrayList<>();
        Connection connection = null;
        connection = ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("{CALL PROC_GET_ALL_CATEGORY()}");
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                Category category = new Category();
                category.setCategoryId(rs.getInt("categoryId"));
                category.setCategoryName(rs.getString("categoryName"));
                category.setStatus(rs.getBoolean("status"));
                categoryList.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return categoryList;
    }

    @Override
    public Boolean add(Category category) {
        Connection connection = null;
        try {
            // Mở kết nối đến cơ sở dữ liệu
            connection = ConnectionDatabase.openConnection();
            // Tạo truy vấn SQL
            PreparedStatement callableStatement = connection.prepareStatement("{CALL PROC_CREATE_CATEGORY(?)}");
            // Thiết lập các giá trị tham số
            callableStatement.setString(1, category.getCategoryName());
            // Thực thi truy vấn
            int rowsAffected = callableStatement.executeUpdate();

            // Kiểm tra xem có bản ghi được thêm thành công không
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return false;
    }

    @Override
    public Boolean update(Category category, Integer id) {
        Boolean ischeck=false;
        Connection connection = null;
        connection = ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement=connection.prepareCall("{CALL UPDATE_CATEGORY(?,?,?)}");
            callableStatement.setInt(1,id);
            callableStatement.setString(2,category.getCategoryName());
            callableStatement.setBoolean(3,category.getStatus());
            int check =callableStatement.executeUpdate();
            if (check>0){
                ischeck=true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return ischeck;
    }

    @Override
    public Category findById(Integer id) {
        Category category = new Category();
        Connection connection = null;
        connection = ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("{CALL CATEGORY_BY_ID(?)}");
            callableStatement.setInt(1, id);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                category.setCategoryId(rs.getInt("categoryId"));
                category.setCategoryName(rs.getString("categoryName"));
                category.setStatus(rs.getBoolean("status"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return category;
    }

    @Override
    public void delete(Integer id) {
       Connection connection=null;
       connection=ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement=connection.prepareCall("{CALL DELETE_CATEGORY(?)}");
            callableStatement.setInt(1, id);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }
}
