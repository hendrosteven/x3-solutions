/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.musrenbang.dao.jpa;

import com.x3.musrenbang.dao.UsulanKecamatanDAO;
import com.x3.musrenbang.entity.Kecamatan;
import com.x3.musrenbang.entity.TahunAnggaran;
import com.x3.musrenbang.entity.UsulanKecamatan;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class UsulanKecamatanDAOImpl extends GeneralDAOImpl implements UsulanKecamatanDAO {

    public UsulanKecamatan get(int id) {
        return getJpaTemplate().find(UsulanKecamatan.class,id);
    }

    public List<UsulanKecamatan> gets() {
        return getJpaTemplate().find("select u from UsulanKecamatan u");
    }

    public List<UsulanKecamatan> gets(TahunAnggaran tahun) {
        return getJpaTemplate().find("select u from UsulanKecamatan u where u.tahunAnggaran=?1", tahun);
    }

    public List<UsulanKecamatan> gets(TahunAnggaran tahun, Kecamatan kec) {
        return getJpaTemplate().find("select u from UsulanKecamatan u where u.tahunAnggaran=?1 and u.kecamatan=?2",tahun,kec);
    }

    public List<UsulanKecamatan> gets(Kecamatan kec) {
        return getJpaTemplate().find("select u from UsulanKecamatan u where u.kecamatan=?1", kec);
    }

}
