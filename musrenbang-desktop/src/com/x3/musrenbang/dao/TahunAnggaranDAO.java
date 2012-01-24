/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.musrenbang.dao;

import com.x3.musrenbang.entity.TahunAnggaran;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface TahunAnggaranDAO extends GeneralDAO {

    public TahunAnggaran get(int id);

    public List<TahunAnggaran> gets();
}
