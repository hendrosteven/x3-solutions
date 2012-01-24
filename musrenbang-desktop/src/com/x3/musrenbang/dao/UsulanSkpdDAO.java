/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.musrenbang.dao;

import com.x3.musrenbang.entity.Skpd;
import com.x3.musrenbang.entity.TahunAnggaran;
import com.x3.musrenbang.entity.UsulanKecamatan;
import com.x3.musrenbang.entity.UsulanSkpd;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface UsulanSkpdDAO extends GeneralDAO {

    public UsulanSkpd get(int id);

    public UsulanSkpd getByUsulanKecamatan(UsulanKecamatan usulan);

    public List<UsulanSkpd> gets();

    public List<UsulanSkpd> gets(TahunAnggaran tahun);

    public List<UsulanSkpd> gets(Skpd skpd);

    public List<UsulanSkpd> gets(TahunAnggaran tahun, Skpd skpd);
}
