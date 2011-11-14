/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.dao.jpa;

import com.x3.dishub.dao.KartuPengawasanTaksiDAO;
import com.x3.dishub.entity.KartuPengawasanTaksi;
import com.x3.dishub.entity.Kendaraan;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class KartuPengawasanTaksiDAOImpl extends GeneralDAOImpl implements KartuPengawasanTaksiDAO{

    public List<KartuPengawasanTaksi> getAllKartuPengawasanTaksi() {
        return getJpaTemplate().find("select k from KartuPengawasanTaksi k");
    }

    public List<KartuPengawasanTaksi> getByNomor(String nomor) {
        return getJpaTemplate().find("select k from KartuPengawasanTaksi k where k.nomor=?1", nomor);
        
    }

    public List<KartuPengawasanTaksi> getByKendaraan(Kendaraan kendaraan) {
        return getJpaTemplate().find("select k from KartuPengawasanTaksi k where k.armada.kendaraan=?1",kendaraan);
    }

    public List<KartuPengawasanTaksi> getByDate(Date tanggalMulai, Date tanggalSelesai) {
        return getJpaTemplate().find("select k from KartuPengawasanTaksi k where k.tanggalBerlakuSelesai>=?1 and k.tanggalBerlakuSelesai<=?2", tanggalMulai,tanggalSelesai);
    }

}
