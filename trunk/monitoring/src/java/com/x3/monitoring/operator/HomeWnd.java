/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.operator;

import com.x3.monitoring.ApplicationContext;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

/**
 *
 * @author Hendro Steven
 */
public class HomeWnd extends ApplicationContext {

    public HomeWnd() {
    }

    public void onCreate() {
    }

    public void selayangPandang() throws Exception {
        try {
            Window win = (Window) Executions.createComponents("/zul/operator/selayang_pandang.zul", null, null);
            win.doModal();
        } catch (Exception ex) {
            Messagebox.show(ex.getMessage());
        }
    }

    public void potensiInvestasi() throws Exception {
        try {
            Window win = (Window) Executions.createComponents("/zul/operator/potensi_investasi.zul", null, null);
            win.doModal();
        } catch (Exception ex) {
            Messagebox.show(ex.getMessage());
        }
    }

    public void petaSalatiga() throws Exception {
        try {
            Window win = (Window) Executions.createComponents("/zul/operator/peta_salatiga.zul", null, null);
            win.doModal();
        } catch (Exception ex) {
            Messagebox.show(ex.getMessage());
        }
    }

    public void dataInvestasi() throws Exception {
        Executions.sendRedirect("/zul/operator/index.zul");
    }
}
