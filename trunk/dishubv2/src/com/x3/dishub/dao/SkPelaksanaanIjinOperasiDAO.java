/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.dao;

import com.x3.dishub.entity.Perusahaan;
import com.x3.dishub.entity.SkPelaksanaanIjinOperasi;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface SkPelaksanaanIjinOperasiDAO extends GeneralDAO{
    public List<SkPelaksanaanIjinOperasi> getAllIjin();
    public List<SkPelaksanaanIjinOperasi> getByNomor(String nomor);
    public List<SkPelaksanaanIjinOperasi> getByPerusahaan(Perusahaan perusahaan);
}
