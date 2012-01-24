/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.musrenbang.dao.jpa;

import com.x3.musrenbang.dao.RolesDAO;
import com.x3.musrenbang.entity.Roles;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class RolesDAOImpl extends GeneralDAOImpl implements RolesDAO {

    public Roles getRole(int id) {
        return getJpaTemplate().find(Roles.class, id);
    }

    public List<Roles> gets() {
        return getJpaTemplate().find("select r from Roles r");
    }

}
