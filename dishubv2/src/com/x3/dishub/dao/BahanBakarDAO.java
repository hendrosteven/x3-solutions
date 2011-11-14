/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.dao;

import com.x3.dishub.entity.BahanBakar;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface BahanBakarDAO extends GeneralDAO{
    public List<BahanBakar> getAllBahanBakar();
    public BahanBakar getById(long id);
}
