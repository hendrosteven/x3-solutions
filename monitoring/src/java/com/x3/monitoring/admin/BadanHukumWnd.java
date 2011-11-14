/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.admin;

import com.x3.monitoring.ApplicationContext;
import com.x3.monitoring.dao.BadanHukumDAO;
import com.x3.monitoring.dao.mysql.BadanHukumDAOImpl;
import com.x3.monitoring.entity.BadanHukum;
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
public class BadanHukumWnd extends ApplicationContext {

    Listbox lstBadanHukum;
    Textbox txtNama;
    Textbox txtId;

    public BadanHukumWnd() {
    }

    public void onCreate() throws Exception {
        lstBadanHukum = (Listbox) getFellow("lstBadanHukum");
        txtNama = (Textbox) getFellow("txtNama");
        txtId = (Textbox) getFellow("txtId");
        load();
    }

    public void load()throws Exception{
        Connection conn = getConn();
        try {
            BadanHukumDAO dao = new BadanHukumDAOImpl(conn);
            List<BadanHukum> list = dao.gets();
            lstBadanHukum.getItems().clear();
            int no = 1;
            for (final BadanHukum bh : list) {
                Listitem item = new Listitem();
                item.setValue(bh);
                item.appendChild(new Listcell(bh.getNama()));
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
                        txtId.setValue(bh.getId() + "");
                        txtNama.setValue(bh.getNama());
                        txtNama.setFocus(true);
                    }
                });
                btnHapus.addEventListener("onClick", new EventListener() {

                    public void onEvent(Event event) throws Exception {
                        if (Messagebox.show("Hapus?", "Konfirmasi", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
                            Connection connDel = getConn();
                            try {
                                BadanHukumDAO daoDel = new BadanHukumDAOImpl(connDel);
                                daoDel.delete(bh.getId());
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
                lstBadanHukum.appendChild(item);

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
                BadanHukumDAO dao = new BadanHukumDAOImpl(conn);
                if (txtId.getValue().isEmpty()) {//insert new
                    BadanHukum bh = new BadanHukum();
                    bh.setNama(txtNama.getValue());
                    dao.insert(bh);
                } else {//update
                    BadanHukum bh = dao.get(Integer.valueOf(txtId.getValue()));
                    bh.setNama(txtNama.getValue());
                    dao.update(bh);
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
