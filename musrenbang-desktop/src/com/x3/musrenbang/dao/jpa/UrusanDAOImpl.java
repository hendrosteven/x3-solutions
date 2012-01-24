/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.musrenbang.dao.jpa;

import com.x3.musrenbang.dao.UrusanDAO;
import com.x3.musrenbang.entity.Urusan;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class UrusanDAOImpl extends GeneralDAOImpl implements UrusanDAO {

    public Urusan get(int id) {
        return getJpaTemplate().find(Urusan.class, id);
    }

    public List<Urusan> gets() {
        return getJpaTemplate().find("select u from Urusan u");
    }

}
