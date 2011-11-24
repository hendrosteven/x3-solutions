/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.admin;

import com.x3.monitoring.ApplicationContext;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import org.zkoss.zkex.zul.Jasperreport;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

/**
 *
 * @author Hendro Steven
 */
public class ReportDaftarPerusahaanByKecamatanWnd extends ApplicationContext {

    Jasperreport jasper;
    //Textbox sql;

    public ReportDaftarPerusahaanByKecamatanWnd() {
    }

    public void onCreate() throws Exception {
        jasper = (Jasperreport) getFellow("jasper");
        //sql = (Textbox) getFellow("sql");
        loadReport();
    }

    public void loadReport() throws Exception {
        Connection conn = getConn();
        String sql = "SELECT perusahaan.id, perusahaan.nama_perusahaan, perusahaan.nama_pimpinan,"
                + "perusahaan.alamat_jalan, kelurahan.nama_kel, kecamatan.nama_kec,"
                + "perusahaan.kota, perusahaan.telp, perusahaan.fax, bidang_usaha.jenis,"
                + "(perusahaan.modal_kerja+perusahaan.modal_tetap) AS investasi, (perusahaan.jml_tki_l+"
                + "perusahaan.jml_tki_p) AS tki, (perusahaan.jml_tka_l+perusahaan.jml_tka_p) AS tka,"
                + "(perusahaan.jml_tki_l+perusahaan.jml_tki_p+perusahaan.jml_tka_l+perusahaan.jml_tka_p) AS total "
                + "FROM perusahaan INNER JOIN "
                + "kecamatan ON kecamatan.id = perusahaan.kecamatan_id INNER JOIN "
                + "kelurahan ON kelurahan.id = perusahaan.kelurahan_id INNER JOIN "
                + "bidang_usaha ON bidang_usaha.id = perusahaan.bidang_usaha_id "
                + "ORDER BY kecamatan.nama_kec,perusahaan.nama_perusahaan";
        try {
            String imgPath = this.getDesktop().getWebApp().getRealPath("/");
            imgPath = imgPath.replace('\\', '/');
            jasper.setSrc("/report/DaftarPerusahaanByKecamatanLandscape.jasper");
            jasper.setType("pdf");
            Map mp = new HashMap();
            mp.put("imagePath", imgPath + "/WEB-INF/classes/report/logo/salatiga-rpt.jpg");
            jasper.setParameters(mp);
            ResultSetTableModel rstm = new ResultSetTableModel(getConn(), sql);
            jasper.setDatasource(new JRTableModelDataSource(rstm));
        } catch (Exception ex) {
            ex.printStackTrace();
            Messagebox.show(ex.getMessage());
        } finally {
            conn.close();
        }
    }
}
