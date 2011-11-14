/*
 * Matakuliah.java
 *
 * Created on July 11, 2007, 2:08 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package wii.edu.core.model;

/**
 *
 * @author Hendro
 */
public class Matakuliah {
    private long id;
    private String kode;
    private String nama;
    private int sksAkademik;
    private int sksBayar;
    private Fakultas fakultas;
    private String namaFakultas;
    private ProgramStudi progdi;
    private String namaProgdi;
    private long idProgdi;
    private Matakuliah prasyarat;
    private String namaPrasyarat;
    /** Creates a new instance of Matakuliah */
    public Matakuliah() {
    }

    public long getIdProgdi() {
        return idProgdi;
    }

    public void setIdProgdi(long idProgdi) {
        this.idProgdi = idProgdi;
    }

    public ProgramStudi getProgdi() {
        return progdi;
    }

    public void setProgdi(ProgramStudi progdi) {
        this.progdi = progdi;
        if(getProgdi() == null){
            this.namaProgdi = "--";
            this.idProgdi = 0;
        }
        else{
            this.namaProgdi = getProgdi().getNama();
            this.idProgdi = getProgdi().getId();
        }
    }

    public String getNamaProgdi() {
        return namaProgdi;
    }

    public void setNamaProgdi(String namaProgdi) {
        this.namaProgdi = namaProgdi;
    }

    public String getNamaFakultas() {
        return namaFakultas;
    }

    public void setNamaFakultas(String namaFakultas) {
        this.namaFakultas = namaFakultas;
    }

    public String getNamaPrasyarat() {
        return namaPrasyarat;
    }

    public void setNamaPrasyarat(String namaPrasyarat) {
        this.namaPrasyarat = namaPrasyarat;
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

    public int getSksAkademik() {
        return sksAkademik;
    }

    public void setSksAkademik(int sksAkademik) {
        this.sksAkademik = sksAkademik;
    }

    public int getSksBayar() {
        return sksBayar;
    }

    public void setSksBayar(int sksBayar) {
        this.sksBayar = sksBayar;
    }

    public Fakultas getFakultas() {
        return fakultas;
    }

    public void setFakultas(Fakultas fakultas) {
        this.fakultas = fakultas;
        if(getFakultas() == null)
            this.namaFakultas = "--";
        else
            this.namaFakultas = getFakultas().getNama();
    }

    public Matakuliah getPrasyarat() {
        return prasyarat;
    }

    public void setPrasyarat(Matakuliah prasyarat) {
        this.prasyarat = prasyarat;
        if(getPrasyarat() == null)
            this.namaPrasyarat = "--";
        else
            this.namaPrasyarat = getPrasyarat().getNama();
    }
    
    
    
}
