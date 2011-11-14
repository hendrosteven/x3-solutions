/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.dao.jpa;

import com.x3.dishub.dao.PerusahaanDAO;
import com.x3.dishub.entity.Perusahaan;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class PerusahaanDAOImpl extends GeneralDAOImpl implements PerusahaanDAO{

    public List<Perusahaan> getAllPerusahaan() {
        return getJpaTemplate().find("SELECT p FROM Perusahaan p");
    }

    public List<Perusahaan> getByNama(String nama) {
        return getJpaTemplate().find("SELECT p FROM Perusahaan p WHERE p.nama LIKE ?1", "%"+nama+"%");
    }

}
