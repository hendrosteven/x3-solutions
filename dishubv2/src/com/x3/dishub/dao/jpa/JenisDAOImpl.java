/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.dao.jpa;

import com.x3.dishub.dao.JenisDAO;
import com.x3.dishub.entity.Jenis;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class JenisDAOImpl extends GeneralDAOImpl implements JenisDAO{

    public List<Jenis> getAllJenis() {
        return getJpaTemplate().find("SELECT j FROM Jenis j");
    }

    public Jenis getById(long id) {
        return getJpaTemplate().find(Jenis.class, id);
    }

}
