/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Hendro Steven
 */
@Entity
public class IjinUsahaAngkutanOrang implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomor;
    @Temporal(value = javax.persistence.TemporalType.DATE)
    private Date tanggal;
    @ManyToOne
    private Perusahaan perusahaan;
    private String nomorSuratPermohonan;
    @Temporal(value = javax.persistence.TemporalType.DATE)
    private Date tglSuratPermohonan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    

    @Override
    public String toString() {
        return this.getNomor();
    }

    /**
     * @return the nomor
     */
    public String getNomor() {
        return nomor;
    }

    /**
     * @param nomor the nomor to set
     */
    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    /**
     * @return the tanggal
     */
    public Date getTanggal() {
        return tanggal;
    }

    /**
     * @param tanggal the tanggal to set
     */
    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    /**
     * @return the perusahaan
     */
    public Perusahaan getPerusahaan() {
        return perusahaan;
    }

    /**
     * @param perusahaan the perusahaan to set
     */
    public void setPerusahaan(Perusahaan perusahaan) {
        this.perusahaan = perusahaan;
    }

    /**
     * @return the nomorSuratPermohonan
     */
    public String getNomorSuratPermohonan() {
        return nomorSuratPermohonan;
    }

    /**
     * @param nomorSuratPermohonan the nomorSuratPermohonan to set
     */
    public void setNomorSuratPermohonan(String nomorSuratPermohonan) {
        this.nomorSuratPermohonan = nomorSuratPermohonan;
    }

    /**
     * @return the tglSuratPermohonan
     */
    public Date getTglSuratPermohonan() {
        return tglSuratPermohonan;
    }

    /**
     * @param tglSuratPermohonan the tglSuratPermohonan to set
     */
    public void setTglSuratPermohonan(Date tglSuratPermohonan) {
        this.tglSuratPermohonan = tglSuratPermohonan;
    }

}
