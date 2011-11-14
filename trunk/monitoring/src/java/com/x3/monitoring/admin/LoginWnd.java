/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.admin;

import com.x3.monitoring.ApplicationContextWindow;
import com.x3.monitoring.dao.UserDAO;
import com.x3.monitoring.dao.mysql.UserDAOImpl;
import com.x3.monitoring.entity.User;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;

/**
 *
 * @author Hendro Steven
 */
public class LoginWnd extends ApplicationContextWindow {

    private Textbox txtboxUserName;
    private Textbox txtboxPassword;

    public LoginWnd() {
    }

    public void onCreate() throws Exception {
        txtboxUserName = (Textbox) getFellow("txtboxUserName");
        txtboxPassword = (Textbox) getFellow("txtboxPassword");
    }

    public void btnLoginOnClick() throws Exception {
        if (!txtboxUserName.getValue().isEmpty()) {
            UserDAO dao = new UserDAOImpl(getConn());
            User user = dao.login(txtboxUserName.getValue(), txtboxPassword.getValue());
            if (user != null) {
                Session session = Sessions.getCurrent();
                session.setAttribute("CURRENT_USER", user);
                Executions.sendRedirect("/zul/admin/index.zul");
            } else {
                Messagebox.show("User Name/Password salah atau Account Anda belum aktif", "Pesan Informasi", Messagebox.OK, Messagebox.INFORMATION);
                return;
            }
        } else {
            Messagebox.show("User Name atau Password salah", "Pesan Informasi", Messagebox.OK, Messagebox.INFORMATION);
            return;
        }
    }

    public void btnBatalOnClick() throws Exception {
         Executions.sendRedirect("/zul/operator/index.zul"); 
    }
}
