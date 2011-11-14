/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.dao;

import com.x3.dishub.entity.Provinsi;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface ProvinsiDAO extends GeneralDAO{
    public List<Provinsi> getAllProvinsi();
    public Provinsi getById(long id);
}
