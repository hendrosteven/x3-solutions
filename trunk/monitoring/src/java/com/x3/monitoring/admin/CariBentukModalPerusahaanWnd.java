/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.monitoring.admin;

import com.x3.monitoring.ApplicationContext;
import com.x3.monitoring.dao.BentukModalDAO;
import com.x3.monitoring.dao.PerusahaanDAO;
import com.x3.monitoring.dao.mysql.BentukModalDAOImpl;
import com.x3.monitoring.dao.mysql.PerusahaanDAOImpl;
import com.x3.monitoring.entity.BentukModal;
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
public class CariBentukModalPerusahaanWnd extends ApplicationContext{
    Combobox cmbBentukModal;
    Listbox lstHasil;

    public CariBentukModalPerusahaanWnd(){}

    public void onCreate()throws Exception{
        cmbBentukModal = (Combobox)getFellow("cmbBentukModal");
        lstHasil = (Listbox)getFellow("lstHasil");
        loadBentukModal();
        cmbBentukModal.setSelectedIndex(0);
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

    public void cari() throws Exception {
        Connection conn = getConn();
        try {
            PerusahaanDAO dao = new PerusahaanDAOImpl(conn);
            List<Perusahaan> list = dao.gets((BentukModal)cmbBentukModal.getSelectedItem().getValue());
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
                        Textbox txtIdPerusahaan = (Textbox)win.getFellow("txtIdPerusahaan");
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
        BentukModal bm = (BentukModal) cmbBentukModal.getSelectedItem().getValue();
        sql.setValue("SELECT * FROM perusahaan WHERE bentuk_modal_id=" + bm.getId());
        win.doModal();
    }
}
