/*
 * KomponenBiayaDAO.java
 *
 * Created on July 18, 2007, 2:56 PM
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
import wii.edu.core.model.KomponenBiaya;

/**
 *
 * @author Hendro
 */
public class KomponenBiayaDAO {
    
    /** Creates a new instance of KomponenBiayaDAO */
    public KomponenBiayaDAO() {
    }
    public KomponenBiaya getKomponenBiaya(long id) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        KomponenBiaya obj = null;
        try{
            obj = (KomponenBiaya)session.load(KomponenBiaya.class,id);
        }catch(Exception ex){
            throw ex;
        }
        return obj;
    }
    public List getAllKomponenBiaya() throws Exception{
        HibernateUtil.beginTransaction();
        List list = new ArrayList();
        Session session = HibernateUtil.getSession();
        try{
            list = session.createQuery("FROM KomponenBiaya").list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }
    
    public JSONObject getAllKomponenBiayaJSONObject() throws Exception{
        List list = getAllKomponenBiaya();

        JSONObject root = new JSONObject();
        JSONArray biaya = new JSONArray();


        for(int a=0;a<list.size();a++){
            KomponenBiaya komp = (KomponenBiaya)list.get(a);

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", komp.getId() );
            jsonUser.put( "kode", komp.getKode() );
            jsonUser.put( "nama", komp.getNama() );
            jsonUser.put( "keterangan", komp.getKeterangan() );
            jsonUser.put( "jumlah", komp.getJumlah() );
            jsonUser.put( "isSks", komp.getIsSKS() );

            biaya.add( jsonUser );

        }

        root.put( "daftarBiaya", biaya );

        return root;
    }
}
