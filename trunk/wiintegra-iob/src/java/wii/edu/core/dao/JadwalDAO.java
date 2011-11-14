package wii.edu.core.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import wii.edu.core.model.Dosen;
import wii.edu.core.model.Fakultas;
import wii.edu.core.model.Jadwal;
import wii.edu.core.model.Jadwal;
import wii.edu.core.model.Matakuliah;
import wii.edu.core.model.ProgramStudi;
import wii.edu.core.model.Ruang;
import wii.edu.core.model.Semester;

public class JadwalDAO {

    public JadwalDAO() {
    }

    public Jadwal getJadwal(long id) throws Exception {
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        Jadwal jadwal = null;
        try {
            jadwal = (Jadwal) session.load(Jadwal.class, id);
        } catch (Exception ex) {
            throw ex;
        }
        return jadwal;
    }

    public JSONObject getJadwalJSONObject(long id) throws Exception{
        Jadwal jadwal = getJadwal(id);

        JSONObject root = new JSONObject();

        root.put( "id", jadwal.getId() );
        root.put( "kode", jadwal.getMatakuliah().getKode() + " " + jadwal.getAksara() );
        root.put( "nama", jadwal.getMatakuliah().getNama() );
        root.put( "sksAkademik", jadwal.getMatakuliah().getSksAkademik() );
        root.put( "sksBayar", jadwal.getMatakuliah().getSksBayar() );
        root.put( "ruang", jadwal.getRuang().getNama() );
        root.put( "dosen", jadwal.getDosen().getNama() );
        root.put( "waktu", jadwal.getHari() +  ", " + jadwal.getJamMulai() + " - " + jadwal.getJamSelesai());

        return root;
    }

    public List getJadwal(Semester semester) throws Exception {
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try {
            list = session.createQuery("SELECT jadwal FROM Jadwal jadwal WHERE jadwal.semester= :input ORDER BY matakuliah.fakultas.id ASC,  matakuliah.progdi.id ASC").setParameter("input", semester).list();
        } catch (Exception ex) {
            throw ex;
        }
        return list;
    }

    public List getJadwal(Semester semester, int start, int limit) throws Exception {
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try {
            list = session.createQuery("SELECT jadwal FROM Jadwal jadwal WHERE jadwal.semester= :input").setFirstResult(start).setMaxResults(limit).setParameter("input", semester).list();
        } catch (Exception ex) {
            throw ex;
        }
        return list;
    }

    public JSONObject getJadwalJSONObject(Semester semester) throws Exception{
        List list = getJadwal(semester);

        JSONObject root = new JSONObject();
        JSONArray jadwal = new JSONArray();


        for(int a=0;a<list.size();a++){
            Jadwal jad = (Jadwal)list.get(a);

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", jad.getId() );
            jsonUser.put( "kode", jad.getMatakuliah().getKode() + " " + jad.getAksara() );
            jsonUser.put( "matkul", jad.getMatakuliah().getId() );
            jsonUser.put( "kelas", jad.getAksara());
            jsonUser.put( "dosen", jad.getDosen().getId() );
            jsonUser.put( "hari", jad.getHari());
            jsonUser.put( "mulai", jad.getJamMulai());
            jsonUser.put( "selesai", jad.getJamSelesai());
            jsonUser.put( "ruang", jad.getRuang().getId() );
            jsonUser.put( "kapasitas", jad.getKapasitas() );

            jadwal.add( jsonUser );

        }

        root.put( "daftarJadwal", jadwal );

        return root;
    }

    public JSONObject getJadwalJSONObject(Semester semester, int start, int limit) throws Exception{
        List list = getJadwal(semester, start, limit);
        List list_all = getJadwal(semester);

        JSONObject root = new JSONObject();
        JSONArray jadwal = new JSONArray();


        for(int a=0;a<list.size();a++){
            Jadwal jad = (Jadwal)list.get(a);

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", jad.getId() );
            jsonUser.put( "kode", jad.getMatakuliah().getKode() + " " + jad.getAksara() );
            jsonUser.put( "matkul", jad.getMatakuliah().getId() );
            jsonUser.put( "kelas", jad.getAksara());
            jsonUser.put( "dosen", jad.getDosen().getId() );
            jsonUser.put( "hari", jad.getHari());
            jsonUser.put( "mulai", jad.getJamMulai());
            jsonUser.put( "selesai", jad.getJamSelesai());
            jsonUser.put( "ruang", jad.getRuang().getId() );
            jsonUser.put( "kapasitas", jad.getKapasitas() );

            jadwal.add( jsonUser );

        }

        root.put( "total", list_all.size() );
        root.put( "daftarJadwal", jadwal );

        return root;
    }

    public JSONObject getJadwalJSONObjectAll(Semester semester) throws Exception{
        List list = getJadwal(semester);

        JSONObject root = new JSONObject();
        JSONArray jadwal = new JSONArray();


        for(int a=0;a<list.size();a++){
            Jadwal jad = (Jadwal)list.get(a);

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", jad.getId() );
            jsonUser.put( "kode", jad.getMatakuliah().getKode() + " " + jad.getAksara() );
            jsonUser.put( "nama", jad.getMatakuliah().getNama() + " " + jad.getAksara() );
            jsonUser.put( "dosen", jad.getDosen().getNama() );
            jsonUser.put( "waktu", jad.getHari() + ", " + jad.getJamMulai() + " - " + jad.getJamSelesai());
            jsonUser.put( "ruang", jad.getRuang().getNama() );
            jsonUser.put( "kapasitas", jad.getKapasitas() );

            jadwal.add( jsonUser );

        }

        root.put( "daftarJadwal", jadwal );

        return root;
    }

    public JSONObject getJadwalJSONObjectAll(Semester semester, int start, int limit) throws Exception{
        List list = getJadwal(semester, start, limit);
        List list_all = getJadwal(semester);

        JSONObject root = new JSONObject();
        JSONArray jadwal = new JSONArray();


        for(int a=0;a<list.size();a++){
            Jadwal jad = (Jadwal)list.get(a);

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", jad.getId() );
            jsonUser.put( "kode", jad.getMatakuliah().getKode() + " " + jad.getAksara() );
            jsonUser.put( "nama", jad.getMatakuliah().getNama() + " " + jad.getAksara() );
            jsonUser.put( "dosen", jad.getDosen().getNama() );
            jsonUser.put( "waktu", jad.getHari() + ", " + jad.getJamMulai() + " - " + jad.getJamSelesai());
            jsonUser.put( "ruang", jad.getRuang().getNama() );
            jsonUser.put( "kapasitas", jad.getKapasitas() );

            jadwal.add( jsonUser );

        }

        root.put( "total", list_all.size() );
        root.put( "daftarJadwal", jadwal );

        return root;
    }

    public JSONObject getJadwalJSONObjectAll(Semester semester, Fakultas fak, int start, int limit) throws Exception{
        List list = getJadwal(semester, fak, start, limit);
        List list_all = getJadwal(semester, fak);

        JSONObject root = new JSONObject();
        JSONArray jadwal = new JSONArray();


        for(int a=0;a<list.size();a++){
            Jadwal jad = (Jadwal)list.get(a);

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", jad.getId() );
            jsonUser.put( "kode", jad.getMatakuliah().getKode() + " " + jad.getAksara() );
            jsonUser.put( "nama", jad.getMatakuliah().getNama() + " " + jad.getAksara() );
            jsonUser.put( "dosen", jad.getDosen().getNama() );
            jsonUser.put( "waktu", jad.getHari() + ", " + jad.getJamMulai() + " - " + jad.getJamSelesai());
            jsonUser.put( "ruang", jad.getRuang().getNama() );
            jsonUser.put( "kapasitas", jad.getKapasitas() );

            jadwal.add( jsonUser );

        }

        root.put( "total", list_all.size() );
        root.put( "daftarJadwal", jadwal );

        return root;
    }

    public JSONObject getJadwalJSONObjectAll(Semester semester, Fakultas fak, ProgramStudi prog, int start, int limit) throws Exception{
        List list = getJadwal(semester, fak, prog, start, limit);
        List list_all = getJadwal(semester, fak, prog);

        JSONObject root = new JSONObject();
        JSONArray jadwal = new JSONArray();


        for(int a=0;a<list.size();a++){
            Jadwal jad = (Jadwal)list.get(a);

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", jad.getId() );
            jsonUser.put( "kode", jad.getMatakuliah().getKode() + " " + jad.getAksara() );
            jsonUser.put( "nama", jad.getMatakuliah().getNama() + " " + jad.getAksara() );
            jsonUser.put( "dosen", jad.getDosen().getNama() );
            jsonUser.put( "waktu", jad.getHari() + ", " + jad.getJamMulai() + " - " + jad.getJamSelesai());
            jsonUser.put( "ruang", jad.getRuang().getNama() );
            jsonUser.put( "kapasitas", jad.getKapasitas() );

            jadwal.add( jsonUser );

        }

        root.put( "total", list_all.size() );
        root.put( "daftarJadwal", jadwal );

        return root;
    }

    public List getJadwal(Dosen dosen) throws Exception {
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try {
            list = session.createQuery("SELECT jadwal FROM Jadwal jadwal WHERE jadwal.dosen= :input").setParameter("input", dosen).list();
        } catch (Exception ex) {
            throw ex;
        }
        return list;
    }

    public List getJadwal(Matakuliah mtk) throws Exception {
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try {
            list = session.createQuery("SELECT jadwal FROM Jadwal jadwal WHERE jadwal.matakuliah= :input").setParameter("input", mtk).list();
        } catch (Exception ex) {
            throw ex;
        }
        return list;
    }

    public List getJadwal(Ruang ruang) throws Exception {
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try {
            list = session.createQuery("SELECT jadwal FROM Jadwal jadwal WHERE jadwal.ruang= :input").setParameter("input", ruang).list();
        } catch (Exception ex) {
            throw ex;
        }
        return list;
    }

    public List getJadwal(Semester semester, Dosen dosen) throws Exception {
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try {
            list = session.createQuery("SELECT jadwal FROM Jadwal jadwal WHERE jadwal.semester= :input AND jadwal.dosen= :input2").setParameter("input", semester).setParameter("input2", dosen).list();
        } catch (Exception ex) {
            throw ex;
        }
        return list;
    }

    public JSONObject getJadwalJSONObject(Semester semester, Dosen dosen) throws Exception{
        List list = getJadwal(semester, dosen);

        JSONObject root = new JSONObject();
        JSONArray jadwal = new JSONArray();


        for(int a=0;a<list.size();a++){
            Jadwal jad = (Jadwal)list.get(a);

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", jad.getId() );
            jsonUser.put( "kode", jad.getMatakuliah().getKode() + " " + jad.getAksara() );
            jsonUser.put( "nama", jad.getMatakuliah().getNama() + " " + jad.getAksara() );
            jsonUser.put( "waktu", jad.getHari() + ", Pkl. " + jad.getJamMulai() + " - " + jad.getJamSelesai());
            jsonUser.put( "ruang", jad.getRuang().getNama() );

            jadwal.add( jsonUser );

        }

        root.put( "daftarJadwal", jadwal );

        return root;
    }

    public List getJadwal(Semester semester, Fakultas fakultas) throws Exception {
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try {
            list = session.createQuery("SELECT jadwal FROM Jadwal jadwal WHERE jadwal.semester= :input AND jadwal.matakuliah.fakultas= :input2 ORDER BY jadwal.matakuliah.progdi.id ASC").setParameter("input", semester).setParameter("input2", fakultas).list();
        } catch (Exception ex) {
            throw ex;
        }
        return list;
    }

    public List getJadwal(Semester semester, Fakultas fakultas, ProgramStudi prog) throws Exception {
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try {
            list = session.createQuery("SELECT jadwal FROM Jadwal jadwal WHERE jadwal.semester= :input AND jadwal.matakuliah.fakultas= :input2 AND (jadwal.matakuliah.progdi = :input3 OR jadwal.matakuliah.progdi.id = 1)")
                    .setParameter("input", semester).setParameter("input2", fakultas).setParameter("input3", prog).list();
        } catch (Exception ex) {
            throw ex;
        }
        return list;
    }

    public List getJadwal(Semester semester, Fakultas fakultas, int start, int limit) throws Exception {
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try {
            list = session.createQuery("SELECT jadwal FROM Jadwal jadwal WHERE jadwal.semester= :input AND jadwal.matakuliah.fakultas= :input2")
                    .setFirstResult(start).setMaxResults(limit).setParameter("input", semester).setParameter("input2", fakultas).list();
        } catch (Exception ex) {
            throw ex;
        }
        return list;
    }

    public List getJadwal(Semester semester, Fakultas fakultas, ProgramStudi prog, int start, int limit) throws Exception {
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try {
            list = session.createQuery("SELECT jadwal FROM Jadwal jadwal WHERE jadwal.semester= :input AND jadwal.matakuliah.fakultas= :input2 AND jadwal.matakuliah.progdi= :input3")
                    .setFirstResult(start).setMaxResults(limit).setParameter("input", semester).setParameter("input2", fakultas).setParameter("input3", prog).list();
        } catch (Exception ex) {
            throw ex;
        }
        return list;
    }

    public JSONObject getJadwalJSONObject(Semester semester, Fakultas fakultas) throws Exception{
        List list = getJadwal(semester, fakultas);

        JSONObject root = new JSONObject();
        JSONArray jadwal = new JSONArray();


        for(int a=0;a<list.size();a++){
            Jadwal jad = (Jadwal)list.get(a);

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", jad.getId() );
            jsonUser.put( "kode", jad.getMatakuliah().getKode() + " " + jad.getAksara() );
            jsonUser.put( "nama", jad.getMatakuliah().getNama() + " " + jad.getAksara() );

            jadwal.add( jsonUser );

        }

        root.put( "daftarJadwal", jadwal );

        return root;
    }

    public JSONObject getJadwalJSONObject(Semester semester, Fakultas fak, ProgramStudi prog) throws Exception{
        List list = getJadwal(semester, fak, prog);

        JSONObject root = new JSONObject();
        JSONArray jadwal = new JSONArray();


        for(int a=0;a<list.size();a++){
            Jadwal jad = (Jadwal)list.get(a);

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", jad.getId() );
            jsonUser.put( "kode", jad.getMatakuliah().getKode() + " " + jad.getAksara() );
            jsonUser.put( "nama", jad.getMatakuliah().getNama() + " " + jad.getAksara() );

            jadwal.add( jsonUser );

        }

        root.put( "daftarJadwal", jadwal );

        return root;
    }

    public JSONObject getJadwalInFakultasJSONObject(Semester semester, Fakultas fakultas) throws Exception{
        List list = getJadwal(semester, fakultas);

        JSONObject root = new JSONObject();
        JSONArray jadwal = new JSONArray();


        for(int a=0;a<list.size();a++){
            Jadwal jad = (Jadwal)list.get(a);

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", jad.getId() );
            jsonUser.put( "kode", jad.getMatakuliah().getKode() + " " + jad.getAksara() );
            jsonUser.put( "matkul", jad.getMatakuliah().getId() );
            jsonUser.put( "kelas", jad.getAksara());
            jsonUser.put( "dosen", jad.getDosen().getId() );
            jsonUser.put( "hari", jad.getHari());
            jsonUser.put( "mulai", jad.getJamMulai());
            jsonUser.put( "selesai", jad.getJamSelesai());
            jsonUser.put( "ruang", jad.getRuang().getId() );
            jsonUser.put( "kapasitas", jad.getKapasitas() );

            jadwal.add( jsonUser );

        }

        root.put( "daftarJadwal", jadwal );

        return root;
    }

    public JSONObject getJadwalInFakultasJSONObject(Semester semester, Fakultas fakultas, int start, int limit) throws Exception{
        List list = getJadwal(semester, fakultas, start, limit);
        List list_all = getJadwal(semester, fakultas);

        JSONObject root = new JSONObject();
        JSONArray jadwal = new JSONArray();


        for(int a=0;a<list.size();a++){
            Jadwal jad = (Jadwal)list.get(a);

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", jad.getId() );
            jsonUser.put( "kode", jad.getMatakuliah().getKode() + " " + jad.getAksara() );
            jsonUser.put( "matkul", jad.getMatakuliah().getId() );
            jsonUser.put( "kelas", jad.getAksara());
            jsonUser.put( "dosen", jad.getDosen().getId() );
            jsonUser.put( "hari", jad.getHari());
            jsonUser.put( "mulai", jad.getJamMulai());
            jsonUser.put( "selesai", jad.getJamSelesai());
            jsonUser.put( "ruang", jad.getRuang().getId() );
            jsonUser.put( "kapasitas", jad.getKapasitas() );

            jadwal.add( jsonUser );

        }

        root.put( "total", list_all.size() );
        root.put( "daftarJadwal", jadwal );

        return root;
    }

    public JSONObject getJadwalInFakultasJSONObject(Semester semester, Fakultas fakultas, ProgramStudi prog, int start, int limit) throws Exception{
        List list = getJadwal(semester, fakultas, prog, start, limit);
        List list_all = getJadwal(semester, fakultas, prog);

        JSONObject root = new JSONObject();
        JSONArray jadwal = new JSONArray();


        for(int a=0;a<list.size();a++){
            Jadwal jad = (Jadwal)list.get(a);

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", jad.getId() );
            jsonUser.put( "kode", jad.getMatakuliah().getKode() + " " + jad.getAksara() );
            jsonUser.put( "matkul", jad.getMatakuliah().getId() );
            jsonUser.put( "kelas", jad.getAksara());
            jsonUser.put( "dosen", jad.getDosen().getId() );
            jsonUser.put( "hari", jad.getHari());
            jsonUser.put( "mulai", jad.getJamMulai());
            jsonUser.put( "selesai", jad.getJamSelesai());
            jsonUser.put( "ruang", jad.getRuang().getId() );
            jsonUser.put( "kapasitas", jad.getKapasitas() );

            jadwal.add( jsonUser );

        }

        root.put( "total", list_all.size() );
        root.put( "daftarJadwal", jadwal );

        return root;
    }

    /*public JSONObject getAllJadwalJSONObject(Semester semester) throws Exception{
        List list = getJadwal(semester);

        JSONObject root = new JSONObject();
        JSONArray jadultas = new JSONArray();


        for(int a=0;a<list.size();a++){
            Jadwal jad = (Jadwal)list.get(a);

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", jad.getId() );
            jsonUser.put( "kode", jad.getKode() );
            jsonUser.put( "nama", jad.getNama() );

            jadultas.add( jsonUser );

        }

        root.put( "daftarJadwal", jadultas );

        return root;
    }*/
}