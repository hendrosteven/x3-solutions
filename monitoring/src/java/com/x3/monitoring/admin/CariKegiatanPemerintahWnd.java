/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.admin;

import com.x3.monitoring.ApplicationContext;
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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

/**
 *
 * @author Hendro Steven
 */
public class CariKegiatanPemerintahWnd extends ApplicationContext {

    Textbox txtNama;
    Combobox cmbKelurahan;
    Combobox cmbKecamatan;
    Textbox txtKota;
    Combobox cmbSumberDana;
    Textbox txtTahun;
    Combobox cmbBidangKegiatan;
    Checkbox chkNama;
    Checkbox chkKelurahan;
    Checkbox chkKecamatan;
    Checkbox chkKota;
    Checkbox chkSumberDana;
    Checkbox chkTahun;
    Checkbox chkBidang;
    Listbox lstHasil;
    Textbox txtTotal;

    public CariKegiatanPemerintahWnd() {
    }

    public void onCreate() throws Exception {
        txtNama = (Textbox) getFellow("txtNama");
        cmbKelurahan = (Combobox) getFellow("cmbKelurahan");
        cmbKecamatan = (Combobox) getFellow("cmbKecamatan");
        txtKota = (Textbox) getFellow("txtKota");
        cmbSumberDana = (Combobox) getFellow("cmbSumberDana");
        txtTahun = (Textbox) getFellow("txtTahun");
        cmbBidangKegiatan = (Combobox) getFellow("cmbBidangKegiatan");

        chkNama = (Checkbox) getFellow("chkNama");
        chkKelurahan = (Checkbox) getFellow("chkKelurahan");
        chkKecamatan = (Checkbox) getFellow("chkKecamatan");
        chkKota = (Checkbox) getFellow("chkKota");
        chkSumberDana = (Checkbox) getFellow("chkSumberDana");
        chkTahun = (Checkbox) getFellow("chkTahun");
        chkBidang = (Checkbox) getFellow("chkBidang");
        lstHasil = (Listbox) getFellow("lstHasil");
        txtTotal = (Textbox)getFellow("txtTotal");

        loadBidangKegiatan();
        loadKecamatan();
        loadKelurahan();
        loadSumberDana();
    }

    private String getSQL() {
        List<String> kondisi = new ArrayList<String>();
        String sql = "SELECT "
                + "kegiatan_pemerintah.`id`,"
                + "kegiatan_pemerintah.`nama` AS nama,"
                + "kegiatan_pemerintah.`jalan` AS jalan,"
                + "kegiatan_pemerintah.`kota` AS kota,"
                + "kegiatan_pemerintah.`tahun` AS tahun,"
                + "kelurahan.`id` as kelurahan_id,"
                + "kecamatan.`id` as kecamatan_id,"
                + "sumber_dana.`id` as sumber_dana_id,"
                + "bidang_usaha_pemerintah.`id` as bidang_usaha_pemerintah_id,"
                + "kegiatan_pemerintah.`nilai_investasi`,"
                + "kelurahan.`nama_kel`,"
                + "kecamatan.`nama_kec`,"
                + "sumber_dana.`nama_sumber`,"
                + "kegiatan_pemerintah.`kelurahan_id`,"
                + "kegiatan_pemerintah.`kecamatan_id`,"
                + "kegiatan_pemerintah.`bidang_usaha_pemerintah_id`,"
                + "kegiatan_pemerintah.`sumber_dana_id`,"
                + "bidang_usaha_pemerintah.`nama_bidang` "
                + "FROM "
                + "`kecamatan` kecamatan INNER JOIN `kegiatan_pemerintah` kegiatan_pemerintah ON kecamatan.`id` = kegiatan_pemerintah.`kecamatan_id` "
                + "INNER JOIN `kelurahan` kelurahan ON kegiatan_pemerintah.`kelurahan_id` = kelurahan.`id` "
                + "INNER JOIN `sumber_dana` sumber_dana ON kegiatan_pemerintah.`sumber_dana_id` = sumber_dana.`id` "
                + "INNER JOIN `bidang_usaha_pemerintah` bidang_usaha_pemerintah ON kegiatan_pemerintah.`bidang_usaha_pemerintah_id` = bidang_usaha_pemerintah.`id`";
        //String sql = "SELECT id,nama,jalan,kota,tahun,kelurahan_id,kecamatan_id,sumber_dana_id,bidang_usaha_pemerintah_id FROM kegiatan_pemerintah";
        if (chkNama.isChecked()) {
            kondisi.add("kegiatan_pemerintah.`nama` LIKE '%" + txtNama.getValue() + "%'");
        }
        if (chkKota.isChecked()) {
            kondisi.add("kota LIKE '%" + txtKota.getValue() + "%'");
        }
        if (chkTahun.isChecked()) {
            kondisi.add("tahun=" + txtTahun.getValue());
        }
        if (chkKelurahan.isChecked()) {
            kondisi.add("kelurahan.`id`=" + ((Kelurahan) cmbKelurahan.getSelectedItem().getValue()).getId());
        }
        if (chkKecamatan.isChecked()) {
            kondisi.add("kecamatan.`id`=" + ((Kecamatan) cmbKecamatan.getSelectedItem().getValue()).getId());
        }
        if (chkSumberDana.isChecked()) {
            kondisi.add("sumber_dana.`id`=" + ((SumberDana) cmbSumberDana.getSelectedItem().getValue()).getId());
        }
        if (chkBidang.isChecked()) {
            kondisi.add("bidang_usaha_pemerintah.`id`=" + ((BidangUsahaPemerintah) cmbBidangKegiatan.getSelectedItem().getValue()).getId());
        }

        if (kondisi.size() > 0) {
            sql = sql + " WHERE ";
            for (int x = 0; x < kondisi.size(); x++) {
                sql += kondisi.get(x);
                if (x < kondisi.size() - 1) {
                    sql += " AND ";
                }
            }
        }
        return sql;
    }

    public void cari() throws Exception {
        Connection conn = getConn();
        try {
            KegiatanPemerintahDAO dao = new KegiatanPemerintahDAOImpl(conn);
            List<KegiatanPemerintah> list = dao.gets(getSQL());
            lstHasil.getItems().clear();
            int no = 1;
            NumberFormat formatter = new DecimalFormat("#,##0.##");
            double total = 0;
            for (final KegiatanPemerintah kp : list) {
                Listitem item = new Listitem();
                item.setValue(kp);
                item.appendChild(new Listcell(kp.getNama()));
                item.appendChild(new Listcell(kp.getJalan() + " " + kp.getKelurahan().getNama() + " " + kp.getKecamatan().getNama() + " " + kp.getKota()));
                item.appendChild(new Listcell(kp.getSumberDana().getNama()));
                item.appendChild(new Listcell(kp.getTahun()));
                item.appendChild(new Listcell(kp.getBidang().getNama()));
                item.appendChild(new Listcell(formatter.format(kp.getNilaiInvestasi())));
                total += kp.getNilaiInvestasi();
                Toolbarbutton btnDetail = new Toolbarbutton();
                btnDetail.setId("btnDetail" + (no++));
                btnDetail.setTooltiptext("Klik di sini untuk melihat detail Kegiatan Pemerintah");
                btnDetail.setImage("/img/detail.png");
                btnDetail.addEventListener("onClick", new EventListener() {

                    public void onEvent(Event event) throws Exception {
                        Window win = (Window) Executions.createComponents("/zul/admin/edit_kegiatan_pemerintah.zul", null, null);
                        Textbox txtId = (Textbox) win.getFellow("txtId");
                        txtId.setValue(kp.getId() + "");
                        win.doModal();
                        cari();
                    }
                });

                Toolbarbutton btnHapus = new Toolbarbutton();
                btnHapus.setId("btnHapus" + no++);
                btnHapus.setImage("/img/delete.png");
                btnHapus.setTooltiptext("Klik untuk mengahapus Kegiatan Pemerintah");
                btnHapus.addEventListener("onClick", new EventListener() {

                    public void onEvent(Event event) throws Exception {
                        if (Messagebox.show("Hapus?", "Konfirmasi", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
                            Connection connDel = getConn();
                            try {
                                KegiatanPemerintahDAO daoDel = new KegiatanPemerintahDAOImpl(connDel);
                                daoDel.delete(kp.getId());
                                cari();
                            } catch (Exception ex) {
                                Messagebox.show(ex.getMessage());
                            } finally {
                                connDel.close();
                            }
                        }
                    }
                });

                Listcell cellAksi = new Listcell();
                cellAksi.appendChild(btnDetail);
                cellAksi.appendChild(new Space());
                cellAksi.appendChild(btnHapus);
                item.appendChild(cellAksi);
                lstHasil.appendChild(item);
            }
            txtTotal.setValue(formatter.format(total));
        } catch (Exception ex) {
            Messagebox.show(ex.getMessage());
        } finally {
            conn.close();
        }
        
    }

    public void cetak() throws Exception {        
        Window win = (Window) Executions.createComponents("/zul/admin/rpt_daftar_kegiatan_pemerintah.zul", null, null);
        Textbox sql = (Textbox) win.getFellow("sql");        
        sql.setValue(getSQL());
        win.doModal();
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
