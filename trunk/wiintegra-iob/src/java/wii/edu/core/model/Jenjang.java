/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wii.edu.core.model;

/**
 *
 * @author Retha@wii
 */
public class Jenjang {
    private long id;
    private String nama;
    private String keterangan;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
