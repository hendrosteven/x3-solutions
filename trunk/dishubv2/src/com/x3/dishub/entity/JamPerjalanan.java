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

/**
 *
 * @author Hendro Steven
 */
@Entity
public class JamPerjalanan implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tempatSinggah;
    private String jamTiba;
    private String jamBerangkat;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

   

    @Override
    public String toString() {
        return this.getTempatSinggah()+" "+this.getJamBerangkat()+" "+this.getJamTiba();
    }

    /**
     * @return the tempatSinggah
     */
    public String getTempatSinggah() {
        return tempatSinggah;
    }

    /**
     * @param tempatSinggah the tempatSinggah to set
     */
    public void setTempatSinggah(String tempatSinggah) {
        this.tempatSinggah = tempatSinggah;
    }

    /**
     * @return the jamTiba
     */
    public String getJamTiba() {
        return jamTiba;
    }

    /**
     * @param jamTiba the jamTiba to set
     */
    public void setJamTiba(String jamTiba) {
        this.jamTiba = jamTiba;
    }

    /**
     * @return the jamBerangkat
     */
    public String getJamBerangkat() {
        return jamBerangkat;
    }

    /**
     * @param jamBerangkat the jamBerangkat to set
     */
    public void setJamBerangkat(String jamBerangkat) {
        this.jamBerangkat = jamBerangkat;
    }

}
