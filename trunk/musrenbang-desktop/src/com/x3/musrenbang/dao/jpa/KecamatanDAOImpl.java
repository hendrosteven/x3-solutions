/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.musrenbang.dao.jpa;

import com.x3.musrenbang.dao.KecamatanDAO;
import com.x3.musrenbang.entity.Kecamatan;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class KecamatanDAOImpl extends GeneralDAOImpl implements KecamatanDAO {

    public Kecamatan getKecamatan(int id) {
        return getJpaTemplate().find(Kecamatan.class, id);
    }

    public List<Kecamatan> gets() {
        return getJpaTemplate().find("select k from Kecamatan k");
    }

}
