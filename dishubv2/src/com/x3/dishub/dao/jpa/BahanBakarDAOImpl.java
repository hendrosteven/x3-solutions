/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.dao.jpa;

import com.x3.dishub.dao.BahanBakarDAO;
import com.x3.dishub.entity.BahanBakar;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class BahanBakarDAOImpl extends GeneralDAOImpl implements BahanBakarDAO{

    public List<BahanBakar> getAllBahanBakar() {
        return getJpaTemplate().find("SELECT bb From BahanBakar bb");
    }

    public BahanBakar getById(long id) {
        return (BahanBakar)getJpaTemplate().find(BahanBakar.class, id);
    }

    

}
