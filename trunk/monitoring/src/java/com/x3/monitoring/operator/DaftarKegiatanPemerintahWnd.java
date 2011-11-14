/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.operator;

import com.x3.monitoring.ApplicationContext;
import com.x3.monitoring.dao.KegiatanPemerintahDAO;
import com.x3.monitoring.dao.mysql.KegiatanPemerintahDAOImpl;
import com.x3.monitoring.entity.KegiatanPemerintah;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;

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
                item.appendChild(new Listcell(kp.getJalan()+" "+ kp.getKelurahan().getNama()+" "+ kp.getKecamatan().getNama()+" "+kp.getKota()));
                item.appendChild(new Listcell(kp.getSumberDana().getNama()));
                item.appendChild(new Listcell(kp.getTahun()));
                item.appendChild(new Listcell(kp.getBidang().getNama()));
                item.appendChild(new Listcell(formatter.format(kp.getNilaiInvestasi())));
                lstKegiatanPemerintah.appendChild(item);
            }
        } catch (Exception ex) {
            Messagebox.show(ex.getMessage());
        } finally {
            conn.close();
        }
    }
}
