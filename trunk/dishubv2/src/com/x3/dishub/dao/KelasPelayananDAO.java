/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.dao;

import com.x3.dishub.entity.KelasPelayanan;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface KelasPelayananDAO extends GeneralDAO{
    public List<KelasPelayanan> getAllKelasPelayanan();
    public KelasPelayanan getById(long id);
}
