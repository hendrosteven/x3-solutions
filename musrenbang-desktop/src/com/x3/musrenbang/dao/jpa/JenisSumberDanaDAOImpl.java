/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.musrenbang.dao.jpa;

import com.x3.musrenbang.dao.JenisSumberDanaDAO;
import com.x3.musrenbang.entity.JenisSumberDana;
import com.x3.musrenbang.entity.SumberDana;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class JenisSumberDanaDAOImpl extends GeneralDAOImpl implements JenisSumberDanaDAO {

    public JenisSumberDana get(int id) {
        return getJpaTemplate().find(JenisSumberDana.class, id);
    }

    public List<JenisSumberDana> gets() {
        return getJpaTemplate().find("Select j From JenisSumberDana j");
    }

    public List<JenisSumberDana> gets(SumberDana sd) {
        return getJpaTemplate().find("Select j From JenisSumberDana j Where j.sumberDana=?1", sd);
    }
}
