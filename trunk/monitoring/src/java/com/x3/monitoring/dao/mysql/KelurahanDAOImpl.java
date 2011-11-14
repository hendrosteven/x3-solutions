/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.dao.mysql;

import com.x3.monitoring.dao.KelurahanDAO;
import com.x3.monitoring.entity.Kelurahan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class KelurahanDAOImpl implements KelurahanDAO {

    private Connection conn;

    public KelurahanDAOImpl(Connection conn) {
        this.conn = conn;
    }

    public Kelurahan get(int id) throws Exception {
        String sql = "SELECT * FROM kelurahan WHERE id=?";
        PreparedStatement ps = null;
        Kelurahan kel = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                kel = new Kelurahan();
                kel.setId(rs.getInt(1));
                kel.setNama(rs.getString(2));
            }
        } catch (Exception ex) {
            throw new Exception("Getting Kelurahan Gagal!!\nProblems:\n" + ex.getMessage());
        }
        return kel;
    }

    public List<Kelurahan> gets() throws Exception {
        String sql = "SELECT * FROM kelurahan";
        PreparedStatement ps = null;
        List<Kelurahan> list = new ArrayList<Kelurahan>();
        try {
            ps = this.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Kelurahan kel = new Kelurahan();
                kel.setId(rs.getInt(1));
                kel.setNama(rs.getString(2));
                list.add(kel);
            }
        } catch (Exception ex) {
            throw new Exception("Getting Kelurahan Gagal!!\nProblems:\n" + ex.getMessage());
        }
        return list;
    }

    public void insert(Kelurahan kel) throws Exception {
        String sql = "INSERT INTO kelurahan(nama) VALUES(?)";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setString(1, kel.getNama());
            ps.execute();
        } catch (Exception ex) {
            throw new Exception("Insert Kelurahan Gagal!!\nProblems:\n" + ex.getMessage());
        }
    }

    public void update(Kelurahan kel) throws Exception {
        String sql = "UPDATE kelurahan SET nama=? WHERE id=?";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setString(1, kel.getNama());
            ps.setInt(2, kel.getId());
            ps.execute();
        } catch (Exception ex) {
            throw new Exception("Update Kelurahan Gagal!!\nProblems:\n" + ex.getMessage());
        }
    }

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM kelurahan WHERE id=?";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
        } catch (Exception ex) {
            throw new Exception("Delete Kelurahan Gagal!!\nProblems:\n" + ex.getMessage());
        }
    }
}
