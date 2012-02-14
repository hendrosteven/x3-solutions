/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.musrenbang.entity;

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
public class Kelurahan implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nama;
    @ManyToOne
    private Kecamatan kecamatan;

    public Long getId() {
        return id;
    }

     
    public void setId(Long id) {
        this.id = id;
    }

    

    @Override
    public String toString() {
        return this.nama;
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
     * @return the kecamatan
     */
    public Kecamatan getKecamatan() {
        return kecamatan;
    }

    /**
     * @param kecamatan the kecamatan to set
     */
    public void setKecamatan(Kecamatan kecamatan) {
        this.kecamatan = kecamatan;
    }

}
