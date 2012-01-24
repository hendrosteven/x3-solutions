/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.musrenbang.dao;

import com.x3.musrenbang.entity.Program;
import com.x3.musrenbang.entity.Skpd;
import com.x3.musrenbang.entity.Urusan;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface ProgramDAO extends GeneralDAO {
    public Program get(int id);
    public List<Program> gets();
    public List<Program> gets(Urusan urusan);
    public List<Program> gets(Urusan urusan,Skpd skpd);
}
