/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.monitoring.admin;

import com.x3.monitoring.ApplicationContext;
import com.x3.monitoring.dao.StatusPerusahaanDAO;
import com.x3.monitoring.dao.mysql.StatusPerusahaanDAOImpl;
import com.x3.monitoring.entity.StatusPerusahaan;
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
public class StatusPerusahaanWnd extends ApplicationContext {
    Listbox lstStatusPerusahaan;
    Textbox txtNama;
    Textbox txtId;

    public StatusPerusahaanWnd(){}

    public void onCreate()throws Exception{
        lstStatusPerusahaan = (Listbox)getFellow("lstStatusPerusahaan");
        txtNama = (Textbox)getFellow("txtNama");
        txtId = (Textbox)getFellow("txtId");
        load();
    }

    public void load()throws Exception{
        Connection conn = getConn();
        try {
            final StatusPerusahaanDAO dao = new StatusPerusahaanDAOImpl(conn);
            List<StatusPerusahaan> list = dao.gets();
            lstStatusPerusahaan.getItems().clear();
            int no = 1;
            for (final StatusPerusahaan sp : list) {
                Listitem item = new Listitem();
                item.setValue(sp);
                item.appendChild(new Listcell(sp.getNama()));
                Listcell cellAksi = new Listcell();
                Toolbarbutton btnEdit = new Toolbarbutton();
                Toolbarbutton btnHapus = new Toolbarbutton();
                btnEdit.setId("btnEdit" + no++);
                btnEdit.setImage("/img/edit.png");
                btnEdit.setTooltiptext("Klik untuk mengubah Badan Hukum");
                btnHapus.setId("btnHapus" + no++);
                btnHapus.setImage("/img/delete.png");
                btnHapus.setTooltiptext("Klik untuk mengahapus Badan Hukum");
                btnEdit.addEventListener("onClick", new EventListener() {

                    public void onEvent(Event event) throws Exception {
                        txtId.setValue(sp.getId() + "");
                        txtNama.setValue(sp.getNama());
                        txtNama.setFocus(true);
                    }
                });
                btnHapus.addEventListener("onClick", new EventListener() {

                    public void onEvent(Event event) throws Exception {
                        if (Messagebox.show("Hapus?", "Konfirmasi", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
                            Connection connDel = getConn();
                            try {
                                StatusPerusahaanDAO daoDel = new StatusPerusahaanDAOImpl(connDel);
                                daoDel.delete(sp.getId());
                                load();
                            } catch (Exception ex) {
                                Messagebox.show(ex.getMessage());
                            } finally{
                                connDel.close();
                            }
                        }
                    }
                });
                cellAksi.appendChild(btnEdit);
                cellAksi.appendChild(new Space());
                cellAksi.appendChild(btnHapus);
                item.appendChild(cellAksi);
                lstStatusPerusahaan.appendChild(item);

            }
        } catch (Exception ex) {
            Messagebox.show(ex.getMessage());
        }finally{
            conn.close();
        }
    }

    public void simpan() throws Exception {
        Connection conn = getConn();
        try {
            if (!txtNama.getValue().isEmpty()) {
                StatusPerusahaanDAO dao = new StatusPerusahaanDAOImpl(conn);
                if (txtId.getValue().isEmpty()) {//insert new
                    StatusPerusahaan sp = new StatusPerusahaan();
                    sp.setNama(txtNama.getValue());
                    dao.insert(sp);
                } else {//update
                    StatusPerusahaan sp = dao.get(Integer.valueOf(txtId.getValue()));
                    sp.setNama(txtNama.getValue());
                    dao.update(sp);
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
