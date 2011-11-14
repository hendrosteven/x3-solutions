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
public class Spio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomor;
    private String sifat;
    private String perihal;
    @Temporal(value = javax.persistence.TemporalType.DATE)
    private Date tanggalPenetapan;
    @Temporal(value = javax.persistence.TemporalType.DATE)
    private Date tanggalBerakhir;
    @ManyToOne
    private Perusahaan perusahaan;
    @Temporal(value = javax.persistence.TemporalType.DATE)
    private Date tanggalPermohonan;
    private String nomorSuratPermohonan;
    @ManyToOne
    private Merk merk;
    private String typeJenis;
    private String tahunKendaraan;
    private int jumlah;
    @ManyToOne
    private Trayek trayek;
    private String lainLain;

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
     * @return the sifat
     */
    public String getSifat() {
        return sifat;
    }

    /**
     * @param sifat the sifat to set
     */
    public void setSifat(String sifat) {
        this.sifat = sifat;
    }

    /**
     * @return the perihal
     */
    public String getPerihal() {
        return perihal;
    }

    /**
     * @param perihal the perihal to set
     */
    public void setPerihal(String perihal) {
        this.perihal = perihal;
    }

    /**
     * @return the tanggalPenetapan
     */
    public Date getTanggalPenetapan() {
        return tanggalPenetapan;
    }

    /**
     * @param tanggalPenetapan the tanggalPenetapan to set
     */
    public void setTanggalPenetapan(Date tanggalPenetapan) {
        this.tanggalPenetapan = tanggalPenetapan;
    }

    /**
     * @return the tanggalBerakhir
     */
    public Date getTanggalBerakhir() {
        return tanggalBerakhir;
    }

    /**
     * @param tanggalBerakhir the tanggalBerakhir to set
     */
    public void setTanggalBerakhir(Date tanggalBerakhir) {
        this.tanggalBerakhir = tanggalBerakhir;
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
     * @return the tanggalPermohonan
     */
    public Date getTanggalPermohonan() {
        return tanggalPermohonan;
    }

    /**
     * @param tanggalPermohonan the tanggalPermohonan to set
     */
    public void setTanggalPermohonan(Date tanggalPermohonan) {
        this.tanggalPermohonan = tanggalPermohonan;
    }

    /**
     * @return the nomorSuratPermohonan
     */
    public String getNomorSuratPermohonan() {
        return nomorSuratPermohonan;
    }

    /**
     * @param nomorSuratPermohonan the nomorSuratPermohonan to set
     */
    public void setNomorSuratPermohonan(String nomorSuratPermohonan) {
        this.nomorSuratPermohonan = nomorSuratPermohonan;
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
     * @return the typeJenis
     */
    public String getTypeJenis() {
        return typeJenis;
    }

    /**
     * @param typeJenis the typeJenis to set
     */
    public void setTypeJenis(String typeJenis) {
        this.typeJenis = typeJenis;
    }

    /**
     * @return the tahunKendaraan
     */
    public String getTahunKendaraan() {
        return tahunKendaraan;
    }

    /**
     * @param tahunKendaraan the tahunKendaraan to set
     */
    public void setTahunKendaraan(String tahunKendaraan) {
        this.tahunKendaraan = tahunKendaraan;
    }

    /**
     * @return the jumlah
     */
    public int getJumlah() {
        return jumlah;
    }

    /**
     * @param jumlah the jumlah to set
     */
    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
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
     * @return the lainLain
     */
    public String getLainLain() {
        return lainLain;
    }

    /**
     * @param lainLain the lainLain to set
     */
    public void setLainLain(String lainLain) {
        this.lainLain = lainLain;
    }

}
