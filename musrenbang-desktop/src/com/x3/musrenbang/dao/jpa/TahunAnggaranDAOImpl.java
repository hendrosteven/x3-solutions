/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.musrenbang.dao.jpa;

import com.x3.musrenbang.dao.TahunAnggaranDAO;
import com.x3.musrenbang.entity.TahunAnggaran;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class TahunAnggaranDAOImpl extends GeneralDAOImpl implements TahunAnggaranDAO {

    public TahunAnggaran get(int id) {
        return getJpaTemplate().find(TahunAnggaran.class, id);
    }

    public List<TahunAnggaran> gets() {
        return getJpaTemplate().find("select t from TahunAnggaran t");
    }

}
