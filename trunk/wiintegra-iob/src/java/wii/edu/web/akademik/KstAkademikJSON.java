/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wii.edu.web.akademik;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import wii.edu.core.dao.JadwalDAO;
import wii.edu.core.dao.MahasiswaDAO;
import wii.edu.core.dao.MahasiswaIP;
import wii.edu.core.dao.ObjectDAO;
import wii.edu.core.dao.RegistrasiMatakuliahDAO;
import wii.edu.core.dao.SemesterDAO;
import wii.edu.core.model.Jadwal;
import wii.edu.core.model.Mahasiswa;
import wii.edu.core.model.RegistrasiMatakuliah;
import wii.edu.core.model.Semester;
import wii.edu.core.model.User;

/**
 *
 * @author Retha@wii
 */
public class KstAkademikJSON extends HttpServlet {
   
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
            //HttpSession sesi = request.getSession();
            RegistrasiMatakuliahDAO dao = new RegistrasiMatakuliahDAO();

            if(task.equals("MAHASISWA")){
                output = new MahasiswaDAO().getAllMahasiswaBelumLulusJSONObject().toString();
            }
            else if(task.equals("SEMESTER"))
                output = new SemesterDAO().getAllSemesterJSONObjectforCombobox().toString();
            else if(task.equals("CURRENT")){
                long idSemester = Long.parseLong(request.getParameter("idSemester"));
                long idMahasiswa = Long.parseLong(request.getParameter("idMahasiswa"));

                Semester semester = new SemesterDAO().getSemester(idSemester);
                Mahasiswa mahasiswa = new MahasiswaDAO().getMahasiswa(idMahasiswa);

                JSONObject json = new JSONObject();
                json.put("currentSemester", semester.getNama() + " " + semester.getTahunAjaran());
                json.put("noIndukMhs", mahasiswa.getNomorInduk());
                json.put("namaMhs", mahasiswa.getNama());
                output = json.toString();
            }
            else if(task.equals("LISTING")){
                long idSemester = Long.parseLong(request.getParameter("idSemester"));
                long idMahasiswa = Long.parseLong(request.getParameter("idMahasiswa"));

                Semester semester = new SemesterDAO().getSemester(idSemester);
                Mahasiswa mahasiswa = new MahasiswaDAO().getMahasiswa(idMahasiswa);

                output = dao.getMatakuliahMahasiswaJSONObject(mahasiswa,semester).toString();
                sesi.setAttribute("dataTotal", dao.getDataTotal().toString());
            }
            else if(task.equals("LISTING_MATKUL")){
                long idSemester = Long.parseLong(request.getParameter("idSemester"));
                long idMahasiswa = Long.parseLong(request.getParameter("idMahasiswa"));

                Semester semester = new SemesterDAO().getSemester(idSemester);
                Mahasiswa mahasiswa = new MahasiswaDAO().getMahasiswa(idMahasiswa);

                output = new JadwalDAO().getJadwalJSONObject(semester, mahasiswa.getFakultas(), mahasiswa.getProgdi()).toString();
            }
            else if(task.equals("DELETE")){
                String jsonIds = request.getParameter("ids");
                System.out.println(jsonIds);
                Object ids = JSONValue.parse(jsonIds);
                JSONArray array=(JSONArray)ids;

                for(int a=0;a<array.size();a++){
                    System.out.println(array.get(a));
                    RegistrasiMatakuliah reg = new RegistrasiMatakuliahDAO().getRegistrasiMatakuliah((Long)array.get(a));
                    new ObjectDAO().delete(reg);
                }
                output = "1";
                /*long id = Long.parseLong(request.getParameter("id"));

                RegistrasiMatakuliah reg = new RegistrasiMatakuliahDAO().getRegistrasiMatakuliah(id);
                output = Integer.toString(new ObjectDAO().delete(reg));*/
            }
            else if(task.equals("TAMBAH")){
                Jadwal jadwal = new JadwalDAO().getJadwal(Long.parseLong(request.getParameter("idJadwal")));
                Mahasiswa mahasiswa = new MahasiswaDAO().getMahasiswa(Long.parseLong(request.getParameter("idMahasiswa")));
                RegistrasiMatakuliah regmat = new RegistrasiMatakuliah();
                regmat.setJadwal(jadwal);
                regmat.setMatakuliah(jadwal.getMatakuliah());
                regmat.setMahasiswa(mahasiswa);
                regmat.setStatus(request.getParameter("status"));
                output = Integer.toString(new ObjectDAO().insert(regmat));
            }
            else if(task.equals("DETAIL")){
                Jadwal jadwal = new JadwalDAO().getJadwal(Long.parseLong(request.getParameter("idJadwal")));
                Mahasiswa mahasiswa = new MahasiswaDAO().getMahasiswa(Long.parseLong(request.getParameter("idMahasiswa")));

                //Cek Masih Bisa Ambil Matakuliah Atau Tidak
                int bisaambil = 0;
                double IPK = new MahasiswaIP().getIPK(mahasiswa);
                double IPS = new MahasiswaIP().getIPS(mahasiswa, new SemesterDAO().getSemesterSebelumnya());
                int MaxBeban = new MahasiswaIP().getMaxBeban(IPK, IPS);
                List<RegistrasiMatakuliah> regmatlist = new RegistrasiMatakuliahDAO().getMatakuliahMahasiswa(mahasiswa, new SemesterDAO().getCurrentSemester());
                int TotalSKS = 0;
                for (int i = 0; i < regmatlist.size(); i++) {
                    TotalSKS += regmatlist.get(i).getMatakuliah().getSksAkademik();
                }
                TotalSKS += jadwal.getMatakuliah().getSksAkademik();
                if (MaxBeban >= TotalSKS) {
                    bisaambil = 1;
                }

                //Cek Sudah Mengambil Matakuliah Ini Pada Semester Ini
                int sudahdiambil = 0;
                for (int i = 0; i < regmatlist.size(); i++) {
                    if (regmatlist.get(i).getJadwal().getMatakuliah().getId() == jadwal.getMatakuliah().getId()) {
                        sudahdiambil = 1;
                        break;
                    }
                }

                //Cek Sudah Mengambil Matakuliah Prasyarat Pada Semester Lalu
                int ambildulu = 0;
                if (jadwal.getMatakuliah().getPrasyarat() != null) {
                    ambildulu = 1;
                    List<RegistrasiMatakuliah> regmatlist2 = new RegistrasiMatakuliahDAO().getAllMatakuliahIPK(mahasiswa, new SemesterDAO().getCurrentSemester());
                    for (int i = 0; i < regmatlist2.size(); i++) {
                        if (regmatlist2.get(i).getMatakuliah().getId() == jadwal.getMatakuliah().getPrasyarat().getId()) {
                            ambildulu = 0;
                            break;
                        }
                    }
                }

                //Cek Jumlah Kursi Yang Masih Tersedia
                int jumlahpeserta = new RegistrasiMatakuliahDAO().getRegistrasiMatakuliah(jadwal).size();
                int tersedia = jadwal.getKapasitas() - jumlahpeserta;

                JSONObject json = new JadwalDAO().getJadwalJSONObject(Long.parseLong(request.getParameter("idJadwal")));
                json.put("bisaambil", bisaambil);
                json.put("sudahdiambil", sudahdiambil);
                json.put("ambildulu", ambildulu);
                json.put("tersedia", tersedia);
                if(ambildulu == 1)
                    json.put("prasyarat", jadwal.getMatakuliah().getPrasyarat().getKode() + " - " + jadwal.getMatakuliah().getPrasyarat().getNama());

                output = json.toString();
            }
            else if(task.equals("TOTAL")){
                output = (String)sesi.getAttribute("dataTotal");
                sesi.removeAttribute("dataTotal");
            }

            System.out.println(output);
            out.println(output);
        } catch (Exception ex) {
            Logger.getLogger(KstAkademikJSON.class.getName()).log(Level.SEVERE, null, ex);
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
