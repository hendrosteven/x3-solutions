/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.admin;

import com.x3.monitoring.ApplicationContext;
import com.x3.monitoring.dao.KriteriaUsahaDAO;
import com.x3.monitoring.dao.PerusahaanDAO;
import com.x3.monitoring.dao.mysql.KriteriaUsahaDAOImpl;
import com.x3.monitoring.dao.mysql.PerusahaanDAOImpl;
import com.x3.monitoring.entity.KriteriaUsaha;
import com.x3.monitoring.entity.Perusahaan;
import java.sql.Connection;
import java.util.List;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
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
public class CariKriteriaTenagaKerjaPerusahaanWnd extends ApplicationContext {

    Combobox cmbKriteriaUsaha;
    Listbox lstHasil;

    public CariKriteriaTenagaKerjaPerusahaanWnd() {
    }

    public void onCreate() throws Exception {
        cmbKriteriaUsaha = (Combobox) getFellow("cmbKriteriaUsaha");
        lstHasil = (Listbox) getFellow("lstHasil");
        loadKriteriaUsaha();
    }

    private void loadKriteriaUsaha() throws Exception {
        Connection conn = getConn();
        try {
            KriteriaUsahaDAO dao = new KriteriaUsahaDAOImpl(conn);
            cmbKriteriaUsaha.getItems().clear();
            for (KriteriaUsaha ku : dao.gets()) {
                Comboitem item = new Comboitem();
                item.setValue(ku);
                item.setLabel(ku.getKeterangan());
                cmbKriteriaUsaha.appendChild(item);
            }
        } catch (Exception ex) {
            Messagebox.show(ex.getMessage());
        } finally {
            conn.close();
        }
    }

    private String getSql() {
        String sql = "";
        KriteriaUsaha ku = (KriteriaUsaha) cmbKriteriaUsaha.getSelectedItem().getValue();
        sql = "SELECT * FROM perusahaan WHERE ((jml_tki_l+jml_tki_p+jml_tka_l+jml_tka_p)" + ku.getOperator1() + "" + ku.getNilai1() + ") AND ("
                + "(jml_tki_l+jml_tki_p+jml_tka_l+jml_tka_p)" + ku.getOperator2() + "" + ku.getNilai2() + ")";
        System.out.println(sql);
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
                item.appendChild(new Listcell("L = " + p.getJmlTKILaki() + ", P = " + p.getJmlTKIPerem()));
                item.appendChild(new Listcell("L = " + p.getJmlTKALaki() + ", P = " + p.getJmlTKAPerem()));
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
}
