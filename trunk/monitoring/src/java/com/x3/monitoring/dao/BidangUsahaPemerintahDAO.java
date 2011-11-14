/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.monitoring.dao;

import com.x3.monitoring.entity.BidangUsahaPemerintah;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface BidangUsahaPemerintahDAO {
    public void insert(BidangUsahaPemerintah obj)throws Exception;
    public void update(BidangUsahaPemerintah obj)throws Exception;
    public void delete(int id)throws Exception;
    public BidangUsahaPemerintah get(int id)throws Exception;
    public List<BidangUsahaPemerintah> gets()throws Exception;

}
