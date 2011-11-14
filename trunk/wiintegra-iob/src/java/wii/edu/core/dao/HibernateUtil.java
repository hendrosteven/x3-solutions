/*
 * HibernateUtil.java
 *
 * Created on April 23, 2007, 10:14 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package wii.edu.core.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Hendro
 */
public class HibernateUtil {
    private static final SessionFactory sessionFactory;
    private static final ThreadLocal threadSession = new ThreadLocal();
    private static final ThreadLocal threadTransaction = new ThreadLocal();
    static{
        try{
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }catch(Throwable ex){
            System.err.println("Initial SessionFactory failed. "+ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    public static Session getSession(){
        Session s = (Session)threadSession.get();
        try{
            if(s==null){
                s = sessionFactory.openSession();
                threadSession.set(s);
            }
        }catch(HibernateException ex){
            throw ex;
        }
        return s;
    }
    
    public static void closeSession(){
        try{
            Session s = (Session)threadSession.get();
            threadSession.set(null);
            if(s!=null && s.isOpen())
                s.close();
        }catch(HibernateException ex){
            throw ex;
        }
    }
    
    public static void beginTransaction(){
        Transaction tx = (Transaction) threadTransaction.get();
        try {
            if (tx == null) {
                tx = getSession().beginTransaction();
                threadTransaction.set(tx);
            }
        } catch (HibernateException ex) {
            throw ex;
        }
    }
    
    public static void commitTransaction() {
        Transaction tx = (Transaction) threadTransaction.get();
        try {
            if ( tx != null && !tx.wasCommitted() && !tx.wasRolledBack() )
                tx.commit();
            threadTransaction.set(null);
        } catch (HibernateException ex) {
            rollbackTransaction();
            throw ex;
        }
    }
    
    public static void rollbackTransaction(){
        Transaction tx = (Transaction)threadTransaction.get();
        try{
            threadTransaction.set(null);
            if(tx!=null && !tx.wasCommitted() && !tx.wasRolledBack()){
                tx.rollback();
            }
        }catch(HibernateException ex){
            throw ex;
        }finally{
            closeSession();
        }
    }
    
}
