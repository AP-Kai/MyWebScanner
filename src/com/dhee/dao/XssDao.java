package com.dhee.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dhee.entity.UrlEntity;
import com.dhee.entity.XssEntity;

public class XssDao extends DBConnection{
    public ArrayList<XssEntity> select() {
        Connection con = getConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        ArrayList<XssEntity> arrayList = new ArrayList<XssEntity>();
        try {
            ps=con.prepareStatement("SELECT * FROM XSS");
            rs= ps.executeQuery();
            while(rs.next()){
                XssEntity xss = new XssEntity();
                xss.setId(rs.getString(1));
                xss.setXssPayload(rs.getString(2));
                arrayList.add(xss);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(con);
        }
        return arrayList;
    }

    public ArrayList<UrlEntity> selectByUrl(UrlEntity UrlEntity) {
        Connection con = getConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        ArrayList<UrlEntity> arrayList = new ArrayList<UrlEntity>();
        try {
            ps = con.prepareStatement("SELECT * FROM URL WHERE URL=?");
            ps.setString(1, UrlEntity.getUrl());
            rs=ps.executeQuery();
            while(rs.next()){
                UrlEntity url = new UrlEntity();
                url.setUrl(rs.getString(2));
                url.setSqli(rs.getString(3));
                url.setXss(rs.getString(4));
                arrayList.add(url);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(con);
        }
        return arrayList;
    }

    public void updateUrl(UrlEntity UrlEntity) {
        Connection con = getConnection();
        PreparedStatement ps=null;
        try {
            ps = con.prepareStatement("UPDATE URL SET XSS= ? WHERE URL=?");
            ps.setString(1, UrlEntity.getXss());
            ps.setString(2, UrlEntity.getUrl());
            ps.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            closeStatement(ps);
            closeConnection(con);
        }

    }

    public void insertUrl(UrlEntity UrlEntity) {
        Connection con = getConnection();
        PreparedStatement ps=null;
        try {
            ps = con.prepareStatement("INSERT INTO URL(URL,SQLI,XSS) VALUES(?,0,?)");
            ps.setString(1, UrlEntity.getUrl());
            ps.setString(2, UrlEntity.getXss());
            ps.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            closeStatement(ps);
            closeConnection(con);
        }

    }



}
