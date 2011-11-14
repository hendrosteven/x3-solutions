/*
 * Biodata.java
 *
 * Created on July 11, 2007, 2:08 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package wii.edu.core.model;

import java.util.Date;

/**
 *
 * @author Hendro
 */
public class Biodata {
    private long id;
    private int index;
    private String jalan;
    private String kelurahan;
    private String kecamatan;
    private String subDistrik;
    private Distrik distrik;
    private String kodePos;
    private String telepon;
    private String handphone;
    private String email;
    private Semester semester;
    private String tempatLahir;
    private Date tanggalLahir;
    private Mahasiswa mahasiswa;
    
    /** Creates a new instance of Biodata */
    public Biodata() {
    }

    public Mahasiswa getMahasiswa() {
        return mahasiswa;
    }

    public void setMahasiswa(Mahasiswa mahasiswa) {
        this.mahasiswa = mahasiswa;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Date getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(Date tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getTempatLahir() {
        return tempatLahir;
    }

    public void setTempatLahir(String tempatLahir) {
        this.tempatLahir = tempatLahir;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getJalan() {
        return jalan;
    }

    public void setJalan(String jalan) {
        this.jalan = jalan;
    }

    public String getKelurahan() {
        return kelurahan;
    }

    public void setKelurahan(String kelurahan) {
        this.kelurahan = kelurahan;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getSubDistrik() {
        return subDistrik;
    }

    public void setSubDistrik(String subDistrik) {
        this.subDistrik = subDistrik;
    }

    public Distrik getDistrik() {
        return distrik;
    }

    public void setDistrik(Distrik distrik) {
        this.distrik = distrik;
    }

    public String getKodePos() {
        return kodePos;
    }

    public void setKodePos(String kodePos) {
        this.kodePos = kodePos;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getHandphone() {
        return handphone;
    }

    public void setHandphone(String handphone) {
        this.handphone = handphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }
    
}
