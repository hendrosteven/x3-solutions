/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wii.edu.web.mahasiswa;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;
import wii.edu.core.dao.JadwalDAO;
import wii.edu.core.dao.MahasiswaIP;
import wii.edu.core.dao.ObjectDAO;
import wii.edu.core.dao.PembayaranDAO;
import wii.edu.core.dao.RegistrasiMatakuliahDAO;
import wii.edu.core.dao.SemesterDAO;
import wii.edu.core.model.Jadwal;
import wii.edu.core.model.Mahasiswa;
import wii.edu.core.model.RegistrasiMatakuliah;
import wii.edu.core.model.Semester;

/**
 *
 * @author Retha@wii
 */
public class RegMatakuliahJSON extends HttpServlet {
   
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
                if(!role.equals("mahasiswa")){
                    response.sendRedirect("logout");
                    return;
                }
            }

            String output = "";
            String task = request.getParameter("task");
            //HttpSession sesi = request.getSession();
            Mahasiswa mahasiswa = (Mahasiswa) sesi.getAttribute("currentMhs");
            Semester semester = new SemesterDAO().getCurrentSemester();

            System.out.println(task);

            if(task.equals("CEK")){
                //Cek Sudah Bayar Apa Lom
                int sudahbayar = 0;
                //if (new TagihanDAO().isRegistrasi(mahasiswa, semester)) {
                if(new PembayaranDAO().isLunas(mahasiswa, semester)){
                    sudahbayar = 1;
                }

                //Cek Masih Bisa Registrasi Apa Tidak
                int valid = 1;
                Date TanggalSekarang = new Date();
                Date TanggalBatas = new SimpleDateFormat("yyyy-MM-dd").parse(new SemesterDAO().getCurrentSemester().getBatasRegistrasi());
                GregorianCalendar calTanggalBatas = new GregorianCalendar();
                calTanggalBatas.set(TanggalBatas.getYear(), TanggalBatas.getMonth(), TanggalBatas.getDate(), 0, 0, 0);
                GregorianCalendar calTanggalSekarang = new GregorianCalendar();
                calTanggalSekarang.set(TanggalSekarang.getYear(), TanggalSekarang.getMonth(), TanggalSekarang.getDate(), 0, 0, 0);

                if (calTanggalSekarang.after(calTanggalBatas)) {
                    valid = 0;
                }

//                valid = 1;
//                sudahbayar = 0;

                JSONObject json = new JSONObject();
                json.put("sudahbayar", sudahbayar);
                json.put("valid", valid);
                json.put("currentSemester", semester.getNama() + " " + semester.getTahunAjaran());
                output = json.toString();
            }
            else if(task.equals("LISTING")){
                output = new JadwalDAO().getJadwalJSONObject(semester, mahasiswa.getFakultas(), mahasiswa.getProgdi()).toString();
            }
            else if(task.equals("DETAIL")){
                Jadwal jadwal = new JadwalDAO().getJadwal(Long.parseLong(request.getParameter("id")));

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

                JSONObject json = new JadwalDAO().getJadwalJSONObject(Long.parseLong(request.getParameter("id")));
                json.put("bisaambil", bisaambil);
                json.put("sudahdiambil", sudahdiambil);
                json.put("ambildulu", ambildulu);
                json.put("tersedia", tersedia);
                if(ambildulu == 1)
                    json.put("prasyarat", jadwal.getMatakuliah().getPrasyarat().getKode() + " - " + jadwal.getMatakuliah().getPrasyarat().getNama());

                output = json.toString();
            }
            else if(task.equals("INSERT")){
                Jadwal jadwal = new JadwalDAO().getJadwal(Long.parseLong(request.getParameter("id")));
                RegistrasiMatakuliah regmat = new RegistrasiMatakuliah();
                regmat.setJadwal(jadwal);
                regmat.setMatakuliah(jadwal.getMatakuliah());
                regmat.setMahasiswa(mahasiswa);
                regmat.setStatus("B");
                output = Integer.toString(new ObjectDAO().insert(regmat));
            }

            System.out.println(output);
            out.println(output);
        } catch (Exception ex) {
            Logger.getLogger(RegMatakuliahJSON.class.getName()).log(Level.SEVERE, null, ex);
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
