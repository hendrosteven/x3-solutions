/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.admin;

import com.x3.monitoring.ApplicationContext;
import com.x3.monitoring.dao.KecamatanDAO;
import com.x3.monitoring.dao.mysql.KecamatanDAOImpl;
import com.x3.monitoring.entity.Kecamatan;
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
public class KecamatanWnd extends ApplicationContext {

    Listbox lstKecamatan;
    Textbox txtNamaKecamatan;
    Textbox txtIdKecamatan;

    public KecamatanWnd() {
    }

    public void onCreate() throws Exception {
        lstKecamatan = (Listbox) getFellow("lstKecamatan");
        txtNamaKecamatan = (Textbox) getFellow("txtNamaKecamatan");
        txtIdKecamatan = (Textbox) getFellow("txtIdKecamatan");
        load();
    }

    private void load() throws Exception {
        Connection conn = getConn();
        try {
            final KecamatanDAO dao = new KecamatanDAOImpl(conn);
            List<Kecamatan> list = dao.gets();
            lstKecamatan.getItems().clear();
            int no = 1;
            for (final Kecamatan kec : list) {
                Listitem item = new Listitem();
                item.setValue(kec);
                item.appendChild(new Listcell(kec.getNama()));
                Listcell cellAksi = new Listcell();
                Toolbarbutton btnEdit = new Toolbarbutton();
                Toolbarbutton btnHapus = new Toolbarbutton();
                btnEdit.setId("btnEdit" + no++);
                btnEdit.setImage("/img/edit.png");
                btnEdit.setTooltiptext("Klik untuk mengubah kecamatan");
                btnHapus.setId("btnHapus" + no++);
                btnHapus.setImage("/img/delete.png");
                btnHapus.setTooltiptext("Klik untuk mengahapus kecamatan");
                btnEdit.addEventListener("onClick", new EventListener() {

                    public void onEvent(Event event) throws Exception {
                        txtIdKecamatan.setValue(kec.getId() + "");
                        txtNamaKecamatan.setValue(kec.getNama());
                        txtNamaKecamatan.setFocus(true);
                    }
                });
                btnHapus.addEventListener("onClick", new EventListener() {

                    public void onEvent(Event event) throws Exception {
                        if (Messagebox.show("Hapus?", "Konfirmasi", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
                            Connection connDel = getConn();
                            try {
                                KecamatanDAO daoDel = new KecamatanDAOImpl(connDel);
                                daoDel.delete(kec.getId());
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
                lstKecamatan.appendChild(item);

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
            if (!txtNamaKecamatan.getValue().isEmpty()) {
                KecamatanDAO dao = new KecamatanDAOImpl(conn);
                if (txtIdKecamatan.getValue().isEmpty()) {//insert new
                    Kecamatan kec = new Kecamatan();
                    kec.setNama(txtNamaKecamatan.getValue());
                    dao.insert(kec);
                } else {//update
                    Kecamatan kec = dao.get(Integer.valueOf(txtIdKecamatan.getValue()));
                    kec.setNama(txtNamaKecamatan.getValue());
                    dao.update(kec);
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
        txtNamaKecamatan.setValue("");
        txtIdKecamatan.setValue("");
    }
}
