/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.admin;

import com.x3.monitoring.entity.User;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
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
        Session session = Sessions.getCurrent();
        User user = (User) session.getAttribute("CURRENT_USER");
        if (user == null) {
            login().doModal();
        }
        contentDiv = (Div) getFellow("contentDiv");
//        menubar = (Menubar) getFellow("menubar");
//        popup = (Menupopup) getFellow("popup");
//
//        Menuitem item = new Menuitem();
//        item.setId("menuItemLogout");
//        item.setLabel("Logout Admin");
//        item.addEventListener("onClick", new EventListener() {
//
//            public void onEvent(Event event) throws Exception {
//                menubar.getChildren().clear();
//                contentDiv.getChildren().clear();
//                logout();
//            }
//        });
//        item.setParent(menubar.getFellow("popup"));
    }

    public void logout() {
        Session session = Sessions.getCurrent();
        session.removeAttribute("CURRENT_USER");
        Executions.sendRedirect("/zul/operator/index.zul");
    }

    public Window login() {
        final Window win = (Window) Executions.createComponents("/zul/admin/login.zul", this, null);
        return win;
    }

    public void showObject(String objName) {
        contentDiv.getChildren().clear();
        Executions.createComponents(objName, contentDiv, null);
    }

    public void showInputPerusahaan() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/admin/inputPerusahaan.zul", contentDiv, null);
    }

    public void showDaftarPerusahaan() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/admin/daftarPerusahaan.zul", contentDiv, null);
    }

    public void showDaftarKelurahan() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/admin/kelurahan.zul", contentDiv, null);
    }

    public void showDaftarKecamatan() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/admin/kecamatan.zul", contentDiv, null);
    }

    public void showDaftarUser() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/admin/administrator.zul", contentDiv, null);
    }

    public void showDaftarBadanHukum() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/admin/badan_hukum.zul", contentDiv, null);
    }

    public void showDaftarBentukModal() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/admin/bentuk_modal.zul", contentDiv, null);
    }

    public void showDaftarStatusPerusahaan() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/admin/status_perusahaan.zul", contentDiv, null);
    }

    public void showDaftarBidangUsaha() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/admin/bidang_usaha.zul", contentDiv, null);
    }

    public void showDaftarKriteriaUsaha() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/admin/kriteria_usaha.zul", contentDiv, null);
    }

    public void showCariNamaPerusahaan() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/admin/cari_nama.zul", contentDiv, null);
    }

    public void showCariKecamatanPerusahaan() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/admin/cari_kecamatan.zul", contentDiv, null);
    }

    public void showCariBadanHukumPerusahaan() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/admin/cari_badan_hukum.zul", contentDiv, null);
    }

    public void showCariBentukModalPerusahaan() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/admin/cari_bentuk_modal.zul", contentDiv, null);
    }

    public void showCariBidangUsahaPerusahaan() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/admin/cari_bidang_usaha.zul", contentDiv, null);
    }

    public void showCariStatusPerusahaan() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/admin/cari_status.zul", contentDiv, null);
    }

    public void showCariLanjutPerusahaan() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/admin/cari_lanjut.zul", contentDiv, null);
    }

    public void showCariJumlahTenagaKerjaPerusahaan() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/admin/cari_jumlah_karyawan.zul", contentDiv, null);
    }

    public void showCariJumlahKriteriaPerusahaan(){
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/admin/cari_kriteria_jumlah_karyawan.zul", contentDiv, null);
    }

    public void showReportPerusahaanByKecamatan() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/admin/rpt_perusahaan_kecamatan.zul", contentDiv, null);
    }

    public void showReportDaftarPerusahaanByKecamatan() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/admin/rpt_daftar_perusahaan_kecamatan.zul", contentDiv, null);
    }

    public void showReportPerusahaanByBentukModal() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/admin/rpt_perusahaan_bentuk_modal.zul", contentDiv, null);
    }

    public void showReportPerusahaanByBidangUsaha() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/admin/rpt_perusahaan_bidang_usaha.zul", contentDiv, null);
    }

    public void showDaftarSumberDana() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/admin/sumber_dana.zul", contentDiv, null);
    }

    public void showDaftarBidangKegiatanPemerintah() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/admin/bidang_usaha_pemerintah.zul", contentDiv, null);
    }

    public void showDaftarKegiatanPemerintah() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/admin/daftar_kegiatan_pemerintah.zul", contentDiv, null);
    }

    public void showInputKegiatanPemerintah() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/admin/input_kegiatan_pemerintah.zul", contentDiv, null);
    }

    public void showSelayangPandang() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/admin/input_selayang_pandang.zul", contentDiv, null);
    }

    public void showPotensi() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/admin/input_potensi_investasi.zul", contentDiv, null);
    }

    public void showCariTotalNilaiInvestasi() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/admin/cari_total_investasi.zul", contentDiv, null);
    }

    public void showCariTotalNilaiInvestasiPerKecamatan() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/admin/cari_total_investasi_kecamatan.zul", contentDiv, null);
    }

    public void showCariTotalNilaiInvestasiPerBidangUsaha() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/admin/cari_total_investasi_bidang_usaha.zul", contentDiv, null);
    }

    public void showCariTotalNilaiInvestasiPerBentukModal() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/admin/cari_total_investasi_bentukmodal.zul", contentDiv, null);
    }

    public void showCariKegiatanPemerintah() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/admin/cari_kegiatan_pemerintah.zul", contentDiv, null);
    }

     public void showBackupAndRestore() {
        contentDiv.getChildren().clear();
        Executions.createComponents("/zul/admin/backup_restore.zul", contentDiv, null);
    }
}
