/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.admin;

import com.x3.monitoring.ApplicationContext;
import com.x3.monitoring.dao.KriteriaUsahaDAO;
import com.x3.monitoring.dao.mysql.KriteriaUsahaDAOImpl;
import com.x3.monitoring.entity.KriteriaUsaha;
import java.sql.Connection;
import java.util.List;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Intbox;
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
public class KriteriaUsahaWnd extends ApplicationContext {

    Textbox txtKeterangan;
    Textbox txtId;
    Combobox cmbOperator1;
    Intbox txtNilai1;
    Combobox cmbOperator2;
    Intbox txtNilai2;
    Listbox lstKriteriaUsaha;

    public KriteriaUsahaWnd() {
    }

    public void onCreate() throws Exception {
        txtKeterangan = (Textbox) getFellow("txtKeterangan");
        txtId = (Textbox) getFellow("txtId");
        cmbOperator1 = (Combobox) getFellow("cmbOperator1");
        txtNilai1 = (Intbox) getFellow("txtNilai1");
        cmbOperator2 = (Combobox) getFellow("cmbOperator2");
        txtNilai2 = (Intbox) getFellow("txtNilai2");
        lstKriteriaUsaha = (Listbox) getFellow("lstKriteriaUsaha");
        load();
    }

    private void load() throws Exception {
        Connection conn = getConn();
        try {
            final KriteriaUsahaDAO dao = new KriteriaUsahaDAOImpl(conn);
            List<KriteriaUsaha> list = dao.gets();
            lstKriteriaUsaha.getItems().clear();
            int no = 1;
            for (final KriteriaUsaha ku : list) {
                Listitem item = new Listitem();
                item.setValue(ku);
                item.appendChild(new Listcell(ku.getKeterangan()));
                item.appendChild(new Listcell(ku.getOperator1()));
                item.appendChild(new Listcell(ku.getNilai1() + ""));
                item.appendChild(new Listcell(ku.getOperator2()));
                item.appendChild(new Listcell(ku.getNilai2() + ""));

                Toolbarbutton btnEdit = new Toolbarbutton();
                btnEdit.setId("btnEdit" + no++);
                btnEdit.setImage("/img/edit.png");
                btnEdit.setTooltiptext("Klik untuk mengubah Kriteria Usaha");
                btnEdit.addEventListener("onClick", new EventListener() {

                    public void onEvent(Event event) throws Exception {
                        txtId.setValue(ku.getId() + "");
                        txtKeterangan.setValue(ku.getKeterangan());
                        cmbOperator1.setValue(ku.getOperator1());
                        txtNilai1.setValue(ku.getNilai1());
                        cmbOperator2.setValue(ku.getOperator2());
                        txtNilai2.setValue(ku.getNilai2());
                        txtKeterangan.setFocus(true);
                    }
                });

                Toolbarbutton btnHapus = new Toolbarbutton();
                btnHapus.setId("btnHapus" + no++);
                btnHapus.setImage("/img/delete.png");
                btnHapus.setTooltiptext("Klik untuk mengahapus Kriteria Usaha");
                btnHapus.addEventListener("onClick", new EventListener() {

                    public void onEvent(Event event) throws Exception {
                        if (Messagebox.show("Hapus?", "Konfirmasi", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
                            Connection connDel = getConn();
                            try {
                                KriteriaUsahaDAO daoDel = new KriteriaUsahaDAOImpl(connDel);
                                daoDel.delete(ku.getId());
                                load();
                            } catch (Exception ex) {
                                Messagebox.show(ex.getMessage());
                            } finally {
                                connDel.close();
                            }
                        }
                    }
                });

                Listcell cellAksi = new Listcell();
                cellAksi.appendChild(btnEdit);
                cellAksi.appendChild(new Space());
                cellAksi.appendChild(btnHapus);
                item.appendChild(cellAksi);
                lstKriteriaUsaha.appendChild(item);
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
            if (!txtKeterangan.getValue().isEmpty()) {
                KriteriaUsahaDAO dao = new KriteriaUsahaDAOImpl(conn);
                if (txtId.getValue().isEmpty()) {//insert new
                    KriteriaUsaha ku = new KriteriaUsaha();
                    ku.setKeterangan(txtKeterangan.getValue());
                    ku.setOperator1(cmbOperator1.getSelectedItem().getLabel());
                    ku.setNilai1(txtNilai1.getValue());
                    ku.setOperator2(cmbOperator2.getSelectedItem().getLabel());
                    ku.setNilai2(txtNilai2.getValue());
                    dao.insert(ku);
                } else {//update
                    KriteriaUsaha ku = dao.get(Integer.valueOf(txtId.getValue()));
                    ku.setKeterangan(txtKeterangan.getValue());
                    ku.setOperator1(cmbOperator1.getSelectedItem().getLabel());
                    ku.setNilai1(txtNilai1.getValue());
                    ku.setOperator2(cmbOperator2.getSelectedItem().getLabel());
                    ku.setNilai2(txtNilai2.getValue());
                    dao.update(ku);
                }
                Messagebox.show("Data tersimpan");
                batal();
                load();
            }
        } catch (Exception ex) {
            Messagebox.show(ex.getMessage());
        } finally {
            conn.close();
        }
    }

    public void batal() {
        txtId.setValue("");
        txtKeterangan.setValue("");
        cmbOperator1.setValue("");
        txtNilai1.setValue(0);
        cmbOperator2.setValue("");
        txtNilai2.setValue(0);
    }
}
