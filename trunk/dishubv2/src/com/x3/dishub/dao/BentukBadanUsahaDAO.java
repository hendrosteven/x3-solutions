/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.dao;

import com.x3.dishub.entity.BentukBadanUsaha;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface BentukBadanUsahaDAO extends GeneralDAO{
    public List<BentukBadanUsaha> getAllBentukBadanUsaha();
    public BentukBadanUsaha getById(long id);
}
