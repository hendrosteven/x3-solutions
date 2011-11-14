/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

/**
 *
 * @author Hendro Steven
 */
public class IndexWnd extends Window {

    public void onCreate() throws Exception {
        //Executions.sendRedirect("/zul/operator/index.zul");
       Executions.sendRedirect("home.zul");
    }
}
