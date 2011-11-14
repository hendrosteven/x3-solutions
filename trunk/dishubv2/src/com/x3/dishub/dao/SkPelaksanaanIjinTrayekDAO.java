/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.dao;

import com.x3.dishub.entity.Perusahaan;
import com.x3.dishub.entity.SkPelaksanaanIjinTrayek;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface SkPelaksanaanIjinTrayekDAO extends GeneralDAO {
    public List<SkPelaksanaanIjinTrayek> getAllIjin();
    public List<SkPelaksanaanIjinTrayek> getByNomor(String nomor);
    public List<SkPelaksanaanIjinTrayek> getByPerusahaan(Perusahaan perusahaan);
}
