package com.example.project_modul4.model.dao;

import com.example.project_modul4.dto.response.ResponseUserLoginDTO;
import com.example.project_modul4.model.entity.Order;
import com.example.project_modul4.model.entity.User;
import com.example.project_modul4.util.ConnectionDatabase;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
@Repository
public class OrderDAOImpl implements OrderDao{

 @Autowired
 private UserAdminDAO userAdminDAO;
    @Override
    public List<Order> getAll() {
        Connection connection = null;
        connection = ConnectionDatabase.openConnection();
        List<Order> orderList = new ArrayList<>();
        try {
            CallableStatement callableStatement = connection.prepareCall("{CALL GetAllOrders()}");
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()){
                Order order = new Order();
                order.setOrderId(rs.getInt("orderId"));

                User user = userAdminDAO.findById(rs.getInt("userId"));
                ModelMapper modelMapper = new ModelMapper();
                ResponseUserLoginDTO responseUserLoginDTO = modelMapper.map(user, ResponseUserLoginDTO.class);
                order.setUser(responseUserLoginDTO);
                order.setTotalAmount(rs.getDouble("totalAmount"));
                order.setOrderDate(rs.getDate("orderDate"));
                order.setName(rs.getString("name"));
                order.setEmail(rs.getString("email"));
                order.setPhone(rs.getString("phone"));
                order.setAddress(rs.getString("address"));
                order.setTotalPrice(rs.getDouble("totalPrice"));

                orderList.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }

        return orderList;
    }

    @Override
    public Boolean add(Order order) {

        Connection connection = null;
        connection = ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("{CALL PRO_InsertOrder(?,?,?,?,?,?,?)}");
            callableStatement.setInt(1,order.getUser().getUserId());
            callableStatement.setDouble(2,order.getTotalAmount());
            callableStatement.setString(3,order.getName());
            callableStatement.setString(4,order.getEmail());
            callableStatement.setString(5,order.getPhone());
            callableStatement.setString(6,order.getAddress());
            callableStatement.setDouble(7,order.getTotalPrice());

            int rs = callableStatement.executeUpdate();
            if(rs > 0){
                return true;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return false;
    }

    @Override
    public Boolean update(Order order, Integer integer) {
        return null;
    }

    @Override
    public Order findById(Integer integer) {
        return null;
    }

    @Override
    public void delete(Integer integer) {

    }
}
