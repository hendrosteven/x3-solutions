/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.admin;

import com.x3.monitoring.ApplicationContext;
import com.x3.monitoring.dao.KegiatanPemerintahDAO;
import com.x3.monitoring.dao.mysql.KegiatanPemerintahDAOImpl;
import com.x3.monitoring.entity.KegiatanPemerintah;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
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
public class DaftarKegiatanPemerintahWnd extends ApplicationContext {

    Listbox lstKegiatanPemerintah;

    public DaftarKegiatanPemerintahWnd() {
    }

    public void onCreate() throws Exception {
        lstKegiatanPemerintah = (Listbox) getFellow("lstKegiatanPemerintah");
        load();
    }

    public void load() throws Exception {
        Connection conn = getConn();
        try {
            KegiatanPemerintahDAO dao = new KegiatanPemerintahDAOImpl(conn);
            List<KegiatanPemerintah> list = dao.gets();
            lstKegiatanPemerintah.getItems().clear();
            int no = 1;
            NumberFormat formatter = new DecimalFormat("#,##0.##");
            for (final KegiatanPemerintah kp : list) {
                Listitem item = new Listitem();
                item.setValue(kp);
                item.appendChild(new Listcell(kp.getNama()));
                item.appendChild(new Listcell(kp.getJalan() + " " + kp.getKelurahan().getNama() + " " + kp.getKecamatan().getNama() + " " + kp.getKota()));
                item.appendChild(new Listcell(kp.getSumberDana().getNama()));
                item.appendChild(new Listcell(kp.getTahun()));
                item.appendChild(new Listcell(kp.getBidang().getNama()));
                item.appendChild(new Listcell(formatter.format(kp.getNilaiInvestasi())));
                Toolbarbutton btnDetail = new Toolbarbutton();
                btnDetail.setId("btnDetail" + (no++));
                btnDetail.setTooltiptext("Klik di sini untuk melihat detail Kegiatan Pemerintah");
                btnDetail.setImage("/img/detail.png");
                btnDetail.addEventListener("onClick", new EventListener() {

                    public void onEvent(Event event) throws Exception {
                        Window win = (Window) Executions.createComponents("/zul/admin/edit_kegiatan_pemerintah.zul", null, null);
                        Textbox txtId = (Textbox) win.getFellow("txtId");
                        txtId.setValue(kp.getId() + "");
                        win.doModal();
                        load();
                    }
                });

                Toolbarbutton btnHapus = new Toolbarbutton();
                btnHapus.setId("btnHapus" + no++);
                btnHapus.setImage("/img/delete.png");
                btnHapus.setTooltiptext("Klik untuk mengahapus Kegiatan Pemerintah");
                btnHapus.addEventListener("onClick", new EventListener() {

                    public void onEvent(Event event) throws Exception {
                        if (Messagebox.show("Hapus?", "Konfirmasi", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
                            Connection connDel = getConn();
                            try {
                                KegiatanPemerintahDAO daoDel = new KegiatanPemerintahDAOImpl(connDel);
                                daoDel.delete(kp.getId());
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
                cellAksi.appendChild(btnDetail);
                cellAksi.appendChild(new Space());
                cellAksi.appendChild(btnHapus);
                item.appendChild(cellAksi);
                lstKegiatanPemerintah.appendChild(item);
            }
        } catch (Exception ex) {
            Messagebox.show(ex.getMessage());
        } finally {
            conn.close();
        }
    }
}
