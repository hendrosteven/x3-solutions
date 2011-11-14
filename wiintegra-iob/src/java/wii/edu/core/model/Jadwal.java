/*
 * Jadwal.java
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
public class Jadwal {
    private long id;
    private Ruang ruang;
    private String namaRuang;
    private Semester semester;
    //@Cascade(CascadeType.ALL)
    private Matakuliah matakuliah;
    private String namaMatkul;
    private String kode;
    private Dosen dosen;
    private String namaDosen;
    private String aksara;
    private String hari;
    private String jamMulai;
    private String jamSelesai;
    private String waktu;
    private int kapasitas;
    private String namaFakultas;
    private long idProgdi;
    private String namaProgdi;
    
    /** Creates a new instance of Jadwal */
    public Jadwal() {
    }

    public long getIdProgdi() {
        return idProgdi;
    }

    public void setIdProgdi(long idProgdi) {
        this.idProgdi = idProgdi;
    }

    public String getNamaProgdi() {
        return namaProgdi;
    }

    public void setNamaProgdi(String namaProgdi) {
        this.namaProgdi = namaProgdi;
    }

    public String getNamaDosen() {
        return namaDosen;
    }

    public void setNamaDosen(String namaDosen) {
        this.namaDosen = namaDosen;
    }

    public String getNamaMatkul() {
        return namaMatkul;
    }

    public void setNamaMatkul(String namaMatkul) {
        this.namaMatkul = namaMatkul;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNamaRuang() {
        return namaRuang;
    }

    public void setNamaRuang(String namaRuang) {
        this.namaRuang = namaRuang;
    }

    public Ruang getRuang() {
        return ruang;
    }

    public void setRuang(Ruang ruang) {
        this.ruang = ruang;
        this.namaRuang = ruang.getNama();
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public String getNamaFakultas() {
        return namaFakultas;
    }

    public void setNamaFakultas(String namaFakultas) {
        this.namaFakultas = namaFakultas;
    }

    public Matakuliah getMatakuliah() {
        return matakuliah;
    }

    public void setMatakuliah(Matakuliah matakuliah) {
        this.matakuliah = matakuliah;
        this.namaFakultas = matakuliah.getFakultas().getNama();
        this.idProgdi = matakuliah.getProgdi().getId();
        this.namaProgdi = matakuliah.getProgdi().getNama();
    }

    public Dosen getDosen() {
        return dosen;
    }

    public void setDosen(Dosen dosen) {
        this.dosen = dosen;
        this.namaDosen = dosen.getNama();
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getAksara() {
        return aksara;
    }

    public void setAksara(String aksara) {
        this.aksara = aksara;
        this.namaMatkul = getMatakuliah().getNama();
        this.kode = getMatakuliah().getKode();
        if(aksara != null && !aksara.equals("")){
            this.namaMatkul = this.namaMatkul + " " + aksara;
            this.kode = this.kode + " " + aksara;
        }
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getJamMulai() {
        return jamMulai;
    }

    public void setJamMulai(String jamMulai) {
        this.jamMulai = jamMulai;
    }

    public String getJamSelesai() {
        return jamSelesai;
    }

    public void setJamSelesai(String jamSelesai) {
        this.jamSelesai = jamSelesai;
        this.waktu = getHari() + ", " + getJamMulai() + " - " + getJamSelesai();
    }

    public int getKapasitas() {
        return kapasitas;
    }

    public void setKapasitas(int kapasitas) {
        this.kapasitas = kapasitas;
    }
    
    
    
}
