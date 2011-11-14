/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.dao;

import com.x3.monitoring.entity.BidangUsaha;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface BidangUsahaDAO {

    public void insert(BidangUsaha bu) throws Exception;

    public void update(BidangUsaha bu) throws Exception;

    public void delete(int id) throws Exception;

    public BidangUsaha get(int id) throws Exception;

    public BidangUsaha get(String kbli) throws Exception;

    public List<BidangUsaha> gets() throws Exception;

    public List<BidangUsaha> gets(int tahun) throws Exception;

    public List<BidangUsaha> gets(String key) throws Exception;
}
