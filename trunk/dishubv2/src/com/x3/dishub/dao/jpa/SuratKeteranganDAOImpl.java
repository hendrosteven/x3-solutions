/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.dao.jpa;

import com.x3.dishub.dao.SuratKeteranganDAO;
import com.x3.dishub.entity.SuratKeterangan;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class SuratKeteranganDAOImpl extends GeneralDAOImpl implements SuratKeteranganDAO{

    public List<SuratKeterangan> getAllSuratKeterangan() {
        return getJpaTemplate().find("SELECT s FROM SuratKeterangan s");
    }

    public List<SuratKeterangan> getByNomor(String nomor) {
        return getJpaTemplate().find("SELECT s FROM SuratKeterangan s WHERE s.nomor=?1", nomor);
    }

}
