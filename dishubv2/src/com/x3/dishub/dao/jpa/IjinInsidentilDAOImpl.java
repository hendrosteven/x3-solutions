/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.dishub.dao.jpa;

import com.x3.dishub.dao.IjinInsidentilDAO;
import com.x3.dishub.entity.IjinInsidentil;
import com.x3.dishub.entity.Kendaraan;
import com.x3.dishub.entity.Perusahaan;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class IjinInsidentilDAOImpl extends GeneralDAOImpl implements IjinInsidentilDAO {

    public List<IjinInsidentil> getAllIjinInsidentil() {
        return getJpaTemplate().find("SELECT i FROM IjinInsidentil i");
    }

    public List<IjinInsidentil> getByNomor(String nomor) {
        return getJpaTemplate().find("SELECT i FROM IjinInsidentil i WHERE i.nomor=?1", nomor);

    }

    public List<IjinInsidentil> getByPerusahaan(Perusahaan perusahaan) {
        return getJpaTemplate().find("select i from IjinInsidentil i where i.perusahaan=?1", perusahaan);
    }

    public List<IjinInsidentil> getByKendaraan(Kendaraan kendaraan) {
        return getJpaTemplate().find("select i from IjinInsidentil i where i.kendaraan=?1",kendaraan);
    }
}
