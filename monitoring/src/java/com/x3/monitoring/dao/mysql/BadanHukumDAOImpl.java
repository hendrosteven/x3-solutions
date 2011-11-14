/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.dao.mysql;

import com.x3.monitoring.dao.BadanHukumDAO;
import com.x3.monitoring.entity.BadanHukum;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class BadanHukumDAOImpl implements BadanHukumDAO {

    private Connection conn;

    public BadanHukumDAOImpl(Connection conn) {
        this.conn = conn;
    }

    public void insert(BadanHukum bh) throws Exception {
        String sql = "INSERT INTO badan_hukum(nama) VALUES(?)";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setString(1, bh.getNama());
            ps.executeUpdate();
        } catch (Exception ex) {
            throw new Exception("Insert BadanHukum Gagal!!\nProblems:\n" + ex.getMessage());
        }
    }

    public void update(BadanHukum bh) throws Exception {
        String sql = "UPDATE badan_hukum SET nama=? WHERE id=?";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setString(1, bh.getNama());
            ps.setInt(2, bh.getId());
            ps.executeUpdate();
        } catch (Exception ex) {
            throw new Exception("Update BadanHukum Gagal!!\nProblems:\n" + ex.getMessage());
        }
    }

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM badan_hukum WHERE id=?";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception ex) {
            throw new Exception("Delete BadanHukum Gagal!!\nProblems:\n" + ex.getMessage());
        }
    }

    public BadanHukum get(int id) throws Exception {
        BadanHukum bh = null;
        String sql = "SELECT id,nama FROM badan_hukum WHERE id=?";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                bh = new BadanHukum();
                bh.setId(rs.getInt(1));
                bh.setNama(rs.getString(2));
            }
        } catch (Exception ex) {
            throw new Exception("Getting BadanHukum Gagal!!\nProblems:\n" + ex.getMessage());
        }
        return bh;
    }

    public List<BadanHukum> gets() throws Exception {
        List<BadanHukum> list = new ArrayList<BadanHukum>();
        String sql = "SELECT id,nama FROM badan_hukum";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BadanHukum bh = new BadanHukum();
                bh.setId(rs.getInt(1));
                bh.setNama(rs.getString(2));
                list.add(bh);
            }
        } catch (Exception ex) {
            throw new Exception("Getting BadanHukum Gagal!!\nProblems:\n" + ex.getMessage());
        }
        return list;
    }
}
