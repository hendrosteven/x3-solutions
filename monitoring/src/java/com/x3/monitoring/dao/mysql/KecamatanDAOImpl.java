/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.dao.mysql;

import com.x3.monitoring.dao.KecamatanDAO;
import com.x3.monitoring.entity.Kecamatan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class KecamatanDAOImpl implements KecamatanDAO {

    private Connection conn;

    public KecamatanDAOImpl(Connection conn) {
        this.conn = conn;
    }

    public Kecamatan get(int id) throws Exception {
        String sql = "SELECT * FROM kecamatan WHERE id=?";
        PreparedStatement ps = null;
        Kecamatan kec = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                kec = new Kecamatan();
                kec.setId(rs.getInt(1));
                kec.setNama(rs.getString(2));
            }
        } catch (Exception ex) {
            throw new Exception("Getting Kecamatan Gagal!!\nProblems:\n" + ex.getMessage());
        }
        return kec;
    }

    public List<Kecamatan> gets() throws Exception {
        String sql = "SELECT * FROM kecamatan";
        PreparedStatement ps = null;
        List<Kecamatan> list = new ArrayList<Kecamatan>();
        try {
            ps = this.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Kecamatan kec = new Kecamatan();
                kec.setId(rs.getInt(1));
                kec.setNama(rs.getString(2));
                list.add(kec);
            }
        } catch (Exception ex) {
            throw new Exception("Getting Kecamatan Gagal!!\nProblems:\n" + ex.getMessage());
        }
        return list;
    }

    public void insert(Kecamatan kec) throws Exception {
        String sql = "INSERT INTO kecamatan(nama_kec) VALUES(?)";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setString(1, kec.getNama());
            ps.execute();
        } catch (Exception ex) {
            throw new Exception("Insert Kecamatan Gagal!!\nProblems:\n" + ex.getMessage());
        }
    }

    public void update(Kecamatan kec) throws Exception {
        String sql = "UPDATE kecamatan SET nama_kec=? WHERE id=?";
        PreparedStatement ps = null;
        try{
            ps = this.conn.prepareStatement(sql);
            ps.setString(1, kec.getNama());
            ps.setInt(2, kec.getId());
            ps.execute();
        }catch(Exception ex){
            throw new Exception("Update Kecamatan Gagal!!\nProblems:\n" + ex.getMessage());
        }
    }

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM kecamatan_kec WHERE id=?";
        PreparedStatement ps = null;
        try{
            ps = this.conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
        }catch(Exception ex){
            throw new Exception("Delete Kecamatan Gagal!!\nProblems:\n" + ex.getMessage());
        }
    }
}
