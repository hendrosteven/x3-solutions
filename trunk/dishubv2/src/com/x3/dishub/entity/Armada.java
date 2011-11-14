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
public class Armada implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Kendaraan kendaraan;
    @ManyToOne
    private Trayek trayek;    
    @ManyToOne
    private KelasPelayanan kelasPelayanan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    

    @Override
    public String toString() {
        return this.getKendaraan().getNomorPolisi();
    }

    /**
     * @return the kendaraan
     */
    public Kendaraan getKendaraan() {
        return kendaraan;
    }

    /**
     * @param kendaraan the kendaraan to set
     */
    public void setKendaraan(Kendaraan kendaraan) {
        this.kendaraan = kendaraan;
    }

    /**
     * @return the trayek
     */
    public Trayek getTrayek() {
        return trayek;
    }

    /**
     * @param trayek the trayek to set
     */
    public void setTrayek(Trayek trayek) {
        this.trayek = trayek;
    }

        

    /**
     * @return the kelasPelayanan
     */
    public KelasPelayanan getKelasPelayanan() {
        return kelasPelayanan;
    }

    /**
     * @param kelasPelayanan the kelasPelayanan to set
     */
    public void setKelasPelayanan(KelasPelayanan kelasPelayanan) {
        this.kelasPelayanan = kelasPelayanan;
    }

}
