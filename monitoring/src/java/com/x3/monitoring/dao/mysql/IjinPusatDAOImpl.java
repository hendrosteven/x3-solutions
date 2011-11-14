/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.dao.mysql;

import com.x3.monitoring.dao.IjinPusatDAO;
import com.x3.monitoring.entity.IjinPusat;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;

/**
 *
 * @author Hendro Steven
 */
public class IjinPusatDAOImpl implements IjinPusatDAO {

    private Connection conn;

    public IjinPusatDAOImpl(Connection conn) {
        this.conn = conn;
    }

    public void insert(IjinPusat ijin) throws Exception {
        String sql = "INSERT INTO ijin_pusat(nomor,tanggal,perusahaan_id,keterangan) VALUES(?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setString(1, ijin.getNomor());
            DateTime dt = new DateTime(ijin.getTanggal());
            ps.setString(2, dt.toString("yyyy-MM-dd"));
            ps.setString(3, ijin.getPerusahaan().getId());
            ps.setString(4, ijin.getKeterangan());
            ps.execute();
        } catch (Exception ex) {
            throw new Exception("Insert IjinPusat Gagal!!\nProblems:\n" + ex.getMessage());
        }
    }

    public void update(IjinPusat ijin) throws Exception {
        String sql = "UPDATE ijin_pusat SET nomor=?,tanggal=?,perusahaan_id=?,keterangan=? WHERE id=?";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setString(1, ijin.getNomor());
            DateTime dt = new DateTime(ijin.getTanggal());
            ps.setString(2, dt.toString("yyyy-MM-dd"));
            ps.setString(3, ijin.getPerusahaan().getId());
            ps.setString(4, ijin.getKeterangan());
            ps.setInt(5, ijin.getId());
            ps.execute();
        } catch (Exception ex) {
            throw new Exception("Update IjinPusat Gagal!!\nProblems:\n" + ex.getMessage());
        }
    }

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM ijin_pusat WHERE id=?";
        PreparedStatement ps = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
        } catch (Exception ex) {
            throw new Exception("Delete IjinPusat Gagal!!\nProblems:\n" + ex.getMessage());
        }
    }

    public IjinPusat get(int id) throws Exception {
        String sql = "SELECT id,nomor,tanggal,keterangan FROM ijin_pusat WHERE id=?";
        PreparedStatement ps = null;
        IjinPusat ip = null;
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ip = new IjinPusat();
                ip.setId(rs.getInt(1));
                ip.setNomor(rs.getString(2));
                ip.setTanggal(new DateTime(rs.getString(3)).toDate());
                ip.setKeterangan(rs.getString(4));
            }
        } catch (Exception ex) {
            throw new Exception("Getting IjinPusat Gagal!!\nProblems:\n" + ex.getMessage());
        }
        return ip;
    }

    public List<IjinPusat> gets(String idPerusahaan) throws Exception {
        String sql = "SELECT id,nomor,tanggal,keterangan FROM ijin_pusat WHERE perusahaan_id=?";
        PreparedStatement ps = null;
        List<IjinPusat> list = new ArrayList<IjinPusat>();
        try {
            ps = this.conn.prepareStatement(sql);
            ps.setString(1, idPerusahaan);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                IjinPusat ip = new IjinPusat();
                ip.setId(rs.getInt(1));
                ip.setNomor(rs.getString(2));
                ip.setTanggal(new DateTime(rs.getString(3)).toDate());
                ip.setKeterangan(rs.getString(4));
                list.add(ip);
            }
        } catch (Exception ex) {
            throw new Exception("Getting IjinPusat Gagal!!\nProblems:\n" + ex.getMessage());
        }
        return list;
    }
}
