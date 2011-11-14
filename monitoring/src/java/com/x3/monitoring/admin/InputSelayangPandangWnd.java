/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.admin;

import com.x3.monitoring.ApplicationContext;
import com.x3.monitoring.dao.SelayangPandangDAO;
import com.x3.monitoring.dao.mysql.SelayangPandangDAOImpl;
import com.x3.monitoring.entity.SelayangPandang;
import java.sql.Connection;
import org.zkforge.fckez.FCKeditor;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Window;

/**
 *
 * @author Hendro Steven
 */
public class InputSelayangPandangWnd extends ApplicationContext {

    FCKeditor fckSelayangPandang;

    public InputSelayangPandangWnd() {
    }

    public void onCreate() throws Exception {
        fckSelayangPandang = (FCKeditor) getFellow("fckSelayangPandang");
        load();
    }

    private void load() throws Exception {
        Connection conn = getConn();
        try {
            SelayangPandangDAO dao = new SelayangPandangDAOImpl(conn);
            SelayangPandang sp = dao.get();
            if (sp != null) {
                fckSelayangPandang.setValue(sp.getText());
            }
        } catch (Exception ex) {
            Messagebox.show(ex.getMessage());
        } finally {
            conn.close();
        }
    }

    public void simpan() throws Exception {
        Connection conn = getConn();
        try {
            if (!fckSelayangPandang.getValue().isEmpty()) {
                SelayangPandangDAO dao = new SelayangPandangDAOImpl(conn);
                SelayangPandang sp = dao.get();
                if (sp != null) {//update
                    sp.setText(fckSelayangPandang.getValue());
                    dao.update(sp);
                }else{//baru
                    sp = new SelayangPandang();
                    sp.setText(fckSelayangPandang.getValue());
                    dao.insert(sp);
                }
                Messagebox.show("Data tersimpan");
                load();
            } else {
                Messagebox.show("Silahkan Input Data");
            }
        } catch (Exception ex) {
            Messagebox.show(ex.getMessage());
        } finally {
            conn.close();
        }
    }

    public void lihat()throws Exception{
        try {
            Window win = (Window) Executions.createComponents("/zul/operator/selayang_pandang.zul", null, null);
            win.doModal();
        } catch (Exception ex) {
            Messagebox.show(ex.getMessage());
        }
    }
}
