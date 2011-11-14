/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.dao;

import com.x3.dishub.entity.KartuPengawasanTaksi;
import com.x3.dishub.entity.Kendaraan;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface KartuPengawasanTaksiDAO extends GeneralDAO{
    public List<KartuPengawasanTaksi> getAllKartuPengawasanTaksi();
    public List<KartuPengawasanTaksi> getByNomor(String nomor);
    public List<KartuPengawasanTaksi> getByKendaraan(Kendaraan kendaraan);
    public List<KartuPengawasanTaksi> getByDate(Date tanggalMulai,Date tanggalSelesai);
}
