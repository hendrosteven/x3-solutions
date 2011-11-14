/*
 * TagihanDAO.java
 *
 * Created on July 31, 2007, 5:42 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package wii.edu.core.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.Session;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import wii.edu.core.model.Biodata;
import wii.edu.core.model.KomponenBiaya;
import wii.edu.core.model.Mahasiswa;
import wii.edu.core.model.Semester;
import wii.edu.core.model.Tagihan;
import wii.edu.core.model.TagihanDetail;

/**
 *
 * @author Hendro
 */
public class TagihanDAO {
    private JSONObject dataPembayaran;
    private JSONObject totalRekapitulasi;
    /** Creates a new instance of TagihanDAO */
    public TagihanDAO() {
    }
    
    public Tagihan getTagihan(Mahasiswa mhs, Semester sem) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        Tagihan tagihan = null;
        try{
            tagihan = (Tagihan)session.createQuery("SELECT tagihan FROM Tagihan tagihan WHERE tagihan.mhs= :input1 AND tagihan.semester= :input2")
            .setParameter("input1",mhs).setParameter("input2",sem).uniqueResult();
        }catch(Exception ex){
            throw ex;
        }
        return tagihan;
    }
    
    public JSONObject getTagihanJSONObject(Mahasiswa mhs, Semester sem) throws Exception{
        Tagihan tagih = getTagihan(mhs, sem);
        List list = tagih.getTagihanDetails();

        JSONObject root = new JSONObject();
        JSONObject root2 = new JSONObject();
        JSONArray root3 = new JSONArray();
        JSONArray tagihan = new JSONArray();
        double total = 0;


        for(int a=0;a<list.size();a++){
            TagihanDetail detail = (TagihanDetail)list.get(a);
            int sksKontrak = detail.getSksAmbil();
            double sksAmbil = new RegistrasiMatakuliahDAO().getTotalSKSBayar(mhs,sem);
            
            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", detail.getId() );
            jsonUser.put( "kode", detail.getKomponen().getKode() );
            jsonUser.put( "tagihanId", tagih.getId() );
            jsonUser.put( "nama", detail.getKomponen().getNama() );
            jsonUser.put( "jumlah", detail.getKomponen().getJumlah() );

            if(sksKontrak > 0){
                jsonUser.put( "sksKontrak", sksKontrak );
                jsonUser.put( "sksAmbil", sksAmbil );
                jsonUser.put( "biayaPerSks", mhs.getAngkatan().getBiayaPerSks() );
                
                double besarTagihan = 0;
                if(sksAmbil > 0){
                    besarTagihan = sksAmbil * mhs.getAngkatan().getBiayaPerSks();
                }
                else{
                    besarTagihan = sksKontrak * mhs.getAngkatan().getBiayaPerSks();
                }
                jsonUser.put( "besarTagihan", besarTagihan );
                total = total + besarTagihan;
            }
            else{
                jsonUser.put( "sksKontrak", "" );
                jsonUser.put( "sksAmbil", "" );
                jsonUser.put( "biayaPerSks", "" );
                jsonUser.put( "besarTagihan", detail.getTagih() );
                total = total + detail.getTagih();
            }

            tagihan.add( jsonUser );

        }

        //JSONArray rootPembayaran = new JSONArray();
        JSONObject dataByr = new JSONObject();

        dataByr.put( "total", total );
        dataByr.put( "bayarAwal", tagih.getBayar() );
        dataByr.put( "bayarLunas", tagih.getPelunasan() );
        dataByr.put( "isDispenAwal", tagih.getIsDispensasi() );
        dataByr.put( "isDispenLunas", tagih.getIsDispensasiPelunasan() );

        //rootPembayaran.add(dataByr);
        setDataPembayaran(dataByr);

        root.put( "tagihan", tagihan );

        return root;
    }

    /**
     * Mengambil mahasiswa yang sudah bayar pada semester tertentu
     */
    public List getRegMahasiswa(Semester sem) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("SELECT tagihan FROM Tagihan tagihan WHERE tagihan.semester= :input")
            .setParameter("input",sem).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }
    
    public JSONObject getRegMahasiswaJSONObject(Semester sem) throws Exception{
        List list = getRegMahasiswa(sem);

        JSONObject root = new JSONObject();
        JSONArray mahasiswa = new JSONArray();


        for(int a=0;a<list.size();a++){
            Tagihan tagih = (Tagihan)list.get(a);
            Mahasiswa mhs = tagih.getMhs();
            Biodata bio = (Biodata)mhs.getBiodatas().get(mhs.getBiodatas().size()-1);

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", mhs.getId() );
            jsonUser.put( "noInduk", mhs.getNomorInduk() );
            jsonUser.put( "nama", mhs.getNama() );
            jsonUser.put( "jalan", bio.getJalan() );
            jsonUser.put( "kelurahan", bio.getKelurahan() );
            jsonUser.put( "kecamatan", bio.getKecamatan() );
            jsonUser.put( "subDistrik", bio.getSubDistrik() );
            jsonUser.put( "distrik", bio.getDistrik().getId() );
            jsonUser.put( "kodepos", bio.getKodePos() );
            jsonUser.put( "telpon", bio.getTelepon() );
            jsonUser.put( "handphone", bio.getHandphone() );
            jsonUser.put( "email", bio.getEmail() );

            mahasiswa.add( jsonUser );

        }

        root.put( "daftarMahasiswa", mahasiswa );

        return root;
    }

    /**
     * Digunakan untuk mengecek apakah mahasiswa sudah melakukan registrasi pada semester tertentu
     */
    public boolean isRegistrasi(Mahasiswa mhs,Semester sem) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        Tagihan tagihan = null;
        try{
            tagihan = (Tagihan)session.createQuery("SELECT tagihan FROM Tagihan tagihan WHERE tagihan.mhs= :input1 AND tagihan.semester= :input2 AND (tagihan.bayar>0 OR tagihan.isDispensasi=1)")
            .setParameter("input1",mhs).setParameter("input2",sem).uniqueResult();
        }catch(Exception ex){
            throw ex;
        }
        if(tagihan!=null){
            return true;
        }else{
            return false;
        }
    }
    
    public List getDetailTagihanSemester(Semester sem) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("SELECT tagihan FROM Tagihan tagihan WHERE tagihan.semester= :input AND (tagihan.bayar>0 OR tagihan.isDispensasi=1)")
            .setParameter("input",sem).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }
    
    public JSONObject getDetailTagihanSemesterJSONObject(Semester sem) throws Exception{
        List list = getDetailTagihanSemester(sem);

        JSONObject root = new JSONObject();
        JSONArray rekap = new JSONArray();
        double totalBayar = 0;
        double totalLunas = 0;
        double totalTagih = 0;
        double totalTunggakan = 0;


        for(int a=0;a<list.size();a++){
            Tagihan tagih = (Tagihan)list.get(a);

            JSONObject jsonUser = new JSONObject();
            jsonUser.put( "id", tagih.getId() );
            jsonUser.put( "noInduk", tagih.getMhs().getNomorInduk() );
            jsonUser.put( "total", tagih.getTotalTagihan() );
            jsonUser.put( "isDispenAwal", tagih.getIsDispensasi() == 0 ? "Tunai" : "Dispensasi");
            jsonUser.put( "biayaAwal", tagih.getBayar() );
            jsonUser.put( "isDispenLunas", tagih.getIsDispensasiPelunasan() == 0 ? "Tunai" : "Dispensasi" );
            jsonUser.put( "biayaLunas", tagih.getPelunasan() );
            jsonUser.put( "tunggakan", tagih.getTotalTagihan()-(tagih.getBayar()+tagih.getPelunasan()) );

            /*if(tagih.getTotalTagihan()-(tagih.getBayar()+tagih.getPelunasan()) >= 0)
                jsonUser.put( "tunggakan", tagih.getTotalTagihan()-(tagih.getBayar()+tagih.getPelunasan()) );
            else*/
            
            rekap.add( jsonUser );

            totalBayar = totalBayar + tagih.getBayar();
            totalLunas = totalLunas + tagih.getPelunasan();
            totalTagih = totalTagih + tagih.getTotalTagihan();
            totalTunggakan = totalTunggakan + (tagih.getTotalTagihan()-(tagih.getBayar()+tagih.getPelunasan()));

        }

        JSONObject totRekap = new JSONObject();

        totRekap.put( "totalBayar", totalBayar);
        totRekap.put( "totalLunas", totalLunas );
        totRekap.put( "totalTagih", totalTagih );
        totRekap.put( "totalTunggakan", totalTunggakan );

        setTotalRekapitulasi(totRekap);

        root.put( "daftarRekap", rekap );

        return root;
    }

    public List getDetailTagihanPeriode(Date awal,Date akhir) throws Exception{
        HibernateUtil.beginTransaction();
        Session session = HibernateUtil.getSession();
        List list = new ArrayList();
        try{
            list = session.createQuery("SELECT tagihan FROM Tagihan tagihan WHERE tagihan.tagihanDetails.tanggalBayar>= :input1 AND tagihan.tagihanDetails.taggalBayar<= :input2")
            .setParameter("input1",awal).setParameter("input2",akhir).list();
        }catch(Exception ex){
            throw ex;
        }
        return list;
    }

    /**
     * @return the dataPembayaran
     */
    public JSONObject getDataPembayaran() {
        return dataPembayaran;
    }

    /**
     * @param dataPembayaran the dataPembayaran to set
     */
    public void setDataPembayaran(JSONObject dataPembayaran) {
        this.dataPembayaran = dataPembayaran;
    }

    /**
     * @return the totalRekapitulasi
     */
    public JSONObject getTotalRekapitulasi() {
        return totalRekapitulasi;
    }

    /**
     * @param totalRekapitulasi the totalRekapitulasi to set
     */
    public void setTotalRekapitulasi(JSONObject totalRekapitulasi) {
        this.totalRekapitulasi = totalRekapitulasi;
    }
    
}
