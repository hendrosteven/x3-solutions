/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.dao;

import com.x3.dishub.entity.Kendaraan;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface KendaraanDAO extends GeneralDAO{
    //public List<Kendaraan> getByPerusahaan(Perusahaan perusahaan);
    //public List<Kendaraan> getByTrayek(Trayek trayek);
    public List<Kendaraan> getByNomorPolisi(String nomor);
    public List<Kendaraan> getAll();
}
