/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toko.dao.impl;

import com.toko.dao.CategoryDAO;
import com.toko.model.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Class ini merupakan implementasi DAO dari category
 * @author user
 */
public class CategoryDAOImpl implements CategoryDAO{
    
    private Connection conn;
    
    public CategoryDAOImpl(Connection conn){
        this.conn = conn;
    }

    /**
     * Fungsi ini digunakan untuk menyimpan data category
     * ke tabel categories
     * @param category
     * @return
     * @throws Exception 
     */
    @Override
    public int insert(Category category) throws Exception {
        int result = 0;
        String sql = "INSERT INTO categories(name) VALUES(?)";
        try{
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setString(1, category.getName());
            result = ps.executeUpdate();
        }catch(Exception ex){
            throw new Exception("Category gagal disimpan : "+ex.getMessage());
        }
        return result;
    }

    /**
     * Fungsi ini digunakan untuk mengubah data 
     * category ke tabel
     * @param category
     * @return
     * @throws Exception 
     */
    @Override
    public int update(Category category) throws Exception {
        int result = 0;
        String sql = "UPDATE categories SET name=? WHERE id=?";
        try{
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setString(1, category.getName());
            ps.setInt(2,category.getId());
            result = ps.executeUpdate();
        }catch(Exception ex){
            throw new Exception("Category gagal diupdate : "+ex.getMessage());
        }
        return result;
    }

    /**
     * Fungsi ini digunakan untuk menghapus category
     * @param id
     * @return
     * @throws Exception 
     */
    @Override
    public int delete(int id) throws Exception {
        int result = 0;
        String sql = "DELETE FROM categories WHERE id=?";
        try{
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setInt(1, id);
            result = ps.executeUpdate();
        }catch(Exception ex){
            throw new Exception("Category gagal dihapus : "+ex.getMessage());
        }
        return result;
    }

    /**
     * Fungsi digunakan untuk mengambil Category berdasarkan 
     * id tertentu
     * @param id
     * @return
     * @throws Exception 
     */
    @Override
    public Category getById(int id) throws Exception {
        Category category = null;
        String sql = "SELECT id,name FROM categories WHERE id=?";
        try{
            PreparedStatement ps= this.conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                category = new Category(rs.getInt(1),rs.getString(2));
            }
        }catch(Exception ex){
            throw new Exception("Gagal membaca category : "+ex.getMessage());
        }
        return category;
    }

    /**
     * Fungsi ini digunakan untuk mengambil semua category
     * @return
     * @throws Exception 
     */
    @Override
    public List<Category> getAll() throws Exception {
        List<Category> all = new ArrayList<Category>();
        String sql = "SELECT id,name FROM categories";
        try{
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Category category = new Category(rs.getInt(1),rs.getString(2));
                all.add(category);
            }
        }catch(Exception ex){
            throw new Exception("gagal membaca category : "+ex.getMessage());
        }
        return all;
    }
    
}
