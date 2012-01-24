/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.musrenbang.dao;

import com.x3.musrenbang.entity.Kecamatan;
import com.x3.musrenbang.entity.TahunAnggaran;
import com.x3.musrenbang.entity.UsulanKecamatan;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public interface UsulanKecamatanDAO extends GeneralDAO {

    public UsulanKecamatan get(int id);

    public List<UsulanKecamatan> gets();

    public List<UsulanKecamatan> gets(TahunAnggaran tahun);

    public List<UsulanKecamatan> gets(TahunAnggaran tahun, Kecamatan kec);

    public List<UsulanKecamatan> gets(Kecamatan kec);
}
