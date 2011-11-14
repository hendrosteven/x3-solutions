/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.dishub.dao.jpa;

import com.x3.dishub.dao.SpitDAO;
import com.x3.dishub.entity.Perusahaan;
import com.x3.dishub.entity.Spit;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class SpitDAOImpl extends GeneralDAOImpl implements SpitDAO {

    public List<Spit> getAllSpit() {
        return getJpaTemplate().find("select s from Spit s");
    }

    public List<Spit> getByNomor(String nomor) {
        return getJpaTemplate().find("select s from Spit s where s.nomor=?1", nomor);

    }

    public List<Spit> getByPerusahaan(Perusahaan perusahaan) {
        return getJpaTemplate().find("select s from Spit s where s.perusahaan=?1", perusahaan);
    }
}
