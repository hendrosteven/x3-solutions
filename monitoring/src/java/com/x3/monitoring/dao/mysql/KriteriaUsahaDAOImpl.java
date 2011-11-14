/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.monitoring.dao.mysql;

import com.x3.monitoring.dao.KriteriaUsahaDAO;
import com.x3.monitoring.entity.KriteriaUsaha;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class KriteriaUsahaDAOImpl implements KriteriaUsahaDAO {

    private Connection conn;

    public KriteriaUsahaDAOImpl(Connection conn){
        this.conn = conn;
    }

    public void insert(KriteriaUsaha ku) throws Exception {
        String sql = "INSERT INTO kriteria_usaha(keterangan,operator_1,nilai_1,operator_2,nilai_2) " +
                "VALUES(?,?,?,?,?)";
        PreparedStatement ps = null;
        try{
             ps = this.conn.prepareStatement(sql);
             ps.setString(1, ku.getKeterangan());
             ps.setString(2, ku.getOperator1());
             ps.setInt(3, ku.getNilai1());
             ps.setString(4, ku.getOperator2());
             ps.setInt(5,ku.getNilai2());
             ps.execute();
        }catch(Exception ex){
            throw new Exception("Insert Kriteria Usaha Gagal!!\nProblems:\n" + ex.getMessage());
        }
    }

    public void update(KriteriaUsaha ku) throws Exception {
        String sql = "UPDATE kriteria_usaha SET keterangan=?,operator_1=?,nilai_1=?,operator_2=?,nilai_2=? " +
                "WHERE id=?";
        PreparedStatement ps = null;
        try{
            ps = this.conn.prepareStatement(sql);
            ps.setString(1, ku.getKeterangan());
            ps.setString(2, ku.getOperator1());
            ps.setInt(3, ku.getNilai1());
            ps.setString(4, ku.getOperator2());
            ps.setInt(5, ku.getNilai2());
            ps.setInt(6, ku.getId());
            ps.execute();
        }catch(Exception ex){
            throw new Exception("Update Kriteria Usaha gagal!!\nProblems:\n"+ex.getMessage());
        }
    }

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM kriteria_usaha WHERE id=?";
        PreparedStatement ps = null;
        try{
            ps = this.conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.execute();
        }catch(Exception ex){
            throw new Exception("Delete Kriteria Usaha gagal!\nProblems:\n"+ex.getMessage());
        }
    }

    public KriteriaUsaha get(int id) throws Exception {
        KriteriaUsaha ku = null;
        String sql = "SELECT * FROM kriteria_usaha WHERE id=?";
        PreparedStatement ps = null;
        try{
            ps = this.conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                ku = new KriteriaUsaha();
                ku.setId(rs.getInt(1));
                ku.setKeterangan(rs.getString(2));
                ku.setOperator1(rs.getString(3));
                ku.setNilai1(rs.getInt(4));
                ku.setOperator2(rs.getString(5));
                ku.setNilai2(rs.getInt(6));
            }
        }catch(Exception ex){
            throw new Exception("Getting Kriteria Usaha gagal!!\nProblems:\n"+ex.getMessage());
        }
        return ku;
    }

    public List<KriteriaUsaha> gets() throws Exception {
        List<KriteriaUsaha> list = new ArrayList<KriteriaUsaha>();
        String sql = "SELECT * FROM kriteria_usaha";
        PreparedStatement ps = null;
        try{
            ps = this.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                KriteriaUsaha ku = new KriteriaUsaha();
                ku.setId(rs.getInt(1));
                ku.setKeterangan(rs.getString(2));
                ku.setOperator1(rs.getString(3));
                ku.setNilai1(rs.getInt(4));
                ku.setOperator2(rs.getString(5));
                ku.setNilai2(rs.getInt(6));
                list.add(ku);
            }
        }catch(Exception ex){
            throw new Exception("Getting Kriteria Usaha gagal!!\nProblems:\n"+ex.getMessage());
        }
        return list;
    }

}
