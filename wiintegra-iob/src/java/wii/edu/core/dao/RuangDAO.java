/*
 * RuangDAO.java
 *
 * Created on July 18, 2007, 10:03 PM
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
import wii.edu.core.model.Ruang;

/**
 *
 * @author Hendro
 */
public class RuangDAO {
    
    /** Creates a new instance of RuangDAO */
    public RuangDAO() {
    }
    
    public Ruang getRuang(long id) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        Ruang ruang = null;
        try{
            ruang = (Ruang)session.load(Ruang.class,id);
        }catch(Exception ex){
            throw ex;
        }
        return ruang;
    }
    
    public Ruang getRuang(String kode) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        Ruang ruang = null;
        try{
            ruang = (Ruang)session.createQuery("SELECT ruang FROM Ruang ruang WHERE ruang.kode= :input")
            .setParameter("input",kode).uniqueResult();
        }catch(Exception ex){
            throw ex;
        }
        return ruang;
    }
    
    public List getAllRuang() throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("FROM Ruang").list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }

    public JSONObject getAllRuangJSONObject() throws Exception{
        List list = getAllRuang();

        JSONObject root = new JSONObject();
        JSONArray ruang = new JSONArray();


        for(int a=0;a<list.size();a++){
            Ruang rng = (Ruang)list.get(a);

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", rng.getId() );
            jsonUser.put( "kode", rng.getKode() );
            jsonUser.put( "nama", rng.getNama() );
            jsonUser.put( "kapasitas", rng.getKapasitas() );

            ruang.add( jsonUser );

        }

        root.put( "daftarRuang", ruang );

        return root;
    }

     public boolean kodeIsUsed(String kode) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        Ruang ruang = null;
        boolean isUsed = false;
        try{
            ruang = (Ruang)session.createQuery("SELECT ruang FROM Ruang ruang WHERE ruang.kode= :input")
            .setParameter("input",kode).uniqueResult();
            if(ruang!=null){
                isUsed = true;
            }
        }catch(Exception ex){
            throw ex;
        }
        return isUsed;
    }
}
