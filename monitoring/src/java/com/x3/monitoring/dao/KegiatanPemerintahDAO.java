/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.dao;

import com.x3.monitoring.entity.KegiatanPemerintah;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface KegiatanPemerintahDAO {

    public void insert(KegiatanPemerintah kp) throws Exception;

    public void update(KegiatanPemerintah kp) throws Exception;

    public void delete(int id) throws Exception;

    public KegiatanPemerintah get(int id) throws Exception;

    public List<KegiatanPemerintah> gets() throws Exception;

    public List<KegiatanPemerintah> gets(String sql) throws Exception;
}
