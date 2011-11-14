/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.dao.jpa;

import com.x3.dishub.dao.TrayekDAO;
import com.x3.dishub.entity.Trayek;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class TrayekDAOImpl extends GeneralDAOImpl implements TrayekDAO{

    public List<Trayek> getAllTrayek() {
        return getJpaTemplate().find("SELECT t FROM Trayek t");
    }

    public Trayek getById(long id) {
        return getJpaTemplate().find(Trayek.class, id);
    }

}
