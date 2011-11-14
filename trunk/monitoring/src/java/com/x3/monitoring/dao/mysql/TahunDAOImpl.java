/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.dao.mysql;

import com.x3.monitoring.dao.TahunDAO;
import com.x3.monitoring.entity.Tahun;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class TahunDAOImpl implements TahunDAO {

    private Connection conn;

    public TahunDAOImpl(Connection conn) {
        this.conn = conn;
    }

    public void insert(Tahun thn) throws Exception {
        String sql = "INSERT INTO tahun(nama,status) VALUES(?,?)";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setString(1, thn.getNama());
            ps.setInt(2, thn.getStatus());
            ps.execute();
        } catch (Exception ex) {
            throw new Exception("INSERT Tahun Gagal!!\nProblems:\n" + ex.getMessage());
        }
    }

    public Tahun get(int id) throws Exception {
        Tahun thn = null;
        String sql = "SELECT id,nama,status FROM tahun WHERE id=?";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                thn = new Tahun();
                thn.setId(rs.getInt(1));
                thn.setNama(rs.getString(2));
                thn.setStatus(rs.getInt(3));
            }
        } catch (Exception ex) {
            throw new Exception("Getting Tahun Gagal!!\nProblems:\n" + ex.getMessage());
        }
        return thn;
    }

    public Tahun getActif() throws Exception {
        Tahun thn = null;
        String sql = "SELECT id,nama,status FROM tahun WHERE status=?";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setInt(1, 1);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                thn = new Tahun();
                thn.setId(rs.getInt(1));
                thn.setNama(rs.getString(2));
                thn.setStatus(rs.getInt(3));
            }
        } catch (Exception ex) {
            throw new Exception("Getting Tahun Gagal!!\nProblems:\n" + ex.getMessage());
        }
        return thn;
    }

    public List<Tahun> gets() throws Exception {
        List<Tahun> list = new ArrayList<Tahun>();
        String sql = "SELECT id,nama,status FROM tahun";
        PreparedStatement ps = null;
        try{
            ps = this.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Tahun thn = new Tahun();
                thn.setId(rs.getInt(1));
                thn.setNama(rs.getString(2));
                thn.setStatus(rs.getInt(3));
                list.add(thn);
            }
        }catch(Exception ex){
            throw new Exception("Getting Tahun Gagal!!\nProblems:\n" + ex.getMessage());
        }
        return list;
    }

    public void update(Tahun thn) throws Exception {
        String sql = "UPDATE tahun SET nama=?,status=? WHERE id=?";
        PreparedStatement ps = null;
        try{
            ps = this.conn.prepareStatement(sql);
            ps.setString(1, thn.getNama());
            ps.setInt(2, thn.getStatus());
            ps.setInt(3, thn.getId());
            ps.execute();
        }catch(Exception ex){
            throw new Exception("Update Tahun Gagal!!\nProblems:\n" + ex.getMessage());
        }
    }
}
