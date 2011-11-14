/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.dao.mysql;

import com.x3.monitoring.dao.BidangUsahaDAO;
import com.x3.monitoring.entity.BidangUsaha;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class BidangUsahaDAOImpl implements BidangUsahaDAO {

    private Connection conn;

    public BidangUsahaDAOImpl(Connection conn) {
        this.conn = conn;
    }

    public void insert(BidangUsaha bu) throws Exception {
        String sql = "INSERT INTO bidang_usaha(kbli,jenis,tahun_id) VALUES(?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setString(1, bu.getKbli());
            ps.setString(2, bu.getJenis());
            ps.setInt(3, bu.getTahun().getId());
            ps.execute();
        } catch (Exception ex) {
            throw new Exception("Insert BidangUsaha Gagal!!\nProblems:\n" + ex.getMessage());
        }
    }

    public void update(BidangUsaha bu) throws Exception {
        String sql = "UPDATE bidang_usaha SET kbli=?,jenis=? WHERE id=?";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setString(1, bu.getKbli());
            ps.setString(2, bu.getJenis());
            //ps.setInt(3, bu.getTahun().getId());
            ps.setInt(3, bu.getId());
            ps.execute();
        } catch (Exception ex) {
            throw new Exception("Update BidangUsaha Gagal!!\nProblems:\n" + ex.getMessage());
        }
    }

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM bidang_usaha WHERE id=?";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
        } catch (Exception ex) {
            throw new Exception("Delete BidangUsaha Gagal!!\nProblems:\n" + ex.getMessage());
        }
    }

    public BidangUsaha get(int id) throws Exception {
        BidangUsaha bu = null;
        String sql = "SELECT id,kbli,jenis FROM bidang_usaha WHERE id=?";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                bu = new BidangUsaha();
                bu.setId(rs.getInt(1));
                bu.setKbli(rs.getString(2));
                bu.setJenis(rs.getString(3));
            }
        } catch (Exception ex) {
            throw new Exception("Getting BidangUsaha Gagal!!\nProblems:\n" + ex.getMessage());
        }
        return bu;
    }

    public List<BidangUsaha> gets() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<BidangUsaha> gets(int tahun) throws Exception {
        List<BidangUsaha> list = new ArrayList<BidangUsaha>();
        String sql = "SELECT id,kbli,jenis FROM bidang_usaha WHERE tahun_id=?";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setInt(1, tahun);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BidangUsaha bu = new BidangUsaha();
                bu.setId(rs.getInt(1));
                bu.setKbli(rs.getString(2));
                bu.setJenis(rs.getString(3));
                list.add(bu);
            }
        } catch (Exception ex) {
            throw new Exception("Getting BidangUsaha Gagal!!\nProblems:\n" + ex.getMessage());
        }
        return list;
    }

    public List<BidangUsaha> gets(String key) throws Exception {
        List<BidangUsaha> list = new ArrayList<BidangUsaha>();
        String sql = "SELECT id,kbli,jenis FROM bidang_usaha WHERE jenis LIKE ?";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setString(1, "%" + key + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                BidangUsaha bu = new BidangUsaha();
                bu.setId(rs.getInt(1));
                bu.setKbli(rs.getString(2));
                bu.setJenis(rs.getString(3));
                list.add(bu);
            }
        } catch (Exception ex) {
            throw new Exception("Getting BidangUsaha Gagal!!\nProblems:\n" + ex.getMessage());
        }
        return list;
    }

    public BidangUsaha get(String kbli) throws Exception {
        BidangUsaha bu = null;
        String sql = "SELECT id,kbli,jenis FROM bidang_usaha WHERE kbli=?";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setString(1, kbli);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                bu = new BidangUsaha();
                bu.setId(rs.getInt(1));
                bu.setKbli(rs.getString(2));
                bu.setJenis(rs.getString(3));
            }
        } catch (Exception ex) {
            throw new Exception("Getting BidangUsaha Gagal!!\nProblems:\n" + ex.getMessage());
        }
        return bu;
    }
}
