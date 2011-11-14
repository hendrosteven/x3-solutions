/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.dao.jpa;

import com.x3.dishub.dao.ProvinsiDAO;
import com.x3.dishub.entity.Provinsi;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class ProvinsiDAOImpl extends GeneralDAOImpl implements ProvinsiDAO{

    public List<Provinsi> getAllProvinsi() {
        return getJpaTemplate().find("SELECT p FROM Provinsi p");
    }

    public Provinsi getById(long id) {
        return getJpaTemplate().find(Provinsi.class, id);
    }

}
