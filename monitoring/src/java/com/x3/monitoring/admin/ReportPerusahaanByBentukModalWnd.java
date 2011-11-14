/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.admin;

import com.x3.monitoring.ApplicationContext;
import java.sql.Connection;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import org.zkoss.zkex.zul.Jasperreport;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author Hendro Steven
 */
public class ReportPerusahaanByBentukModalWnd extends ApplicationContext {

    Jasperreport jasper;

    public ReportPerusahaanByBentukModalWnd() {
    }

    public void onCreate() throws Exception {
        jasper = (Jasperreport) getFellow("jasper");
        //report = (Iframe) getFellow("report");
        loadReport();
    }

    private void loadReport() throws Exception {
        Connection conn = getConn();
        try {
            String sql = "SELECT bentuk_modal.nama, COUNT(perusahaan.bentuk_modal_id) AS jml "
                    + "FROM monitoring.perusahaan RIGHT JOIN monitoring.bentuk_modal "
                    + "ON (perusahaan.bentuk_modal_id = bentuk_modal.id) GROUP BY bentuk_modal.id "
                    + "ORDER BY jml";
            jasper.setSrc("/report/PerusahaanByBentukModal.jasper");
            jasper.setType("pdf");
            jasper.setParameters(null);
            ResultSetTableModel rstm = new ResultSetTableModel(getConn(), sql);
            jasper.setDatasource(new JRTableModelDataSource(rstm));

//        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("D:/PerusahaanByKecamatan.jasper");
//        final byte[] buf = JasperRunManager.runReportToPdf(is, null, conn);
//        final InputStream mediais = new ByteArrayInputStream(buf);
//        final AMedia amedia = new AMedia("PerusahaanByKecamatan.pdf", "pdf", "application/pdf", mediais);
//        //set iframe content
//        report.setContent(amedia);
        } catch (Exception ex) {
            ex.printStackTrace();
            Messagebox.show(ex.getMessage());
        } finally {
            conn.close();
        }
    }
}
