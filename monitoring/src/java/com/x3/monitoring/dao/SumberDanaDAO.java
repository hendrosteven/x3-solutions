/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.monitoring.dao;

import com.x3.monitoring.entity.SumberDana;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface SumberDanaDAO {
    public void insert(SumberDana sd)throws Exception;
    public void update(SumberDana sd)throws Exception;
    public void delete(int id)throws Exception;
    public SumberDana get(int id)throws Exception;
    public List<SumberDana> gets()throws Exception;
}
