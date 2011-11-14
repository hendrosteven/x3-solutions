/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.dao;

import com.x3.dishub.entity.Armada;
import com.x3.dishub.entity.Kendaraan;
import com.x3.dishub.entity.Trayek;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface ArmadaDAO extends GeneralDAO{
    public List<Armada> getByKendaraan(Kendaraan kendaraan);
    public List<Armada> getByTrayek(Trayek trayek);
}
