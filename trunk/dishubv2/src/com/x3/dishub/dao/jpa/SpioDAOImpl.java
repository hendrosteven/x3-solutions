/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.dao.jpa;

import com.x3.dishub.dao.SpioDAO;
import com.x3.dishub.entity.Perusahaan;
import com.x3.dishub.entity.Spio;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class SpioDAOImpl extends GeneralDAOImpl implements SpioDAO{

    public List<Spio> getAllSpio() {
        return getJpaTemplate().find("select s from Spio s");
    }

    public List<Spio> getByNomor(String nomor) {
        return getJpaTemplate().find("select s from Spio s where s.nomor=?1", nomor);
        
    }

    public List<Spio> getByPerusahaan(Perusahaan perusahaan) {
        return getJpaTemplate().find("select s from Spio s where s.perusahaan=?1", perusahaan);
    }

}
