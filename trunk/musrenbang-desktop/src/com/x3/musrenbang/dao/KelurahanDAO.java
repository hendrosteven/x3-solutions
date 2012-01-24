/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.musrenbang.dao;

import com.x3.musrenbang.entity.Kecamatan;
import com.x3.musrenbang.entity.Kelurahan;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface KelurahanDAO extends GeneralDAO{
    public Kelurahan get(int id);
    public List<Kelurahan> gets();
    public List<Kelurahan> gets(Kecamatan kec);
}
