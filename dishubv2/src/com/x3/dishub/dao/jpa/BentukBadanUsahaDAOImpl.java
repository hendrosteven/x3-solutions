/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.dao.jpa;

import com.x3.dishub.dao.BentukBadanUsahaDAO;
import com.x3.dishub.entity.BentukBadanUsaha;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class BentukBadanUsahaDAOImpl extends GeneralDAOImpl implements BentukBadanUsahaDAO{

    public List<BentukBadanUsaha> getAllBentukBadanUsaha() {
        return getJpaTemplate().find("SELECT bbu FROM BentukBadanUsaha bbu");
    }

    public BentukBadanUsaha getById(long id) {
        return getJpaTemplate().find(BentukBadanUsaha.class, id);
    }

}
