/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.dao;

import com.x3.dishub.entity.Merk;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface MerkDAO extends GeneralDAO{
    public List<Merk> getAllMerk();
    public Merk getById(long id);
}
