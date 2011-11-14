/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.operator;

import com.x3.monitoring.ApplicationContextWindow;
import com.x3.monitoring.dao.IjinPusatDAO;
import com.x3.monitoring.dao.PerusahaanDAO;
import com.x3.monitoring.dao.mysql.IjinPusatDAOImpl;
import com.x3.monitoring.dao.mysql.PerusahaanDAOImpl;
import com.x3.monitoring.entity.IjinPusat;
import com.x3.monitoring.entity.Perusahaan;
import java.sql.Connection;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Label;

/**
 *
 * @author Hendro Steven
 */
public class DetailPerusahaanWnd extends ApplicationContextWindow {

    Label lblNamaPerusahaan;
    Label lblNamaPimpinan;
    Label lblJalan;
    Label lblKelurahan;
    Label lblKecamatan;
    Label lblKota;
    Label lblTelp;
    Label lblFax;
    Doublebox intLuasTanah;
    Doublebox intLuasGedung;
    Label lblBadanHukum;
    Label lblBentukModal;
    Label lblStatusPerusahaan;
    Label lblBidangUsaha;
    Label lblProduk;
    Doublebox dblModalTetap;
    Doublebox dblModalKerja;
    Intbox intTKILaki;
    Intbox intTKIPerem;
    Intbox intTKIJumlah;
    Intbox intTKALaki;
    Intbox intTKAPerem;
    Intbox intTKAJumlah;
    Label lblTahunProduksi;
    Label lblJenisProduksi;
    Label lblKapasitasTerpasang;
    Label lblRealisasi;
    Label lblExport;
    Doublebox dblNilaiExport;
    Label lblTahun1;
    Doublebox dblNilaiTahun1;
    Label lblTahun2;
    Doublebox dblNilaiTahun2;
    Label lblTahun3;
    Doublebox dblNilaiTahun3;
    Label lblDokAmdal;
    Label lblIjinPusat1;
    Label lblIjinPusatNomor1;
    Datebox dateIjinPusatTanggal1;
    Label lblIjinPusat2;
    Label lblIjinPusatNomor2;
    Datebox dateIjinPusatTanggal2;
    Label lblIjinPusat3;
    Label lblIjinPusatNomor3;
    Datebox dateIjinPusatTanggal3;
    Label lblIjinLokasiNomor;
    Datebox dateIjinLokasi;
    Label lblIMBNomor;
    Datebox dateIMB;
    Label lblTandaDaftarGudangNomor;
    Datebox dateTandaDaftarGudang;
    Label lblHONomor;
    Datebox dateHO;
    Label lblSIUPNomor;
    Datebox dateSIUP;
    Label lblTDPNomor;
    Datebox dateTDP;
    Label lblNPWP;
    Label lblIdPerusahaan;
    Datebox dateTanggalInput;

    public DetailPerusahaanWnd() {
    }

    public void onCreate() throws Exception {
        lblNamaPerusahaan = (Label) getFellow("lblNamaPerusahaan");
        lblNamaPimpinan = (Label) getFellow("lblNamaPimpinan");
        lblJalan = (Label) getFellow("lblJalan");
        lblKelurahan = (Label) getFellow("lblKelurahan");
        lblKecamatan = (Label) getFellow("lblKecamatan");
        lblKota = (Label) getFellow("lblKota");
        lblTelp = (Label) getFellow("lblTelp");
        lblFax = (Label) getFellow("lblFax");
        intLuasTanah = (Doublebox) getFellow("intLuasTanah");
        intLuasGedung = (Doublebox) getFellow("intLuasGedung");
        lblBadanHukum = (Label) getFellow("lblBadanHukum");
        lblBentukModal = (Label) getFellow("lblBentukModal");
        lblStatusPerusahaan = (Label) getFellow("lblStatusPerusahaan");
        lblBidangUsaha = (Label) getFellow("lblBidangUsaha");
        lblProduk = (Label) getFellow("lblProduk");
        dblModalTetap = (Doublebox) getFellow("dblModalTetap");
        dblModalKerja = (Doublebox) getFellow("dblModalKerja");
        intTKILaki = (Intbox) getFellow("intTKILaki");
        intTKIPerem = (Intbox) getFellow("intTKIPerem");
        intTKIJumlah = (Intbox) getFellow("intTKIJumlah");
        intTKALaki = (Intbox) getFellow("intTKALaki");
        intTKAPerem = (Intbox) getFellow("intTKAPerem");
        intTKAJumlah = (Intbox) getFellow("intTKAJumlah");
        lblTahunProduksi = (Label) getFellow("lblTahunProduksi");
        lblJenisProduksi = (Label) getFellow("lblJenisProduksi");
        lblKapasitasTerpasang = (Label) getFellow("lblKapasitasTerpasang");
        lblRealisasi = (Label) getFellow("lblRealisasi");
        lblExport = (Label) getFellow("lblExport");
        dblNilaiExport = (Doublebox) getFellow("dblNilaiExport");
        lblTahun1 = (Label) getFellow("lblTahun1");
        dblNilaiTahun1 = (Doublebox) getFellow("dblNilaiTahun1");
        lblTahun2 = (Label) getFellow("lblTahun2");
        dblNilaiTahun2 = (Doublebox) getFellow("dblNilaiTahun2");
        lblTahun3 = (Label) getFellow("lblTahun3");
        dblNilaiTahun3 = (Doublebox) getFellow("dblNilaiTahun3");
        lblDokAmdal = (Label) getFellow("lblDokAmdal");
        lblIjinPusat1 = (Label) getFellow("lblIjinPusat1");
        lblIjinPusatNomor1 = (Label) getFellow("lblIjinPusatNomor1");
        dateIjinPusatTanggal1 = (Datebox) getFellow("dateIjinPusatTanggal1");
        lblIjinPusat2 = (Label) getFellow("lblIjinPusat2");
        lblIjinPusatNomor2 = (Label) getFellow("lblIjinPusatNomor2");
        dateIjinPusatTanggal2 = (Datebox) getFellow("dateIjinPusatTanggal2");
        lblIjinPusat3 = (Label) getFellow("lblIjinPusat3");
        lblIjinPusatNomor3 = (Label) getFellow("lblIjinPusatNomor3");
        dateIjinPusatTanggal3 = (Datebox) getFellow("dateIjinPusatTanggal3");
        lblIjinLokasiNomor = (Label) getFellow("lblIjinLokasiNomor");
        dateIjinLokasi = (Datebox) getFellow("dateIjinLokasi");
        lblIMBNomor = (Label) getFellow("lblIMBNomor");
        dateIMB = (Datebox) getFellow("dateIMB");
        lblTandaDaftarGudangNomor = (Label) getFellow("lblTandaDaftarGudangNomor");
        dateTandaDaftarGudang = (Datebox) getFellow("dateTandaDaftarGudang");
        lblHONomor = (Label) getFellow("lblHONomor");
        dateHO = (Datebox) getFellow("dateHO");
        lblSIUPNomor = (Label) getFellow("lblSIUPNomor");
        dateSIUP = (Datebox) getFellow("dateSIUP");
        lblTDPNomor = (Label) getFellow("lblTDPNomor");
        dateTDP = (Datebox) getFellow("dateTDP");
        lblNPWP = (Label) getFellow("lblNPWP");

        lblIdPerusahaan = (Label) getFellow("lblIdPerusahaan");
        dateTanggalInput = (Datebox)getFellow("dateTanggalInput");

        loadData();
    }

    private void loadData() throws Exception {
        Connection conn = getConn();
        try {
            PerusahaanDAO dao = new PerusahaanDAOImpl(conn);
            Perusahaan p = dao.get(lblIdPerusahaan.getValue());
            dateTanggalInput.setValue(p.getTanggalInput());
            lblNamaPerusahaan.setValue(p.getNamaPerusahaan());
            lblNamaPimpinan.setValue(p.getNamaPimpinan());
            lblJalan.setValue(p.getAlamatJalan());

            lblKelurahan.setValue(p.getKelurahan().getNama());
            lblKecamatan.setValue(p.getKecamatan().getNama());
            lblKota.setValue(p.getKota());
            lblTelp.setValue(p.getTelp());
            lblFax.setValue(p.getFax());
            intLuasTanah.setValue(p.getLuasTanah());
            intLuasGedung.setValue(p.getLuasGedung());
            lblBadanHukum.setValue(p.getBadanHukum().getNama());
            lblBentukModal.setValue(p.getBentukModal().getNama());
            lblStatusPerusahaan.setValue(p.getStatusPerusahaan().getNama());
            lblBidangUsaha.setValue(p.getBidangUsaha().getJenis());
            lblProduk.setValue(p.getProduk());
            dblModalTetap.setValue(p.getModalTetap());
            dblModalKerja.setValue(p.getModalKerja());
            intTKILaki.setValue(p.getJmlTKILaki());
            intTKIPerem.setValue(p.getJmlTKIPerem());
            intTKIJumlah.setValue(p.getJmlTKIPerem() + p.getJmlTKILaki());
            intTKALaki.setValue(p.getJmlTKALaki());
            intTKAPerem.setValue(p.getJmlTKAPerem());
            intTKAJumlah.setValue(p.getJmlTKALaki() + p.getJmlTKAPerem());
            lblTahunProduksi.setValue(p.getTahunKegiatan());
            lblJenisProduksi.setValue(p.getJenisProduksi());
            lblKapasitasTerpasang.setValue(p.getKapasitasTerpasang());
            lblRealisasi.setValue(p.getRealisasiProduksi());
            lblExport.setValue(p.getExport());
            dblNilaiExport.setValue(p.getNilaiExport());
            lblTahun1.setValue(p.getTahunOmset1());
            lblTahun2.setValue(p.getTahunOmset2());
            lblTahun3.setValue(p.getTahunOmset3());
            dblNilaiTahun1.setValue(p.getNilaiOmset1());
            dblNilaiTahun2.setValue(p.getNilaiOmset2());
            dblNilaiTahun3.setValue(p.getNilaiOmset3());
            lblDokAmdal.setValue(p.getDokumenAmdal());
            IjinPusatDAO ipDAO = new IjinPusatDAOImpl(conn);
            p.setIjinPusat(ipDAO.gets(p.getId()));
            if (p.getIjinPusat().size() > 0) {
                IjinPusat ip1 = p.getIjinPusat().get(0);
                lblIjinPusat1.setValue(ip1.getKeterangan());
                lblIjinPusatNomor1.setValue(ip1.getNomor());
                dateIjinPusatTanggal1.setValue(ip1.getTanggal());
            }
            if (p.getIjinPusat().size() > 1) {
                IjinPusat ip2 = p.getIjinPusat().get(1);
                lblIjinPusat2.setValue(ip2.getKeterangan());
                lblIjinPusatNomor2.setValue(ip2.getNomor());
                dateIjinPusatTanggal2.setValue(ip2.getTanggal());
            }
            if (p.getIjinPusat().size() > 2) {
                IjinPusat ip3 = p.getIjinPusat().get(2);
                lblIjinPusat3.setValue(ip3.getKeterangan());
                lblIjinPusatNomor3.setValue(ip3.getNomor());
                dateIjinPusatTanggal3.setValue(ip3.getTanggal());
            }

            lblIjinLokasiNomor.setValue(p.getLokasiNomor());
            dateIjinLokasi.setValue(p.getLokasiTanggal());
            lblIMBNomor.setValue(p.getImbNomor());
            dateIMB.setValue(p.getImbTanggal());
            lblTandaDaftarGudangNomor.setValue(p.getGudangNomor());
            dateTandaDaftarGudang.setValue(p.getGudangTanggal());
            lblHONomor.setValue(p.getHoNomor());
            dateHO.setValue(p.getHoTanggal());
            lblSIUPNomor.setValue(p.getSiupNomor());
            dateSIUP.setValue(p.getSiupTanggal());
            lblTDPNomor.setValue(p.getTdpNomor());
            dateTDP.setValue(p.getTdpTanggal());
            lblNPWP.setValue(p.getNpwp());


        } catch (Exception ex) {
            ex.printStackTrace();
            Messagebox.show(ex.getMessage());
        } finally {
            conn.close();
        }
    }
}
