/*
 * RegistrasiMatakuliahDAO.java
 *
 * Created on July 18, 2007, 9:58 PM
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
import wii.edu.core.model.Dosen;
import wii.edu.core.model.Jadwal;
import wii.edu.core.model.Mahasiswa;
import wii.edu.core.model.Matakuliah;
import wii.edu.core.model.RegistrasiMatakuliah;
import wii.edu.core.model.Semester;

/**
 *
 * @author Hendro
 */
public class RegistrasiMatakuliahDAO {
    private JSONObject dataTotal;
    
    /** Creates a new instance of RegistrasiMatakuliahDAO */
    public RegistrasiMatakuliahDAO() {
    }
    
    public RegistrasiMatakuliah getRegistrasiMatakuliah(long id) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        RegistrasiMatakuliah reg = null;
        try{
            reg = (RegistrasiMatakuliah)session.load(RegistrasiMatakuliah.class,id);
        }catch(Exception ex){
            throw ex;
        }
        return reg;
    }
    
    public List getRegistrasiMatakuliah(Jadwal jadwal) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("SELECT reg FROM RegistrasiMatakuliah reg WHERE reg.jadwal= :input")
            .setParameter("input",jadwal).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }
    
    public List getRegistrasiMatakuliah(Matakuliah mtk) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("SELECT reg FROM RegistrasiMatakuliah reg WHERE reg.matakuliah= :input")
            .setParameter("input",mtk).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }

    /*public List getDaftarNilai(Jadwal jadwal) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("SELECT reg.mahasiswa, status, nilai FROM RegistrasiMatakuliah reg WHERE reg.jadwal= :input")
            .setParameter("input",jadwal).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }*/

    public List getRegistrasiMatakuliah(Jadwal jadwal, int start, int limit) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("SELECT reg FROM RegistrasiMatakuliah reg WHERE reg.jadwal= :input")
                .setFirstResult(start).setMaxResults(limit).setParameter("input",jadwal).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }

    public JSONObject getRegistrasiMatakuliahJSONObject(Jadwal jadwal) throws Exception{
        List list = getRegistrasiMatakuliah(jadwal);

        JSONObject root = new JSONObject();
        JSONArray fakultas = new JSONArray();


        for(int a=0;a<list.size();a++){
            RegistrasiMatakuliah reg = (RegistrasiMatakuliah)list.get(a);

            Mahasiswa mhs = reg.getMahasiswa();

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", reg.getId() );
            jsonUser.put( "noInduk", mhs.getNomorInduk() );
            jsonUser.put( "nama", mhs.getNama() );
            jsonUser.put( "status", reg.getStatus() );
            if(reg.getNilai() == null)
                jsonUser.put( "nilai", "" );
            else
                jsonUser.put( "nilai", reg.getNilai() );

            fakultas.add( jsonUser );

        }

        root.put( "daftarMahasiswa", fakultas );

        return root;
    }

    public JSONObject getRegistrasiMatakuliahJSONObject(Jadwal jadwal, int start, int limit) throws Exception{
        List list = getRegistrasiMatakuliah(jadwal, start, limit);
        List list_all = getRegistrasiMatakuliah(jadwal);

        JSONObject root = new JSONObject();
        JSONArray fakultas = new JSONArray();


        for(int a=0;a<list.size();a++){
            RegistrasiMatakuliah reg = (RegistrasiMatakuliah)list.get(a);

            Mahasiswa mhs = reg.getMahasiswa();

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", reg.getId() );
            jsonUser.put( "noInduk", mhs.getNomorInduk() );
            jsonUser.put( "nama", mhs.getNama() );
            jsonUser.put( "status", reg.getStatus() );
            if(reg.getNilai() == null)
                jsonUser.put( "nilai", "" );
            else
                jsonUser.put( "nilai", reg.getNilai() );

            fakultas.add( jsonUser );

        }

        root.put( "total", list_all.size() );
        root.put( "daftarMahasiswa", fakultas );

        return root;
    }

    public List getMatakuliahMahasiswa(Mahasiswa mhs, Semester semester) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("SELECT regMtk FROM RegistrasiMatakuliah regMtk WHERE regMtk.mahasiswa= :input1 AND regMtk.jadwal.semester= :input2")
            .setParameter("input1",mhs).setParameter("input2",semester).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }
    
    public JSONObject getMatakuliahMahasiswaJSONObject(Mahasiswa mhs, Semester semester) throws Exception{
        List list = getMatakuliahMahasiswa(mhs, semester);

        double totSksAkademik = 0;
        double totSksBayar = 0;
        double totAngka = 0;
        //double totSksAmbil = 0;
        double ipk = 0;

        JSONObject root = new JSONObject();
        JSONArray matkul = new JSONArray();


        for(int a=0;a<list.size();a++){
            RegistrasiMatakuliah reg = (RegistrasiMatakuliah)list.get(a);

            JSONObject json = new JSONObject();
            json.put( "id", reg.getId() );
            json.put( "kode", reg.getMatakuliah().getKode() + " " + reg.getJadwal().getAksara() );
            json.put( "nama", reg.getMatakuliah().getNama() + " " + reg.getJadwal().getAksara() );
            json.put( "status", reg.getStatus() );
            json.put( "sksAkademik", reg.getMatakuliah().getSksAkademik() );
            json.put( "sksBayar", reg.getMatakuliah().getSksBayar() );

            int angka = 0;
            if(reg.getNilai() == null){
                json.put( "nilai", "" );
                json.put("angka", "");
            }
            else{
                if(reg.getNilai().equals("A"))
                    angka = 4 * reg.getMatakuliah().getSksAkademik();
                else if(reg.getNilai().equals("B"))
                    angka = 3 * reg.getMatakuliah().getSksAkademik();
                else if(reg.getNilai().equals("C"))
                    angka = 2 * reg.getMatakuliah().getSksAkademik();
                else if(reg.getNilai().equals("D"))
                    angka = 1 * reg.getMatakuliah().getSksAkademik();
                else if(reg.getNilai().equals("E"))
                    angka = 0 * reg.getMatakuliah().getSksAkademik();
                else if(reg.getNilai().equals("T"))
                    angka = 0 * reg.getMatakuliah().getSksAkademik();

                json.put( "nilai", reg.getNilai() );
                json.put("angka", angka);
            }

            totSksAkademik = totSksAkademik + reg.getMatakuliah().getSksAkademik();
            totSksBayar = totSksBayar + reg.getMatakuliah().getSksBayar();

            totAngka = totAngka + angka;
            
            matkul.add( json );
        }

        root.put( "daftarMatkul", matkul );

        JSONObject total = new JSONObject();
        total.put("totSksAkademik", totSksAkademik);
        total.put("totSksBayar", totSksBayar);
        total.put("totAngka", totAngka);

        double IPK=new MahasiswaIP().getIPK(mhs);
        double IPS=new MahasiswaIP().getIPS(mhs,new SemesterDAO().getSemesterSebelumnya());
        int maxBeban=new MahasiswaIP().getMaxBeban(IPK,IPS);
        total.put("maxBeban", maxBeban);
        total.put("ips", IPS);
        total.put("ipk", IPK);
        /*if(totSksAkademik > 0)
            total.put("ipk", totAngka / totSksAkademik);
        else
            total.put("ipk", 0);*/

        setDataTotal(total);

        return root;
    }

    public double getTotalSKSBayar(Mahasiswa mhs,Semester semester){
        double hasil=0;
        try {
            List list = getMatakuliahMahasiswa(mhs,semester);
            for(int x=0;x<list.size();x++){
                RegistrasiMatakuliah regMtk = (RegistrasiMatakuliah)list.get(x);
                hasil +=regMtk.getMatakuliah().getSksBayar();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return hasil;
    }
    
    public List getAllMatakuliahMahasiswa(Mahasiswa mhs) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("SELECT regMtk FROM RegistrasiMatakuliah regMtk WHERE regMtk.mahasiswa= :input1")
            .setParameter("input1",mhs).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }
    
    public JSONObject getAllMatakuliahMahasiswaJSONObject(Mahasiswa mahasiswa) throws Exception{
        List list = getAllMatakuliahMahasiswa(mahasiswa);

        JSONObject root = new JSONObject();
        JSONArray fakultas = new JSONArray();


        for(int a=0;a<list.size();a++){
            RegistrasiMatakuliah reg = (RegistrasiMatakuliah)list.get(a);

            Jadwal jad = reg.getJadwal();

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", reg.getId() );
            jsonUser.put( "kode", jad.getMatakuliah().getKode() + " " + jad.getAksara() );
            jsonUser.put( "nama", jad.getMatakuliah().getNama() + " " + jad.getAksara() );
            jsonUser.put( "status", reg.getStatus() );
            if(reg.getNilai() == null)
                jsonUser.put( "nilai", "" );
            else
                jsonUser.put( "nilai", reg.getNilai() );

            fakultas.add( jsonUser );

        }

        root.put( "daftarNilai", fakultas );

        return root;
    }

    public JSONObject getAllMatakuliahMahasiswaSisaJSONObject(Mahasiswa mahasiswa) throws Exception{
        List<RegistrasiMatakuliah> listregmat=new RegistrasiMatakuliahDAO().getAllMatakuliahMahasiswa(mahasiswa);
        List<Matakuliah> listmatakuliah=new MatakuliahDAO().getAllMatakuliah();
        if(listregmat.size()>0) {
            for(int i=0;i<listregmat.size();i++) {
                for(int j=0;j<listmatakuliah.size();j++) {
                    if(listregmat.get(i).getMatakuliah().getId()==listmatakuliah.get(j).getId()) {
                        listmatakuliah.remove(j);
                    }
                }
            }
        }

        JSONObject root = new JSONObject();
        JSONArray matkul = new JSONArray();

        for(int a=0;a<listmatakuliah.size();a++){
            Matakuliah mat = (Matakuliah)listmatakuliah.get(a);

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", mat.getId() );
            jsonUser.put( "nama", mat.getNama() );

            matkul.add( jsonUser );

        }

        root.put( "daftarMatkul", matkul );

        return root;
    }

    public List getAllMatakuliahIPK(Mahasiswa mhs,Semester semester) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("SELECT regMtk FROM RegistrasiMatakuliah regMtk WHERE regMtk.mahasiswa= :input1 AND regMtk.jadwal.semester!= :input2")
            .setParameter("input1",mhs).setParameter("input2",semester).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }
    
    public JSONObject getAllMatakuliahIPKJSONObject(Mahasiswa mhs, Semester semester) throws Exception{
        List<RegistrasiMatakuliah> listregmat = getAllMatakuliahIPK(mhs, semester);

        double totSksAkademik = 0;
        double totSksBayar = 0;
        double totAngka = 0;
        //double totSksAmbil = 0;
        double ipk = 0;

        JSONObject root = new JSONObject();
        JSONArray matkul = new JSONArray();


        //for(int a=0;a<list.size();a++){
        for(int i=0;i<listregmat.size();i++) {
            double BobotNilai=0;
            if(listregmat.get(i).getNilai()!=null) {
                if(listregmat.get(i).getNilai().equals("A")) {
                    BobotNilai=4;
                } else if(listregmat.get(i).getNilai().equals("B")) {
                    BobotNilai=3;
                } else if(listregmat.get(i).getNilai().equals("C")) {
                    BobotNilai=2;
                } else if(listregmat.get(i).getNilai().equals("D")) {
                    BobotNilai=1;
                }
                for(int j=i-1;j>=0;j--) {
                    if(j<0) {
                        break;
                    }
                    if(listregmat.get(i).getMatakuliah().getId()==listregmat.get(j).getMatakuliah().getId()) {
                        double oldBobotNilai=0;
                        if(listregmat.get(j).getNilai()!=null) {
                            if(listregmat.get(j).getNilai().equals("A")) {
                                oldBobotNilai=4;
                            } else if(listregmat.get(j).getNilai().equals("B")) {
                                oldBobotNilai=3;
                            } else if(listregmat.get(j).getNilai().equals("C")) {
                                oldBobotNilai=2;
                            } else if(listregmat.get(j).getNilai().equals("D")) {
                                oldBobotNilai=1;
                            }
                        }
                        if(oldBobotNilai>=BobotNilai) {
                            listregmat.remove(i);
                        } else {
                            listregmat.remove(j);
                        }
                        break;
                    }
                }
            }
        }

        for(int a=0;a<listregmat.size();a++){
            RegistrasiMatakuliah reg = listregmat.get(a);

            JSONObject json = new JSONObject();
            json.put( "id", reg.getId() );
            json.put( "kode", reg.getMatakuliah().getKode() + " " + reg.getJadwal().getAksara() );
            json.put( "nama", reg.getMatakuliah().getNama() + " " + reg.getJadwal().getAksara() );
            json.put( "sksAkademik", reg.getMatakuliah().getSksAkademik() );

            int angka = 0;
            if(reg.getNilai() == null){
                json.put( "nilai", "" );
                json.put("angka", "");
            }
            else{
                if(reg.getNilai().equals("A"))
                    angka = 4 * reg.getMatakuliah().getSksAkademik();
                else if(reg.getNilai().equals("B"))
                    angka = 3 * reg.getMatakuliah().getSksAkademik();
                else if(reg.getNilai().equals("C"))
                    angka = 2 * reg.getMatakuliah().getSksAkademik();
                else if(reg.getNilai().equals("D"))
                    angka = 1 * reg.getMatakuliah().getSksAkademik();
                else if(reg.getNilai().equals("E"))
                    angka = 0 * reg.getMatakuliah().getSksAkademik();
                else if(reg.getNilai().equals("T"))
                    angka = 0 * reg.getMatakuliah().getSksAkademik();

                json.put( "nilai", reg.getNilai() );
                json.put("angka", angka);
            }

            json.put("status", reg.getStatus());

            matkul.add( json );

            totSksAkademik = totSksAkademik + reg.getMatakuliah().getSksAkademik();
            totSksBayar = totSksBayar + reg.getMatakuliah().getSksBayar();

            totAngka = totAngka + angka;
        }

        root.put( "daftarMatkul", matkul );

        JSONObject total = new JSONObject();
        total.put("totSksAkademik", totSksAkademik);
        total.put("totAngka", totAngka);
        if(totSksAkademik > 0)
            total.put("ipk", totAngka / totSksAkademik);
        else
            total.put("ipk", 0);

        setDataTotal(total);
        System.out.println("set data " +totSksAkademik );

        return root;
    }

    public List getAllMatakuliahJadwal(Dosen dosen,Semester semester) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("SELECT reg FROM RegistrasiMatakuliah reg WHERE reg.jadwal.dosen= :input1 AND reg.jadwal.semester= :input2")
            .setParameter("input1",dosen).setParameter("input2",semester).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }
    
    public List getAllMatakuliahJadwal(Jadwal jadwal) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("SELECT reg FROM RegistrasiMatakuliah reg WHERE reg.jadwal= :input")
            .setParameter("input",jadwal).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }

    public double getIPK(Mahasiswa mhs) throws Exception{
        List<RegistrasiMatakuliah> listregmat = getAllMatakuliahMahasiswa(mhs);

        double totSksAkademik = 0;
        double totSksBayar = 0;
        double totAngka = 0;
        double ipk = 0;

        for(int i=0;i<listregmat.size();i++) {
            double BobotNilai=0;
            if(listregmat.get(i).getNilai()!=null) {
                if(listregmat.get(i).getNilai().equals("A")) {
                    BobotNilai=4;
                } else if(listregmat.get(i).getNilai().equals("B")) {
                    BobotNilai=3;
                } else if(listregmat.get(i).getNilai().equals("C")) {
                    BobotNilai=2;
                } else if(listregmat.get(i).getNilai().equals("D")) {
                    BobotNilai=1;
                }
                for(int j=i-1;j>=0;j--) {
                    if(j<0) {
                        break;
                    }
                    if(listregmat.get(i).getMatakuliah().getId()==listregmat.get(j).getMatakuliah().getId()) {
                        double oldBobotNilai=0;
                        if(listregmat.get(j).getNilai()!=null) {
                            if(listregmat.get(j).getNilai().equals("A")) {
                                oldBobotNilai=4;
                            } else if(listregmat.get(j).getNilai().equals("B")) {
                                oldBobotNilai=3;
                            } else if(listregmat.get(j).getNilai().equals("C")) {
                                oldBobotNilai=2;
                            } else if(listregmat.get(j).getNilai().equals("D")) {
                                oldBobotNilai=1;
                            }
                        }
                        if(oldBobotNilai>=BobotNilai) {
                            listregmat.remove(i);
                        } else {
                            listregmat.remove(j);
                        }
                        break;
                    }
                }
            }
        }

        for(int a=0;a<listregmat.size();a++){
            RegistrasiMatakuliah reg = listregmat.get(a);

            int angka = 0;
            if(reg.getNilai() != null){
                if(reg.getNilai().equals("A"))
                    angka = 4 * reg.getMatakuliah().getSksAkademik();
                else if(reg.getNilai().equals("B"))
                    angka = 3 * reg.getMatakuliah().getSksAkademik();
                else if(reg.getNilai().equals("C"))
                    angka = 2 * reg.getMatakuliah().getSksAkademik();
                else if(reg.getNilai().equals("D"))
                    angka = 1 * reg.getMatakuliah().getSksAkademik();
                else if(reg.getNilai().equals("E"))
                    angka = 0 * reg.getMatakuliah().getSksAkademik();
                else if(reg.getNilai().equals("T"))
                    angka = 0 * reg.getMatakuliah().getSksAkademik();

                totSksAkademik = totSksAkademik + reg.getMatakuliah().getSksAkademik();
                totAngka = totAngka + angka;
            }
        }

        if(totSksAkademik > 0)
            ipk = totAngka / totSksAkademik;
        else
            ipk = 0;

        return ipk;
    }

    public String getPredikat(Mahasiswa mhs) throws Exception{
        String predikat = "--";
        double ipk = getIPK(mhs);

        if(ipk >= 2 && ipk < 2.75)
            predikat = "baik";
        else if(ipk >= 2.75 && ipk < 3)
            predikat = "memuaskan";
        else if(ipk >= 3 && ipk < 3.5)
            predikat = "sangat memuaskan";
        else if(ipk >= 3.5 && ipk <= 4)
            predikat = "terpuji";

        return predikat;
    }

    /**
     * @return the dataTotal
     */
    public JSONObject getDataTotal() {
        return dataTotal;
    }

    /**
     * @param dataTotal the dataTotal to set
     */
    public void setDataTotal(JSONObject dataTotal) {
        this.dataTotal = dataTotal;
    }
    
}
