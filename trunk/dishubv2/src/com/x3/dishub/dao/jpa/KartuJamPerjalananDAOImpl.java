/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.dishub.dao.jpa;

import com.x3.dishub.dao.KartuJamPerjalananDAO;
import com.x3.dishub.entity.KartuJamPerjalanan;
import com.x3.dishub.entity.Kendaraan;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class KartuJamPerjalananDAOImpl extends GeneralDAOImpl implements KartuJamPerjalananDAO {

    public List<KartuJamPerjalanan> getAllKartuJamPerjalanan() {
        return getJpaTemplate().find("select k from KartuJamPerjalanan k");
    }

    public List<KartuJamPerjalanan> getByNomor(String nomor) {
        return getJpaTemplate().find("select k from KartuJamPerjalanan k where k.nomor=?1", nomor);

    }

    public List<KartuJamPerjalanan> getByKendraan(Kendaraan kendaraan) {
        return getJpaTemplate().find("select k from KartuJamPerjalanan k where k.armada.kendaraan=?1", kendaraan);
    }

    public List<KartuJamPerjalanan> getByDate(Date tanggalMulai, Date tanggalSelesai) {
        return getJpaTemplate().find("select k from KartuJamPerjalanan k where k.tanggalMulaiBerakhir>=?1 and k.tanggalMulaiBerakhir<=?2", tanggalMulai, tanggalSelesai);
    }
}
