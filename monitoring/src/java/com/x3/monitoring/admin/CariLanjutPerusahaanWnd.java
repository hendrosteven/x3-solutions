/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.admin;

import com.x3.monitoring.ApplicationContext;
import com.x3.monitoring.dao.BadanHukumDAO;
import com.x3.monitoring.dao.BentukModalDAO;
import com.x3.monitoring.dao.KecamatanDAO;
import com.x3.monitoring.dao.PerusahaanDAO;
import com.x3.monitoring.dao.StatusPerusahaanDAO;
import com.x3.monitoring.dao.mysql.BadanHukumDAOImpl;
import com.x3.monitoring.dao.mysql.BentukModalDAOImpl;
import com.x3.monitoring.dao.mysql.KecamatanDAOImpl;
import com.x3.monitoring.dao.mysql.PerusahaanDAOImpl;
import com.x3.monitoring.dao.mysql.StatusPerusahaanDAOImpl;
import com.x3.monitoring.entity.BadanHukum;
import com.x3.monitoring.entity.BentukModal;
import com.x3.monitoring.entity.Kecamatan;
import com.x3.monitoring.entity.Perusahaan;
import com.x3.monitoring.entity.StatusPerusahaan;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Intbox;
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
public class CariLanjutPerusahaanWnd extends ApplicationContext {

    Textbox txtNama;
    Combobox cmbKecamatan;
    Combobox cmbBadanHukum;
    Combobox cmbBentukModal;
    Combobox cmbStatusPerusahaan;
    Textbox txtBidangUsaha;
    Intbox txtIdBidangUsaha;
    Checkbox chkNama;
    Checkbox chkKecamatan;
    Checkbox chkBadanHukum;
    Checkbox chkBentukModal;
    Checkbox chkStatusPerusahaan;
    Checkbox chkBidangUsaha;
    Listbox lstHasil;
    //Datebox dateTanggalInput;
    Textbox txtTahunInput;
    Checkbox chkTanggalInput;
    Combobox cmbBulanInput;

    public CariLanjutPerusahaanWnd() {
    }

    public void onCreate() throws Exception {
        txtNama = (Textbox) getFellow("txtNama");
        cmbKecamatan = (Combobox) getFellow("cmbKecamatan");
        cmbBadanHukum = (Combobox) getFellow("cmbBadanHukum");
        cmbBentukModal = (Combobox) getFellow("cmbBentukModal");
        cmbStatusPerusahaan = (Combobox) getFellow("cmbStatusPerusahaan");
        txtBidangUsaha = (Textbox) getFellow("txtBidangUsaha");
        txtIdBidangUsaha = (Intbox) getFellow("txtIdBidangUsaha");

        chkNama = (Checkbox) getFellow("chkNama");
        chkKecamatan = (Checkbox) getFellow("chkKecamatan");
        chkBadanHukum = (Checkbox) getFellow("chkBadanHukum");
        chkBentukModal = (Checkbox) getFellow("chkBentukModal");
        chkStatusPerusahaan = (Checkbox) getFellow("chkStatusPerusahaan");
        chkBidangUsaha = (Checkbox) getFellow("chkBidangUsaha");
        lstHasil = (Listbox) getFellow("lstHasil");
        //dateTanggalInput = (Datebox)getFellow("dateTanggalInput");
        txtTahunInput = (Textbox) getFellow("txtTahunInput");
        chkTanggalInput = (Checkbox) getFellow("chkTanggalInput");
        cmbBulanInput = (Combobox)getFellow("cmbBulanInput");

        loadKecamatan();
        loadBadanHukum();
        loadBentukModal();
        loadStatusPerusahaan();
        loadBulan();
    }

    private void loadBulan() throws Exception {
        cmbBulanInput.getItems().clear();
        Comboitem item1 = new Comboitem();
        item1.setValue("1");
        item1.setLabel("Januari");
        cmbBulanInput.appendChild(item1);

        Comboitem item2 = new Comboitem();
        item2.setValue("2");
        item2.setLabel("Februari");
        cmbBulanInput.appendChild(item2);

        Comboitem item3 = new Comboitem();
        item3.setValue("3");
        item3.setLabel("Maret");
        cmbBulanInput.appendChild(item3);

        Comboitem item4 = new Comboitem();
        item4.setValue("4");
        item4.setLabel("April");
        cmbBulanInput.appendChild(item4);

        Comboitem item = new Comboitem();
        item.setValue("5");
        item.setLabel("Mei");
        cmbBulanInput.appendChild(item);

        Comboitem item6 = new Comboitem();
        item6.setValue("6");
        item6.setLabel("Juni");
        cmbBulanInput.appendChild(item6);

        Comboitem item7 = new Comboitem();
        item7.setValue("7");
        item7.setLabel("Juli");
        cmbBulanInput.appendChild(item7);

        Comboitem item8 = new Comboitem();
        item8.setValue("8");
        item8.setLabel("Agustus");
        cmbBulanInput.appendChild(item8);

        Comboitem item9 = new Comboitem();
        item9.setValue("9");
        item9.setLabel("September");
        cmbBulanInput.appendChild(item9);

        Comboitem item10 = new Comboitem();
        item10.setValue("10");
        item10.setLabel("Oktober");
        cmbBulanInput.appendChild(item10);

        Comboitem item11 = new Comboitem();
        item11.setValue("11");
        item11.setLabel("November");
        cmbBulanInput.appendChild(item11);

        Comboitem item12 = new Comboitem();
        item12.setValue("12");
        item12.setLabel("Desember");
        cmbBulanInput.appendChild(item12);

        cmbBulanInput.setSelectedIndex(0);
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

    private String getSql() throws Exception{
        List<String> kondisi = new ArrayList<String>();
        String sql = "SELECT * FROM perusahaan";
        if (chkNama.isChecked()) {
            kondisi.add("nama_perusahaan LIKE '%" + txtNama.getValue() + "%'");
        }
        if (chkKecamatan.isChecked()) {
            kondisi.add("kecamatan_id=" + ((Kecamatan) cmbKecamatan.getSelectedItem().getValue()).getId());
        }
        if (chkBadanHukum.isChecked()) {
            kondisi.add("badan_hukum_id=" + ((BadanHukum) cmbBadanHukum.getSelectedItem().getValue()).getId());
        }
        if (chkBentukModal.isChecked()) {
            kondisi.add("bentuk_modal_id=" + ((BentukModal) cmbBentukModal.getSelectedItem().getValue()).getId());
        }
        if (chkStatusPerusahaan.isChecked()) {
            kondisi.add("status_perusahaan_id=" + ((StatusPerusahaan) cmbStatusPerusahaan.getSelectedItem().getValue()).getId());
        }
        if (chkBidangUsaha.isChecked()) {
            kondisi.add("bidang_usaha_id=" + txtIdBidangUsaha.getValue());
        }
        if (chkTanggalInput.isChecked()) {
            //kondisi.add("tgl_input='"+new DateTime(dateTanggalInput.getValue()).toString("yyyy-MM-dd")+"'");
            if(!txtTahunInput.getValue().isEmpty()){
            kondisi.add("MONTH(tgl_input)="+cmbBulanInput.getSelectedItem().getValue().toString());
            kondisi.add("YEAR(tgl_input)=" + txtTahunInput.getValue());
            }else{
                Messagebox.show("Field Tahun harus diisi..");                
            }
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
        //System.out.println(sql);
        return sql;
    }

    public void cari() throws Exception {
        Connection conn = getConn();
        try {
            //System.out.println(sql);
            PerusahaanDAO dao = new PerusahaanDAOImpl(conn);
            List<Perusahaan> list = dao.getsPerusahaan(getSql());
            lstHasil.getItems().clear();
            int no = 1;
            for (final Perusahaan p : list) {
                Listitem item = new Listitem();
                item.setValue(p);
                item.appendChild(new Listcell(p.getNamaPerusahaan()));
                item.appendChild(new Listcell(p.getNamaPimpinan()));
                item.appendChild(new Listcell(p.getAlamatJalan() + ", " + p.getKota() + " Telp: " + p.getTelp() + " Fax: " + p.getFax()));
                Toolbarbutton btnDetail = new Toolbarbutton();
                btnDetail.setId("btnDetail" + (no++));
                btnDetail.setImage("/img/detail.png");
                btnDetail.setTooltiptext("Klik di sini untuk Detail dan Perubahan data");
                btnDetail.addEventListener("onClick", new EventListener() {

                    public void onEvent(Event event) throws Exception {
                        //panggil form edit
                        Window win = (Window) Executions.createComponents("/zul/admin/editPerusahaan.zul", null, null);
                        Textbox txtIdPerusahaan = (Textbox) win.getFellow("txtIdPerusahaan");
                        txtIdPerusahaan.setValue(p.getId());
                        win.doModal();
                        cari();
                    }
                });

                Toolbarbutton btnHapus = new Toolbarbutton();
                btnHapus.setId("btnHapus" + no++);
                btnHapus.setImage("/img/delete.png");
                btnHapus.setTooltiptext("Klik untuk mengahapus Perusahaan");
                btnHapus.addEventListener("onClick", new EventListener() {

                    public void onEvent(Event event) throws Exception {
                        if (Messagebox.show("Hapus?", "Konfirmasi", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
                            Connection connDel = getConn();
                            try {
                                PerusahaanDAO daoDel = new PerusahaanDAOImpl(connDel);
                                daoDel.delete(p.getId());
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
        } catch (Exception ex) {
            Messagebox.show(ex.getMessage());
        } finally {
            conn.close();
        }
    }

    public void cetak() throws Exception {
        Window win = (Window) Executions.createComponents("/zul/admin/rpt_daftar_perusahaan.zul", null, null);
        Textbox sql = (Textbox) win.getFellow("sql");
        sql.setValue(getSql());
        win.doModal();
    }

    public void browseBidangUsaha() throws Exception {
        Window win = (Window) Executions.createComponents("/zul/admin/daftarBidangUsahaPencarian.zul", this, null);
        win.doModal();
    }
}
