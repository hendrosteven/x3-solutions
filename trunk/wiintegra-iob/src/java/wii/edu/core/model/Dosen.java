/*
 * Dosen.java
 *
 * Created on July 11, 2007, 2:09 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package wii.edu.core.model;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import org.hibernate.Session;
import wii.edu.core.dao.HibernateUtil;

/**
 *
 * @author Hendro
 */
public class Dosen implements HttpSessionBindingListener {
    private long id;
    private String nomorPegawai;
    private String password;
    private String nama;
    private String jalan;
    private String kelurahan;
    private String kecamatan;
    private String subDistrik;
    private Distrik distrik;
    private String kodePos;
    private String telepon;
    private String handphone;
    private String email;
    private int jenisKelamin;
    private String pendidikanTerakhir;
    private int isLogin; //false : tidak aktif, true : aktif
    private String alamatLengkap;
    
    
    /** Creates a new instance of Dosen */
    public Dosen() {
    }

    public String getAlamatLengkap() {
        return alamatLengkap;
    }

    public void setAlamatLengkap() {
        this.alamatLengkap = getJalan() + " " + getSubDistrik() + " " + getKodePos() + ", " + getDistrik().getNama();
    }

    public int getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(int jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getPendidikanTerakhir() {
        return pendidikanTerakhir;
    }

    public void setPendidikanTerakhir(String pendidikanTerakhir) {
        this.pendidikanTerakhir = pendidikanTerakhir;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNomorPegawai() {
        return nomorPegawai;
    }

    public void setNomorPegawai(String nomorPegawai) {
        this.nomorPegawai = nomorPegawai;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
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
        this.alamatLengkap = getJalan() + ", " + getSubDistrik() + " " + getKodePos() + ", " + getDistrik().getNama();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(int isLogin) {
        this.isLogin = isLogin;
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
            Dosen activeDosen = (Dosen)event.getValue();
            System.out.println("is login : " + activeDosen.getIsLogin());
            activeDosen.setIsLogin(0);

            HibernateUtil.beginTransaction();
            Session sesi = HibernateUtil.getSession();
            sesi.clear();
            sesi.update(activeDosen);
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
            System.out.println("bisa..");
        } catch (Exception ex) {
            Logger.getLogger(Dosen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
