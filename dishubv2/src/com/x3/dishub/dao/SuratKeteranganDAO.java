/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.dao;

import com.x3.dishub.entity.SuratKeterangan;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface SuratKeteranganDAO extends GeneralDAO {
    public List<SuratKeterangan> getAllSuratKeterangan();
    public List<SuratKeterangan> getByNomor(String nomor);
}
