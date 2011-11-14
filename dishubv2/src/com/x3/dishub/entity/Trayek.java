/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 *
 * @author Hendro Steven
 */
@Entity
public class Trayek implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String kode;
    private String nama;
    private String dasarHukum;
    private String keterangan;
    @Lob
    @Basic(fetch=FetchType.LAZY)
    private byte[] peta;

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
     * @return the kode
     */
    public String getKode() {
        return kode;
    }

    /**
     * @param kode the kode to set
     */
    public void setKode(String kode) {
        this.kode = kode;
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
     * @return the dasarHukum
     */
    public String getDasarHukum() {
        return dasarHukum;
    }

    /**
     * @param dasarHukum the dasarHukum to set
     */
    public void setDasarHukum(String dasarHukum) {
        this.dasarHukum = dasarHukum;
    }

    /**
     * @return the keterangan
     */
    public String getKeterangan() {
        return keterangan;
    }

    /**
     * @param keterangan the keterangan to set
     */
    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    /**
     * @return the peta
     */
    public byte[] getPeta() {
        return peta;
    }

    /**
     * @param peta the peta to set
     */
    public void setPeta(byte[] peta) {
        this.peta = peta;
    }

}
