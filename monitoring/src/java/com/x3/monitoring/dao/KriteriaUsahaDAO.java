/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.monitoring.dao;

import com.x3.monitoring.entity.KriteriaUsaha;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface KriteriaUsahaDAO {
    public void insert(KriteriaUsaha ku)throws Exception;
    public void update(KriteriaUsaha ku)throws Exception;
    public void delete(int id)throws Exception;
    public KriteriaUsaha get(int id)throws Exception;
    public List<KriteriaUsaha> gets()throws Exception;
}
