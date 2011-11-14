/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.dishub.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author Hendro Steven
 */
@Entity
public class Kendaraan implements Serializable {
    private static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomorPolisi;
    private String nomorUji;
    private String nomorMesin;
    private String nomorRangka;
    private String tahunPembuatan;
    private String tahunPerakitan;
    private String isiSilinder;
    private String warna;
    @ManyToOne
    private BahanBakar bahanBakar;
    private String nomorBPKB;
    private String warnaTNKB;
    private String tahunRegistrasi;
    @ManyToOne
    private Merk merk;
    private String type;
    @ManyToOne
    private Jenis jenis;
    private String model;    
    @ManyToMany
    private List<Pemilik> pemilik;
    private int dayaAngkutOrang;
    private int dayaAngkutBarang;

    @OneToOne
    @JoinColumn(nullable=true)
    private Kendaraan peremajaanUntuk;

    private boolean isAktif;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    

    @Override
    public String toString() {
        return "";
    }

    /**
     * @return the nomorUji
     */
    public String getNomorUji() {
        return nomorUji;
    }

    /**
     * @param nomorUji the nomorUji to set
     */
    public void setNomorUji(String nomorUji) {
        this.nomorUji = nomorUji;
    }

    /**
     * @return the nomorMesin
     */
    public String getNomorMesin() {
        return nomorMesin;
    }

    /**
     * @param nomorMesin the nomorMesin to set
     */
    public void setNomorMesin(String nomorMesin) {
        this.nomorMesin = nomorMesin;
    }

    /**
     * @return the nomorRangka
     */
    public String getNomorRangka() {
        return nomorRangka;
    }

    /**
     * @param nomorRangka the nomorRangka to set
     */
    public void setNomorRangka(String nomorRangka) {
        this.nomorRangka = nomorRangka;
    }

    /**
     * @return the tahunPembuatan
     */
    public String getTahunPembuatan() {
        return tahunPembuatan;
    }

    /**
     * @param tahunPembuatan the tahunPembuatan to set
     */
    public void setTahunPembuatan(String tahunPembuatan) {
        this.tahunPembuatan = tahunPembuatan;
    }

    /**
     * @return the tahunPerakitan
     */
    public String getTahunPerakitan() {
        return tahunPerakitan;
    }

    /**
     * @param tahunPerakitan the tahunPerakitan to set
     */
    public void setTahunPerakitan(String tahunPerakitan) {
        this.tahunPerakitan = tahunPerakitan;
    }

    /**
     * @return the isiSilinder
     */
    public String getIsiSilinder() {
        return isiSilinder;
    }

    /**
     * @param isiSilinder the isiSilinder to set
     */
    public void setIsiSilinder(String isiSilinder) {
        this.isiSilinder = isiSilinder;
    }

    /**
     * @return the warna
     */
    public String getWarna() {
        return warna;
    }

    /**
     * @param warna the warna to set
     */
    public void setWarna(String warna) {
        this.warna = warna;
    }

    /**
     * @return the bahanBakar
     */
    public BahanBakar getBahanBakar() {
        return bahanBakar;
    }

    /**
     * @param bahanBakar the bahanBakar to set
     */
    public void setBahanBakar(BahanBakar bahanBakar) {
        this.bahanBakar = bahanBakar;
    }

    /**
     * @return the nomorBPKB
     */
    public String getNomorBPKB() {
        return nomorBPKB;
    }

    /**
     * @param nomorBPKB the nomorBPKB to set
     */
    public void setNomorBPKB(String nomorBPKB) {
        this.nomorBPKB = nomorBPKB;
    }

    /**
     * @return the warnaTNKB
     */
    public String getWarnaTNKB() {
        return warnaTNKB;
    }

    /**
     * @param warnaTNKB the warnaTNKB to set
     */
    public void setWarnaTNKB(String warnaTNKB) {
        this.warnaTNKB = warnaTNKB;
    }

    /**
     * @return the tahunRegistrasi
     */
    public String getTahunRegistrasi() {
        return tahunRegistrasi;
    }

    /**
     * @param tahunRegistrasi the tahunRegistrasi to set
     */
    public void setTahunRegistrasi(String tahunRegistrasi) {
        this.tahunRegistrasi = tahunRegistrasi;
    }

    /**
     * @return the merk
     */
    public Merk getMerk() {
        return merk;
    }

    /**
     * @param merk the merk to set
     */
    public void setMerk(Merk merk) {
        this.merk = merk;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the jenis
     */
    public Jenis getJenis() {
        return jenis;
    }

    /**
     * @param jenis the jenis to set
     */
    public void setJenis(Jenis jenis) {
        this.jenis = jenis;
    }

    /**
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * @param model the model to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    
    /**
     * @return the pemilik
     */
    public List<Pemilik> getPemilik() {
        return pemilik;
    }

    /**
     * @param pemilik the pemilik to set
     */
    public void setPemilik(List<Pemilik> pemilik) {
        this.pemilik = pemilik;
    }

    /**
     * @return the dayaAngkutOrang
     */
    public int getDayaAngkutOrang() {
        return dayaAngkutOrang;
    }

    /**
     * @param dayaAngkutOrang the dayaAngkutOrang to set
     */
    public void setDayaAngkutOrang(int dayaAngkutOrang) {
        this.dayaAngkutOrang = dayaAngkutOrang;
    }

    /**
     * @return the dayaAngkutBarang
     */
    public int getDayaAngkutBarang() {
        return dayaAngkutBarang;
    }

    /**
     * @param dayaAngkutBarang the dayaAngkutBarang to set
     */
    public void setDayaAngkutBarang(int dayaAngkutBarang) {
        this.dayaAngkutBarang = dayaAngkutBarang;
    }

    /**
     * @return the nomorPolisi
     */
    public String getNomorPolisi() {
        return nomorPolisi;
    }

    /**
     * @param nomorPolisi the nomorPolisi to set
     */
    public void setNomorPolisi(String nomorPolisi) {
        this.nomorPolisi = nomorPolisi;
    }

    /**
     * @return the peremajaanUntuk
     */
    public Kendaraan getPeremajaanUntuk() {
        return peremajaanUntuk;
    }

    /**
     * @param peremajaanUntuk the peremajaanUntuk to set
     */
    public void setPeremajaanUntuk(Kendaraan peremajaanUntuk) {
        this.peremajaanUntuk = peremajaanUntuk;
    }

    /**
     * @return the isAktif
     */
    public boolean isIsAktif() {
        return isAktif;
    }

    /**
     * @param isAktif the isAktif to set
     */
    public void setIsAktif(boolean isAktif) {
        this.isAktif = isAktif;
    }

}
