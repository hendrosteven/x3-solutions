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
public class KartuPengawasanOtobisUmum implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomor;
    @Temporal(value = javax.persistence.TemporalType.DATE)
    private Date tanggal;
    @ManyToOne
    private IjinTrayek ijinTrayek;
    @ManyToOne
    private Armada armada;
    @Temporal(value = javax.persistence.TemporalType.DATE)
    private Date tanggalBerlakuMulai;
    @Temporal(value = javax.persistence.TemporalType.DATE)
    private Date tanggalBerlakuSelesai;

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
     * @return the ijinTrayek
     */
    public IjinTrayek getIjinTrayek() {
        return ijinTrayek;
    }

    /**
     * @param ijinTrayek the ijinTrayek to set
     */
    public void setIjinTrayek(IjinTrayek ijinTrayek) {
        this.ijinTrayek = ijinTrayek;
    }

    /**
     * @return the armada
     */
    public Armada getArmada() {
        return armada;
    }

    /**
     * @param armada the armada to set
     */
    public void setArmada(Armada armada) {
        this.armada = armada;
    }

    /**
     * @return the tanggalBerlakuMulai
     */
    public Date getTanggalBerlakuMulai() {
        return tanggalBerlakuMulai;
    }

    /**
     * @param tanggalBerlakuMulai the tanggalBerlakuMulai to set
     */
    public void setTanggalBerlakuMulai(Date tanggalBerlakuMulai) {
        this.tanggalBerlakuMulai = tanggalBerlakuMulai;
    }

    /**
     * @return the tanggalBerlakuSelesai
     */
    public Date getTanggalBerlakuSelesai() {
        return tanggalBerlakuSelesai;
    }

    /**
     * @param tanggalBerlakuSelesai the tanggalBerlakuSelesai to set
     */
    public void setTanggalBerlakuSelesai(Date tanggalBerlakuSelesai) {
        this.tanggalBerlakuSelesai = tanggalBerlakuSelesai;
    }

}
