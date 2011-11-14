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
public class IjinInsidentil implements Serializable {
    private static long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomor;
    @Temporal(value = javax.persistence.TemporalType.DATE)
    private Date tanggal;
    @ManyToOne
    private Perusahaan perusahaan;
    @ManyToOne
    private Kendaraan kendaraan;
    @ManyToOne
    private KartuPengawasanOtobisUmum kartuPengawasan;
    private String maksudPerjalanan;
    private String nomorUjiBerkala;
    private String masaBerlakuUjiBerkala;
    private int kapasitasTempatDuduk;
    @Temporal(value = javax.persistence.TemporalType.DATE)
    private Date berlakuSampai;
    private int jumlahPenumpang;
    private String rutePerjalanan;

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
     * @return the perusahaan
     */
    public Perusahaan getPerusahaan() {
        return perusahaan;
    }

    /**
     * @param perusahaan the perusahaan to set
     */
    public void setPerusahaan(Perusahaan perusahaan) {
        this.perusahaan = perusahaan;
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
     * @return the kartuPengawasan
     */
    public KartuPengawasanOtobisUmum getKartuPengawasan() {
        return kartuPengawasan;
    }

    /**
     * @param kartuPengawasan the kartuPengawasan to set
     */
    public void setKartuPengawasan(KartuPengawasanOtobisUmum kartuPengawasan) {
        this.kartuPengawasan = kartuPengawasan;
    }

    /**
     * @return the maksudPerjalanan
     */
    public String getMaksudPerjalanan() {
        return maksudPerjalanan;
    }

    /**
     * @param maksudPerjalanan the maksudPerjalanan to set
     */
    public void setMaksudPerjalanan(String maksudPerjalanan) {
        this.maksudPerjalanan = maksudPerjalanan;
    }

    /**
     * @return the nomorUjiBerkala
     */
    public String getNomorUjiBerkala() {
        return nomorUjiBerkala;
    }

    /**
     * @param nomorUjiBerkala the nomorUjiBerkala to set
     */
    public void setNomorUjiBerkala(String nomorUjiBerkala) {
        this.nomorUjiBerkala = nomorUjiBerkala;
    }

    /**
     * @return the masaBerlakuUjiBerkala
     */
    public String getMasaBerlakuUjiBerkala() {
        return masaBerlakuUjiBerkala;
    }

    /**
     * @param masaBerlakuUjiBerkala the masaBerlakuUjiBerkala to set
     */
    public void setMasaBerlakuUjiBerkala(String masaBerlakuUjiBerkala) {
        this.masaBerlakuUjiBerkala = masaBerlakuUjiBerkala;
    }

    /**
     * @return the kapasitasTempatDuduk
     */
    public int getKapasitasTempatDuduk() {
        return kapasitasTempatDuduk;
    }

    /**
     * @param kapasitasTempatDuduk the kapasitasTempatDuduk to set
     */
    public void setKapasitasTempatDuduk(int kapasitasTempatDuduk) {
        this.kapasitasTempatDuduk = kapasitasTempatDuduk;
    }

    /**
     * @return the berlakuSampai
     */
    public Date getBerlakuSampai() {
        return berlakuSampai;
    }

    /**
     * @param berlakuSampai the berlakuSampai to set
     */
    public void setBerlakuSampai(Date berlakuSampai) {
        this.berlakuSampai = berlakuSampai;
    }

    /**
     * @return the jumlahPenumpang
     */
    public int getJumlahPenumpang() {
        return jumlahPenumpang;
    }

    /**
     * @param jumlahPenumpang the jumlahPenumpang to set
     */
    public void setJumlahPenumpang(int jumlahPenumpang) {
        this.jumlahPenumpang = jumlahPenumpang;
    }

    /**
     * @return the rutePerjalanan
     */
    public String getRutePerjalanan() {
        return rutePerjalanan;
    }

    /**
     * @param rutePerjalanan the rutePerjalanan to set
     */
    public void setRutePerjalanan(String rutePerjalanan) {
        this.rutePerjalanan = rutePerjalanan;
    }

}
