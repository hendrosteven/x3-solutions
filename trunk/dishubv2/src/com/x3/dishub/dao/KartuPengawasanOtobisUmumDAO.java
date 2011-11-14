/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.dao;

import com.x3.dishub.entity.KartuPengawasanOtobisUmum;
import com.x3.dishub.entity.Kendaraan;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface KartuPengawasanOtobisUmumDAO extends GeneralDAO{
    public List<KartuPengawasanOtobisUmum> getAllKartuPengawasanOtobisUmum();
    public List<KartuPengawasanOtobisUmum> getByNomor(String nomor);
    public List<KartuPengawasanOtobisUmum> getByKendaraan(Kendaraan kendaraan);
    public List<KartuPengawasanOtobisUmum> getByDate(Date tanggalMulai,Date tanggalSelesai);
}
