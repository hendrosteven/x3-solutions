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
public class UsulanBappeda implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private TahunAnggaran tahunAnggaran;
    private SumberDana sumberDana;
    private Skpd skpd;
    private Urusan urusan;
    private Program program;
    private Kegiatan kegiatan;
    private String indikator;
    private String sasaran;
    private String penanggungJawab;
    private double realisasiN2;
    private double alokasiTAMin1;
    private double belanjaPegawai;
    private double belanjaBarangJasa;
    private double belanjaModal;
    private double lainLain;
    private double pln;
    private double hibah;
    private double prakiraanTAPlus1;
    @ManyToOne
    private Users user;
    //private int prioritas;
    @ManyToOne
    private UsulanKecamatan usulKecamatan;
    @ManyToOne
    private UsulanSkpd usulSkpd;
    @ManyToOne
    private JenisSumberDana jenisSumberDana;
    private int isAbpnRenja;
    private int isSkpdProv;
    private int isBanGup;
    private String sifatKegiatan;
    private String permasalahan;
    @ManyToOne
    private Kecamatan kecamatan;
    @ManyToOne
    private Kelurahan kelurahan;
    private String rt;
    private String rw;
    private String volume;
    private String satuan;
    private String baruLanjut;
    private double pendampingan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

   

    @Override
    public String toString() {
        return "com.x3.musrenbang.entity.UsulanBappeda[id=" + id + "]";
    }

    /**
     * @return the tahunAnggaran
     */
    public TahunAnggaran getTahunAnggaran() {
        return tahunAnggaran;
    }

    /**
     * @param tahunAnggaran the tahunAnggaran to set
     */
    public void setTahunAnggaran(TahunAnggaran tahunAnggaran) {
        this.tahunAnggaran = tahunAnggaran;
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
     * @return the skpd
     */
    public Skpd getSkpd() {
        return skpd;
    }

    /**
     * @param skpd the skpd to set
     */
    public void setSkpd(Skpd skpd) {
        this.skpd = skpd;
    }

    /**
     * @return the urusan
     */
    public Urusan getUrusan() {
        return urusan;
    }

    /**
     * @param urusan the urusan to set
     */
    public void setUrusan(Urusan urusan) {
        this.urusan = urusan;
    }

    /**
     * @return the program
     */
    public Program getProgram() {
        return program;
    }

    /**
     * @param program the program to set
     */
    public void setProgram(Program program) {
        this.program = program;
    }

    /**
     * @return the kegiatan
     */
    public Kegiatan getKegiatan() {
        return kegiatan;
    }

    /**
     * @param kegiatan the kegiatan to set
     */
    public void setKegiatan(Kegiatan kegiatan) {
        this.kegiatan = kegiatan;
    }

    /**
     * @return the indikator
     */
    public String getIndikator() {
        return indikator;
    }

    /**
     * @param indikator the indikator to set
     */
    public void setIndikator(String indikator) {
        this.indikator = indikator;
    }

    /**
     * @return the sasaran
     */
    public String getSasaran() {
        return sasaran;
    }

    /**
     * @param sasaran the sasaran to set
     */
    public void setSasaran(String sasaran) {
        this.sasaran = sasaran;
    }

    /**
     * @return the penanggungJawab
     */
    public String getPenanggungJawab() {
        return penanggungJawab;
    }

    /**
     * @param penanggungJawab the penanggungJawab to set
     */
    public void setPenanggungJawab(String penanggungJawab) {
        this.penanggungJawab = penanggungJawab;
    }

    /**
     * @return the realisasiN2
     */
    public double getRealisasiN2() {
        return realisasiN2;
    }

    /**
     * @param realisasiN2 the realisasiN2 to set
     */
    public void setRealisasiN2(double realisasiN2) {
        this.realisasiN2 = realisasiN2;
    }

    /**
     * @return the alokasiTAMin1
     */
    public double getAlokasiTAMin1() {
        return alokasiTAMin1;
    }

    /**
     * @param alokasiTAMin1 the alokasiTAMin1 to set
     */
    public void setAlokasiTAMin1(double alokasiTAMin1) {
        this.alokasiTAMin1 = alokasiTAMin1;
    }

    /**
     * @return the belanjaPegawai
     */
    public double getBelanjaPegawai() {
        return belanjaPegawai;
    }

    /**
     * @param belanjaPegawai the belanjaPegawai to set
     */
    public void setBelanjaPegawai(double belanjaPegawai) {
        this.belanjaPegawai = belanjaPegawai;
    }

    /**
     * @return the belanjaBarangJasa
     */
    public double getBelanjaBarangJasa() {
        return belanjaBarangJasa;
    }

    /**
     * @param belanjaBarangJasa the belanjaBarangJasa to set
     */
    public void setBelanjaBarangJasa(double belanjaBarangJasa) {
        this.belanjaBarangJasa = belanjaBarangJasa;
    }

    /**
     * @return the belanjaModal
     */
    public double getBelanjaModal() {
        return belanjaModal;
    }

    /**
     * @param belanjaModal the belanjaModal to set
     */
    public void setBelanjaModal(double belanjaModal) {
        this.belanjaModal = belanjaModal;
    }

    /**
     * @return the lainLain
     */
    public double getLainLain() {
        return lainLain;
    }

    /**
     * @param lainLain the lainLain to set
     */
    public void setLainLain(double lainLain) {
        this.lainLain = lainLain;
    }

    /**
     * @return the pln
     */
    public double getPln() {
        return pln;
    }

    /**
     * @param pln the pln to set
     */
    public void setPln(double pln) {
        this.pln = pln;
    }

    /**
     * @return the hibah
     */
    public double getHibah() {
        return hibah;
    }

    /**
     * @param hibah the hibah to set
     */
    public void setHibah(double hibah) {
        this.hibah = hibah;
    }

    /**
     * @return the prakiraanTAPlus1
     */
    public double getPrakiraanTAPlus1() {
        return prakiraanTAPlus1;
    }

    /**
     * @param prakiraanTAPlus1 the prakiraanTAPlus1 to set
     */
    public void setPrakiraanTAPlus1(double prakiraanTAPlus1) {
        this.prakiraanTAPlus1 = prakiraanTAPlus1;
    }

    /**
     * @return the user
     */
    public Users getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(Users user) {
        this.user = user;
    }

    /**
     * @return the usulKecamatan
     */
    public UsulanKecamatan getUsulKecamatan() {
        return usulKecamatan;
    }

    /**
     * @param usulKecamatan the usulKecamatan to set
     */
    public void setUsulKecamatan(UsulanKecamatan usulKecamatan) {
        this.usulKecamatan = usulKecamatan;
    }

    /**
     * @return the usulSkpd
     */
    public UsulanSkpd getUsulSkpd() {
        return usulSkpd;
    }

    /**
     * @param usulSkpd the usulSkpd to set
     */
    public void setUsulSkpd(UsulanSkpd usulSkpd) {
        this.usulSkpd = usulSkpd;
    }

    /**
     * @return the jenisSumberDana
     */
    public JenisSumberDana getJenisSumberDana() {
        return jenisSumberDana;
    }

    /**
     * @param jenisSumberDana the jenisSumberDana to set
     */
    public void setJenisSumberDana(JenisSumberDana jenisSumberDana) {
        this.jenisSumberDana = jenisSumberDana;
    }

    /**
     * @return the isAbpnRenja
     */
    public int getIsAbpnRenja() {
        return isAbpnRenja;
    }

    /**
     * @param isAbpnRenja the isAbpnRenja to set
     */
    public void setIsAbpnRenja(int isAbpnRenja) {
        this.isAbpnRenja = isAbpnRenja;
    }

    /**
     * @return the isSkpdProv
     */
    public int getIsSkpdProv() {
        return isSkpdProv;
    }

    /**
     * @param isSkpdProv the isSkpdProv to set
     */
    public void setIsSkpdProv(int isSkpdProv) {
        this.isSkpdProv = isSkpdProv;
    }

    /**
     * @return the isBanGup
     */
    public int getIsBanGup() {
        return isBanGup;
    }

    /**
     * @param isBanGup the isBanGup to set
     */
    public void setIsBanGup(int isBanGup) {
        this.isBanGup = isBanGup;
    }

    /**
     * @return the sifatKegiatan
     */
    public String getSifatKegiatan() {
        return sifatKegiatan;
    }

    /**
     * @param sifatKegiatan the sifatKegiatan to set
     */
    public void setSifatKegiatan(String sifatKegiatan) {
        this.sifatKegiatan = sifatKegiatan;
    }

    /**
     * @return the permasalahan
     */
    public String getPermasalahan() {
        return permasalahan;
    }

    /**
     * @param permasalahan the permasalahan to set
     */
    public void setPermasalahan(String permasalahan) {
        this.permasalahan = permasalahan;
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
     * @return the rt
     */
    public String getRt() {
        return rt;
    }

    /**
     * @param rt the rt to set
     */
    public void setRt(String rt) {
        this.rt = rt;
    }

    /**
     * @return the rw
     */
    public String getRw() {
        return rw;
    }

    /**
     * @param rw the rw to set
     */
    public void setRw(String rw) {
        this.rw = rw;
    }

    /**
     * @return the volume
     */
    public String getVolume() {
        return volume;
    }

    /**
     * @param volume the volume to set
     */
    public void setVolume(String volume) {
        this.volume = volume;
    }

    /**
     * @return the satuan
     */
    public String getSatuan() {
        return satuan;
    }

    /**
     * @param satuan the satuan to set
     */
    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    /**
     * @return the baruLanjut
     */
    public String getBaruLanjut() {
        return baruLanjut;
    }

    /**
     * @param baruLanjut the baruLanjut to set
     */
    public void setBaruLanjut(String baruLanjut) {
        this.baruLanjut = baruLanjut;
    }

    /**
     * @return the pendampingan
     */
    public double getPendampingan() {
        return pendampingan;
    }

    /**
     * @param pendampingan the pendampingan to set
     */
    public void setPendampingan(double pendampingan) {
        this.pendampingan = pendampingan;
    }

}
