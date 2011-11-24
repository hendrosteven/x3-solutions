/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.dao.mysql;

import com.x3.monitoring.dao.SumberDanaDAO;
import com.x3.monitoring.entity.SumberDana;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class SumberDanaDAOImpl implements SumberDanaDAO {

    private Connection conn;

    public SumberDanaDAOImpl(Connection conn) {
        this.conn = conn;
    }

    public void insert(SumberDana sd) throws Exception {
        String sql = "INSERT INTO sumber_dana(nama_sumber) VALUES(?)";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setString(1, sd.getNama());
            ps.executeUpdate();
        } catch (Exception ex) {
            throw new Exception("Insert SumberDana Gagal!!\nProblems:\n" + ex.getMessage());
        }
    }

    public void update(SumberDana sd) throws Exception {
        String sql = "UPDATE sumber_dana SET nama_sumber=? WHERE id=?";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setString(1, sd.getNama());
            ps.setInt(2, sd.getId());
            ps.executeUpdate();
        } catch (Exception ex) {
            throw new Exception("Update SumberDana Gagal!!\nProblems:\n" + ex.getMessage());
        }
    }

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM sumber_dana WHERE id=?";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception ex) {
            throw new Exception("Delete SumberDana Gagal!!\nProblems:\n" + ex.getMessage());
        }
    }

    public SumberDana get(int id) throws Exception {
        String sql = "SELECT id,nama_sumber FROM sumber_dana WHERE id=?";
        PreparedStatement ps = null;
        SumberDana dn = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                dn = new SumberDana();
                dn.setId(rs.getInt(1));
                dn.setNama(rs.getString(2));
            }
        } catch (Exception ex) {
            throw new Exception("Getting SumberDana Gagal!!\nProblems:\n" + ex.getMessage());
        }
        return dn;
    }

    public List<SumberDana> gets() throws Exception {
        List<SumberDana> list = new ArrayList<SumberDana>();
        String sql = "SELECT id,nama_sumber FROM sumber_dana";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SumberDana dn = new SumberDana();
                dn.setId(rs.getInt(1));
                dn.setNama(rs.getString(2));
                list.add(dn);
            }
        } catch (Exception ex) {
            throw new Exception("Getting SumberDana Gagal!!\nProblems:\n" + ex.getMessage());
        }
        return list;
    }
}
