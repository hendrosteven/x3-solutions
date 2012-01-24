/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.musrenbang.dao;

import com.x3.musrenbang.entity.Urusan;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface UrusanDAO extends GeneralDAO{
    public Urusan get(int id);
    public List<Urusan> gets();
}
