/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.dao;

import com.x3.dishub.entity.Perusahaan;
import com.x3.dishub.entity.Spio;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface SpioDAO extends GeneralDAO{
    public List<Spio> getAllSpio();
    public List<Spio> getByNomor(String nomor);
    public List<Spio> getByPerusahaan(Perusahaan perusahaan);
    
}
