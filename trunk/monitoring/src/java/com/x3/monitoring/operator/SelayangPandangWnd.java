/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.operator;

import com.x3.monitoring.ApplicationContextWindow;
import com.x3.monitoring.dao.SelayangPandangDAO;
import com.x3.monitoring.dao.mysql.SelayangPandangDAOImpl;
import com.x3.monitoring.entity.SelayangPandang;
import java.sql.Connection;
import org.zkoss.zul.Html;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author Hendro Steven
 */
public class SelayangPandangWnd extends ApplicationContextWindow {

    Html html;

    public SelayangPandangWnd() {
    }

    public void onCreate() throws Exception {
        html = (Html) getFellow("html");
        load();
    }

    private void load() throws Exception {
        Connection conn = getConn();
        try {
            SelayangPandangDAO dao = new SelayangPandangDAOImpl(conn);
            SelayangPandang sp = dao.get();
            html.setContent(sp.getText());
        } catch (Exception ex) {
            Messagebox.show(ex.getMessage());
        } finally {
            conn.close();
        }
    }
}
