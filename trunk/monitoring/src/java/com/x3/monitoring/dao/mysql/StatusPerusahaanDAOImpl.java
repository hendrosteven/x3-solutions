/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.monitoring.dao.mysql;

import com.x3.monitoring.dao.StatusPerusahaanDAO;
import com.x3.monitoring.entity.StatusPerusahaan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class StatusPerusahaanDAOImpl implements StatusPerusahaanDAO{
    private Connection conn;

    public StatusPerusahaanDAOImpl(Connection conn){
        this.conn = conn;
    }

    public void insert(StatusPerusahaan sp) throws Exception {
        String sql = "INSERT INTO status_perusahaan(nama) VALUES(?)";
        PreparedStatement ps = null;
        try{
            ps = this.conn.prepareStatement(sql);
            ps.setString(1, sp.getNama());
            ps.execute();
        }catch(Exception ex){
            throw new Exception("INSERT StatusPerusahaan Gagal!!\nProblems:\n" + ex.getMessage());
        }
    }

    public void update(StatusPerusahaan sp) throws Exception {
        String sql = "UPDATE status_perusahaan SET nama=? WHERE id=?";
        PreparedStatement ps = null;
        try{
            ps = this.conn.prepareStatement(sql);
            ps.setString(1, sp.getNama());
            ps.setInt(2, sp.getId());
            ps.execute();
        }catch(Exception ex){
             throw new Exception("UPDATE StatusPerusahaan Gagal!!\nProblems:\n"+ex.getMessage());
        }
    }

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM status_perusahaan WHERE id=?";
        PreparedStatement ps = null;
        try{
            ps = this.conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
        }catch(Exception ex){
             throw new Exception("DELETE StatusPerusahaan Gagal!!\nProblems:\n"+ex.getMessage());
        }
    }

    public StatusPerusahaan get(int id) throws Exception {
        String sql = "SELECT id,nama FROM status_perusahaan WHERE id=?";
        PreparedStatement ps = null;
        StatusPerusahaan sp = null;
        try{
            ps = this.conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                sp = new StatusPerusahaan();
                sp.setId(rs.getInt(1));
                sp.setNama(rs.getString(2));
            }
        }catch(Exception ex){
             throw new Exception("Getting StatusPerusahaan Gagal!!\nProblems:\n"+ex.getMessage());
        }
        return sp;
    }

    public List<StatusPerusahaan> gets() throws Exception {
        String sql = "SELECT id,nama FROM status_perusahaan";
        PreparedStatement ps = null;
        List<StatusPerusahaan> list = new ArrayList<StatusPerusahaan>();
        try{
            ps = this.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                StatusPerusahaan sp = new StatusPerusahaan();
                sp.setId(rs.getInt(1));
                sp.setNama(rs.getString(2));
                list.add(sp);
            }
        }catch(Exception ex){
             throw new Exception("Getting StatusPerusahaan Gagal!!\nProblems:\n"+ex.getMessage());
        }
        return list;
    }

}
