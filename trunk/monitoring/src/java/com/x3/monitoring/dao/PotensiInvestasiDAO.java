/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.dao;

import com.x3.monitoring.entity.PotensiInvestasi;

/**
 *
 * @author Hendro Steven
 */
public interface PotensiInvestasiDAO {

    public void insert(PotensiInvestasi pi) throws Exception;

    public void update(PotensiInvestasi pi) throws Exception;

    public PotensiInvestasi get() throws Exception;
}
