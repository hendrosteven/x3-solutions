/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wii.edu.core.model;

import java.util.Date;

/**
 *
 * @author Retha@wii
 */
public class DataKelulusan {
    private long id;
    private Date tglUjian;
    private Date tglYudisium;
    private String judulSkripsi;
    private String nilai;
    private String predikat;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJudulSkripsi() {
        return judulSkripsi;
    }

    public void setJudulSkripsi(String judulSkripsi) {
        this.judulSkripsi = judulSkripsi;
    }

    public String getNilai() {
        return nilai;
    }

    public void setNilai(String nilai) {
        this.nilai = nilai;
    }

    public String getPredikat() {
        return predikat;
    }

    public void setPredikat(String predikat) {
        this.predikat = predikat;
    }

    public Date getTglUjian() {
        return tglUjian;
    }

    public void setTglUjian(Date tglUjian) {
        this.tglUjian = tglUjian;
    }

    public Date getTglYudisium() {
        return tglYudisium;
    }

    public void setTglYudisium(Date tglYudisium) {
        this.tglYudisium = tglYudisium;
    }
}
