/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.dao;

import com.x3.dishub.entity.KartuJamPerjalanan;
import com.x3.dishub.entity.Kendaraan;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface KartuJamPerjalananDAO extends GeneralDAO{
    public List<KartuJamPerjalanan> getAllKartuJamPerjalanan();
    public List<KartuJamPerjalanan> getByNomor(String nomor);
    public List<KartuJamPerjalanan> getByKendraan(Kendaraan kendaraan);
    public List<KartuJamPerjalanan> getByDate(Date tanggalMulai, Date tanggalSelesai);
}
