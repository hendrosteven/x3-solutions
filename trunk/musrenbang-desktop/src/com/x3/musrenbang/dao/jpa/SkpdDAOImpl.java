/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.musrenbang.dao.jpa;

import com.x3.musrenbang.dao.SkpdDAO;
import com.x3.musrenbang.entity.Bidang;
import com.x3.musrenbang.entity.Skpd;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class SkpdDAOImpl extends GeneralDAOImpl implements SkpdDAO{

    public Skpd getSkpd(int id) {
        return getJpaTemplate().find(Skpd.class, id);
    }

    public List<Skpd> gets() {
        return getJpaTemplate().find("select s from Skpd s");
    }

    public List<Skpd> gets(Bidang bidang) {
        //return getJpaTemplate().find("select s from Skpd s where s.bidang=?1", bidang);
        return null;
    }

}
