/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package wii.edu.web.akademik;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import wii.edu.core.dao.FakultasDAO;
import wii.edu.core.dao.JadwalDAO;
import wii.edu.core.dao.ProgramStudiDAO;
import wii.edu.core.dao.RegistrasiMatakuliahDAO;
import wii.edu.core.dao.SemesterDAO;
import wii.edu.core.model.Dosen;
import wii.edu.core.model.Fakultas;
import wii.edu.core.model.ProgramStudi;
import wii.edu.core.model.Semester;
import wii.edu.core.model.User;

/**
 *
 * @author Retha@wii
 */
public class DaftarKelasAkademikJSON extends HttpServlet {
   
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

            Semester semester=new SemesterDAO().getCurrentSemester();
            //HttpSession sesi = request.getSession();
            Dosen dosen=(Dosen)sesi.getAttribute("currentDosen");
            String task = request.getParameter("task");

            if(task.equals("JADWAL")){
                int start = Integer.parseInt(request.getParameter("start"));
                int limit = Integer.parseInt(request.getParameter("limit"));

                if((request.getParameter("fakultas") == null || request.getParameter("fakultas").equals("")) &&
                   (request.getParameter("progdi") == null || request.getParameter("progdi").equals(""))){
                    output = new JadwalDAO().getJadwalJSONObjectAll(semester, start, limit).toString();
                }
                else if(!(request.getParameter("fakultas") == null || request.getParameter("fakultas").equals("")) &&
                         (request.getParameter("progdi") == null || request.getParameter("progdi").equals(""))){
                    long idFakultas = Long.parseLong(request.getParameter("fakultas"));
                    Fakultas fak = new FakultasDAO().getFakultas(idFakultas);
                    output = new JadwalDAO().getJadwalJSONObjectAll(semester, fak, start, limit).toString();
                }
                else if((request.getParameter("fakultas") == null || request.getParameter("fakultas").equals("")) &&
                        !(request.getParameter("progdi") == null || request.getParameter("progdi").equals(""))){
                    long idProgdi = Long.parseLong(request.getParameter("progdi"));
                    ProgramStudi prog = new ProgramStudiDAO().getProgramStudi(idProgdi);
                    Fakultas fak = prog.getFakultas();
                    output = new JadwalDAO().getJadwalJSONObjectAll(semester, fak, prog, start, limit).toString();
                }
                else{
                    long idFakultas = Long.parseLong(request.getParameter("fakultas"));
                    long idProgdi = Long.parseLong(request.getParameter("progdi"));
                    Fakultas fak = new FakultasDAO().getFakultas(idFakultas);
                    ProgramStudi prog = new ProgramStudiDAO().getProgramStudi(idProgdi);
                    output = new JadwalDAO().getJadwalJSONObjectAll(semester, fak, prog, start, limit).toString();
                }
            }
            else if(task.equals("FAKULTAS"))
                output = new FakultasDAO().getAllFakultasJSONObject().toString();
            else if(task.equals("PROGDI")){
                String strFak = request.getParameter("fakultas");
                if(strFak == null || strFak.equals("")){
                    output = new ProgramStudiDAO().getAllProgramStudiJSONObject().toString();
                }
                else{
                    long idFakultas = Long.parseLong(strFak);

                    Fakultas fak = new FakultasDAO().getFakultas(idFakultas);
                    output = new ProgramStudiDAO().getAllProgramStudiPlusJSONObject(fak).toString();
                }
            }
            else if(task.equals("CURRENT")){
                output = "Semester " + semester.getNama() + " Tahun Ajaran " + semester.getTahunAjaran();
            }
            else if(task.equals("MAHASISWA_LIMIT")){
                int start = Integer.parseInt(request.getParameter("start"));
                int limit = Integer.parseInt(request.getParameter("limit"));

                output = new RegistrasiMatakuliahDAO().getRegistrasiMatakuliahJSONObject(new JadwalDAO().getJadwal(Long.parseLong(request.getParameter("id"))), start, limit).toString();
            }

            System.out.println(output);
            out.println(output);
        } catch (Exception ex) {
            Logger.getLogger(DaftarKelasAkademikJSON.class.getName()).log(Level.SEVERE, null, ex);
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
