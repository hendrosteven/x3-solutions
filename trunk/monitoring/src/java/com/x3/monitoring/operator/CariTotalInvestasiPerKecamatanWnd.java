/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.operator;

import com.x3.monitoring.ApplicationContext;
import com.x3.monitoring.dao.KecamatanDAO;
import com.x3.monitoring.dao.PerusahaanDAO;
import com.x3.monitoring.dao.mysql.KecamatanDAOImpl;
import com.x3.monitoring.dao.mysql.PerusahaanDAOImpl;
import com.x3.monitoring.entity.Kecamatan;
import com.x3.monitoring.entity.Perusahaan;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
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
public class CariTotalInvestasiPerKecamatanWnd extends ApplicationContext {

    Combobox cmbKecamatan;
    Listbox lstHasil;
    Textbox txtTotal;

    public CariTotalInvestasiPerKecamatanWnd() {
    }

    public void onCreate() throws Exception {
        cmbKecamatan = (Combobox) getFellow("cmbKecamatan");
        lstHasil = (Listbox) getFellow("lstHasil");
        txtTotal = (Textbox) getFellow("txtTotal");
        loadKecamatan();
        cmbKecamatan.setSelectedIndex(0);
    }

    private void loadKecamatan() throws Exception {
        Connection conn = getConn();
        try {
            KecamatanDAO dao = new KecamatanDAOImpl(conn);
            cmbKecamatan.getItems().clear();
            for (Kecamatan kec : dao.gets()) {
                Comboitem item = new Comboitem();
                item.setValue(kec);
                item.setLabel(kec.getNama());
                cmbKecamatan.appendChild(item);
            }
        } catch (Exception ex) {
            Messagebox.show(ex.getMessage());
        } finally {
            conn.close();
        }
    }

    public void cari() throws Exception {
        Connection conn = getConn();
        try {
            PerusahaanDAO dao = new PerusahaanDAOImpl(conn);
            List<Perusahaan> list = dao.gets((Kecamatan) cmbKecamatan.getSelectedItem().getValue());
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

    
}
