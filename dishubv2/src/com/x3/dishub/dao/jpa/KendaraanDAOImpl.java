/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.dishub.dao.jpa;

import com.x3.dishub.dao.KendaraanDAO;
import com.x3.dishub.entity.Kendaraan;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class KendaraanDAOImpl extends GeneralDAOImpl implements KendaraanDAO {

    public List<Kendaraan> getByNomorPolisi(String nomor) {
        return getJpaTemplate().find("SELECT k FROM Kendaraan k WHERE k.nomorPolisi LIKE ?1", "%"+nomor+"%");
//        if(list.size()>0)
//            return list.get(0);
//        else return null;
    }

    public List<Kendaraan> getAll() {
        return getJpaTemplate().find("SELECT k FROM Kendaraan k");
    }
}
