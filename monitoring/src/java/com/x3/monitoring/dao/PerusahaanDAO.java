/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.dao;

import com.x3.monitoring.entity.BadanHukum;
import com.x3.monitoring.entity.BentukModal;
import com.x3.monitoring.entity.BidangUsaha;
import com.x3.monitoring.entity.Kecamatan;
import com.x3.monitoring.entity.Perusahaan;
import com.x3.monitoring.entity.StatusPerusahaan;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface PerusahaanDAO {

    public void insert(Perusahaan p) throws Exception;

    public void update(Perusahaan p) throws Exception;

    public void delete(String id) throws Exception;

    public Perusahaan get(String id) throws Exception;

    public List<Perusahaan> gets() throws Exception;

    public List<Perusahaan> gets(String key) throws Exception;

    public List<Perusahaan> gets(Kecamatan kecamatan)throws Exception;

    public List<Perusahaan> gets(BadanHukum bh)throws Exception;

    public List<Perusahaan> gets(BentukModal bh)throws Exception;

    public List<Perusahaan> gets(BidangUsaha bu)throws Exception;

    public List<Perusahaan> gets(StatusPerusahaan sp)throws Exception;

    public List<Perusahaan> getsPerusahaan(String sql)throws Exception;

    public List<Perusahaan> gets(double nilaiInvestasi, boolean isLebihBesar)throws Exception;

    public List<Perusahaan> gets(double nilaiInvestasiA, double nilaiInvestasiB)throws Exception;
}
