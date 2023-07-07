package com.dhee.dao;

import com.dhee.entity.UrlEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UrlDao extends DBConnection{

    public boolean add(ArrayList<UrlEntity> urlEntities) {
        Connection connection = null;
        PreparedStatement ps = null;
        try {
            connection = this.getConnection();
            for (UrlEntity urlEntity : urlEntities) {
                String sql = "insert into url(id,url,sqli,xss) values(?,?,0,0)";
                ps = connection.prepareStatement(sql);
                ps.setString(1, urlEntity.getId());
                ps.setString(2, urlEntity.getUrl());
                ps.executeUpdate();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            closeConnection(connection);
            closePreparedStatement(ps);
        }
    }


}
