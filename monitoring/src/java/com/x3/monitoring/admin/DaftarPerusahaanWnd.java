/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.admin;

import com.x3.monitoring.ApplicationContext;
import com.x3.monitoring.dao.PerusahaanDAO;
import com.x3.monitoring.dao.mysql.PerusahaanDAOImpl;
import com.x3.monitoring.entity.Perusahaan;
import java.sql.Connection;
import java.util.List;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

/**
 *
 * @author Hendro Steven
 */
public class DaftarPerusahaanWnd extends ApplicationContext {

    Listbox lstPerusahaan;

    public DaftarPerusahaanWnd() {
    }

    public void onCreate() throws Exception {
        lstPerusahaan = (Listbox) getFellow("lstPerusahaan");
        load();
    }

    private void load() throws Exception {
        Connection conn = getConn();
        try {
            PerusahaanDAO dao = new PerusahaanDAOImpl(conn);
            List<Perusahaan> list = dao.gets();
            lstPerusahaan.getItems().clear();
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
                        Textbox txtIdPerusahaan = (Textbox) win.getFellow("txtIdPerusahaan");
                        txtIdPerusahaan.setValue(p.getId());
                        win.doModal();
                        load();
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
                lstPerusahaan.appendChild(item);
            }
        } catch (Exception ex) {
            Messagebox.show(ex.getMessage());
        } finally {
            conn.close();
        }
    }

    public void cetak() throws Exception {
        Window win = (Window) Executions.createComponents("/zul/admin/rpt_daftar_perusahaan_landscape.zul", null, null);
        Textbox sql = (Textbox) win.getFellow("sql");
        sql.setValue("SELECT perusahaan.id, perusahaan.nama_perusahaan, perusahaan.nama_pimpinan,"
                + "perusahaan.alamat_jalan, kelurahan.nama_kel, kecamatan.nama_kec, "
                + "perusahaan.kota, perusahaan.telp, perusahaan.fax, bidang_usaha.jenis, "
                + "(perusahaan.modal_kerja+perusahaan.modal_tetap) AS investasi, (perusahaan.jml_tki_l+ "
                + "perusahaan.jml_tki_p) AS tki, (perusahaan.jml_tka_l+perusahaan.jml_tka_p) AS tka, "
                + "(perusahaan.jml_tki_l+perusahaan.jml_tki_p+perusahaan.jml_tka_l+perusahaan.jml_tka_p) AS total "
                + "FROM perusahaan INNER JOIN "
                + "kecamatan ON kecamatan.id = perusahaan.kecamatan_id INNER JOIN "
                + "kelurahan ON kelurahan.id = perusahaan.kelurahan_id INNER JOIN "
                + "bidang_usaha ON bidang_usaha.id = perusahaan.bidang_usaha_id "
                + "ORDER BY perusahaan.nama_perusahaan");
        win.doModal();
    }
}
