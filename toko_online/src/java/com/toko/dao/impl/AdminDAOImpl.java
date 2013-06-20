/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toko.dao.impl;

import com.toko.dao.AdminDAO;
import com.toko.model.Admin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author user
 */
public class AdminDAOImpl implements AdminDAO{

    private Connection conn;
    public AdminDAOImpl(Connection conn){
        this.conn = conn;
    }
    
    @Override
    public Admin login(String user, String pass) throws Exception {
        Admin admin = null;
        String sql = "SELECT id,username,password "
                + "FROM admin WHERE username=? AND password=?";
        try{
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setString(1,user);
            ps.setString(2,pass);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                admin = new Admin(rs.getInt(1),rs.getString(2),rs.getString(3));
            }
        }catch(Exception ex){
            throw new Exception("Login fail : "+ex.getMessage());
        }
        return admin;
    }
    
}
