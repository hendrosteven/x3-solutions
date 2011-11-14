/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.dishub.dao.jpa;

import com.x3.dishub.dao.SkPelaksanaanIjinTrayekDAO;
import com.x3.dishub.entity.Perusahaan;
import com.x3.dishub.entity.SkPelaksanaanIjinTrayek;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class SkPelaksanaanIjinTrayekDAOImpl extends GeneralDAOImpl implements SkPelaksanaanIjinTrayekDAO {

    public List<SkPelaksanaanIjinTrayek> getAllIjin() {
        return getJpaTemplate().find("select sk from SkPelaksanaanIjinTrayek sk");
    }

    public List<SkPelaksanaanIjinTrayek> getByNomor(String nomor) {
        return getJpaTemplate().find("select sk from SkPelaksanaanIjinTrayek sk where sk.nomor=?1", nomor);

    }

    public List<SkPelaksanaanIjinTrayek> getByPerusahaan(Perusahaan perusahaan) {
        return getJpaTemplate().find("select sk from SkPelaksanaanIjinTrayek sk where sk.ijinTrayek.perusahaan=?1", perusahaan);
    }
}
