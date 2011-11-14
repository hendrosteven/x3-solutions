/*
 * KomponenBiaya.java
 *
 * Created on July 11, 2007, 2:11 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package wii.edu.core.model;

/**
 *
 * @author Hendro
 */
public class KomponenBiaya {
    private long id;
    private String kode;
    private String nama;
    private String keterangan;
    private double jumlah;
    private int isSKS;
    
    /** Creates a new instance of KomponenBiaya */
    public KomponenBiaya() {
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

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public double getJumlah() {
        return jumlah;
    }

    public void setJumlah(double jumlah) {
        this.jumlah = jumlah;
    }

    public int getIsSKS() {
        return isSKS;
    }

    public void setIsSKS(int isSKS) {
        this.isSKS = isSKS;
    }
    
}
