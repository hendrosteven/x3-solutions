/*
 * FakultasDAO.java
 *
 * Created on July 18, 2007, 2:40 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package wii.edu.core.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Session;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import wii.edu.core.model.Fakultas;

/**
 *
 * @author Hendro
 */
public class FakultasDAO {
    
    /** Creates a new instance of FakultasDAO */
    public FakultasDAO() {
    }
    
    public Fakultas getFakultas(long id) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        Fakultas fak = null;
        try{
            fak = (Fakultas)session.load(Fakultas.class,id);
        }catch(Exception ex){
            throw ex;
        }
        return fak;
    }
    
    public Fakultas getFakultas(String kode) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        Fakultas fak = null;
        try{
            fak = (Fakultas)session.createQuery("SELECT fak FROM Fakultas fak WHERE fak.kode= :input")
            .setParameter("input",kode).uniqueResult();
        }catch(Exception ex){
            throw ex;
        }
        return fak;
    }

    public List getAllFakultas() throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("FROM Fakultas WHERE nama != '--'").list();
        }catch(Exception ex){            
            throw ex;
        }
        return list;
    }
    
    public Iterator getAllFakultasDanJumlahMahasiswa() throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        Iterator iter = null;
        try{
            iter = session.createQuery("SELECT fak.kode, fak.nama, COUNT(mhs.id) FROM Mahasiswa mhs right outer join mhs.fakultas fak WHERE mhs.dataKelulusan is null AND fak.id != 1 GROUP BY fak").iterate();
       }catch(Exception ex){
            throw ex;
        }
        return iter;
    }

    /*public List getAllFakultasDanJumlahMahasiswa() throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            //list = session.createQuery("SELECT new Fakultas(fak.kode, fak.nama, COUNT(*)) FROM Fakultas fak full join wii.edu.core.model.Mahasiswa mhs WHERE mhs.dataKelulusan is null GROUP BY fak").list();
            list = session.createQuery("SELECT new Fakultas(fak.kode, fak.nama, COUNT(*)) FROM Mahasiswa mhs right outer join mhs.fakultas fak WHERE mhs.dataKelulusan is null GROUP BY fak").list();
       }catch(Exception ex){
            throw ex;
        }
        return list;
    }*/

    public JSONObject getAllFakultasJSONObject() throws Exception{
        List list = getAllFakultas();

        JSONObject root = new JSONObject();
        JSONArray fakultas = new JSONArray();


        for(int a=0;a<list.size();a++){
            Fakultas fak = (Fakultas)list.get(a);

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", fak.getId() );
            jsonUser.put( "kode", fak.getKode() );
            jsonUser.put( "nama", fak.getNama() );

            fakultas.add( jsonUser );

        }

        root.put( "daftarFakultas", fakultas );

        return root;
    }

     public boolean kodeIsUsed(String kode) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        Fakultas fakultas = null;
        boolean isUsed = false;
        try{
            fakultas = (Fakultas)session.createQuery("SELECT fakultas FROM Fakultas fakultas WHERE fakultas.kode= :input")
            .setParameter("input",kode).uniqueResult();
            if(fakultas!=null){
                isUsed = true;
            }
        }catch(Exception ex){
            throw ex;
        }
        return isUsed;
    }
}
