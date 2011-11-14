/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.operator;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Div;
import org.zkoss.zul.Window;

/**
 *
 * @author Hendro Steven
 */
public class IndexWnd extends Window {

    Div contentDiv;
//    Menubar menubar;
//    Menupopup popup;

    public IndexWnd() {
    }

    public void onCreate() throws Exception {
        contentDiv = (Div) getFellow("contentDiv");
//        menubar = (Menubar) getFellow("menubar");
//        popup = (Menupopup) getFellow("popup");
//
//        Menuitem item = new Menuitem();
//        item.setId("menuItemLogin");
//        item.setLabel("Login Admin");
//        item.addEventListener("onClick", new EventListener() {
//
//            public void onEvent(Event event) throws Exception {
//                menubar.getChildren().clear();
//                contentDiv.getChildren().clear();
//                login().doModal();
//
//            }
//        });
//        item.setParent(menubar.getFellow("popup"));
        //showHome();
    }

    public void login() throws InterruptedException {
        final Window win = (Window) Executions.createComponents("/zul/admin/login.zul", this, null);
        win.doModal();
        //return win;
    }

    public void showPerusahaan() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/operator/daftarPerusahaan.zul", contentDiv, null);
    }

    public void showHome() {
        //contentDiv.getChildren().clear();
        //Executions.createComponents("/zul/operator/home.zul", contentDiv, null);
        Executions.sendRedirect("/home.zul");
    }

    public void showReportPerusahaanByKecamatan() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/admin/rpt_perusahaan_kecamatan.zul", contentDiv, null);
    }

    public void showReportPerusahaanByBentukModal() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/admin/rpt_perusahaan_bentuk_modal.zul", contentDiv, null);
    }

    public void showReportPerusahaanByBidangUsaha() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/admin/rpt_perusahaan_bidang_usaha.zul", contentDiv, null);
    }

    public void showCariNamaPerusahaan() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/operator/cari_nama.zul", contentDiv, null);
    }

    public void showCariKecamatanPerusahaan() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/operator/cari_kecamatan.zul", contentDiv, null);
    }

    public void showCariBadanHukumPerusahaan() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/operator/cari_badan_hukum.zul", contentDiv, null);
    }

    public void showCariBentukModalPerusahaan() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/operator/cari_bentuk_modal.zul", contentDiv, null);
    }

    public void showCariBidangUsahaPerusahaan() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/operator/cari_bidang_usaha.zul", contentDiv, null);
    }

    public void showCariStatusPerusahaan() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/operator/cari_status.zul", contentDiv, null);
    }

    public void showCariJumlahKaryawanPerusahaan() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/operator/cari_jumlah_karyawan.zul", contentDiv, null);
    }

    public void showCariJumlahKriteriaPerusahaan(){
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/operator/cari_kriteria_jumlah_karyawan.zul", contentDiv, null);
    }

    public void showCariLanjutPerusahaan() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/operator/cari_lanjut.zul", contentDiv, null);
    }

    public void showDaftarKegiatanPemerintah() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/operator/daftar_kegiatan_pemerintah.zul", contentDiv, null);
    }

    public void showCariTotalInvestasi() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/operator/cari_total_investasi.zul", contentDiv, null);
    }

    public void showCariTotalInvestasiPerKecamatan() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/operator/cari_total_investasi_kecamatan.zul", contentDiv, null);
    }

    public void showCariTotalInvestasiPerBidangUsaha() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/operator/cari_total_investasi_bidang_usaha.zul", contentDiv, null);
    }

     public void showCariTotalInvestasiPerBentukModal() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/operator/cari_total_investasi_bentukmodal.zul", contentDiv, null);
    }

    public void showCariKegiatanPemerintah() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/operator/cari_kegiatan_pemerintah.zul", contentDiv, null);
    }
}
