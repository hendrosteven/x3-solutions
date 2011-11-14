/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.dao;

import com.x3.dishub.entity.IjinInsidentil;
import com.x3.dishub.entity.Kendaraan;
import com.x3.dishub.entity.Perusahaan;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface IjinInsidentilDAO extends GeneralDAO{
    public List<IjinInsidentil> getAllIjinInsidentil();
    public List<IjinInsidentil> getByNomor(String nomor);
    public List<IjinInsidentil> getByPerusahaan(Perusahaan perusahaan);
    public List<IjinInsidentil> getByKendaraan(Kendaraan kendaraan);
}
