/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.musrenbang.dao;

import com.x3.musrenbang.entity.Bidang;
import com.x3.musrenbang.entity.Skpd;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface BidangDAO extends GeneralDAO {

    public Bidang get(int id);

    public List<Bidang> gets();

    public void deleteSkpd(Bidang bidang, Skpd skpd);

    public void insertSkpdBidang(Skpd skpd, Bidang bidang);
}
