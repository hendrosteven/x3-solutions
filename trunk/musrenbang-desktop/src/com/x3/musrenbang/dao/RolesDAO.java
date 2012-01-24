/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.musrenbang.dao;

import com.x3.musrenbang.entity.Roles;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface RolesDAO extends GeneralDAO {

    public Roles getRole(int id);

    public List<Roles> gets();
}
