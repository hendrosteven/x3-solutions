/*
 * ProgramStudiDAO.java
 *
 * Created on July 20, 2007, 1:57 PM
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
import wii.edu.core.model.ProgramStudi;

/**
 *
 * @author Hendro
 */
public class ProgramStudiDAO {
    
    /** Creates a new instance of ProgramStudiDAO */
    public ProgramStudiDAO() {
    }
    
    public ProgramStudi getProgramStudi(long id) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        ProgramStudi progdi = new ProgramStudi();
        try{
            progdi = (ProgramStudi)session.load(ProgramStudi.class,id);
        }catch(Exception ex){
            throw ex;
        }
        return progdi;
    }
    
    public ProgramStudi getProgramStudi(String kode) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        ProgramStudi progdi = new ProgramStudi();
        try{
            progdi = (ProgramStudi)session.createQuery("SELECT progdi FROM ProgramStudi progdi WHERE progdi.kode= :input")
            .setParameter("input",kode).uniqueResult();
        }catch(Exception ex){
            throw ex;
        }
        return progdi;
    }


    public Iterator getAllProgramStudiDanJumlahMahasiswa() throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        Iterator iter = null;
        try{
            //iter = session.createQuery("SELECT fak.nama, COUNT(mhs.progdi.id), prog.kode, prog.nama, COUNT(mhs.id) FROM Mahasiswa mhs right outer join mhs.progdi prog right outer join mhs.fakultas fak WHERE mhs.dataKelulusan is null AND fak.id != 1 AND prog.id != 1 GROUP BY fak, prog").iterate();
            iter = session.createQuery("SELECT fak.nama, COUNT(mhs.progdi.id), prog.kode, prog.nama, COUNT(mhs.id) FROM Mahasiswa mhs right outer join mhs.progdi prog right outer join mhs.progdi.fakultas fak WHERE mhs.dataKelulusan is null AND fak.id != 1 AND prog.id != 1 GROUP BY fak, prog ORDER BY fak").iterate();
       }catch(Exception ex){
            throw ex;
        }
        return iter;
    }

    public List getAllProgramStudi() throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("FROM ProgramStudi").list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }
    
    public List getAllProgramStudiMinus() throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("FROM ProgramStudi WHERE id != 1").list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }

    public JSONObject getAllProgramStudiMinusJSONObject() throws Exception{
        List list = getAllProgramStudiMinus();

        JSONObject root = new JSONObject();
        JSONArray fakultas = new JSONArray();


        for(int a=0;a<list.size();a++){
            ProgramStudi prog = (ProgramStudi)list.get(a);

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", prog.getId() );
            jsonUser.put( "kode", prog.getKode() );
            jsonUser.put( "nama", prog.getNama() );
            jsonUser.put( "fakultas", prog.getFakultas().getId());

            fakultas.add( jsonUser );

        }

        root.put( "daftarProgdi", fakultas );

        return root;
    }

    public JSONObject getAllProgramStudiJSONObject() throws Exception{
        List list = getAllProgramStudi();

        JSONObject root = new JSONObject();
        JSONArray fakultas = new JSONArray();


        for(int a=0;a<list.size();a++){
            ProgramStudi prog = (ProgramStudi)list.get(a);

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", prog.getId() );
            jsonUser.put( "kode", prog.getKode() );
            jsonUser.put( "nama", prog.getNama() );
            jsonUser.put( "fakultas", prog.getFakultas().getId());

            fakultas.add( jsonUser );

        }

        root.put( "daftarProgdi", fakultas );

        return root;
    }

    public List getAllProgramStudi(Fakultas fak) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("SELECT progdi FROM ProgramStudi progdi WHERE progdi.fakultas= :input")
            .setParameter("input",fak).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }
    
    public List getAllProgramStudiPlus(Fakultas fak) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("SELECT progdi FROM ProgramStudi progdi WHERE progdi.fakultas= :input OR progdi.fakultas.id = 1")
            .setParameter("input",fak).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }

    public JSONObject getAllProgramStudiJSONObject(Fakultas fak) throws Exception{
        List list = getAllProgramStudi(fak);

        JSONObject root = new JSONObject();
        JSONArray fakultas = new JSONArray();


        for(int a=0;a<list.size();a++){
            ProgramStudi prog = (ProgramStudi)list.get(a);

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", prog.getId() );
            jsonUser.put( "kode", prog.getKode() );
            jsonUser.put( "nama", prog.getNama() );
            jsonUser.put( "fakultas", prog.getFakultas().getId());

            fakultas.add( jsonUser );
        }

        root.put( "daftarProgdi", fakultas );

        return root;
    }

    public JSONObject getAllProgramStudiPlusJSONObject(Fakultas fak) throws Exception{
        List list = getAllProgramStudiPlus(fak);

        JSONObject root = new JSONObject();
        JSONArray fakultas = new JSONArray();


        for(int a=0;a<list.size();a++){
            ProgramStudi prog = (ProgramStudi)list.get(a);

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", prog.getId() );
            jsonUser.put( "kode", prog.getKode() );
            jsonUser.put( "nama", prog.getNama() );
            jsonUser.put( "fakultas", prog.getFakultas().getId());

            fakultas.add( jsonUser );
        }

        root.put( "daftarProgdi", fakultas );

        return root;
    }

    public boolean kodeIsUsed(String kode) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        ProgramStudi progdi = null;
        boolean isUsed = false;
        try{
            progdi = (ProgramStudi)session.createQuery("SELECT progdi FROM ProgramStudi progdi WHERE progdi.kode= :input")
            .setParameter("input",kode).uniqueResult();
            if(progdi!=null){
                isUsed = true;
            }
        }catch(Exception ex){
            throw ex;
        }
        return isUsed;
    }
    
}
