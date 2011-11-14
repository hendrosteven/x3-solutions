/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.dao;

import com.x3.dishub.entity.IjinUsahaAngkutanBarang;
import com.x3.dishub.entity.Perusahaan;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface IjinUsahaAngkutanBarangDAO extends GeneralDAO{
    public List<IjinUsahaAngkutanBarang> getAllIjinUsahaAngkutanBarang();
    public List<IjinUsahaAngkutanBarang> getByNomor(String nomor);
    public List<IjinUsahaAngkutanBarang> getByPerusahaan(Perusahaan perusahaan);
}
