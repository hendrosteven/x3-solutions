/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.musrenbang.dao.jpa;

import com.x3.musrenbang.dao.KegiatanDAO;
import com.x3.musrenbang.entity.Kegiatan;
import com.x3.musrenbang.entity.Program;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class KegiatanDAOImpl extends GeneralDAOImpl implements KegiatanDAO {

    public Kegiatan get(int id) {
        return getJpaTemplate().find(Kegiatan.class, id);
    }

    public List<Kegiatan> gets() {
       return getJpaTemplate().find("select k from Kegiatan k");
    }

    public List<Kegiatan> gets(Program program) {
        return getJpaTemplate().find("select k from Kegiatan k where k.program=?1", program);
    }

}
