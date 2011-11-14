/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.admin;

import com.x3.monitoring.ApplicationContext;
import com.x3.monitoring.dao.KelurahanDAO;
import com.x3.monitoring.dao.mysql.KelurahanDAOImpl;
import com.x3.monitoring.entity.Kelurahan;
import java.sql.Connection;
import java.util.List;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

/**
 *
 * @author Hendro Steven
 */
public class KelurahanWnd extends ApplicationContext {

    Listbox lstKelurahan;
    Textbox txtNamaKelurahan;
    Textbox txtIdKelurahan;

    public KelurahanWnd() {
    }

    public void onCreate() throws Exception {
        lstKelurahan = (Listbox) getFellow("lstKelurahan");
        txtNamaKelurahan = (Textbox) getFellow("txtNamaKelurahan");
        txtIdKelurahan = (Textbox) getFellow("txtIdKelurahan");
        load();
    }

    private void load() throws Exception {
        Connection conn = getConn();
        try {
            final KelurahanDAO dao = new KelurahanDAOImpl(conn);
            List<Kelurahan> list = dao.gets();
            lstKelurahan.getItems().clear();
            int no = 1;
            for (final Kelurahan kel : list) {
                Listitem item = new Listitem();
                item.setValue(kel);
                item.appendChild(new Listcell(kel.getNama()));
                Listcell cellAksi = new Listcell();
                Toolbarbutton btnEdit = new Toolbarbutton();
                Toolbarbutton btnHapus = new Toolbarbutton();
                btnEdit.setId("btnEdit" + no++);
                btnEdit.setImage("/img/edit.png");
                btnEdit.setTooltiptext("Klik untuk mengubah kelurahan");
                btnHapus.setId("btnHapus" + no++);
                btnHapus.setImage("/img/delete.png");
                btnHapus.setTooltiptext("Klik untuk mengahapus kelurahan");
                btnEdit.addEventListener("onClick", new EventListener() {

                    public void onEvent(Event event) throws Exception {
                        txtIdKelurahan.setValue(kel.getId() + "");
                        txtNamaKelurahan.setValue(kel.getNama());
                        txtNamaKelurahan.setFocus(true);
                    }
                });
                btnHapus.addEventListener("onClick", new EventListener() {

                    public void onEvent(Event event) throws Exception {
                        if (Messagebox.show("Hapus?", "Konfirmasi", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
                            Connection connDel = getConn();
                            try {
                                KelurahanDAO daoDel = new KelurahanDAOImpl(connDel);
                                daoDel.delete(kel.getId());
                                load();
                            } catch (Exception ex) {
                                Messagebox.show(ex.getMessage());
                            }finally{
                                connDel.close();
                            }
                        }
                    }
                });
                cellAksi.appendChild(btnEdit);
                cellAksi.appendChild(new Space());
                cellAksi.appendChild(btnHapus);
                item.appendChild(cellAksi);
                lstKelurahan.appendChild(item);

            }
        } catch (Exception ex) {
            Messagebox.show(ex.getMessage());
        } finally {
            conn.close();
        }
    }

    public void simpan() throws Exception {
        Connection conn = getConn();
        try {
            if (!txtNamaKelurahan.getValue().isEmpty()) {
                KelurahanDAO dao = new KelurahanDAOImpl(conn);
                if (txtIdKelurahan.getValue().isEmpty()) {//insert new
                    Kelurahan kel = new Kelurahan();
                    kel.setNama(txtNamaKelurahan.getValue());
                    dao.insert(kel);
                } else {//update
                    Kelurahan kel = dao.get(Integer.valueOf(txtIdKelurahan.getValue()));
                    kel.setNama(txtNamaKelurahan.getValue());
                    dao.update(kel);
                }
                Messagebox.show("Data tersimpan");
                batal();
                load();
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
        txtNamaKelurahan.setValue("");
        txtIdKelurahan.setValue("");
    }
}
