/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.musrenbang.dao.jpa;

import com.x3.musrenbang.dao.ProgramDAO;
import com.x3.musrenbang.entity.Program;
import com.x3.musrenbang.entity.Skpd;
import com.x3.musrenbang.entity.Urusan;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class ProgramDAOImpl extends GeneralDAOImpl implements ProgramDAO {

    public Program get(int id) {
        return getJpaTemplate().find(Program.class, id);
    }

    public List<Program> gets() {
        return getJpaTemplate().find("select p from Program p");
    }

    public List<Program> gets(Urusan urusan) {
        return getJpaTemplate().find("select p from Program p where p.bidang.urusan=?1", urusan);
    }

    public List<Program> gets(Urusan urusan, Skpd skpd) {
        //return getJpaTemplate().find("select p from Program p where p.bidang.urusan=?1 and ")
        return null;
    }

}
