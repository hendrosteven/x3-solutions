/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.musrenbang.dao.jpa;

import com.x3.musrenbang.dao.SumberDanaDAO;
import com.x3.musrenbang.entity.SumberDana;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class SumberDanaDAOImpl extends GeneralDAOImpl implements SumberDanaDAO {

    public SumberDana get(int id) {
        return getJpaTemplate().find(SumberDana.class,id);
    }

    public List<SumberDana> gets() {
        return getJpaTemplate().find("select s from SumberDana s");
    }

}
