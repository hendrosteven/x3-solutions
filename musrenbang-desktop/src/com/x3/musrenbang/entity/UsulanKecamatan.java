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
public class UsulanKecamatan implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private TahunAnggaran tahunAnggaran;
    @ManyToOne
    private Kecamatan kecamatan;
    private int prioritas;
    private String kegiatanMusrenbang;
    @ManyToOne
    private Kegiatan kegiatanPermendagri;
    private String fisikNonFisik;
    private String detailNonFisik;
    @ManyToOne
    private Kelurahan kelurahan;
    private String rt;
    private String rw;
    private String volume;
    private double swadaya;
    private double apbd;
    private double pnpm;
    private String baruLanjut;
    @ManyToOne
    private Users user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    

    @Override
    public String toString() {
        return "com.x3.musrenbang.entity.UsulanKecamatan[id=" + id + "]";
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
     * @return the prioritas
     */
    public int getPrioritas() {
        return prioritas;
    }

    /**
     * @param prioritas the prioritas to set
     */
    public void setPrioritas(int prioritas) {
        this.prioritas = prioritas;
    }

    /**
     * @return the kegiatanMusrenbang
     */
    public String getKegiatanMusrenbang() {
        return kegiatanMusrenbang;
    }

    /**
     * @param kegiatanMusrenbang the kegiatanMusrenbang to set
     */
    public void setKegiatanMusrenbang(String kegiatanMusrenbang) {
        this.kegiatanMusrenbang = kegiatanMusrenbang;
    }

    /**
     * @return the kegiatanPermendagri
     */
    public Kegiatan getKegiatanPermendagri() {
        return kegiatanPermendagri;
    }

    /**
     * @param kegiatanPermendagri the kegiatanPermendagri to set
     */
    public void setKegiatanPermendagri(Kegiatan kegiatanPermendagri) {
        this.kegiatanPermendagri = kegiatanPermendagri;
    }

    /**
     * @return the fisikNonFisik
     */
    public String getFisikNonFisik() {
        return fisikNonFisik;
    }

    /**
     * @param fisikNonFisik the fisikNonFisik to set
     */
    public void setFisikNonFisik(String fisikNonFisik) {
        this.fisikNonFisik = fisikNonFisik;
    }

    /**
     * @return the detailNonFisik
     */
    public String getDetailNonFisik() {
        return detailNonFisik;
    }

    /**
     * @param detailNonFisik the detailNonFisik to set
     */
    public void setDetailNonFisik(String detailNonFisik) {
        this.detailNonFisik = detailNonFisik;
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
     * @return the swadaya
     */
    public double getSwadaya() {
        return swadaya;
    }

    /**
     * @param swadaya the swadaya to set
     */
    public void setSwadaya(double swadaya) {
        this.swadaya = swadaya;
    }

    /**
     * @return the apbd
     */
    public double getApbd() {
        return apbd;
    }

    /**
     * @param apbd the apbd to set
     */
    public void setApbd(double apbd) {
        this.apbd = apbd;
    }

    /**
     * @return the pnpm
     */
    public double getPnpm() {
        return pnpm;
    }

    /**
     * @param pnpm the pnpm to set
     */
    public void setPnpm(double pnpm) {
        this.pnpm = pnpm;
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

}
