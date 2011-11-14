/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.operator;

import com.x3.monitoring.ApplicationContext;
import com.x3.monitoring.dao.PerusahaanDAO;
import com.x3.monitoring.dao.mysql.BidangUsahaDAOImpl;
import com.x3.monitoring.dao.mysql.PerusahaanDAOImpl;
import com.x3.monitoring.entity.Perusahaan;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

/**
 *
 * @author Hendro Steven
 */
public class CariTotalInvestasiPerBidangUsahaWnd extends ApplicationContext {

    Textbox txtBidangUsaha;
    Intbox txtIdBidangUsaha;
    Listbox lstHasil;
    Textbox txtTotal;

    public CariTotalInvestasiPerBidangUsahaWnd() {
    }

    public void onCreate() throws Exception {
        txtBidangUsaha = (Textbox) getFellow("txtBidangUsaha");
        txtIdBidangUsaha = (Intbox) getFellow("txtIdBidangUsaha");
        lstHasil = (Listbox) getFellow("lstHasil");
        txtTotal = (Textbox) getFellow("txtTotal");
    }

    public void cari() throws Exception {
        Connection conn = getConn();
        try {
            PerusahaanDAO dao = new PerusahaanDAOImpl(conn);
            List<Perusahaan> list = dao.gets(new BidangUsahaDAOImpl(conn).get(txtIdBidangUsaha.getValue()));
            lstHasil.getItems().clear();
            int no = 1;
            double total = 0;
            NumberFormat formatter = new DecimalFormat("#,##0.##");
            for (final Perusahaan p : list) {
                Listitem item = new Listitem();
                item.setValue(p);
                item.appendChild(new Listcell(p.getNamaPerusahaan()));
                item.appendChild(new Listcell(p.getNamaPimpinan()));
                item.appendChild(new Listcell(p.getAlamatJalan() + ", " + p.getKota() + " Telp: " + p.getTelp() + " Fax: " + p.getFax()));
                double totalInvestasi = p.getModalKerja() + p.getModalTetap();
                total += totalInvestasi;
                item.appendChild(new Listcell(formatter.format(totalInvestasi)));
                Toolbarbutton btnDetail = new Toolbarbutton();
                btnDetail.setId("btnDetail" + (no++));
                btnDetail.setImage("/img/detail.png");
                btnDetail.setTooltiptext("Klik di sini untuk Detail dan Perubahan data");
                btnDetail.addEventListener("onClick", new EventListener() {

                    public void onEvent(Event event) throws Exception {
                        //panggil form edit
                        Window win = (Window) Executions.createComponents("/zul/operator/detailPerusahaan.zul", null, null);
                        Label lblIdPerusahaan = (Label) win.getFellow("lblIdPerusahaan");
                        lblIdPerusahaan.setValue(p.getId());
                        win.doModal();
                        cari();
                    }
                });



                Listcell cellAksi = new Listcell();
                cellAksi.appendChild(btnDetail);
                item.appendChild(cellAksi);
                lstHasil.appendChild(item);
            }
            txtTotal.setValue(formatter.format(total));
        } catch (Exception ex) {
            Messagebox.show(ex.getMessage());
        } finally {
            conn.close();
        }
    }

    public void browseBidangUsaha() throws Exception {
        Window win = (Window) Executions.createComponents("/zul/admin/daftarBidangUsahaPencarian.zul", this, null);
        win.doModal();
    }
}
