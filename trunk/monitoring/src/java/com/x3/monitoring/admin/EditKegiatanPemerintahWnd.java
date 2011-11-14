/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.admin;

import com.x3.monitoring.ApplicationContextWindow;
import com.x3.monitoring.dao.BidangUsahaPemerintahDAO;
import com.x3.monitoring.dao.KecamatanDAO;
import com.x3.monitoring.dao.KegiatanPemerintahDAO;
import com.x3.monitoring.dao.KelurahanDAO;
import com.x3.monitoring.dao.SumberDanaDAO;
import com.x3.monitoring.dao.mysql.BidangUsahaPemerintahDAOImpl;
import com.x3.monitoring.dao.mysql.KecamatanDAOImpl;
import com.x3.monitoring.dao.mysql.KegiatanPemerintahDAOImpl;
import com.x3.monitoring.dao.mysql.KelurahanDAOImpl;
import com.x3.monitoring.dao.mysql.SumberDanaDAOImpl;
import com.x3.monitoring.entity.BidangUsahaPemerintah;
import com.x3.monitoring.entity.Kecamatan;
import com.x3.monitoring.entity.KegiatanPemerintah;
import com.x3.monitoring.entity.Kelurahan;
import com.x3.monitoring.entity.SumberDana;
import java.sql.Connection;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

/**
 *
 * @author Hendro Steven
 */
public class EditKegiatanPemerintahWnd extends ApplicationContextWindow {

    Textbox txtId;
    Textbox txtNama;
    Textbox txtJalan;
    Combobox cmbKelurahan;
    Combobox cmbKecamatan;
    Textbox txtKota;
    Combobox cmbSumberDana;
    Textbox txtTahun;
    Combobox cmbBidangKegiatan;
    Doublebox dblNilaiInvestasi;

    public EditKegiatanPemerintahWnd() {
    }

    public void onCreate() throws Exception {
        txtNama = (Textbox) getFellow("txtNama");
        txtJalan = (Textbox) getFellow("txtJalan");
        cmbKelurahan = (Combobox) getFellow("cmbKelurahan");
        cmbKecamatan = (Combobox) getFellow("cmbKecamatan");
        txtKota = (Textbox) getFellow("txtKota");
        cmbSumberDana = (Combobox) getFellow("cmbSumberDana");
        txtTahun = (Textbox) getFellow("txtTahun");
        cmbBidangKegiatan = (Combobox) getFellow("cmbBidangKegiatan");
        txtId = (Textbox) getFellow("txtId");
        dblNilaiInvestasi = (Doublebox)getFellow("dblNilaiInvestasi");
        loadKelurahan();
        loadKecamatan();
        loadSumberDana();
        loadBidangKegiatan();
        loadData();
    }

    public void simpan() throws Exception {
        Connection conn = getConn();
        try {
            if (!txtNama.getValue().isEmpty() && !txtJalan.getValue().isEmpty() && !txtKota.getValue().isEmpty() && !txtTahun.getValue().isEmpty()) {
                KegiatanPemerintahDAO dao = new KegiatanPemerintahDAOImpl(conn);
                KegiatanPemerintah kp = dao.get(Integer.valueOf(txtId.getValue()));
                kp.setNama(txtNama.getValue());
                kp.setJalan(txtJalan.getValue());
                kp.setKota(txtKota.getValue());
                kp.setTahun(txtTahun.getValue());
                kp.setKelurahan((Kelurahan) cmbKelurahan.getSelectedItem().getValue());
                kp.setKecamatan((Kecamatan) cmbKecamatan.getSelectedItem().getValue());
                kp.setSumberDana((SumberDana) cmbSumberDana.getSelectedItem().getValue());
                kp.setBidang((BidangUsahaPemerintah) cmbBidangKegiatan.getSelectedItem().getValue());
                kp.setNilaiInvestasi(dblNilaiInvestasi.getValue());
                dao.update(kp);
                Messagebox.show("Data tersimpan");
                this.detach();
            } else {
                Messagebox.show("Input data dengan lengkap");
            }
        } catch (Exception ex) {
            Messagebox.show(ex.getMessage());
        } finally {
            conn.close();
        }
    }

    public void batal() {
        this.detach();
    }

    private void loadData() throws Exception {
        Connection conn = getConn();
        try {
            KegiatanPemerintahDAO dao = new KegiatanPemerintahDAOImpl(conn);
            KegiatanPemerintah kp = dao.get(Integer.valueOf(txtId.getValue()));
            txtNama.setValue(kp.getNama());
            txtJalan.setValue(kp.getJalan());
            txtKota.setValue(kp.getKota());
            txtTahun.setValue(kp.getTahun());
            dblNilaiInvestasi.setValue(kp.getNilaiInvestasi());

            for (int x = 0; x < cmbKelurahan.getItemCount(); x++) {
                Kelurahan kel = (Kelurahan) cmbKelurahan.getItemAtIndex(x).getValue();
                if (kel.getId() == kp.getKelurahan().getId()) {
                    cmbKelurahan.setSelectedIndex(x);
                    break;
                }
            }
            for (int x = 0; x < cmbKecamatan.getItemCount(); x++) {
                Kecamatan kec = (Kecamatan) cmbKecamatan.getItemAtIndex(x).getValue();
                if (kec.getId() == kp.getKecamatan().getId()) {
                    cmbKecamatan.setSelectedIndex(x);
                    break;
                }
            }

            for (int x = 0; x < cmbSumberDana.getItemCount(); x++) {
                SumberDana sd = (SumberDana) cmbSumberDana.getItemAtIndex(x).getValue();
                if (sd.getId() == kp.getSumberDana().getId()) {
                    cmbSumberDana.setSelectedIndex(x);
                    break;
                }
            }

            for (int x = 0; x < cmbBidangKegiatan.getItemCount(); x++) {
                BidangUsahaPemerintah bup = (BidangUsahaPemerintah) cmbBidangKegiatan.getItemAtIndex(x).getValue();
                if (bup.getId() == kp.getBidang().getId()) {
                    cmbBidangKegiatan.setSelectedIndex(x);
                    break;
                }
            }
        } catch (Exception ex) {
            Messagebox.show(ex.getMessage());
        } finally {
            conn.close();
        }
    }

    private void loadSumberDana() throws Exception {
        Connection conn = getConn();
        try {
            SumberDanaDAO dao = new SumberDanaDAOImpl(conn);
            cmbSumberDana.getItems().clear();
            for (SumberDana sd : dao.gets()) {
                Comboitem item = new Comboitem();
                item.setValue(sd);
                item.setLabel(sd.getNama());
                cmbSumberDana.appendChild(item);
            }
        } catch (Exception ex) {
            Messagebox.show(ex.getMessage());
        } finally {
            conn.close();
        }
    }

    private void loadBidangKegiatan() throws Exception {
        Connection conn = getConn();
        try {
            BidangUsahaPemerintahDAO dao = new BidangUsahaPemerintahDAOImpl(conn);
            cmbBidangKegiatan.getItems().clear();
            for (BidangUsahaPemerintah bup : dao.gets()) {
                Comboitem item = new Comboitem();
                item.setValue(bup);
                item.setLabel(bup.getNama());
                cmbBidangKegiatan.appendChild(item);
            }
        } catch (Exception ex) {
            Messagebox.show(ex.getMessage());
        } finally {
            conn.close();
        }
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
}
