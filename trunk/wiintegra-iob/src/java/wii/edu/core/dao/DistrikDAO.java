/*
 * DistrikDAO.java
 *
 * Created on July 18, 2007, 9:53 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package wii.edu.core.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import wii.edu.core.model.Distrik;

/**
 *
 * @author Hendro
 */
public class DistrikDAO {
    
    /** Creates a new instance of DistrikDAO */
    public DistrikDAO() {
    }
    public Distrik getDistrik(long id) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        Distrik prov = null;
        try{
            prov = (Distrik)session.load(Distrik.class,id);
        }catch(Exception ex){
            throw ex;
        }
        return prov;
    }
    
    public List getAllDistrik() throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("FROM Distrik WHERE nama != '--'").list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }

    public List getAllDistrik(int start, int limit) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("FROM Distrik WHERE nama != '--'").setFirstResult(start).setMaxResults(limit).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }

    public JSONObject getAllDistrikJSONObject() throws Exception{
        List list = getAllDistrik();

        JSONObject root = new JSONObject();
        JSONArray distrik = new JSONArray();


        for(int a=0;a<list.size();a++){
            Distrik prov = (Distrik)list.get(a);

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", prov.getId() );
            jsonUser.put( "nama", prov.getNama() );

            distrik.add( jsonUser );

        }

        root.put( "daftarDistrik", distrik );

        return root;
    }

    public JSONObject getAllDistrikJSONObject(int start, int limit) throws Exception{
        List list = getAllDistrik(start, limit);
        List list_all = getAllDistrik();

        JSONObject root = new JSONObject();
        JSONArray distrik = new JSONArray();


        for(int a=0;a<list.size();a++){
            Distrik prov = (Distrik)list.get(a);

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", prov.getId() );
            jsonUser.put( "nama", prov.getNama() );

            distrik.add( jsonUser );

        }

        root.put( "total", list_all.size() );
        root.put( "daftarDistrik", distrik );

        return root;
    }
}
