/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.dao;

import com.x3.dishub.entity.KabupatenKota;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface KabupatenKotaDAO extends GeneralDAO{
    public List<KabupatenKota> getAllKabupatenKota();

    public KabupatenKota getById(long id);
}
