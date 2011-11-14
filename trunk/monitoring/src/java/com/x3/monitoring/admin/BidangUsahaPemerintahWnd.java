/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.admin;

import com.x3.monitoring.ApplicationContext;
import com.x3.monitoring.dao.BidangUsahaPemerintahDAO;
import com.x3.monitoring.dao.mysql.BidangUsahaPemerintahDAOImpl;
import com.x3.monitoring.entity.BidangUsahaPemerintah;
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
public class BidangUsahaPemerintahWnd extends ApplicationContext{

    Listbox lstBidangKegiatan;
    Textbox txtNama;
    Textbox txtId;

    public BidangUsahaPemerintahWnd(){}

    public void onCreate()throws Exception{
        lstBidangKegiatan = (Listbox) getFellow("lstBidangKegiatan");
        txtNama = (Textbox) getFellow("txtNama");
        txtId = (Textbox) getFellow("txtId");
        load();
    }

    public void load()throws Exception{
        Connection conn = getConn();
        try {
            final BidangUsahaPemerintahDAO dao = new BidangUsahaPemerintahDAOImpl(conn);
            List<BidangUsahaPemerintah> list = dao.gets();
            lstBidangKegiatan.getItems().clear();
            int no = 1;
            for (final BidangUsahaPemerintah obj : list) {
                Listitem item = new Listitem();
                item.setValue(obj);
                item.appendChild(new Listcell(obj.getNama()));
                Listcell cellAksi = new Listcell();
                Toolbarbutton btnEdit = new Toolbarbutton();
                Toolbarbutton btnHapus = new Toolbarbutton();
                btnEdit.setId("btnEdit" + no++);
                btnEdit.setImage("/img/edit.png");
                btnEdit.setTooltiptext("Klik untuk mengubah Bidang Kegiatan Pemerintah");
                btnHapus.setId("btnHapus" + no++);
                btnHapus.setImage("/img/delete.png");
                btnHapus.setTooltiptext("Klik untuk mengahapus Bidang Kegiatan Pemerintah");
                btnEdit.addEventListener("onClick", new EventListener() {

                    public void onEvent(Event event) throws Exception {
                        txtId.setValue(obj.getId() + "");
                        txtNama.setValue(obj.getNama());
                        txtNama.setFocus(true);
                    }
                });
                btnHapus.addEventListener("onClick", new EventListener() {

                    public void onEvent(Event event) throws Exception {
                        if (Messagebox.show("Hapus?", "Konfirmasi", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
                            Connection connDel = getConn();
                            try {
                                BidangUsahaPemerintahDAO daoDel = new BidangUsahaPemerintahDAOImpl(connDel);
                                daoDel.delete(obj.getId());
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
                lstBidangKegiatan.appendChild(item);
            }

        } catch (Exception ex) {
            Messagebox.show(ex.getMessage());
        } finally {
            conn.close();
        }
    }

    public void simpan()throws Exception{
        Connection conn = getConn();
        try {
            if (!txtNama.getValue().isEmpty()) {
                BidangUsahaPemerintahDAO dao = new BidangUsahaPemerintahDAOImpl(conn);
                if (txtId.getValue().isEmpty()) {//insert new
                    BidangUsahaPemerintah obj = new BidangUsahaPemerintah();
                    obj.setNama(txtNama.getValue());
                    dao.insert(obj);
                } else {//update
                    BidangUsahaPemerintah obj = dao.get(Integer.valueOf(txtId.getValue()));
                    obj.setNama(txtNama.getValue());
                    dao.update(obj);
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

    public void batal(){
        txtNama.setValue("");
        txtId.setValue("");
    }
}

