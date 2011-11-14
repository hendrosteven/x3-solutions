/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.monitoring.entity;

/**
 *
 * @author Hendro Steven
 */
public class BidangUsaha {
    private int id;
    private Tahun tahun;
    private String kbli;
    private String jenis;

    public BidangUsaha(){}

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the tahun
     */
    public Tahun getTahun() {
        return tahun;
    }

    /**
     * @param tahun the tahun to set
     */
    public void setTahun(Tahun tahun) {
        this.tahun = tahun;
    }

    /**
     * @return the kbli
     */
    public String getKbli() {
        return kbli;
    }

    /**
     * @param kbli the kbli to set
     */
    public void setKbli(String kbli) {
        this.kbli = kbli;
    }

    /**
     * @return the jenis
     */
    public String getJenis() {
        return jenis;
    }

    /**
     * @param jenis the jenis to set
     */
    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    
}
