/*
 * UserDAO.java
 *
 * Created on July 18, 2007, 3:02 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package wii.edu.core.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.annotations.CascadeType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import wii.edu.core.model.Fakultas;
import wii.edu.core.model.User;

/**
 *
 * @author Hendro
 */
public class UserDAO {
    //CascadeType
    /** Creates a new instance of UserDAO */
    public UserDAO() {
    }
    public User getUser(long id) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        User user = null;
        try{
            user = (User)session.load(User.class,id);
        }catch(Exception ex){
            throw ex;
        }
        return user;
    }
    
    public User getUser(String username) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        User user = null;
        try{
            user = (User)session.createQuery("SELECT user FROM User user WHERE user.username= :input")
            .setParameter("input",username).uniqueResult();
        }catch(Exception ex){
            throw ex;
        }
        return user;
    }

    public User login(String username,String password) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        User user = null;
        try{
            //user = (User)session.createQuery("SELECT user FROM User user WHERE user.username= :input AND user.isLogin = 0")
            user = (User)session.createQuery("SELECT user FROM User user WHERE user.username= :input")
            .setParameter("input",username).uniqueResult();
//            if(user!=null){
//                if(!user.getPassword().equals(password)){
//                    user = null;
//                }else{
//                    user.setLastLoginDate(new Date());
//                    user.setLastLoginTime(new Date());
//                    user.setIsLogin(1);
//                    session.update(user);
//                }
//            }
        }catch(Exception ex){
            throw ex;
        }
        return user;
    }
    
    public int logout(User user) throws Exception{
        int result=0;
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        //User user = null;
        try{
            user.setIsLogin(0);
            System.out.println("nama : " + user.getRealName());
            session.update(user);
            result = 1;
        }catch(Exception ex){
            throw ex;
            //return 0;
        }
        return result;
    }

    public List getAllUser() throws Exception{
        HibernateUtil.beginTransaction();
        List list = new ArrayList();
        Session session = HibernateUtil.getSession();
        try{
            list = session.createQuery("FROM User").list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }

    public JSONObject getAllUserJSONObject() throws Exception{
        List list = getAllUser();

        JSONObject root = new JSONObject();
        JSONArray users = new JSONArray();


        for(int a=0;a<list.size();a++){
            User user = (User)list.get(a);

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", user.getId() );
            jsonUser.put( "userName", user.getUsername() );
            jsonUser.put( "password", user.getPassword() );
            jsonUser.put( "realName", user.getRealName() );
            
            /*if(user.getLevel() == 1)
                jsonUser.put( "level", "Administrator" );
            else if(user.getLevel() == 2)
                jsonUser.put( "level", "Akademik" );
            else if(user.getLevel() == 3)
                jsonUser.put( "level", "Keuangan" );*/
            jsonUser.put( "level", user.getLevel() );

            String tanggal = "";
            if(user.getLastLoginDate() != null){
                SimpleDateFormat formatDate = new SimpleDateFormat("d MMMM yyyy");
                tanggal = tanggal + formatDate.format(user.getLastLoginDate());
                if(user.getLastLoginTime() != null){
                    SimpleDateFormat formatTime = new SimpleDateFormat("hh:mm:ss");
                    tanggal = tanggal + " " + formatTime.format(user.getLastLoginTime());
                }
            }
            jsonUser.put( "lastLogin", tanggal );
            jsonUser.put( "isLogin", user.getIsLogin() == 1 ? true : false );

            users.add( jsonUser );

        }

        root.put( "daftarUser", users );

        return root;
    }

    /**
     * Digunakan untuk mengecek apakah username sudah digunakan atau belum.<br>
     * Mengembalikan nilai true jika sudah digunakan dan false sebaliknya.
     */
    public boolean idIsUsed(String username) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        User user = null;
        boolean isUsed = false;
        try{
            user = (User)session.createQuery("SELECT user FROM User user WHERE user.username= :input")
            .setParameter("input",username).uniqueResult();
            if(user!=null){
                isUsed = true;
            }
        }catch(Exception ex){
            throw ex;
        }
        return isUsed;
    }
}
