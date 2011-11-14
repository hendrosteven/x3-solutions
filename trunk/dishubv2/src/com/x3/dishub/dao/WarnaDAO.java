/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.dao;

import com.x3.dishub.entity.Warna;

/**
 *
 * @author Hendro Steven
 */
public interface WarnaDAO extends GeneralDAO {
    public Warna getByNama(String nama);
}
