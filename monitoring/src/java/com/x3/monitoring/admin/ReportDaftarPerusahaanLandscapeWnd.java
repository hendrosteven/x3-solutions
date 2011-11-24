/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.admin;

import com.x3.monitoring.ApplicationContextWindow;
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
public class ReportDaftarPerusahaanLandscapeWnd extends ApplicationContextWindow {

    Jasperreport jasper;
    Textbox sql;

    public ReportDaftarPerusahaanLandscapeWnd() {
    }

    public void onCreate() throws Exception {
        jasper = (Jasperreport) getFellow("jasper");
        sql = (Textbox) getFellow("sql");
        loadReport();
    }

    public void loadReport() throws Exception {
        Connection conn = getConn();
        try {
            String imgPath = this.getDesktop().getWebApp().getRealPath("/");
            imgPath = imgPath.replace('\\', '/');
            jasper.setSrc("/report/DaftarPerusahaanLandscape.jasper");
            jasper.setType("pdf");
            Map mp = new HashMap();
            mp.put("imagePath", imgPath + "/WEB-INF/classes/report/logo/salatiga-rpt.jpg");
            jasper.setParameters(mp);
            ResultSetTableModel rstm = new ResultSetTableModel(getConn(), sql.getValue());
            jasper.setDatasource(new JRTableModelDataSource(rstm));
        } catch (Exception ex) {
            ex.printStackTrace();
            Messagebox.show(ex.getMessage());
        } finally {
            conn.close();
        }
    }
}

