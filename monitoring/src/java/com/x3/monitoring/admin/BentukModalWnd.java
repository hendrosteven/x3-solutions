/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.admin;

import com.x3.monitoring.ApplicationContext;
import com.x3.monitoring.dao.BentukModalDAO;
import com.x3.monitoring.dao.mysql.BentukModalDAOImpl;
import com.x3.monitoring.entity.BentukModal;
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
public class BentukModalWnd extends ApplicationContext {

    Listbox lstBentukModal;
    Textbox txtNama;
    Textbox txtId;

    public BentukModalWnd() {
    }

    public void onCreate() throws Exception {
        lstBentukModal = (Listbox) getFellow("lstBentukModal");
        txtNama = (Textbox) getFellow("txtNama");
        txtId = (Textbox) getFellow("txtId");
        load();
    }

    public void load()throws Exception{
        Connection conn = getConn();
        try {
            final BentukModalDAO dao = new BentukModalDAOImpl(conn);
            List<BentukModal> list = dao.gets();
            lstBentukModal.getItems().clear();
            int no = 1;
            for (final BentukModal bm : list) {
                Listitem item = new Listitem();
                item.setValue(bm);
                item.appendChild(new Listcell(bm.getNama()));

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
                        txtId.setValue(bm.getId() + "");
                        txtNama.setValue(bm.getNama());
                        txtNama.setFocus(true);
                    }
                });
                btnHapus.addEventListener("onClick", new EventListener() {

                    public void onEvent(Event event) throws Exception {
                        if (Messagebox.show("Hapus?", "Konfirmasi", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
                            Connection connDel = getConn();
                            try {
                                BentukModalDAO daoDel = new BentukModalDAOImpl(connDel);
                                daoDel.delete(bm.getId());
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
                lstBentukModal.appendChild(item);

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
                BentukModalDAO dao = new BentukModalDAOImpl(conn);
                if (txtId.getValue().isEmpty()) {//insert new
                    BentukModal bm = new BentukModal();
                    bm.setNama(txtNama.getValue());
                    dao.insert(bm);
                } else {//update
                    BentukModal bm = dao.get(Integer.valueOf(txtId.getValue()));
                    bm.setNama(txtNama.getValue());
                    dao.update(bm);
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
