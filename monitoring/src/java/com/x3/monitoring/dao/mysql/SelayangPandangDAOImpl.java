/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.dao.mysql;

import com.x3.monitoring.dao.SelayangPandangDAO;
import com.x3.monitoring.entity.SelayangPandang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Hendro Steven
 */
public class SelayangPandangDAOImpl implements SelayangPandangDAO {

    private Connection conn;

    public SelayangPandangDAOImpl(Connection conn) {
        this.conn = conn;
    }

    public void insert(SelayangPandang sp) throws Exception {
        String sql = "INSERT INTO selayang_pandang(str) VALUES(?)";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setString(1, sp.getText());
            ps.execute();
        } catch (Exception ex) {
            throw new Exception("Insert SelayangPandang Gagal!!\nProblems:\n" + ex.getMessage());

        }

    }

    public void update(SelayangPandang sp) throws Exception {
        String sql = "UPDATE selayang_pandang SET str=?";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setString(1, sp.getText());
            ps.execute();
        } catch (Exception ex) {
            throw new Exception("Update SelayangPandang Gagal!!\nProblems:\n" + ex.getMessage());

        }
    }

    public SelayangPandang get() throws Exception {
        SelayangPandang sp = null;
        String sql = "SELECT id,str FROM selayang_pandang";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                sp = new SelayangPandang();
                sp.setId(rs.getInt(1));
                sp.setText(rs.getString(2));                
            }
        } catch (Exception ex) {
            throw new Exception("Getting SelayangPandang Gagal!!\nProblems:\n" + ex.getMessage());

        }
        return sp;
    }
}
