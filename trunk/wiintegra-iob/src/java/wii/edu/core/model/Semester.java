/*
 * Semester.java
 *
 * Created on July 11, 2007, 2:09 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package wii.edu.core.model;

/**
 *
 * @author Hendro
 */
public class Semester {
    private long id;
    private int nama;
    private String tahunAjaran;
    private String batasRegistrasi;
    
    /** Creates a new instance of Semester */
    public Semester() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNama() {
        return nama;
    }

    public void setNama(int nama) {
        this.nama = nama;
    }

    public String getTahunAjaran() {
        return tahunAjaran;
    }

    public void setTahunAjaran(String tahunAjaran) {
        this.tahunAjaran = tahunAjaran;
    }

    public String getBatasRegistrasi() {
        return batasRegistrasi;
    }

    public void setBatasRegistrasi(String batasRegistrasi) {
        this.batasRegistrasi = batasRegistrasi;
    }
    
}
