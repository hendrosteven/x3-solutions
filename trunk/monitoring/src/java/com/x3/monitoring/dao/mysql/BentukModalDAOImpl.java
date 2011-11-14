/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.monitoring.dao.mysql;

import com.x3.monitoring.dao.BentukModalDAO;
import com.x3.monitoring.entity.BentukModal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class BentukModalDAOImpl implements BentukModalDAO{
    private Connection conn;
    public BentukModalDAOImpl(Connection conn){
        this.conn = conn;
    }

    public void insert(BentukModal bm) throws Exception {
        String sql = "INSERT INTO bentuk_modal(nama) VALUES(?)";
        PreparedStatement ps = null;
        try{
            ps = this.conn.prepareStatement(sql);
            ps.setString(1, bm.getNama());
            ps.executeUpdate();
        }catch(Exception ex){
            throw new Exception("Insert BentukModal Gagal!!\nProblems:\n" + ex.getMessage());
        }
    }

    public void update(BentukModal bm) throws Exception {
        String sql = "UPDATE bentuk_modal SET nama=? WHERE id=?";
        PreparedStatement ps = null;
        try{
            ps = this.conn.prepareStatement(sql);
            ps.setString(1, bm.getNama());
            ps.setInt(2, bm.getId());
            ps.executeUpdate();
        }catch(Exception ex){
            throw new Exception("Update BentukModal Gagal!!\nProblems:\n" + ex.getMessage());
        }
    }

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM bentuk_modal WHERE id=?";
        PreparedStatement ps = null;
        try{
            ps = this.conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        }catch(Exception ex){
            throw new Exception("Delete BentukModal Gagal!!\nProblems:\n" + ex.getMessage());
        }
    }

    public BentukModal get(int id) throws Exception {
        BentukModal bm = null;
        String sql = "SELECT id,nama FROM bentuk_modal WHERE id=?";
        PreparedStatement ps = null;
        try{
            ps = this.conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                bm = new BentukModal();
                bm.setId(rs.getInt(1));
                bm.setNama(rs.getString(2));
            }
        }catch(Exception ex){
            throw new Exception("Getting BentukModal Gagal!!\nProblems:\n" + ex.getMessage());
        }
        return bm;
    }

    public List<BentukModal> gets() throws Exception {
        List<BentukModal> list = new ArrayList<BentukModal>();
        String sql = "SELECT id,nama FROM bentuk_modal";
        PreparedStatement ps = null;
        try{
            ps = this.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                BentukModal bm = new BentukModal();
                bm.setId(rs.getInt(1));
                bm.setNama(rs.getString(2));
                list.add(bm);
            }
        }catch(Exception ex){
            throw new Exception("Getting BentukModal Gagal!!\nProblems:\n" + ex.getMessage());
        }
        return list;
    }

}
