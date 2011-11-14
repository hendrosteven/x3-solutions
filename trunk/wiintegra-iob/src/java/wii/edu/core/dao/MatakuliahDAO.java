/*
 * MatakuliahDAO.java
 *
 * Created on July 18, 2007, 9:48 PM
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
import wii.edu.core.model.Fakultas;
import wii.edu.core.model.Matakuliah;
import wii.edu.core.model.ProgramStudi;

/**
 *
 * @author Hendro
 */
public class MatakuliahDAO {
    
    /** Creates a new instance of MatakuliahDAO */
    public MatakuliahDAO() {
    }
    public Matakuliah getMatakuliah(long id) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        Matakuliah mtk = null;
        try{
            mtk = (Matakuliah)session.load(Matakuliah.class,id);
        }catch(Exception ex){
            throw ex;
        }
        return mtk;
    }
    
    public Matakuliah getMatakuliah(String kode) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        Matakuliah mtk = null;
        try{
            mtk = (Matakuliah)session.createQuery("SELECT mtk FROM Matakuliah mtk WHERE mtk.kode= :input")
            .setParameter("input",kode).uniqueResult();
        }catch(Exception ex){
            throw ex;
        }
        return mtk;
    }
    
    public List getAllMatakuliahMinus() throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("FROM Matakuliah WHERE id != 1 order by fakultas.id ASC, progdi.id ASC").list();
            //list.remove(0);
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }

    public List getAllMatakuliahMinus(int start, int limit) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("FROM Matakuliah WHERE id != 1 order by id ASC").setFirstResult(start).setMaxResults(limit).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }

    public List getAllMatakuliah() throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("FROM Matakuliah").list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }
    
    public List getAllMatakuliah(Fakultas fakultas) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("FROM Matakuliah mtk WHERE mtk.fakultas = :input ORDER BY progdi.id ASC")
                    .setParameter("input", fakultas).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }

    public List getAllMatakuliah(Fakultas fakultas, ProgramStudi prog) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("FROM Matakuliah mtk WHERE mtk.fakultas = :input AND mtk.progdi = :input2")
                    .setParameter("input", fakultas).setParameter("input2", prog).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }

    public List getAllMatakuliah(Fakultas fakultas, int start, int limit) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("FROM Matakuliah mtk WHERE mtk.fakultas = :input")
                    .setParameter("input", fakultas).setFirstResult(start).setMaxResults(limit).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }

    public List getAllMatakuliah(Fakultas fakultas, ProgramStudi prog, int start, int limit) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("FROM Matakuliah mtk WHERE mtk.fakultas = :input AND mtk.progdi = :input2")
                    .setParameter("input", fakultas).setParameter("input2", prog).setFirstResult(start).setMaxResults(limit).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }

    public JSONObject getAllMatakuliahMinusJSONObject() throws Exception{
        List list = getAllMatakuliahMinus();

        JSONObject root = new JSONObject();
        JSONArray fakultas = new JSONArray();


        for(int a=0;a<list.size();a++){
            Matakuliah mat = (Matakuliah)list.get(a);

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", mat.getId() );
            jsonUser.put( "kode", mat.getKode() );
            jsonUser.put( "nama", mat.getNama() );
            jsonUser.put( "sksAkademik", mat.getSksAkademik() );
            jsonUser.put( "sksBayar", mat.getSksBayar() );
            if(mat.getPrasyarat()!= null)
                jsonUser.put( "prasyarat", mat.getPrasyarat().getId() );
            else
                jsonUser.put( "prasyarat", 1 );
            if(mat.getFakultas()!= null)
                jsonUser.put( "fakultas", mat.getFakultas().getId() );
            else
                jsonUser.put( "fakultas", "" );
            if(mat.getProgdi()!= null)
                jsonUser.put( "progdi", mat.getProgdi().getId() );
            else
                jsonUser.put( "progdi", "" );

            fakultas.add( jsonUser );

        }

        root.put( "daftarMatakuliah", fakultas );

        return root;
    }

    public JSONObject getAllMatakuliahMinusJSONObject(int start, int limit) throws Exception{
        List list = getAllMatakuliahMinus(start, limit);
        List list_all = getAllMatakuliahMinus();

        JSONObject root = new JSONObject();
        JSONArray fakultas = new JSONArray();


        for(int a=0;a<list.size();a++){
            Matakuliah mat = (Matakuliah)list.get(a);

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", mat.getId() );
            jsonUser.put( "kode", mat.getKode() );
            jsonUser.put( "nama", mat.getNama() );
            jsonUser.put( "sksAkademik", mat.getSksAkademik() );
            jsonUser.put( "sksBayar", mat.getSksBayar() );
            if(mat.getPrasyarat()!= null)
                jsonUser.put( "prasyarat", mat.getPrasyarat().getId() );
            else
                jsonUser.put( "prasyarat", 1 );
            if(mat.getFakultas()!= null)
                jsonUser.put( "fakultas", mat.getFakultas().getId() );
            else
                jsonUser.put( "fakultas", "" );
            if(mat.getProgdi()!= null)
                jsonUser.put( "progdi", mat.getProgdi().getId() );
            else
                jsonUser.put( "progdi", "" );

            fakultas.add( jsonUser );

        }

        root.put("total", list_all.size());
        root.put( "daftarMatakuliah", fakultas );

        return root;
    }

    public JSONObject getAllMatakuliahMinusJSONObject(Fakultas fak) throws Exception{
        List list = getAllMatakuliah(fak);

        JSONObject root = new JSONObject();
        JSONArray fakultas = new JSONArray();


        for(int a=0;a<list.size();a++){
            Matakuliah mat = (Matakuliah)list.get(a);

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", mat.getId() );
            jsonUser.put( "kode", mat.getKode() );
            jsonUser.put( "nama", mat.getNama() );
            jsonUser.put( "sksAkademik", mat.getSksAkademik() );
            jsonUser.put( "sksBayar", mat.getSksBayar() );
            if(mat.getPrasyarat()!= null)
                jsonUser.put( "prasyarat", mat.getPrasyarat().getId() );
            else
                jsonUser.put( "prasyarat", 1 );
            if(mat.getFakultas()!= null)
                jsonUser.put( "fakultas", mat.getFakultas().getId() );
            else
                jsonUser.put( "fakultas", "" );
            if(mat.getProgdi()!= null)
                jsonUser.put( "progdi", mat.getProgdi().getId() );
            else
                jsonUser.put( "progdi", "" );

            fakultas.add( jsonUser );

        }

        root.put( "daftarMatakuliah", fakultas );

        return root;
    }

    public JSONObject getAllMatakuliahMinusJSONObject(Fakultas fak, int start, int limit) throws Exception{
        List list = getAllMatakuliah(fak, start, limit);
        List list_all = getAllMatakuliah(fak);

        JSONObject root = new JSONObject();
        JSONArray fakultas = new JSONArray();


        for(int a=0;a<list.size();a++){
            Matakuliah mat = (Matakuliah)list.get(a);

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", mat.getId() );
            jsonUser.put( "kode", mat.getKode() );
            jsonUser.put( "nama", mat.getNama() );
            jsonUser.put( "sksAkademik", mat.getSksAkademik() );
            jsonUser.put( "sksBayar", mat.getSksBayar() );
            if(mat.getPrasyarat()!= null)
                jsonUser.put( "prasyarat", mat.getPrasyarat().getId() );
            else
                jsonUser.put( "prasyarat", 1 );
            if(mat.getFakultas()!= null)
                jsonUser.put( "fakultas", mat.getFakultas().getId() );
            else
                jsonUser.put( "fakultas", "" );
            if(mat.getProgdi()!= null)
                jsonUser.put( "progdi", mat.getProgdi().getId() );
            else
                jsonUser.put( "progdi", "" );

            fakultas.add( jsonUser );

        }

        root.put("total", list_all.size());
        root.put( "daftarMatakuliah", fakultas );

        return root;
    }

    public JSONObject getAllMatakuliahMinusJSONObject(Fakultas fak, ProgramStudi prog, int start, int limit) throws Exception{
        List list = getAllMatakuliah(fak, prog, start, limit);
        List list_all = getAllMatakuliah(fak, prog);

        JSONObject root = new JSONObject();
        JSONArray fakultas = new JSONArray();


        for(int a=0;a<list.size();a++){
            Matakuliah mat = (Matakuliah)list.get(a);

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", mat.getId() );
            jsonUser.put( "kode", mat.getKode() );
            jsonUser.put( "nama", mat.getNama() );
            jsonUser.put( "sksAkademik", mat.getSksAkademik() );
            jsonUser.put( "sksBayar", mat.getSksBayar() );
            if(mat.getPrasyarat()!= null)
                jsonUser.put( "prasyarat", mat.getPrasyarat().getId() );
            else
                jsonUser.put( "prasyarat", 1 );
            if(mat.getFakultas()!= null)
                jsonUser.put( "fakultas", mat.getFakultas().getId() );
            else
                jsonUser.put( "fakultas", "" );
            if(mat.getProgdi()!= null)
                jsonUser.put( "progdi", mat.getProgdi().getId() );
            else
                jsonUser.put( "progdi", "" );

            fakultas.add( jsonUser );

        }

        root.put("total", list_all.size());
        root.put( "daftarMatakuliah", fakultas );

        return root;
    }

    public JSONObject getAllMatakuliahJSONObject() throws Exception{
        List list = getAllMatakuliah();

        JSONObject root = new JSONObject();
        JSONArray fakultas = new JSONArray();


        for(int a=0;a<list.size();a++){
            Matakuliah mat = (Matakuliah)list.get(a);

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", mat.getId() );
            jsonUser.put( "kode", mat.getKode() );
            jsonUser.put( "nama", mat.getNama() );
            jsonUser.put( "sksAkademik", mat.getSksAkademik() );
            jsonUser.put( "sksBayar", mat.getSksBayar() );
            if(mat.getPrasyarat()!= null)
                jsonUser.put( "prasyarat", mat.getPrasyarat().getId() );
            else
                jsonUser.put( "prasyarat", 1 );
            if(mat.getFakultas()!= null)
                jsonUser.put( "fakultas", mat.getFakultas().getId() );
            else
                jsonUser.put( "fakultas", "" );
            if(mat.getProgdi()!= null)
                jsonUser.put( "progdi", mat.getProgdi().getId() );
            else
                jsonUser.put( "progdi", "" );

            fakultas.add( jsonUser );

        }

        root.put( "daftarMatakuliah", fakultas );

        return root;
    }

    public boolean kodeIsUsed(String kode) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        Matakuliah mtk = null;
        boolean isUsed = false;
        try{
            mtk = (Matakuliah)session.createQuery("SELECT mtk FROM Matakuliah mtk WHERE mtk.kode= :input")
            .setParameter("input",kode).uniqueResult();
            if(mtk!=null){
                isUsed = true;
            }
        }catch(Exception ex){
            throw ex;
        }
        return isUsed;
    }
}
