/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.musrenbang.dao.jpa;

import com.x3.musrenbang.dao.BidangDAO;
import com.x3.musrenbang.entity.Bidang;
import com.x3.musrenbang.entity.Skpd;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class BidangDAOImpl extends GeneralDAOImpl implements BidangDAO {

    public Bidang get(int id) {
        return getJpaTemplate().find(Bidang.class, id);
    }

    public List<Bidang> gets() {
        return getJpaTemplate().find("Select b From Bidang b");
    }

    public void deleteSkpd(Bidang bidang, Skpd skpd) {
        
    }

    public void insertSkpdBidang(Skpd skpd, Bidang bidang) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
