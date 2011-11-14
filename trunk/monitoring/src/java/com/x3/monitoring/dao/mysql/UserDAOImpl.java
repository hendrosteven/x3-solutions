/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.dao.mysql;

import com.x3.monitoring.dao.UserDAO;
import com.x3.monitoring.entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class UserDAOImpl implements UserDAO {

    private Connection conn;

    public UserDAOImpl(Connection conn) {
        this.conn = conn;
    }

    public void insert(User user) throws Exception {
        String sql = "INSERT INTO user(username,password,nama,email,level) VALUES(?,?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getNama());
            ps.setString(4, user.getEmail());
            ps.setInt(5, user.getLevel());
            ps.executeUpdate();
        } catch (Exception ex) {
            throw new Exception("Insert User Gagal!!\nProblems:\n" + ex.getMessage());
        }
    }

    public void update(User user) throws Exception {
        String sql = "UPDATE user SET username=?,password=?,nama=?,email=?,level=? WHERE id=?";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getNama());
            ps.setString(4, user.getEmail());
            ps.setInt(5, user.getLevel());
            ps.setInt(6, user.getId());
            ps.executeUpdate();
        } catch (Exception ex) {
            throw new Exception("Update User Gagal!!\nProblems:\n" + ex.getMessage());
        }
    }

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM user WHERE id=?";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception ex) {
            throw new Exception("Delete User Gagal!!\nProblems:\n" + ex.getMessage());
        }
    }

    public User get(int id) throws Exception {
        User user = null;
        String sql = "SELECT id,username,password,nama,email,level FROM user WHERE id=?";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt(1));
                user.setUserName(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setNama(rs.getString(4));
                user.setEmail(rs.getString(5));
                user.setLevel(rs.getInt(6));
            }
        } catch (Exception ex) {
            throw new Exception("Getting User Gagal!!\nProblems:\n" + ex.getMessage());
        }
        return user;
    }

    public List<User> gets() throws Exception {
        List<User> list = new ArrayList<User>();
        String sql = "SELECT id,username,password,nama,email,level FROM user";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setUserName(rs.getString(2));
                user.setPassword(rs.getString(3));
                user.setNama(rs.getString(4));
                user.setEmail(rs.getString(5));
                user.setLevel(rs.getInt(6));
                list.add(user);
            }
        } catch (Exception ex) {
            throw new Exception("Getting User Gagal!!\nProblems:\n" + ex.getMessage());
        }
        return list;
    }

    public User login(String userName, String password) throws Exception {
        User user = null;
        String sql = "SELECT id,username,password,nama,email,level FROM user WHERE username=?";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getString(3).equals(password)) {
                    user = new User();
                    user.setId(rs.getInt(1));
                    user.setUserName(rs.getString(2));
                    user.setPassword(rs.getString(3));
                    user.setNama(rs.getString(4));
                    user.setEmail(rs.getString(5));
                    user.setLevel(rs.getInt(6));
                }
            }
        } catch (Exception ex) {
            throw new Exception("Login Gagal!!\nProblems:\n" + ex.getMessage());
        }
        return user;
    }
}
