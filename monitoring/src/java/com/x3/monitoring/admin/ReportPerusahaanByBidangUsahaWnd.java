/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.admin;

import com.x3.monitoring.ApplicationContext;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import org.zkoss.zkex.zul.Jasperreport;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author Hendro Steven
 */
public class ReportPerusahaanByBidangUsahaWnd extends ApplicationContext {

    Jasperreport jasper;

    public ReportPerusahaanByBidangUsahaWnd() {
    }

    public void onCreate() throws Exception {
        jasper = (Jasperreport) getFellow("jasper");
        //report = (Iframe) getFellow("report");
        loadReport();
    }

    private void loadReport() throws Exception {
        Connection conn = getConn();
        try {
            String sql = "SELECT bidang_usaha.kbli, bidang_usaha.jenis, COUNT(perusahaan.bidang_usaha_id)AS jml "
                    + "FROM monitoring.perusahaan RIGHT JOIN monitoring.bidang_usaha ON (perusahaan.bidang_usaha_id = bidang_usaha.id) "
                    + "GROUP BY bidang_usaha.id ORDER BY bidang_usaha.id";
            jasper.setSrc("/report/PerusahaanByBidangUsaha.jasper");
            jasper.setType("pdf");
            //String imgPath = this.getDesktop().getWebApp().getRealPath("/");
            ServletContext ctx = (ServletContext) this.getDesktop().getWebApp().getNativeContext();
            String imgPath = ctx.getRealPath("/");
            imgPath = imgPath.replace('\\', '/');
            Map mp = new HashMap();
            mp.put("imagePath", imgPath + "/WEB-INF/classes/report/logo/salatiga-rpt.jpg");
            jasper.setParameters(mp);
            jasper.setParameters(mp);
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
