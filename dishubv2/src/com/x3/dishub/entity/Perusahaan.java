/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Hendro Steven
 */
@Entity
public class Perusahaan implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nama;
    private String namaPimpinan;
    @ManyToOne
    private BentukBadanUsaha badanUsaha;
    private String alamat;
    private String telp;
    private String email;
    @ManyToOne
    private KabupatenKota kabKota;
    @ManyToOne
    private Provinsi provinsi;
    private String npwp;
    
    private String noSKNotaris;
    private String tahunBerdiri;
    private String website;
    



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

   

    @Override
    public String toString() {
        return this.getNama();
    }

    /**
     * @return the nama
     */
    public String getNama() {
        return nama;
    }

    /**
     * @param nama the nama to set
     */
    public void setNama(String nama) {
        this.nama = nama;
    }

    /**
     * @return the namaPimpinan
     */
    public String getNamaPimpinan() {
        return namaPimpinan;
    }

    /**
     * @param namaPimpinan the namaPimpinan to set
     */
    public void setNamaPimpinan(String namaPimpinan) {
        this.namaPimpinan = namaPimpinan;
    }

    /**
     * @return the badanUsaha
     */
    public BentukBadanUsaha getBadanUsaha() {
        return badanUsaha;
    }

    /**
     * @param badanUsaha the badanUsaha to set
     */
    public void setBadanUsaha(BentukBadanUsaha badanUsaha) {
        this.badanUsaha = badanUsaha;
    }

    /**
     * @return the alamat
     */
    public String getAlamat() {
        return alamat;
    }

    /**
     * @param alamat the alamat to set
     */
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    /**
     * @return the telp
     */
    public String getTelp() {
        return telp;
    }

    /**
     * @param telp the telp to set
     */
    public void setTelp(String telp) {
        this.telp = telp;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the kabKota
     */
    public KabupatenKota getKabKota() {
        return kabKota;
    }

    /**
     * @param kabKota the kabKota to set
     */
    public void setKabKota(KabupatenKota kabKota) {
        this.kabKota = kabKota;
    }

    /**
     * @return the provinsi
     */
    public Provinsi getProvinsi() {
        return provinsi;
    }

    /**
     * @param provinsi the provinsi to set
     */
    public void setProvinsi(Provinsi provinsi) {
        this.provinsi = provinsi;
    }

    /**
     * @return the npwp
     */
    public String getNpwp() {
        return npwp;
    }

    /**
     * @param npwp the npwp to set
     */
    public void setNpwp(String npwp) {
        this.npwp = npwp;
    }

    

    /**
     * @return the noSKNotaris
     */
    public String getNoSKNotaris() {
        return noSKNotaris;
    }

    /**
     * @param noSKNotaris the noSKNotaris to set
     */
    public void setNoSKNotaris(String noSKNotaris) {
        this.noSKNotaris = noSKNotaris;
    }

    /**
     * @return the tahunBerdiri
     */
    public String getTahunBerdiri() {
        return tahunBerdiri;
    }

    /**
     * @param tahunBerdiri the tahunBerdiri to set
     */
    public void setTahunBerdiri(String tahunBerdiri) {
        this.tahunBerdiri = tahunBerdiri;
    }

    /**
     * @return the website
     */
    public String getWebsite() {
        return website;
    }

    /**
     * @param website the website to set
     */
    public void setWebsite(String website) {
        this.website = website;
    }

}
