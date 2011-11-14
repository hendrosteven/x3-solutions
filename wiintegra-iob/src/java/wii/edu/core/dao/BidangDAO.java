/*
 * BidangDAO.java
 *
 * Created on August 14, 2007, 4:32 AM
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
import wii.edu.core.model.BidangStudi;
import wii.edu.core.model.Fakultas;

/**
 *
 * @author Hendro
 */
public class BidangDAO {
    
    /** Creates a new instance of BidangDAO */
    public BidangDAO() {
    }
    
    public BidangStudi getBidangStudi(long id) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        BidangStudi bidang = null;
        try{
            bidang = (BidangStudi)session.load(BidangStudi.class,id);
        }catch(Exception ex){
            throw ex;
        }
        return bidang;
    }
    
    public List getAllBidangStudi() throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("FROM BidangStudi").list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }
    
    public List getAllBidangStudi(Fakultas fak) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("SELECT bidang FROM BidangStudi bidang WHERE bidang.fakultas= :input")
            .setParameter("input",fak).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }
    

    public JSONObject getAllBidangStudiJSONObject() throws Exception{
        List list = getAllBidangStudi();

        JSONObject root = new JSONObject();
        JSONArray bidangStudi = new JSONArray();


        for(int a=0;a<list.size();a++){
            BidangStudi bid = (BidangStudi)list.get(a);
            Fakultas fak = bid.getFakultas();

            JSONObject jsonFak = new JSONObject();
            jsonFak.put( "id", fak.getId() );
            jsonFak.put( "kode", fak.getKode() );
            jsonFak.put( "nama", fak.getNama() );

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", bid.getId() );
            jsonUser.put( "kode", bid.getKode() );
            jsonUser.put( "nama", bid.getNama() );
            jsonUser.put( "fakultas", jsonFak );

            bidangStudi.add( jsonUser );

        }

        root.put( "daftarBidangStudi", bidangStudi );

        return root;
    }

    public boolean kodeIsUsed(String kode) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        BidangStudi bidang = null;
        boolean isUsed = false;
        try{
            bidang = (BidangStudi)session.createQuery("SELECT bidang FROM BidangStudi bidang WHERE bidang.kode= :input")
            .setParameter("input",kode).uniqueResult();
            if(bidang!=null){
                isUsed = true;
            }
        }catch(Exception ex){
            throw ex;
        }
        return isUsed;
    }
}
