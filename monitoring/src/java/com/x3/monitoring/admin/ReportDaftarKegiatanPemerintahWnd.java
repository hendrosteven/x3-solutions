/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.monitoring.admin;

import com.x3.monitoring.ApplicationContextWindow;
import java.sql.Connection;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import org.zkoss.zkex.zul.Jasperreport;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

/**
 *
 * @author Hendro Steven
 */
public class ReportDaftarKegiatanPemerintahWnd extends  ApplicationContextWindow {
    Jasperreport jasper;
    Textbox sql;

    public ReportDaftarKegiatanPemerintahWnd(){}

    public void onCreate()throws Exception{
        jasper = (Jasperreport)getFellow("jasper");
        sql = (Textbox)getFellow("sql");
        System.out.println(sql.getValue());
        loadReport();
    }

    public void loadReport()throws Exception{
        Connection conn = getConn();
        try{
        jasper.setSrc("/report/DaftarKegiatanPemerintah.jasper");
        jasper.setType("pdf");
        jasper.setParameters(null);
        ResultSetTableModel rstm = new ResultSetTableModel(getConn(), sql.getValue());
        jasper.setDatasource(new JRTableModelDataSource(rstm));
        }catch(Exception ex){
            ex.printStackTrace();
            Messagebox.show(ex.getMessage());
        }finally{
            conn.close();
        }
    }
}
