/*
 * Angkatan.java
 *
 * Created on July 12, 2007, 1:01 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package wii.edu.core.model;

/**
 *
 * @author Hendro
 */
public class Angkatan {
    private long id;
    private String nama;
    private String keterangan;
    private double biayaPerSks;
    
    
    /** Creates a new instance of Angkatan */
    public Angkatan() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public double getBiayaPerSks() {
        return biayaPerSks;
    }

    public void setBiayaPerSks(double biayaPerSks) {
        this.biayaPerSks = biayaPerSks;
    }
    
}
