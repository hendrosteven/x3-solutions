/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.dao;

import com.x3.dishub.entity.IjinUsahaAngkutanOrang;
import com.x3.dishub.entity.Perusahaan;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface IjinUsahaAngkutanOrangDAO extends GeneralDAO{
    public List<IjinUsahaAngkutanOrang> getAllIjinUsahaAngkutanOrang();
    public List<IjinUsahaAngkutanOrang> getByNomor(String nomor);
    public List<IjinUsahaAngkutanOrang> getByPerusahaan(Perusahaan perusahaan);
}
