/*
 * ProgramStudi.java
 *
 * Created on July 20, 2007, 1:54 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package wii.edu.core.model;

/**
 *
 * @author Hendro
 */
public class ProgramStudi {
    private long id;
    private String kode;
    private String nama;
    private Fakultas fakultas;
    private String namaFakultas;
    private long jumlahFak;
    private long jumlah;
    
    /** Creates a new instance of ProgramStudi */
    public ProgramStudi() {
    }

    public long getJumlah() {
        return jumlah;
    }

    public void setJumlah(long jumlah) {
        this.jumlah = jumlah;
    }

    public long getJumlahFak() {
        return jumlahFak;
    }

    public void setJumlahFak(long jumlahFak) {
        this.jumlahFak = jumlahFak;
    }

    public String getNamaFakultas() {
        return namaFakultas;
    }

    public void setNamaFakultas(String namaFakultas) {
        this.namaFakultas = namaFakultas;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public Fakultas getFakultas() {
        return fakultas;
    }

    public void setFakultas(Fakultas fakultas) {
        this.fakultas = fakultas;
        this.namaFakultas = fakultas.getNama();
        this.jumlahFak = fakultas.getJumlah();
    }
    
}
