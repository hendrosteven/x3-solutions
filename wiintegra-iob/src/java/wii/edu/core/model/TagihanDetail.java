/*
 * TagihanDetail.java
 *
 * Created on July 18, 2007, 12:04 PM
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
public class TagihanDetail {
    private long id;
    private KomponenBiaya komponen;
    private double tagih;
    private int sksAmbil;
    //private double bayar;
    //private Date tanggalBayar;
    //private int isDispensasi; //1= dispensasi   
    
      
    /** Creates a new instance of TagihanDetail */
    public TagihanDetail() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public KomponenBiaya getKomponen() {
        return komponen;
    }

    public void setKomponen(KomponenBiaya komponen) {
        this.komponen = komponen;
    }

//    public double getBayar() {
//        return bayar;
//    }
//
//    public void setBayar(double bayar) {
//        this.bayar = bayar;
//    }
//
//    public Date getTanggalBayar() {
//        return tanggalBayar;
//    }
//
//    public void setTanggalBayar(Date tanggalBayar) {
//        this.tanggalBayar = tanggalBayar;
//    }
//
//    public int getIsDispensasi() {
//        return isDispensasi;
//    }
//
//    public void setIsDispensasi(int isDispensasi) {
//        this.isDispensasi = isDispensasi;
//    }

    public double getTagih() {
        return tagih;
    }

    public void setTagih(double tagih) {
        this.tagih = tagih;
    }

    public int getSksAmbil() {
        return sksAmbil;
    }

    public void setSksAmbil(int sksAmbil) {
        this.sksAmbil = sksAmbil;
    }

        
}
