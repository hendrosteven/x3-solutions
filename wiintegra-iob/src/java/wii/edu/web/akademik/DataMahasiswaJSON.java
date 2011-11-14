/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wii.edu.web.akademik;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import wii.edu.core.dao.AngkatanDAO;
import wii.edu.core.dao.FakultasDAO;
import wii.edu.core.dao.MahasiswaDAO;
import wii.edu.core.dao.ObjectDAO;
import wii.edu.core.dao.ProgramStudiDAO;
import wii.edu.core.dao.DistrikDAO;
import wii.edu.core.dao.PembayaranDAO;
import wii.edu.core.dao.RegistrasiMatakuliahDAO;
import wii.edu.core.dao.SemesterDAO;
import wii.edu.core.model.Angkatan;
import wii.edu.core.model.Biodata;
import wii.edu.core.model.Fakultas;
import wii.edu.core.model.Mahasiswa;
import wii.edu.core.model.ProgramStudi;
import wii.edu.core.model.Distrik;
import wii.edu.core.model.User;

/**
 *
 * @author Retha@wii
 */
public class DataMahasiswaJSON extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            HttpSession sesi = request.getSession();
            if(sesi.getAttribute("role") == null){
                request.getRequestDispatcher("page/index.jsp").forward(request, response);
                return;
            }
            else{
                String role = (String)sesi.getAttribute("role");
                if(role.equals("user")){
                    User user = (User)sesi.getAttribute("currentUser");
                    if(user.getLevel() != 2){
                        response.sendRedirect("logout");
                        return;
                    }
                }
                else{
                        response.sendRedirect("logout");
                    return;
                }
            }
            String output = "";
            String task = request.getParameter("task");

            if(task.equals("LISTING_LIMIT")){
                int start = Integer.parseInt(request.getParameter("start"));
                int limit = Integer.parseInt(request.getParameter("limit"));

                System.out.println("start : " + start + ", limit : " + limit);
                if((request.getParameter("fakultas") == null || request.getParameter("fakultas").equals("")) &&
                   (request.getParameter("progdi") == null || request.getParameter("progdi").equals(""))){
                    output = new MahasiswaDAO().getAllMahasiswaBelumLulusJSONObject(start, limit).toString();
                }
                else if(!(request.getParameter("fakultas") == null || request.getParameter("fakultas").equals("")) &&
                         (request.getParameter("progdi") == null || request.getParameter("progdi").equals(""))){
                    long idFakultas = Long.parseLong(request.getParameter("fakultas"));
                    Fakultas fak = new FakultasDAO().getFakultas(idFakultas);
                    output = new MahasiswaDAO().getAllMahasiswaJSONObject(fak, start, limit).toString();
                }
                else if((request.getParameter("fakultas") == null || request.getParameter("fakultas").equals("")) &&
                        !(request.getParameter("progdi") == null || request.getParameter("progdi").equals(""))){
                    long idProgdi = Long.parseLong(request.getParameter("progdi"));
                    ProgramStudi prog = new ProgramStudiDAO().getProgramStudi(idProgdi);
                    Fakultas fak = prog.getFakultas();
                    output = new MahasiswaDAO().getAllMahasiswaJSONObject(fak, prog, start, limit).toString();
                }
                else{
                    long idFakultas = Long.parseLong(request.getParameter("fakultas"));
                    long idProgdi = Long.parseLong(request.getParameter("progdi"));
                    Fakultas fak = new FakultasDAO().getFakultas(idFakultas);
                    ProgramStudi prog = new ProgramStudiDAO().getProgramStudi(idProgdi);
                    output = new MahasiswaDAO().getAllMahasiswaJSONObject(fak, prog, start, limit).toString();
                }
            }
            else if(task.equals("DELETE")){
                String jsonIds = request.getParameter("ids");
                System.out.println(jsonIds);
                Object ids = JSONValue.parse(jsonIds);
                JSONArray array=(JSONArray)ids;

                int ind = 0;
                for(int a=0;a<array.size();a++){
                    System.out.println(array.get(a));
                    Mahasiswa mhs = new MahasiswaDAO().getMahasiswa((Long)array.get(a));
                    List listMtk = new RegistrasiMatakuliahDAO().getAllMatakuliahMahasiswa(mhs);
                    List listMhs = new PembayaranDAO().getByMahasiswa(mhs);
                    if((listMtk == null || listMtk.size()<=0) && (listMhs == null || listMhs.size()<=0)){
                        new ObjectDAO().delete(mhs);
                        ind++;
                        System.out.println("sukses???");
                    }
                }

                if(ind == 0){
                    output = "0";
                }
                else if(ind == array.size()){
                    output = "1";
                }
                else if(ind < array.size()){
                    output = "-1";
                }
            }else if(task.equals("UPDATE")){
                Long id = Long.parseLong(request.getParameter("id"));
                String noInduk = request.getParameter("noInduk");
                MahasiswaDAO dao = new MahasiswaDAO();
                if(dao.nomorIndukIsUsed(noInduk) && dao.getMahasiswa(noInduk).getId() != id){
                    output = "-1";
                }
                else{
                    String password = request.getParameter("password");
                    String nama = request.getParameter("nama");
                    long fakultas = Long.parseLong(request.getParameter("fakultas"));
                    long progdi = 1;
                    if(request.getParameter("progdi") == null || request.getParameter("progdi").equals(""))
                        progdi = 1;
                    else
                        progdi = Long.parseLong(request.getParameter("progdi"));
                    long angkatan = Long.parseLong(request.getParameter("angkatan"));
                    String jenjang = request.getParameter("jenjang");
                    int jenisKelamin = Integer.parseInt(request.getParameter("jenisKelamin"));
                    String tempatLahir = request.getParameter("tempatLahir");
                    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
                    Date tanggalLahir = format.parse(request.getParameter("tanggalLahir"));
                    String jalan = request.getParameter("jalan");
                    //String kelurahan = request.getParameter("kelurahan");
                    //String kecamatan = request.getParameter("kecamatan");
                    String subDistrik = request.getParameter("subDistrik");
                    long distrik = Long.parseLong(request.getParameter("distrik"));
                    String kodepos = request.getParameter("kodepos");
                    String telpon = request.getParameter("telpon");
                    String handphone = request.getParameter("handphone");
                    String email = request.getParameter("email");
                    String namaAyah = request.getParameter("namaAyah");
                    String namaIbu = request.getParameter("namaIbu");
                    String alamatOrtu = request.getParameter("alamatOrtu");

                    Mahasiswa mhs = new MahasiswaDAO().getMahasiswa(id);

                    Fakultas fak = new FakultasDAO().getFakultas(fakultas);
                    ProgramStudi prog = new ProgramStudiDAO().getProgramStudi(progdi);
                    Angkatan angk = new AngkatanDAO().getAngkatan(angkatan);
                    Distrik prov = new DistrikDAO().getDistrik(distrik);
                    Biodata bio = (Biodata)mhs.getBiodatas().get(mhs.getBiodatas().size()-1);
                    bio.setTempatLahir(tempatLahir);
                    bio.setTanggalLahir(tanggalLahir);
                    bio.setJalan(jalan);
                    //bio.setKelurahan(kelurahan);
                    //bio.setKecamatan(kecamatan);
                    bio.setSubDistrik(subDistrik);
                    bio.setDistrik(prov);
                    bio.setKodePos(kodepos);
                    bio.setTelepon(telpon);
                    bio.setHandphone(handphone);
                    bio.setEmail(email);
                    List list_biodata = new ArrayList();
                    list_biodata.add(bio);

                    //mhs.setId(id);
                    mhs.setNomorInduk(noInduk);
                    mhs.setPassword(password);
                    mhs.setNama(nama);
                    mhs.setJenisKelamin(jenisKelamin);
                    mhs.setFakultas(fak);
                    mhs.setProgdi(prog);
                    mhs.setAngkatan(angk);
                    mhs.setJenjang(jenjang);
                    mhs.setBiodatas(list_biodata);
                    mhs.setNamaAyah(namaAyah);
                    mhs.setNamaIbu(namaIbu);
                    mhs.setAlamatOrangTua(alamatOrtu);
                    mhs.setDataKelulusan(null);
                    bio.setMahasiswa(mhs);

                    output = Integer.toString(new ObjectDAO().update(mhs));
                }
            }else if(task.equals("CREATE")){
                String noInduk = request.getParameter("noInduk");

                if(new MahasiswaDAO().nomorIndukIsUsed(noInduk)){
                    output = "-1";
                }
                else{
                    String password = request.getParameter("password");
                    String nama = request.getParameter("nama");
                    long fakultas = Long.parseLong(request.getParameter("fakultas"));
                    long progdi = 1;
                    if(request.getParameter("progdi") == null || request.getParameter("progdi").equals(""))
                        progdi = 1;
                    else
                        progdi = Long.parseLong(request.getParameter("progdi"));
                    long angkatan = Long.parseLong(request.getParameter("angkatan"));
                    String jenjang = request.getParameter("jenjang");
                    int jenisKelamin = Integer.parseInt(request.getParameter("jenisKelamin"));
                    String tempatLahir = request.getParameter("tempatLahir");
                    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
                    Date tanggalLahir = format.parse(request.getParameter("tanggalLahir"));
                    String jalan = request.getParameter("jalan");
                    //String kelurahan = request.getParameter("kelurahan");
                    //String kecamatan = request.getParameter("kecamatan");
                    String subDistrik = request.getParameter("subDistrik");
                    long distrik = Long.parseLong(request.getParameter("distrik"));
                    String kodepos = request.getParameter("kodepos");
                    String telpon = request.getParameter("telpon");
                    String handphone = request.getParameter("handphone");
                    String email = request.getParameter("email");
                    String namaAyah = request.getParameter("namaAyah");
                    String namaIbu = request.getParameter("namaIbu");
                    String alamatOrtu = request.getParameter("alamatOrtu");

                    Fakultas fak = new FakultasDAO().getFakultas(fakultas);
                    ProgramStudi prog = new ProgramStudiDAO().getProgramStudi(progdi);
                    Angkatan angk = new AngkatanDAO().getAngkatan(angkatan);
                    Distrik prov = new DistrikDAO().getDistrik(distrik);
                    Biodata bio = new Biodata();
                    bio.setTempatLahir(tempatLahir);
                    bio.setTanggalLahir(tanggalLahir);
                    bio.setJalan(jalan);
                    //bio.setKelurahan(kelurahan);
                    //bio.setKecamatan(kecamatan);
                    bio.setSubDistrik(subDistrik);
                    bio.setDistrik(prov);
                    bio.setKodePos(kodepos);
                    bio.setTelepon(telpon);
                    bio.setHandphone(handphone);
                    bio.setEmail(email);
                    bio.setSemester(new SemesterDAO().getCurrentSemester());
                    List list_biodata = new ArrayList();
                    list_biodata.add(bio);

                    Mahasiswa mhs = new Mahasiswa();
                    mhs.setNomorInduk(noInduk);
                    mhs.setPassword(password);
                    mhs.setNama(nama);
                    mhs.setJenisKelamin(jenisKelamin);
                    mhs.setFakultas(fak);
                    mhs.setProgdi(prog);
                    mhs.setAngkatan(angk);
                    mhs.setJenjang(jenjang);
                    mhs.setBiodatas(list_biodata);
                    mhs.setNamaAyah(namaAyah);
                    mhs.setNamaIbu(namaIbu);
                    mhs.setAlamatOrangTua(alamatOrtu);
                    mhs.setDataKelulusan(null);
                    bio.setMahasiswa(mhs);

                    output = Integer.toString(new ObjectDAO().insert(mhs));
                    new ObjectDAO().insert(bio);
                }
            }
            else if(task.equals("DISTRIK"))
                output = new DistrikDAO().getAllDistrikJSONObject().toString();
            else if(task.equals("FAKULTAS"))
                output = new FakultasDAO().getAllFakultasJSONObject().toString();
            else if(task.equals("PROGDI")){
                String strFak = request.getParameter("fakultas");
                System.out.println("strFak : " + strFak);
                if(strFak == null || strFak.equals("")){
                    output = new ProgramStudiDAO().getAllProgramStudiMinusJSONObject().toString();
                }
                else{
                    long idFakultas = Long.parseLong(strFak);

                    Fakultas fak = new FakultasDAO().getFakultas(idFakultas);
                    output = new ProgramStudiDAO().getAllProgramStudiJSONObject(fak).toString();
                }
            }
            else if(task.equals("ANGKATAN"))
                output = new AngkatanDAO().getAllAngkatanJSONObject().toString();
            else if(task.equals("LOGOUT")){
                String jsonIds = request.getParameter("ids");
                System.out.println(jsonIds);
                Object ids = JSONValue.parse(jsonIds);
                JSONArray array=(JSONArray)ids;

                for(int a=0;a<array.size();a++){
                    System.out.println(array.get(a));
                    Mahasiswa mhs = new MahasiswaDAO().getMahasiswa((Long)array.get(a));
                    new MahasiswaDAO().logout(mhs);
                }
                output = "1";
            }

            System.out.println(output);
            out.println(output);
        } catch (Exception ex) {
            Logger.getLogger(DataMahasiswaJSON.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out.close();
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
