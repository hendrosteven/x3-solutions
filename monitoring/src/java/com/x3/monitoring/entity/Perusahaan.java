/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Hendro Steven
 */
public class Perusahaan {

    private String id;
    private String namaPerusahaan;
    private String namaPimpinan;
    private String alamatJalan;
    private Kelurahan kelurahan;
    private Kecamatan kecamatan;
    private String kota;
    private String telp;
    private String fax;
    private double luasTanah;
    private double luasGedung;
    private BadanHukum badanHukum;
    private BentukModal bentukModal;
    private StatusPerusahaan statusPerusahaan;
    private BidangUsaha bidangUsaha;
    private String produk;
    private double modalKerja;
    private double modalTetap;
    private int jmlTKILaki;
    private int jmlTKIPerem;
    private int jmlTKALaki;
    private int jmlTKAPerem;
    private String tahunKegiatan;
    private String jenisProduksi;
    private String kapasitasTerpasang;
    private String realisasiProduksi;
    private String export;
    private double nilaiExport;
    private String tahunOmset1;
    private double nilaiOmset1;
    private String tahunOmset2;
    private double nilaiOmset2;
    private String tahunOmset3;
    private double nilaiOmset3;
    private String dokumenAmdal;
    private List<IjinPusat> ijinPusat = new ArrayList<IjinPusat>();
    private String lokasiNomor;
    private Date lokasiTanggal;
    private String imbNomor;
    private Date imbTanggal;
    private String gudangNomor;
    private Date gudangTanggal;
    private String hoNomor;
    private Date hoTanggal;
    private String siupNomor;
    private Date siupTanggal;
    private String tdpNomor;
    private Date tdpTanggal;
    private String npwp;
    private Date tanggalInput;
    private String tdiNomor;
    private Date tdiTanggal;
    
    

    public Perusahaan() {
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the namaPerusahaan
     */
    public String getNamaPerusahaan() {
        return namaPerusahaan;
    }

    /**
     * @param namaPerusahaan the namaPerusahaan to set
     */
    public void setNamaPerusahaan(String namaPerusahaan) {
        this.namaPerusahaan = namaPerusahaan;
    }

    /**
     * @return the namaPimpinan
     */
    public String getNamaPimpinan() {
        return namaPimpinan;
    }

    /**
     * @param namaPimpinan the namaPimpinan to set
     */
    public void setNamaPimpinan(String namaPimpinan) {
        this.namaPimpinan = namaPimpinan;
    }

    /**
     * @return the alamatJalan
     */
    public String getAlamatJalan() {
        return alamatJalan;
    }

    /**
     * @param alamatJalan the alamatJalan to set
     */
    public void setAlamatJalan(String alamatJalan) {
        this.alamatJalan = alamatJalan;
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
     * @return the telp
     */
    public String getTelp() {
        return telp;
    }

    /**
     * @param telp the telp to set
     */
    public void setTelp(String telp) {
        this.telp = telp;
    }

    /**
     * @return the fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * @param fax the fax to set
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * @return the luasTanah
     */
    public double getLuasTanah() {
        return luasTanah;
    }

    /**
     * @param luasTanah the luasTanah to set
     */
    public void setLuasTanah(double luasTanah) {
        this.luasTanah = luasTanah;
    }

    /**
     * @return the luasGedung
     */
    public double getLuasGedung() {
        return luasGedung;
    }

    /**
     * @param luasGedung the luasGedung to set
     */
    public void setLuasGedung(double luasGedung) {
        this.luasGedung = luasGedung;
    }

    /**
     * @return the badanHukum
     */
    public BadanHukum getBadanHukum() {
        return badanHukum;
    }

    /**
     * @param badanHukum the badanHukum to set
     */
    public void setBadanHukum(BadanHukum badanHukum) {
        this.badanHukum = badanHukum;
    }

    /**
     * @return the bentukModal
     */
    public BentukModal getBentukModal() {
        return bentukModal;
    }

    /**
     * @param bentukModal the bentukModal to set
     */
    public void setBentukModal(BentukModal bentukModal) {
        this.bentukModal = bentukModal;
    }

    /**
     * @return the statusPerusahaan
     */
    public StatusPerusahaan getStatusPerusahaan() {
        return statusPerusahaan;
    }

    /**
     * @param statusPerusahaan the statusPerusahaan to set
     */
    public void setStatusPerusahaan(StatusPerusahaan statusPerusahaan) {
        this.statusPerusahaan = statusPerusahaan;
    }

    /**
     * @return the bidangUsaha
     */
    public BidangUsaha getBidangUsaha() {
        return bidangUsaha;
    }

    /**
     * @param bidangUsaha the bidangUsaha to set
     */
    public void setBidangUsaha(BidangUsaha bidangUsaha) {
        this.bidangUsaha = bidangUsaha;
    }

    /**
     * @return the produk
     */
    public String getProduk() {
        return produk;
    }

    /**
     * @param produk the produk to set
     */
    public void setProduk(String produk) {
        this.produk = produk;
    }

    /**
     * @return the modalKerja
     */
    public double getModalKerja() {
        return modalKerja;
    }

    /**
     * @param modalKerja the modalKerja to set
     */
    public void setModalKerja(double modalKerja) {
        this.modalKerja = modalKerja;
    }

    /**
     * @return the modalTetap
     */
    public double getModalTetap() {
        return modalTetap;
    }

    /**
     * @param modalTetap the modalTetap to set
     */
    public void setModalTetap(double modalTetap) {
        this.modalTetap = modalTetap;
    }

    /**
     * @return the jmlTKILaki
     */
    public int getJmlTKILaki() {
        return jmlTKILaki;
    }

    /**
     * @param jmlTKILaki the jmlTKILaki to set
     */
    public void setJmlTKILaki(int jmlTKILaki) {
        this.jmlTKILaki = jmlTKILaki;
    }

    /**
     * @return the jmlTKIPerem
     */
    public int getJmlTKIPerem() {
        return jmlTKIPerem;
    }

    /**
     * @param jmlTKIPerem the jmlTKIPerem to set
     */
    public void setJmlTKIPerem(int jmlTKIPerem) {
        this.jmlTKIPerem = jmlTKIPerem;
    }

    /**
     * @return the jmlTKALaki
     */
    public int getJmlTKALaki() {
        return jmlTKALaki;
    }

    /**
     * @param jmlTKALaki the jmlTKALaki to set
     */
    public void setJmlTKALaki(int jmlTKALaki) {
        this.jmlTKALaki = jmlTKALaki;
    }

    /**
     * @return the jmlTKAPerem
     */
    public int getJmlTKAPerem() {
        return jmlTKAPerem;
    }

    /**
     * @param jmlTKAPerem the jmlTKAPerem to set
     */
    public void setJmlTKAPerem(int jmlTKAPerem) {
        this.jmlTKAPerem = jmlTKAPerem;
    }

    /**
     * @return the tahunKegiatan
     */
    public String getTahunKegiatan() {
        return tahunKegiatan;
    }

    /**
     * @param tahunKegiatan the tahunKegiatan to set
     */
    public void setTahunKegiatan(String tahunKegiatan) {
        this.tahunKegiatan = tahunKegiatan;
    }

    /**
     * @return the jenisProduksi
     */
    public String getJenisProduksi() {
        return jenisProduksi;
    }

    /**
     * @param jenisProduksi the jenisProduksi to set
     */
    public void setJenisProduksi(String jenisProduksi) {
        this.jenisProduksi = jenisProduksi;
    }

    /**
     * @return the kapasitasTerpasang
     */
    public String getKapasitasTerpasang() {
        return kapasitasTerpasang;
    }

    /**
     * @param kapasitasTerpasang the kapasitasTerpasang to set
     */
    public void setKapasitasTerpasang(String kapasitasTerpasang) {
        this.kapasitasTerpasang = kapasitasTerpasang;
    }

    /**
     * @return the realisasiProduksi
     */
    public String getRealisasiProduksi() {
        return realisasiProduksi;
    }

    /**
     * @param realisasiProduksi the realisasiProduksi to set
     */
    public void setRealisasiProduksi(String realisasiProduksi) {
        this.realisasiProduksi = realisasiProduksi;
    }

    /**
     * @return the export
     */
    public String getExport() {
        return export;
    }

    /**
     * @param export the export to set
     */
    public void setExport(String export) {
        this.export = export;
    }

    /**
     * @return the nilaiExport
     */
    public double getNilaiExport() {
        return nilaiExport;
    }

    /**
     * @param nilaiExport the nilaiExport to set
     */
    public void setNilaiExport(double nilaiExport) {
        this.nilaiExport = nilaiExport;
    }

    /**
     * @return the tahunOmset1
     */
    public String getTahunOmset1() {
        return tahunOmset1;
    }

    /**
     * @param tahunOmset1 the tahunOmset1 to set
     */
    public void setTahunOmset1(String tahunOmset1) {
        this.tahunOmset1 = tahunOmset1;
    }

    /**
     * @return the nilaiOmset1
     */
    public double getNilaiOmset1() {
        return nilaiOmset1;
    }

    /**
     * @param nilaiOmset1 the nilaiOmset1 to set
     */
    public void setNilaiOmset1(double nilaiOmset1) {
        this.nilaiOmset1 = nilaiOmset1;
    }

    /**
     * @return the tahunOmset2
     */
    public String getTahunOmset2() {
        return tahunOmset2;
    }

    /**
     * @param tahunOmset2 the tahunOmset2 to set
     */
    public void setTahunOmset2(String tahunOmset2) {
        this.tahunOmset2 = tahunOmset2;
    }

    /**
     * @return the nilaiOmset2
     */
    public double getNilaiOmset2() {
        return nilaiOmset2;
    }

    /**
     * @param nilaiOmset2 the nilaiOmset2 to set
     */
    public void setNilaiOmset2(double nilaiOmset2) {
        this.nilaiOmset2 = nilaiOmset2;
    }

    /**
     * @return the tahunOmset3
     */
    public String getTahunOmset3() {
        return tahunOmset3;
    }

    /**
     * @param tahunOmset3 the tahunOmset3 to set
     */
    public void setTahunOmset3(String tahunOmset3) {
        this.tahunOmset3 = tahunOmset3;
    }

    /**
     * @return the nilaiOmset3
     */
    public double getNilaiOmset3() {
        return nilaiOmset3;
    }

    /**
     * @param nilaiOmset3 the nilaiOmset3 to set
     */
    public void setNilaiOmset3(double nilaiOmset3) {
        this.nilaiOmset3 = nilaiOmset3;
    }

    /**
     * @return the dokumenAmdal
     */
    public String getDokumenAmdal() {
        return dokumenAmdal;
    }

    /**
     * @param dokumenAmdal the dokumenAmdal to set
     */
    public void setDokumenAmdal(String dokumenAmdal) {
        this.dokumenAmdal = dokumenAmdal;
    }

    /**
     * @return the ijinPusat
     */
    public List<IjinPusat> getIjinPusat() {
        return ijinPusat;
    }

    /**
     * @param ijinPusat the ijinPusat to set
     */
    public void setIjinPusat(List<IjinPusat> ijinPusat) {
        this.ijinPusat = ijinPusat;
    }

    /**
     * @return the lokasiNomor
     */
    public String getLokasiNomor() {
        return lokasiNomor;
    }

    /**
     * @param lokasiNomor the lokasiNomor to set
     */
    public void setLokasiNomor(String lokasiNomor) {
        this.lokasiNomor = lokasiNomor;
    }

    /**
     * @return the lokasiTanggal
     */
    public Date getLokasiTanggal() {
        return lokasiTanggal;
    }

    /**
     * @param lokasiTanggal the lokasiTanggal to set
     */
    public void setLokasiTanggal(Date lokasiTanggal) {
        this.lokasiTanggal = lokasiTanggal;
    }

    /**
     * @return the imbNomor
     */
    public String getImbNomor() {
        return imbNomor;
    }

    /**
     * @param imbNomor the imbNomor to set
     */
    public void setImbNomor(String imbNomor) {
        this.imbNomor = imbNomor;
    }

    /**
     * @return the imbTanggal
     */
    public Date getImbTanggal() {
        return imbTanggal;
    }

    /**
     * @param imbTanggal the imbTanggal to set
     */
    public void setImbTanggal(Date imbTanggal) {
        this.imbTanggal = imbTanggal;
    }

    /**
     * @return the gudangNomor
     */
    public String getGudangNomor() {
        return gudangNomor;
    }

    /**
     * @param gudangNomor the gudangNomor to set
     */
    public void setGudangNomor(String gudangNomor) {
        this.gudangNomor = gudangNomor;
    }

    /**
     * @return the gudangTanggal
     */
    public Date getGudangTanggal() {
        return gudangTanggal;
    }

    /**
     * @param gudangTanggal the gudangTanggal to set
     */
    public void setGudangTanggal(Date gudangTanggal) {
        this.gudangTanggal = gudangTanggal;
    }

    /**
     * @return the hoNomor
     */
    public String getHoNomor() {
        return hoNomor;
    }

    /**
     * @param hoNomor the hoNomor to set
     */
    public void setHoNomor(String hoNomor) {
        this.hoNomor = hoNomor;
    }

    /**
     * @return the hoTanggal
     */
    public Date getHoTanggal() {
        return hoTanggal;
    }

    /**
     * @param hoTanggal the hoTanggal to set
     */
    public void setHoTanggal(Date hoTanggal) {
        this.hoTanggal = hoTanggal;
    }

    /**
     * @return the siupNomor
     */
    public String getSiupNomor() {
        return siupNomor;
    }

    /**
     * @param siupNomor the siupNomor to set
     */
    public void setSiupNomor(String siupNomor) {
        this.siupNomor = siupNomor;
    }

    /**
     * @return the siupTanggal
     */
    public Date getSiupTanggal() {
        return siupTanggal;
    }

    /**
     * @param siupTanggal the siupTanggal to set
     */
    public void setSiupTanggal(Date siupTanggal) {
        this.siupTanggal = siupTanggal;
    }

    /**
     * @return the tdpNomor
     */
    public String getTdpNomor() {
        return tdpNomor;
    }

    /**
     * @param tdpNomor the tdpNomor to set
     */
    public void setTdpNomor(String tdpNomor) {
        this.tdpNomor = tdpNomor;
    }

    /**
     * @return the tdpTanggal
     */
    public Date getTdpTanggal() {
        return tdpTanggal;
    }

    /**
     * @param tdpTanggal the tdpTanggal to set
     */
    public void setTdpTanggal(Date tdpTanggal) {
        this.tdpTanggal = tdpTanggal;
    }

    /**
     * @return the npwp
     */
    public String getNpwp() {
        return npwp;
    }

    /**
     * @param npwp the npwp to set
     */
    public void setNpwp(String npwp) {
        this.npwp = npwp;
    }

    /**
     * @return the tanggalInput
     */
    public Date getTanggalInput() {
        return tanggalInput;
    }

    /**
     * @param tanggalInput the tanggalInput to set
     */
    public void setTanggalInput(Date tanggalInput) {
        this.tanggalInput = tanggalInput;
    }

    /**
     * @return the tdiNomor
     */
    public String getTdiNomor() {
        return tdiNomor;
    }

    /**
     * @param tdiNomor the tdiNomor to set
     */
    public void setTdiNomor(String tdiNomor) {
        this.tdiNomor = tdiNomor;
    }

    /**
     * @return the tdiTanggal
     */
    public Date getTdiTanggal() {
        return tdiTanggal;
    }

    /**
     * @param tdiTanggal the tdiTanggal to set
     */
    public void setTdiTanggal(Date tdiTanggal) {
        this.tdiTanggal = tdiTanggal;
    }

   
}
