/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.dao;

import com.x3.dishub.entity.Perusahaan;
import com.x3.dishub.entity.Spit;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface SpitDAO extends GeneralDAO{
    public List<Spit> getAllSpit();
    public List<Spit> getByNomor(String nomor);
    public List<Spit> getByPerusahaan(Perusahaan perusahaan);
}
