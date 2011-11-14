/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.dao.jpa;

import com.x3.dishub.dao.KabupatenKotaDAO;
import com.x3.dishub.entity.KabupatenKota;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class KabupatenKotaDAOImpl extends GeneralDAOImpl implements KabupatenKotaDAO{

    public List<KabupatenKota> getAllKabupatenKota() {
        return getJpaTemplate().find("SELECT kk FROM KabupatenKota KK");
    }

    public KabupatenKota getById(long id) {
        return getJpaTemplate().find(KabupatenKota.class, id);
    }

}
