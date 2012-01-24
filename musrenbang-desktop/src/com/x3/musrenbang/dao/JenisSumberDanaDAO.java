/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.musrenbang.dao;

import com.x3.musrenbang.entity.JenisSumberDana;
import com.x3.musrenbang.entity.SumberDana;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface JenisSumberDanaDAO extends GeneralDAO {

    public JenisSumberDana get(int id);

    public List<JenisSumberDana> gets();

    public List<JenisSumberDana> gets(SumberDana sd) ;
}
