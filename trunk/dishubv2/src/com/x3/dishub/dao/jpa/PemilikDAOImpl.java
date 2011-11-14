/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.dao.jpa;

import com.x3.dishub.dao.PemilikDAO;
import com.x3.dishub.entity.Pemilik;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class PemilikDAOImpl extends GeneralDAOImpl implements PemilikDAO{

    public List<Pemilik> getAllPemilik() {
        return getJpaTemplate().find("SELECT p FROM Pemilik p");
    }

    public List<Pemilik> getByName(String nama) {
        return getJpaTemplate().find("SELECT p FROM Pemilik p WHERE p.namaDepan LIKE ?1 OR p.namaBelakang LIKE ?2",
                "%"+nama+"%","%"+nama+"%");
    }

}
