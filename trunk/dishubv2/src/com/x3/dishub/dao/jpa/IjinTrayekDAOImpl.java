/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.dishub.dao.jpa;

import com.x3.dishub.dao.IjinTrayekDAO;
import com.x3.dishub.entity.Armada;
import com.x3.dishub.entity.IjinTrayek;
import com.x3.dishub.entity.Perusahaan;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class IjinTrayekDAOImpl extends GeneralDAOImpl implements IjinTrayekDAO {

    public List<IjinTrayek> getAllIjinTrayek() {
        return getJpaTemplate().find("SELECT i FROM IjinTrayek i");
    }

    public List<IjinTrayek> getByNomor(String nomor) {
        return getJpaTemplate().find("SELECT i FROM IjinTrayek i WHERE i.nomor=?1", nomor);

    }

    public List<IjinTrayek> getByPerusahaan(Perusahaan perusahaan) {
        return getJpaTemplate().find("SELECT i FROM IjinTrayek i WHERE i.perusahaan=?1", perusahaan);
    }

    public List<IjinTrayek> getByDate(Date tanggalMulai, Date tanggalSelesai) {
        return getJpaTemplate().find("select i from IjinTrayek i WHERE i.masaBerlakuSelesai>=?1 AND i.masaBerlakuSelesai<=?2", tanggalMulai, tanggalSelesai);
    }

    public List<Armada> getArmadaByNoKendaraan(String nomor) {
        return getJpaTemplate().find("select a FROM IjinTrayek.armadas a WHERE a.kendaraan.nomorPolisi=?1",nomor);
    }
}
