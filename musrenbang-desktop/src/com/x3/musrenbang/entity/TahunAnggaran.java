/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.musrenbang.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 *
 * @author Hendro Steven
 */
@Entity
public class TahunAnggaran implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tahun;
    private String keterangan;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date inputMulai;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date inputSelesai;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

   

    @Override
    public String toString() {
        return this.getTahun();
    }

    /**
     * @return the tahun
     */
    public String getTahun() {
        return tahun;
    }

    /**
     * @param tahun the tahun to set
     */
    public void setTahun(String tahun) {
        this.tahun = tahun;
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
     * @return the inputMulai
     */
    public Date getInputMulai() {
        return inputMulai;
    }

    /**
     * @param inputMulai the inputMulai to set
     */
    public void setInputMulai(Date inputMulai) {
        this.inputMulai = inputMulai;
    }

    /**
     * @return the inputSelesai
     */
    public Date getInputSelesai() {
        return inputSelesai;
    }

    /**
     * @param inputSelesai the inputSelesai to set
     */
    public void setInputSelesai(Date inputSelesai) {
        this.inputSelesai = inputSelesai;
    }

}
