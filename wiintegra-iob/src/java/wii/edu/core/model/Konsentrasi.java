/*
 * Konsentrasi.java
 *
 * Created on August 13, 2007, 10:04 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package wii.edu.core.model;

/**
 *
 * @author Hendro
 */
public class Konsentrasi {
    private long id;
    private String kode;
    private String nama;
    private Fakultas fakultas;
    /** Creates a new instance of Konsentrasi */
    public Konsentrasi() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Fakultas getFakultas() {
        return fakultas;
    }

    public void setFakultas(Fakultas fakultas) {
        this.fakultas = fakultas;
    }
    
}
