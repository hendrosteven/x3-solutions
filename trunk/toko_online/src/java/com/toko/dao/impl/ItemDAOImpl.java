/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toko.dao.impl;

import com.toko.dao.ItemDAO;
import com.toko.model.Item;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author user
 */
public class ItemDAOImpl implements ItemDAO {

    private Connection conn;

    public ItemDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public int insert(Item item) throws Exception {
        int result = 0;
        String sql = "INSERT INTO item(name,description,price,categories_id) "
                + "VALUES(?,?,?,?)";
        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setString(1, item.getName());
            ps.setString(2, item.getDescription());
            ps.setDouble(3, item.getPrice());
            ps.setInt(4, item.getCategory().getId());
            result = ps.executeUpdate();
        } catch (Exception ex) {
            throw new Exception("Insert Item fail : " + ex.getMessage());
        }
        return result;
    }

    @Override
    public int update(Item item) throws Exception {
        int result = 0;
        String sql = "UPDATE item SET name=?,description=?,price=?,categories_id=? "
                + "WHERE id=?";
        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setString(1, item.getName());
            ps.setString(2, item.getDescription());
            ps.setDouble(3, item.getPrice());
            ps.setInt(4, item.getCategory().getId());
            ps.setInt(5, item.getId());
            result = ps.executeUpdate();
        } catch (Exception ex) {
            throw new Exception("Update Item fail : " + ex.getMessage());
        }
        return result;
    }

    @Override
    public int delete(int id) throws Exception {
        int result = 0;
        String sql = "DELETE FROM item WHERE id=?";
        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setInt(1, id);
            result = ps.executeUpdate();
        } catch (Exception ex) {
            throw new Exception("Delete Item fail : " + ex.getMessage());
        }
        return result;
    }

    @Override
    public Item getById(int id) throws Exception {
        Item item = null;
        String sql = "SELECT id,name,description,price,categories_id FROM item "
                + "WHERE id=?";
        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                item = new Item();
                item.setId(rs.getInt(1));
                item.setName(rs.getString(2));
                item.setDescription(rs.getString(3));
                item.setPrice(rs.getDouble(4));
                item.setCategory(new CategoryDAOImpl(this.conn).getById(rs.getInt(5)));
            }
        } catch (Exception ex) {
            throw new Exception("Get Item fail : " + ex.getMessage());
        }
        return item;
    }

    @Override
    public List<Item> getAll() throws Exception {
        List<Item> items = new ArrayList<Item>();
        String sql = "SELECT id,name,description,price,categories_id FROM item";
        try {
            PreparedStatement ps = this.conn.prepareStatement(sql);           
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Item item = new Item();             
                item.setId(rs.getInt(1));
                item.setName(rs.getString(2));
                item.setDescription(rs.getString(3));
                item.setPrice(rs.getDouble(4));
                item.setCategory(new CategoryDAOImpl(this.conn).getById(rs.getInt(5)));
                items.add(item);
            }
        } catch (Exception ex) {
            throw new Exception("Get Item fail : " + ex.getMessage());
        }
        return items;
    }
}
