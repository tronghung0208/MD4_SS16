package com.example.project_modul4.model.dao;

import com.example.project_modul4.model.entity.Category;
import com.example.project_modul4.model.entity.Product;
import com.example.project_modul4.util.ConnectionDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Repository
public class ProductDAOImpl implements ProductDAO{
    @Autowired
    CategoryDAO categoryDAO;
    @Override
    public List<Product> getAll() {
        Connection connection=null;
        List<Product> productList=new ArrayList<>();
        try{
        connection= ConnectionDatabase.openConnection();
        CallableStatement callableStatement = connection.prepareCall("{CALL PROC_GET_ALL_PRODUCT()}");
        ResultSet rs = callableStatement.executeQuery();
        while (rs.next()) {
            Product product = new Product();
            product.setProductId(rs.getInt("productId"));
            product.setProductName(rs.getString("productName"));
            Category category = categoryDAO.findById(rs.getInt("categoryId"));
            product.setCategory(category);
            product.setImage(rs.getString("image"));
            product.setPrice(rs.getDouble("price"));
            product.setDescription(rs.getString("description"));
            product.setStock(rs.getInt("stock"));
            product.setStatus(rs.getBoolean("status"));
            productList.add(product);
        }
    } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
        ConnectionDatabase.closeConnection(connection);
    }
        return productList;

    }

    @Override
    public Boolean add(Product product) {
        Boolean isCheck = false;
        Connection connection = null;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{call PROC_CREATE_PRODUCT(?,?,?,?,?,?)}");
            callableStatement.setString(1,product.getProductName());
            callableStatement.setInt(2,product.getCategory().getCategoryId());
            callableStatement.setString(3,product.getImage());
            callableStatement.setDouble(4,product.getPrice());
            callableStatement.setString(5,product.getDescription());
            callableStatement.setInt(6, product.getStock());
            int check = callableStatement.executeUpdate();
            if (check > 0 ) {
                isCheck = true ;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return isCheck;
    }

    @Override
    public Boolean update(Product product, Integer id) {
        Boolean isCheck = false;
        Connection connection = null;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{call UPDATE_PRODUCT(?,?,?,?,?,?,?,?)}");
            callableStatement.setString(1,product.getProductName());
            callableStatement.setInt(2,product.getCategory().getCategoryId());
            callableStatement.setString(3,product.getImage());
            callableStatement.setDouble(4,product.getPrice());
            callableStatement.setString(5,product.getDescription());
            callableStatement.setInt(6, product.getStock());
            callableStatement.setBoolean(7,product.getStatus());
            callableStatement.setInt(8,id );
            int check = callableStatement.executeUpdate();
            if (check > 0 ) {
                isCheck = true ;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return isCheck;
    }

    @Override
    public Product findById(Integer id) {
        Connection connection = null ;
        Product product = new Product();
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement callableStatement = connection.prepareCall("{CALL PRODUCT_BY_ID(?)}");
            callableStatement.setInt(1,id);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                product.setProductId(rs.getInt("productId"));
                product.setProductName(rs.getString("productName"));
                Category category = categoryDAO.findById(rs.getInt("categoryId"));
                product.setCategory(category);
                product.setImage(rs.getString("image"));
                product.setPrice(rs.getDouble("price"));
                product.setDescription(rs.getString("description"));
                product.setStock(rs.getInt("stock"));
                product.setStatus(rs.getBoolean("status"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return product;
    }

    @Override
    public void delete(Integer id) {
        Connection connection=null;
        connection=ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement=connection.prepareCall("{CALL DELETE_PRODUCT(?)}");
            callableStatement.setInt(1, id);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }
}
