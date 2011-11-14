/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.dao.mysql;

import com.x3.monitoring.dao.PotensiInvestasiDAO;
import com.x3.monitoring.entity.PotensiInvestasi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Hendro Steven
 */
public class PotensiInvestasiDAOImpl implements PotensiInvestasiDAO {

    private Connection conn;

    public PotensiInvestasiDAOImpl(Connection conn) {
        this.conn = conn;
    }

    public void insert(PotensiInvestasi pi) throws Exception {
        String sql = "INSERT INTO potensi(str) VALUES(?)";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setString(1, pi.getText());
            ps.execute();
        } catch (Exception ex) {
            throw new Exception("Insert PotensiInvestasi Gagal!!\nProblems:\n" + ex.getMessage());

        }

    }

    public void update(PotensiInvestasi pi) throws Exception {
        String sql = "UPDATE potensi SET str=?";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setString(1, pi.getText());
            ps.execute();
        } catch (Exception ex) {
            throw new Exception("Update PotensiInvestasi Gagal!!\nProblems:\n" + ex.getMessage());

        }
    }

    public PotensiInvestasi get() throws Exception {
        PotensiInvestasi pi = null;
        String sql = "SELECT id,str FROM potensi";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                pi = new PotensiInvestasi();
                pi.setId(rs.getInt(1));
                pi.setText(rs.getString(2));
            }
        } catch (Exception ex) {
            throw new Exception("Getting PotensiInvestasi Gagal!!\nProblems:\n" + ex.getMessage());

        }
        return pi;
    }
}
