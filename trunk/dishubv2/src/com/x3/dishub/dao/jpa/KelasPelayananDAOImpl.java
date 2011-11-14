/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.dao.jpa;

import com.x3.dishub.dao.KelasPelayananDAO;
import com.x3.dishub.entity.KelasPelayanan;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class KelasPelayananDAOImpl extends GeneralDAOImpl implements KelasPelayananDAO{

    public List<KelasPelayanan> getAllKelasPelayanan() {
        return getJpaTemplate().find("SELECT kp FROM KelasPelayanan kp");
    }

    public KelasPelayanan getById(long id) {
        return getJpaTemplate().find(KelasPelayanan.class, id);
    }

}
