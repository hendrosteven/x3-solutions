/*
 * Tagihan.java
 *
 * Created on 30 Juli 2007, 14:55
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package wii.edu.core.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author hendro
 */
public class Tagihan {
    private long id;   
    private Mahasiswa mhs;
    private Semester semester;
    private List tagihanDetails = new ArrayList();
    private double bayar;
    private int isDispensasi;
    private int isDispensasiPelunasan;
    private Date tanggalBayar;
    private double pelunasan;
    private Date tanggalPelunasan;
    private double totalTagihan;
    /** Creates a new instance of Tagihan */
    public Tagihan() {
    }

    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Mahasiswa getMhs() {
        return mhs;
    }

    public void setMhs(Mahasiswa mhs) {
        this.mhs = mhs;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public List getTagihanDetails() {
        return tagihanDetails;
    }

    public void setTagihanDetails(List tagihanDetails) {
        this.tagihanDetails = tagihanDetails;
    }

    public double getBayar() {
        return bayar;
    }

    public void setBayar(double bayar) {
        this.bayar = bayar;
    }

    public int getIsDispensasi() {
        return isDispensasi;
    }

    public void setIsDispensasi(int isDispensasi) {
        this.isDispensasi = isDispensasi;
    }

    public Date getTanggalBayar() {
        return tanggalBayar;
    }

    public void setTanggalBayar(Date tanggalBayar) {
        this.tanggalBayar = tanggalBayar;
    }

    public double getPelunasan() {
        return pelunasan;
    }

    public void setPelunasan(double pelunasan) {
        this.pelunasan = pelunasan;
    }

    public Date getTanggalPelunasan() {
        return tanggalPelunasan;
    }

    public void setTanggalPelunasan(Date tanggalPelunasan) {
        this.tanggalPelunasan = tanggalPelunasan;
    }

    public int getIsDispensasiPelunasan() {
        return isDispensasiPelunasan;
    }

    public void setIsDispensasiPelunasan(int isDispensasiPelunasan) {
        this.isDispensasiPelunasan = isDispensasiPelunasan;
    }

    public double getTotalTagihan() {
        return totalTagihan;
    }

    public void setTotalTagihan(double totalTagihan) {
        this.totalTagihan = totalTagihan;
    }

   
}
