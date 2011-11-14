/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.dao;

import com.x3.dishub.entity.Perusahaan;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface PerusahaanDAO extends GeneralDAO{
    public List<Perusahaan> getAllPerusahaan();
    public List<Perusahaan> getByNama(String nama);
}
