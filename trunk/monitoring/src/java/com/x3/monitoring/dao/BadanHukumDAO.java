/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.dao;

import com.x3.monitoring.entity.BadanHukum;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface BadanHukumDAO {

    public void insert(BadanHukum bh) throws Exception;

    public void update(BadanHukum bh) throws Exception;

    public void delete(int id) throws Exception;

    public BadanHukum get(int id) throws Exception;

    public List<BadanHukum> gets() throws Exception;
}
