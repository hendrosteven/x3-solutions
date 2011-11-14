/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.dao.jpa;

import com.x3.dishub.dao.GeneralDAO;
import org.springframework.orm.jpa.support.JpaDaoSupport;

/**
 *
 * @author Instructur
 */
public class GeneralDAOImpl extends JpaDaoSupport implements GeneralDAO{

    public void insert(Object obj) {
        getJpaTemplate().persist(obj);
    }

    public void update(Object obj) {
        getJpaTemplate().merge(obj);
    }

    public void delete(Object obj) {
        getJpaTemplate().remove(getJpaTemplate().merge(obj));
    }

}
