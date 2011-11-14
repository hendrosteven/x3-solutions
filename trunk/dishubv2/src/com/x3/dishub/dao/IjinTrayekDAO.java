/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.dao;

import com.x3.dishub.entity.Armada;
import com.x3.dishub.entity.IjinTrayek;
import com.x3.dishub.entity.Perusahaan;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface IjinTrayekDAO extends GeneralDAO{
    public List<IjinTrayek> getAllIjinTrayek();
    public List<IjinTrayek> getByNomor(String nomor);
    public List<IjinTrayek> getByPerusahaan(Perusahaan perusahaan);
    public List<IjinTrayek> getByDate(Date tanggalMulai,Date tanggalSelesai);
    public List<Armada> getArmadaByNoKendaraan(String nomor);
}
