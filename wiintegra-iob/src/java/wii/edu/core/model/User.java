/*
 * User.java
 *
 * Created on July 11, 2007, 2:08 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package wii.edu.core.model;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import org.hibernate.Session;
import wii.edu.core.dao.HibernateUtil;
import wii.edu.core.dao.ObjectDAO;
import wii.edu.core.dao.UserDAO;

/**
 *
 * @author Hendro
 */
public class User implements HttpSessionBindingListener {
    private long id;
    private String username;
    private String password;
    private String realName;
    private int level;
    private Date lastLoginDate;
    private Date lastLoginTime;
    private int isLogin; //false : tidak aktif, true : aktif
   

    public int getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(int isLogin) {
        this.isLogin = isLogin;
    }
    /*private String role;
    private ServletContext context;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ServletContext getContext() {
        return context;
    }

    public void setContext(ServletContext context) {
        this.context = context;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }*/

    /** Creates a new instance of User */
    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

   
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        try {
            System.out.println("keluaaaaaaaaaar");
            User activeUser = (User)event.getValue();
            System.out.println("is login : " + activeUser.getIsLogin());
            activeUser.setIsLogin(0);

            HibernateUtil.beginTransaction();
            Session sesi = HibernateUtil.getSession();
            sesi.clear();
            sesi.update(activeUser);
            HibernateUtil.commitTransaction();
            HibernateUtil.closeSession();
            System.out.println("bisa..");
        } catch (Exception ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
            //HttpSession sesi = event.getSession();
            //HttpSession session = getSesi();
            /*ServletContext context = getContext();
            String role = getRole();
            if(role.equals("admin")){
            List<User> list_admin = (List<User>)context.getAttribute("list_admin");
            //User admin = (User)session.getAttribute("currentUser");
            User admin = getUser();
            System.out.println("nama admin : " + admin.getRealName());
            list_admin = hapusContextAdmin(list_admin, admin);
            context.setAttribute("list_admin", list_admin);
            }
            else if(role.equals("akademik")){
            List<User> list_akademik = (List<User>)context.getAttribute("list_akademik");
            //User akademik = (User)session.getAttribute("currentUser");
            User akademik = getUser();
            list_akademik = hapusContextAkademik(list_akademik, akademik);
            context.setAttribute("list_akademik", list_akademik);
            }
        //HttpSession sesi = event.getSession();
        //HttpSession session = getSesi();
        /*ServletContext context = getContext();

        String role = getRole();

        if(role.equals("admin")){
            List<User> list_admin = (List<User>)context.getAttribute("list_admin");
            //User admin = (User)session.getAttribute("currentUser");
            User admin = getUser();
            System.out.println("nama admin : " + admin.getRealName());
            list_admin = hapusContextAdmin(list_admin, admin);
            context.setAttribute("list_admin", list_admin);
        }
        else if(role.equals("akademik")){
            List<User> list_akademik = (List<User>)context.getAttribute("list_akademik");
            //User akademik = (User)session.getAttribute("currentUser");
            User akademik = getUser();
            list_akademik = hapusContextAkademik(list_akademik, akademik);
            context.setAttribute("list_akademik", list_akademik);
        }*/
    }
    
    public List<User> hapusContextAdmin(List<User> list_admin, User actAdmin){
        System.out.println("size Admin sblm dihapus : " + list_admin.size());
        list_admin.remove(actAdmin);
        System.out.println("size Admin stlh dihapus : " + list_admin.size());
        return list_admin;

        /*for(int a=0;a<list_admin.size();a++){
            User admin = list_admin.get(a);

            if(admin.getUsername().equals(actAdmin.getUsername()) && admin.getPassword().equals(actAdmin.getPassword())){
                list_admin.remove(a);
                //return true;
            }
        }
        System.out.println("size Admin stlh dihapus : " + list_admin.size());
        return list_admin;*/
        //return false;
    }

    public List<User> hapusContextAkademik(List<User> list_akademik, User actAkademik){
        System.out.println("size Akademik sblm dihapus : " + list_akademik.size());
        list_akademik.remove(actAkademik);
        System.out.println("size Akademik stlh dihapus : " + list_akademik.size());
        return list_akademik;
    }

}
