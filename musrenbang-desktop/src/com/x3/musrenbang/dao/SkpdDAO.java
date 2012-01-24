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
public interface SkpdDAO extends GeneralDAO {
    public Skpd getSkpd(int id);
    public List<Skpd> gets();
    public List<Skpd> gets(Bidang bidang);
}
