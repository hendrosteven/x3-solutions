/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.dao;

import com.x3.dishub.entity.Pemilik;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface PemilikDAO extends GeneralDAO{
    public List<Pemilik> getAllPemilik();
    public List<Pemilik> getByName(String nama);
}
