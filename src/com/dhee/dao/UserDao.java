package com.dhee.dao;

import com.dhee.entity.UserEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDao extends DBConnection{

    public  ArrayList<UserEntity>  select(){
        Connection connection = this.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<UserEntity> userList = new ArrayList<UserEntity>();
        try {
            ps = connection.prepareStatement("SELECT ID,USERNAME,PASSWORD,REALNAME FROM USER");
            rs = ps.executeQuery();
            while(rs.next()){
                UserEntity userEntity = new UserEntity();
                userEntity.setId(rs.getInt(1));
                userEntity.setUserName(rs.getString(2));
                userEntity.setPassword(rs.getString(3));
                userEntity.setRealName(rs.getString(4));
                userList.add(userEntity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
}
