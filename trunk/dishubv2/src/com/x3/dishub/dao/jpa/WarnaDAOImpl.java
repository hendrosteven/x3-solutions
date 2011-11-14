/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.dao.jpa;

import com.x3.dishub.dao.WarnaDAO;
import com.x3.dishub.entity.Warna;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class WarnaDAOImpl extends GeneralDAOImpl implements WarnaDAO {

    public Warna getByNama(String nama) {
        List<Warna> list = getJpaTemplate().find("SELECT w FROM Warna w WHERE w.name=?1", nama);
        if(list.size()>0){
            return list.get(0);
        }else{
            return null;
        }
    }

}
