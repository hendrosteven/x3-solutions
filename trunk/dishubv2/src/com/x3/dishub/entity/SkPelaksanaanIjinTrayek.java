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
public class SkPelaksanaanIjinTrayek implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomor;
    @Temporal(value = javax.persistence.TemporalType.DATE)
    private Date tanggal;
    @ManyToOne
    private IjinTrayek ijinTrayek;
    @OneToMany
    private List<Armada> armadas;

    
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

}
