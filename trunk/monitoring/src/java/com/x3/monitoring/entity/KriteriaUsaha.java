/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.monitoring.entity;

/**
 *
 * @author Hendro Steven
 */
public class KriteriaUsaha {
    private int id;
    private String keterangan;
    private String operator1;
    private int nilai1;
    private String operator2;
    private int nilai2;

    public KriteriaUsaha(){}

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
     * @return the operator1
     */
    public String getOperator1() {
        return operator1;
    }

    /**
     * @param operator1 the operator1 to set
     */
    public void setOperator1(String operator1) {
        this.operator1 = operator1;
    }

    /**
     * @return the nilai1
     */
    public int getNilai1() {
        return nilai1;
    }

    /**
     * @param nilai1 the nilai1 to set
     */
    public void setNilai1(int nilai1) {
        this.nilai1 = nilai1;
    }

    /**
     * @return the operator2
     */
    public String getOperator2() {
        return operator2;
    }

    /**
     * @param operator2 the operator2 to set
     */
    public void setOperator2(String operator2) {
        this.operator2 = operator2;
    }

    /**
     * @return the nilai2
     */
    public int getNilai2() {
        return nilai2;
    }

    /**
     * @param nilai2 the nilai2 to set
     */
    public void setNilai2(int nilai2) {
        this.nilai2 = nilai2;
    }

    
}
