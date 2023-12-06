package com.example.project_modul4.model.dao;

import com.example.project_modul4.model.entity.User;
import com.example.project_modul4.util.ConnectionDatabase;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
@Repository
public class UserAdminDAOImpl implements UserAdminDAO{
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
    public User findById(Integer id) {
        Connection connection = null;
        connection = ConnectionDatabase.openConnection();
        User user = new User();
        try {
            CallableStatement callableStatement = connection.prepareCall("{CALL PRO_FIND_USER(?)}");
            callableStatement.setInt(1,id);
            ResultSet rs = callableStatement.executeQuery();

            while (rs.next()){
                user.setUserId(rs.getInt("userId"));
                user.setUserName(rs.getString("userName"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setAddress(rs.getString("address"));
                user.setPhone(rs.getString("phone"));
                user.setRole(rs.getByte("role"));
                user.setStatus(rs.getByte("status"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return user;
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public Boolean updateStatus(Integer id) {
        Connection connection = null;
        connection = ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("{CALL UpdateUserStatus(?)}");
            callableStatement.setInt(1,id);
            int check = callableStatement.executeUpdate();
            if(check > 0){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return false;
    }
}
