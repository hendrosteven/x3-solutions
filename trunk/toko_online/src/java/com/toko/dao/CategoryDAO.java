/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toko.dao;

import com.toko.model.Category;
import java.util.List;

/**
 *
 * @author user
 */
public interface CategoryDAO {
    public int insert(Category category)throws Exception;
    public int update (Category category)throws Exception;
    public int delete(int id)throws Exception;
    public Category getById(int id)throws Exception;
    public List<Category> getAll()throws Exception;
}
