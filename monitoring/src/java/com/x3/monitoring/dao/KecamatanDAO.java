/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.monitoring.dao;

import com.x3.monitoring.entity.Kecamatan;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface KecamatanDAO {
    public void insert(Kecamatan kec)throws Exception;
    public void update(Kecamatan kec)throws Exception;
    public void delete(int id)throws Exception;
    public Kecamatan get(int id)throws Exception;
    public List<Kecamatan> gets()throws Exception;
}
