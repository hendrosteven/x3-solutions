/*
 * MahasiswaDAO.java
 *
 * Created on July 18, 2007, 9:41 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package wii.edu.core.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import wii.edu.core.model.Angkatan;
import wii.edu.core.model.Biodata;
import wii.edu.core.model.Distrik;
import wii.edu.core.model.Fakultas;
import wii.edu.core.model.Mahasiswa;
import wii.edu.core.model.ProgramStudi;
import wii.edu.core.model.Semester;

/**
 *
 * @author Hendro
 */
public class MahasiswaDAO {
    
    /** Creates a new instance of MahasiswaDAO */
    public MahasiswaDAO() {
    }
    public Mahasiswa getMahasiswa(long id) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        Mahasiswa mhs = null;
        try{
            mhs = (Mahasiswa)session.load(Mahasiswa.class,id);
        }catch(Exception ex){
            throw ex;
        }
        return mhs;
    }
    
    public Mahasiswa getMahasiswa(String nomorInduk) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        Mahasiswa mhs = null;
        try{
            mhs = (Mahasiswa)session.createQuery("SELECT mhs FROM Mahasiswa mhs WHERE mhs.nomorInduk= :input")
            .setParameter("input",nomorInduk).uniqueResult();
        }catch(Exception ex){
            throw ex;
        }
        return mhs;
    }
    
    public List getAllMahasiswa() throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("FROM Mahasiswa").list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }
    
    public List getAllBiodata(Distrik distrik) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("FROM Biodata WHERE distrik = :input")
                    .setParameter("input", distrik).list();
            System.out.println("list size : " + list.size());
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }

    public List getAllBiodata(Semester semester) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("FROM Biodata WHERE semester = :input")
                    .setParameter("input", semester).list();
            System.out.println("list size : " + list.size());
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }

    public List<Mahasiswa> getAllMahasiswaBelumLulus() throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        //List<Mahasiswa> list = new ArrayList<Mahasiswa>();
        List list = new ArrayList();
        try{
            /*Criteria criteria = session.createCriteria(Mahasiswa.class);
            criteria.add(Expression.isNull("dataKelulusan"));
            list = criteria.list();
            System.out.println("size : " + list.size());*/
            list = session.createQuery("FROM Mahasiswa mhs WHERE mhs.dataKelulusan is null ORDER BY mhs.fakultas.id, mhs.progdi.id").list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }

    public List<Mahasiswa> getAllMahasiswaBelumLulus(int start, int limit) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        //List<Mahasiswa> list = new ArrayList<Mahasiswa>();
        List list = new ArrayList();
        try{
            /*Criteria criteria = session.createCriteria(Mahasiswa.class);
            criteria.add(Expression.isNull("dataKelulusan"));
            list = criteria.list();
            System.out.println("size : " + list.size());*/
            list = session.createQuery("FROM Mahasiswa mhs WHERE mhs.dataKelulusan is null").setFirstResult(start).setMaxResults(limit).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }

    public List<Mahasiswa> getAllMahasiswaLulus() throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        //List<Mahasiswa> list = new ArrayList<Mahasiswa>();
        List list = new ArrayList();
        try{
            /*Criteria criteria = session.createCriteria(Mahasiswa.class);
            criteria.add(Expression.isNull("dataKelulusan"));
            list = criteria.list();
            System.out.println("size : " + list.size());*/
            list = session.createQuery("FROM Mahasiswa mhs WHERE mhs.dataKelulusan is not null").list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }

    public List<Mahasiswa> getAllMahasiswaLulus(int start, int limit) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        //List<Mahasiswa> list = new ArrayList<Mahasiswa>();
        List list = new ArrayList();
        try{
            /*Criteria criteria = session.createCriteria(Mahasiswa.class);
            criteria.add(Expression.isNull("dataKelulusan"));
            list = criteria.list();
            System.out.println("size : " + list.size());*/
            list = session.createQuery("FROM Mahasiswa mhs WHERE mhs.dataKelulusan is not null")
                    .setFirstResult(start).setMaxResults(limit).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }

    public JSONObject getAllMahasiswaJSONObject() throws Exception{
        List list = getAllMahasiswa();

        JSONObject root = new JSONObject();
        JSONArray mahasiswa = new JSONArray();


        for(int a=0;a<list.size();a++){
            Mahasiswa mhs = (Mahasiswa)list.get(a);
            Biodata bio = (Biodata)mhs.getBiodatas().get(mhs.getBiodatas().size()-1);
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", mhs.getId() );
            jsonUser.put( "noInduk", mhs.getNomorInduk() );
            jsonUser.put( "nama", mhs.getNama() );
            jsonUser.put( "password", mhs.getPassword() );
            jsonUser.put( "fakultas", mhs.getFakultas().getId() );
            jsonUser.put( "progdi", mhs.getProgdi().getId() );
            jsonUser.put( "angkatan", mhs.getAngkatan().getId() );
            jsonUser.put( "jenjang", mhs.getJenjang() );
            jsonUser.put( "tempatLahir", bio.getTempatLahir() );
            jsonUser.put( "tanggalLahir", format.format(bio.getTanggalLahir()) );
            jsonUser.put( "jenisKelamin", mhs.getJenisKelamin() );
            jsonUser.put( "jalan", bio.getJalan() );
            //jsonUser.put( "kelurahan", bio.getKelurahan() );
            //jsonUser.put( "kecamatan", bio.getKecamatan() );
            jsonUser.put( "subDistrik", bio.getSubDistrik() );
            jsonUser.put( "distrik", bio.getDistrik().getId() );
            jsonUser.put( "kodepos", bio.getKodePos() );
            jsonUser.put( "telpon", bio.getTelepon() );
            jsonUser.put( "handphone", bio.getHandphone() );
            jsonUser.put( "email", bio.getEmail() );
            jsonUser.put( "namaAyah", mhs.getNamaAyah() );
            jsonUser.put( "namaIbu", mhs.getNamaIbu() );
            jsonUser.put( "alamatOrtu", mhs.getAlamatOrangTua() );
            jsonUser.put( "isLogin", mhs.getIsLogin() == 1 ? true : false );

            mahasiswa.add( jsonUser );
        }

        root.put( "daftarMahasiswa", mahasiswa );

        return root;
    }

    public JSONObject getAllMahasiswaBelumLulusJSONObject() throws Exception{
        List list = getAllMahasiswaBelumLulus();
        //List list = getAllMahasiswa();

        JSONObject root = new JSONObject();
        JSONArray mahasiswa = new JSONArray();


        for(int a=0;a<list.size();a++){
            Mahasiswa mhs = (Mahasiswa)list.get(a);
            Biodata bio = (Biodata)mhs.getBiodatas().get(mhs.getBiodatas().size()-1);
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", mhs.getId() );
            jsonUser.put( "noInduk", mhs.getNomorInduk() );
            jsonUser.put( "nama", mhs.getNama() );
            jsonUser.put( "password", mhs.getPassword() );
            jsonUser.put( "fakultas", mhs.getFakultas().getId() );
            jsonUser.put( "progdi", mhs.getProgdi().getId() );
            jsonUser.put( "angkatan", mhs.getAngkatan().getId() );
            jsonUser.put( "jenjang", mhs.getJenjang() );
            jsonUser.put( "tempatLahir", bio.getTempatLahir() );
            jsonUser.put( "tanggalLahir", format.format(bio.getTanggalLahir()) );
            jsonUser.put( "jenisKelamin", mhs.getJenisKelamin() );
            jsonUser.put( "jalan", bio.getJalan() );
            //jsonUser.put( "kelurahan", bio.getKelurahan() );
            //jsonUser.put( "kecamatan", bio.getKecamatan() );
            jsonUser.put( "subDistrik", bio.getSubDistrik() );
            jsonUser.put( "distrik", bio.getDistrik().getId() );
            jsonUser.put( "kodepos", bio.getKodePos() );
            jsonUser.put( "telpon", bio.getTelepon() );
            jsonUser.put( "handphone", bio.getHandphone() );
            jsonUser.put( "email", bio.getEmail() );
            jsonUser.put( "namaAyah", mhs.getNamaAyah() );
            jsonUser.put( "namaIbu", mhs.getNamaIbu() );
            jsonUser.put( "alamatOrtu", mhs.getAlamatOrangTua() );
            jsonUser.put( "isLogin", mhs.getIsLogin() == 1 ? true : false );

            mahasiswa.add( jsonUser );
        }

        root.put( "daftarMahasiswa", mahasiswa );

        return root;
    }

    public JSONObject getAllMahasiswaBelumLulusJSONObject(int start, int limit) throws Exception{
        List list = getAllMahasiswaBelumLulus(start, limit);
        List list_all = getAllMahasiswaBelumLulus();

        JSONObject root = new JSONObject();
        JSONArray mahasiswa = new JSONArray();


        for(int a=0;a<list.size();a++){
            Mahasiswa mhs = (Mahasiswa)list.get(a);
            Biodata bio = (Biodata)mhs.getBiodatas().get(mhs.getBiodatas().size()-1);
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", mhs.getId() );
            jsonUser.put( "noInduk", mhs.getNomorInduk() );
            jsonUser.put( "nama", mhs.getNama() );
            jsonUser.put( "password", mhs.getPassword() );
            jsonUser.put( "fakultas", mhs.getFakultas().getId() );
            jsonUser.put( "progdi", mhs.getProgdi().getId() );
            jsonUser.put( "angkatan", mhs.getAngkatan().getId() );
            jsonUser.put( "jenjang", mhs.getJenjang() );
            jsonUser.put( "tempatLahir", bio.getTempatLahir() );
            jsonUser.put( "tanggalLahir", format.format(bio.getTanggalLahir()) );
            jsonUser.put( "jenisKelamin", mhs.getJenisKelamin() );
            jsonUser.put( "jalan", bio.getJalan() );
            //jsonUser.put( "kelurahan", bio.getKelurahan() );
            //jsonUser.put( "kecamatan", bio.getKecamatan() );
            jsonUser.put( "subDistrik", bio.getSubDistrik() );
            jsonUser.put( "distrik", bio.getDistrik().getId() );
            jsonUser.put( "kodepos", bio.getKodePos() );
            jsonUser.put( "telpon", bio.getTelepon() );
            jsonUser.put( "handphone", bio.getHandphone() );
            jsonUser.put( "email", bio.getEmail() );
            jsonUser.put( "namaAyah", mhs.getNamaAyah() );
            jsonUser.put( "namaIbu", mhs.getNamaIbu() );
            jsonUser.put( "alamatOrtu", mhs.getAlamatOrangTua() );
            jsonUser.put( "isLogin", mhs.getIsLogin() == 1 ? true : false );

            mahasiswa.add( jsonUser );
        }

        root.put( "total", list_all.size() );
        root.put( "daftarMahasiswa", mahasiswa );

        return root;
    }

    public boolean nomorIndukIsUsed(String nomorInduk) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        Mahasiswa mhs = null;
        boolean isUsed = false;
        try{
            mhs = (Mahasiswa)session.createQuery("SELECT mhs FROM Mahasiswa mhs WHERE mhs.nomorInduk= :input")
            .setParameter("input",nomorInduk).uniqueResult();
            if(mhs!=null){
                isUsed = true;
            }
        }catch(Exception ex){
            throw ex;
        }
        return isUsed;
    }
    
    public List getSemuaMahasiswa(Fakultas fak) throws Exception{
        HibernateUtil.beginTransaction();
        Session session  = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("SELECT mhs FROM Mahasiswa mhs WHERE mhs.fakultas= :input")
            .setParameter("input",fak).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }
    
    public List getAllMahasiswa(Angkatan angkatan) throws Exception{
        HibernateUtil.beginTransaction();
        Session session  = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("SELECT mhs FROM Mahasiswa mhs WHERE mhs.angkatan= :input")
            .setParameter("input",angkatan).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }

    public List getAllMahasiswa(Fakultas fak) throws Exception{
        HibernateUtil.beginTransaction();
        Session session  = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("SELECT mhs FROM Mahasiswa mhs WHERE mhs.fakultas= :input AND mhs.dataKelulusan is null")
            .setParameter("input",fak).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }

    public List getAllMahasiswa(Fakultas fak, int start, int limit) throws Exception{
        HibernateUtil.beginTransaction();
        Session session  = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("SELECT mhs FROM Mahasiswa mhs WHERE mhs.fakultas= :input AND mhs.dataKelulusan is null")
            .setFirstResult(start).setMaxResults(limit).setParameter("input",fak).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }

    public List getAllMahasiswa(Fakultas fak, ProgramStudi prog, int start, int limit) throws Exception{
        HibernateUtil.beginTransaction();
        Session session  = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("SELECT mhs FROM Mahasiswa mhs WHERE mhs.fakultas= :input AND mhs.progdi= :input2 AND mhs.dataKelulusan is null")
            .setFirstResult(start).setMaxResults(limit).setParameter("input",fak).setParameter("input2",prog).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }

    public List getAllMahasiswaLulus(Fakultas fak) throws Exception{
        HibernateUtil.beginTransaction();
        Session session  = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("SELECT mhs FROM Mahasiswa mhs WHERE mhs.fakultas= :input AND mhs.dataKelulusan is not null")
            .setParameter("input",fak).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }

    public List getAllMahasiswaLulus(Fakultas fak, ProgramStudi prog) throws Exception{
        HibernateUtil.beginTransaction();
        Session session  = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("SELECT mhs FROM Mahasiswa mhs WHERE mhs.fakultas= :input AND mhs.progdi= :input2 AND mhs.dataKelulusan is not null")
                .setParameter("input",fak).setParameter("input2",prog).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }

    public List getAllMahasiswaLulus(Fakultas fak, int start, int limit) throws Exception{
        HibernateUtil.beginTransaction();
        Session session  = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("SELECT mhs FROM Mahasiswa mhs WHERE mhs.fakultas= :input AND mhs.dataKelulusan is not null")
                .setFirstResult(start).setMaxResults(limit).setParameter("input",fak).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }

    public List getAllMahasiswaLulus(Fakultas fak, ProgramStudi prog, int start, int limit) throws Exception{
        HibernateUtil.beginTransaction();
        Session session  = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("SELECT mhs FROM Mahasiswa mhs WHERE mhs.fakultas= :input AND mhs.progdi= :input2 AND mhs.dataKelulusan is not null")
                .setFirstResult(start).setMaxResults(limit).setParameter("input",fak).setParameter("input2",prog).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }

    public JSONObject getAllMahasiswaJSONObject(Fakultas fak) throws Exception{
        List list = getAllMahasiswa(fak);

        JSONObject root = new JSONObject();
        JSONArray mahasiswa = new JSONArray();


        for(int a=0;a<list.size();a++){
            Mahasiswa mhs = (Mahasiswa)list.get(a);
            Biodata bio = (Biodata)mhs.getBiodatas().get(mhs.getBiodatas().size()-1);
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", mhs.getId() );
            jsonUser.put( "noInduk", mhs.getNomorInduk() );
            jsonUser.put( "nama", mhs.getNama() );
            jsonUser.put( "password", mhs.getPassword() );
            jsonUser.put( "fakultas", mhs.getFakultas().getId() );
            jsonUser.put( "progdi", mhs.getProgdi().getId() );
            jsonUser.put( "angkatan", mhs.getAngkatan().getId() );
            jsonUser.put( "jenjang", mhs.getJenjang() );
            jsonUser.put( "tempatLahir", bio.getTempatLahir() );
            jsonUser.put( "tanggalLahir", format.format(bio.getTanggalLahir()) );
            jsonUser.put( "jenisKelamin", mhs.getJenisKelamin() );
            jsonUser.put( "jalan", bio.getJalan() );
            //jsonUser.put( "kelurahan", bio.getKelurahan() );
            //jsonUser.put( "kecamatan", bio.getKecamatan() );
            jsonUser.put( "subDistrik", bio.getSubDistrik() );
            jsonUser.put( "distrik", bio.getDistrik().getId() );
            jsonUser.put( "kodepos", bio.getKodePos() );
            jsonUser.put( "telpon", bio.getTelepon() );
            jsonUser.put( "handphone", bio.getHandphone() );
            jsonUser.put( "email", bio.getEmail() );
            jsonUser.put( "namaAyah", mhs.getNamaAyah() );
            jsonUser.put( "namaIbu", mhs.getNamaIbu() );
            jsonUser.put( "alamatOrtu", mhs.getAlamatOrangTua() );
            jsonUser.put( "isLogin", mhs.getIsLogin() == 1 ? true : false );

            mahasiswa.add( jsonUser );

        }

        root.put( "daftarMahasiswa", mahasiswa );

        return root;
    }

    public JSONObject getAllMahasiswaJSONObject(Fakultas fak, int start, int limit) throws Exception{
        List list = getAllMahasiswa(fak, start, limit);
        List list_all = getAllMahasiswa(fak);

        JSONObject root = new JSONObject();
        JSONArray mahasiswa = new JSONArray();


        for(int a=0;a<list.size();a++){
            Mahasiswa mhs = (Mahasiswa)list.get(a);
            Biodata bio = (Biodata)mhs.getBiodatas().get(mhs.getBiodatas().size()-1);
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", mhs.getId() );
            jsonUser.put( "noInduk", mhs.getNomorInduk() );
            jsonUser.put( "nama", mhs.getNama() );
            jsonUser.put( "password", mhs.getPassword() );
            jsonUser.put( "fakultas", mhs.getFakultas().getId() );
            jsonUser.put( "progdi", mhs.getProgdi().getId() );
            jsonUser.put( "angkatan", mhs.getAngkatan().getId() );
            jsonUser.put( "jenjang", mhs.getJenjang() );
            jsonUser.put( "tempatLahir", bio.getTempatLahir() );
            jsonUser.put( "tanggalLahir", format.format(bio.getTanggalLahir()) );
            jsonUser.put( "jenisKelamin", mhs.getJenisKelamin() );
            jsonUser.put( "jalan", bio.getJalan() );
            //jsonUser.put( "kelurahan", bio.getKelurahan() );
            //jsonUser.put( "kecamatan", bio.getKecamatan() );
            jsonUser.put( "subDistrik", bio.getSubDistrik() );
            jsonUser.put( "distrik", bio.getDistrik().getId() );
            jsonUser.put( "kodepos", bio.getKodePos() );
            jsonUser.put( "telpon", bio.getTelepon() );
            jsonUser.put( "handphone", bio.getHandphone() );
            jsonUser.put( "email", bio.getEmail() );
            jsonUser.put( "namaAyah", mhs.getNamaAyah() );
            jsonUser.put( "namaIbu", mhs.getNamaIbu() );
            jsonUser.put( "alamatOrtu", mhs.getAlamatOrangTua() );
            jsonUser.put( "isLogin", mhs.getIsLogin() == 1 ? true : false );

            mahasiswa.add( jsonUser );

        }

        root.put( "total", list_all.size() );
        root.put( "daftarMahasiswa", mahasiswa );

        return root;
    }

    public JSONObject getAllMahasiswaJSONObject(Fakultas fak, ProgramStudi prog, int start, int limit) throws Exception{
        List list = getAllMahasiswa(fak, prog, start, limit);
        List list_all = getAllMahasiswa(fak, prog);

        JSONObject root = new JSONObject();
        JSONArray mahasiswa = new JSONArray();


        for(int a=0;a<list.size();a++){
            Mahasiswa mhs = (Mahasiswa)list.get(a);
            Biodata bio = (Biodata)mhs.getBiodatas().get(mhs.getBiodatas().size()-1);
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", mhs.getId() );
            jsonUser.put( "noInduk", mhs.getNomorInduk() );
            jsonUser.put( "nama", mhs.getNama() );
            jsonUser.put( "password", mhs.getPassword() );
            jsonUser.put( "fakultas", mhs.getFakultas().getId() );
            jsonUser.put( "progdi", mhs.getProgdi().getId() );
            jsonUser.put( "angkatan", mhs.getAngkatan().getId() );
            jsonUser.put( "jenjang", mhs.getJenjang() );
            jsonUser.put( "tempatLahir", bio.getTempatLahir() );
            jsonUser.put( "tanggalLahir", format.format(bio.getTanggalLahir()) );
            jsonUser.put( "jenisKelamin", mhs.getJenisKelamin() );
            jsonUser.put( "jalan", bio.getJalan() );
            //jsonUser.put( "kelurahan", bio.getKelurahan() );
            //jsonUser.put( "kecamatan", bio.getKecamatan() );
            jsonUser.put( "subDistrik", bio.getSubDistrik() );
            jsonUser.put( "distrik", bio.getDistrik().getId() );
            jsonUser.put( "kodepos", bio.getKodePos() );
            jsonUser.put( "telpon", bio.getTelepon() );
            jsonUser.put( "handphone", bio.getHandphone() );
            jsonUser.put( "email", bio.getEmail() );
            jsonUser.put( "namaAyah", mhs.getNamaAyah() );
            jsonUser.put( "namaIbu", mhs.getNamaIbu() );
            jsonUser.put( "alamatOrtu", mhs.getAlamatOrangTua() );
            jsonUser.put( "isLogin", mhs.getIsLogin() == 1 ? true : false );

            mahasiswa.add( jsonUser );

        }

        root.put( "total", list_all.size() );
        root.put( "daftarMahasiswa", mahasiswa );

        return root;
    }

    public List getAllMahasiswa(ProgramStudi progdi) throws Exception{
        HibernateUtil.beginTransaction();
        Session session  = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("SELECT mhs FROM Mahasiswa mhs WHERE mhs.progdi= :input")
            .setParameter("input",progdi).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }

    public List getAllMahasiswa(Fakultas fak,ProgramStudi progdi) throws Exception{
        HibernateUtil.beginTransaction();
        Session session  = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("SELECT mhs FROM Mahasiswa mhs WHERE mhs.fakultas= :input AND mhs.progdi= :input2 AND mhs.dataKelulusan is null")
            .setParameter("input",fak).setParameter("input2",progdi).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }
    
    public Mahasiswa login(String username,String password) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        Mahasiswa mhs = null;
        try{
            //mhs = (Mahasiswa)session.createQuery("SELECT mhs FROM Mahasiswa mhs WHERE mhs.nomorInduk= :input AND mhs.dataKelulusan is null AND mhs.isLogin=0")
            mhs = (Mahasiswa)session.createQuery("SELECT mhs FROM Mahasiswa mhs WHERE mhs.nomorInduk= :input")
            .setParameter("input",username).uniqueResult();
//            if(mhs!=null){
//                if(!mhs.getPassword().equals(password)){
//                    mhs = null;
//                }else{
//                    mhs.setIsLogin(1);
//                    session.update(mhs);
//                }
//            }
        }catch(Exception ex){
            throw ex;
        }
        return mhs;
    }

    public int logout(Mahasiswa mhs) throws Exception{
        int result=0;
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        //User user = null;
        try{
            mhs.setIsLogin(0);
            session.update(mhs);
            result = 1;
        }catch(Exception ex){
            throw ex;
            //return 0;
        }
        return result;
    }

    public Iterator countBelumLulus() throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        Iterator iter = null;
        try{
            //iter = session.createQuery("SELECT jenisKelamin, COUNT(*) FROM Biodata bio inner join Mahasiswa mhs WHERE mhs.dataKelulusan is null GROUP BY bio.jenisKelamin").iterate();
            iter = session.createQuery("SELECT jenisKelamin, COUNT(*) FROM Mahasiswa WHERE dataKelulusan is null GROUP BY jenisKelamin").iterate();
        }catch(Exception ex){
            throw ex;
        }
        return iter;
    }

    public Iterator countBelumLulus(Fakultas fak) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        Iterator iter = null;
        try{
            //iter = session.createQuery("SELECT jenisKelamin, COUNT(*) FROM Biodata bio inner join Mahasiswa mhs WHERE mhs.dataKelulusan is null GROUP BY bio.jenisKelamin").iterate();
            iter = session.createQuery("SELECT jenisKelamin, COUNT(*) FROM Mahasiswa WHERE fakultas = :input AND dataKelulusan is null GROUP BY jenisKelamin")
                    .setParameter("input", fak).iterate();
        }catch(Exception ex){
            throw ex;
        }
        return iter;
    }
}
