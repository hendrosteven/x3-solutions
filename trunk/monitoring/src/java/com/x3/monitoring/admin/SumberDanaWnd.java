/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.admin;

import com.x3.monitoring.ApplicationContext;
import com.x3.monitoring.dao.SumberDanaDAO;
import com.x3.monitoring.dao.mysql.SumberDanaDAOImpl;
import com.x3.monitoring.entity.SumberDana;
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
public class SumberDanaWnd extends ApplicationContext {

    Listbox lstSumberDana;
    Textbox txtNama;
    Textbox txtId;

    public SumberDanaWnd() {
    }

    public void onCreate() throws Exception {
        lstSumberDana = (Listbox) getFellow("lstSumberDana");
        txtNama = (Textbox) getFellow("txtNama");
        txtId = (Textbox) getFellow("txtId");
        load();
    }

    public void load() throws Exception {
        Connection conn = getConn();
        try {
            final SumberDanaDAO dao = new SumberDanaDAOImpl(conn);
            List<SumberDana> list = dao.gets();
            lstSumberDana.getItems().clear();
            int no = 1;
            for (final SumberDana sd : list) {
                Listitem item = new Listitem();
                item.setValue(sd);
                item.appendChild(new Listcell(sd.getNama()));
                Listcell cellAksi = new Listcell();
                Toolbarbutton btnEdit = new Toolbarbutton();
                Toolbarbutton btnHapus = new Toolbarbutton();
                btnEdit.setId("btnEdit" + no++);
                btnEdit.setImage("/img/edit.png");
                btnEdit.setTooltiptext("Klik untuk mengubah Sumber Dana");
                btnHapus.setId("btnHapus" + no++);
                btnHapus.setImage("/img/delete.png");
                btnHapus.setTooltiptext("Klik untuk mengahapus Sumber Dana");
                btnEdit.addEventListener("onClick", new EventListener() {

                    public void onEvent(Event event) throws Exception {
                        txtId.setValue(sd.getId() + "");
                        txtNama.setValue(sd.getNama());
                        txtNama.setFocus(true);
                    }
                });
                btnHapus.addEventListener("onClick", new EventListener() {

                    public void onEvent(Event event) throws Exception {
                        if (Messagebox.show("Hapus?", "Konfirmasi", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
                            Connection connDel = getConn();
                            try {
                                SumberDanaDAO daoDel = new SumberDanaDAOImpl(connDel);
                                daoDel.delete(sd.getId());
                                load();
                            } catch (Exception ex) {
                                Messagebox.show(ex.getMessage());
                            } finally {
                                connDel.close();
                            }
                        }
                    }
                });
                cellAksi.appendChild(btnEdit);
                cellAksi.appendChild(new Space());
                cellAksi.appendChild(btnHapus);
                item.appendChild(cellAksi);
                lstSumberDana.appendChild(item);
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
            if (!txtNama.getValue().isEmpty()) {
                SumberDanaDAO dao = new SumberDanaDAOImpl(conn);
                if (txtId.getValue().isEmpty()) {//insert new
                    SumberDana sd = new SumberDana();
                    sd.setNama(txtNama.getValue());
                    dao.insert(sd);
                } else {//update
                    SumberDana sd = dao.get(Integer.valueOf(txtId.getValue()));
                    sd.setNama(txtNama.getValue());
                    dao.update(sd);
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
        txtNama.setValue("");
        txtId.setValue("");
    }
}
