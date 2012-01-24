/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.musrenbang.dao.jpa;

import com.x3.musrenbang.dao.UsulanSkpdDAO;
import com.x3.musrenbang.entity.Skpd;
import com.x3.musrenbang.entity.TahunAnggaran;
import com.x3.musrenbang.entity.UsulanKecamatan;
import com.x3.musrenbang.entity.UsulanSkpd;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class UsulanSkpdDAOImpl extends GeneralDAOImpl implements UsulanSkpdDAO{

    public UsulanSkpd get(int id) {
        return getJpaTemplate().find(UsulanSkpd.class, id);
    }

    public UsulanSkpd getByUsulanKecamatan(UsulanKecamatan usulan) {
        return null;
    }

    public List<UsulanSkpd> gets() {
        return getJpaTemplate().find("select u from UsulanSkpd u");
    }

    public List<UsulanSkpd> gets(TahunAnggaran tahun) {
        return getJpaTemplate().find("select u from UsulanSkpd u where u.tahunAnggaran=?1", tahun);
    }

    public List<UsulanSkpd> gets(Skpd skpd) {
        return getJpaTemplate().find("select u from UsulanSkpd u where u.skpd=?1", skpd);
    }

    public List<UsulanSkpd> gets(TahunAnggaran tahun, Skpd skpd) {
        return getJpaTemplate().find("select u from UsulanSkpd u where u.tahunAnggaran=?1 and u.skpd=?2",tahun,skpd);
    }

}
