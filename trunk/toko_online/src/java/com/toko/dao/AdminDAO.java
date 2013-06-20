/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.toko.dao;

import com.toko.model.Admin;

/**
 *
 * @author user
 */
public interface AdminDAO {

    public Admin login(String user, String pass) 
            throws Exception;
}
