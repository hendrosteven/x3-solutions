/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.dao;

import com.x3.dishub.entity.Trayek;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface TrayekDAO extends GeneralDAO {
    public List<Trayek> getAllTrayek();
    public Trayek getById(long id);
    
    
}
