/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.dao.jpa;

import com.x3.dishub.dao.MerkDAO;
import com.x3.dishub.entity.Merk;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class MerkDAOImpl extends GeneralDAOImpl implements MerkDAO{

    public List<Merk> getAllMerk() {
        return getJpaTemplate().find("SELECT m FROM Merk m");
    }

    public Merk getById(long id) {
        return getJpaTemplate().find(Merk.class, id);
    }

}
