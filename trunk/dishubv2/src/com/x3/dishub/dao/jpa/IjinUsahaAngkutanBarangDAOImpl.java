/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.dishub.dao.jpa;

import com.x3.dishub.dao.IjinUsahaAngkutanBarangDAO;
import com.x3.dishub.entity.IjinUsahaAngkutanBarang;
import com.x3.dishub.entity.Perusahaan;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class IjinUsahaAngkutanBarangDAOImpl extends GeneralDAOImpl implements IjinUsahaAngkutanBarangDAO {

    public List<IjinUsahaAngkutanBarang> getAllIjinUsahaAngkutanBarang() {
        return getJpaTemplate().find("select i from IjinUsahaAngkutanBarang i");
    }

    public List<IjinUsahaAngkutanBarang> getByNomor(String nomor) {
        return getJpaTemplate().find("select i from IjinUsahaAngkutanBarang i where i.nomor=?1", nomor);

    }

    public List<IjinUsahaAngkutanBarang> getByPerusahaan(Perusahaan perusahaan) {
        return getJpaTemplate().find("select i from IjinUsahaAngkutanBarang i where i.perusahaan=?1", perusahaan);
    }
}
