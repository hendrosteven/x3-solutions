/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toko.dao;

import com.toko.model.Item;
import java.util.List;

/**
 *
 * @author user
 */
public interface ItemDAO {
    public int insert(Item item)throws Exception;
    public int update(Item item)throws Exception;
    public int delete(int id)throws Exception;
    public Item getById(int id)throws Exception;
    public List<Item> getAll()throws Exception;
}
