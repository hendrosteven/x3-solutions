/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.monitoring.dao;

import com.x3.monitoring.entity.StatusPerusahaan;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface StatusPerusahaanDAO {
    public void insert(StatusPerusahaan sp)throws Exception;
    public void update(StatusPerusahaan sp)throws Exception;
    public void delete(int id)throws Exception;
    public StatusPerusahaan get(int id)throws Exception;
    public List<StatusPerusahaan> gets()throws Exception;
}
