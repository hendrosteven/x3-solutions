/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.monitoring.dao;

import com.x3.monitoring.entity.Tahun;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface TahunDAO {
    public void insert(Tahun thn)throws Exception;
    public void update(Tahun thn)throws Exception;
    public Tahun get(int id)throws Exception;
    public Tahun getActif()throws Exception;
    public List<Tahun> gets()throws Exception;
    
}
