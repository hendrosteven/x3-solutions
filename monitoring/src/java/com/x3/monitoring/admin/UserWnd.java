/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.x3.monitoring.admin;

import com.x3.monitoring.ApplicationContext;
import com.x3.monitoring.dao.UserDAO;
import com.x3.monitoring.dao.mysql.UserDAOImpl;
import com.x3.monitoring.entity.User;
import java.sql.Connection;
import java.util.List;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Space;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;

/**
 *
 * @author Hendro Steven
 */
public class UserWnd extends ApplicationContext {

    Textbox txtUserName;
    Textbox txtIdUser;
    Textbox txtPassword;
    Textbox txtNama;
    Textbox txtEmail;
    Listbox lstAdmin;

    public UserWnd() {
    }

    public void onCreate()throws Exception {
        txtUserName = (Textbox) getFellow("txtUserName");
        txtIdUser = (Textbox) getFellow("txtIdUser");
        txtPassword = (Textbox) getFellow("txtPassword");
        txtNama = (Textbox) getFellow("txtNama");
        txtEmail = (Textbox) getFellow("txtEmail");
        lstAdmin = (Listbox) getFellow("lstAdmin");
        load();
    }

    private void load() throws Exception {
        Connection conn = getConn();
        try {
            UserDAO dao = new UserDAOImpl(conn);
            List<User> list = dao.gets();
            lstAdmin.getItems().clear();
            int no = 1;
            for (final User user : list) {
                Listitem item = new Listitem();
                item.setValue(user);
                item.appendChild(new Listcell(user.getNama()));
                item.appendChild(new Listcell(user.getEmail()));
                Listcell cellAksi = new Listcell();
                Toolbarbutton btnEdit = new Toolbarbutton();
                Toolbarbutton btnHapus = new Toolbarbutton();
                btnEdit.setId("btnEdit" + no++);
                btnEdit.setImage("/img/edit.png");
                btnEdit.setTooltiptext("Klik untuk mengubah Administrator");
                btnHapus.setId("btnHapus" + no++);
                btnHapus.setImage("/img/delete.png");
                btnHapus.setTooltiptext("Klik untuk mengahapus Administrator");
                btnEdit.addEventListener("onClick", new EventListener() {

                    public void onEvent(Event event) throws Exception {
                        txtIdUser.setValue(user.getId() + "");
                        txtUserName.setValue(user.getUserName());
                        txtPassword.setValue(user.getPassword());
                        txtNama.setValue(user.getNama());
                        txtEmail.setValue(user.getEmail());
                        txtUserName.setFocus(true);
                    }
                });
                btnHapus.addEventListener("onClick", new EventListener() {

                    public void onEvent(Event event) throws Exception {
                        if (Messagebox.show("Hapus?", "Konfirmasi", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION) == Messagebox.YES) {
                            Connection connDel = getConn();
                            try {
                                UserDAO daoDel = new UserDAOImpl(connDel);
                                daoDel.delete(user.getId());
                                load();
                            } catch (Exception ex) {
                                Messagebox.show(ex.getMessage());
                            } finally{
                                connDel.close();
                            }
                        }
                    }
                });
                cellAksi.appendChild(btnEdit);
                cellAksi.appendChild(new Space());
                cellAksi.appendChild(btnHapus);
                item.appendChild(cellAksi);
                lstAdmin.appendChild(item);
            }
        } catch (Exception ex) {
            Messagebox.show(ex.getMessage());
        } finally {
            conn.close();
        }
    }

    public void simpan()throws Exception{
        Connection conn = getConn();
        try{
            if(!txtUserName.getValue().isEmpty() && !txtPassword.getValue().isEmpty()){
                UserDAO dao = new UserDAOImpl(conn);
                if(txtIdUser.getValue().isEmpty()){//insert new
                    User user = new User();
                    user.setUserName(txtUserName.getValue());
                    user.setPassword(txtPassword.getValue());
                    user.setNama(txtNama.getValue());
                    user.setEmail(txtEmail.getValue());
                    user.setLevel(0);
                    dao.insert(user);
                }else{//update
                    User user = dao.get(Integer.valueOf(txtIdUser.getValue()));
                    user.setUserName(txtUserName.getValue());
                    user.setPassword(txtPassword.getValue());
                    user.setNama(txtNama.getValue());
                    user.setEmail(txtEmail.getValue());
                    dao.update(user);
                }
                Messagebox.show("Data tersimpan");
                batal();
                load();
            }else{
                Messagebox.show("Input data dengan lengkap");
            }
        }catch(Exception ex){
            Messagebox.show(ex.getMessage());
        }finally{
            conn.close();
        }
    }

    public void batal()throws Exception{
        txtIdUser.setValue("");
        txtUserName.setValue("");
        txtPassword.setValue("");
        txtNama.setValue("");
        txtEmail.setValue("");
    }
}

