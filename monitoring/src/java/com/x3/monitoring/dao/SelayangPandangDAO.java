/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.monitoring.dao;

import com.x3.monitoring.entity.SelayangPandang;

/**
 *
 * @author Hendro Steven
 */
public interface SelayangPandangDAO {
    public void insert(SelayangPandang sp)throws Exception;
    public void update(SelayangPandang sp)throws Exception;
    public SelayangPandang get()throws Exception;
}
