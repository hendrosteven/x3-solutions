/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.musrenbang.dao.jpa;

import com.x3.musrenbang.dao.KelurahanDAO;
import com.x3.musrenbang.entity.Kecamatan;
import com.x3.musrenbang.entity.Kelurahan;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class KelurahanDAOImpl extends GeneralDAOImpl implements KelurahanDAO{

    public Kelurahan get(int id) {
        return getJpaTemplate().find(Kelurahan.class, id);
    }

    public List<Kelurahan> gets() {
       return getJpaTemplate().find("select k from Kelurahan k");
    }

    public List<Kelurahan> gets(Kecamatan kec) {
       return getJpaTemplate().find("select k from Kelurahan k where k.kecamatan=?1", kec);
    }

}
