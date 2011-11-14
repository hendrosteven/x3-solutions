/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.dao;

import com.x3.monitoring.entity.BentukModal;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface BentukModalDAO {

    public void insert(BentukModal bm) throws Exception;

    public void update(BentukModal bm) throws Exception;

    public void delete(int id) throws Exception;

    public BentukModal get(int id) throws Exception;

    public List<BentukModal> gets() throws Exception;
}
