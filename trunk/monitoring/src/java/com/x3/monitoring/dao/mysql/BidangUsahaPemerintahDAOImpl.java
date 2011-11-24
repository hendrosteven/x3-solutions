/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.monitoring.dao.mysql;

import com.x3.monitoring.dao.BidangUsahaPemerintahDAO;
import com.x3.monitoring.entity.BidangUsahaPemerintah;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class BidangUsahaPemerintahDAOImpl implements BidangUsahaPemerintahDAO{
    private Connection conn;
    public BidangUsahaPemerintahDAOImpl(Connection conn){
        this.conn = conn;
    }

    public void insert(BidangUsahaPemerintah obj) throws Exception {
        String sql = "INSERT INTO bidang_usaha_pemerintah(nama_bidang) VALUES(?)";
        PreparedStatement ps = null;
        try{
            ps = this.conn.prepareStatement(sql);
            ps.setString(1, obj.getNama());
            ps.executeUpdate();
        }catch(Exception ex){
            throw new Exception("Insert BidangUsahaPemerintah Gagal!!\nProblems:\n" + ex.getMessage());
        }
    }

    public void update(BidangUsahaPemerintah obj) throws Exception {
        String sql = "UPDATE bidang_usaha_pemerintah SET nama_bidang=? WHERE id=?";
        PreparedStatement ps = null;
        try{
            ps = this.conn.prepareStatement(sql);
            ps.setString(1, obj.getNama());
            ps.setInt(2, obj.getId());
            ps.execute();
        }catch(Exception ex){
            throw new Exception("Update BidangUsahaPemerintah Gagal!!\nProblems:\n" + ex.getMessage());
        }
    }

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM bidang_usaha_pemerintah WHERE id=?";
        PreparedStatement ps = null;
        try{
            ps = this.conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
        }catch(Exception ex){
            throw new Exception("Delete BidangUsahaPemerintah Gagal!!\nProblems:\n" + ex.getMessage());
        }
    }

    public BidangUsahaPemerintah get(int id) throws Exception {
        BidangUsahaPemerintah obj = null;
        String sql = "SELECT id,nama_bidang FROM bidang_usaha_pemerintah WHERE id=?";
        PreparedStatement ps = null;
        try{
            ps = this.conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                obj = new BidangUsahaPemerintah();
                obj.setId(rs.getInt(1));
                obj.setNama(rs.getString(2));
            }
        }catch(Exception ex){
            throw new Exception("Getting BidangUsahaPemerintah Gagal!!\nProblems:\n" + ex.getMessage());
        }
        return obj;
    }

    public List<BidangUsahaPemerintah> gets() throws Exception {
        List<BidangUsahaPemerintah> list = new ArrayList<BidangUsahaPemerintah>();
        String sql = "SELECT id,nama_bidang FROM bidang_usaha_pemerintah";
        PreparedStatement ps = null;
        try{
            ps = this.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                BidangUsahaPemerintah obj = new BidangUsahaPemerintah();
                obj.setId(rs.getInt(1));
                obj.setNama(rs.getString(2));
                list.add(obj);
            }
        }catch(Exception ex){
            throw new Exception("Getting BidangUsahaPemerintah Gagal!!\nProblems:\n" + ex.getMessage());
        }
        return list;
    }

}
