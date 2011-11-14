/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.dishub.dao.jpa;

import com.x3.dishub.dao.IjinUsahaAngkutanOrangDAO;
import com.x3.dishub.entity.IjinUsahaAngkutanOrang;
import com.x3.dishub.entity.Perusahaan;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class IjinUsahaAngkutanOrangDAOImpl extends GeneralDAOImpl implements IjinUsahaAngkutanOrangDAO {

    public List<IjinUsahaAngkutanOrang> getAllIjinUsahaAngkutanOrang() {
        return getJpaTemplate().find("select i from IjinUsahaAngkutanOrang i");
    }

    public List<IjinUsahaAngkutanOrang> getByNomor(String nomor) {
        return getJpaTemplate().find("select i from IjinUsahaAngkutanOrang i where i.nomor=?1", nomor);
    }

    public List<IjinUsahaAngkutanOrang> getByPerusahaan(Perusahaan perusahaan) {
        return getJpaTemplate().find("select i from IjinUsahaAngkutanOrang i where i.perusahaan=?1", perusahaan);
    }
}
