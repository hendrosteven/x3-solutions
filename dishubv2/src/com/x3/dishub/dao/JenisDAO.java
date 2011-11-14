/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.dao;

import com.x3.dishub.entity.Jenis;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface JenisDAO extends GeneralDAO {
    public List<Jenis> getAllJenis();
    public Jenis getById(long id);
}
