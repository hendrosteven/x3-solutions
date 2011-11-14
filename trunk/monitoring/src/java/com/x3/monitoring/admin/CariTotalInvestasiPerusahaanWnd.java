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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Doublebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

/**
 *
 * @author Hendro Steven
 */
public class CariTotalInvestasiPerusahaanWnd extends ApplicationContext {

    //Checkbox chkLebihDari;
    Doublebox dblNilaiLebih;
    //Checkbox chkKurangDari;
    Doublebox dblNilaiKurang;
    //Checkbox chkAntara;
    Doublebox dblNilaiA;
    Doublebox dblNilaiB;
    Listbox lstHasil;
    Radiogroup rdPilihan;
    Textbox txtTotal;

    public CariTotalInvestasiPerusahaanWnd() {
    }

    public void onCreate() throws Exception {
        dblNilaiLebih = (Doublebox) getFellow("dblNilaiLebih");
        dblNilaiKurang = (Doublebox) getFellow("dblNilaiKurang");
        dblNilaiA = (Doublebox) getFellow("dblNilaiA");
        dblNilaiB = (Doublebox) getFellow("dblNilaiB");
        lstHasil = (Listbox) getFellow("lstHasil");
        rdPilihan = (Radiogroup) getFellow("rdPilihan");
        txtTotal = (Textbox) getFellow("txtTotal");
    }

    public void cari() throws Exception {
        Connection conn = getConn();
        try {
            PerusahaanDAO dao = new PerusahaanDAOImpl(conn);
            List<Perusahaan> list = new ArrayList<Perusahaan>();
            if (rdPilihan.getSelectedItem().getValue().equals("1")) {
                list = dao.gets(dblNilaiLebih.getValue(), true);
            } else if (rdPilihan.getSelectedItem().getValue().equals("2")) {
                list = dao.gets(dblNilaiKurang.getValue(), false);
            } else if (rdPilihan.getSelectedItem().getValue().equals("3")) {
                list = dao.gets(dblNilaiA.getValue(), dblNilaiB.getValue());
            }
            lstHasil.getItems().clear();
            int no = 1;
            NumberFormat formatter = new DecimalFormat("#,##0.##");
            double total = 0;
            for (final Perusahaan p : list) {
                Listitem item = new Listitem();
                item.setValue(p);
                item.appendChild(new Listcell(p.getNamaPerusahaan()));
                item.appendChild(new Listcell(p.getNamaPimpinan()));
                item.appendChild(new Listcell(p.getAlamatJalan() + ", " + p.getKota() + " Telp: " + p.getTelp() + " Fax: " + p.getFax()));
                double totalInvestasi = p.getModalKerja() + p.getModalTetap();
                item.appendChild(new Listcell(formatter.format(totalInvestasi)));
                total += totalInvestasi;
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
                        cari();
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
                                cari();
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
                lstHasil.appendChild(item);
            }
            txtTotal.setValue(formatter.format(total));
        } catch (Exception ex) {
            ex.printStackTrace();
            Messagebox.show("Terjadi Error : " + ex.getMessage());
        } finally {
            conn.close();
        }
    }

    public void cetak() throws Exception {
        Window win = (Window) Executions.createComponents("/zul/admin/rpt_daftar_perusahaan.zul", null, null);
        Textbox sql = (Textbox) win.getFellow("sql");
        if (rdPilihan.getSelectedItem().getValue().equals("1")) {
            sql.setValue("SELECT * FROM perusahaan WHERE modal_tetap+modal_kerja>=" + dblNilaiLebih.getValue());
        } else if (rdPilihan.getSelectedItem().getValue().equals("2")) {
            sql.setValue("SELECT * FROM perusahaan WHERE modal_tetap+modal_kerja<=" + dblNilaiKurang.getValue());
        } else if (rdPilihan.getSelectedItem().getValue().equals("3")) {
            sql.setValue("SELECT * FROM perusahaan WHERE modal_tetap+modal_kerja BETWEEN " + dblNilaiA.getValue() + " AND " + dblNilaiB.getValue());
        }
        win.doModal();
    }
}
