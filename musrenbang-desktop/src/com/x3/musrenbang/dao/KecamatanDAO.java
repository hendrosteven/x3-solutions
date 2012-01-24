/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.musrenbang.dao;

import com.x3.musrenbang.entity.Kecamatan;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface KecamatanDAO extends GeneralDAO {
    public Kecamatan getKecamatan(int id);
    public List<Kecamatan> gets();
}
