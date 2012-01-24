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
public class JenisSumberDana implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nama;
    @ManyToOne
    private SumberDana sumberDana;

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
     * @return the sumberDana
     */
    public SumberDana getSumberDana() {
        return sumberDana;
    }

    /**
     * @param sumberDana the sumberDana to set
     */
    public void setSumberDana(SumberDana sumberDana) {
        this.sumberDana = sumberDana;
    }

}
