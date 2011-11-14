/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.dishub.dao.jpa;

import com.x3.dishub.dao.IjinOperasiDAO;
import com.x3.dishub.entity.IjinOperasi;
import com.x3.dishub.entity.Perusahaan;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class IjinOperasiDAOImpl extends GeneralDAOImpl implements IjinOperasiDAO {

    public List<IjinOperasi> getAllIjinOperasi() {
        return getJpaTemplate().find("SELECT i FROM IjinOperasi i");
    }

    public List<IjinOperasi> getByNomor(String nomor) {
        return getJpaTemplate().find("SELECT i FROM IjinOperasi i WHERE i.nomor=?1", nomor);
    }

    public List<IjinOperasi> getByPersusahaan(Perusahaan perusahaan) {
        return getJpaTemplate().find("SELECT i FROM IjinOperasi i WHERE i.perusahaan=?1", perusahaan);
    }

    public List<IjinOperasi> getByDate(Date tanggalMulai, Date tanggalSelesai) {

        return getJpaTemplate().find("SELECT i FROM IjinOperasi i WHERE i.masaBerlakuSelesai>=?1 AND i.masaBerlakuSelesai<=?2", tanggalMulai, tanggalSelesai);
    }
}
