/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.musrenbang.dao;

import com.x3.musrenbang.entity.Kegiatan;
import com.x3.musrenbang.entity.Program;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface KegiatanDAO extends GeneralDAO {

    public Kegiatan get(int id);

    public List<Kegiatan> gets();

    public List<Kegiatan> gets(Program program);
}
