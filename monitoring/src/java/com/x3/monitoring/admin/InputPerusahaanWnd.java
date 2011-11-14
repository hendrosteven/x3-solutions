/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.admin;

import com.x3.monitoring.ApplicationContext;
import com.x3.monitoring.dao.BadanHukumDAO;
import com.x3.monitoring.dao.BentukModalDAO;
import com.x3.monitoring.dao.IjinPusatDAO;
import com.x3.monitoring.dao.KecamatanDAO;
import com.x3.monitoring.dao.KelurahanDAO;
import com.x3.monitoring.dao.PerusahaanDAO;
import com.x3.monitoring.dao.StatusPerusahaanDAO;
import com.x3.monitoring.dao.mysql.BadanHukumDAOImpl;
import com.x3.monitoring.dao.mysql.BentukModalDAOImpl;
import com.x3.monitoring.dao.mysql.BidangUsahaDAOImpl;
import com.x3.monitoring.dao.mysql.IjinPusatDAOImpl;
import com.x3.monitoring.dao.mysql.KecamatanDAOImpl;
import com.x3.monitoring.dao.mysql.KelurahanDAOImpl;
import com.x3.monitoring.dao.mysql.PerusahaanDAOImpl;
import com.x3.monitoring.dao.mysql.StatusPerusahaanDAOImpl;
import com.x3.monitoring.entity.BadanHukum;
import com.x3.monitoring.entity.BentukModal;
import com.x3.monitoring.entity.IjinPusat;
import com.x3.monitoring.entity.Kecamatan;
import com.x3.monitoring.entity.Kelurahan;
import com.x3.monitoring.entity.Perusahaan;
import com.x3.monitoring.entity.StatusPerusahaan;
import java.sql.Connection;
import java.util.Date;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

/**
 *
 * @author Hendro Steven
 */
public class InputPerusahaanWnd extends ApplicationContext {

    Textbox txtNamaPerusahaan;
    Textbox txtNamaPimpinan;
    Textbox txtJalan;
    Combobox cmbKelurahan;
    Combobox cmbKecamatan;
    Textbox txtKota;
    Textbox txtTelp;
    Textbox txtFax;
    Intbox intLuasTanah;
    Intbox intLuasGedung;
    Combobox cmbBadanHukum;
    Combobox cmbBentukModal;
    Combobox cmbStatusPerusahaan;
    Textbox txtBidangUsaha;
    Intbox txtIdBidangUsaha;
    Textbox txtProduk;
    Doublebox dblModalTetap;
    Doublebox dblModalKerja;
    Intbox intTKILaki;
    Intbox intTKIPerem;
    Intbox intTKIJumlah;
    Intbox intTKALaki;
    Intbox intTKAPerem;
    Intbox intTKAJumlah;
    Textbox txtTahunProduksi;
    Textbox txtJenisProduksi;
    Textbox txtKapasitasTerpasang;
    Textbox txtRealisasi;
    Textbox txtExport;
    Doublebox dblNilaiExport;
    Textbox txtTahun1;
    Doublebox dblNilaiTahun1;
    Textbox txtTahun2;
    Doublebox dblNilaiTahun2;
    Textbox txtTahun3;
    Doublebox dblNilaiTahun3;
    Textbox txtDokAmdal;
    Textbox txtIjinPusat1;
    Textbox txtIjinPusatNomor1;
    Datebox dateIjinPusatTanggal1;
    Textbox txtIjinPusat2;
    Textbox txtIjinPusatNomor2;
    Datebox dateIjinPusatTanggal2;
    Textbox txtIjinPusat3;
    Textbox txtIjinPusatNomor3;
    Datebox dateIjinPusatTanggal3;
    Textbox txtIjinLokasiNomor;
    Datebox dateIjinLokasi;
    Textbox txtIMBNomor;
    Datebox dateIMB;
    Textbox txtTandaDaftarGudangNomor;
    Datebox dateTandaDaftarGudang;
    Textbox txtHONomor;
    Datebox dateHO;
    Textbox txtSIUPNomor;
    Datebox dateSIUP;
    Textbox txtTDPNomor;
    Datebox dateTDP;
    Textbox txtNPWP;
    Datebox dateTanggalInput;
    Textbox txtTDINomor;
    Datebox dateTDI;

    public InputPerusahaanWnd() {
    }

    public void onCreate() throws Exception {
        txtNamaPerusahaan = (Textbox) getFellow("txtNamaPerusahaan");
        txtNamaPimpinan = (Textbox) getFellow("txtNamaPimpinan");
        txtJalan = (Textbox) getFellow("txtJalan");
        cmbKelurahan = (Combobox) getFellow("cmbKelurahan");
        cmbKecamatan = (Combobox) getFellow("cmbKecamatan");
        txtKota = (Textbox) getFellow("txtKota");
        txtTelp = (Textbox) getFellow("txtTelp");
        txtFax = (Textbox) getFellow("txtFax");
        intLuasTanah = (Intbox) getFellow("intLuasTanah");
        intLuasGedung = (Intbox) getFellow("intLuasGedung");
        cmbBadanHukum = (Combobox) getFellow("cmbBadanHukum");
        cmbBentukModal = (Combobox) getFellow("cmbBentukModal");
        cmbStatusPerusahaan = (Combobox) getFellow("cmbStatusPerusahaan");
        txtBidangUsaha = (Textbox) getFellow("txtBidangUsaha");
        txtIdBidangUsaha = (Intbox) getFellow("txtIdBidangUsaha");
        txtProduk = (Textbox) getFellow("txtProduk");
        dblModalTetap = (Doublebox) getFellow("dblModalTetap");
        dblModalKerja = (Doublebox) getFellow("dblModalKerja");
        intTKILaki = (Intbox) getFellow("intTKILaki");
        intTKIPerem = (Intbox) getFellow("intTKIPerem");
        intTKIJumlah = (Intbox) getFellow("intTKIJumlah");
        intTKALaki = (Intbox) getFellow("intTKALaki");
        intTKAPerem = (Intbox) getFellow("intTKAPerem");
        intTKAJumlah = (Intbox) getFellow("intTKAJumlah");
        txtTahunProduksi = (Textbox) getFellow("txtTahunProduksi");
        txtJenisProduksi = (Textbox) getFellow("txtJenisProduksi");
        txtKapasitasTerpasang = (Textbox) getFellow("txtKapasitasTerpasang");
        txtRealisasi = (Textbox) getFellow("txtRealisasi");
        txtExport = (Textbox) getFellow("txtExport");
        dblNilaiExport = (Doublebox) getFellow("dblNilaiExport");
        txtTahun1 = (Textbox) getFellow("txtTahun1");
        dblNilaiTahun1 = (Doublebox) getFellow("dblNilaiTahun1");
        txtTahun2 = (Textbox) getFellow("txtTahun2");
        dblNilaiTahun2 = (Doublebox) getFellow("dblNilaiTahun2");
        txtTahun3 = (Textbox) getFellow("txtTahun3");
        dblNilaiTahun3 = (Doublebox) getFellow("dblNilaiTahun3");
        txtDokAmdal = (Textbox) getFellow("txtDokAmdal");
        txtIjinPusat1 = (Textbox) getFellow("txtIjinPusat1");
        txtIjinPusatNomor1 = (Textbox) getFellow("txtIjinPusatNomor1");
        dateIjinPusatTanggal1 = (Datebox) getFellow("dateIjinPusatTanggal1");
        txtIjinPusat2 = (Textbox) getFellow("txtIjinPusat2");
        txtIjinPusatNomor2 = (Textbox) getFellow("txtIjinPusatNomor2");
        dateIjinPusatTanggal2 = (Datebox) getFellow("dateIjinPusatTanggal2");
        txtIjinPusat3 = (Textbox) getFellow("txtIjinPusat3");
        txtIjinPusatNomor3 = (Textbox) getFellow("txtIjinPusatNomor3");
        dateIjinPusatTanggal3 = (Datebox) getFellow("dateIjinPusatTanggal3");
        txtIjinLokasiNomor = (Textbox) getFellow("txtIjinLokasiNomor");
        dateIjinLokasi = (Datebox) getFellow("dateIjinLokasi");
        txtIMBNomor = (Textbox) getFellow("txtIMBNomor");
        dateIMB = (Datebox) getFellow("dateIMB");
        txtTandaDaftarGudangNomor = (Textbox) getFellow("txtTandaDaftarGudangNomor");
        dateTandaDaftarGudang = (Datebox) getFellow("dateTandaDaftarGudang");
        txtHONomor = (Textbox) getFellow("txtHONomor");
        dateHO = (Datebox) getFellow("dateHO");
        txtSIUPNomor = (Textbox) getFellow("txtSIUPNomor");
        dateSIUP = (Datebox) getFellow("dateSIUP");
        txtTDPNomor = (Textbox) getFellow("txtTDPNomor");
        dateTDP = (Datebox) getFellow("dateTDP");
        txtNPWP = (Textbox) getFellow("txtNPWP");
        dateTanggalInput = (Datebox) getFellow("dateTanggalInput");
        txtTDINomor = (Textbox)getFellow("txtTDINomor");
        dateTDI = (Datebox)getFellow("dateTDI");

        loadKelurahan();
        loadKecamatan();
        loadBadanHukum();
        loadBentukModal();
        loadStatusPerusahaan();
    }

    private void loadKelurahan() throws Exception {
        Connection conn = getConn();
        try {
            KelurahanDAO dao = new KelurahanDAOImpl(conn);
            cmbKelurahan.getItems().clear();
            for (Kelurahan kel : dao.gets()) {
                Comboitem item = new Comboitem();
                item.setValue(kel);
                item.setLabel(kel.getNama());
                cmbKelurahan.appendChild(item);
            }
        } catch (Exception ex) {
            Messagebox.show(ex.getMessage());
        } finally {
            conn.close();
        }
    }

    private void loadKecamatan() throws Exception {
        Connection conn = getConn();
        try {
            KecamatanDAO dao = new KecamatanDAOImpl(conn);
            cmbKecamatan.getItems().clear();
            for (Kecamatan kec : dao.gets()) {
                Comboitem item = new Comboitem();
                item.setValue(kec);
                item.setLabel(kec.getNama());
                cmbKecamatan.appendChild(item);
            }
        } catch (Exception ex) {
            Messagebox.show(ex.getMessage());
        } finally {
            conn.close();
        }
    }

    private void loadBadanHukum() throws Exception {
        Connection conn = getConn();
        try {
            BadanHukumDAO dao = new BadanHukumDAOImpl(conn);
            cmbBadanHukum.getItems().clear();
            for (BadanHukum bh : dao.gets()) {
                Comboitem item = new Comboitem();
                item.setValue(bh);
                item.setLabel(bh.getNama());
                cmbBadanHukum.appendChild(item);
            }
        } catch (Exception ex) {
            Messagebox.show(ex.getMessage());
        } finally {
            conn.close();
        }
    }

    private void loadBentukModal() throws Exception {
        Connection conn = getConn();
        try {
            BentukModalDAO dao = new BentukModalDAOImpl(conn);
            cmbBentukModal.getItems().clear();
            for (BentukModal bm : dao.gets()) {
                Comboitem item = new Comboitem();
                item.setValue(bm);
                item.setLabel(bm.getNama());
                cmbBentukModal.appendChild(item);
            }
        } catch (Exception ex) {
            Messagebox.show(ex.getMessage());
        } finally {
            conn.close();
        }
    }

    private void loadStatusPerusahaan() throws Exception {
        Connection conn = getConn();
        try {
            StatusPerusahaanDAO dao = new StatusPerusahaanDAOImpl(conn);
            cmbStatusPerusahaan.getItems().clear();
            for (StatusPerusahaan sp : dao.gets()) {
                Comboitem item = new Comboitem();
                item.setValue(sp);
                item.setLabel(sp.getNama());
                cmbStatusPerusahaan.appendChild(item);
            }
        } catch (Exception ex) {
            Messagebox.show(ex.getMessage());
        } finally {
            conn.close();
        }
    }

    public void browseBidangUsaha() throws Exception {
        Window win = (Window) Executions.createComponents("/zul/admin/daftarBidangUsaha.zul", this, null);
        win.doModal();
    }

    public void hitungJmlTKI() throws Exception {
        intTKIJumlah.setValue(intTKILaki.getValue() + intTKIPerem.getValue());
    }

    public void hitungJmlTKA() throws Exception {
        intTKAJumlah.setValue(intTKALaki.getValue() + intTKAPerem.getValue());
    }

    public void simpan() throws Exception {
        Connection conn = getConn();
        conn.setAutoCommit(false);
        try {
            if (!txtNamaPerusahaan.getValue().isEmpty() && !txtNamaPimpinan.getValue().isEmpty() && !txtTelp.getValue().isEmpty()) {
                Perusahaan p = new Perusahaan();
                p.setId(System.currentTimeMillis() + "");
                p.setNamaPerusahaan(txtNamaPerusahaan.getValue());
                p.setNamaPimpinan(txtNamaPimpinan.getValue());
                p.setAlamatJalan(txtJalan.getValue());
                p.setKelurahan((Kelurahan) cmbKelurahan.getSelectedItem().getValue());
                p.setKecamatan((Kecamatan) cmbKecamatan.getSelectedItem().getValue());
                p.setKota(txtKota.getValue());
                p.setTelp(txtTelp.getValue());
                p.setFax(txtFax.getValue());
                p.setLuasTanah(intLuasTanah.getValue());
                p.setLuasGedung(intLuasGedung.getValue());
                p.setBadanHukum((BadanHukum) cmbBadanHukum.getSelectedItem().getValue());
                p.setBentukModal((BentukModal) cmbBentukModal.getSelectedItem().getValue());
                p.setStatusPerusahaan((StatusPerusahaan) cmbStatusPerusahaan.getSelectedItem().getValue());
                p.setBidangUsaha(new BidangUsahaDAOImpl(conn).get(txtIdBidangUsaha.getValue()));
                p.setProduk(txtProduk.getValue());
                p.setJmlTKILaki(intTKILaki.getValue());
                p.setJmlTKIPerem(intTKIPerem.getValue());
                p.setJmlTKALaki(intTKALaki.getValue());
                p.setJmlTKAPerem(intTKAPerem.getValue());
                p.setTahunKegiatan(txtTahunProduksi.getValue());
                p.setJenisProduksi(txtJenisProduksi.getValue());
                p.setKapasitasTerpasang(txtKapasitasTerpasang.getValue());
                p.setRealisasiProduksi(txtRealisasi.getValue());
                p.setExport(txtExport.getValue());
                p.setNilaiExport(dblNilaiExport.getValue());
                p.setTahunOmset1(txtTahun1.getValue());
                p.setNilaiOmset1(dblNilaiTahun1.getValue());
                p.setTahunOmset2(txtTahun2.getValue());
                p.setNilaiOmset2(dblNilaiTahun2.getValue());
                p.setTahunOmset3(txtTahun3.getValue());
                p.setNilaiOmset3(dblNilaiTahun3.getValue());
                p.setDokumenAmdal(txtDokAmdal.getValue());
                p.setTanggalInput(dateTanggalInput.getValue());
                p.setModalTetap(dblModalTetap.getValue());
                p.setModalKerja(dblModalKerja.getValue());


                //ijin daerah

                p.setLokasiNomor(txtIjinLokasiNomor.getValue());
                p.setLokasiTanggal(dateIjinLokasi.getValue());
                p.setImbNomor(txtIMBNomor.getValue());
                p.setImbTanggal(dateIMB.getValue());
                p.setGudangNomor(txtTandaDaftarGudangNomor.getValue());
                p.setGudangTanggal(dateTandaDaftarGudang.getValue());
                p.setHoNomor(txtHONomor.getValue());
                p.setHoTanggal(dateHO.getValue());
                p.setSiupNomor(txtSIUPNomor.getValue());
                p.setSiupTanggal(dateSIUP.getValue());
                p.setTdpNomor(txtTDPNomor.getValue());
                p.setTdpTanggal(dateTDP.getValue());
                p.setTdiNomor(txtTDINomor.getValue());
                p.setTdiTanggal(dateTDI.getValue());
                p.setNpwp(txtNPWP.getValue());
                PerusahaanDAO dao = new PerusahaanDAOImpl(conn);
                dao.insert(p);
                conn.commit();

                //Ijin Pusat
                IjinPusat ijin1 = null;
                if (!txtIjinPusatNomor1.getValue().isEmpty()) {
                    ijin1 = new IjinPusat();
                    ijin1.setPerusahaan(p);
                    ijin1.setKeterangan(txtIjinPusat1.getValue());
                    ijin1.setNomor(txtIjinPusatNomor1.getValue());
                    ijin1.setTanggal(dateIjinPusatTanggal1.getValue());
                    //System.out.println(ijin1.getTanggal().toString());
                }

                IjinPusat ijin2 = null;
                if (!txtIjinPusatNomor2.getValue().isEmpty()) {
                    ijin2 = new IjinPusat();
                    ijin2.setPerusahaan(p);
                    ijin2.setKeterangan(txtIjinPusat2.getValue());
                    ijin2.setNomor(txtIjinPusatNomor2.getValue());
                    ijin2.setTanggal(dateIjinPusatTanggal2.getValue());
                    //System.out.println(ijin1.getTanggal().toString());
                }

                IjinPusat ijin3 = null;
                if (!txtIjinPusatNomor3.getValue().isEmpty()) {
                    ijin3 = new IjinPusat();
                    ijin3.setPerusahaan(p);
                    ijin3.setKeterangan(txtIjinPusat3.getValue());
                    ijin3.setNomor(txtIjinPusatNomor3.getValue());
                    ijin3.setTanggal(dateIjinPusatTanggal3.getValue());
                    //System.out.println(ijin1.getTanggal().toString());
                }

                IjinPusatDAO ijinDAO = new IjinPusatDAOImpl(conn);
                if (ijin1 != null) {
                    ijinDAO.insert(ijin1);
                }
                if (ijin2 != null) {
                    ijinDAO.insert(ijin2);
                }
                if (ijin3 != null) {
                    ijinDAO.insert(ijin3);
                }

                conn.commit();

                Messagebox.show("Data Perusahaan Tersimpan.\n Untuk mengubah data perusahaan silahkan Edit dari daftar Perusahaan");
                //this.detach();
                batal();
                //this.getParent().getChildren().clear();
                //Executions.createComponents("/zul/admin/inputPerusahaan.zul", this.getRoot().getFellow("contentDiv"), null);
            } else {
                Messagebox.show("Input data dengan lengkap");
            }
        } catch (Exception ex) {
            conn.rollback();
            Messagebox.show(ex.getMessage());
        } finally {
            conn.setAutoCommit(true);
            conn.close();
        }
    }

    public void batal() throws Exception {
        txtNamaPerusahaan.setValue("");
        txtNamaPimpinan.setValue("");
         txtJalan.setValue("");
         cmbKelurahan.setValue("");
         cmbKecamatan.setValue("");
         txtKota.setValue("");
         txtTelp.setValue("");
         txtFax.setValue("");
         intLuasTanah.setValue(0);
         intLuasGedung.setValue(0);
         cmbBadanHukum.setValue("");
         cmbBentukModal.setValue("");
         cmbStatusPerusahaan.setValue("");
         txtBidangUsaha.setValue("");
         txtIdBidangUsaha.setValue(0);
         txtProduk.setValue("");
         dblModalTetap.setValue(0);
         dblModalKerja.setValue(0);
         intTKILaki.setValue(0);
         intTKIPerem.setValue(0);
         intTKIJumlah.setValue(0);
         intTKALaki.setValue(0);
         intTKAPerem.setValue(0);
         intTKAJumlah.setValue(0);
         txtTahunProduksi.setValue("");
         txtJenisProduksi.setValue("");
         txtKapasitasTerpasang.setValue("");
         txtRealisasi.setValue("");
         txtExport.setValue("");
         dblNilaiExport.setValue(0);
         txtTahun1.setValue("");
         dblNilaiTahun1.setValue(0);
         txtTahun2.setValue("");
         dblNilaiTahun2.setValue(0);
         txtTahun3.setValue("");
         dblNilaiTahun3.setValue(0);
         txtDokAmdal.setValue("");
         txtIjinPusat1.setValue("");
         txtIjinPusatNomor1.setValue("");
         dateIjinPusatTanggal1.setValue(new Date());
         txtIjinPusat2.setValue("");
         txtIjinPusatNomor2.setValue("");
         dateIjinPusatTanggal2.setValue(new Date());
         txtIjinPusat3.setValue("");
         txtIjinPusatNomor3.setValue("");
         dateIjinPusatTanggal3.setValue(new Date());
         txtIjinLokasiNomor.setValue("");
         dateIjinLokasi.setValue(new Date());
         txtIMBNomor.setValue("");
         dateIMB.setValue(new Date());
         txtTandaDaftarGudangNomor.setValue("");
         dateTandaDaftarGudang.setValue(new Date());
         txtHONomor.setValue("");
         dateHO.setValue(new Date());
         txtSIUPNomor.setValue("");
         dateSIUP.setValue(new Date());
         txtTDPNomor.setValue("");
         dateTDP.setValue(new Date());
         txtTDINomor.setValue("");
         dateTDI.setValue(new Date());
         txtNPWP.setValue("");
         dateTanggalInput.setValue(new Date());
    }
}
