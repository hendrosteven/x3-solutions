/*
 * ObjectDAO.java
 *
 * Created on July 18, 2007, 2:46 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package wii.edu.core.dao;

import org.hibernate.Session;

/**
 *
 * @author Hendro
 */
public class ObjectDAO {
    
    /** Creates a new instance of ObjectDAO */
    public ObjectDAO() {
    }
    public int insert(Object obj) throws Exception{
        int result=0;
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        try{
            session.save(obj);
            result = 1;
        }catch(Exception ex){
            ex.printStackTrace();
            result = -1;
            return result;
            //throw ex;
        }
        return result;
    }
    
    public int delete(Object obj) throws Exception{
        int result=0;
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        try{
            session.delete(obj);
            result = 1;
        }catch(Exception ex){
            System.out.println("masuk catch objectdao");
            ex.printStackTrace();
            result = -1;
            return result;
            //throw ex;
        }
        return result;
    }
    
    public int update(Object obj) throws Exception{
        int result=0;
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        try{
            session.clear();
            session.update(obj);
            result = 1;
        }catch(Exception ex){
            ex.printStackTrace();
            result = -1;
            return result;
            //throw ex;
        }
        return result;
    }

    public int merge(Object obj) throws Exception{
        int result=0;
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        try{
            session.merge(obj);
            result = 1;
        }catch(Exception ex){
            ex.printStackTrace();
            result = -1;
            return result;
            //throw ex;
        }
        return result;
    }
}
