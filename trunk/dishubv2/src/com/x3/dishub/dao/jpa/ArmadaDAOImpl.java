/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.dao.jpa;

import com.x3.dishub.dao.ArmadaDAO;
import com.x3.dishub.entity.Armada;
import com.x3.dishub.entity.Kendaraan;
import com.x3.dishub.entity.Trayek;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class ArmadaDAOImpl extends GeneralDAOImpl implements ArmadaDAO {

    public List<Armada> getByKendaraan(Kendaraan kendaraan) {
        return getJpaTemplate().find("SELECT a FROM Armada a WHERE a.kendaraan=?1",kendaraan);
    }

    public List<Armada> getByTrayek(Trayek trayek) {
        return getJpaTemplate().find("SELECT a FROM Armada a WHERE a.trayek=?1", trayek);
    }

}
