/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wii.edu.core.model;

import java.util.Date;

/**
 *
 * @author Retha@wii
 */
public class Pembayaran {
    private long id;
    private Mahasiswa mahasiswa;
    private Semester semester;
    private Date tanggal;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Mahasiswa getMahasiswa() {
        return mahasiswa;
    }

    public void setMahasiswa(Mahasiswa mahasiswa) {
        this.mahasiswa = mahasiswa;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }
}
