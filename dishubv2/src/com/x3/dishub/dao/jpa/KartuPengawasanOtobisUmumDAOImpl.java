/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.dishub.dao.jpa;

import com.x3.dishub.dao.KartuPengawasanOtobisUmumDAO;
import com.x3.dishub.entity.KartuPengawasanOtobisUmum;
import com.x3.dishub.entity.Kendaraan;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class KartuPengawasanOtobisUmumDAOImpl extends GeneralDAOImpl implements KartuPengawasanOtobisUmumDAO {

    public List<KartuPengawasanOtobisUmum> getAllKartuPengawasanOtobisUmum() {
        return getJpaTemplate().find("select k from KartuPengawasanOtobisUmum k");
    }

    public List<KartuPengawasanOtobisUmum> getByNomor(String nomor) {
        return getJpaTemplate().find("select k from KartuPengawasanOtobisUmum k where k.nomor=?1", nomor);

    }

    public List<KartuPengawasanOtobisUmum> getByKendaraan(Kendaraan kendaraan) {
        return getJpaTemplate().find("select k from KartuPengawasanOtobisUmum k where k.armada.kendaraan=?1", kendaraan);
    }

    public List<KartuPengawasanOtobisUmum> getByDate(Date tanggalMulai, Date tanggalSelesai) {
        return getJpaTemplate().find("select k from KartuPengawasanOtobisUmum k where k.tanggalBerlakuSelesai>=?1 and k.tanggalBerlakuSelesai<=?2", tanggalMulai, tanggalSelesai);
    }
}
