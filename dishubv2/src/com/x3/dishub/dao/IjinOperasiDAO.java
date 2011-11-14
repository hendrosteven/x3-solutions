/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.dao;

import com.x3.dishub.entity.IjinOperasi;
import com.x3.dishub.entity.Perusahaan;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface IjinOperasiDAO extends GeneralDAO{
    public List<IjinOperasi> getAllIjinOperasi();
    public List<IjinOperasi> getByNomor(String nomor);
    public List<IjinOperasi> getByPersusahaan(Perusahaan perusahaan);
    public List<IjinOperasi> getByDate(Date tanggalMuladi, Date tanggalSelseai);
}
