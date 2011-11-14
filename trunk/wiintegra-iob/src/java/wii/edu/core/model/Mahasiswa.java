/*
 * Mahasiswa.java
 *
 * Created on July 11, 2007, 2:08 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package wii.edu.core.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import org.hibernate.Session;
import wii.edu.core.dao.HibernateUtil;
import wii.edu.core.dao.MahasiswaDAO;

/**
 *
 * @author Hendro
 */
public class Mahasiswa implements HttpSessionBindingListener {
    private long id;
    private String nomorInduk;
    private String password;
    private String nama;
    private Fakultas fakultas;
    private String namaFakultas;
    private ProgramStudi progdi;
    private String namaProgdi;
    private long idProgdi;
    private String namaAyah;
    private String namaIbu;
    private String alamatOrangTua;
    private List biodatas = new ArrayList();
    private String alamatLengkap;
    private String ttl;
    private String telepon;
    private String email;
    private int jenisKelamin; //0:perempuan, 1:laki-laki
    private Angkatan angkatan = new Angkatan();
    private DataKelulusan dataKelulusan = new DataKelulusan();
    private String jenjang;
    private int isLogin; //false : tidak aktif, true : aktif

    //private BidangStudi bidangStudi;
   // private Konsentrasi konsentrasi;
    //private List<JadwalDetail> jadwalKuliah = new ArrayList<JadwalDetail>();
    //private List tagihan = new ArrayList();
    
    /** Creates a new instance of Mahasiswa */
    public Mahasiswa() {
    }

    public long getIdProgdi() {
        return idProgdi;
    }

    public void setIdProgdi(long idProgdi) {
        this.idProgdi = idProgdi;
    }

    public String getAlamatLengkap() {
        return alamatLengkap;
    }

    public void setAlamatLengkap(String alamatLengkap) {
        this.alamatLengkap = alamatLengkap;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(int jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getNamaFakultas() {
        return namaFakultas;
    }

    public void setNamaFakultas(String namaFakultas) {
        this.namaFakultas = namaFakultas;
    }

    public String getNamaProgdi() {
        return namaProgdi;
    }

    public void setNamaProgdi(String namaProgdi) {
        this.namaProgdi = namaProgdi;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public String getJenjang() {
        return jenjang;
    }

    public void setJenjang(String jenjang) {
        this.jenjang = jenjang;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNomorInduk() {
        return nomorInduk;
    }

    public void setNomorInduk(String nomorInduk) {
        this.nomorInduk = nomorInduk;
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
    }

    public String getNamaAyah() {
        return namaAyah;
    }

    public void setNamaAyah(String namaAyah) {
        this.namaAyah = namaAyah;
    }

    public String getNamaIbu() {
        return namaIbu;
    }

    public void setNamaIbu(String namaIbu) {
        this.namaIbu = namaIbu;
    }

    public String getAlamatOrangTua() {
        return alamatOrangTua;
    }

    public void setAlamatOrangTua(String alamatOrangTua) {
        this.alamatOrangTua = alamatOrangTua;
    }

    public List getBiodatas() {
        return biodatas;
    }

    public void setBiodatas(List biodatas) {
        this.biodatas = biodatas;
        if(biodatas.size() >= 1){
            Biodata bio = (Biodata)biodatas.get(biodatas.size() - 1);
            SimpleDateFormat format = new SimpleDateFormat("d MMMM yyyy");
            this.ttl = bio.getTempatLahir() + ", " + format.format(bio.getTanggalLahir());
            this.alamatLengkap = bio.getJalan() + ", " + bio.getSubDistrik() + " " + bio.getKodePos() + ", " + bio.getDistrik().getNama();
            this.telepon = bio.getTelepon() + " / " + bio.getHandphone();
            this.email = bio.getEmail();
        }
        else{
            this.ttl = "";
            this.alamatLengkap = "";
            this.telepon = "";
            this.email = "";
        }
    }

    public Angkatan getAngkatan() {
        return angkatan;
    }

    public void setAngkatan(Angkatan angkatan) {
        this.angkatan = angkatan;
    }

//    public List<JadwalDetail> getJadwalKuliah() {
//        return jadwalKuliah;
//    }
//
//    public void setJadwalKuliah(List<JadwalDetail> jadwalKuliah) {
//        this.jadwalKuliah = jadwalKuliah;
//    }

//    public List getTagihan() {
//        return tagihan;
//    }
//
//    public void setTagihan(List tagihan) {
//        this.tagihan = tagihan;
//    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

//    public BidangStudi getBidangStudi() {
//        return bidangStudi;
//    }
//
//    public void setBidangStudi(BidangStudi bidangStudi) {
//        this.bidangStudi = bidangStudi;
//    }

//    public Konsentrasi getKonsentrasi() {
//        return konsentrasi;
//    }
//
//    public void setKonsentrasi(Konsentrasi konsentrasi) {
//        this.konsentrasi = konsentrasi;
//    }
    public DataKelulusan getDataKelulusan() {
        return dataKelulusan;
    }

    public void setDataKelulusan(DataKelulusan dataKelulusan) {
        this.dataKelulusan = dataKelulusan;
    }

    public int isIsLogin() {
        return isLogin;
    }

    public void setIsLogin(int isLogin) {
        this.isLogin = isLogin;
    }

    public int getIsLogin() {
        return isLogin;
    }

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        //throw new UnsupportedOperationException("Not supported yet.");
        System.out.println("masuuuuk");
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        //throw new UnsupportedOperationException("Not supported yet.");
        try {
            System.out.println("keluaaaaaaaaaar");
            Mahasiswa activeMhs = (Mahasiswa)event.getValue();
            System.out.println("is login : " + activeMhs.getIsLogin());
            Mahasiswa mhs = new MahasiswaDAO().getMahasiswa(activeMhs.getId());
            mhs.setIsLogin(0);

            HibernateUtil.beginTransaction();
            Session sesi = HibernateUtil.getSession();
            sesi.clear();
            sesi.update(mhs);
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
            System.out.println("bisa..");
        } catch (Exception ex) {
            Logger.getLogger(Mahasiswa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
