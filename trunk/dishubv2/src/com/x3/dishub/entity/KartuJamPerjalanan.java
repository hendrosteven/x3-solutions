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
public class KartuJamPerjalanan implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomor;
    @Temporal(value = javax.persistence.TemporalType.DATE)
    private Date tanggalMulaiBerlaku;
    @Temporal(value = javax.persistence.TemporalType.DATE)
    private Date tanggalMulaiBerakhir;
    @ManyToOne
    private IjinTrayek ijinTrayek;
    @ManyToOne
    private Armada armada;
    @OneToMany
    private List<JamPerjalanan> jamPerjalanan;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    
    @Override
    public String toString() {
        return getNomor();
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
     * @return the tanggalMulaiBerlaku
     */
    public Date getTanggalMulaiBerlaku() {
        return tanggalMulaiBerlaku;
    }

    /**
     * @param tanggalMulaiBerlaku the tanggalMulaiBerlaku to set
     */
    public void setTanggalMulaiBerlaku(Date tanggalMulaiBerlaku) {
        this.tanggalMulaiBerlaku = tanggalMulaiBerlaku;
    }

    /**
     * @return the tanggalMulaiBerakhir
     */
    public Date getTanggalMulaiBerakhir() {
        return tanggalMulaiBerakhir;
    }

    /**
     * @param tanggalMulaiBerakhir the tanggalMulaiBerakhir to set
     */
    public void setTanggalMulaiBerakhir(Date tanggalMulaiBerakhir) {
        this.tanggalMulaiBerakhir = tanggalMulaiBerakhir;
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
     * @return the jamPerjalanan
     */
    public List<JamPerjalanan> getJamPerjalanan() {
        return jamPerjalanan;
    }

    /**
     * @param jamPerjalanan the jamPerjalanan to set
     */
    public void setJamPerjalanan(List<JamPerjalanan> jamPerjalanan) {
        this.jamPerjalanan = jamPerjalanan;
    }

}
