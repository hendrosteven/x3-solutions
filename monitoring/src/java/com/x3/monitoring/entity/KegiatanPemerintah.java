/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.monitoring.entity;

/**
 *
 * @author Hendro Steven
 */
public class KegiatanPemerintah {
    private int id;
    private String nama;
    private String jalan;
    private Kelurahan kelurahan;
    private Kecamatan kecamatan;
    private String kota;
    private SumberDana sumberDana;
    private String tahun;
    private BidangUsahaPemerintah bidang;
    private double nilaiInvestasi;

    public KegiatanPemerintah(){}

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
     * @return the jalan
     */
    public String getJalan() {
        return jalan;
    }

    /**
     * @param jalan the jalan to set
     */
    public void setJalan(String jalan) {
        this.jalan = jalan;
    }

    /**
     * @return the kelurahan
     */
    public Kelurahan getKelurahan() {
        return kelurahan;
    }

    /**
     * @param kelurahan the kelurahan to set
     */
    public void setKelurahan(Kelurahan kelurahan) {
        this.kelurahan = kelurahan;
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

    /**
     * @return the kota
     */
    public String getKota() {
        return kota;
    }

    /**
     * @param kota the kota to set
     */
    public void setKota(String kota) {
        this.kota = kota;
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

    /**
     * @return the tahun
     */
    public String getTahun() {
        return tahun;
    }

    /**
     * @param tahun the tahun to set
     */
    public void setTahun(String tahun) {
        this.tahun = tahun;
    }

    /**
     * @return the bidang
     */
    public BidangUsahaPemerintah getBidang() {
        return bidang;
    }

    /**
     * @param bidang the bidang to set
     */
    public void setBidang(BidangUsahaPemerintah bidang) {
        this.bidang = bidang;
    }

    /**
     * @return the nilaiInvestasi
     */
    public double getNilaiInvestasi() {
        return nilaiInvestasi;
    }

    /**
     * @param nilaiInvestasi the nilaiInvestasi to set
     */
    public void setNilaiInvestasi(double nilaiInvestasi) {
        this.nilaiInvestasi = nilaiInvestasi;
    }

    
}
