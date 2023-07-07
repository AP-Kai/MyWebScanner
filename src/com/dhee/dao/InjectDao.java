package com.dhee.dao;

import com.dhee.entity.InjectEntity;
import com.dhee.entity.UrlEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InjectDao extends DBConnection {
    InjectEntity sqliEntity = new InjectEntity();

    public ArrayList<InjectEntity> select() {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<InjectEntity> arrayList = new ArrayList<InjectEntity>();
        try {
            ps = con.prepareStatement("select * from SQLINJECT");
            rs = ps.executeQuery();
            while (rs.next()) {
                InjectEntity inject = new InjectEntity();
                inject.setId(rs.getString(1));  //取id
                inject.setSqlinject(rs.getString(2)); //取sql注入payload
                arrayList.add(inject);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(con);
        }

        return arrayList;
    }

    public List<UrlEntity> selectByUrl(UrlEntity urlEntity) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<UrlEntity> list = new ArrayList<UrlEntity>();
        try {
            ps = con.prepareStatement("SELECT * FROM URL WHERE URL=?");
            ps.setString(1, urlEntity.getUrl());
            rs = ps.executeQuery();
            while (rs.next()) {
                UrlEntity url = new UrlEntity();
                url.setId(rs.getString(1));
                url.setUrl(rs.getString(2));
                url.setSqli(rs.getString(3));
                list.add(url);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(con);
        }
        return list;

    }

    public void updateUrl(UrlEntity urlEntity) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        try {
            ps = con
                    .prepareStatement("UPDATE  URL SET SQLI= ? WHERE URL=?");
            ps.setString(1, urlEntity.getSqli());
            ps.setString(2, urlEntity.getUrl());
            ps.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            closeStatement(ps);
            closeConnection(con);
        }
    }

    public void insertUrl(UrlEntity urlEntity) {
        Connection con = getConnection();
        PreparedStatement ps = null;
        try {
            ps = con
                    .prepareStatement("INSERT INTO URL (URL,SQLI,XSS) VALUES (?,?,'0')");
            ps.setString(1, urlEntity.getUrl());
            ps.setString(2, urlEntity.getSqli());
            ps.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            closeStatement(ps);
            closeConnection(con);
        }
    }
}
