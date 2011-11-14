/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.x3.monitoring.operator;

import com.x3.monitoring.ApplicationContextWindow;
import com.x3.monitoring.dao.PotensiInvestasiDAO;
import com.x3.monitoring.dao.mysql.PotensiInvestasiDAOImpl;
import com.x3.monitoring.entity.PotensiInvestasi;
import java.sql.Connection;
import org.zkoss.zul.Html;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author Hendro Steven
 */
public class PotensiInvestasiWnd extends ApplicationContextWindow{
Html html;

    public PotensiInvestasiWnd() {
    }

    public void onCreate() throws Exception {
        html = (Html) getFellow("html");
        load();
    }

    private void load() throws Exception {
        Connection conn = getConn();
        try {
            PotensiInvestasiDAO dao = new PotensiInvestasiDAOImpl(conn);
            PotensiInvestasi pi = dao.get();
            html.setContent(pi.getText());
        } catch (Exception ex) {
            Messagebox.show(ex.getMessage());
        } finally {
            conn.close();
        }
    }
}
