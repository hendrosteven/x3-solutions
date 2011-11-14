/*
 * To change this template, choose Tools | Templates
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
import wii.edu.core.model.Mahasiswa;
import wii.edu.core.model.Pembayaran;
import wii.edu.core.model.ProgramStudi;
import wii.edu.core.model.Semester;

/**
 *
 * @author Retha@wii
 */
public class PembayaranDAO {
    /** Creates a new instance of PembayaranDAO */
    public PembayaranDAO() {
    }


    public Pembayaran getPembayaran(long id) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        Pembayaran pembayaran = null;
        try{
            pembayaran = (Pembayaran)session.load(Pembayaran.class,id);
        }catch(Exception ex){
            throw ex;
        }
        return pembayaran;
    }


    public List getAllPembayaran() throws Exception{
        HibernateUtil.beginTransaction();
        List list = new ArrayList();
        Session session = HibernateUtil.getSession();
        try{
            list = session.createQuery("FROM Pembayaran").list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }

    public List getByMahasiswa(Mahasiswa mhs) throws Exception{
        HibernateUtil.beginTransaction();
        List list = new ArrayList();
        Session session = HibernateUtil.getSession();
        try{
            list = session.createQuery("FROM Pembayaran bayar where bayar.mahasiswa = :input")
                    .setParameter("input", mhs).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }

    public List getAllMahasiswaTerregistrasi(Semester semester) throws Exception{
        HibernateUtil.beginTransaction();
        List list = new ArrayList();
        Session session = HibernateUtil.getSession();
        try{
            list = session.createQuery("Select bayar.mahasiswa FROM Pembayaran bayar where bayar.semester = :semester AND bayar.mahasiswa.dataKelulusan is null order by bayar.mahasiswa")
                    .setParameter("semester", semester).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }

    public List getSemuaMahasiswaTerregistrasi(Semester semester) throws Exception{
        HibernateUtil.beginTransaction();
        List list = new ArrayList();
        Session session = HibernateUtil.getSession();
        try{
            list = session.createQuery("Select bayar.mahasiswa FROM Pembayaran bayar where bayar.semester = :semester")
                    .setParameter("semester", semester).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }

    public List getAllMahasiswaTerregistrasi(Semester semester, int start, int limit) throws Exception{
        HibernateUtil.beginTransaction();
        List list = new ArrayList();
        Session session = HibernateUtil.getSession();
        try{
            list = session.createQuery("FROM Pembayaran bayar where bayar.semester = :semester AND bayar.mahasiswa.dataKelulusan is null order by bayar.mahasiswa")
                    .setFirstResult(start).setMaxResults(limit).setParameter("semester", semester).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }

    public List getAllMahasiswaTerregistrasi(Semester semester, Fakultas fak) throws Exception{
        HibernateUtil.beginTransaction();
        List list = new ArrayList();
        Session session = HibernateUtil.getSession();
        try{
            list = session.createQuery("Select bayar.mahasiswa FROM Pembayaran bayar where bayar.semester = :semester AND bayar.mahasiswa.fakultas = :fakultas AND bayar.mahasiswa.dataKelulusan is null order by bayar.mahasiswa")
                    .setParameter("semester", semester).setParameter("fakultas", fak).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }

    public List getAllMahasiswaTerregistrasi(Semester semester, Fakultas fak, ProgramStudi prog) throws Exception{
        HibernateUtil.beginTransaction();
        List list = new ArrayList();
        Session session = HibernateUtil.getSession();
        try{
            list = session.createQuery("Select bayar.mahasiswa FROM Pembayaran bayar where bayar.semester = :semester AND bayar.mahasiswa.fakultas = :fakultas AND bayar.mahasiswa.progdi = :progdi AND bayar.mahasiswa.dataKelulusan is null order by bayar.mahasiswa")
                    .setParameter("semester", semester).setParameter("fakultas", fak).setParameter("progdi", prog).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }

    public List getAllMahasiswaTerregistrasi(Semester semester, Fakultas fak, int start, int limit) throws Exception{
        HibernateUtil.beginTransaction();
        List list = new ArrayList();
        Session session = HibernateUtil.getSession();
        try{
            list = session.createQuery("FROM Pembayaran bayar where bayar.semester = :semester AND bayar.mahasiswa.fakultas = :fakultas AND bayar.mahasiswa.dataKelulusan is null order by bayar.mahasiswa")
                    .setFirstResult(start).setMaxResults(limit).setParameter("semester", semester).setParameter("fakultas", fak).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }

    public List getAllMahasiswaTerregistrasi(Semester semester, Fakultas fak, ProgramStudi prog, int start, int limit) throws Exception{
        HibernateUtil.beginTransaction();
        List list = new ArrayList();
        Session session = HibernateUtil.getSession();
        try{
            list = session.createQuery("FROM Pembayaran bayar where bayar.semester = :semester AND bayar.mahasiswa.fakultas = :fakultas AND bayar.mahasiswa.progdi = :progdi AND bayar.mahasiswa.dataKelulusan is null order by bayar.mahasiswa")
                    .setFirstResult(start).setMaxResults(limit).setParameter("semester", semester).setParameter("fakultas", fak).setParameter("progdi", prog).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }

    public List getAllMahasiswaTakTerregistrasi(Semester semester) throws Exception{
        HibernateUtil.beginTransaction();
        List<Mahasiswa> list_reg = new ArrayList<Mahasiswa>();
        List<Mahasiswa> list_unreg = new ArrayList<Mahasiswa>();

        Session session = HibernateUtil.getSession();
        try{
            list_reg = session.createQuery("Select bayar.mahasiswa FROM Pembayaran bayar where bayar.semester = :semester AND bayar.mahasiswa.dataKelulusan is null order by bayar.mahasiswa")
                    .setParameter("semester", semester).list();
            if(list_reg.size()>0){
                Long[] idMhs = new Long[list_reg.size()];

                for(int a=0;a<list_reg.size();a++){
                    idMhs[a] = list_reg.get(a).getId();
                }

                list_unreg = session.createQuery("FROM Mahasiswa mhs where mhs NOT IN (:unreg) AND mhs.dataKelulusan is null")
                        .setParameterList("unreg", list_reg).list();
            }
            else{
                list_unreg = session.createQuery("FROM Mahasiswa mhs where mhs.dataKelulusan is null").list();
            }
        }catch(Exception ex){
            throw ex;
        }
        return list_unreg;
    }

    public List getAllMahasiswaTakTerregistrasi(Semester semester, int start, int limit) throws Exception{
        HibernateUtil.beginTransaction();
        List<Mahasiswa> list_reg = new ArrayList<Mahasiswa>();
        List<Mahasiswa> list_unreg = new ArrayList<Mahasiswa>();

        Session session = HibernateUtil.getSession();
        try{
            list_reg = session.createQuery("Select bayar.mahasiswa FROM Pembayaran bayar where bayar.semester = :semester AND bayar.mahasiswa.dataKelulusan is null order by bayar.mahasiswa")
                    .setParameter("semester", semester).list();
            if(list_reg.size()>0){
                Long[] idMhs = new Long[list_reg.size()];

                for(int a=0;a<list_reg.size();a++){
                    idMhs[a] = list_reg.get(a).getId();
                }

                list_unreg = session.createQuery("FROM Mahasiswa mhs where mhs NOT IN (:unreg) AND mhs.dataKelulusan is null")
                        .setFirstResult(start).setMaxResults(limit).setParameterList("unreg", list_reg).list();
            }
            else{
                list_unreg = session.createQuery("FROM Mahasiswa mhs where mhs.dataKelulusan is null")
                        .setFirstResult(start).setMaxResults(limit).list();
            }
        }catch(Exception ex){
            throw ex;
        }
        return list_unreg;
    }

    public List getAllMahasiswaTakTerregistrasi(Semester semester, Fakultas fak) throws Exception{
        HibernateUtil.beginTransaction();
        List<Mahasiswa> list_reg = new ArrayList<Mahasiswa>();
        List<Mahasiswa> list_unreg = new ArrayList<Mahasiswa>();

        Session session = HibernateUtil.getSession();
        try{
            list_reg = session.createQuery("Select bayar.mahasiswa FROM Pembayaran bayar where bayar.semester = :semester order by bayar.mahasiswa")
                    .setParameter("semester", semester).list();
            if(list_reg.size()>0){
                Long[] idMhs = new Long[list_reg.size()];

                for(int a=0;a<list_reg.size();a++){
                    idMhs[a] = list_reg.get(a).getId();
                }

                list_unreg = session.createQuery("FROM Mahasiswa mhs where mhs NOT IN (:unreg) AND mhs.fakultas = :fakultas AND mhs.dataKelulusan is null")
                        .setParameterList("unreg", list_reg).setParameter("fakultas", fak).list();
            }
            else{
                list_unreg = session.createQuery("FROM Mahasiswa mhs where mhs.fakultas = :fakultas AND mhs.dataKelulusan is null")
                        .setParameter("fakultas", fak).list();
            }
        }catch(Exception ex){
            throw ex;
        }
        return list_unreg;
    }

    public List getAllMahasiswaTakTerregistrasi(Semester semester, Fakultas fak, ProgramStudi prog) throws Exception{
        HibernateUtil.beginTransaction();
        List<Mahasiswa> list_reg = new ArrayList<Mahasiswa>();
        List<Mahasiswa> list_unreg = new ArrayList<Mahasiswa>();

        Session session = HibernateUtil.getSession();
        try{
            list_reg = session.createQuery("Select bayar.mahasiswa FROM Pembayaran bayar where bayar.semester = :semester order by bayar.mahasiswa")
                    .setParameter("semester", semester).list();
            if(list_reg.size()>0){
                Long[] idMhs = new Long[list_reg.size()];

                for(int a=0;a<list_reg.size();a++){
                    idMhs[a] = list_reg.get(a).getId();
                }

                list_unreg = session.createQuery("FROM Mahasiswa mhs where mhs NOT IN (:unreg) AND mhs.fakultas = :fakultas AND mhs.progdi = :progdi AND mhs.dataKelulusan is null")
                        .setParameterList("unreg", list_reg).setParameter("fakultas", fak).setParameter("progdi", prog).list();
            }
            else{
                list_unreg = session.createQuery("FROM Mahasiswa mhs where mhs.fakultas = :fakultas AND mhs.progdi = :progdi AND mhs.dataKelulusan is null")
                        .setParameter("fakultas", fak).setParameter("progdi", prog).list();
            }
        }catch(Exception ex){
            throw ex;
        }
        return list_unreg;
    }

    public List getAllMahasiswaTakTerregistrasi(Semester semester, Fakultas fak, int start, int limit) throws Exception{
        HibernateUtil.beginTransaction();
        List<Mahasiswa> list_reg = new ArrayList<Mahasiswa>();
        List<Mahasiswa> list_unreg = new ArrayList<Mahasiswa>();

        Session session = HibernateUtil.getSession();
        try{
            list_reg = session.createQuery("Select bayar.mahasiswa FROM Pembayaran bayar where bayar.semester = :semester order by bayar.mahasiswa")
                    .setParameter("semester", semester).list();
            if(list_reg.size()>0){
                Long[] idMhs = new Long[list_reg.size()];

                for(int a=0;a<list_reg.size();a++){
                    idMhs[a] = list_reg.get(a).getId();
                }

                list_unreg = session.createQuery("FROM Mahasiswa mhs where mhs NOT IN (:unreg) AND mhs.fakultas = :fakultas AND mhs.dataKelulusan is null")
                        .setFirstResult(start).setMaxResults(limit)
                        .setParameterList("unreg", list_reg).setParameter("fakultas", fak).list();
            }
            else{
                list_unreg = session.createQuery("FROM Mahasiswa mhs where mhs.fakultas = :fakultas AND mhs.dataKelulusan is null")
                        .setFirstResult(start).setMaxResults(limit)
                        .setParameter("fakultas", fak).list();
            }
        }catch(Exception ex){
            throw ex;
        }
        return list_unreg;
    }

    public List getAllMahasiswaTakTerregistrasi(Semester semester, Fakultas fak, ProgramStudi prog, int start, int limit) throws Exception{
        HibernateUtil.beginTransaction();
        List<Mahasiswa> list_reg = new ArrayList<Mahasiswa>();
        List<Mahasiswa> list_unreg = new ArrayList<Mahasiswa>();

        Session session = HibernateUtil.getSession();
        try{
            list_reg = session.createQuery("Select bayar.mahasiswa FROM Pembayaran bayar where bayar.semester = :semester order by bayar.mahasiswa")
                    .setParameter("semester", semester).list();
            if(list_reg.size()>0){
                Long[] idMhs = new Long[list_reg.size()];

                for(int a=0;a<list_reg.size();a++){
                    idMhs[a] = list_reg.get(a).getId();
                }

                list_unreg = session.createQuery("FROM Mahasiswa mhs where mhs NOT IN (:unreg) AND mhs.fakultas = :fakultas AND mhs.progdi = :progdi AND mhs.dataKelulusan is null")
                        .setFirstResult(start).setMaxResults(limit)
                        .setParameterList("unreg", list_reg).setParameter("fakultas", fak).setParameter("progdi", prog).list();
            }
            else{
                list_unreg = session.createQuery("FROM Mahasiswa mhs where mhs.fakultas = :fakultas AND mhs.progdi = :progdi AND mhs.dataKelulusan is null")
                        .setFirstResult(start).setMaxResults(limit)
                        .setParameter("fakultas", fak).setParameter("progdi", prog).list();
            }
        }catch(Exception ex){
            throw ex;
        }
        return list_unreg;
    }

    public JSONObject getAllMahasiswaTerregistrasiJSONObject(Semester semester) throws Exception{
        List list = getAllMahasiswaTerregistrasi(semester);

        JSONObject root = new JSONObject();
        JSONArray mahasiswa = new JSONArray();

        System.out.println("size " + list.size());
        for(int a=0;a<list.size();a++){
            Pembayaran bayar = (Pembayaran)list.get(a);
            Mahasiswa mhs = bayar.getMahasiswa();

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", bayar.getId() );
            jsonUser.put( "idMhs", mhs.getId() );
            jsonUser.put( "noInduk", mhs.getNomorInduk() );
            jsonUser.put( "nama", mhs.getNama() );
            jsonUser.put( "fakultas", mhs.getFakultas().getNama() );
            jsonUser.put( "progdi",  mhs.getProgdi().getNama());

            mahasiswa.add( jsonUser );

        }

        root.put( "daftarMahasiswa", mahasiswa );

        return root;
    }

    public JSONObject getAllMahasiswaTerregistrasiJSONObject(Semester semester, int start, int limit) throws Exception{
        List list = getAllMahasiswaTerregistrasi(semester, start, limit);
        List list_all = getAllMahasiswaTerregistrasi(semester);

        JSONObject root = new JSONObject();
        JSONArray mahasiswa = new JSONArray();

        System.out.println("size " + list.size());
        for(int a=0;a<list.size();a++){
            Pembayaran bayar = (Pembayaran)list.get(a);
            Mahasiswa mhs = bayar.getMahasiswa();

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", bayar.getId() );
            jsonUser.put( "idMhs", mhs.getId() );
            jsonUser.put( "noInduk", mhs.getNomorInduk() );
            jsonUser.put( "nama", mhs.getNama() );
            jsonUser.put( "fakultas", mhs.getFakultas().getNama() );
            jsonUser.put( "progdi",  mhs.getProgdi().getNama());

            mahasiswa.add( jsonUser );

        }

        root.put( "total", list_all.size() );
        root.put( "daftarMahasiswa", mahasiswa );

        return root;
    }

    public JSONObject getAllMahasiswaTerregistrasiJSONObject(Semester semester, Fakultas fak) throws Exception{
        List list = getAllMahasiswaTerregistrasi(semester, fak);

        JSONObject root = new JSONObject();
        JSONArray mahasiswa = new JSONArray();

        System.out.println("size " + list.size());
        for(int a=0;a<list.size();a++){
            Pembayaran bayar = (Pembayaran)list.get(a);
            Mahasiswa mhs = bayar.getMahasiswa();

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", bayar.getId() );
            jsonUser.put( "idMhs", mhs.getId() );
            jsonUser.put( "noInduk", mhs.getNomorInduk() );
            jsonUser.put( "nama", mhs.getNama() );
            jsonUser.put( "fakultas", mhs.getFakultas().getNama() );
            jsonUser.put( "progdi",  mhs.getProgdi().getNama());

            mahasiswa.add( jsonUser );

        }

        root.put( "daftarMahasiswa", mahasiswa );

        return root;
    }

    public JSONObject getAllMahasiswaTerregistrasiJSONObject(Semester semester, Fakultas fak, int start, int limit) throws Exception{
        List list = getAllMahasiswaTerregistrasi(semester, fak, start, limit);
        List list_all = getAllMahasiswaTerregistrasi(semester, fak);

        JSONObject root = new JSONObject();
        JSONArray mahasiswa = new JSONArray();

        System.out.println("size " + list.size());
        for(int a=0;a<list.size();a++){
            Pembayaran bayar = (Pembayaran)list.get(a);
            Mahasiswa mhs = bayar.getMahasiswa();

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", bayar.getId() );
            jsonUser.put( "idMhs", mhs.getId() );
            jsonUser.put( "noInduk", mhs.getNomorInduk() );
            jsonUser.put( "nama", mhs.getNama() );
            jsonUser.put( "fakultas", mhs.getFakultas().getNama() );
            jsonUser.put( "progdi",  mhs.getProgdi().getNama());

            mahasiswa.add( jsonUser );

        }

        root.put( "total", list_all.size() );
        root.put( "daftarMahasiswa", mahasiswa );

        return root;
    }

    public JSONObject getAllMahasiswaTerregistrasiJSONObject(Semester semester, Fakultas fak, ProgramStudi prog, int start, int limit) throws Exception{
        List list = getAllMahasiswaTerregistrasi(semester, fak, prog, start, limit);
        List list_all = getAllMahasiswaTerregistrasi(semester, fak, prog);

        JSONObject root = new JSONObject();
        JSONArray mahasiswa = new JSONArray();

        System.out.println("size " + list.size());
        for(int a=0;a<list.size();a++){
            Pembayaran bayar = (Pembayaran)list.get(a);
            Mahasiswa mhs = bayar.getMahasiswa();

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", bayar.getId() );
            jsonUser.put( "idMhs", mhs.getId() );
            jsonUser.put( "noInduk", mhs.getNomorInduk() );
            jsonUser.put( "nama", mhs.getNama() );
            jsonUser.put( "fakultas", mhs.getFakultas().getNama() );
            jsonUser.put( "progdi",  mhs.getProgdi().getNama());

            mahasiswa.add( jsonUser );

        }

        root.put( "total", list_all.size() );
        root.put( "daftarMahasiswa", mahasiswa );

        return root;
    }

    public JSONObject getAllMahasiswaTakTerregistrasiJSONObject(Semester semester) throws Exception{
        List<Mahasiswa> list = getAllMahasiswaTakTerregistrasi(semester);

        JSONObject root = new JSONObject();
        JSONArray mahasiswa = new JSONArray();

        System.out.println("size " + list.size());
        for(int a=0;a<list.size();a++){
            Mahasiswa mhs = list.get(a);

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", mhs.getId() );
            jsonUser.put( "noInduk", mhs.getNomorInduk() );
            jsonUser.put( "nama", mhs.getNama() );
            jsonUser.put( "fakultas", mhs.getFakultas().getNama() );
            jsonUser.put( "progdi",  mhs.getProgdi().getNama());

            mahasiswa.add( jsonUser );

        }

        root.put( "daftarMahasiswa", mahasiswa );

        return root;
    }

    public JSONObject getAllMahasiswaTakTerregistrasiJSONObject(Semester semester, int start, int limit) throws Exception{
        List<Mahasiswa> list = getAllMahasiswaTakTerregistrasi(semester, start, limit);
        List<Mahasiswa> list_all = getAllMahasiswaTakTerregistrasi(semester);

        JSONObject root = new JSONObject();
        JSONArray mahasiswa = new JSONArray();

        System.out.println("size " + list.size());
        for(int a=0;a<list.size();a++){
            Mahasiswa mhs = list.get(a);

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", mhs.getId() );
            jsonUser.put( "noInduk", mhs.getNomorInduk() );
            jsonUser.put( "nama", mhs.getNama() );
            jsonUser.put( "fakultas", mhs.getFakultas().getNama() );
            jsonUser.put( "progdi",  mhs.getProgdi().getNama());

            mahasiswa.add( jsonUser );

        }

        root.put( "total", list_all.size() );
        root.put( "daftarMahasiswa", mahasiswa );

        return root;
    }

    public JSONObject getAllMahasiswaTakTerregistrasiJSONObject(Semester semester, Fakultas fak) throws Exception{
        List<Mahasiswa> list = getAllMahasiswaTakTerregistrasi(semester, fak);

        JSONObject root = new JSONObject();
        JSONArray mahasiswa = new JSONArray();

        System.out.println("size " + list.size());
        for(int a=0;a<list.size();a++){
            Mahasiswa mhs = list.get(a);

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", mhs.getId() );
            jsonUser.put( "noInduk", mhs.getNomorInduk() );
            jsonUser.put( "nama", mhs.getNama() );
            jsonUser.put( "fakultas", mhs.getFakultas().getNama() );
            jsonUser.put( "progdi",  mhs.getProgdi().getNama());

            mahasiswa.add( jsonUser );

        }

        root.put( "daftarMahasiswa", mahasiswa );

        return root;
    }

    public JSONObject getAllMahasiswaTakTerregistrasiJSONObject(Semester semester, Fakultas fak, int start, int limit) throws Exception{
        List<Mahasiswa> list = getAllMahasiswaTakTerregistrasi(semester, fak, start, limit);
        List<Mahasiswa> list_all = getAllMahasiswaTakTerregistrasi(semester, fak);

        JSONObject root = new JSONObject();
        JSONArray mahasiswa = new JSONArray();

        System.out.println("size " + list.size());
        for(int a=0;a<list.size();a++){
            Mahasiswa mhs = list.get(a);

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", mhs.getId() );
            jsonUser.put( "noInduk", mhs.getNomorInduk() );
            jsonUser.put( "nama", mhs.getNama() );
            jsonUser.put( "fakultas", mhs.getFakultas().getNama() );
            jsonUser.put( "progdi",  mhs.getProgdi().getNama());

            mahasiswa.add( jsonUser );

        }

        root.put( "total", list_all.size() );
        root.put( "daftarMahasiswa", mahasiswa );

        return root;
    }

    public JSONObject getAllMahasiswaTakTerregistrasiJSONObject(Semester semester, Fakultas fak, ProgramStudi prog, int start, int limit) throws Exception{
        List<Mahasiswa> list = getAllMahasiswaTakTerregistrasi(semester, fak, prog, start, limit);
        List<Mahasiswa> list_all = getAllMahasiswaTakTerregistrasi(semester, fak, prog);

        JSONObject root = new JSONObject();
        JSONArray mahasiswa = new JSONArray();

        System.out.println("size " + list.size());
        for(int a=0;a<list.size();a++){
            Mahasiswa mhs = list.get(a);

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", mhs.getId() );
            jsonUser.put( "noInduk", mhs.getNomorInduk() );
            jsonUser.put( "nama", mhs.getNama() );
            jsonUser.put( "fakultas", mhs.getFakultas().getNama() );
            jsonUser.put( "progdi",  mhs.getProgdi().getNama());

            mahasiswa.add( jsonUser );

        }

        root.put( "total", list_all.size() );
        root.put( "daftarMahasiswa", mahasiswa );

        return root;
    }

    public boolean isLunas(Mahasiswa mahasiswa, Semester semester) throws Exception{
        HibernateUtil.beginTransaction();
        Pembayaran bayar = null;
        boolean isLunas = false;
        Session session = HibernateUtil.getSession();
        try{
            bayar = (Pembayaran) session.createQuery("FROM Pembayaran bayar where bayar.mahasiswa = :mahasiswa AND bayar.semester = :semester")
                   .setParameter("mahasiswa",mahasiswa).setParameter("semester", semester).uniqueResult();
            if(bayar == null){
                isLunas = false;
            }
            else{
                isLunas = true;
            }
        }catch(Exception ex){
            throw ex;
        }
        return isLunas;
    }

    /*public Iterator countTakTerregistrasi(Semester semester) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        Iterator iter = null;

        try{
            iter = session.createQuery("Select bayar.mahasiswa.jenisKelamin, COUNT(*) FROM Pembayaran bayar inner join Mahasiswa mhs where bayar.semester = :semester AND bayar.mahasiswa.dataKelulusan is null GROUP BY bayar.mahasiswa.jenisKelamin")
                    .setParameter("semester", semester).iterate();
        }catch(Exception ex){
            throw ex;
        }
        return iter;
    }

    public Iterator countTakTerregistrasi(Semester semester, Fakultas fak) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        Iterator iter = null;

        try{
            iter = session.createQuery("Select bayar.mahasiswa.jenisKelamin, COUNT(*) FROM Pembayaran inner join Mahasiswa mhs bayar where bayar.semester = :semester AND bayar.mahasiswa.fakultas = :fak AND bayar.mahasiswa.dataKelulusan is null GROUP BY bayar.mahasiswa.jenisKelamin")
                    .setParameter("semester", semester).setParameter("fak", fak).iterate();
        }catch(Exception ex){
            throw ex;
        }
        return iter;
    }

    public Iterator countTerregistrasi(Semester semester) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        Iterator iter = null;

        try{
            //iter = session.createQuery("Select bayar.mahasiswa.jenisKelamin, COUNT(*) FROM Pembayaran bayar inner join Mahasiswa mhs where bayar.semester = :semester AND bayar.mahasiswa.dataKelulusan is null GROUP BY bayar.mahasiswa.jenisKelamin")
            iter = session.createQuery("Select mhs.jenisKelamin, COUNT(*) FROM Pembayaran bayar inner join wii.edu.core.model.Mahasiswa mhs where bayar.semester = :semester AND mhs.dataKelulusan is null GROUP BY mhs.jenisKelamin")
                    .setParameter("semester", semester).iterate();
        }catch(Exception ex){
            throw ex;
        }
        return iter;
    }

    public Iterator countTerregistrasi(Semester semester, Fakultas fak) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        Iterator iter = null;

        try{
            iter = session.createQuery("Select mhs.jenisKelamin, COUNT(*) FROM Pembayaran bayar inner join wii.edu.core.model.Mahasiswa mhs where bayar.semester = :semester AND mhs.fakultas = :fakultas AND mhs.dataKelulusan is null GROUP BY mhs.jenisKelamin")
                    .setParameter("semester", semester).setParameter("fakultas", fak).iterate();
        }catch(Exception ex){
            throw ex;
        }
        return iter;
    }*/

    public int[] countTakTerregistrasi(Semester semester) throws Exception{
        int[] hasil = new int[2];
        int jum_wanita = 0;
        int jum_laki = 0;
        List list = getAllMahasiswaTakTerregistrasi(semester);
        for(int a=0;a<list.size();a++){
            //Pembayaran bayar = (Pembayaran)list.get(a);
            //Mahasiswa mhs = bayar.getMahasiswa();
            Mahasiswa mhs = (Mahasiswa)list.get(a);
            if(mhs.getJenisKelamin() == 0)
                jum_wanita++;
            else if(mhs.getJenisKelamin() == 1)
                jum_laki++;
        }
        hasil[0] = jum_wanita;
        hasil[1] = jum_laki;
        return hasil;
    }

    public int[] countTakTerregistrasi(Semester semester, Fakultas fakultas) throws Exception{
        int[] hasil = new int[2];
        int jum_wanita = 0;
        int jum_laki = 0;
        List list = getAllMahasiswaTakTerregistrasi(semester, fakultas);
        for(int a=0;a<list.size();a++){
            //Pembayaran bayar = (Pembayaran)list.get(a);
            //Mahasiswa mhs = bayar.getMahasiswa();
            Mahasiswa mhs = (Mahasiswa)list.get(a);
            if(mhs.getJenisKelamin() == 0)
                jum_wanita++;
            else if(mhs.getJenisKelamin() == 1)
                jum_laki++;
        }
        hasil[0] = jum_wanita;
        hasil[1] = jum_laki;
        return hasil;
    }

    public int[] countTerregistrasi(Semester semester) throws Exception{
        int[] hasil = new int[2];
        int jum_wanita = 0;
        int jum_laki = 0;
        List list = getAllMahasiswaTerregistrasi(semester);
        for(int a=0;a<list.size();a++){
            //Pembayaran bayar = (Pembayaran)list.get(a);
            //Mahasiswa mhs = bayar.getMahasiswa();
            Mahasiswa mhs = (Mahasiswa)list.get(a);
            if(mhs.getJenisKelamin() == 0)
                jum_wanita++;
            else if(mhs.getJenisKelamin() == 1)
                jum_laki++;
        }
        hasil[0] = jum_wanita;
        hasil[1] = jum_laki;
        return hasil;
    }

    public int[] countTerregistrasi(Semester semester, Fakultas fakultas) throws Exception{
        int[] hasil = new int[2];
        int jum_wanita = 0;
        int jum_laki = 0;
        List list = getAllMahasiswaTerregistrasi(semester, fakultas);
        for(int a=0;a<list.size();a++){
            //Pembayaran bayar = (Pembayaran)list.get(a);
            //Mahasiswa mhs = bayar.getMahasiswa();
            Mahasiswa mhs = (Mahasiswa)list.get(a);
            if(mhs.getJenisKelamin() == 0)
                jum_wanita++;
            else if(mhs.getJenisKelamin() == 1)
                jum_laki++;
        }
        hasil[0] = jum_wanita;
        hasil[1] = jum_laki;
        return hasil;
    }
}