/*
 * SemesterDAO.java
 *
 * Created on July 18, 2007, 10:09 PM
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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import wii.edu.core.model.Semester;

/**
 *
 * @author Hendro
 */
public class SemesterDAO {
    
    /** Creates a new instance of SemesterDAO */
    public SemesterDAO() {
    }
    
    public Semester getSemester(long id) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        Semester sem = null;
        try{
            sem = (Semester)session.load(Semester.class,id);
        }catch(Exception ex){
            throw ex;
        }
        return sem;
    }
    
    public Semester getCurrentSemester() throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        Semester sem = null;
        List list = new ArrayList();
        try{
            list = session.createQuery("SELECT sem FROM Semester sem ORDER BY sem.id DESC").list();
            sem = (Semester)list.get(0);
        }catch(Exception ex){
            throw ex;
        }
        return sem;
    }
    
    public JSONObject getAllSemesterJSONObject() throws Exception{
        List list = getAllSemester();

        JSONObject root = new JSONObject();
        JSONArray semester = new JSONArray();


        for(int a=0;a<list.size();a++){
            Semester sem = (Semester)list.get(a);

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", sem.getId() );
            jsonUser.put( "nama", sem.getNama() );
            jsonUser.put( "tahunAjaran", sem.getTahunAjaran() );
            if(sem.getBatasRegistrasi() == null){
                jsonUser.put( "batas", "");
            }
            else{
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date = format.parse(sem.getBatasRegistrasi());
                SimpleDateFormat format2 = new SimpleDateFormat("MM/dd/yyyy");
                jsonUser.put( "batas", format2.format(date));
            }

            semester.add( jsonUser );

        }

        root.put( "daftarSemester", semester );

        return root;
    }

    public JSONObject getAllSemesterJSONObject(int start, int limit) throws Exception{
        List list = getAllSemester(start, limit);
        List list_all = getAllSemester();

        JSONObject root = new JSONObject();
        JSONArray semester = new JSONArray();


        for(int a=0;a<list.size();a++){
            Semester sem = (Semester)list.get(a);

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", sem.getId() );
            jsonUser.put( "nama", sem.getNama() );
            jsonUser.put( "tahunAjaran", sem.getTahunAjaran() );
            if(sem.getBatasRegistrasi() == null){
                jsonUser.put( "batas", "");
            }
            else{
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date date = format.parse(sem.getBatasRegistrasi());
                SimpleDateFormat format2 = new SimpleDateFormat("MM/dd/yyyy");
                jsonUser.put( "batas", format2.format(date));
            }

            semester.add( jsonUser );

        }

        root.put( "total", list_all.size() );
        root.put( "daftarSemester", semester );

        return root;
    }

    public JSONObject getAllSemesterJSONObjectforCombobox() throws Exception{
        List list = getAllSemester();

        JSONObject root = new JSONObject();
        JSONArray semester = new JSONArray();


        for(int a=0;a<list.size();a++){
            Semester sem = (Semester)list.get(a);

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", sem.getId() );
            jsonUser.put( "nama", sem.getNama() + " " + sem.getTahunAjaran() );

            semester.add( jsonUser );

        }

        root.put( "daftarSemester", semester );

        return root;
    }

    public JSONObject getCurrentSemesterJSONObject() throws Exception{
        Semester currSemester = getCurrentSemester();

        JSONObject root = new JSONObject();
        JSONArray semester = new JSONArray();


            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", currSemester.getId() );
            jsonUser.put( "nama", currSemester.getNama() );
            jsonUser.put( "tahunAjaran", currSemester.getTahunAjaran() );
            jsonUser.put( "batasRegistrasi", currSemester.getBatasRegistrasi() );

            semester.add( jsonUser );

        root.put( "currentSemester", semester );

        return root;
    }

     public Semester getSemesterSebelumnya() throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        Semester sem = null;
        List list = new ArrayList();
        try{
            list = session.createQuery("SELECT sem FROM Semester sem ORDER BY sem.id DESC").list();
            if(list.size()>1){
                sem = (Semester)list.get(1);
            }else{
                sem = (Semester)list.get(0);
            }
        }catch(Exception ex){
            throw ex;
        }
        return sem;
    }
    
    public List getAllSemester() throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("SELECT sem FROM Semester sem ORDER BY sem.id DESC").list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }

    public List getAllSemester(int start, int limit) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("SELECT sem FROM Semester sem ORDER BY sem.id DESC").setFirstResult(start).setMaxResults(limit).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }
}
