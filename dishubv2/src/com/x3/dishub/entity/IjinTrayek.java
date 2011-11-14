/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author Hendro Steven
 */
@Entity
public class IjinTrayek implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomor;
    @ManyToOne
    private Perusahaan perusahaan;
    @Temporal(value = javax.persistence.TemporalType.DATE)
    private Date masaBerlakuMulai;
    @Temporal(value = javax.persistence.TemporalType.DATE)
    private Date masaBerlakuSelesai;
    @OneToMany
    private List<Armada> armadas;
    @ManyToOne
    private Spit spit;
    private String nomorSuratPermohonan;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date tanggalSuratPermohonan;


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
     * @return the masaBerlakuMulai
     */
    public Date getMasaBerlakuMulai() {
        return masaBerlakuMulai;
    }

    /**
     * @param masaBerlakuMulai the masaBerlakuMulai to set
     */
    public void setMasaBerlakuMulai(Date masaBerlakuMulai) {
        this.masaBerlakuMulai = masaBerlakuMulai;
    }

    /**
     * @return the masaBerlakuSelesai
     */
    public Date getMasaBerlakuSelesai() {
        return masaBerlakuSelesai;
    }

    /**
     * @param masaBerlakuSelesai the masaBerlakuSelesai to set
     */
    public void setMasaBerlakuSelesai(Date masaBerlakuSelesai) {
        this.masaBerlakuSelesai = masaBerlakuSelesai;
    }

    /**
     * @return the armadas
     */
    public List<Armada> getArmadas() {
        return armadas;
    }

    /**
     * @param armadas the armadas to set
     */
    public void setArmadas(List<Armada> armadas) {
        this.armadas = armadas;
    }

    /**
     * @return the spit
     */
    public Spit getSpit() {
        return spit;
    }

    /**
     * @param spit the spit to set
     */
    public void setSpit(Spit spit) {
        this.spit = spit;
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
     * @return the tanggalSuratPermohonan
     */
    public Date getTanggalSuratPermohonan() {
        return tanggalSuratPermohonan;
    }

    /**
     * @param tanggalSuratPermohonan the tanggalSuratPermohonan to set
     */
    public void setTanggalSuratPermohonan(Date tanggalSuratPermohonan) {
        this.tanggalSuratPermohonan = tanggalSuratPermohonan;
    }

}
