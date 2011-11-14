/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.dishub.dao.jpa;

import com.x3.dishub.dao.SkPelaksanaanIjinOperasiDAO;
import com.x3.dishub.entity.Perusahaan;
import com.x3.dishub.entity.SkPelaksanaanIjinOperasi;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class SkPelaksanaanIjinOperasiDAOImpl extends GeneralDAOImpl implements SkPelaksanaanIjinOperasiDAO {

    public List<SkPelaksanaanIjinOperasi> getAllIjin() {
        return getJpaTemplate().find("select sk from SkPelaksanaanIjinOperasi sk");
    }

    public List<SkPelaksanaanIjinOperasi> getByNomor(String nomor) {
        return getJpaTemplate().find("select sk from SkPelaksanaanIjinOperasi sk where sk.nomor=?1", nomor);

    }

    public List<SkPelaksanaanIjinOperasi> getByPerusahaan(Perusahaan perusahaan) {
        return getJpaTemplate().find("select sk from SkPelaksanaanIjinOperasi sk where sk.ijinOperasi.perusahaan=?1", perusahaan);
    }
}
